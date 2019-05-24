package cn.orange.core.picture;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.orange.core.ActionBarActivity;
import cn.orange.core.R;
import cn.orange.core.option.DefaultOptionCallBack;
import cn.orange.core.option.OptionDialog;
import cn.orange.core.option.OptionItem;
import cn.orange.core.ui.PictureDialog;
import cn.orange.core.util.IDUtil;
import cn.orange.core.util.IOUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Orange on 18-12-13.
 * Email:addskya@163.com
 * <p>
 * 需要展示图片的Activity
 * 注意:目前只带有一个图片列表,如果需要多个,需要自己实现 {@link PictureContract.View}
 */
public abstract class PictureActivity extends ActionBarActivity
        implements PictureContract.View {
    private static final String TAG = "PictureActivity";
    private static final int REQUEST_CODE_PICTURE_ALBUM = 0x62;
    private static final int REQUEST_CODE_PICTURE_CAMERA = 0x63;

    private static final int IDENTIFIER_ALBUM = 0;
    private static final int IDENTIFIER_CAMERA = 1;

    protected GalleryAdapter mPictureAdapter;
    private Uri mExtraOutputUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        mPictureAdapter = new PictureAdapter(inflater, this);
    }

    @Override
    public final void pickPicture() {
        Log.i(TAG, "pickPicture");
        new OptionDialog(this, new DefaultOptionCallBack() {
            @Override
            public void onCallBack(OptionItem item) {
                switch (item.getIdentifier()) {
                    case IDENTIFIER_ALBUM: {
                        String[] permission = {
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        };
                        requestPermissions(permission, () -> {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_CODE_PICTURE_ALBUM);
                            }
                        });
                        break;
                    }
                    case IDENTIFIER_CAMERA: {
                        String[] permissions = {
                                Manifest.permission.CAMERA
                        };

                        requestPermissions(permissions, () -> {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(getFilesDir(), IDUtil.obtainID() + ".jpg");
                            Uri uri = FileProvider.getUriForFile(PictureActivity.this,
                                    getPackageName(), file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            mExtraOutputUri = uri;
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_CODE_PICTURE_CAMERA);
                            }
                        });
                        break;
                    }
                }
            }
        }) {
            @Override
            protected List<OptionItem> getOptionItems() {
                return Arrays.asList(
                        new OptionItem(IDENTIFIER_ALBUM, R.string.text_album),
                        new OptionItem(IDENTIFIER_CAMERA, R.string.text_camera));
            }
        }.show();
    }

    @Override
    public void viewPicture(@Nullable String uri) {
        Log.i(TAG, "viewPicture:" + uri);
        if (TextUtils.isEmpty(uri)) {
            return;
        }
        PictureDialog.intentTo(this, uri, mPictureAdapter.getAll());
    }

    @Override
    public void deletePicture(@Nullable String uri) {
        mPictureAdapter.remove(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICTURE_ALBUM: {
                if (resultCode == RESULT_OK && data != null) {
                    mPictureAdapter.add(data.getDataString());
                }
                break;
            }
            case REQUEST_CODE_PICTURE_CAMERA: {
                if (resultCode == RESULT_OK && mExtraOutputUri != null) {
                    mPictureAdapter.add(mExtraOutputUri.toString());
                }
                mExtraOutputUri = null;
                break;
            }
            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 图片转码
     *
     * @param callback 转码完成后的动作
     */
    protected void convertPicture(@NonNull final CompressCallback<Uri> callback) {
        Observable.fromIterable(mPictureAdapter.getAll())
                .map(new Function<String, Uri>() {
                    @Override
                    public Uri apply(String picturePath) {
                        if (TextUtils.isEmpty(picturePath)) {
                            return null;
                        }
                        return isPictureNeedCopyToApp(picturePath) ?
                                copyPictureAsBitmap(picturePath) : Uri.parse(picturePath);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.observers.DefaultObserver<Uri>() {
                    List<Uri> localPath = new ArrayList<>();

                    @Override
                    public void onNext(Uri path) {
                        if (path != null) {
                            localPath.add(path);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onCallback(localPath);
                    }

                    @Override
                    public void onComplete() {
                        callback.onCallback(localPath);
                    }
                });
    }

    /**
     * 判断图片文件是否需要Copy到App空间
     *
     * @param picturePath 图片文件路径
     * @return true, 如果图片文件地址不是APP私有
     */
    private boolean isPictureNeedCopyToApp(@NonNull String picturePath) {
        if (TextUtils.isEmpty(picturePath)) {
            return false;
        }

        // content://cn.orange.core/files/8157504f-1c1d-4c8e-b075-fd08cdca4b38.jpg
        // content://media/external/images/media/2570
        final String pathPrefix = "content://" + getPackageName();
        return !picturePath.startsWith(pathPrefix);
    }

    /**
     * 以文件方式复制系统图片到APP指定空间
     * 需要解决一个难题: 原始图片扩展名
     *
     * @param originPictureUri 原始图片Uri
     * @return 复制到新地址后的文件地址Uri
     */
    @SuppressWarnings("unused")
    private Uri copyPictureAsBinary(@NonNull String originPictureUri) {
        Uri originFileUri = Uri.parse(originPictureUri);

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = getContentResolver().openInputStream(originFileUri);
            if (is == null) {
                return null;
            }
            bis = new BufferedInputStream(is);

            File destFile = new File(getPictureFileStoreRoot(), IDUtil.obtainID() + ".jpg");
            if (destFile.exists() && !destFile.delete()) {
                return null;
            }

            if (!destFile.createNewFile()) {
                return null;
            }

            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            IOUtil.copy(bis, bos);
            return Uri.fromFile(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtil.closeQuietly(bis);
            IOUtil.closeQuietly(bos);
        }
    }

    /**
     * 以图片方式复制图片到外部App存储空间
     *
     * @param originPicturePath 原始图片Uri
     * @return 复制到新地址后的文件地址Uri
     */
    private Uri copyPictureAsBitmap(@NonNull String originPicturePath) {
        try {
            Uri originFileUri = Uri.parse(originPicturePath);
            File destFile = new File(getPictureFileStoreRoot(), IDUtil.obtainID() + ".jpg");
            InputStream is = getContentResolver().openInputStream(originFileUri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            FileOutputStream os = new FileOutputStream(destFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(os);
            return Uri.fromFile(destFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private File getPictureFileStoreRoot() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExtraOutputUri = null;
    }
}
