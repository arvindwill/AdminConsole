/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.gateway.utils;

import com.glenwood.glaceemr.gateway.utils.message.GatewayMessage;
import com.glenwood.glaceemr.gateway.utils.message.ObjectFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author gsivashanmugam
 */
public class GatewayMessageUtils {
    public static String getXMLString(GatewayMessage message)throws JAXBException{
        JAXBContext context=JAXBContext.newInstance("com.glenwood.glaceemr.gateway.utils.message");
        Marshaller marshaller = context.createMarshaller();
        JAXBElement<GatewayMessage> rootElement = (new ObjectFactory()).createGatewayMessage(message);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        marshaller.marshal(rootElement, outStream);
        return new String(outStream.toByteArray());
    }
    
    public static GatewayMessage getMessageObject(String xmlString)throws Exception{
        JAXBContext context=JAXBContext.newInstance("com.glenwood.glaceemr.gateway.utils.message");
        Unmarshaller unMarshaller=context.createUnmarshaller();
        return (GatewayMessage)((JAXBElement)unMarshaller.unmarshal(new ByteArrayInputStream(xmlString.getBytes()))).getValue();
    }
    
}
