package code.complete.common.utils;

import android.os.Handler;
import android.os.Looper;

import code.complete.common.app.ToastUtils;

/**
 * 线程相关工具类
 * @author imkarl
 */
public final class ThreadUtils {
    private static Handler sUiHandler;

    /**
     * 判断当前线程是否为UI线程
     */
    public static boolean isUiThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 获取UI线程的Handler
     */
    public static Handler getUiHandler() {
        if (sUiHandler == null) {
            synchronized (ToastUtils.class) {
                if (sUiHandler == null) {
                    sUiHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sUiHandler;
    }

    /**
     * 在UI线程执行
     */
    public static void runOnUiThread(Runnable runnable) {
        getUiHandler().post(runnable);
    }

}
