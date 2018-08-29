package com.okan.assettenapkyukletme;

class ApkItem {

    private String apkName;
    private String pkgcName;
    private Boolean isInstalled;

    ApkItem(String apkName, String pkgcName, Boolean isInstalled) {
        this.setApkName(apkName);
        this.setPkgcName(pkgcName);
        this.setInstalled(isInstalled);
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getPkgcName() {
        return pkgcName;
    }

    public void setPkgcName(String pkgcName) {
        this.pkgcName = pkgcName;
    }

    public Boolean getInstalled() {
        return isInstalled;
    }

    public void setInstalled(Boolean installed) {
        isInstalled = installed;
    }

}

