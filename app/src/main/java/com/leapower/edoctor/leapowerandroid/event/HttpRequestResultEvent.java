package com.leapower.edoctor.leapowerandroid.event;

import com.leapower.edoctor.leapowerandroid.enums.HttpRequestType;

import java.util.Map;

/**
 * Created by chao on 2018/05/15.
 */
public class HttpRequestResultEvent<T> {

    /*API编码*/
private HttpRequestType apiType;
    /*原始请求地址*/
private String apiUrl;
    /*API请求结果 成功or失败*/
private boolean apiResult;
    /*请求结果*/
private T apiData;
    /* 失败信息 */
private RestError[] errors;
    /*传递跟踪信息*/
private Map<String, Object> extras;

public HttpRequestResultEvent(HttpRequestType apiType,String apiUrl,boolean apiResult,T apiData,Map<String, Object> extras){
        super();
        this.apiType = apiType;
        this.apiUrl = apiUrl;
        this.apiResult = apiResult;
        this.apiData = apiData;
        this.extras = extras;
        }

public HttpRequestResultEvent(HttpRequestType apiType,String apiUrl,boolean apiResult,RestError[] errors,Map<String, Object> extras){
        super();
        this.apiType = apiType;
        this.apiUrl = apiUrl;
        this.apiResult = apiResult;
        this.errors = errors;
        this.extras = extras;
        }

@Override
public String toString() {
        return "HttpRequestResultEvent{" +
        "apiType=" + apiType +
        ", apiUrl=" + apiUrl +
        ", apiResult=" + apiResult +
        ", apiData=" + apiData +
        ", errors=" + errors +
        ", errors=" + errors +
        ", extras=" + extras +
        '}';
        }

public HttpRequestType getApiType() {
        return apiType;
        }

public void setApiType(HttpRequestType apiType) {
        this.apiType = apiType;
        }

public String getApiUrl() {
        return apiUrl;
        }

public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        }

public boolean isApiResult() {
        return apiResult;
        }

public void setApiResult(boolean apiResult) {
        this.apiResult = apiResult;
        }

public T getApiData() {
        return apiData;
        }

public void setApiData(T apiData) {
        this.apiData = apiData;
        }

public RestError[] getErrors() {
        return errors;
        }

public void setErrors(RestError[] errors) {
        this.errors = errors;
        }

public Map<String, Object> getExtras() {
        return extras;
        }

public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
        }
        }

