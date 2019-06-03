package cn.orange.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import cn.orange.core.R;


/**
 * Created by Orange on 2019/5/15
 * Email:addskya@163.com
 * Fresco的图片展示View
 */
public class SimpleImageView extends SimpleDraweeView {
    private static final String TAG = "SimpleImageView";

    public SimpleImageView(@NonNull Context context) {
        this(context, null);
    }

    public SimpleImageView(@NonNull Context context,
                           @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageView(@NonNull Context context,
                           @Nullable AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
        if (ta.hasValue(R.styleable.SimpleImageView_src)) {
            setSrc(ta.getResourceId(R.styleable.SimpleImageView_src, 0));
        }
        ta.recycle();
    }

    public final void setSrc(@DrawableRes int srcResId) {
        if (srcResId == 0) {
            return;
        }
        setController(buildController(getUri(srcResId), null));
    }

    public void setImageURI(@Nullable Uri uri,
                            @Nullable ControllerListener<ImageInfo> listener) {
        setController(buildController(uri, listener));
    }

    private Uri getUri(@DrawableRes int srcResId) {
        return Uri.parse("res:///" + srcResId);
    }

    private DraweeController buildController(@Nullable Uri uri,
                                             @Nullable ControllerListener<ImageInfo> listener) {
        return Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .setControllerListener(listener)
                .build();
    }
}
