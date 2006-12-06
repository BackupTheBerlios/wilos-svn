package wilos.presentation.assistant.applitest;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.process.Process;
import wilos.spelp.webservices.*;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

                try { // Call Web Service Operation
                    WizardServicesService service = new WizardServicesService();
                    WizardServices port = service.getWizardServicesPort();
                    // TODO initialize WS operation arguments here
                    java.lang.String login = "testSansBD";
                    java.lang.String password = "testSansBD";
                    // TODO process result here
                    List<String> result = port.getAllProcess(login, password);
                    System.out.println("Result = "+result);
                    XStream xstream = new XStream();                
                    Process pro = (Process)xstream.fromXML(result.get(0));
                    System.out.println("NomProcessus : "+pro.getName());
                    for (BreakdownElement bk : pro.getBreakDownElements()) {
                        System.out.println("BreakDownElement : "+bk.getName());
                    }
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }
            
	
        
        }

}
