package code.complete.log;

import android.support.annotation.IntDef;
import android.util.Log;

/**
 * 日志级别
 * @author imkarl
 */
@IntDef({Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR})
public @interface LogPriority {
}
