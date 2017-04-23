package com.backend.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Skynet
 * @date 2017年04月22日 10:02
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 3060125660632618378L;

    public Result() {
        //成功默认码值：0
        put("code", 0);
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result ok(){
        return new Result();
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result ok(Map<String, Object> map) {
        Result result = new Result();
        result.putAll(map);
        return result;
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
