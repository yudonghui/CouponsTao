package com.ydh.couponstao.common.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by Android on 2018/6/11.
 */

public class PermissionSetting {
    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    private Activity activity;

    public PermissionSetting(Activity activity) {
        this.activity = activity;
    }

    public void execute() {
        Intent intent = obtainSettingIntent();
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            activity.startActivity(defaultApi(activity));
        }
    }

    public void execute(int requestCode) {
        Intent intent = obtainSettingIntent();
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            activity.startActivityForResult(defaultApi(activity), requestCode);
        }
    }

    public void cancel() {
    }

    private Intent obtainSettingIntent() {
        if (MARK.contains("huawei")) {
            return huaweiApi(activity);
        } else if (MARK.contains("xiaomi")) {
            return xiaomiApi(activity);
        } else if (MARK.contains("oppo")) {
            return oppoApi(activity);
        } else if (MARK.contains("vivo")) {
            return vivoApi(activity);
        } else if (MARK.contains("samsung")) {
            return samsungApi(activity);
        } else if (MARK.contains("meizu")) {
            return meizuApi(activity);
        } else if (MARK.contains("smartisan")) {
            return smartisanApi(activity);
        }
        return defaultApi(activity);
    }

    /**
     * App details page.
     */
    private static Intent defaultApi(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    /**
     * Huawei cell phone Api23 the following method.
     */
    private static Intent huaweiApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return defaultApi(context);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        return intent;
    }

    /**
     * Xiaomi phone to achieve the method.
     */
    private static Intent xiaomiApi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    /**
     * Vivo phone to achieve the method.
     */
    private static Intent vivoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
        } else {
            intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
        }
        return intent;
    }

    /**
     * Oppo phone to achieve the method.
     */
    private static Intent oppoApi(Context context) {
        return defaultApi(context);
    }

    /**
     * Meizu phone to achieve the method.
     */
    private static Intent meizuApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return defaultApi(context);
        }
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
        return intent;
    }

    /**
     * Smartisan phone to achieve the method.
     */
    private static Intent smartisanApi(Context context) {
        return defaultApi(context);
    }

    /**
     * Samsung phone to achieve the method.
     */
    private static Intent samsungApi(Context context) {
        return defaultApi(context);
    }
}
