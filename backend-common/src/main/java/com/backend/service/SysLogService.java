package com.backend.service;

import com.backend.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Skynet
 * @date 2017年04月22日 01:30
 */
public interface SysLogService {

    SysLogEntity queryObject(Long id);

    List<SysLogEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysLogEntity sysLog);

    void update(SysLogEntity sysLog);

    void delete(Long id);

    void deleteBatch(Long[] ids);

}
