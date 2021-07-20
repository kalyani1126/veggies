package com.vgs.ws.dto;

/*test by kalyani*/
/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class TokenDTO {

    private String vToken;
    private String tokenType;
    private String accountType;
    private String expiresIn;
    private String refreshToken;
    private String mobile;
    private String mail;
    private String accountId;
    private String seviceUrl;


	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the expiresIn
	 */
	public String getExpiresIn() {
		return expiresIn;
	}

	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

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
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
	 * @return the seviceUrl
	 */
	public String getSeviceUrl() {
		return seviceUrl;
	}

	/**
	 * @param seviceUrl the seviceUrl to set
	 */
	public void setSeviceUrl(String seviceUrl) {
		this.seviceUrl = seviceUrl;
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

}
