package com.ydh.couponstao.common.permission;

import java.util.List;

/**
 * Created by Android on 2018/6/11.
 */

public class PermissionUtils {
    private static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
    private static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";

    private static final String CAMERA = "android.permission.CAMERA";

    private static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
    private static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    private static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";

    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";

    private static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";

    private static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    private static final String CALL_PHONE = "android.permission.CALL_PHONE";
    private static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    private static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    private static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    private static final String USE_SIP = "android.permission.USE_SIP";
    private static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";

    private static final String BODY_SENSORS = "android.permission.BODY_SENSORS";

    private static final String SEND_SMS = "android.permission.SEND_SMS";
    private static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    private static final String READ_SMS = "android.permission.READ_SMS";
    private static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    private static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";

    private static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String MEDIA_CONTENT_CONTROL = "android.permission.MEDIA_CONTENT_CONTROL";


    public static String getName(List<String> perssionList) {
        String message = "";
        for (int i = 0; i < perssionList.size(); i++) {
            String perssion = perssionList.get(i);
            switch (perssion) {
                case READ_CALENDAR:
                case WRITE_CALENDAR: {
                    if (!message.contains("读/写日历"))
                        message += "读/写日历";
                    break;
                }

                case CAMERA: {
                    if (!message.contains("相机"))
                        message = "相机";
                    break;
                }
                case READ_CONTACTS:
                case WRITE_CONTACTS:
                case GET_ACCOUNTS: {
                    if (!message.contains("通讯录"))
                        message = "通讯录";
                    break;
                }
                case ACCESS_FINE_LOCATION:
                case ACCESS_COARSE_LOCATION: {
                    if (!message.contains("位置信息"))
                        message = "位置信息";
                    break;
                }
                case RECORD_AUDIO: {
                    if (!message.contains("麦克风"))
                        message = "麦克风";
                    break;
                }
                case READ_PHONE_STATE:
                case CALL_PHONE:
                case READ_CALL_LOG:
                case WRITE_CALL_LOG:
                case USE_SIP:
                case PROCESS_OUTGOING_CALLS: {
                    if (!message.contains("电话"))
                        message = "电话";
                    break;
                }
                case BODY_SENSORS: {
                    if (!message.contains("身体传感器"))
                        message = "身体传感器";
                    break;
                }
                case SEND_SMS:
                case RECEIVE_SMS:
                case READ_SMS:
                case RECEIVE_WAP_PUSH:
                case RECEIVE_MMS: {
                    if (!message.contains("短信"))
                        message = "短信";
                    break;
                }
                case READ_EXTERNAL_STORAGE:
                case WRITE_EXTERNAL_STORAGE: {
                    if (!message.contains("存储空间"))
                        message = "存储空间";
                    break;
                }
                case MEDIA_CONTENT_CONTROL:

                    break;
            }
        }
        return message;
    }
}
