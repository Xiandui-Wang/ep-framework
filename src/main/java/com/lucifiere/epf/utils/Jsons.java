package com.lucifiere.epf.utils;

import com.alibaba.fastjson2.JSON;

import java.util.List;

public final class Jsons {

    public static <T> T parseJson(String s, Class<T> clazz) {
        return JSON.parseObject(s, clazz);
    }

    public static <T> List<T> parseJsonList(String s, Class<T> clazz) {
        return JSON.parseArray(s, clazz);
    }

    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

}
