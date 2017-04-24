package com.backend.controller;


import com.backend.annotation.SysLog;
import com.backend.entity.SysMenuEntity;
import com.backend.service.SysMenuService;
import com.backend.utils.*;
import com.backend.utils.Constant.MenuType;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Skynet
 * @date 2017年04月24日 23:06
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysMenuEntity> sysMenuList = sysMenuService.queryList(query);
        int total = sysMenuService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(total, query.getLimit(), query.getPage(), sysMenuList);
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select() {
        //查询列表数据
        List<SysMenuEntity> sysMenuList = sysMenuService.queryNotButtonList();

        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        sysMenuList.add(root);
        return Result.ok().put("sysMenuList", sysMenuList);
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    public Result perms() {
        //查询列表数据
        List<SysMenuEntity> sysMenuList = null;

        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() == Constant.SUPER_ADMIN) {
            sysMenuList = sysMenuService.queryList(new HashMap<String, Object>());
        } else {
            sysMenuList = sysMenuService.queryUserPermsList(getUserId());
        }
        return Result.ok().put("sysMenuList", sysMenuList);
    }

    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity sysMenu = sysMenuService.queryObject(menuId);
        return Result.ok().put("sysMenu", sysMenu);
    }

    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenuEntity sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    private void verifyForm(SysMenuEntity sysMenu) {
        if (StringUtils.isBlank(sysMenu.getName())) {
            throw new BEException("菜单名称不能为空");
        }
        if (sysMenu.getParentId() == null) {
            throw new BEException("上级菜单不能为空");
        }

        //菜单
        if (sysMenu.getType() == MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(sysMenu.getUrl())) {
                throw new BEException("菜单URL不能为空");
            }
        }
    }


}
