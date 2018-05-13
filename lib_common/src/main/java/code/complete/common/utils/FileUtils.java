package code.complete.common.utils;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import code.complete.log.Logs;

/**
 * 文件相关工具类
 * @author imkarl
 */
public final class FileUtils {
    private FileUtils() {
    }


    /**
     * 判断文件是否存在
     * @return 如果file为null，或不存在，则返回false
     */
    public static boolean exists(@Nullable File file) {
        return file != null && file.exists();
    }


    /**
     * 创建文件夹（包括其父目录）
     * @return 如果文件夹已存在，则直接返回true
     */
    @SuppressWarnings("ConstantConditions")
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean mkdirs(@NonNull File dir) {
        if (dir == null) {
            return false;
        }
        if (exists(dir)) {
            return dir.isDirectory();
        }
        return dir.mkdirs() || dir.isDirectory();
    }

    /**
     * 创建文件
     * 如果父目录不存在，则新建；如果文件已存在，则删除重建
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean createNewFile(@NonNull File file) throws IOException {
        if (exists(file) && file.isFile()) {
            deleteFile(file);
        }

        if (!mkdirs(file.getParentFile())) {
            Logs.w("mkdirs 'NewFile' parent failed: "+file.getParentFile().getAbsolutePath());
        }
        return file.createNewFile();
    }


    /**
     * 删除文件或文件夹
     * @return 成功删除文件及文件夹，则返回true
     * @see #deleteFile(File)
     * @see #deleteDir(File)
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean deleteFileOrDir(@Nullable File file) throws IOException {
        if (!exists(file)) {
            return true;
        }
        return deleteFile(file) && deleteDir(file);
    }
    /**
     * 删除文件（不支持文件夹）
     * @return 不存在该文件，则返回true
     * @see #deleteDir(File)
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean deleteFile(@Nullable File file) {
        if (!exists(file) || !file.isFile()) {
            return true;
        }
        return file.delete() || !file.exists();
    }
    /**
     * 删除文件夹（不支持文件）
     * @return 不存在该文件夹，则返回true
     * @see #deleteFile(File)
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean deleteDir(@Nullable File dir) throws IOException {
        if (!exists(dir) || !dir.isDirectory()) {
            return true;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            final String reason = "deleteDir 'dir' not a readable directory: " + dir;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                throw new AccessDeniedException(dir.getAbsolutePath(), null, reason);
            } else {
                throw new IOException(reason);
            }
        } else {
            for (File file : files) {
                boolean delete;
                if (file.isDirectory()) {
                    delete = deleteDir(file);
                } else {
                    delete = deleteFile(file);
                }
                if (!delete) {
                    Logs.w("delete failed : " + (file.isDirectory() ? "dir " : "file ") + file);
                    return false;
                }
            }
        }

        return dir.delete() || !dir.exists();
    }


    /**
     * 重命名文件
     * @return 原文件名与目标文件名相同，则直接返回true
     */
    @SuppressWarnings("ConstantConditions")
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean rename(@NonNull File from, @NonNull File to) throws IOException {
        if (!exists(from)) {
            throw new FileNotFoundException("File not found, 'from' is: "+from);
        }
        if (to == null) {
            throw new NullPointerException();
        }
        if (TextUtils.equals(from.getAbsolutePath(), to.getAbsolutePath())) {
            return true;
        }
        // 如果目标文件已操作，则先删除
        deleteFile(to);
        // 重命名
        return from.renameTo(to);
    }

