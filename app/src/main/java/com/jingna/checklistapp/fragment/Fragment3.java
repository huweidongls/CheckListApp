package com.jingna.checklistapp.fragment;

import android.content.Intent;
import android.view.View;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.base.LazyFragment;
import com.jingna.checklistapp.page.AlipayBindActivity;
import com.jingna.checklistapp.page.BillActivity;
import com.jingna.checklistapp.page.BillListActivity;
import com.jingna.checklistapp.page.ChangePasswordActivity;
import com.jingna.checklistapp.page.JifenInfoActivity;
import com.jingna.checklistapp.page.LoginActivity;
import com.jingna.checklistapp.page.MybankCardActivity;
import com.jingna.checklistapp.util.SpUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/6/9.
 */

public class Fragment3 extends LazyFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment3;
    }

    @Override
    protected void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @OnClick({R.id.textView1, R.id.textView2, R.id.textView3, R.id.tv_card, R.id.tv_jifen, R.id.tv_zfb})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_zfb://跳转支付宝，绑定页面
                intent.setClass(getContext(), AlipayBindActivity.class);
                startActivity(intent);
                break;
            case R.id.textView1:
                intent.setClass(getContext(), BillListActivity.class);
                startActivity(intent);
                break;
            case R.id.textView2:
                intent.setClass(getContext(), BillActivity.class);
                startActivity(intent);
                break;
            case R.id.textView3:
                intent.setClass(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_card:
                intent.setClass(getContext(), MybankCardActivity.class);
                intent.putExtra("type", "mysql");
                startActivity(intent);
                break;
            case R.id.tv_jifen:
                intent.setClass(getContext(), JifenInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

}
