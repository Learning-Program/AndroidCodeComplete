package code.complete.common.utils;

import android.support.annotation.Nullable;

import java.util.Iterator;

/**
 * 字符串相关工具类
 * @author imkarl
 */
public final class StringUtils {
    private StringUtils() {
    }

    public static final String EMPTY = "";


    public static String join(@Nullable final long[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final int[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final short[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final byte[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final char[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final float[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final double[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final Object[] array, @Nullable final String separator) {
        if (array == null) {
            return null;
        }

        final int len = array.length;
        if (len <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(len * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && separator != null) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String join(@Nullable final Iterator<?> iterator, @Nullable final String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ((first != null) ? first.toString() : "");
        }

        // two or more elements
        // Java default is 16, probably too small
        final StringBuilder buf = new StringBuilder(128);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String join(@Nullable final Iterable<?> iterable, @Nullable final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

}
