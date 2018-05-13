package code.complete.common.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * 显示器相关工具类
 * @author imkarl
 */
public final class DisplayUtils {

    private DisplayUtils() {
    }

    private static final DisplayMetrics DISPLAY_METRICS;

    static {
        DISPLAY_METRICS = new DisplayMetrics();
        Display display = ((WindowManager) AppUtils.getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(DISPLAY_METRICS);
    }

    @NonNull
    public static DisplayMetrics getDisplayMetrics() {
        return DISPLAY_METRICS;
    }

    public static int getWidthPixels() {
        return DISPLAY_METRICS.widthPixels;
    }
    public static int getWidthDp() {
        return px2dp(DISPLAY_METRICS.widthPixels);
    }
    public static int getHeightPixels() {
        return DISPLAY_METRICS.heightPixels;
    }
    public static int getHeightDp() {
        return px2dp(DISPLAY_METRICS.heightPixels);
    }

    public static float getDensity() {
        return DISPLAY_METRICS.density;
    }
    public static float getScaledDensity() {
        return DISPLAY_METRICS.scaledDensity;
    }

    /**
     * dp转px
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(final float dpValue) {
        final float scale = DISPLAY_METRICS.density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(final float pxValue) {
        final float scale = DISPLAY_METRICS.density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = DISPLAY_METRICS.scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = DISPLAY_METRICS.scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 单位转换
     * @param unit  单位
     * @param value 值
     * @return px值
     */
    public static float applyDimension(final int unit, final float value) {
        return (int) TypedValue.applyDimension(unit, value, DISPLAY_METRICS);
    }

}
