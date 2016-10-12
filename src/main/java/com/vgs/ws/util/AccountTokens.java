
package com.vgs.ws.util;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class AccountTokens {

    //static identifiers
    public static final String USERUID = "user.uid";
    public static final String USERNAME = "username";
    public static final String V_TOKEN = "v.token";
    public static final String REFRESH_TOKEN = "refresh.token";
    public static final String DEVICE_INFO = "device.info";
    public static final String CLIENTPLATFORM = "clientplatform";
    public static final String DOMAIN = "domain";
    public static final String APPID = "appid";

    private String refreshToken;
    private String vToken;
    private String clientVersion;
    private String mdn;
    private String type;
    private String platForm;
    private String statusCode;
    private String statusMsg;
    private String deviceinfo;
    private String accountId;
    private String mobile;
    private String email;
	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	/**
	 * @return the vToken
	 */
	public String getvToken() {
		return vToken;
	}
	/**
	 * @param vToken the vToken to set
	 */
	public void setvToken(String vToken) {
		this.vToken = vToken;
	}
	/**
	 * @return the clientVersion
	 */
	public String getClientVersion() {
		return clientVersion;
	}
	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return mdn;
	}
	/**
	 * @param mdn the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the platForm
	 */
	public String getPlatForm() {
		return platForm;
	}
	/**
	 * @param platForm the platForm to set
	 */
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}
	/**
	 * @param statusMsg the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	/**
	 * @return the deviceinfo
	 */
	public String getDeviceinfo() {
		return deviceinfo;
	}
	/**
	 * @param deviceinfo the deviceinfo to set
	 */
	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}
	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
