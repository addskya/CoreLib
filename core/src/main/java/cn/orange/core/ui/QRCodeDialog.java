package cn.orange.core.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import java.io.File;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;
import cn.orange.core.util.BarCodeUtil;

/**
 * Created by Orange on 18-11-30.
 * Email:addskya@163.com
 * 展会二维码对话框
 * QRCodeDialog.intentTo(getContext(), "123456789,123456");
 */
public class QRCodeDialog extends BaseDialog {
    private static final String TAG = "BarCodeDialog";
    private static final String BAR_CODE_FILE = "BarCode.jpg";
    private CharSequence mBarCodeText;

    private QRCodeDialog(@NonNull Context context,
                          @NonNull CharSequence text) {
        super(context);
        mBarCodeText = text;
    }

    /**
     * 展会二维码对话框
     *
     * @param context the app context
     * @param text    the bar code content
     */
    public static void intentTo(@NonNull Context context,
                                @NonNull CharSequence text) {
        BaseDialog dialog = new QRCodeDialog(context, text);
        dialog.show();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_bar_code, parent, false);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);
        SimpleImageView imageView = view.findViewById(R.id.barcode);
        imageView.setImageURI(createBarcodeFile(mBarCodeText));
    }

    private Uri createBarcodeFile(@NonNull CharSequence text) {
        final File cacheBarcodeFile = getCacheFile(getContext());
        final Uri uri = Uri.fromFile(cacheBarcodeFile);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromCache(uri);
        BarCodeUtil.createQRImage(text, 600, 600, cacheBarcodeFile);
        return uri;
    }

    private File getCacheFile(@NonNull Context context) {
        File fileRoot = context.getCacheDir();
        return new File(fileRoot, BAR_CODE_FILE);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 删除二维码生成的临时文件
        File cache = getCacheFile(getContext());
        Log.i(TAG, "Drop cache file:" + cache);
        if (!cache.delete()) {
            cache.deleteOnExit();
        }
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getWindowAnimation() {
        return 0;
    }
}
