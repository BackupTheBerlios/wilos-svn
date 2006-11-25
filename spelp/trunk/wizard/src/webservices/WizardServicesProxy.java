package webservices;

import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import java.util.List;
import woops2.model.breakdownelement.BreakdownElement;

import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDescriptor;
import woops2.model.process.Process;

public class WizardServicesProxy {
	public static ArrayList<RoleDescriptor> getRolesByUser(String login, String password) {	
            ArrayList<RoleDescriptor> myRoleListe = new  ArrayList<RoleDescriptor>();
            List<Process> pros = getAllProcess(login, password);
            for (Process pro : pros) {
                for (BreakdownElement bre: pro.getBreakDownElements()) {
                    if (bre instanceof RoleDescriptor) {
                        myRoleListe.add((RoleDescriptor)bre);
                    }
                }
            }

            return myRoleListe;
	}
        
        private static List<Process> getAllProcess(String login, String password) {
             ArrayList<Process> pros = new  ArrayList<Process>();
             try { 
                // Call Web Service Operation
                services.WizardServicesService service = new services.WizardServicesService();
                services.WizardServices port = service.getWizardServicesPort();
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
