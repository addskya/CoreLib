package cn.orange.core.picture;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

/**
 * Created by Orange on 18-12-16.
 * Email:addskya@163.com
 * <p>
 * 图片展示Adapter(无添加新图片功能)
 */
public class SlideAdapter extends GalleryAdapter {

    public SlideAdapter(@NonNull LayoutInflater inflater,
                        @Nullable PictureContract.View view) {
        super(inflater, view);
    }

    @Override
    protected int getFooterCount() {
        return 0;
    }

    protected boolean showDelete() {
        return false;
    }
}
