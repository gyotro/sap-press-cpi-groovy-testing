/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

def Message processData(Message message) {
    //Headers
    def map = message.getHeaders();
    String sBody = message.getBody(String)
    String operation = map.get("CamelHttpMethod");

    //Properties
    Map mapProp = message.getProperties();

    if (operation == 'GET')
    {
        LocalDateTime local = LocalDateTime.now(ZoneId.of("Europe/Rome"))
        map.put("timestamp", local.toString())
        map.put("mode", "onDemandALL")
        map.put("numFatt", null)
        message.setProperties(mapProp)
    }
    else if (operation == 'POST')
    {
        JsonSlurper fatt = new JsonSlurper()
        String numFatt = fatt.parseText(sBody).numFatt
        map.put("mode", "onDemandSINGLE")
        map.put("numFatt", numFatt)
        map.put("timestamp", null)
        message.setHeaders(map)
    }

/*    message.setHeader("oldHeader", value + "modified");
    message.setHeader("newHeader", "newHeader");*/

    //Properties
/*    map = message.getProperties();
    value = map.get("oldProperty");
    message.setProperty("oldProperty", value + "modified");
    message.setProperty("newProperty", "newProperty");*/
    
    return message;
}