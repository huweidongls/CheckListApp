package com.jingna.checklistapp.page;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.app.MyApplication;
import com.jingna.checklistapp.base.BaseActivity;
import com.jingna.checklistapp.fragment.Fragment1;
import com.jingna.checklistapp.fragment.Fragment2;
import com.jingna.checklistapp.fragment.Fragment3;
import com.jingna.checklistapp.util.StatusBarUtils;
import com.jingna.checklistapp.util.ToastUtil;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private Context context = MainActivity.this;

    @BindView(R.id.mViewPager)
    ViewPager mViewPger;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator alphaTabsIndicator;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication.getInstance().addActivity(MainActivity.this);
        ButterKnife.bind(MainActivity.this);
        PermissionManager.instance().request(this, new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_allow) + "\n" + permissionName);
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_refuse) + "\n" + permissionName);
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_noAsk) + "\n" + permissionName);
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE);
        init();

    }

    /**
     * 初始化各个组件
     */
    private void init() {

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setOffscreenPageLimit(3);
        mViewPger.setAdapter(mainAdapter);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator.setViewPager(mViewPger);

    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        Fragment fragment1 = new Fragment1();
        Fragment fragment2 = new Fragment2();
        Fragment fragment3 = new Fragment3();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(fragment1);
            fragments.add(fragment2);
            fragments.add(fragment3);
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
            if (0 == position) {
                StatusBarUtils.setStatusBar(MainActivity.this, getResources().getColor(R.color.theme));
            } else if (1 == position) {
                StatusBarUtils.setStatusBar(MainActivity.this, getResources().getColor(R.color.theme));
            } else if (2 == position) {
                StatusBarUtils.setStatusBar(MainActivity.this, getResources().getColor(R.color.theme));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public void onBackPressed() {
        backtrack();
    }

    /**
     * 退出销毁所有activity
     */
    private void backtrack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort(context, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
            exitTime = 0;
        }
    }

}
