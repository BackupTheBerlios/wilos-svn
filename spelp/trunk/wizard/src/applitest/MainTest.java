package applitest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.rmi.RemoteException;
import javax.xml.ws.Service;
import wilos.spelp.webservices.RoleDescriptor;
import wilos.spelp.webservices.WizardServices;
import wilos.spelp.webservices.WizardServicesService;



public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            try { // Call Web Service Operation
                wilos.spelp.webservices.WizardServicesService service = new wilos.spelp.webservices.WizardServicesService();
                // TODO initialize WS operation arguments here
                java.lang.String login = "toto";
                java.lang.String password = "toto";
                // TODO process result here
                
                 Service servic2 = WizardServicesService.create(new URL("http://localhost:8084/remote/WizardServices?wsdl"),service.getServiceName());
            
                WizardServices port = servic2.getPort(WizardServices.class);
                java.util.List<RoleDescriptor> result = port.getRolesByUser(login, password);
                System.out.println("Result = "+result);
            } catch (Exception ex) {
                // TODO handle custom exceptions here
                ex.printStackTrace();
            }
//		// on instancie le proxy local
//		WizardServicesProxy px = new WizardServicesProxy();
//		// on specifie l'adresse du serveur
//		px.setEndpoint("http://localhost:8080/wilos_remote/services/WizardServices");
//		// on appelle la m�thode sur le proxy local comme on le ferait sur l'objet distant
//		try {
//			RoleDescriptor [] tabRoles = px.getRolesByUser("toto", "toto");
//			for (int i=0; i<tabRoles.length; i++) {
//				System.out.println(tabRoles[i].getName());
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
            
	}

}
