package com.luyat.demo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Herdsric-M-003 on 2018/1/26.
 */
public interface DemoApiMapper {
    Map<String,Object> selectUserByName(Map<String,Object> paramMap);
    List<Map<String,Object>> selectPermissionByName(Map<String,Object> paramMap);
    List<Map<String,Object>> selectRoleByName(Map<String,Object> paramMap);
    List<Map<String,Object>> selectMenusByName(Map<String,Object> paramMap);
}
