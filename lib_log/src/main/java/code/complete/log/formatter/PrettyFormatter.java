package code.complete.log.formatter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import code.complete.log.LogFormatter;

/**
 * 美化输出的Formatter
 * @author imkarl
 */
public final class PrettyFormatter implements LogFormatter {

    private static final String WRAP_START = "╔═══════════════════════════════════════════════════════════════════";
    private static final String WRAP_MIDDLE = "║";
    private static final String WRAP_BETWEEN = "══════════════════════════════════════════════════════════════════";
    private static final String WRAP_END = "╚═══════════════════════════════════════════════════════════════════";

    @NonNull
    @Override
    public String format(@Nullable Object message) {
        if (message == null) {
            return "[NULL]";
        }

        String str;
        if (message instanceof String) {
            str = getSpecialString((String) message);
        } else if (message instanceof JSONObject) {
            str = getJSONString((JSONObject) message);
        } else if (message instanceof JSONArray) {
            str = getJSONString((JSONArray) message);
        } else if (message instanceof Throwable) {
            str = getThrowableString((Throwable) message);
        } else if (message instanceof StackTraceElement[]) {
            str = getStackTraceString((StackTraceElement[]) message);
        } else {
            str = message.toString();
        }
        return wrap(str);
    }


    @NonNull
    private static String getSpecialString(@NonNull String str) {
        if (str.trim().isEmpty()) {
            str = str.replace("\n", "\\n");
            str = str.replace("\t", "\\t");
            return "["+str+"]";
        }
        return str;
    }

    @NonNull
    private static String getJSONString(@NonNull JSONObject json) {
        try {
            return json.toString(2);
        } catch (JSONException e) {
            return json.toString();
        }
    }
    @NonNull
    private static String getJSONString(@NonNull JSONArray json) {
        try {
            return wrap(json.toString(2));
        } catch (JSONException e) {
            return json.toString();
        }
    }

    @NonNull
    private static String getThrowableString(@NonNull Throwable throwable) {
        final String throwableName = throwable.getClass().getName();
        final String message = throwable.getLocalizedMessage();

        StringBuilder str = new StringBuilder("This is throwable stack trace:").append('\n');
        str.append(WRAP_BETWEEN).append('\n');
        str.append(message != null ? (throwableName + ": " + message) : throwableName).append('\n');
        final StackTraceElement[] traces = throwable.getStackTrace();
        if (traces.length > 0) {
            for (StackTraceElement element : traces) {
                str.append("\tat ").append(element).append('\n');
            }
        } else {
            str.append("\t(No StackTrace)").append('\n');
        }
        return str.toString();
    }
    @NonNull
    private static String getStackTraceString(@NonNull StackTraceElement[] traces) {
        StringBuilder str = new StringBuilder("This is caller method trace:").append('\n');
        str.append(WRAP_BETWEEN).append('\n');
        if (traces.length > 0) {
            for (StackTraceElement element : traces) {
                str.append("\tat ").append(element).append('\n');
            }
        } else {
            str.append("\t(No StackTrace)").append('\n');
        }
        return str.toString();
    }

    @NonNull
    private static String wrap(@NonNull String str) {
        StringBuilder strBuilder = new StringBuilder(WRAP_START).append('\n');
        for (String line : str.split("\n")) {
            while (line.length() > 150) {
                strBuilder.append(WRAP_MIDDLE).append(" ").append(line.substring(0, 150)).append('\n');
                line = line.substring(150);
            }
            if (!TextUtils.isEmpty(line)) {
                strBuilder.append(WRAP_MIDDLE).append(" ").append(line).append('\n');
            }
        }
        strBuilder.append(WRAP_END);
        return strBuilder.toString();
    }

}
