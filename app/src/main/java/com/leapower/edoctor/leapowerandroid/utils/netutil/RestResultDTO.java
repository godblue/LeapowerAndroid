package com.leapower.edoctor.leapowerandroid.utils.netutil;

import com.leapower.edoctor.leapowerandroid.event.RestError;

import java.io.Serializable;

/**
 * Created by chao on 2018/05/15.
 */
public class RestResultDTO<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4828710422514242917L;

    /**
     * 请求的处理结果状态
     */
    public enum Status {
        /**
         * 成功 或 失败
         */
        success, failure;
    }

    /**
     * 处理结果状态
     */
    protected Status status;

    /**
     * 错误消息
     */
    protected RestError[] errors;

    private T data;

    /**
     * 时间戳
     */
    protected Long timestamp;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RestError[] getErrors() {
        return errors;
    }

    public void setErrors(RestError[] errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
