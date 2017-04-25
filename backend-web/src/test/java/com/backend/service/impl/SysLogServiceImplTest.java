package com.backend.service.impl;

import com.backend.entity.SysLogEntity;
import com.backend.service.SysLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Skynet
 * @date 2017年04月24日 00:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-*.xml"})
public class SysLogServiceImplTest {

    @Autowired
    @Qualifier("sysLogService")
    private SysLogService sysLogService;

    @Test
    public void queryObject() throws Exception {
        Long id = 1L;
        SysLogEntity sysLogEntity = sysLogService.queryObject(id);
        Assert.assertNotNull(sysLogEntity);
        System.out.println(sysLogEntity);
    }

    @Test
    public void queryList() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("key","delete");
        List<SysLogEntity> sysLogEntities = sysLogService.queryList(map);
        for (SysLogEntity sysLogEntity : sysLogEntities) {
            System.out.println(sysLogEntity);
        }
    }

    @Test
    public void queryTotal() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "sky");
        int count = sysLogService.queryTotal(map);
        System.out.println(count);
    }

    @Test
    public void save() throws Exception {
        SysLogEntity syslog = new SysLogEntity();
        InetAddress addr = InetAddress.getLocalHost();
        syslog.setUsername("tomcat");
        syslog.setOperation("delete");
        syslog.setMethod("com.backend.delete");
        syslog.setParams("333");
        syslog.setIp(addr.getHostAddress());
        sysLogService.save(syslog);
    }

    @Test
    public void update() throws Exception {
        SysLogEntity sysLog = sysLogService.queryObject(1L);
        sysLog.setUsername("Jerry");
        sysLogService.update(sysLog);
    }

    @Test
    public void delete() throws Exception {
        sysLogService.delete(2L);
    }

    @Test
    public void deleteBatch() throws Exception {
        Long[] ids = {3L,5L};
        sysLogService.deleteBatch(ids);
    }

}