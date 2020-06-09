package com.jingna.checklistapp.fragment;

import android.view.View;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.base.LazyFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/6/9.
 */

public class FragmentMyOrder extends LazyFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

}
