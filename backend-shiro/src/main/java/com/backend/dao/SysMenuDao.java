package com.backend.dao;

import com.backend.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 * @author Skynet
 * @date 2017年04月24日 16:42
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity>{

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @return
     */
    List<SysMenuEntity> queryListByParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 查询用户的权限列表
     */
    List<SysMenuEntity> queryUserPermsList(Long userId);

}
