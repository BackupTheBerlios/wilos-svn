package applitest;

import com.thoughtworks.xstream.XStream;
import woops2.model.role.RoleDescriptor;
import woops2.model.activity.Activity;
import woops2.model.task.TaskDescriptor;


//import wilos.spelp.webservices.RoleDescriptor;
//import wilos.spelp.webservices.WizardServices;
//import wilos.spelp.webservices.WizardServicesService;



public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            try { // Call Web Service Operation
                webservices.WizardServicesService service = new webservices.WizardServicesService();
                webservices.WizardServices port = service.getWizardServicesPort();
                // TODO initialize WS operation arguments here
                java.lang.String login = "";
                java.lang.String password = "";
                // TODO process result here
                java.util.List<java.lang.String> result = port.getRolesByUserXstream(login, password);
                System.out.println("Result = "+result);
                XStream xstream = new XStream();
                //xstream.setClassLoader(RoleDescriptor.class.getClassLoader());
                RoleDescriptor rd = (RoleDescriptor)xstream.fromXML(result.get(0));
                System.out.println(rd.getName());
                System.out.println(rd.getPrimaryTasks().iterator().next().getName());
            } catch (Exception ex) {
                // TODO handle custom exceptions here
                ex.printStackTrace();
            }

//            try { // Call Web Service Operation
//                wilos.spelp.webservices.WizardServicesService service = new wilos.spelp.webservices.WizardServicesService();
//                // TODO initialize WS operation arguments here
//                java.lang.String login = "toto";
//                java.lang.String password = "toto";
//                // TODO process result here
//                
//                 Service servic2 = WizardServicesService.create(new URL("http://localhost:8084/remote/WizardServices?wsdl"),service.getServiceName());
//            
//                WizardServices port = servic2.getPort(WizardServices.class);
//                java.util.List<RoleDescriptor> result = port.getRolesByUser(login, password);
//                System.out.println("Result = "+result);
//            } catch (Exception ex) {
//                // TODO handle custom exceptions here
//                ex.printStackTrace();
//            }
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
