package code.complete.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import code.complete.common.app.AppUtils;

/**
 * 键盘相关工具类
 * @author imkarl
 */
@SuppressWarnings("ConstantConditions")
public final class KeyboardUtils {
    private KeyboardUtils() {
    }


    public static boolean show(@NonNull Activity activity) {
        if (activity == null) {
            return false;
        }
        View focusView = activity.getCurrentFocus();
        return show(focusView);
    }

    public static boolean show(@NonNull View view) {
        if (view == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return false;
        }
        view.requestFocus();
        return imm.showSoftInput(view, 0);
    }


    public static boolean hide(@NonNull Activity activity) {
        if (activity == null) {
            return false;
        }
        View focusView = activity.getCurrentFocus();
        return hide(focusView);
    }

    public static boolean hide(@NonNull View view) {
        if (view == null) {
            return false;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void toggle() {
        InputMethodManager imm = (InputMethodManager) AppUtils.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(0, 0);
    }

}
