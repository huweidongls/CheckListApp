package com.jingna.checklistapp.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.Loginbean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.jingna.checklistapp.util.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private Context context = MainActivity.this;
    @BindView(R.id.textview_user)
    TextView textview_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        textview_user.setText(SpUtils.getUserName(context));
        initData();
    }

    private void initData() {
        if(SpUtils.getUserId(context).equals("0")){
            Intent intent = new Intent();
            intent.setClass(context, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @OnClick({R.id.imageView,R.id.textView4,R.id.textView1,R.id.textView2,R.id.textView3})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imageView:
                SpUtils.clear(context);
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.textView4:
                SpUtils.clear(context);
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.textView1:
                intent.setClass(context,BillListActivity.class);
                startActivity(intent);
                break;
            case R.id.textView2:
                intent.setClass(context,BillActivity.class);
                startActivity(intent);
                break;
            case R.id.textView3:
                intent.setClass(context,ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
