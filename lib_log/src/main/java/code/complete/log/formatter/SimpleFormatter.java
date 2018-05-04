package code.complete.log.formatter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import code.complete.log.LogFormatter;

/**
 * 简单的Formatter实现
 * @author imkarl
 */
public final class SimpleFormatter implements LogFormatter {

    @NonNull
    @Override
    public String format(@Nullable Object message) {
        if (message == null) {
            return "[NULL]";
        }
        if (message instanceof String) {
            if (((String) message).trim().isEmpty()) {
                return " ";
            }
            return (String) message;
        }
        if (message instanceof Throwable) {
            return getThrowableString((Throwable) message);
        }
        if (message instanceof StackTraceElement[]) {
            return getStackTraceString((StackTraceElement[]) message);
        }
        return message.toString();
    }


    private static String getThrowableString(Throwable throwable) {
        return "This is throwable stack trace:" + Log.getStackTraceString(throwable);
    }
    private static String getStackTraceString(StackTraceElement[] trace) {
        if (trace.length > 0) {
            StringBuilder str = new StringBuilder("This is caller method trace:").append('\n');
            for (StackTraceElement element : trace) {
                str.append("\tat ").append(element).append('\n');
            }
            return str.toString();
        } else {
            return "\t(No StackTrace)";
        }
    }

}
