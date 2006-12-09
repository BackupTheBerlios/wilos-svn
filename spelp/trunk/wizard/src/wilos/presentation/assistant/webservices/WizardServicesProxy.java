package wilos.presentation.assistant.webservices;

import com.thoughtworks.xstream.XStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.process.Process;
import wilos.business.webservices.*;

public class WizardServicesProxy {
        public static String ENDPOINT = "/WizardServices?wsdl";
        public static String URLWebService = "http://webservices.business.wilos/";
        public static String nameWebService = "WizardServicesService";
    
	public static ArrayList<RoleDescriptor> getRolesByUser(String login, String password, String adresseServeur) {	
            ArrayList<RoleDescriptor> myRoleListe = new  ArrayList<RoleDescriptor>();
            List<Process> pros = getAllProcess(login, password,adresseServeur);
            for (Process pro : pros) {
                for (BreakdownElement bre: pro.getBreakDownElements()) {
                    if (bre instanceof RoleDescriptor) {
                        myRoleListe.add((RoleDescriptor)bre);
                    }
                }
            }

            return myRoleListe;
	}
        
        public static Participant getParticipant(String login, String password, String address)
        {
            Participant myParticipant = null;
            
            try { 
                Service service = Service.create(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));
                WizardServices port = service.getPort(WizardServices.class);
                //myWilosUser = port.getWilosUser(login,password);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return myParticipant;
        }
        private static List<Process> getAllProcess(String login, String password, String adresseServeur) {
             ArrayList<Process> pros = new  ArrayList<Process>();
             try { 
                Service service = Service.create(new URL(adresseServeur+ENDPOINT), new QName(URLWebService, nameWebService));
                // Call Web Service Operation
                //services.WizardServicesService service = new services.WizardServicesService();
                WizardServices port = service.getPort(WizardServices.class);
                java.util.List<String> result = port.getAllProcess(login, password);
                XStream xstream = new XStream();     
                for (String strxml : result) {
                    pros.add((Process)xstream.fromXML(strxml));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
             return pros;
        }
}
