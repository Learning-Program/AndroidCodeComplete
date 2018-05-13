package code.complete.common.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * 设备基本信息
 * Created by imkarl on 2017/9/7.
 */
public class DeviceInfo {
    private DeviceInfo() {
    }

    private static final String KEY_UNIQUE_ID = "KEY_UNIQUE_ID";

    private static String uniqueId;


    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getCpuABI() {
        if (!TextUtils.isEmpty(Build.CPU_ABI)) {
            return Build.CPU_ABI;
        }
        if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
            return Build.CPU_ABI2;
        }
        return "";
    }

    /**
     * 获取设备唯一ID
     * 建议获取权限，以便卸载重装时也能获取到相同的唯一ID: {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     * @return 根据设备相关信息生成
     */
    @NonNull
    @SuppressLint("MissingPermission")
    public static String getUniqueId() {
        if (TextUtils.isEmpty(uniqueId)) {
            synchronized (DeviceInfo.class) {
                if (TextUtils.isEmpty(uniqueId)) {
                    String uuid = AppSettings.getString(KEY_UNIQUE_ID);
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getIMEI();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getAndroidId();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getSimSerialNumber();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = UUID.randomUUID().toString();
                    }
                    if (!TextUtils.isEmpty(uuid)) {
                        AppSettings.put(KEY_UNIQUE_ID, uuid);
                    }
                    uniqueId = uuid;
                }
            }
        }
        return uniqueId;
    }


    /**
     * 获取sim卡的唯一标识：IMSI
     */
    @NonNull
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSubscriberId() {
        try {
            @SuppressWarnings("ConstantConditions") @SuppressLint("HardwareIds")
            String subscriberId = ((TelephonyManager) AppUtils.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            if (!TextUtils.isEmpty(subscriberId)) {
                return subscriberId;
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * 获取设备串号：IMEI
     */
    @NonNull
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI() {
        try {
            String deviceId = null;
            TelephonyManager telephonyManager = ((TelephonyManager) AppUtils.getApplication().getSystemService(Context.TELEPHONY_SERVICE));
            if (telephonyManager != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    deviceId = telephonyManager.getImei();
                } else {
                    deviceId = telephonyManager.getDeviceId();
                }
            }
            if (!TextUtils.isEmpty(deviceId) && Long.parseLong(deviceId) > 0) {
                return deviceId;
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * 获取序列号：SN
     */
    @NonNull
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSimSerialNumber() {
        try {
            @SuppressWarnings("ConstantConditions") @SuppressLint("HardwareIds")
            String tmSerial = ((TelephonyManager) AppUtils.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
            if (!TextUtils.isEmpty(tmSerial)) {
                return tmSerial;
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * 获取Android设备标识
     */
    @NonNull
    public static String getAndroidId() {
        try {
            @SuppressWarnings("ConstantConditions") @SuppressLint("HardwareIds")
            String androidId = Settings.Secure.getString(AppUtils.getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
                return androidId;
            }
        } catch (SecurityException ignored) {
        }
        return "";
    }

    /**
     * 获取描述信息
     */
    @NonNull
    public static String getDescription() {
        return "DeviceInfo{" +
                "model=" + getModel() +
                ", brand=" + getBrand() +
                ", sdkVersion=" + getSdkVersion() +
                ", cpuABI=" + getCpuABI() +
                ", uniqueId=" + getUniqueId() +
                '}';
    }

}
