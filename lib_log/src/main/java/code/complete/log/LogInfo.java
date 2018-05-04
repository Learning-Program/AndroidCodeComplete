package code.complete.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import java.util.List;

/**
 * Log Info
 * @author imkarl
 */
public final class LogInfo {
    private int priority;
    private String tag;
    private long time;
    private String threadName;
    private StackTraceElement[] callerStackTrace;

    private LogInfo() {
    }

    @NonNull
    static LogInfo obtain(@LogPriority int priority, @NonNull String tag, @NonNull Thread currentThread, @NonNull List<String> innerCallerNames) {
        LogInfo infos = new LogInfo();
        infos.priority = priority;
        infos.tag = tag;
        infos.time = System.currentTimeMillis();
        infos.threadName = currentThread.getName();
        infos.callerStackTrace = filterCaller(currentThread.getStackTrace(), innerCallerNames);
        return infos;
    }

    /**
     * 过滤调用栈
     * @return 不包含Logs相关的调用栈
     */
    private static StackTraceElement[] filterCaller(@NonNull StackTraceElement[] callStackTrace, @Nullable List<String> innerCallerNames) {
        boolean hasCurrentClass = false;
        int startIndex = 0;
        for (int i = 0; i < callStackTrace.length; i++) {
            String className = callStackTrace[i].getClassName();
            if (className.startsWith(Logs.class.getName())) {
                hasCurrentClass = true;

            } else if (hasCurrentClass) {
                boolean isInnerCaller = false;
                if (innerCallerNames != null) {
                    for (String callerName : innerCallerNames) {
                        if (className.startsWith(callerName)) {
                            isInnerCaller = true;
                            break;
                        }
                    }
                }

                if (!isInnerCaller) {
                    startIndex = i;
                    break;
                }
            }
        }

        StackTraceElement[] stackTraceElement = new StackTraceElement[callStackTrace.length - startIndex];
        System.arraycopy(callStackTrace, startIndex, stackTraceElement, 0, stackTraceElement.length);
        return stackTraceElement;
    }


    /**
     * 获取调用者信息
     */
    @NonNull
    public CharSequence getCallerString() {
        final StackTraceElement element = callerStackTrace[0];
        final String className = element.getClassName();

        StringBuilder content = new StringBuilder(className.substring(className.lastIndexOf(".") + 1));
        content.append('.').append(element.getMethodName());
        if (element.isNativeMethod()) {
            return content.append("(Native Method)");
        }
        if (element.getFileName() != null && element.getLineNumber() >= 0) {
            return content.append('(').append(element.getFileName()).append(':').append(element.getLineNumber()).append(')');
        }
        if (element.getFileName() != null) {
            return content.append('(').append(element.getFileName()).append(')');
        }
        return content.append("(Unknown Source)");
    }

    void recycle() {
        tag = null;
        threadName = null;
        callerStackTrace = null;
    }

    @LogPriority
    public int getPriority() {
        return priority;
    }
    @NonNull
    public String getTag() {
        return tag;
    }
    public long getTime() {
        return time;
    }
    @NonNull
    public String getThreadName() {
        return threadName;
    }
    @NonNull @Size(min=1)
    public StackTraceElement[] getCallerStackTrace() {
        return callerStackTrace;
    }
}
