package code.complete.common.app;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * 资源相关工具类
 * @author imkarl
 */
public class ResourcesUtils {
    private ResourcesUtils() {
    }

    private static final Resources RESOURCES = AppUtils.getApplication().getResources();


    public static CharSequence getText(@StringRes int resId) {
        return RESOURCES.getText(resId);
    }

    public static CharSequence getString(@StringRes int resId) {
        return RESOURCES.getString(resId);
    }
    public static CharSequence getString(@StringRes int resId, @NonNull Object... formatArgs) {
        return RESOURCES.getString(resId, formatArgs);
    }

    @Nullable
    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(AppUtils.getApplication(), id);
    }

    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int id) {
        return ContextCompat.getColorStateList(AppUtils.getApplication(), id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(AppUtils.getApplication(), id);
    }

}
