package com.zixue.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WeixinNewsUtil {
	
	public static void sendtext(HttpServletRequest request,HttpServletResponse response,String text){
//		MenuResponseMessage menuResponseMessage=(MenuResponseMessage)request.getAttribute("MenuResponseMessage");
		StringBuilder out = new StringBuilder();
		out.append("\n<xml>\n");
//		out.append("	<ToUserName><![CDATA[").append(menuResponseMessage.getToUserName()).append("]]></ToUserName>\n");
//		out.append("	<FromUserName><![CDATA[").append(menuResponseMessage.getFromUserName()).append("]]></FromUserName>\n");
		out.append("	<MsgType><![CDATA[").append("text").append("]]></MsgType>\n");
		out.append("	<CreateTime><![CDATA[").append(new Date()).append("]]></CreateTime>\n");
		out.append(" <Content><![CDATA[").append(text).append("]]></Content>");
		out.append("</xml>\n");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			response.getWriter().print(out);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void sendnews(HttpServletRequest request,HttpServletResponse response,List<Map<String,Object>> newsList){
//		MenuResponseMessage menuResponseMessage=(MenuResponseMessage)request.getAttribute("MenuResponseMessage");
		StringBuilder out = new StringBuilder();
		out.append("\n<xml>\n");
//		out.append("	<ToUserName><![CDATA[").append(menuResponseMessage.getToUserName()).append("]]></ToUserName>\n");
//		out.append("	<FromUserName><![CDATA[").append(menuResponseMessage.getFromUserName()).append("]]></FromUserName>\n");
		out.append("	<MsgType><![CDATA[").append("news").append("]]></MsgType>\n");
		out.append("	<CreateTime><![CDATA[").append(new Date()).append("]]></CreateTime>\n");
		
		out.append("  <ArticleCount>").append(newsList.size()).append("</ArticleCount>\n");
		out.append("  <Articles>\n");
		for(Map<String,Object> map:newsList){
			out.append("  <item>\n");
			out.append("    <Title><![CDATA[").append(String.valueOf(map.get("title"))).append("]]></Title>\n");
			out.append("    <Description><![CDATA[").append(String.valueOf(map.get("description"))).append("]]></Description>\n");
			out.append("    <PicUrl><![CDATA[").append(String.valueOf(map.get("picurl"))).append("]]></PicUrl>\n");
			out.append("    <Url><![CDATA[").append(String.valueOf(map.get("newsurl"))).append("]]></Url>\n");
			out.append("  </item>\n");
		}
		out.append("  </Articles>\n");
		
		out.append("</xml>\n");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			System.out.println(out);
			response.getWriter().print(out);
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendnew(HttpServletRequest request,HttpServletResponse response,List<Map<String,Object>> newsList,String toUserName,String fromUserName ){
		StringBuilder out = new StringBuilder();
		out.append("\n<xml>\n");
		out.append("	<ToUserName><![CDATA[").append(fromUserName).append("]]></ToUserName>\n");
		out.append("	<FromUserName><![CDATA[").append(toUserName).append("]]></FromUserName>\n");
		out.append("	<MsgType><![CDATA[").append("news").append("]]></MsgType>\n");
		out.append("	<CreateTime><![CDATA[").append(new Date()).append("]]></CreateTime>\n");
		
		out.append("  <ArticleCount>").append(newsList.size()).append("</ArticleCount>\n");
		out.append("  <Articles>\n");
		for(Map<String,Object> map:newsList){
			out.append("  <item>\n");
			out.append("    <Title><![CDATA[").append(String.valueOf(map.get("title"))).append("]]></Title>\n");
			out.append("    <Description><![CDATA[").append(String.valueOf(map.get("description"))).append("]]></Description>\n");
			out.append("    <PicUrl><![CDATA[").append(String.valueOf(map.get("picurl"))).append("]]></PicUrl>\n");
			out.append("    <Url><![CDATA[").append(String.valueOf(map.get("newsurl"))).append("]]></Url>\n");
			out.append("  </item>\n");
		}
		out.append("  </Articles>\n");
		
		out.append("</xml>\n");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			System.out.println(out);
			response.getWriter().print(out);
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
