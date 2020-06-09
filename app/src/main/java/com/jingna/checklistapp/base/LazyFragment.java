package com.jingna.checklistapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author zwl
 * @describe 懒加载
 * @date on 2019-12-09
 */
public abstract class LazyFragment extends Fragment {

    private final String TAG = LazyFragment.class.getName();
    private View rootView;

    private boolean isViewCreated = false;//View是否创建
    private boolean currentVisibleState = false;//当前显示状态
    private boolean isFirstVisible = true;//是否第一次显示

    // fragment 生命周期：
    // onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume -> onPause -> onStop -> onDestroyView -> onDestroy -> onDetach
    // 对于 ViewPager + Fragment 的实现我们需要关注的几个生命周期有：
    // onCreatedView + onActivityCreated + onResume + onPause + onDestroyView

    /**
     * 打印日志
     *
     * @param content
     */
    public void LOG(String content) {
        if (!TextUtils.isEmpty(content)) {
            Log.d(TAG, content);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LOG("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LOG("onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }

        //初始化view
        initView(rootView);
        isViewCreated = true;


        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }

        return rootView;
    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View rootView);


    /**
     * 统一处理用户可见信息分发
     *
     * @param isVisible
     */
    public void dispatchUserVisibleHint(boolean isVisible) {
        LOG("dispatchUserVisibleHint  isVisible= " + isVisible);

        //事实上作为父 Fragment 并没有分发可见事件，他通过 getUserVisibleHint() 得到的结果为 false，首先我想到的
        //是能在负责分发事件的方法中判断一下当前父 fragment 是否可见，如果父 fragment 不可见我们就不进行可见事件的分发
        if (isVisible && isParentInvisible()) {
            return;
        }
        //状态没有改变 就不做处理
        if (currentVisibleState == isVisible) {
            return;
        }

        //设置当前状态
        currentVisibleState = isVisible;

        if (isVisible) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentVisible();
            //在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
            //此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
            //需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true);
        } else {
            onFragmentHide();
            dispatchChildVisibleState(false);
        }

    }

    /**
     * 统一处理子可见信息分发
     *
     * @param isVisible
     */
    private void dispatchChildVisibleState(boolean isVisible) {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof LazyFragment &&
                        !fragment.isHidden() &&
                        fragment.getUserVisibleHint()) {
                    ((LazyFragment) fragment).dispatchUserVisibleHint(isVisible);
                }
            }
        }
    }

    /**
     * fragment 隐藏
     */
    protected void onFragmentHide() {
        LOG("--------------------onFragmentHide-------------");
    }

    /**
     * fragment 显示
     */
    protected void onFragmentVisible() {
        LOG("--------------------onFragmentVisible-------------");
    }


    /**
     * fragment第一次显示
     */
    protected void onFragmentFirstVisible() {
        LOG("--------------------onFragmentFirstVisible-------------");
    }


    /**
     * 父Fragment是否显示
     *
     * @return
     */
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof LazyFragment) {
            return !((LazyFragment) parentFragment).currentVisibleState();
        }
        return false;
    }

    /**
     * 当前显示状态
     *
     * @return true  显示   false  隐藏
     */
    private boolean currentVisibleState() {
        return currentVisibleState;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LOG("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LOG("onStart");
    }


    /**
     * 修改fragment的可见性
     * setUserVisibleHint 被调用有两种情况：
     * 1）在切换tab的时候，会先于所有fragment的其他生命周期，先调用这个函数，可以看log
     * 2）对于之前已经调用过setUserVisibleHint 方法的fragment后，让fragment从可见到不可见之间状态的变化
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LOG("setUserVisibleHint   isVisibleToUser=" + isVisibleToUser);

        //对于情况1）不予处理，用 isViewCreated 进行判断，如果isViewCreated false，说明它没有被创建
        if (isViewCreated) {

            //对于情况2）要分情况考虑，如果是不可见->可见是下面的情况 2.1），如果是可见->不可见是下面的情况2.2），只有当可见状态进行改变的时候才需要切换，否则会出现反复调用的情况从而导致事件分发带来的多次更新

            //2.1）不可见（currentVisibleState=false）->可见（isVisibleToUser=true）
            if (isVisibleToUser && !currentVisibleState) {//2.1
                dispatchUserVisibleHint(true);
            }

            //2.2）可见（currentVisibleState=true）->不可见（isVisibleToUser=false）
            else if (!isVisibleToUser && currentVisibleState) {//2.2
                dispatchUserVisibleHint(false);
            }

        }
    }


    /**
     * 用FragmentTransaction来控制fragment的hide和show时，那么这个方法就会被调用。每当你对某个Fragment使用hide或者是show的时候，那么这个Fragment就会自动调用这个方法。
     * 注：你会发现使用hide和show这时fragment的生命周期不再执行，不走任何的生命周期，这样在有的情况下，数据将无法通过生命周期方法进行刷新，所以你可以使用onHiddenChanged方法来解决这问题。
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LOG("onHiddenChanged   hidden=" + hidden);
        //hidden false就是显示 ，true就是隐藏
        dispatchUserVisibleHint(!hidden);
    }


    @Override
    public void onResume() {
        super.onResume();
        LOG("onResume");

        //在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法，类似于在tab1 滑到tab2，此时tab3会缓存，这个时候会调用tab3 fragment的
        //onResume，所以，此时是不需要去调用 dispatchUserVisibleHint(true)的，因而出现了下面的if
        if (!isFirstVisible) {
            //由于Activit1 中如果有多个fragment，然后从Activity1 跳转到Activity2，此时会有多个fragment会在activity1缓存，此时，如果再从activity2跳转回
            //activity1，这个时候会将所有的缓存的fragment进行onResume生命周期的重复，这个时候我们无需对所有缓存的fragnment 调用dispatchUserVisibleHint(true)
            //我们只需要对可见的fragment进行加载，因此就有下面的if
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    /**
     * 只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint
     * currentVisibleState && getUserVisibleHint() 能够限定是当前可见的 Fragment
     * 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
     * 子 fragment 走到这里的时候自身又会调用一遍
     */
    @Override
    public void onPause() {
        super.onPause();
        LOG("onPause");
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LOG("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LOG("onDestroyView");
        isViewCreated = false;
        isFirstVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOG("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LOG("onDetach");
    }
}
