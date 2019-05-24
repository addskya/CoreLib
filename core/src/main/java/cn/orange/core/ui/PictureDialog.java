package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;
import cn.orange.core.databinding.ItemSlideBinding;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 * <p>
 * 显示图片预览的对话框
 */
public class PictureDialog extends BaseDialog {
    @SuppressWarnings("unused")
    private static final String TAG = "PictureDialog";
    private String mNow;
    private List<String> mPictures;

    /**
     * 展示图片框
     *
     * @param context Activity context
     * @param now     当前显示的图片地址
     * @param picture 所有图片地址列表
     */
    private PictureDialog(@NonNull Context context,
                          @NonNull String now,
                          @NonNull List<String> picture) {
        super(context, R.style.AppFullDialog);
        mNow = now;
        mPictures = picture;
    }

    /**
     * 显示图片预览的对话框
     *
     * @param context the app context
     * @param now     当前查看的图片地址
     * @param picture the picture list
     */
    public static void intentTo(@NonNull Context context,
                                @NonNull String now,
                                @NonNull List<String> picture) {
        BaseDialog dialog = new PictureDialog(context, now, picture);
        dialog.show();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_picture, parent, false);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);

        PictureAdapter adapter = new PictureAdapter(mPictures);

        ViewPager pager = view.findViewById(R.id.picture);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.indexTo(mNow));
        // other
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }

    private class PictureAdapter extends PagerAdapter {

        private final List<String> mPictures = new ArrayList<>();
        private LayoutInflater mInflater;

        private PictureAdapter(@NonNull List<String> list) {
            mPictures.addAll(list);
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mPictures.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ItemSlideBinding binding = ItemSlideBinding.inflate(
                    mInflater, container, false);
            binding.setUri(mPictures.get(position));
            binding.executePendingBindings();

            View view = binding.getRoot();

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        private int indexTo(@NonNull String uri) {
            return mPictures.indexOf(uri);
        }
    }
}
