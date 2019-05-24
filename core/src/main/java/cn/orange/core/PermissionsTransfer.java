package cn.orange.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ChengHe.Zhang on 2019/4/1.
 * Email:chengheZhang@kaifa.cn
 * Android中权限请求处理类
 * 比如: 要请求相机扫描二维码,
 * 需要把扫描二维码的操作封装成Runnable,
 * 然后向所在的Activity发起申请即可
 */
public interface PermissionsTransfer {

    /**
     * request system Permissions
     *
     * @param permissions     the permissions array
     * @param pendingRunnable the pending runnable if GRANTED
     */
    void requestPermissions(@NonNull String[] permissions,
                            @Nullable Runnable pendingRunnable);
}
