package com.leapower.edoctor.leapowerandroid.utils.netutil;

import com.leapower.edoctor.leapowerandroid.event.RestError;

/**
 * Created by chao on 2018/05/15.
 */
public abstract class RestCallback<T> {
    public abstract void onSuccess(T data);

    public abstract void onFail(RestError[] errors);

    public void inProgress(float progress, long total , int id){

    }
}