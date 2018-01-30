package com.luyat.common.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultObj {
	public ResultObj(ResultStatus result,String text,int code,Map<String,Object> data){
		this.result = result;
		this.text = text;
		this.code = code;
		this.data = data;
		if(data == null) this.data = new HashMap<>();
	}
	public ResultObj(ResultStatus result,int code,List<Map<String,Object>> data){
		this.result = result;
		this.code = code;
		this.dataList = data;
		if(data == null) this.dataList = new ArrayList<>();
	}
	private ResultStatus result;
	private String text;
	private int code;
	private Map<String,Object> data;
	private List<Map<String,Object>> dataList;
	
	@Override
	public String toString() {
		return this.result+","+this.text+","+this.code+","+this.data.toString();
	}
	
	public Map<String,Object> getMap(){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", this.result);
		resultMap.put("text", this.text);
		resultMap.put("code", this.code);
		resultMap.put("data", this.data);
		return resultMap;
	}
	
	public Map<String,Object> getMapList(){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", this.result);
		resultMap.put("code", this.code);
		resultMap.put("data", this.dataList);
		return resultMap;
	}
	
	public Map<String,Object> getCodeMap(){
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", this.result);
		resultMap.put("text", this.text);
		resultMap.put("code", this.data);
		if(this.data == null || this.data.size() == 0){
			resultMap.put("code", this.code);
		}
		return resultMap;
	}
}	
