package wilos.presentation.assistant.applitest;

import java.util.List;

import wilos.business.webservices.WizardServices;
import wilos.business.webservices.WizardServicesService;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;

import com.thoughtworks.xstream.XStream;

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
