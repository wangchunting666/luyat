package com.luyat.common.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultUtil {
	/**
	 * 返回数据
	 * @param paramMap
	 * @param resultNum
	 * @param resultList
	 * @return
	 */
	public static Map<String,Object> retWhereMap(Map<String,Object> paramMap,
			int resultNum,List<Map<String,Object>> resultList){
		int limit = Integer.valueOf(paramMap.get("limit").toString());
		int page  = Integer.valueOf(paramMap.get("page").toString());
		int pageNum = resultNum / limit;
		if(resultNum % limit != 0) pageNum = pageNum+1;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("total", pageNum);dataMap.put("rows", resultList);
		dataMap.put("page", page);dataMap.put("records", resultNum);
		return dataMap;
	}
}
