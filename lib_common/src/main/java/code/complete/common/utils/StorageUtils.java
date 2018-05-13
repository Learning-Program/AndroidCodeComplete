package code.complete.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 存储相关工具类
 * @author imkarl
 */
public final class StorageUtils {
    private StorageUtils() {
    }

    /**
     * 是否存在外置存储
     */
    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取外置存储的根路径
     */
    public static File getExternalStorageDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取外置存储的可用空间
     * @return 单位：kb
     */
    public static long getExternalStorageAvailableSize() {
        if (!hasExternalStorage()) {
            return 0;
        }

        final StatFs stat = new StatFs(getExternalStorageDir().getAbsolutePath());
        final long blockSize;
        final long availableBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

}
