package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.BankTiXianBean;
import com.jingna.checklistapp.bean.BnakListBean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.StringUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommissionActivity extends AppCompatActivity {
    private Context context = CommissionActivity.this;

    private List<String> mList;
    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.btn_mone)
    Button btn_mone;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_select)
    TextView tvSelect;

    private String bankId = "";
    private double allMoney;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);
        StatusBarUtil.setStatusBarColor(CommissionActivity.this, getResources().getColor(R.color.color_ffff));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(CommissionActivity.this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(CommissionActivity.this,0x55000000);
        }
        ButterKnife.bind(CommissionActivity.this);
        initData();
    }
    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
    private void initData() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", SpUtils.getUserId(context));
        ViseUtil.Get(context, NetUrl.AppVerificationSheetCheckwithdrawal, map, new ViseUtil.ViseListener() {
            @Override
            public void onReturn(String s) {
                Gson gson = new Gson();
                BankTiXianBean bean = gson.fromJson(s, BankTiXianBean.class);
                allMoney = bean.getData().getBalance();
                tvMoney.setText("余额¥" + StringUtils.roundByScale(allMoney, 2) + "，");
                bankId = bean.getData().getCardid()+"";
                tvBankName.setText(bean.getData().getCardname());
                tvSelect.setVisibility(View.GONE);
            }
        });

        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String message = s.toString();
                if (StringUtils.isEmpty(message)) {
                    btn_mone.setBackgroundColor(Color.parseColor("#CF9297"));
                    ToastUtil.showShort(CommissionActivity.this, "请填写提现金额");
                } else {
                    btn_mone.setBackgroundColor(Color.parseColor("#A02630"));
                }

            }
        });
    }
    @OnClick({R.id.rl_back, R.id.btn_mone, R.id.all, R.id.ll_bank})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_mone:
                String msg = money.getText().toString();
                int m = 0;
                if(!StringUtils.isEmpty(msg)){
                    m = Integer.valueOf(msg);
                }
                if (StringUtils.isEmpty(msg)) {
                    ToastUtil.showShort(CommissionActivity.this, "请填写提现金额");
                } else if(StringUtils.isEmpty(bankId)){
                    ToastUtil.showShort(CommissionActivity.this, "请选择提现银行卡");
                }else if(!(m % 100 == 0)){
                    ToastUtil.showShort(CommissionActivity.this, "只能提现100的倍数金额");
                }else if(m>allMoney){
                    ToastUtil.showShort(CommissionActivity.this, "提现金额超过当前余额");
                }else {

                    dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("merchantId", SpUtils.getUserId(context));
                    map.put("cardId", bankId);
                    map.put("money", msg);
                    ViseUtil.Get(context, NetUrl.AppVerificationSheetCheckwithdrawalApply, map, dialog, new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            ToastUtil.showShort(context, "提现成功");
                            Intent intent = new Intent();
                            intent.setClass(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
                break;
            case R.id.all:
                money.setText((int) allMoney+"");
                money.setSelection(money.getText().toString().length());
                break;
            case R.id.ll_bank:
                intent.setClass(context, MybankCardActivity.class);
                intent.putExtra("type", "tixian");
                startActivityForResult(intent, 1003);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003 && data != null) {
            BnakListBean.DataBean bean = (BnakListBean.DataBean) data.getSerializableExtra("bean");
            bankId = bean.getId() + "";
            tvBankName.setText(bean.getCardName());
            tvSelect.setVisibility(View.GONE);
        }
    }
}
