package cn.orange.core;

import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import cn.orange.core.util.LogUtil;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * <p>
 * 底部导航抽象界面,类似微信,QQ,支付宝这类,当前默认不显示标题
 */
public abstract class BottomNavigationActivity extends BaseActivity {
    private static final String TAG = "BottomNavigationActivity";
    private static final String KEY_POSITION
            = "cn.orange.core.KEY_POSITION";

    private FragmentManager mFragmentManager;
    private Fragment[] mFragments;
    private int mPosition;
    private BottomNavigationView mNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (menuItem -> {
        Menu menu = mNavigationView.getMenu();
        int tabCount = menu.size();
        for (int index = 0; index < tabCount; index++) {
            if (menuItem == menu.getItem(index)) {
                LogUtil.i(TAG, "onNavigationItemSelected:" + index + "/" + tabCount);
                showFragment(index);
                return true;
            }
        }
        return false;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        mFragmentManager = getSupportFragmentManager();

        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.inflateMenu(getNavigationMenu());
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragments = getNavigationFragments();

        FragmentManager fragmentManager = mFragmentManager;
        for (Fragment fragment : mFragments) {
            String tag = fragment.toString();
            // 如果找到指定的Fragment,表明已经添加
            if (fragmentManager.findFragmentByTag(tag) != null) {
                continue;
            }
            fragmentManager.beginTransaction()
                    .add(R.id.content, fragment, tag)
                    .commitAllowingStateLoss();
        }

        restoreInstanceState(savedInstanceState);
    }

    /**
     * 模拟用户点击Tab项,以实现Tab切换.
     *
     * @param position tab index
     */
    private void checkMenuItem(int position) {
        Menu menu = mNavigationView.getMenu();
        final int tabCount = menu.size();
        position = Math.max(0, Math.min(position, tabCount - 1));
        menu.getItem(position).setChecked(true);
    }

    /**
     * 显示指定位置的Fragment
     *
     * @param position Fragment位置
     */
    private void showFragment(int position) {
        LogUtil.i(TAG, "showFragment,position:" + position);
        Fragment[] allFragment = mFragments;
        FragmentManager manager = mFragmentManager;

        FragmentTransaction transaction = manager.beginTransaction();

        final int size = allFragment.length;
        for (int index = 0; index < size; index++) {
            Fragment fragment = allFragment[index];
            boolean visible = position == index;
            if (visible) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }
        transaction.commitAllowingStateLoss();
        mPosition = position;
    }

    /**
     * 返回底部导航条菜单项
     *
     * @return 底部导航条菜单项
     */
    @MenuRes
    protected abstract int getNavigationMenu();

    /**
     * 返回所有子Fragment数组
     *
     * @return 子Fragment数组
     */
    @NonNull
    protected abstract Fragment[] getNavigationFragments();

    /**
     * 返回默认显示的Fragment位置
     *
     * @return 默认显示的Fragment位置
     */
    protected int getDefaultSelectedPosition() {
        return 0;
    }

    private int getValidSelectedPosition() {
        return Math.max(0, Math.min(getDefaultSelectedPosition(), mFragments.length - 1));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreInstanceState(savedInstanceState);
    }

    private void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        int defaultSelectedPosition = getValidSelectedPosition();
        mPosition = savedInstanceState != null ? savedInstanceState.getInt(
                KEY_POSITION, defaultSelectedPosition) : defaultSelectedPosition;
        checkMenuItem(mPosition);
    }
}
