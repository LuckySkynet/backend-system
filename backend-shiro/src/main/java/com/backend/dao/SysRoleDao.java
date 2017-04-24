package com.backend.dao;

import com.backend.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色管理
 *
 * @author Skynet
 * @date 2017年04月24日 16:50
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     * @param createUserId
     * @return
     */
    List<Long> queryRoleIdList(Long createUserId);

}
