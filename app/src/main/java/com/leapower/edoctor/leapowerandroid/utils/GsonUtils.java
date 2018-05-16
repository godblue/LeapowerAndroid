package com.leapower.edoctor.leapowerandroid.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by chao on 2018/05/15.
 */
public class GsonUtils {
    private static Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     * 对象实例转JSON字符串。
     *
     * @param pojo 对象实例
     * @param <T> 对象类型
     * @return 转换的JSON字符串
     */
    public static <T> String pojoToJson(final T pojo) {
        if (pojo == null) {
            return null;
        }

        return gson.toJson(pojo);
    }

    /**
     * JSON字符串转对象实例。
     *
     * @param json JSON串
     * @param pojoClass 对象类型
     * @param <T> 对象类型
     * @return 转换的对象实例
     */
    public static <T> T jsonToPojo(final String json, final Class<T> pojoClass) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return gson.fromJson(json, pojoClass);
    }

    /**
     * JSON字符串转对象实例。
     *
     * @param json JSON串
     * @param pojoClass 对象类型
     * @param <T> 对象类型
     * @return 转换的对象实例
     */
    public static <T> T jsonToPojo(final String json, final TypeToken<T> pojoClass) {
        if (!StringUtils.hasText(json)) {
            return null;
        }

        return gson.fromJson(json, pojoClass.getType());
    }

    /**
     * json转list对象
     * @param json json字符串
     * @param valueTypeRef TypeToken<List<T>> 类型
     * @return
     */
    public static <T> List<T> jsonToPojoList(final String json, TypeToken<List<T>> valueTypeRef) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        return gson.fromJson(json, valueTypeRef.getType());
    }

    public static void main(String[] s) {

//        String json = "{\"status\":\"failure\",\"errors\":[{\"field\":\"field\",\"errmsg\":\"errmsg\",\"errcode\":\"errcode\"}]}";
//        RestResultDTO<TestBean> result = GsonUtils.jsonToPojo(json, new TypeToken<RestResultDTO<TestBean>>() {
//        });
//        System.out.println(result.getErrors()[0].getErrmsg());
//
//        String json1 = "{\"data\":{\"code\":\"EDOCTOR-1NQ57WQDZRUD\",\"mobile\":\"13333332005\"},\"status\":\"success\"}";
//        RestResultDTO<TestBean> result1 = GsonUtils.jsonToPojo(json1, new TypeToken<RestResultDTO<TestBean>>() {
//        });
//        System.out.println(result1.getData().getCode());
//
//        String json2 = "{\"data\":[{\"code\":\"EDOCTOR-1NQ57WQDZRUD\",\"mobile\":\"13333332005\"}],\"status\":\"success\"}";
//        RestResultDTO<List<TestBean>> result2 = GsonUtils.jsonToPojo(json2,
//                new TypeToken<RestResultDTO<List<TestBean>>>() {
//                });
//        System.out.println(result2.getData().get(0).getCode());
//
//        String json3 = "[{\"code\":\"EDOCTOR-1NQ57WQDZRUD\",\"mobile\":\"13333332005\"}]";
//        List<TestBean> result3 = GsonUtils.jsonToPojoList(json3, new TypeToken<List<TestBean>>() {
//        });
//        System.out.println(result3.get(0).getCode());

    }
}

