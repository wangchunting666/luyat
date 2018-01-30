package com.luyat.demo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Herdsric-M-003 on 2018/1/26.
 */
public interface DemoApiService {
    public Map<String,Object> getUserByName(Map<String,Object> paramMap);
    public List<Map<String,Object>> getPermissionByName(Map<String,Object> paramMap);
    public List<Map<String,Object>> getRoleByName(Map<String,Object> paramMap);
    public List<Map<String,Object>> getMenusByName(Map<String,Object> paramMap);
}
