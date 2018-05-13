package code.complete.common.utils;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * Collection相关工具类
 * @author imkarl
 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <E> boolean isEmpty(@Nullable Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

}
