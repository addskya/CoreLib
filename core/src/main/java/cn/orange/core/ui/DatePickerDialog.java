package cn.orange.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import cn.orange.core.BaseDialog;
import cn.orange.core.R;
import cn.orange.core.util.LogUtil;

/**
 * Created by Orange on 18-11-28.
 * Email:addskya@163.com
 * <p>
 * 时间选取对话框
 */
public class DatePickerDialog extends BaseDialog {

    private static final String TAG = "DatePickerDialog";
    private CallBack<Long> mCallback;

    private DatePickerDialog(@NonNull Context context,
                             @Nullable CallBack<Long> callBack) {
        super(context);
        mCallback = callBack;
    }

    /**
     * 日期选择对话框
     *
     * @param context  application context
     * @param callBack 用户动作回调
     */
    public static void intentTo(@NonNull Context context,
                                @Nullable CallBack<Long> callBack) {
        BaseDialog dialog = new DatePickerDialog(context, callBack);
        dialog.show();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater,
                                @Nullable ViewGroup parent) {
        return inflater.inflate(R.layout.dialog_date_picker, parent, false);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);

        final MaterialCalendarView calendarView = view.findViewById(R.id.calendar_view);
        if (calendarView == null) {
            throw new IllegalStateException("You must contain a MaterialCalendarView in dialog.");
        }

        calendarView.setSelectedDate(Calendar.getInstance());
        calendarView.setOnDateChangedListener((widget, date, selected) ->
                LogUtil.i(TAG, "onDateSelected:" + date)
        );

        View cancelView = view.findViewById(R.id.cancel);
        cancelView.setOnClickListener((v) -> dismiss());

        View okView = view.findViewById(R.id.ok);
        okView.setOnClickListener((v) -> {
            dismiss();
            long timeInMillis = calendarView.getSelectedDate().getCalendar().getTimeInMillis();
            if (mCallback != null) {
                mCallback.onCallback(timeInMillis);
            }
        });
    }

    @Override
    protected int getWindowGravity() {
        return Gravity.BOTTOM;
    }
}
