package com.backend.service.impl;

import com.backend.entity.SysLogEntity;
import com.backend.service.SysLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;

/**
 * @author Skynet
 * @date 2017年04月24日 00:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-jdbc.xml"})
public class SysLogServiceImplTest {

    @Autowired
    @Qualifier("sysLogService")
    private SysLogService sysLogService;

    @Test
    public void queryObject() throws Exception {

    }

    @Test
    public void queryList() throws Exception {

    }

    @Test
    public void queryTotal() throws Exception {

    }

    @Test
    public void save() throws Exception {
        SysLogEntity syslog = new SysLogEntity();
        InetAddress addr = InetAddress.getLocalHost();
        syslog.setUsername("skynet");
        syslog.setOperation("insert");
        syslog.setMethod("com.backend.insert");
        syslog.setParams("123");
        syslog.setIp(addr.getHostAddress());
        sysLogService.save(syslog);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void deleteBatch() throws Exception {

    }

}