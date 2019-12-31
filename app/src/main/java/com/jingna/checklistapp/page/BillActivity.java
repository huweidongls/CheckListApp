package com.jingna.checklistapp.page;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.checklistapp.R;
import com.jingna.checklistapp.adapter.BillAdapter;
import com.jingna.checklistapp.bean.BillBean;
import com.jingna.checklistapp.net.NetUrl;
import com.jingna.checklistapp.util.SpUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.jingna.checklistapp.util.ViseUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillActivity extends AppCompatActivity {
    private Context context = BillActivity.this;
    private List<BillBean.DataBean> mList;
    private BillAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(BillActivity.this);
        initData();
    }
    @OnClick({R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
    private void initData() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("id", SpUtils.getUserId(context));
        ViseUtil.Get(context, NetUrl.AppVerificationSheetCheckqueryListById, map, new ViseUtil.ViseListener() {
            @Override
            public void onReturn(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.optString("status").equals("200")){
                        Gson gson = new Gson();
                        BillBean bean = gson.fromJson(s,BillBean.class);
                        mList = bean.getData();
                        adapter = new BillAdapter(mList);
                        LinearLayoutManager manager = new LinearLayoutManager(context);
                        manager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
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
