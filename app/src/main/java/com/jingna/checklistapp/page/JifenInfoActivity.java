package com.jingna.checklistapp.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.StatusBarUtil;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JifenInfoActivity extends AppCompatActivity {
    private Context context = JifenInfoActivity.this;
    @BindView(R.id.tv_ptb)
    TextView tv_ptb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen_info);
        StatusBarUtil.setStatusBarColor(JifenInfoActivity.this, getResources().getColor(R.color.color_ffff));
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(JifenInfoActivity.this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(JifenInfoActivity.this,0x55000000);
        }
        ButterKnife.bind(JifenInfoActivity.this);
        initData();
    }

    private void initData() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("id", SpUtils.getUserId(context));
        ViseUtil.Get(context, NetUrl.AppCooperativeMerchantgetBalanceById, map, new ViseUtil.ViseListener() {
            @Override
            public void onReturn(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.optString("status").equals("200")){
                        tv_ptb.setText(roundByScale(jsonObject.optDouble("data"),2));
                    }else{
                        ToastUtil.showShort(context, jsonObject.optString("errorMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }
    @OnClick({R.id.rl_back,R.id.rl_details})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_details:
                intent.setClass(context,BillActivity.class);
                startActivity(intent);
                break;
        }
    }
}
