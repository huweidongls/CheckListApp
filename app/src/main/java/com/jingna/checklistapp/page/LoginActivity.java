package com.jingna.checklistapp.page;

import android.content.Context;
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
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(LoginActivity.this);
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
