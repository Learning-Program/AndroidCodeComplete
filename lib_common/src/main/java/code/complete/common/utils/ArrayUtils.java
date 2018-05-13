package code.complete.common.utils;

import android.support.annotation.Nullable;

/**
 * Array相关工具类
 * @author imkarl
 */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static boolean isEmpty(@Nullable int[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable short[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable byte[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable long[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable float[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable double[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable boolean[] array) {
        return array == null || array.length == 0;
    }
    public static boolean isEmpty(@Nullable char[] array) {
        return array == null || array.length == 0;
    }
    public static <T> boolean isEmpty(@Nullable T[] array) {
        return array == null || array.length == 0;
    }

}
