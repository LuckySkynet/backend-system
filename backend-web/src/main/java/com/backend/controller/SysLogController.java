package com.backend.controller;

import com.backend.entity.SysLogEntity;
import com.backend.service.SysLogService;
import com.backend.utils.PageUtils;
import com.backend.utils.Query;
import com.backend.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 *
 * @author Skynet
 * @date 2017年04月24日 15:24
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public Result list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<SysLogEntity> sysLogList = sysLogService.queryList(query);
        int total = sysLogService.queryTotal(query);

        PageUtils pageUtils = new PageUtils(total, query.getLimit(), query.getPage(), sysLogList);

        return Result.ok().put("page", pageUtils);
    }

}