    /**
     * 移动文件
     * @return 原文件与目标文件位置相同，则直接返回true
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean moveFile(@NonNull File from, @NonNull File to) throws IOException {
        if (TextUtils.equals(from.getAbsolutePath(), to.getAbsolutePath())) {
            return true;
        }
        boolean status = copyFile(from, to);
        if (status) {
            deleteFile(from);
        }
        return status;
    }
    /**
     * 移动文件夹
     * @return 原文件夹与目标文件夹位置相同，则直接返回true
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean moveDir(@NonNull File from, @NonNull File to) throws IOException {
        if (TextUtils.equals(from.getAbsolutePath(), to.getAbsolutePath())) {
            return true;
        }
        boolean status = copyDir(from, to);
        if (status) {
            deleteDir(from);
        }
        return status;
    }


    /**
     * 复制文件或文件夹
     * @return 不存在源文件，则返回true
     * @see #copyFile(File, File)
     * @see #copyDir(File, File)
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean copyFileOrDir(@NonNull File source, @NonNull File target) throws IOException {
        if (!exists(source)) {
            return true;
        }

        if (source.isDirectory()) {
            return copyDir(source, target);
        } else if (source.isFile()) {
            return copyFile(source, target);
        } else {
            throw new UnsupportedOperationException("copyFileOrDir source is unkown file type: "+source.getPath());
        }
    }
    /**
     * 复制文件夹
     * @return 不存在源文件，则返回true
     * @see #copyFile(File, File)
     * @see #copyFileOrDir(File, File)
     */
    @SuppressWarnings("ConstantConditions")
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean copyDir(@NonNull File source, @NonNull File target) throws IOException {
        if (!exists(source)) {
            return true;
        }
        if (!source.isDirectory()) {
            throw new IllegalArgumentException("copyDir source is not a dir: "+source);
        }
        if (target == null) {
            throw new NullPointerException();
        }
        if (TextUtils.equals(source.getAbsolutePath(), target.getAbsolutePath())) {
            return true;
        }

        boolean mkdir = mkdirs(target);
        if (!mkdir) {
            Logs.w("mkdir dir failed : " + target);
        }

        File[] childs = source.listFiles();
        if (childs == null) {
            final String reason = "copyFileOrDir source not a readable directory: "+source;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                throw new AccessDeniedException(source.getAbsolutePath(), null, reason);
            } else {
                throw new IOException(reason);
            }
        }
        if (childs.length == 0) {
            return true;
        }

        // 如果目标文件夹已操作，则先删除
        deleteDir(target);

        for (File child : childs) {
            // 复制到对应的目标路径
            boolean copy = copyFileOrDir(child, new File(target, child.getName()));
            if (!copy) {
                Logs.w("copyFileOrDir failed : " + source + " to " + target);
                return false;
            }
        }
        return true;
    }
    /**
     * 复制文件
     * @return 不存在源文件，则返回true
     * @see #copyDir(File, File)
     * @see #copyFileOrDir(File, File)
     */
    @SuppressWarnings("ConstantConditions")
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public static boolean copyFile(@NonNull File source, @NonNull File target) throws IOException {
        if (!exists(source)) {
            return true;
        }
        if (!source.isFile()) {
            throw new IllegalArgumentException("copyFile source is not a file: "+source);
        }
        if (target == null) {
            throw new NullPointerException();
        }
        if (TextUtils.equals(source.getAbsolutePath(), target.getAbsolutePath())) {
            return true;
        }

        if (source.length() == 0) {
            return true;
        }

        boolean mkdir = mkdirs(target.getParentFile());
        if (!mkdir) {
            Logs.w("mkdir dir failed : " + target.getParentFile());
        }

        // 如果目标文件已操作，则先删除
        deleteFile(target);

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            return IOUtils.copy(inStream, outStream);
        } finally {
            IOUtils.closeQuietly(inStream);
            IOUtils.closeQuietly(outStream);
        }
    }


    /**
     * 计算文件或文件夹的大小
     * @return bytes,字节大小
     */
    public static long size(@NonNull File file) {
        if (!exists(file)) {
            return 0;
        }

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) {
                return 0;
            }

            long sum = 0;
            for (File child : children) {
                sum += size(child);
            }
            return sum;
        } else {
            return file.length();
        }
    }

    /**
     * 计算文件夹总空间容量，单位byte
     */
    public static long getTotalSpace(@NonNull File dir) {
        if (exists(dir)) {
            return dir.getTotalSpace();
        } else {
            return 0;
        }
    }

    /**
     * 计算文件夹剩余空间容量，单位byte
     */
    public static long getFreeSpace(@NonNull File dir) {
        if (exists(dir)) {
            return dir.getFreeSpace();
        } else {
            return 0;
        }
    }

}
