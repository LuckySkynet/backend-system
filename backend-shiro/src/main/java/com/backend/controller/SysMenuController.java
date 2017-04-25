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
        List<SysMenuEntity> menuList = sysMenuService.queryList(query);
        int total = sysMenuService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(total, query.getLimit(), query.getPage(), menuList);
        return Result.ok().put("page", pageUtil);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return Result.ok().put("menuList", menuList);
    }

    /**
     * 角色授权菜单
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    public Result perms() {
        //查询列表数据
        List<SysMenuEntity> menuList = null;

        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() == Constant.SUPER_ADMIN) {
            menuList = sysMenuService.queryList(new HashMap<String, Object>());
        } else {
            menuList = sysMenuService.queryUserPermsList(getUserId());
        }
        return Result.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity sysMenu = sysMenuService.queryObject(menuId);
        return Result.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenuEntity sysMenu) {
        //数据校验
        verifyForm(sysMenu);
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody SysMenuEntity sysMenu){
        //数据校验
        verifyForm(sysMenu);
        sysMenuService.update(sysMenu);
        return Result.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(@RequestBody Long[] menuIds){
        for (Long menuId : menuIds) {
            if (menuId.longValue() <= 30) {
                return Result.error("系统菜单，不能删除");
            }
        }
        sysMenuService.deleteBatch(menuIds);
        return Result.ok();
    }

    /**
     * 用户菜单列表
     */
    @RequestMapping("/user")
    public Result user(){
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        return Result.ok().put("menuList", menuList);
    }

    /**
     * 验证参数是否正确
     */
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

        //上级菜单类型
        int parentType = MenuType.CATALOG.getValue();
        if (sysMenu.getParentId() != 0) {
            SysMenuEntity parentSysMenu = sysMenuService.queryObject(sysMenu.getParentId());
            parentType = parentSysMenu.getType();
        }

        //目录、菜单
        if(sysMenu.getType() == MenuType.CATALOG.getValue() ||
                sysMenu.getType() == MenuType.MENU.getValue()){
            if (parentType != MenuType.CATALOG.getValue()) {
                throw new BEException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if(sysMenu.getType() == MenuType.BUTTON.getValue()){
            if(parentType != MenuType.MENU.getValue()){
                throw new BEException("上级菜单只能为菜单类型");
            }
            return ;
        }

    }


}
