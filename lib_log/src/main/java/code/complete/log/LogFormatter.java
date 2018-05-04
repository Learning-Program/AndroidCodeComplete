package code.complete.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 格式化工具
 * @author imkarl
 */
public interface LogFormatter {

    /**
     * 将Object转化成可视化的内容
     */
    @NonNull
    String format(@Nullable Object message);

}
