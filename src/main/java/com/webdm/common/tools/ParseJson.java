package com.webdm.common.tools;
//package com.tj.hadoop.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.alibaba.fastjson.JSONObject;
//
//public class ParseJson {
//
//private static final String URL = "http://tv.sohu.com/frag/vrs_inc/phb_zh_tv_day_50.js";
///**
//http://tv.sohu.com/frag/vrs_inc/phb_zh_mv_day_50.js 
//http://tv.sohu.com/frag/vrs_inc/phb_zh_tv_day_50.js 
//*/
//public static List getList(String url) {
//	List list=null;
//	try {
//		
//		String json = HttpClientUtil.httpReader(url,"GBK");
//		//System.out.println(json);
//		//System.out.println(json.substring(21));
//		if (StringUtils.isNotEmpty(json)) {
//			JSONObject jsonObj = JSONObject.fromObject(json.substring(21));		
//			JSONArray jsonArray = JSONArray.fromObject(jsonObj.get("videos"));
//			//System.out.println(jsonArray.size());
//					
//			try {
//				if (jsonArray != null && jsonArray.size() != 0) {
//					list =new ArrayList();
//					for(int i=0;i<jsonArray.size();i++){
//					    JSONObject jsonArrayObj = JSONObject.fromObject(jsonArray.get(i));
//						String tv_name=jsonArrayObj.getString("tv_name"); 
//	                    String tv_count=jsonArrayObj.getString("tv_count"); 
//	                    String tv_url=jsonArrayObj.getString("tv_url"); 
//	                    System.out.println(tv_name);
//						System.out.println(tv_count);
//						System.out.println(tv_url);
//						list.add(tv_name+";"+tv_count+";"+tv_url);						
//					}
//				}
//				return list;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return list;
//}
//
///**
// * @param args
// */
//	public static void main(String[] args) {
//		getList(URL);
//	}
//}

