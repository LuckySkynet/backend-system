package com.backend.controller;

import com.backend.entity.SysUserEntity;
import com.backend.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author Skynet
 * @date 2017年04月24日 18:54
 */
public abstract class AbstractController {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser(){
        return ShiroUtils.getUserEntity();
    }

    protected Long getUserId(){
        return getUser().getUserId();
    }


}
