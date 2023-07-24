package com.ydh.couponstao.entitys;


/**
 * Created by zhengluping on 2018/7/5.
 */

public class IpEntiyty {
    private String ip;//ip
    private String port;//端口
    private String ipValue;//完整地址
    private String remark = "";//备注
    private boolean checked;//是否选中
    private boolean show;//是否列表显示

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    private String playUrl;//在线学习连接

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpValue() {
        return ipValue;
    }

    public void setIpValue(String ipValue) {
        this.ipValue = ipValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

}
