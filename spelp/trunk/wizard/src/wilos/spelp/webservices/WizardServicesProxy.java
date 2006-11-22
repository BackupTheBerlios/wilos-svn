package wilos.spelp.webservices;

public class WizardServicesProxy implements wilos.spelp.webservices.WizardServices {
  private String _endpoint = null;
  private wilos.spelp.webservices.WizardServices wizardServices = null;
  
  public WizardServicesProxy() {
    _initWizardServicesProxy();
  }
  
  private void _initWizardServicesProxy() {
    try {
      wizardServices = (new wilos.spelp.webservices.WizardServicesServiceLocator()).getWizardServices();
      if (wizardServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wizardServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wizardServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wizardServices != null)
      ((javax.xml.rpc.Stub)wizardServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public wilos.spelp.webservices.WizardServices getWizardServices() {
    if (wizardServices == null)
      _initWizardServicesProxy();
    return wizardServices;
  }
  
  public woops2.model.role.RoleDescriptor[] getRolesByUser(java.lang.String login, java.lang.String password) throws java.rmi.RemoteException{
    if (wizardServices == null)
      _initWizardServicesProxy();
    return wizardServices.getRolesByUser(login, password);
  }
  
  
}