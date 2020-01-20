package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.BillContentBean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankCardInsertActivity extends AppCompatActivity {
    private Context context = BankCardInsertActivity.this;
    private boolean iv_agree = false;
    @BindView(R.id.et_bank_card)
    EditText et_bank_card;
    @BindView(R.id.et_bank_name)
    EditText et_bank_name;
    @BindView(R.id.et_phonenum)
    EditText et_phonenum;
    @BindView(R.id.btn_agree)
    Button btnAgree;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_agree)
    ImageView ivAgree;
    private String code="";
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_insert);
        StatusBarUtil.setStatusBarColor(BankCardInsertActivity.this, getResources().getColor(R.color.color_ffff));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(BankCardInsertActivity.this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(BankCardInsertActivity.this,0x55000000);
        }
        ButterKnife.bind(BankCardInsertActivity.this);
    }
    @OnClick({R.id.rl_back,R.id.iv_agree,R.id.btn_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_agree:
                if(!iv_agree){
                    iv_agree = true;
                    Glide.with(context).load(R.mipmap.apply_true).into(ivAgree);
                    btnAgree.setBackgroundResource(R.drawable.bg_ff0004_2dp);
                }else {
                    iv_agree = false;
                    Glide.with(context).load(R.mipmap.apply_false).into(ivAgree);
                    btnAgree.setBackgroundResource(R.drawable.bg_cccccc_2dp);
                }
                break;
            case R.id.btn_agree:
                String et_bank_cards = et_bank_card.getText().toString();
                String et_bank_names = et_bank_name.getText().toString();
                String et_phonenums = et_phonenum.getText().toString();
                String et_username = et_name.getText().toString();
                if(et_bank_cards.isEmpty() || et_bank_names.isEmpty() || et_phonenums.isEmpty() || et_username.isEmpty()){
                    ToastUtil.showShort(context, "请将信息填写完整!");
                }else if(!isPhoneNumberValid(et_phonenums)){
                    ToastUtil.showShort(context, "请输入正确的手机号!");
                }else{
                    if (!iv_agree){
                        ToastUtil.showShort(context, "请勾选协议!");
                    }else{
                        showShareTable(et_bank_cards,et_bank_names,et_phonenums,et_username);
                    }
                }
                break;
        }
    }
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "^((1[3,4,5,7,8,9]{1}[0-9]{1})+\\d{8})$";

        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public void showShareTable(final String cards,final String bankname,final String phone,final String et_username){
        View contentview = LayoutInflater.from(this).inflate(R.layout.dialog_bank_code, null);
        final TextView tv_phone_title = contentview.findViewById(R.id.tv_phone_title);
        final TextView tv_cancel = contentview.findViewById(R.id.tv_cancel);
        final TextView tv_get_code = contentview.findViewById(R.id.tv_get_code);
        final TextView et_code = contentview.findViewById(R.id.et_code);
        final TextView tv_sure = contentview.findViewById(R.id.tv_sure);
        tv_phone_title.setText("输入电话号"+phone+"的短信验证码");
        final TimeCount time = new TimeCount(60000, 1000, tv_get_code);
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.isEmpty()){
                    ToastUtil.showShort(context, "电话号码不能为空!");
                }else{
                    time.start();
                    Map<String,String> map = new LinkedHashMap<>();
                    map.put("phone",phone);
                    ViseUtil.Get(context, NetUrl.AppCooperativeMerchantsendMessage, map, new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if(jsonObject.optString("status").equals("200")){
                                    code = jsonObject.optString("data");
                                    ToastUtil.showShort(context, "验证码发送成功，请注意查收!");
                                }else{
                                    ToastUtil.showShort(context, jsonObject.optString("errorMsg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

        }
        });
        final BankCardInsertActivity.ButtomDialogView dialogView=new BankCardInsertActivity.ButtomDialogView(this,contentview,false,false);
        dialogView.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.cancel();//关闭当前窗口
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_code.getText().toString().isEmpty()){
                    ToastUtil.showShort(context, "请输入验证码!");
                }else{
                    if(!code.equals(et_code.getText().toString())){
                        ToastUtil.showShort(context,"验证码不正确!");
                    }else{//提交银行卡信息
                        dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                        Map<String,String> map = new LinkedHashMap<>();
                        map.put("cardNumber",cards);
                        map.put("cardName",bankname);
                        map.put("cardPhone",phone);
                        map.put("realName",et_username);
                        map.put("cardChannel","银行卡");
                        map.put("cardMerchantId",SpUtils.getUserId(context));
                        ViseUtil.Post(context, NetUrl.AppCooperativeMerchantCardtoUpdate, map, dialog, new ViseUtil.ViseListener() {
                            @Override
                            public void onReturn(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if(jsonObject.optString("status").equals("200")){
                                        ToastUtil.showShort(context, "绑定成功!");
                                        dialogView.cancel();//关闭当前窗口
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    class TimeCount extends CountDownTimer {
        private TextView get_code;
        public TimeCount(long millisInFuture, long countDownInterval,TextView get_code) {
            super(millisInFuture, countDownInterval);
            this.get_code = get_code;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            get_code.setClickable(false);
            get_code.setText("("+millisUntilFinished / 1000 +")后获取 ");
        }
        @Override
        public void onFinish() {
            get_code.setText("获取验证码");
            get_code.setClickable(true);
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
