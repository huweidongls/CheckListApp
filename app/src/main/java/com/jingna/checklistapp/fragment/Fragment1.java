package com.jingna.checklistapp.fragment;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.base.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/6/9.
 */

public class Fragment1 extends LazyFragment {

    @BindView(R.id.mViewPager)
    ViewPager viewPager;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.view2)
    View view2;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment1;
    }

    @Override
    protected void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
    }

    private void initData() {

        MainAdapter mainAdapter = new MainAdapter(getChildFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mainAdapter);
        viewPager.addOnPageChangeListener(mainAdapter);

    }

    @OnClick({R.id.ll1, R.id.ll2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll1:
                select(0);
                break;
            case R.id.ll2:
                select(1);
                break;
        }
    }

    private void select(int i) {

        if(i == 0){
            tv1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.INVISIBLE);
            viewPager.setCurrentItem(0);
        }else if(i == 1){
            tv1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(1);
        }

    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new FragmentQiangOrder();
        Fragment fragment2 = new FragmentMyOrder();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(fragment1);
            fragments.add(fragment2);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                tv1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
            }else if(position == 1){
                tv1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tv2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
