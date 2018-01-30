package com.luyat.demo.dao;

import java.util.List;
import java.util.Map;

public interface DemoUserMapper {
	List<Map<String,Object>> dataByWhere(Map<String, Object> paramMap);
	int countByWhere(Map<String, Object> paramMap);
	int updateData(Map<String, Object> paramMap);
	int deleteData(Map<String, Object> paramMap);
	int insertData(Map<String, Object> paramMap);
	List<Map<String,Object>> selectRoleCombox(Map<String, Object> paramMap);
}
