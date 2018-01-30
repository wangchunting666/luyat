package com.luyat.common.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParamUtils {
	
	private static Logger logger = LogManager.getLogger(ParamUtils.class.getName());
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> generateMap(HttpServletRequest request) {
		try {
			ObjectMapper mapper = new ObjectMapper();  
			InputStream is = request.getInputStream();
			Map<String,Object> paramMap = mapper.readValue(is, Map.class);
			StringBuilder sqlWhere = new StringBuilder();
			Object filters = paramMap.get("filters");
			logger.debug("查询条件filters:"+filters);
			if(filters != null && !"".equals(filters.toString().trim())){
				JsonNode filterNode = mapper.readTree(filters.toString());
				JsonNode rulesNode = filterNode.get("rules");
				if(rulesNode != null){
					rulesNode.forEach(item -> {
						sqlWhere.append(
								ParamUtils.getSqlOp(item.get("op").asText(),item.get("field").asText(),item.get("data").asText())
						);
						sqlWhere.append(" ");
					});
				}
			}
			logger.debug("查询条件sqlWhere:"+sqlWhere.toString());
			paramMap.put("sqlWhere", sqlWhere.toString());
			return paramMap;
		} catch (Exception e) {
			logger.error("Param:request获取参数失败："+e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map<String,Object> generateOperMap(HttpServletRequest request) {
		try {
			Map<String,Object> paramMap = new HashMap<>();
			Enumeration enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();  
				paramMap.put(paraName, request.getParameter(paraName)); 
			}
			logger.debug("查询条件paramMap:"+paramMap);
			return paramMap;
		} catch (Exception e) {
			logger.error("Param:request获取参数失败："+e.getMessage());
			return null;
		}
	}
	
	public static String getSqlOp(String op,String name,String data){
		if("eq".equals(op) && data != null && !"".equals(data)) return " AND "+name + " = " + "'"+data+"'";
		if("ne".equals(op) && data != null && !"".equals(data)) return " AND "+name + " <> " + "'"+data+"'";
		if("lt".equals(op) && data != null && !"".equals(data)) return " AND "+name + " < " + "'"+data+"'";
		if("le".equals(op) && data != null && !"".equals(data)) return " AND "+name + " <= " + "'"+data+"'";
		if("gt".equals(op) && data != null && !"".equals(data)) return " AND "+name + " > " + "'"+data+"'";
		if("ge".equals(op) && data != null && !"".equals(data)) return " AND "+name + " >= " + "'"+data+"'";
		if("in".equals(op) && data != null && !"".equals(data)) return " AND "+name + " in " + "'"+data+"'";
		if("ni".equals(op) && data != null && !"".equals(data)) return " AND "+name + " not in " + "'"+data+"'";
		if("cn".equals(op) && data != null && !"".equals(data)) return " AND "+name + " like " + "'%"+data+"%'";
		if("nc".equals(op) && data != null && !"".equals(data)) return " AND "+name + " not like " + "'%"+data+"%'";
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getAttrByName(HttpSession session,String name){
		Map<String,Object> attr = (Map<String, Object>) session.getAttribute(name);
		return attr;
	}
}
