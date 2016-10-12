package com.vgs.ws.resource;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.vgs.ws.exception.WSException;
import com.vgs.ws.exception.VGSError;
import com.vgs.ws.exception.WSException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import java.io.StringWriter;
import java.util.GregorianCalendar;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public abstract class BaseMarshaller {

    public static GregorianCalendar gc;
    public static DatatypeFactory df = null;

    static {
        gc = new GregorianCalendar();
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException(
                "Exception while obtaining DatatypeFactory instance", dce);
        }
    }

    /**
     * Handles the marshalling of the JAXB Element to an XML String.
     *
     * @param element the jaxb object to be marshalled.
     * @param typesPkg the name of the package
     * @param schemaLocation the schema file name
     *
     * @return the XML String
     *
     * @throws javax.xml.stream.XMLStreamException
     * @throws java.io.IOException
     */

    public String marshall(JAXBElement<?> element, String typesPkg,
                           String schemaLocation) throws WSException {

        String responseXml = null;
        try {
            JAXBContext jc = InstanceFactory.getJaxBContextInstance();
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String arg0, String arg1, boolean arg2) {
                    return "ns1";
                }
            });
            StringWriter writer = new StringWriter();
            m.marshal(element, writer);
            String xmlString = writer.toString();
            responseXml = xmlString;
        } catch (JAXBException exc) {
            throw new WSException(VGSError.INVALID_REQUEST, exc);
        }

        return responseXml;
    }
}
