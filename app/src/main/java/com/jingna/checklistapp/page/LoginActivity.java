package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View ;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.Loginbean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.StringUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private Context context = LoginActivity.this;
    private int PwdRadio = 0;
    @BindView(R.id.edget_pwd)
    EditText edget_pwd;
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
        if (!SpUtils.getUserId(context).equals("0")) {
            Intent intent = new Intent();
            intent.setClass(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick({R.id.rl_back, R.id.btn_login})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_login:
                String phone = et_phone.getText().toString();
                String pwd = edget_pwd.getText().toString();
                if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(context, "请输入手机号或密码");
                } else {
                    dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("username", phone);
                    map.put("password", pwd);
                    ViseUtil.Get(context, NetUrl.AppCooperativeMerchantloginAppPassword, map, dialog, new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            Log.e("123123", s);
                            Gson gson = new Gson();
                            Loginbean loginbean = gson.fromJson(s, Loginbean.class);
                            //Log.e("tokens8888",loginbean.getData().getToken());
                            SpUtils.setToken(context, loginbean.getData().getToken());
                            SpUtils.setUserId(context, loginbean.getData().getUserId() + "");
                            SpUtils.setUserName(context, loginbean.getData().getCompanyName());
                            Map<String, String> map = new LinkedHashMap<>();
                            map.put("fxToken", loginbean.getData().getToken());
                            ViseHttp.CONFIG().baseUrl(NetUrl.BASE_URL)
                                    .globalHeaders(map);
                            Intent intent = new Intent();
                            intent.setClass(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                break;
        }
    }

}
