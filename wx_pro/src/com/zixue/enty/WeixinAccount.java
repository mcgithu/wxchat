package com.zixue.enty;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信服务号对象
 * 
 * @author lwgang
 * @createTime 2015-01-22
 * @history 1.修改时间,修改;修改内容：
 * 
 */
public class WeixinAccount implements Serializable {

	private static final long serialVersionUID = -3783293808632853486L;

	private String id;
	private String appid;//应用id
	private String appsecret;//应用密钥
	private String name;//服务号名称
	private String authdomain;//应用域名
	private String url;//回调URL
	private String token;//令牌
	private String aeskey;//密钥
	private String picurl;//应用图片
	private Integer enable;//启用标志
	private String creator;//创建者
	private Date createtime;//创建时间
	private String userid;//用户账号
	
	private String orgid;	//机构ID
	private String orgname;	//机构名称

	private String qrcode_path;//服务号二维码保存路径
	
	private int is_service;//是否提供服务，1：提供，0：不提供
	
	private String wx_acctno;//微信号
	private String server_context;//服务上下文
	private int is_qyhao;//是否是企业号  0：服务号 1:企业号 3:订阅号
	private String ref_qyNo;//服务号关联的企业号
	
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getQrcode_path() {
		return qrcode_path;
	}

	public void setQrcode_path(String qrcode_path) {
		this.qrcode_path = qrcode_path;
	}

	public WeixinAccount() {
		this.createtime = new Date();
	}
	
	public WeixinAccount(String id,String appid,String appsecret,String name, String authdomain, String url,
			String token, String aeskey, String picurl,Integer enable, String creator,String orgId,String server_context,String ref_qyNo) {
		this.id = id;
		this.appid = appid;
		this.appsecret = appsecret;
		this.name = name;
		this.authdomain = authdomain;
		this.url = url;
		this.token = token;
		this.aeskey = aeskey;
		this.picurl = picurl;
		this.enable = enable;
		this.creator = creator;
		this.createtime = new Date();
		this.orgid = orgId;
		this.server_context = server_context;
		this.ref_qyNo =ref_qyNo;
	}
	
	
	/**
	 * 设置属性：唯一标识
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取属性：唯一标识
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置属性：应用ID
	 * @param appid
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * 获取属性：应用ID
	 * @return
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * 设置属性：应用秘钥
	 * @param appsecret
	 */
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	/**
	 * 获取属性：应用秘钥
	 * @return
	 */
	public String getAppsecret() {
		return appsecret;
	}

	/**
	 * 获取属性：应用名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置属性：应用名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置属性：授权域名
	 * 
	 * @param
	 */
	public void setAuthdomain(String authdomain) {
		this.authdomain = authdomain;
	}

	/**
	 * 获取属性：授权域名
	 * 
	 * @return
	 */
	public String getAuthdomain() {
		return authdomain;
	}

	/**
	 * 设置属性：回调URL
	 * 
	 * @param
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取属性：回调URL
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置属性：令牌
	 * 
	 * @param
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 获取属性：令牌
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置属性：密钥
	 * 
	 * @param
	 */
	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}

	/**
	 * 获取属性：密钥
	 * 
	 * @return
	 */
	public String getAeskey() {
		return aeskey;
	}

	/**
	 * 设置属性：应用图片URL
	 * 
	 * @param
	 */
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	/**
	 * 获取属性：应用图片URL
	 * 
	 * @return
	 */
	public String getPicurl() {
		return picurl;
	}

	/**
	 * 设置属性：启用标志
	 * 
	 * @param
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * 获取属性：启用标志
	 * 
	 * @return
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * 设置属性：创建者
	 * 
	 * @param
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 获取属性：创建者
	 * 
	 * @return
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置属性：创建时间
	 * 
	 * @param
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 获取属性：创建时间
	 * 
	 * @return
	 */
	public Date getCreatetime() {
		return createtime;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getIs_service() {
		return is_service;
	}

	public void setIs_service(int is_service) {
		this.is_service = is_service;
	}

	public String getWx_acctno() {
		return wx_acctno;
	}

	public void setWx_acctno(String wx_acctno) {
		this.wx_acctno = wx_acctno;
	}

	public String getServer_context() {
		return server_context;
	}

	public void setServer_context(String server_context) {
		this.server_context = server_context;
	}

	public int getIs_qyhao() {
		return is_qyhao;
	}

	public void setIs_qyhao(int is_qyhao) {
		this.is_qyhao = is_qyhao;
	}

    public String getRef_qyNo() {
        return ref_qyNo;
    }

    public void setRef_qyNo(String ref_qyNo) {
        this.ref_qyNo = ref_qyNo;
    }
	
	
}
