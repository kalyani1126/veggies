package com.vgs.ws.resource;

import com.vgs.ws.exception.VGSError;
import com.vgs.ws.exception.WSException;
import com.vgs.ws.exception.WSException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import java.io.StringReader;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class BaseUnmarshaller {

    /**
     * Handles the unmarshalling of the REST request XML to the corresponding JAXB element
     *
     * @return JAXBElement
     */
    public JAXBElement<?> unmarshall(String xml, String typesPkg) throws WSException {
        try {
            JAXBContext jc = JAXBContext.newInstance(typesPkg);
            Unmarshaller um = jc.createUnmarshaller();
            JAXBElement<?> element =
                (JAXBElement<?>) um.unmarshal(new StreamSource(new StringReader(xml)));
            return element;
        } catch (UnmarshalException ex) {
            throw new WSException(VGSError.INVALID_XML, ex);
        } catch (JAXBException exc) {
            throw new WSException(VGSError.INVALID_XML, exc);
        }
    }
}
