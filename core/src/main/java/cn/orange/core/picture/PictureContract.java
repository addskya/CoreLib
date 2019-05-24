package cn.orange.core.picture;

import android.support.annotation.Nullable;

/**
 * Created by Orange on 18-12-4.
 * Email:addskya@163.com
 */
public interface PictureContract {

    interface View {

        void pickPicture();

        void viewPicture(@Nullable String uri);

        void deletePicture(@Nullable String uri);
    }
}
