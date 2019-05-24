package cn.orange.core.pick;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import cn.orange.core.PermissionsActivity;
import cn.orange.core.R;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 * <p>
 * 二维码扫描界面
 */
public class ScannerActivity extends PermissionsActivity {
    private static final String TAG = "ScannerActivity";
    private static final String EXTRA_RESULT
            = "cn.orange.identity.EXTRA_RESULT";

    private CaptureManager mCaptureManager;
    private DecoratedBarcodeView mBarcodeView;

    public static void intentTo(@NonNull Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), ScannerActivity.class);
        intent.putExtra(Intents.Scan.BEEP_ENABLED, true);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void intentTo(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ScannerActivity.class);
        intent.putExtra(Intents.Scan.BEEP_ENABLED, true);
        activity.startActivityForResult(intent, requestCode);
    }

    @Nullable
    public static String parseResult(@Nullable Intent intent) {
        return intent != null ? intent.getStringExtra(EXTRA_RESULT) : null;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_scanner);
        mBarcodeView = findViewById(R.id.barcode_view);

        final CheckBox flashLight = findViewById(R.id.flashlight);
        flashLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBarcodeView.setTorchOn();
            } else {
                mBarcodeView.setTorchOff();
            }

        });

        mBarcodeView.setTorchListener(new DecoratedBarcodeView.TorchListener() {
            @Override
            public void onTorchOn() {
                flashLight.setChecked(true);
            }

            @Override
            public void onTorchOff() {
                flashLight.setChecked(false);
            }
        });

        //重要代码，初始化捕获
        mCaptureManager = new CaptureManager(ScannerActivity.this, mBarcodeView);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.setResultCallBack(this::handleScanResult);

        String[] permissions = {
                Manifest.permission.CAMERA,
        };
        requestPermissions(permissions, () -> mCaptureManager.decode());
    }


    @Override
    protected void onRequestPermissionsFailure(String[] requestFailurePermissions) {
        super.onRequestPermissionsFailure(requestFailurePermissions);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    private void handleScanResult(@Nullable String text) {
        Log.i(TAG, "handleScanResult:" + text);
        Intent result = new Intent();
        result.putExtra(EXTRA_RESULT, text);
        setResult(RESULT_OK, result);
        finish();

    }

    /**
     * Manages barcode scanning for a CaptureActivity. This class may be used to have a custom Activity
     * (e.g. with a customized look and feel, or a different superclass), but not the barcode scanning
     * process itself.
     * <p>
     * This is intended for an Activity that is dedicated to capturing a single barcode and returning
     * it via setResult(). For other use cases, use DefaultBarcodeScannerView or BarcodeView directly.
     * <p>
     * The following is managed by this class:
     * - Orientation lock
     * - InactivityTimer
     * - BeepManager
     * - Initializing from an Intent (via IntentIntegrator)
     * - Setting the result and finishing the Activity when a barcode is scanned
     * - Displaying camera errors
     */
    static class CaptureManager {
        private static final String TAG = "CaptureManager";
        private static final String SCAN_COUNT = "cn.orange.identity.EXTRA_SCAN_COUNT";

        private Activity activity;
        private DecoratedBarcodeView barcodeView;
        private boolean destroyed = false;
        private InactivityTimer inactivityTimer;
        private BeepManager beepManager;
        private Handler handler;
        private boolean finishWhenClosed = false;
        private final CameraPreview.StateListener stateListener = new CameraPreview.StateListener() {
            @Override
            public void previewSized() {

            }

            @Override
            public void previewStarted() {

            }

            @Override
            public void previewStopped() {

            }

            @Override
            public void cameraError(Exception error) {
                displayFrameworkBugMessageAndExit();
            }

            @Override
            public void cameraClosed() {
                if (finishWhenClosed) {
                    Log.d(TAG, "Camera closed; finishing activity");
                    finish();
                }
            }
        };
        private int mScanCount;
        private ResultCallBack mResultCallBack;
        private BarcodeCallback callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(final BarcodeResult result) {
                barcodeView.pause();
                beepManager.playBeepSoundAndVibrate();

                handler.post(() -> returnResult(result));

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        };

        CaptureManager(Activity activity, DecoratedBarcodeView barcodeView) {
            this.activity = activity;
            this.barcodeView = barcodeView;
            barcodeView.getBarcodeView().addStateListener(stateListener);

            handler = new Handler();

            inactivityTimer = new InactivityTimer(activity, () -> {
                Log.d(TAG, "Finishing due to inactivity");
                finish();
            });

            beepManager = new BeepManager(activity);
            beepManager.setVibrateEnabled(true);
            beepManager.setBeepEnabled(true);
        }

        /**
         * Perform initialization, according to preferences set in the intent.
         *
         * @param intent             the intent containing the scanning preferences
         * @param savedInstanceState saved state, containing orientation lock
         */
        void initializeFromIntent(Intent intent, Bundle savedInstanceState) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            if (intent != null) {

                if (Intents.Scan.ACTION.equals(intent.getAction())) {
                    barcodeView.initializeFromIntent(intent);
                }

                if (!intent.getBooleanExtra(Intents.Scan.BEEP_ENABLED, true)) {
                    beepManager.setBeepEnabled(false);
                }

                if (intent.hasExtra(SCAN_COUNT)) {
                    mScanCount = intent.getIntExtra(SCAN_COUNT, 1);
                }
            }
        }

        /**
         * Start decoding.
         */
        void decode() {
            if (mScanCount <= 1) {
                barcodeView.decodeSingle(callback);
            } else {
                barcodeView.decodeContinuous(callback);
            }
        }

        /**
         * Call from Activity#onResume().
         */
        void onResume() {
            barcodeView.resume();
            inactivityTimer.start();
        }

        /**
         * Call from Activity#onPause().
         */
        void onPause() {
            inactivityTimer.cancel();
            barcodeView.pauseAndWait();
        }

        /**
         * Call from Activity#onDestroy().
         */
        void onDestroy() {
            destroyed = true;
            inactivityTimer.cancel();
            handler.removeCallbacksAndMessages(null);
        }

        private void finish() {
            activity.finish();
        }

        void closeAndFinish(String text) {
            if (barcodeView.getBarcodeView().isCameraClosed()) {
                if (null != mResultCallBack) {
                    mResultCallBack.callBack(text);
                }
            } else {
                finishWhenClosed = true;
            }

            barcodeView.pause();
            inactivityTimer.cancel();
        }

        void returnResult(BarcodeResult rawResult) {
            if (mScanCount <= 1) {
                closeAndFinish(rawResult.getText());
            } else {
                returnAndContinuous(rawResult.getText());
            }
        }

        void returnAndContinuous(String text) {
            if (null != mResultCallBack) {
                mResultCallBack.callBack(text);
            }
            barcodeView.pause();
            inactivityTimer.cancel();
            inactivityTimer.start();
            barcodeView.resume();
        }

        void displayFrameworkBugMessageAndExit() {
            if (activity.isFinishing() || this.destroyed || finishWhenClosed) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getString(R.string.zxing_app_name));
            builder.setMessage(activity.getString(R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(R.string.zxing_button_ok, (dialog, which) -> finish());
            builder.setOnCancelListener((dialog -> finish()));
            builder.show();
        }

        private void setResultCallBack(ResultCallBack resultCallBack) {
            this.mResultCallBack = resultCallBack;
        }

        public interface ResultCallBack {
            void callBack(String text);
        }

    }
}
