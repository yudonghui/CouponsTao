package com.ydh.couponstao.common.permission;

import java.util.List;

/**
 * Created by Android on 2018/6/11.
 */

public interface PermissionListener {
    //授权成功
    void onGranted();

    //授权部分
    void onGranted(List<String> grantedPermission);

    //拒绝授权
    void onDenied(List<String> deniedPermission);
}
