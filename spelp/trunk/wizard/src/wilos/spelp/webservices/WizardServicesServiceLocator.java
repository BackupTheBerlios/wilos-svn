/**
 * WizardServicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package wilos.spelp.webservices;

public class WizardServicesServiceLocator extends org.apache.axis.client.Service implements wilos.spelp.webservices.WizardServicesService {

    public WizardServicesServiceLocator() {
    }


    public WizardServicesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WizardServicesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WizardServices
    private java.lang.String WizardServices_address = "http://localhost:9014/remote/services/WizardServices";

    public java.lang.String getWizardServicesAddress() {
        return WizardServices_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WizardServicesWSDDServiceName = "WizardServices";

    public java.lang.String getWizardServicesWSDDServiceName() {
        return WizardServicesWSDDServiceName;
    }

    public void setWizardServicesWSDDServiceName(java.lang.String name) {
        WizardServicesWSDDServiceName = name;
    }

    public wilos.spelp.webservices.WizardServices getWizardServices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WizardServices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWizardServices(endpoint);
    }

    public wilos.spelp.webservices.WizardServices getWizardServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            wilos.spelp.webservices.WizardServicesSoapBindingStub _stub = new wilos.spelp.webservices.WizardServicesSoapBindingStub(portAddress, this);
            _stub.setPortName(getWizardServicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWizardServicesEndpointAddress(java.lang.String address) {
        WizardServices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (wilos.spelp.webservices.WizardServices.class.isAssignableFrom(serviceEndpointInterface)) {
                wilos.spelp.webservices.WizardServicesSoapBindingStub _stub = new wilos.spelp.webservices.WizardServicesSoapBindingStub(new java.net.URL(WizardServices_address), this);
                _stub.setPortName(getWizardServicesWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WizardServices".equals(inputPortName)) {
            return getWizardServices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservices.spelp.wilos", "WizardServicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservices.spelp.wilos", "WizardServices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WizardServices".equals(portName)) {
            setWizardServicesEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
