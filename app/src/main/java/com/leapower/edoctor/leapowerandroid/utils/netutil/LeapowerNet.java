package com.leapower.edoctor.leapowerandroid.utils.netutil;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.leapower.edoctor.leapowerandroid.event.NetError;
import com.leapower.edoctor.leapowerandroid.event.RestError;
import com.leapower.edoctor.leapowerandroid.utils.GsonUtils;
import com.leapower.edoctor.leapowerandroid.utils.NetworkUtils;
import com.leapower.edoctor.leapowerandroid.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chao on 2018/05/15.
 */
public class LeapowerNet {
    private static final String TAG = "OkNetUtils";

    private static class SingletonHolder {
        static final LeapowerNet INSTANCE = new LeapowerNet();
    }

    public static LeapowerNet getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private LeapowerNet() {

    }

    private static void onNetworkInvalid() {
        NetError error = new NetError();
        error.setErrorCode(-1);
        error.setUrl("");
        EventBus.getDefault().post(error);
    }

    private static <T> void onFailCallback(RestCallback<T> callback, String code, String msg) {
        RestError error = new RestError(code, msg, code);
        RestError errors[] = {error};
        callback.onFail(errors);
    }

    private static <T> Callback<T> createOkhttpCallback(final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, final String url, final String method) {
        Callback<T> call = new Callback<T>() {

            private NetError error;

            @Override
            public boolean validateReponse(Response response, int id) {
                if ((response.code() >= 200 && response.code() < 300)
                        || response.code() == 401
                        || response.code() == 403
                        || response.code() == 500
                        )
                    return true;
                else
                    return false;
            }

            @Override
            public T parseNetworkResponse(Response response, int id) throws Exception {
                //上层处理已经把401、403、500的状态码返回了，在这里需要先判断状态码
                if (response.code() == 403 || response.code() == 401) {
                    //向上层发送eventbus，请求失败，需要重新自动登录
                    //上层如果接收到消息，则应该自动跳转到登录页面
                    error = new NetError();
                    error.setErrorCode(response.code());
                    error.setUrl(url);
                    EventBus.getDefault().post(error);
                    // 打印log 开始
                    Log.d(TAG, "请求失败（" + response.code() + "）" + method + "：" + url);
                    // 打印log 结束
                    onFailCallback(callback, "401", "认证失败");
                    return null;
                }
                if (response.code() == 500) {
                    //向上层发送eventbus，请求失败
                    //上层如果接收到消息，则应该做相应的提示
                    error = new NetError();
                    error.setErrorCode(response.code());
                    EventBus.getDefault().post(error);
                    onFailCallback(callback, "500", "服务异常");
                    // 打印log 开始
                    Log.d(TAG, "请求失败（" + response.code() + "）" + method + "：" + url);
                    // 打印log 结束
                    return null;
                }


                String json = response.body().string();
                // 打印log 开始
                Log.d(TAG, "请求成功 " + method + "：" + url);
                Log.d(TAG, "返回数据：" + json);
                // 打印log 结束
                RestResultDTO<T> data = GsonUtils.jsonToPojo(json, type);
                if (data.getStatus().equals(RestResultDTO.Status.success)) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFail(data.getErrors());
                }
                return null;
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                callback.inProgress(progress, total, id);

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                onFailCallback(callback, "-1", "网络异常");
                if (e instanceof ConnectException
                        || e instanceof SocketException
                        || e instanceof SocketTimeoutException) {
                    onNetworkInvalid();
                } else {
                    Log.e("greendao", "greenDaoException", e);
                    return;
                }

            }

            @Override
            public void onResponse(Object response, int id) {
                System.out.println("==============onResponse: " + response);
            }
        };
        return call;
    }

    /***
     * get请求
     *
     * @param url
     * @return
     */
    public static <T> void get(Context context, String url, final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, String... urlVariable) {

        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }
        OkHttpUtils.get().url(url).addHeader("Content-Type", "application/json; charset=utf-8").build().execute(createOkhttpCallback(type, callback, url, "get"));
    }

    /***
     * post请求
     *
     * @param url
     * @return
     */
    public static <T> void post(Context context, String url, final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, Object data, String... urlVariable) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }
        String jsonData = null;
        if (null != data) {
            jsonData = GsonUtils.pojoToJson(data);
        } else {
            jsonData = "";
        }
        OkHttpUtils.postString().url(url).content(jsonData).mediaType(MediaType.parse("application/json; charset=utf-8")).build().execute(createOkhttpCallback(type, callback, url, "post"));
    }


    /***
     * put请求
     *
     * @param url
     * @return
     */
    public static <T> void put(Context context, String url, final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, Object data, String... urlVariable) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }
        String jsonData = null;
        if (null != data) {
            jsonData = GsonUtils.pojoToJson(data);
        } else {
            jsonData = "";
        }
        OkHttpUtils.put().url(url).requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)).build().execute(createOkhttpCallback(type, callback, url, "put"));
    }


    /***
     * delete请求
     *
     * @param url
     * @return
     */
    public static <T> void delete(Context context, String url, final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, Object data, String... urlVariable) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }
        String jsonData = "";
        if (data != null) {
            jsonData = GsonUtils.pojoToJson(data);
        }
        OkHttpUtils.delete().url(url).requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData)).build().execute(createOkhttpCallback(type, callback, url, "delete"));
    }


    public static <T> void uploadFile(Context context, String url, final TypeToken<RestResultDTO<T>> type, final RestCallback<T> callback, String name, String filename, File file, String... urlVariable) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }
        OkHttpUtils.post().url(url).addFile(name, filename, file).build().execute(createOkhttpCallback(type, callback, url, "post"));
    }

    public static <T> void downloadFile(Context context, String url, String filePath, String fileName, final RestCallback<File> callback, String... urlVariable) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            onFailCallback(callback, "-1", "网路异常");
            onNetworkInvalid();
            return;
        }

        if (urlVariable.length > 0) {
            url = StringUtils.replaceUrlVariableValue(url, urlVariable);
        }

        OkHttpUtils.get().url(url).build().execute(new FileCallBack(filePath, fileName) {
            @Override
            public boolean validateReponse(Response response, int id) {
                if ((response.code() >= 200 && response.code() < 300)
                        || response.code() == 401
                        || response.code() == 403
                        || response.code() == 500
                        )
                    return true;
                else
                    return false;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                onFailCallback(callback, "-1", "网路异常");
                if (e instanceof ConnectException
                        || e instanceof SocketException
                        || e instanceof SocketTimeoutException) {
                    onNetworkInvalid();
                } else {
                    return;
                }
            }


            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                callback.inProgress(progress, total, id);

            }

            @Override
            public void onResponse(File response, int id) {
                //这里的response是文件的句柄，方便业务处理
                callback.onSuccess(response);
            }

            @Override
            public File parseNetworkResponse(Response response, int id) throws Exception {


                //上层处理已经把401、403、500的状态码返回了，在这里需要先判断状态码
                if (response.code() == 403 || response.code() == 401) {
                    //向上层发送eventbus，请求失败，需要重新自动登录
                    //上层如果接收到消息，则应该自动跳转到登录页面

                    onFailCallback(callback, "401", "认证失败");

                    NetError error401403 = new NetError();
                    error401403.setErrorCode(response.code());
                    EventBus.getDefault().post(error401403);
                    return null;
                }
                if (response.code() == 500) {

                    onFailCallback(callback, "500", "服务器异常");
                    //向上层发送eventbus，请求失败
                    //上层如果接收到消息，则应该做相应的提示
                    NetError error500 = new NetError();
                    error500.setErrorCode(response.code());
                    EventBus.getDefault().post(error500);
                    return null;
                }
                return saveFile(response, id);
            }

        });

    }

}