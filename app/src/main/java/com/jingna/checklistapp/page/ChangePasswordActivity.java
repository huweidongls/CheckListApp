package com.jingna.checklistapp.page;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity {
    private Context context = ChangePasswordActivity.this;
    private TimeCount time;
    @BindView(R.id.edget_code)
    EditText edget_code;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_get_code)
    TextView button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(ChangePasswordActivity.this);
        initView();
        time = new TimeCount(60000, 1000);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();//倒计时开始
            }
        });

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
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //button3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            button3.setClickable(false);
            button3.setText("("+millisUntilFinished / 1000 +")后获取 ");
        }

        @Override
        public void onFinish() {
            button3.setText("获取验证码");
            button3.setClickable(true);
            //button3.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }
}

