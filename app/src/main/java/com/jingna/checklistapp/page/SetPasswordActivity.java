package com.jingna.checklistapp.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends AppCompatActivity {
    private Context context = SetPasswordActivity.this;
    private int PwdRadio = 0;
    private String phone = "";
    @BindView(R.id.edget_pwd)
    EditText edget_pwd;
    @BindView(R.id.iv_get_img)
    ImageView iv_get_img;
    @BindView(R.id.btn_login)
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        StatusBarUtil.setStatusBarColor(SetPasswordActivity.this, getResources().getColor(R.color.color_ffff));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(SetPasswordActivity.this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(SetPasswordActivity.this,0x55000000);
        }
        ButterKnife.bind(SetPasswordActivity.this);
        phone = getIntent().getStringExtra("phone");
        if(phone.isEmpty()){
            ToastUtil.showShort(context,"参数错误!");
            finish();
        }
        initView();
        initPwd();
    }
    @OnClick({R.id.rl_back,R.id.btn_login})
    public void onClick(View view) {
        final Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_login:
                String pwd = edget_pwd.getText().toString();
                if(pwd.isEmpty()){
                    ToastUtil.showShort(context,"请输入密码!");
                }else{
                    Map<String,String> map = new LinkedHashMap<>();
                    map.put("phone",phone);
                    map.put("newPassword",pwd);
                    ViseUtil.Post(context, NetUrl.AppCooperativeMerchantretrievePassword, map, new ViseUtil.ViseListener() {
                        @Override
                        public void onReturn(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.optString("status").equals("200")){
                                    ToastUtil.showShort(context,"修改成功!");
                                    SpUtils.clear(context);
                                    intent.setClass(context,LoginActivity.class);
                                    startActivity(intent);
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
    private void initPwd() {
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

    private void initView() {
        edget_pwd.addTextChangedListener(new TextWatcher() {
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
