package code.complete.common.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences相关工具类
 * @author imkarl
 */
public final class PreferenceUtils {

    @IntDef({Context.MODE_APPEND,
            Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,
            Context.MODE_MULTI_PROCESS,
            Context.MODE_NO_LOCALIZED_COLLATORS,
            Context.MODE_PRIVATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SharedMode{}

    @NonNull
    private final String mFileName;
    @NonNull
    private final SharedPreferences mPreferences;


    public PreferenceUtils(@NonNull String fileName) {
        this(fileName, Context.MODE_PRIVATE);
    }
    public PreferenceUtils(@NonNull String fileName, @SharedMode int mode) {
        this.mFileName = fileName;
        this.mPreferences = AppUtils.getApplication().getSharedPreferences(fileName, mode);
    }


    /**
     * 获取文件名
     */
    @NonNull
    public String getFileName() {
        return mFileName;
    }


    public boolean put(@NonNull String key, @Nullable String value) {
        return !TextUtils.isEmpty(key) && edit().putString(key, value).commit();
    }
    @NonNull
    public String getString(@NonNull String key) {
        return getString(key, "");
    }
    public String getString(@NonNull String key, @Nullable String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean put(@NonNull String key, boolean value) {
        return !TextUtils.isEmpty(key) && edit().putBoolean(key, value).commit();
    }
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public boolean put(@NonNull String key, float value) {
        return !TextUtils.isEmpty(key) && edit().putFloat(key, value).commit();
    }
    public float getFloat(@NonNull String key) {
        return getFloat(key, 0F);
    }
    public float getFloat(@NonNull String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public boolean put(@NonNull String key, double value) {
        return !TextUtils.isEmpty(key) && edit().putString(key, String.valueOf(value)).commit();
    }
    public double getDouble(@NonNull String key) {
        return getDouble(key, 0D);
    }
    public double getDouble(@NonNull String key, double defValue) {
        String strValue = getString(key, null);
        if (!TextUtils.isEmpty(strValue)) {
            try {
                return Double.parseDouble(strValue);
            } catch (Exception ignored) {
            }
        }
        return defValue;
    }

    public boolean put(@NonNull String key, int value) {
        return !TextUtils.isEmpty(key) && edit().putInt(key, value).commit();
    }
    public int getInt(@NonNull String key) {
        return getInt(key, 0);
    }
    public int getInt(@NonNull String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public boolean put(@NonNull String key, long value) {
        return !TextUtils.isEmpty(key) && edit().putLong(key, value).commit();
    }
    public long getLong(@NonNull String key) {
        return getLong(key, 0);
    }
    public long getLong(@NonNull String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public boolean put(@NonNull String key, Set<String> value) {
        return !TextUtils.isEmpty(key) && edit().putStringSet(key, value).commit();
    }
    @NonNull
    public Set<String> getStringSet(@NonNull String key) {
        return getStringSet(key, Collections.emptySet());
    }
    public Set<String> getStringSet(@NonNull String key, @Nullable Set<String> defValue) {
        return mPreferences.getStringSet(key, defValue);
    }

    @NonNull
    public Map<String, ?> getAll() {
        Map<String, ?> data = mPreferences.getAll();
        if (data != null) {
            return data;
        }
        return Collections.emptyMap();
    }

    public boolean contains(@NonNull final String key) {
        return mPreferences.contains(key);
    }

    /**
     * 删除数据
     */
    public boolean remove(@NonNull String key) {
        return !TextUtils.isEmpty(key) && edit().remove(key).commit();
    }

    /**
     * 清空所有数据
     */
    public boolean clear() {
        return edit().clear().commit();
    }


    /**
     * 获取Editor
     */
    @CheckResult
    public SharedPreferences.Editor edit() {
        return mPreferences.edit();
    }

}
