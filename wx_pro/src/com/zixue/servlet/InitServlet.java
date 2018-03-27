package com.zixue.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zixue.util.CommonUtil;
import com.zixue.util.PropsParam;
import com.zixue.util.PropsUtil;
import com.zixue.util.TokenThread;

/**
* 类名: InitServlet </br>
* 描述: 初始化servlet </br>
* 开发人员： souvc </br>
* 创建时间：  Oct 6, 2015 </br>
* 发布版本：V1.0  </br>
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public void init() throws ServletException {
        // 获取web.xml中配置的参数
//        TokenThread.appid = getInitParameter("appid");
//        TokenThread.appsecret = getInitParameter("appsecret");
        TokenThread.appid = PropsUtil.getProperty(PropsParam.WEIXIN_APPID);
        TokenThread.appsecret = PropsUtil.getProperty(PropsParam.WEIXIN_APPSECRET);
        log.info("weixin api appid:{}", TokenThread.appid);
        log.info("weixin api appsecret:{}", TokenThread.appsecret);

        // 未配置appid、appsecret时给出提示
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
            log.error("appid and appsecret configuration error, please check carefully.");
        } else {
            // 启动定时获取access_token的线程
            new Thread(new TokenThread()).start();
        }
    }
}