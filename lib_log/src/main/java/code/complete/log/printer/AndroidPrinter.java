package code.complete.log.printer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import code.complete.log.LogInfo;
import code.complete.log.LogPrinter;
import code.complete.log.LogPriority;

/**
 * Android Logcat Printer
 * @author imkarl
 */
public class AndroidPrinter implements LogPrinter {

    // 单行最大输出长度
    private static final int MAX_LINE_LENGTH = 3000;
    // 是否需要间隔变化TAG
    private static boolean shouldSplitTag = true;

    @Override
    public boolean isLoggable(@LogPriority int priority, @NonNull String tag, @Nullable Object message) {
        return true;
    }

    /**
     * 是否应该显示线程信息
     */
    public boolean shouldShowThread() {
        return true;
    }

    @Override
    public void println(@NonNull LogInfo info, @NonNull final String message) {
        String tag = info.getTag();

        StringBuilder describe = new StringBuilder(info.getCallerString());
        if (shouldShowThread()) {
            describe.append('\t').append("[Thread: ").append(info.getThreadName()).append(']');
        }
        log(info.getPriority(), tag, describe.toString());

        // 避免超出Logcat最大长度限制
        for (String line : message.split("\n")) {
            final int length = line.length();

            int start = 0;
            do {
                int endIndex = start + Math.min(MAX_LINE_LENGTH, length - start);
                String part = line.substring(start, endIndex);
                start = endIndex;

                log(info.getPriority(), tag, "    "+part);
            } while (length > 0 && start < length);
        }
    }

    private static void log(@LogPriority int priority, @NonNull String tag, @NonNull String msg) {
        if (shouldSplitTag) {
            tag += "\u200b";
        }
        shouldSplitTag = !shouldSplitTag;

        android.util.Log.println(priority, tag, msg);
    }

}
