package com.ydh.couponstao.common.updateapp;

/**
 * Created by ydh on 2021/4/27
 */
public class VersionInfoEntity {
    private String lowVersion;
    private String version;
    private String appUrl;
    private String forceUpdate;
    private String versionName;
    private String updateContent;

    public String getLowVersion() {
        return lowVersion;
    }

    public void setLowVersion(String lowVersion) {
        this.lowVersion = lowVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

}
