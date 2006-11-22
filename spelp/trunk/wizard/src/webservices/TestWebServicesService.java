/**
 * TestWebServicesService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package webservices;

public interface TestWebServicesService extends javax.xml.rpc.Service {
    public java.lang.String getTestWebServicesAddress();

    public webservices.TestWebServices getTestWebServices() throws javax.xml.rpc.ServiceException;

    public webservices.TestWebServices getTestWebServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
