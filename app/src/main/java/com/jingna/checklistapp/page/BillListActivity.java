package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.adapter.BillAdapter;
import com.jingna.checklistapp.adapter.BillListAdapter;
import com.jingna.checklistapp.bean.BillContentBean;
import com.jingna.checklistapp.bean.BillListBean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillListActivity extends AppCompatActivity {
    private Context context = BillListActivity.this;
    private List<BillListBean.DataBean> mList;
    private BillListAdapter adapter;
    private int bill_radio = 2;
    private int REQUEST_CODE = 100;
    private int bill_id = 0;
    @BindView(R.id.serche_order)
    Button serche_order;
    @BindView(R.id.rr_empty)
    RelativeLayout rr_empty;
    @BindView(R.id.ll_list)
    LinearLayout ll_list;
    @BindView(R.id.et_codes)
    EditText et_codes;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_ordersn)
    TextView tv_ordersn;
    @BindView(R.id.tv_list_radio)
    TextView tv_list_radio;
    @BindView(R.id.tv_price)
    TextView tv_price;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        StatusBarUtil.setStatusBarColor(BillListActivity.this, getResources().getColor(R.color.white_red));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(BillListActivity.this, false)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(BillListActivity.this,0x55000000);
        }
        ButterKnife.bind(BillListActivity.this);
        initData();
    }
    @OnClick({R.id.rl_back,R.id.serche_order,R.id.ll_list,R.id.iv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_code:
                //startActivityForResult(new Intent(context, CaptureActivity.class), REQUEST_CODE);
                Intent intent = new Intent();
                intent.setClass(context, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.ll_list:
                if(bill_radio == 1){
                    ToastUtil.showShort(context, "已核销!");
                }else{
                    showShareTable(bill_id);
                }
                break;
            case R.id.serche_order:
                String code = et_codes.getText().toString();
                if(code.isEmpty()){
                    ToastUtil.showShort(context, "请填写电子码!");
                }else{
                    dialog = WeiboDialogUtils.createLoadingDialog(context, "检索中...");
                    Map<String,String> map = new LinkedHashMap<>();
                    map.put("num",code);
                    ViseUtil.Get(context, NetUrl.AppVerificationSheetRecordgetOneByNum, map,dialog,new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if(jsonObject.optString("status").equals("200")){
                                    if(jsonObject.optString("data").isEmpty()){
                                        ToastUtil.showShort(context, "暂无内容!");
                                    }else{
                                        rr_empty.setVisibility(View.GONE);
                                        ll_list.setVisibility(View.VISIBLE);
                                        Gson gson = new Gson();
                                        BillListBean bean = gson.fromJson(s,BillListBean.class);
                                        tv_title.setText(bean.getData().getRecordName());
                                        tv_time.setText(bean.getData().getCreateTime());
                                        tv_ordersn.setText(bean.getData().getRecordName());
                                        if(bean.getData().getRecordStatus()==0){
                                            tv_list_radio.setText("未核销");
                                        }else{
                                            tv_list_radio.setText("已核销");
                                        }
                                        bill_radio = bean.getData().getRecordStatus();//存储状态
                                        bill_id = bean.getData().getId();
                                        tv_price.setText("￥"+bean.getData().getRecordPrice()+"元");
                                    }
                                }else{
                                    ToastUtil.showShort(context, jsonObject.optString("errorMsg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
        }
    }
    public void showShareTable(final int id){
        View contentview = LayoutInflater.from(this).inflate(R.layout.activity_writeoff_info, null);
        Button btn_order = contentview.findViewById(R.id.btn_sub);
        final int[] orderprice = {0};
        final TextView tv_order = contentview.findViewById(R.id.tv_order);
        final TextView tv_title = contentview.findViewById(R.id.tv_title);
        final TextView tv_price = contentview.findViewById(R.id.tv_price);
        final TextView tv_tel = contentview.findViewById(R.id.tv_tel);
        final TextView tv_lxr = contentview.findViewById(R.id.tv_lxr);
        final TextView tv_cancel = contentview.findViewById(R.id.tv_cancel);
        Map<String,String> map = new LinkedHashMap<>();
        map.put("id",id+"");
        ViseUtil.Get(context, NetUrl.AppVerificationSheetRecordgetOne, map, new ViseUtil.ViseListener() {
            @Override
            public void onReturn(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.optString("status").equals("200")){
                        Gson gson = new Gson();
                        BillContentBean billContentBean = gson.fromJson(s,BillContentBean.class);
                        orderprice[0] = billContentBean.getData().getRecordPrice();
                        tv_order.setText("电子码 "+billContentBean.getData().getRecordNum());
                        tv_title.setText(billContentBean.getData().getRecordName());
                        tv_price.setText("￥"+billContentBean.getData().getRecordPrice()+"元");
                        tv_tel.setText(billContentBean.getData().getLinkPhone());
                        tv_lxr.setText(billContentBean.getData().getLinkman());
                    }else{
                        ToastUtil.showShort(context, jsonObject.optString("errorMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        final ButtomDialogView dialogView=new ButtomDialogView(this,contentview,true,true);
        dialogView.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.cancel();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = WeiboDialogUtils.createLoadingDialog(context, "等待中...");
                Map<String,String> map1 = new LinkedHashMap<>();
                map1.put("recordId",id+"");
                map1.put("merchantId", SpUtils.getUserId(context));
                map1.put("price",orderprice[0]+"");
                ViseUtil.Post(context, NetUrl.AppVerificationSheetRecordupdateStatus, map1,dialog, new ViseUtil.ViseListener() {
                    @Override
                    public void onReturn(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(jsonObject.optString("status").equals("200")){
                                ToastUtil.showShort(context, "操作成功!");
                                tv_list_radio.setText("已核销");
                                bill_radio = 1;
                                dialogView.cancel();
                            }else{
                                ToastUtil.showShort(context, jsonObject.optString("errorMsg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private void initData() {
       /* mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        adapter = new BillListAdapter(mList, new BillListAdapter.ClickListener() {
            @Override
            public void onItemClick(int pos) {
                showShareTable(pos);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    et_codes.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShort(context, "解析二维码失败");
                }
            }
        }
    }
    public class ButtomDialogView extends Dialog {
        private boolean iscancelable;//控制点击dialog外部是否dismiss
        private boolean isBackCancelable;//控制返回键是否dismiss
        private boolean isBackCanCelable;//
        private View view;
        private Context context;
        public ButtomDialogView(Context context, View view, boolean isCancelable,boolean isBackCancelable) {
            super(context, R.style.MyDialog);
            this.context = context;
            this.view = view;
            this.iscancelable = isCancelable;
            this.isBackCanCelable=isBackCancelable;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(view);//这行一定要写在前面
            setCancelable(iscancelable);//点击外部不可dismiss
            setCanceledOnTouchOutside(isBackCanCelable);
            Window window = this.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = 1000;//WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

    }
}
