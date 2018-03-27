package com.zixue.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zixue.enty.WeixinAccount;

public class PropsUtil {
	private static Properties props = new Properties();
	private WeixinAccount weixinAccount;

	private String access_token;

	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	public void setWeixinAccount(WeixinAccount weixinAccount) {
		access_token = null;
		this.weixinAccount = weixinAccount;
	}
	static{
		InputStream in = null ;
		try {
			in = PropsUtil.class.getClassLoader().getResourceAsStream("config.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(in != null){
				try{
					in.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getProperty(String key){
		return props.getProperty(key);
	}

}
