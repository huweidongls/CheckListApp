package com.jingna.checklistapp.page;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.base.BaseActivity;
import com.jingna.checklistapp.util.StatusBarUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QdOrderDetailsActivity extends BaseActivity {

    private Context context = QdOrderDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qd_order_details);

        StatusBarUtils.setStatusBar(QdOrderDetailsActivity.this, getResources().getColor(R.color.theme));
        ButterKnife.bind(QdOrderDetailsActivity.this);
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
