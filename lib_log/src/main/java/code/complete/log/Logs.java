package code.complete.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import code.complete.log.formatter.SimpleFormatter;
import code.complete.log.printer.AndroidPrinter;

/**
 * 日志相关工具类
 * @author imkarl
 */
public final class Logs {

    // 全局TAG
    private static String sGlobalTag = "CodeComplete";
    // 日志打印器
    private static Set<LogPrinter> sPrinters = new HashSet<>(Collections.singletonList(new AndroidPrinter()));
    // 日志格式化工具
    private static LogFormatter sFormatter = new SimpleFormatter();
    // 内部调用者
    private static List<String> sInnerCallerNames = new ArrayList<>();


    public static void v(@Nullable Object message) {
        println(Log.VERBOSE, sGlobalTag, message);
    }
    public static void v(@NonNull String tag, @Nullable Object message) {
        println(Log.VERBOSE, tag, message);
    }

    public static void d(@Nullable Object message) {
        println(Log.DEBUG, sGlobalTag, message);
    }
    public static void d(@NonNull String tag, @Nullable Object message) {
        println(Log.DEBUG, tag, message);
    }

    public static void i(@Nullable Object message) {
        println(Log.INFO, sGlobalTag, message);
    }
    public static void i(@NonNull String tag, @Nullable Object message) {
        println(Log.INFO, tag, message);
    }

    public static void w(@Nullable Object message) {
        println(Log.WARN, sGlobalTag, message);
    }
    public static void w(@NonNull String tag, @Nullable Object message) {
        println(Log.WARN, tag, message);
    }

    public static void e(@Nullable Object message) {
        println(Log.ERROR, sGlobalTag, message);
    }
    public static void e(@NonNull String tag, @Nullable Object message) {
        println(Log.ERROR, tag, message);
    }


    /**
     * 打印日志
     * @param priority 日志级别
     * @param message 日志内容
     */
    public static void println(@LogPriority int priority, @Nullable Object message) {
        println(priority, sGlobalTag, message);
    }
    /**
     * 打印日志
     * @param priority 日志级别
     * @param tag 日志TAG
     * @param message 日志内容
     */
    public static void println(@LogPriority int priority, @NonNull String tag, @Nullable Object message) {
        LogInfo info = null;
        for (LogPrinter printer : sPrinters) {
            if (printer != null && printer.isLoggable(priority, tag, message)) {
                if (info == null) {
                    info = LogInfo.obtain(priority, tag, Thread.currentThread(), sInnerCallerNames);
                }
                printer.println(info, sFormatter.format(message));
            }
        }
        if (info != null) {
            info.recycle();
        }
    }

    /**
     * 打印调用堆栈
     * @param priority 日志级别
     */
    public static void printlnCallerStackTrace(@LogPriority int priority) {
        printlnCallerStackTrace(priority, sGlobalTag);
    }
    /**
     * 打印调用堆栈
     * @param priority 日志级别
     * @param tag 日志TAG
     */
    public static void printlnCallerStackTrace(@LogPriority int priority, @NonNull String tag) {
        final LogInfo info = LogInfo.obtain(priority, tag, Thread.currentThread(), sInnerCallerNames);
        final StackTraceElement[] message = info.getCallerStackTrace();
        for (LogPrinter printer : sPrinters) {
            if (printer != null && printer.isLoggable(priority, tag, message)) {
                printer.println(info, sFormatter.format(message));
            }
        }
        info.recycle();
    }



    /**
     * 设置全局TAG
     */
    public static void setGlobalTag(@NonNull String tag) {
        sGlobalTag = tag;
    }
    /**
     * 设置打印器
     */
    public static void setPrinters(@NonNull LogPrinter... printers) {
        sPrinters.clear();
        //noinspection ConstantConditions
        if (printers != null && printers.length > 0) {
            sPrinters.addAll(Arrays.asList(printers));
        }
    }
    /**
     * 设置打印器
     */
    public static void setPrinters(@NonNull Collection<LogPrinter> printers) {
        sPrinters.clear();
        //noinspection ConstantConditions
        if (printers != null && !printers.isEmpty()) {
            sPrinters.addAll(printers);
        }
    }
    /**
     * 设置格式化工具
     */
    public static void setFormatter(@NonNull LogFormatter formatter) {
        sFormatter = formatter;
    }
    /**
     * 设置内部调用者
     */
    public static void setInnerCallerName(@Nullable Class... callers) {
        sInnerCallerNames.clear();
        if (callers != null) {
            for (Class caller : callers) {
                if (caller != null) {
                    sInnerCallerNames.add(caller.getName());
                }
            }
        }
    }

}
