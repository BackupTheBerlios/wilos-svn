package webservices;

public class TestWebServicesProxy implements webservices.TestWebServices {
  private String _endpoint = null;
  private webservices.TestWebServices testWebServices = null;
  
  public TestWebServicesProxy() {
    _initTestWebServicesProxy();
  }
  
  private void _initTestWebServicesProxy() {
    try {
      testWebServices = (new webservices.TestWebServicesServiceLocator()).getTestWebServices();
      if (testWebServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)testWebServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)testWebServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (testWebServices != null)
      ((javax.xml.rpc.Stub)testWebServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public webservices.TestWebServices getTestWebServices() {
    if (testWebServices == null)
      _initTestWebServicesProxy();
    return testWebServices;
  }
  
  public java.lang.String getExample() throws java.rmi.RemoteException{
    if (testWebServices == null)
      _initTestWebServicesProxy();
    return testWebServices.getExample();
  }
  
  
}