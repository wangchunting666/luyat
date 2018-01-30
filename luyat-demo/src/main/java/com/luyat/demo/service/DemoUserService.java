package com.luyat.demo.service;

import java.util.List;
import java.util.Map;

public interface DemoUserService {
	public Map<String,Object> getDataService(Map<String, Object> paramMap);
	public int addDataService(Map<String, Object> paramMap);
	public int uptDataService(Map<String, Object> paramMap);
	public int delDataService(Map<String, Object> paramMap);
	public List<Map<String,Object>> getRoleService(Map<String, Object> paramMap);
}
