package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
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
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;

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
        ButterKnife.bind(BillListActivity.this);
        initData();
    }
    @OnClick({R.id.rl_back,R.id.serche_order,R.id.ll_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
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
        final TextView tv_sfcode = contentview.findViewById(R.id.tv_sfcode);
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
                        tv_sfcode.setText(billContentBean.getData().getLinkIdcard());
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
                // ToastUtil.showShort(context, id+"");
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
