package cn.orange.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.orange.core.util.LogUtil;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * 顶部选项式Activity
 */
public abstract class TabbedActivity extends ActionBarActivity {
    private static final String TAG = "TabbedActivity";

    private static final String KEY_POSITION
            = "cn.orange.core.EXTRA_POSITION";

    private RadioGroup mTabbed;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUi();
        setContentView(R.layout.activity_tabbed);

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return getFragments()[position];
            }

            @Override
            public int getCount() {
                return getFragments().length;
            }
        });

        mTabbed = findViewById(R.id.tabbed);
        // 删除已经存在的子View
        mTabbed.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int title : getFragmentTitles()) {
            RadioButton item = (RadioButton) inflater.inflate(R.layout.item_tabbed, mTabbed, false);
            item.setText(title);
            mTabbed.addView(item);
        }

        // 如果无选中项,默认选中第一个Tabbed
        if (mTabbed.getCheckedRadioButtonId() == -1) {
            mTabbed.check(mTabbed.getChildAt(getValidCheckedPosition()).getId());
        }

        // 点击选中切换页事件
        mTabbed.setOnCheckedChangeListener((parent, checkedId) -> {
            final int childCount = mTabbed.getChildCount();
            for (int index = 0; index < childCount; index++) {
                if (checkedId == mTabbed.getChildAt(index).getId()) {
                    mViewPager.setCurrentItem(index, true);
                    break;
                }
            }
        });

        // 左右滑动事件
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabbed.check(mTabbed.getChildAt(position).getId());
            }
        });

        // 恢复之前退出时的页面状态
        restoreState(savedInstanceState);
    }

    private void checkUi() {
        if (getFragments().length != getFragmentTitles().length) {
            throw new IllegalArgumentException("The Child Fragments count MUST Equals Titles count");
        }
    }

    /**
     * 返回此TabbedActivity下所有的子Fragment数组
     *
     * @return 子Fragment数组
     */
    @NonNull
    protected abstract Fragment[] getFragments();

    /**
     * 返回此TabbedActivity下所有的子Fragment标题
     *
     * @return 子Fragment标题数组
     */
    @NonNull
    protected abstract int[] getFragmentTitles();

    /**
     * 返回默认选中的Fragment 位置,默认选中第一个
     *
     * @return 默认选中的Fragment 位置
     */
    protected int getCheckedPosition() {
        return 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_POSITION, mViewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreState(savedInstanceState);
    }

    private void restoreState(@Nullable Bundle inState) {
        LogUtil.i(TAG, "restoreState");
        if (inState == null) {
            return;
        }
        int position = inState.getInt(KEY_POSITION, getValidCheckedPosition());
        mViewPager.setCurrentItem(position);
    }

    /**
     * 返回有效的默认选中Fragment位置,避免 {@link #getCheckedPosition()} 返回无效的值
     *
     * @return 有效的默认选中Fragment位置
     */
    private int getValidCheckedPosition() {
        return Math.max(0, Math.min(getCheckedPosition(), getFragmentTitles().length - 1));
    }
}
