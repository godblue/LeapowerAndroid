package com.leapower.edoctor.leapowerandroid.event;

/**
 * Created by chao on 2018/05/15.
 */
public class NetError {

    private int errorCode;

    private String url;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
