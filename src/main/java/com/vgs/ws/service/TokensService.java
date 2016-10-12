package com.vgs.ws.service;

import com.vgs.ws.exception.AuthenticationException;
import com.vgs.ws.util.AccountTokens;

import java.util.Map;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public interface TokensService {


    /**
     * Retrieve the tokens for the user by the Domain
     *
     * @param userId
     * @param password
     * @param headers
     *
     * @return
     *
     * @throws com.vgs.ws.exception.AuthenticationException
     */
    public AccountTokens getAccountTokens(String userId, String password, Map<String, String> headers) throws AuthenticationException;

    /**
     * @param userId
     * @param password
     * @param headers
     *
     * @throws com.vgs.ws.exception.AuthenticationException
     */
    public void invalidateToken(String userId, String password, Map<String, String> headers) throws AuthenticationException;
}
