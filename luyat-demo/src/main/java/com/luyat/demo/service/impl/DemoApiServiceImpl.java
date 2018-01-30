package com.luyat.demo.service.impl;

import com.luyat.demo.dao.DemoApiMapper;
import com.luyat.demo.service.DemoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Herdsric-M-003 on 2018/1/26.
 */
@Service
public class DemoApiServiceImpl implements DemoApiService{
    @Autowired
    private DemoApiMapper demoApiMapper;

    @Override
    public Map<String, Object> getUserByName(Map<String, Object> paramMap) {
        return demoApiMapper.selectUserByName(paramMap);
    }

    @Override
    public List<Map<String, Object>> getPermissionByName(Map<String, Object> paramMap) {
        return demoApiMapper.selectPermissionByName(paramMap);
    }

    @Override
    public List<Map<String, Object>> getRoleByName(Map<String, Object> paramMap) {
        return demoApiMapper.selectRoleByName(paramMap);
    }

    @Override
    public List<Map<String, Object>> getMenusByName(Map<String, Object> paramMap) {
        return demoApiMapper.selectMenusByName(paramMap);
    }
}
