package com.jingna.checklistapp.page;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.base.BaseActivity;
import com.jingna.checklistapp.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderDetailsActivity extends BaseActivity {

    private Context context = MyOrderDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        StatusBarUtils.setStatusBar(MyOrderDetailsActivity.this, getResources().getColor(R.color.theme));
        ButterKnife.bind(MyOrderDetailsActivity.this);
        initData();

    }

    private void initData() {



    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
