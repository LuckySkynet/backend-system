package com.backend.utils;

/**
 * 自定义异常
 *
 * @author Skynet
 * @date 2017年04月22日 01:38
 */
public class BEException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    public BEException(String message) {
        super(message);
        this.msg = message;
    }

    public BEException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public BEException(String message, int code) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public BEException(String message, int code, Throwable cause) {
        super(message,cause);
        this.msg = message;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}