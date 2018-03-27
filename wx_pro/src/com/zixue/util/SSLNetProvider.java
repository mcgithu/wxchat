package com.zixue.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.apache.naming.resources.jndi.Handler;
import org.json.JSONObject;

import com.zixue.exception.WeixinException;

public class SSLNetProvider {
	private static HttpClient httpClient = null;
	private static Integer statusCode = 0;
	private static String charSet="UTF-8";
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A',

			'B', 'C', 'D', 'E', 'F' };

	private static Logger logger = Logger.getLogger(SSLNetProvider.class);

	private class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	public String doGet(String url)throws WeixinException{
		logger.info("doGet url=" + url);
		httpClient = new HttpClient();
		httpClient.getHostConfiguration().setProxy("14.18.161.18",443);
		httpClient.getParams().setAuthenticationPreemptive(true);
		GetMethod method = null;
		method = new GetMethod(url);
		try {
			setProxy();
			statusCode = httpClient.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.info(Boolean.FALSE);
				logger.info("服务器繁忙，请稍等···");
			} else {
				String searchResult = "";
				logger.info(Boolean.TRUE);
				BufferedInputStream ins = new BufferedInputStream(
						method.getResponseBodyAsStream());
				byte resultBytes[] = readUrlStream(ins);
				if (resultBytes != null && resultBytes.length > 0) {
					searchResult = new String(resultBytes, charSet);
				}
				logger.info(searchResult);
				return searchResult;
			}
		} catch (HttpException e) {
			logger.error("HttpException", e);
			throw new WeixinException("链接微信服务器失败");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
			throw new WeixinException("从微信服务器读取数据失败");
		} catch (IOException e) {
			logger.error("IOException", e);
			throw new WeixinException("从微信服务器读取数据失败");
		}
		method.releaseConnection();
		return null;
	}

	private static byte[] readUrlStream(BufferedInputStream bufferedInputStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100]; // buff用于存放循环读取的临时数据
		int rc = 0;
		while ((rc = bufferedInputStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}

		return swapStream.toByteArray();
	}

	public String doGet2(String url) throws WeixinException {
		logger.info("doGet url=" + url);
		InputStream in = null;
		HttpsURLConnection conn = null;
		try {
			// SSLContext sc = SSLContext.getInstance("SSL");
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new SecureRandom());
			URL console = new URL(null, url, new Handler());
			Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("14.18.161.18",443));
			conn = (HttpsURLConnection) console.openConnection(proxy);
			conn.setSSLSocketFactory(sc.getSocketFactory());
			// conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String ret = null;
			String str_return = "";
			while ((ret = br.readLine()) != null) {
				str_return = str_return + ret + "\n";
			}
			logger.info("result=" + str_return);
			return str_return;
		} catch (ConnectException e) {
			logger.error("ConnectException", e);
			throw new WeixinException("链接微信服务器失败");
		} catch (IOException e) {
			logger.error("IOException", e);
			throw new WeixinException("从微信服务器读取数据失败");
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new WeixinException("从微信服务器读取数据失败");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}
		}
	}
	
	public String doPost(String url, String data) throws WeixinException{
		logger.info("doPost url=" + url);
		logger.info("data=" + data);
		 httpClient = new HttpClient();
		 httpClient.getHostConfiguration().setProxy("14.18.161.18",443);
			httpClient.getParams().setAuthenticationPreemptive(true);
	        PostMethod method = new PostMethod(url);
	        try {
	        	setProxy();
				if(data != null){
				    RequestEntity requestEntity = new StringRequestEntity(data,"text/json","UTF-8");
				    method.setRequestEntity(requestEntity);
				}
				method.releaseConnection();
				statusCode = httpClient.executeMethod(method);
				if (statusCode != HttpStatus.SC_OK) {
					logger.info(Boolean.FALSE);
					logger.info("服务器繁忙，请稍等···");
				}else {
				    String searchResult = "";
				    logger.info(Boolean.TRUE);
				    BufferedInputStream ins=new BufferedInputStream(method.getResponseBodyAsStream());
				    byte resultBytes[] = readUrlStream(ins);
				    if(resultBytes != null && resultBytes.length >0){
				        searchResult = new String(resultBytes, charSet);
				    }
				    logger.info(searchResult);
				   return searchResult;
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException", e);
				throw new WeixinException("链接微信服务器失败");
			} catch (HttpException e) {
				logger.error("UnsupportedEncodingException", e);
				throw new WeixinException("链接微信服务器失败");
			} catch (IOException e) {
				logger.error("IOException", e);
				throw new WeixinException("从微信服务器读取数据失败");
			}
	        method.releaseConnection();
	        return null;
	}


	public String doPost2(String url, String data) throws WeixinException {
		logger.info("doPost url=" + url);
		logger.info("data=" + data);
		InputStream is = null;
		OutputStream out = null;
		HttpsURLConnection conn = null;
		try {
			// SSLContext sc = SSLContext.getInstance("SSL");
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new SecureRandom());
			URL console = new URL(null, url, new Handler());
			Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("14.18.161.18",443));
			conn = (HttpsURLConnection) console.openConnection(proxy);
			conn.setSSLSocketFactory(sc.getSocketFactory());
			// conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			out = conn.getOutputStream();
			DataOutputStream httpOut = new DataOutputStream(out);
			httpOut.write(data.getBytes("UTF-8"));
			httpOut.flush();
			is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String ret = null;
			String str_return = "";
			while ((ret = br.readLine()) != null) {
				str_return = str_return + ret;
			}
			logger.info("result=" + str_return);
			return str_return;
		} catch (ConnectException e) {
			logger.error("ConnectException", e);
			throw new WeixinException("链接微信服务器失败");
		} catch (IOException e) {
			logger.error("IOException", e);
			throw new WeixinException("从微信服务器读取数据失败");
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new WeixinException("从微信服务器读取数据失败");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					logger.error("Exception", e);
				}
			}
		}
	}

	/**
	 * 文件上传到微信服务器
	 * 
	 * @param fileType
	 *            文件类型
	 * @param filePath
	 *            文件路径
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject send(String url, String fileType, String filePath)
			throws Exception {
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("14.18.161.18",443));
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection(proxy);
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = new JSONObject(result);
		return jsonObj;
	}
	private void setProxy(){
		String is_proxy=PropsUtil.getProperty("is_proxy");
		if("1".equals(is_proxy)){
			httpClient.getHostConfiguration().setProxy(PropsUtil.getProperty("proxy_host_ip"), Integer.parseInt(PropsUtil.getProperty("proxy_host_port")));
			String iscredentials=PropsUtil.getProperty("iscredentials");
			if("1".equals(iscredentials)){
				httpClient.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(PropsUtil.getProperty("proxy_credentials_username"),PropsUtil.getProperty("proxy_credentials_pwd")));
			}
			
		}
	}
	/*
	 * public static void main(String[] args) throws WeixinException { String
	 * url =
	 * "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=RWoBipBC2GYItXgYf0CNmk-k0GJ3T66_RV86FdZA8yTMmeLUadLWIY5leIgI5DBBlzMGedSwU3q8Monwiz0EYhP3YZ-GtwKgVJ8edDlIcDE&media_id=C54m8EGpW-OgygaIBQU08gH8O1t_TPgCV2jSLyJW3jtZ2e-9p2icw-JlMEnC_9yp"
	 * ; String filename = "zchai.jpg"; new SSLNetProvider().doGetImage(url,
	 * filename); }
	 */
}
