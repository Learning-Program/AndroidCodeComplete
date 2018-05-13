package code.complete.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 打印器
 * @author imkarl
 */
public interface LogPrinter {

    boolean isLoggable(@LogPriority int priority, @NonNull String tag, @Nullable Object message);

    void println(@NonNull LogInfo infos, @NonNull String message);

}
