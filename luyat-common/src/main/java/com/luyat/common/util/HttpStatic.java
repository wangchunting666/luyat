package com.luyat.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpStatic {
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {in.close();}
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
   }
   public static void main(String[] args) {
   	  String cc = HttpStatic.sendGet("https://graph.qq.com/oauth2.0/me?access_token=11111111111111111111111111111111");
   	  System.out.println(cc.replaceAll("callback|\\(|\\)", ""));
   }
}
