
package com.vgs.ws.resource;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */

/**
 * This classed is used to get the instances of different singleton classes
 */

public class InstanceFactory {

    private static final Logger _logger = Logger.getLogger("com.ws.system.log");
    private static String typesPkg = "com.vgs.ws.jaxb.types";
    private static JAXBContext jc = null;
    private static DatatypeFactory dataTypeFactory = null;

    private InstanceFactory() {
    }

    public static JAXBContext getJaxBContextInstance() {
        if (jc == null) {
            try {
                jc = JAXBContext.newInstance(typesPkg);
            } catch (JAXBException ex) {
                _logger.error("Error while creating JAXBContext instance ", ex);
            }
        }
        return jc;
    }

    public static DatatypeFactory getDataTypeFactoryInstance() {
        if (dataTypeFactory == null) {
            try {
                dataTypeFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException dtce) {
                _logger.error("DatatypeFactory could not be initialized", dtce);
            }
        }
        return dataTypeFactory;
    }
}

