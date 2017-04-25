package com.backend.utils;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.REException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author Skynet
 * @date 2017年04月22日 10:25
 */
public class BEExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(BEExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Result result = new Result();

        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            if(ex instanceof REException){
                result.put("code", ((BEException)ex).getCode());
                result.put("msg", ((BEException)ex).getMessage());
            } else if (ex instanceof DuplicateKeyException) {
                result = Result.error("数据库中已存在该记录");
            } else if (ex instanceof AuthorizationException) {
                result = Result.error("没有权限，请联系管理员授权");
            } else{
                result = Result.error();
            }

            //记录日志
            LOGGER.error(ex.getMessage(), ex);

            String json = JSON.toJSONString(result);
            response.getWriter().print(json);

        } catch (Exception e) {
            LOGGER.error("BEExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
