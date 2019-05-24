package cn.orange.core;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class BaseActivity extends ProgressiveActivity {

    @SuppressWarnings("unused")
    private static final String TAG = "BaseActivity";


    /**
     * Whether or NOT the Activity has finished.
     *
     * @return true if the Activity is NOT finish
     */
    public final boolean isAdded() {
        return !isFinishing();
    }
}
