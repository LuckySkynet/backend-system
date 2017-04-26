package com.backend.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取request和response
 *
 * @author Skynet
 * @date 2017年04月24日 15:08
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse(){
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }


}
