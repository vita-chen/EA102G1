package com.car.config;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletContext;

public class Config {
	private ServletContext servletContext;
	private String path;
	
	public Config(ServletContext servletContext) {
		this.servletContext = servletContext;
		this.path = "/WEB-INF/config.json";
	}
	
	public Map<String, String> get(String key1, String key2) {
		Map<String, String> map = null;
		InputStream is = null;
		Scanner sc = null;
		StringBuffer sb = null;
		try {
			// 載入設定檔
			is = servletContext.getResourceAsStream(path);
			sc = new Scanner(is);
			sb = new StringBuffer();
			while(sc.hasNext()) {
				sb.append(sc.nextLine());
		    }
			if (sc != null) sc.close();
	        if (is != null) is.close();
		
	        // Json 字串 轉成 Map
	        Gson gson = new Gson();
	        Map<String, Map<String, Map<String, String>>> allMap = gson.fromJson(sb.toString(), new TypeToken<Map<String, Map<String, Map<String, String>>>>() {}.getType());
	        //System.out.println(allMap.get("carExt").get("cextStatusMap").get("1"));
	        //System.out.println(allMap.get("carOrder").get("codStatusMap").get("1"));
			//System.out.println(allMap.get("car").get("cstatusMap").get("1"));
	        map = allMap.get(key1).get(key2);
		} catch(Exception e){
	        e.printStackTrace();
		}	
		return map;
	}
}
