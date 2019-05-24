package cn.orange.core.ui;


import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import cn.orange.core.R;


/**
 * Created by Orange on 18-11-25.
 * Email:addskya@163.com
 * <p>
 * 验证码带倒时时Button
 */
public class VerifyCodeButton extends AppCompatButton {

    private CountDownTimer mCountDownTimer;

    public VerifyCodeButton(@NonNull Context context) {
        super(context);
    }

    public VerifyCodeButton(@NonNull Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerifyCodeButton(@NonNull Context context,
                            @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 验证码显示,开始倒计时
     *
     * @param second 从多少秒开始倒计时,单位(秒)
     */
    public void countDown(long second) {
        setEnabled(false);
        cancel();
        mCountDownTimer = new CountDownTimer(second * 1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                showCountDownTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                setText(R.string.resend);
                setEnabled(true);
            }
        }.start();
    }

    private void showCountDownTime(long millisUntilFinished) {
        long second = millisUntilFinished / 1000;
        setText(getResources().getString(R.string.format_verify_code, second));
    }

    /**
     * 取消倒计时
     */
    public void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }
}
