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
import com.jingna.checklistapp.util.StatusBarUtil;
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
        StatusBarUtil.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.color_ffff));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(MainActivity.this,0x55000000);
        }
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
    @OnClick({R.id.imageView,R.id.textView4,R.id.textView1,R.id.textView2,R.id.textView3,R.id.tv_card})
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
            case R.id.tv_card:
                intent.setClass(context,MybankCardActivity.class);
                startActivity(intent);
                break;
        }
    }
}
