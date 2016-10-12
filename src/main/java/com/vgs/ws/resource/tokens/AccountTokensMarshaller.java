package com.vgs.ws.resource.tokens;

import com.vgs.ws.exception.WSException;
import com.vgs.ws.jaxb.types.DeleteTokensResponse;
import com.vgs.ws.jaxb.types.GetAccountTokensResponse;
import com.vgs.ws.jaxb.types.ObjectFactory;
import com.vgs.ws.jaxb.types.StatusType;
import com.vgs.ws.resource.BaseMarshaller;
import com.vgs.ws.util.AccountTokens;

import javax.xml.bind.JAXBElement;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class AccountTokensMarshaller extends BaseMarshaller {

    private String TYPES_PACKAGE = "com.vgs.ws.jaxb.types";

    public String writeTokensResponse(AccountTokens accountTokens, String statusCode, String statusMsg)
        throws WSException {

        GetAccountTokensResponse getAccountTokensResponse = new GetAccountTokensResponse();
        if (accountTokens.getRefreshToken() != null) {
            getAccountTokensResponse.setRefreshtoken(accountTokens.getRefreshToken());
        }
        
        if (accountTokens.getvToken() != null) {
            getAccountTokensResponse.setDvtoken(accountTokens.getvToken());
        }
        
        if (accountTokens.getAccountId() != null) {
            getAccountTokensResponse.setAccountid(accountTokens.getAccountId());
        }
        if (accountTokens.getMobile() != null) {
            getAccountTokensResponse.setMobile(accountTokens.getMobile());
        }
        if (accountTokens.getEmail() != null) {
            getAccountTokensResponse.setEmail(accountTokens.getEmail());
        }
        
        getAccountTokensResponse.setUserid(accountTokens.getAccountId());
        StatusType statusType = new StatusType();
        statusType.setCode(statusCode);
        statusType.setMsg(statusMsg);
        getAccountTokensResponse.setStatus(statusType);
        JAXBElement<GetAccountTokensResponse> element =
            (new ObjectFactory()).createAccountTokensGetResponse(getAccountTokensResponse);
        return marshall(element, TYPES_PACKAGE, "GetAccountTokensRequest.xsd");
    }

    public String writeTokensResponse(String statusCode, String statusMsg)
        throws WSException {
        GetAccountTokensResponse getAccountTokensResponse = new GetAccountTokensResponse();
        StatusType statusType = new StatusType();
        statusType.setCode(statusCode);
        statusType.setMsg(statusMsg);
        getAccountTokensResponse.setStatus(statusType);
        JAXBElement<GetAccountTokensResponse> element =
            (new ObjectFactory()).createAccountTokensGetResponse(getAccountTokensResponse);
        return marshall(element, TYPES_PACKAGE, "GetAccountTokensRequest.xsd");
    }

    public String deleteTokensResponse(String statusCode, String statusMsg)
        throws WSException {
        DeleteTokensResponse deleteTokensResponse = new DeleteTokensResponse();
        StatusType statusType = new StatusType();
        statusType.setCode(statusCode);
        statusType.setMsg(statusMsg);
        deleteTokensResponse.setStatus(statusType);
        JAXBElement<DeleteTokensResponse> element =
            (new ObjectFactory()).createAccountTokensDeleteResponse(deleteTokensResponse);
        return marshall(element, TYPES_PACKAGE, "deleteTokensResponse.xsd");
    }

}
