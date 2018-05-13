package code.complete.common.app;

import java.util.Map;

/**
 * APP基础设置
 * @author imkarl
 */
public class AppSettings {
    private AppSettings() {
    }

    private static final PreferenceUtils PREFERENCE_SETTINGS = new PreferenceUtils("__app_settings");

    public static PreferenceUtils preference() {
        return PREFERENCE_SETTINGS;
    }

    public static String getString(String key) {
        return PREFERENCE_SETTINGS.getString(key);
    }
    public static boolean put(String key, String value) {
        return PREFERENCE_SETTINGS.put(key, value);
    }

    public static Map<String, ?> getAll() {
        return PREFERENCE_SETTINGS.getAll();
    }

}
