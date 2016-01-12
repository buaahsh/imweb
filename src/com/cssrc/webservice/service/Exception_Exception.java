
package com.cssrc.webservice.service;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.4
 * 2016-01-12T20:47:18.410+08:00
 * Generated source version: 3.1.4
 */

@WebFault(name = "Exception", targetNamespace = "http://service.webservice.cssrc.com/")
public class Exception_Exception extends java.lang.Exception {
    
    private com.cssrc.webservice.service.Exception exception;

    public Exception_Exception() {
        super();
    }
    
    public Exception_Exception(String message) {
        super(message);
    }
    
    public Exception_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception_Exception(String message, com.cssrc.webservice.service.Exception exception) {
        super(message);
        this.exception = exception;
    }

    public Exception_Exception(String message, com.cssrc.webservice.service.Exception exception, Throwable cause) {
        super(message, cause);
        this.exception = exception;
    }

    public com.cssrc.webservice.service.Exception getFaultInfo() {
        return this.exception;
    }
}