package com.backend.dao;

import com.backend.entity.SysUserRoleEntity;

import java.util.List;

/**
 * @author Skynet
 * @date 2017年04月24日 17:00
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);
}
