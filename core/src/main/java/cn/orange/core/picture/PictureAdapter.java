package cn.orange.core.picture;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 * 图片展示Adapter(添加新图片)
 */
class PictureAdapter extends GalleryAdapter {

    PictureAdapter(@NonNull LayoutInflater inflater,
                   @Nullable PictureContract.View view) {
        super(inflater, view);
    }

    @Override
    protected int getFooterCount() {
        return 1;
    }

    protected boolean showDelete() {
        return true;
    }
}
