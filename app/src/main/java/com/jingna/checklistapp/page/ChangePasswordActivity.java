package com.jingna.checklistapp.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {
    private Context context = ChangePasswordActivity.this;
    private TimeCount time;
    private String code ="";
    @BindView(R.id.edget_code)
    EditText edget_code;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_get_code)
    TextView button3;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(ChangePasswordActivity.this);
        initView();
        time = new TimeCount(60000, 1000);

    }
    private void initView(){
        edget_code.addTextChangedListener(new TextWatcher() {
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
    @OnClick({R.id.rl_back,R.id.tv_get_code,R.id.btn_login})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_login:
                String phones = et_phone.getText().toString();
                String msgcode = edget_code.getText().toString();
                if(phones.isEmpty()){
                    ToastUtil.showShort(context,"请填写电话号码!");
                }else{
                    if(msgcode.isEmpty()){
                        ToastUtil.showShort(context,"请输入验证码!");
                    }else{
                        if(!code.equals(msgcode)){
                            ToastUtil.showShort(context,"验证码不正确!");
                        }else{
                            intent.setClass(context,SetPasswordActivity.class);
                            intent.putExtra("phone",et_phone.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
                break;
            case R.id.tv_get_code:
                String phone = et_phone.getText().toString();
                if (phone.isEmpty()){
                    ToastUtil.showShort(context,"请填写电话号码!");
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
                                    ToastUtil.showShort(context,"验证码发送成功，请注意查收!");
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
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            button3.setClickable(false);
            button3.setText("("+millisUntilFinished / 1000 +")后获取 ");
        }

        @Override
        public void onFinish() {
            button3.setText("获取验证码");
            button3.setClickable(true);
        }
    }
}

