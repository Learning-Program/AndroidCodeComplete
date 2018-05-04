package code.complete.log;

import android.support.annotation.NonNull;

/**
 * 打印器
 * @author imkarl
 */
public interface LogPrinter {

    void println(@NonNull LogInfo infos, @NonNull String message);

}
