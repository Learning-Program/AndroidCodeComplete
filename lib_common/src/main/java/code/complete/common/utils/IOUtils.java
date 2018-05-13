package code.complete.common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * IO相关工具类
 * @author imkarl
 */
public final class IOUtils {
    private IOUtils() {
    }

    /**
     * 静默关闭，不抛异常
     */
    public static void closeQuietly(@Nullable Closeable closeable) {
        try {
            close(closeable);
        } catch (Throwable ignored) {
        }
    }

    public static void close(@Nullable Closeable closeable) throws IOException {
        if(closeable != null) {
            closeable.close();
        }
    }


    /**
     * 读取所有字节
     */
    public static byte[] readBytes(@NonNull InputStream in) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int tmp;
            while ((tmp = in.read()) != -1) {
                out.write(tmp);
            }
            return out.toByteArray();
        } finally {
            closeQuietly(out);
        }
    }
    /**
     * 根据指定起始位置及长度，读取字节
     */
    public static byte[] readBytes(@NonNull InputStream in, long skip, int size) throws IOException {
        if (skip > 0) {
            long skipped;
            while (skip > 0 && (skipped = in.skip(skip)) > 0) {
                skip -= skipped;
            }
        }

        byte[] buf = new byte[size];
        int read = 0;
        for (int i = 0; i < size; i++) {
            read++;
            buf[i] = (byte) in.read();
        }

        byte[] result = new byte[read];
        System.arraycopy(buf, 0, result, 0, read);
        return result;
    }

    /**
     * 复制输入流的数据到输出流
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean copy(@NonNull InputStream in, @NonNull OutputStream out) throws IOException {
        if (in == null || out == null) {
            throw new NullPointerException();
        }

        if (in instanceof FileInputStream && out instanceof FileOutputStream) {
            FileChannel inChannel = ((FileInputStream) in).getChannel();
            FileChannel outChannel = ((FileOutputStream) out).getChannel();
            long length = inChannel.transferTo(0, inChannel.size(), outChannel);
            return length >= 0;

        } else {
            if (!(in instanceof BufferedInputStream)) {
                in = new BufferedInputStream(in);
            }
            if (!(out instanceof BufferedOutputStream)) {
                out = new BufferedOutputStream(out);
            }
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            return true;
        }
    }

}
