package com.backend.controller;

import com.backend.annotation.SysLog;
import com.backend.entity.SysUserEntity;
import com.backend.service.SysUserRoleService;
import com.backend.service.SysUserService;
import com.backend.utils.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Skynet
 * @date 2017年04月25日 11:06
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        if (getUserId() != Constant.SUPER_ADMIN) {
            //只有超级管理员，才能查看所有管理员列表
            params.put("createUserId", getUserId());
        }

        //查询列表数据
        Query query = new Query(params);
        List<SysUserEntity> sysUserList = sysUserService.queryList(query);
        int total = sysUserService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(total, query.getLimit(), query.getPage(), sysUserList);
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public Result info() {
        return Result.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public Result password(String password, String newPassword) {
        if (StringUtils.isBlank(newPassword)) {
            throw new BEException("新密码不能为空");
        }

        //sha256加密
        password = new Sha256Hash(password).toHex();
        //sha256加密
        newPassword = new Sha256Hash(newPassword).toHex();

        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            return Result.error("原密码不正确");
        }

        //退出
        ShiroUtils.logout();
        return Result.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.queryObject(userId);

        //获取用户的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return Result.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserEntity user) {
        user.setCreateUserId(getUserId());
        sysUserService.save(user);
        return Result.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUserEntity user) {
        user.setCreateUserId(getUserId());
        sysUserService.update(user);
        return Result.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return Result.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return Result.ok();
    }


}
