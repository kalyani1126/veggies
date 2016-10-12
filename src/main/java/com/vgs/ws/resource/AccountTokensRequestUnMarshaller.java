package com.vgs.ws.resource;

import com.vgs.ws.exception.VGSError;
import com.vgs.ws.exception.WSException;
import com.vgs.ws.jaxb.types.GetAccountTokensRequest;
import com.vgs.ws.util.AccountTokens;

import javax.xml.bind.JAXBElement;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class AccountTokensRequestUnMarshaller extends BaseUnmarshaller {

    private String TYPES_PACKAGE = "com.vgs.ws.jaxb.types";

    /**
     *
     * @param xml
     * @param userId
     * @return
     * @throws WSException
     */
    public AccountTokens readAccountTokensRequest(String xml, String userId) throws WSException {

        try {
            JAXBElement<?> element = unmarshall(xml, TYPES_PACKAGE);
            GetAccountTokensRequest getAccountTokensRequest = (GetAccountTokensRequest) element.getValue();
            AccountTokens accountTokens = new AccountTokens();
            if(getAccountTokensRequest.getDeviceinfo()==null || getAccountTokensRequest.getDeviceinfo().isEmpty()){
                throw new WSException(VGSError.DEVICE_INFO_NOT_FOUND,
                        "Missing device info for the user id " + userId);
            }else {
                accountTokens.setDeviceinfo(getAccountTokensRequest.getDeviceinfo());

            }
            return accountTokens;
        } catch (ClassCastException exc) {
           throw new WSException(VGSError.INVALID_XML, exc);
        }
    }

}
