package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.app.MyApplication;
import com.jingna.checklistapp.bean.Loginbean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private Context context = LoginActivity.this;
    private int PwdRadio = 0;
    @BindView(R.id.edget_pwd)
    EditText edget_pwd;
    @BindView(R.id.iv_get_img)
    ImageView iv_get_img;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.btn_login)
    Button btn_login;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
        if(!SpUtils.getUserId(context).equals("0")){
            Intent intent = new Intent();
            intent.setClass(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
        initPwd();
        initView();
    }
    /**
     *
     * 切换密码明文密文
     */
    private void initPwd(){
        iv_get_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PwdRadio == 0){
                    edget_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Glide.with(context).load(R.mipmap.normal).into(iv_get_img);
                    PwdRadio = 1;
                }else{
                    edget_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Glide.with(context).load(R.mipmap.yanjing).into(iv_get_img);
                    PwdRadio = 0;
                }
                edget_pwd.setSelection(edget_pwd.length());
            }
        });
    }
    @OnClick({R.id.rl_back,R.id.btn_login})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_login:
                String phone = et_phone.getText().toString();
                String pwd = edget_pwd.getText().toString();
                if(pwd.isEmpty() || phone.isEmpty()){
                    ToastUtil.showShort(context, "请输入手机号/密码登录!");
                }else{
                    dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                    Map<String,String> map = new LinkedHashMap<>();
                    map.put("username",phone);
                    map.put("password",pwd);
                    ViseUtil.Get(context, NetUrl.AppCooperativeMerchantloginAppPassword, map, dialog, new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if(jsonObject.optString("status").equals("200")){
                                    Gson gson = new Gson();
                                    Loginbean loginbean = gson.fromJson(s, Loginbean.class);
                                    SpUtils.setToken(context, loginbean.getData().getToken());
                                    SpUtils.setUserId(context, loginbean.getData().getUserId() + "");
                                    SpUtils.setUserName(context,loginbean.getData().getCompanyName());
                                    Map<String, String> map = new LinkedHashMap<>();
                                    map.put("fxToken", loginbean.getData().getToken());
                                    ViseHttp.CONFIG().baseUrl(NetUrl.BASE_URL)
                                            .globalHeaders(map);
                                    Intent intent = new Intent();
                                    intent.setClass(context, MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
    private void initView(){
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length()>0){
                   btn_login.setBackgroundResource(R.drawable.loginbg);
               }else if(s.length()<=0){
                   btn_login.setBackgroundResource(R.drawable.shape);
               }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
