package com.luyat.common.util;

import java.security.MessageDigest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Md5Utils {
	private static Logger logger = LogManager.getLogger(Md5Utils.class.getName());
	/**
	 *  MD5加码 生成32位md5码 
	 * @param inStr
	 * @return
	 */
    public static String string2MD5(String inStr){ 
    	try {
    		 MessageDigest md5 = MessageDigest.getInstance("MD5");    
    	     char[] charArray = inStr.toCharArray();  
    	     byte[] byteArray = new byte[charArray.length];  
    	     for (int i = 0; i < charArray.length; i++) byteArray[i] = (byte) charArray[i];  
    	     byte[] md5Bytes = md5.digest(byteArray);  
    	     StringBuffer hexValue = new StringBuffer();  
    	     for (int i = 0; i < md5Bytes.length; i++){  
    	          int val = ((int) md5Bytes[i]) & 0xff;  
    	          if (val < 16) hexValue.append("0");  
    	          hexValue.append(Integer.toHexString(val));  
    	     }  
    	     return hexValue.toString(); 
		} catch (Exception e) {
			logger.error("MD5加密失败:"+e.getStackTrace());
			return "";
		} 
    } 
}
