package com.luyat.demo.service.impl;

import com.luyat.common.result.ResultUtil;
import com.luyat.common.util.Md5Utils;
import com.luyat.demo.dao.DemoUserMapper;
import com.luyat.demo.service.DemoUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("userService")
public class DemoUserServiceImpl implements DemoUserService {
	private static Logger logger = LogManager.getLogger(DemoUserServiceImpl.class.getName());
	@Autowired
	private DemoUserMapper demoUserMapper;

	@Override
	public Map<String, Object> getDataService(Map<String, Object> paramMap) {
		try {
			int resultNum = demoUserMapper.countByWhere(paramMap);
			List<Map<String,Object>> resultList = demoUserMapper.dataByWhere(paramMap);
			return ResultUtil.retWhereMap(paramMap, resultNum, resultList);
		} catch (Exception e) {
			logger.error("查询列表失败", e);
			return null;
		}
	}
	@Override
	public int addDataService(Map<String, Object> paramMap) {
		try {
			String PWD = paramMap.get("PWD").toString();
			paramMap.put("PWD", Md5Utils.string2MD5(PWD));
			int num = demoUserMapper.insertData(paramMap);
			return num;
		} catch (Exception e) {
			logger.error("用户添加失败", e);
			return 0;
		}
	}
	@Override
	public int uptDataService(Map<String, Object> paramMap) {
		try {
			int num = demoUserMapper.updateData(paramMap);
			return num;
		} catch (Exception e) {
			logger.error("用户修改失败", e);
			return 0;
		}
	}
	@Override
	public int delDataService(Map<String, Object> paramMap) {
		try {
			int num = demoUserMapper.deleteData(paramMap);
			return num;
		} catch (Exception e) {
			logger.error("用户删除失败", e);
			return 0;
		}
	}
	@Override
	public List<Map<String, Object>> getRoleService(Map<String, Object> paramMap) {
		try {
			List<Map<String,Object>> resultList = demoUserMapper.selectRoleCombox(paramMap);
			return resultList;
		} catch (Exception e) {
			logger.error("角色下拉查询失败", e);
			return null;
		}
	}

}
