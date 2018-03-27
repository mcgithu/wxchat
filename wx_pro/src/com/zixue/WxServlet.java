//package com.zixue;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.zixue.service.CoreService;
//import com.zixue.util.PropsParam;
//import com.zixue.util.PropsUtil;
//import com.zixue.util.SHA1;
//import com.zixue.util.WeChatUtil;
//
///**
// *接入配置
// * @author lenovo
// **/
//public class WxServlet extends HttpServlet{
//	PropsUtil props = new PropsUtil();
//	WeChatUtil weChatUtil = new WeChatUtil();
//	 @Override
//	 @RequestMapping(value="/wx_pro/wx",method=RequestMethod.GET)
//	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		 //设置编码格式
//		 req.setCharacterEncoding("utf-8");
//		 resp.setCharacterEncoding("utf-8");
//		 StringBuffer requestURL = req.getRequestURL();
//		 System.out.println("requestURL="+requestURL);
//		 //写入
//		 PrintWriter PrintWriter = resp.getWriter();
//		 //链接
//		 connect(req,PrintWriter);
//	}
//
//	private void connect(HttpServletRequest req, PrintWriter resp) {
//		
//		
//		//获取参数
//		String access_token = req.getParameter("access_token");
//		String signature = req.getParameter("signature");
//		String timestamp = req.getParameter("timestamp");
//		String nonce = req.getParameter("nonce");
//		String echostr = req.getParameter("echostr");
//		
//		//拼接参数
//		List<String> list = new ArrayList<String>();
//		list.add(PropsUtil.getProperty(PropsParam.WEIXIN_TOKEN));
//		list.add(timestamp);
//		list.add(nonce);
//		
//		//排序
//		Collections.sort(list);
//		
//		//加密
//		StringBuffer buffer = new StringBuffer();
//		for (String string : list) {
//			buffer.append(string);
//		}
//		String sha = SHA1.encode(buffer.toString());
//		System.out.println("sha:"+sha);
//		System.out.println("signature:"+signature);
//		System.out.println("access_token:"+PropsUtil.getProperty(PropsParam.WEIXIN_TOKEN));
//		
//		//对比 不分区大小写
//		boolean flag = signature.equalsIgnoreCase(sha);
//		if(flag) {
//			System.out.println("接入成功");
//			resp.print(echostr);
//			resp.flush();
//		}
//		
//	}
//	@Override
//	@RequestMapping(value="/wx_pro/wx",method=RequestMethod.POST)
//	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		 // 消息的接收、处理、响应
//        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
//
//        // 调用核心业务类接收消息、处理消息
//        String respXml = CoreService.processRequest(req);
//
//        // 响应消息
//        PrintWriter out = resp.getWriter();
//        out.print(respXml);
//        out.close();
//
//	}
//}
