package applitest;

import com.thoughtworks.xstream.XStream;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.role.RoleDescriptor;
import woops2.model.activity.Activity;
import woops2.model.task.TaskDescriptor;
import woops2.model.process.Process;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

                try { // Call Web Service Operation
                    services.WizardServicesService service = new services.WizardServicesService();
                    services.WizardServices port = service.getWizardServicesPort();
                    // TODO initialize WS operation arguments here
                    java.lang.String login = "testSansBD";
                    java.lang.String password = "testSansBD";
                    // TODO process result here
                    java.util.List<String> result = port.getAllProcess(login, password);
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
