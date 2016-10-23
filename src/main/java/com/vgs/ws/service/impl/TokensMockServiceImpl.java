/**
 * 
 */
package com.vgs.ws.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.vgs.ws.exception.AuthStatusError;
import com.vgs.ws.exception.AuthenticationException;
import com.vgs.ws.service.TokensService;
import com.vgs.ws.util.AccountTokens;

/**
 * @author Santosh.kumar
 * Date : Oct 22, 2016
 */
public class TokensMockServiceImpl implements TokensService {

	/* (non-Javadoc)
	 * @see com.vgs.ws.service.TokensService#getAccountTokens(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public AccountTokens getAccountTokens(String userId, String password,
			Map<String, String> headers) throws AuthenticationException {
		if(!StringUtils.isEmpty(userId)&&!StringUtils.isEmpty(password)&&userId.equals("veggie")){
			return mockAccountTokens();
		}
		// TODO Auto-generated method stub
		throw new AuthenticationException(AuthStatusError.MISSING_USER_CREDENTIALS_ERROR);
	}

	private AccountTokens mockAccountTokens(){
		AccountTokens tokens = new AccountTokens();
		tokens.setAccountId(nextSessionId());
		tokens.setMobile("9988776610");
		tokens.setRefreshToken(token("sesion123:veggie"));
		tokens.setvToken(token("veggie"));
		tokens.setStatusMsg("1000");
		tokens.setStatusCode("");
		
		return tokens;
	}
	private static String token(String s) {
		String refresh = Base64.getEncoder().encode(s.getBytes()).toString();
		return refresh;
	}
	
	private static SecureRandom random = new SecureRandom();

	private static String nextSessionId() {
	    return new BigInteger(130, random).toString(32);
	  }
	  
	/* (non-Javadoc)
	 * @see com.vgs.ws.service.TokensService#invalidateToken(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void invalidateToken(String userId, String password,
			Map<String, String> headers) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

}
