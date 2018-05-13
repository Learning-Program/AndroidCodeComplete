package code.complete.common.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;

import org.joor.Reflect;
import org.joor.ReflectException;

import code.complete.log.Logs;

/**
 * APP常用工具类
 * @author imkarl
 */
public final class AppUtils {
    private AppUtils() {
    }

    private static Application sApplication;
    private static boolean isDebug;

    static {
        // 适用于 android 4.0 以后
        Application application = null;
        try {
            application = Reflect.on("android.app.AppGlobals").call("getInitialApplication").get();
        } catch (ReflectException e) {
            Logs.e(e);
        }
        if (application == null) {
            try {
                application = Reflect.on("android.app.ActivityThread").call("currentApplication").get();
            } catch (ReflectException e) {
                Logs.e(e);
            }
        }
        sApplication = application;

        isDebug = sApplication != null
                && sApplication.getApplicationInfo() != null
                && (sApplication.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 确保Application不为空
     * 如果某些机型上 {@link #getApplication()} 可能会返回null，请在你自定义Application的onCreate()最前面调用该方法
     */
    public static void ensure(@NonNull Application application, boolean isDebug) {
        //noinspection ConstantConditions
        if (application != null) {
            sApplication = application;
        }
        AppUtils.isDebug = isDebug;
    }

    @TargetApi(14)
    public static Application getApplication() {
        return sApplication;
    }

    public static boolean isDebug() {
        return isDebug;
    }

}
