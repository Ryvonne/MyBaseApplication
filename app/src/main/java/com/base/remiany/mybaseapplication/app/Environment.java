package com.base.remiany.mybaseapplication.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.base.remiany.mybaseapplication.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by wangxiaoyan on 15/4/20.
 */
public class Environment {
    private static String uuid;
    private static String imei;

    private static PackageInfo packageInfo;

    private static PackageInfo pkgInfo() {
        if (packageInfo == null) {
            try {
                Context c = MyApplication._instance();
                packageInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }

        return packageInfo;
    }

    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            Log.e(e.toString());
        }
        return version;
    }

    /**
     * 设备的唯一标识
     * <p/>
     * <p/>
     * 5.6之后deviceId为IMEI<br>
     *
     * @Deprecated 5.6 以后deviceId只作为统计系统使用，业务不应该调用.业务调用imei()
     */
    @Deprecated
    public static String deviceId() {
        return imei();
    }

    /**
     * >=5.6 重新启用uuid
     */
    public static String uuid() {

        if (uuid == null) {
            MyApplication c = MyApplication._instance();
            if (c == null) {
                return null;
            }

            /*
             * 兼容预订的uuid, 继续延用bookinguuid作为全局的uuid
             */
            SharedPreferences prefs = c.getApplicationContext().getSharedPreferences("bookinguuid",
                    Context.MODE_PRIVATE);
            String str = prefs.getString("uuid", "");

            if (!TextUtils.isEmpty(str)) {
                // uuid should look like uuid.
                try {
                    UUID.fromString(str);
                } catch (Exception e) {
                    str = null;
                }
            }

            if (TextUtils.isEmpty(str)) {
                str = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("uuid", str);
                editor.commit();
            }

            uuid = str;
        }

        return uuid;
    }

    /**
     * 手机的IMEI设备序列号
     * <p/>
     * 第一次启动时会保存该序列号，可以频繁调用
     *
     * @return IMEI or "00000000000000" if error
     */
    public static String imei() {
        if (imei == null) {
            // update cached imei when identity changed. including brand, model,
            // radio and system version
            String deviceIdentity = Build.VERSION.RELEASE + ";" + Build.MODEL + ";" + Build.BRAND;
            if (deviceIdentity.length() > 64) {
                deviceIdentity = deviceIdentity.substring(0, 64);
            }
            if (deviceIdentity.indexOf('\n') >= 0) {
                deviceIdentity = deviceIdentity.replace('\n', ' ');
            }

            String cachedIdentity = null;
            String cachedImei = null;
            try {
                // do not use file storage, use cached instead
                File path = new File(MyApplication._instance().getCacheDir(), "cached_imei");
                FileInputStream fis = new FileInputStream(path);
                byte[] buf = new byte[1024];
                int l;
                try {
                    l = fis.read(buf);
                } finally {
                    fis.close();
                }
                String str = new String(buf, 0, l, "UTF-8");
                int a = str.indexOf('\n');
                cachedIdentity = str.substring(0, a);
                int b = str.indexOf('\n', a + 1);
                cachedImei = str.substring(a + 1, b);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (deviceIdentity.equals(cachedIdentity)) {
                imei = cachedImei;
            } else {
                imei = null;
            }

            // cache fail, read from telephony manager
            if (imei == null) {
                try {
                    TelephonyManager tel = (TelephonyManager) MyApplication._instance().getSystemService(
                            Context.TELEPHONY_SERVICE);
                    imei = tel.getDeviceId();
                    if (imei != null) {
                        if (imei.length() < 8) {
                            imei = null;
                        } else {
                            char c0 = imei.charAt(0);
                            boolean allSame = true;
                            for (int i = 0, n = imei.length(); i < n; i++) {
                                if (c0 != imei.charAt(i)) {
                                    allSame = false;
                                    break;
                                }
                            }
                            if (allSame)
                                imei = null;
                        }
                    }
                } catch (Exception e) {
                }
                if (imei != null) {
                    try {
                        File path = new File(MyApplication._instance().getCacheDir(), "cached_imei");
                        FileOutputStream fos = new FileOutputStream(path);
                        String str = deviceIdentity + "\n" + imei + "\n";
                        try {
                            fos.write(str.getBytes("UTF-8"));
                        } finally {
                            fos.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    File path = new File(MyApplication._instance().getCacheDir(), "cached_imei");
                    path.delete();
                }
            }

            if (imei == null) {
                imei = "00000000000000";
            }
        }
        return imei;
    }

    /**
     * versionName
     *
     * @return
     */
    public static String versionName() {
        return pkgInfo().versionName;
    }

    /**
     * versionCode
     *
     * @return
     */
    public static int versionCode() {
        return pkgInfo().versionCode;
    }

    public static boolean isDebug() {
        return Log.LEVEL < Integer.MAX_VALUE;
    }
}
