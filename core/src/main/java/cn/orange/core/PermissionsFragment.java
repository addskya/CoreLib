package cn.orange.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public abstract class PermissionsFragment extends Fragment {

    /**
     * request system Permissions
     *
     * @param permissions     the permissions array
     * @param pendingRunnable the pending runnable if GRANTED
     */
    protected final void requestPermissions(@NonNull String[] permissions,
                                            @Nullable Runnable pendingRunnable) {
        getPermissionsTransfer().requestPermissions(permissions, pendingRunnable);
    }

    private PermissionsTransfer getPermissionsTransfer() {
        Activity host = getActivity();
        if (host instanceof PermissionsTransfer) {
            return ((PermissionsTransfer) host);
        }
        throw new IllegalArgumentException("The Host Activity is NOT Implement PermissionsTransfer");
    }
}
