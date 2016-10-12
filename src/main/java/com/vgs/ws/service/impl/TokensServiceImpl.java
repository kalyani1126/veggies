package com.vgs.ws.service.impl;


import java.util.Map;

import com.vgs.ws.exception.AuthenticationException;
import com.vgs.ws.service.TokensService;
import com.vgs.ws.util.AccountTokens;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class TokensServiceImpl implements TokensService {

	/* (non-Javadoc)
	 * @see com.vgs.ws.service.TokensService#getAccountTokens(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public AccountTokens getAccountTokens(String userId, String password,
			Map<String, String> headers) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
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
