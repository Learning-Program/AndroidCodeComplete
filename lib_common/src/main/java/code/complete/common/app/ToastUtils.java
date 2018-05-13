package code.complete.common.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import code.complete.common.utils.ThreadUtils;
import me.drakeet.support.toast.ToastCompat;

/**
 * Toast相关工具类
 * @author imkarl
 */
public final class ToastUtils {
    private ToastUtils() {
    }

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}


    @UiThread
    public static void show(@StringRes int resId) {
        show(ResourcesUtils.getText(resId));
    }
    @UiThread
    public static void show(CharSequence message) {
        show(message, LENGTH_SHORT);
    }

    @UiThread
    public static void show(@StringRes int resId, @Duration int duration) {
        show(ResourcesUtils.getText(resId), duration);
    }
    @UiThread
    public static void show(CharSequence message, @Duration int duration) {
        if (ThreadUtils.isUiThread()) {
            makeText(message, duration).show();
        } else {
            ThreadUtils.runOnUiThread(() -> show(message, duration));
        }
    }

    @UiThread
    @CheckResult(suggest="show()")
    public static Toast makeText(@StringRes int resId, @Duration int duration) {
        return makeText(ResourcesUtils.getText(resId), duration);
    }
    @UiThread
    @SuppressLint("ShowToast")
    @CheckResult(suggest="show()")
    public static Toast makeText(CharSequence text, @Duration int duration) {
        final Context context = AppUtils.getApplication();
        Toast toast;
        if (Build.VERSION.SDK_INT >= 25) {
            toast = ToastCompat.makeText(context, text, duration);
        } else {
            toast = Toast.makeText(context, text, duration);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }


}
