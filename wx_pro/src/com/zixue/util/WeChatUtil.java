package com.zixue.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.zixue.enty.WeixinAccount;
import com.zixue.exception.WeixinException;

/**
 * 调用微信接口服务工具类
 * 
 * @author lwgang
 * @createTime 2015-02-03
 * @history 1.修改时间,修改;修改内容：
 * 
 */
public class WeChatUtil {
	//发送客服消息
	public static final String WEIXIN_MESSAGE_CUSTOM_SEND_SUFFIX = "weixin_message_custom_send_suffix";
//	private static Logger logger = Logger.getLogger(WeChatUtil.class);
	
	private WeixinAccount weixinAccount;

	private Object lock = new Object();

	private String access_token;

	public WeChatUtil() {
		super();
	}

	public WeChatUtil(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public WeixinAccount getWeixinAccount() {
		return weixinAccount;
	}

	public void setWeixinAccount(WeixinAccount weixinAccount) {
		access_token = null;
		this.weixinAccount = weixinAccount;
	}
	
//	/**
//	 * 发送客服文本消息
//	 * 
//	 * @param open_id
//	 * @param content
//	 * @throws WeixinException
//	 * @throws JSONException
//	 */
//	public void sendCustomTextMsg(String open_id, String content)
//			throws WeixinException, JSONException {
//		JSONObject json = new JSONObject();
//		json.put("touser", open_id);
//		json.put("msgtype", "text");
//		content = content.replaceAll("&amp;", "&");
//		json.put("text", new JSONObject().put("content", content));
//		json = doPostJson(PropsParam.WEIXIN_MESSAGE_CUSTOM_SEND_SUFFIX,1, json.toString());
//		if (!JsonUtil.isRetSuccess(json)) {
//			throw new WeixinException("[errcode:" + json.optString("errcode")
//					+ "]:" + json.optString("errmsg"));
//		}
//	}
//	
	/**
	 * 网络访问公共方法(post方式)
	 * 
	 * @param suffix
	 * @param suffix 
	 * @param jsonObject
	 * @param objs
	 *            第一个参数存放access_token
	 * @return
	 * @throws WeixinException
	 * @throws JSONException
	 */
	private JSONObject doPostJson(String type, int suffix, String jsonStr)
			throws WeixinException, JSONException {
		synchronized (lock) {
			if (access_token == null)
				getClient_credential();
			String url = type2uri(type,suffix);
			url = String.format(url, access_token);
			String data = jsonStr;
			SSLNetProvider provider = new SSLNetProvider();
			url=url.replace("null", "");
			String result = provider.doPost(url, data);
			JSONObject json = new JSONObject(result);
			if (!checkAccess_token(json)) {// access_token过期
				System.out.println("-------token过期,重新获取----------");
				getClient_credential();
				url = type2uri(type,suffix);
				url = String.format(url, access_token);
				result = provider.doPost(url, data).replace("null", "");
				json = new JSONObject(result);
			}
			System.out.println("url:" + url);
			return json;
		}
	}
	
	/**
	 * 获取access token
	 * 
	 * @throws WeixinException
	 * @throws JSONException
	 */
	public void getClient_credential() throws WeixinException, JSONException {
	
		String url = type2uri(PropsParam.WEIXIN_TOKEN_SUFFIX);
		url = String.format(url, PropsUtil.getProperty(PropsParam.WEIXIN_APPID), PropsUtil.getProperty(PropsParam.WEIXIN_APPSECRET));
		SSLNetProvider provider = new SSLNetProvider();
		String result = provider.doGet(url).replace("null", "");
		JSONObject json = new JSONObject(result);
		access_token = json.optString("access_token");
		System.out.println("--------------------------------access_token:"
				+ access_token);
		if (access_token!=null && !"".equals(access_token)) {
			System.out.println("--------------------------------access_token:"
					+ access_token);
		} else {
			throw new WeixinException("[errcode:" + json.optString("errcode")
					+ "]:" + json.optString("errmsg"));
		}
	}
	
	/**
	 * 获取完整的请求微信服务的URL
	 * 
	 * @param type
	 * @return
	 */
	private String type2uri(String type) {
		return PropsUtil.getProperty(PropsParam.WEIXIN_SERVER_PREFIX)
				+ PropsUtil.getProperty(type);
	}
	
	/**
	 * 获取完整的请求微信服务的URL
	 * lhyan3
	 * 2015年5月20日下午2:56:47
	 * TODO
	 * @param type
	 * @param suffix
	 * @return
	 */
	private String type2uri(String type, int suffix) {
		if(suffix!=0){
			return PropsUtil.getProperty(PropsParam.WEIXIN_SERVER_PREFIX)
					+ PropsUtil.getProperty(type);
		}else{
			return PropsUtil.getProperty(type);
		}
	}
	
	/**
	 * 判断access_token是否超时
	 * 
	 * @param json
	 * @return
	 */
	private boolean checkAccess_token(JSONObject json) {
		int ret_code = json.optInt("errcode", -1);
		if (ret_code == 42001 || ret_code == 40001)
			return false;
		return true;
	}
	
	
//	/**
//	 * 发送客服图片消息
//	 * 
//	 * @param open_id
//	 * @param content
//	 * @throws WeixinException
//	 * @throws JSONException
//	 */
//	public void sendCustomImageMsg(String open_id, String media_id)
//			throws WeixinException, JSONException {
//		JSONObject json = new JSONObject();
//		json.put("touser", open_id);
//		json.put("msgtype", "image");
//		json.put("image", new JSONObject().put("media_id", media_id));
//		json = doPostJson(PropsParam.WEIXIN_MESSAGE_CUSTOM_SEND_SUFFIX,1, json.toString());
//		if (!JsonUtil.isRetSuccess(json)) {
//			throw new WeixinException("[errcode:" + json.optString("errcode")
//					+ "]:" + json.optString("errmsg"));
//		}
//		
//	}
//	
//	/**上传临时素材*/
//	public JSONObject  uploadTemporaryMedia(String accountid,String fileType, File file) throws Exception{
//		String suffix = PropsParam.WEIXIN_IMAGE_CUSTOM_SEND_SUFFIX;
//		String url = type2uri(suffix);
//		if (access_token == null){
//			getClient_credential();
//		}
//		url=String.format(url,access_token,fileType);
//		System.out.println("url:"+url);
//		SSLNetProvider provider=new SSLNetProvider();
//		JSONObject jsonObject = provider.send(url,fileType,file.getPath());
//		return jsonObject;
//	}
	
//	public static JSONObject getTempMedia(String accountid, String mediaId) throws Exception {
//		JSONObject json = new JSONObject();
//		json.put("touser", open_id);
//		json.put("msgtype", "image");
//		json.put("image", new JSONObject().put("media_id", media_id));
//		json = doPostJson(PropsParam.WEIXIN_MESSAGE_CUSTOM_SEND_SUFFIX,1, json.toString());
//		if (!JsonUtil.isRetSuccess(json)) {
//			throw new WeixinException("[errcode:" + json.optString("errcode")
//					+ "]:" + json.optString("errmsg"));
//		}
//		
//		
//			String url=PropsUtil.getProperty(PropsParam.WEIXIN_GET_MATERIAL_TEMP)+"&media_id="+mediaId;
//			url=String.format(url,access_token,fileType);
//			logger.info("getTempMedia url:"+url);
//			SSLNetProvider provider=new SSLNetProvider();
//			JSONObject jsonObject= provider.send(url,fileType,file.getPath());
//		return jsonObject;
//	}
}