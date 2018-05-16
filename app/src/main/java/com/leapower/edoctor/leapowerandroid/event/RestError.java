package com.leapower.edoctor.leapowerandroid.event;

/**
 * Created by chao on 2018/05/15.
 */
public class RestError {
    /**
     * 异常关联的字段
     */
    private String field;
    /**
     * 异常消息
     */
    private String errmsg;

    private String errcode;

    public RestError() {
    }

    public RestError(final String errmsg, final String field) {
        this.field = field;
        this.errmsg = errmsg;
    }

    public RestError(final String errcode, final String errmsg, final String field) {
        this.field = field;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getField() {
        return this.field;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public String getErrcode() {
        return this.errcode;
    }

    public void setErrcode(final String errcode) {
        this.errcode = errcode;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public void setErrmsg(final String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return String.format("{errcode:%s, errmsg:%s, field:%s}", this.errcode, this.errmsg, this.field);
    }
}

