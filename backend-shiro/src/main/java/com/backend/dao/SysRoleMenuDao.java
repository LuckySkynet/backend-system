package com.backend.dao;

import com.backend.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Skynet
 * @date 2017年04月24日 16:54
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleEntity>{

    /**
     * 根据角色ID，获取菜单ID列表
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);

}
