package wilos.application.console;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.role.RoleDescriptorService;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;

public class RoleDescriptorDaoConsoleTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		RoleDescriptorService am = (RoleDescriptorService) ctx.getBean("RoleDescriptorService");
		//am.TestSpelpParsingXML();
		
		List <RoleDescriptor>liste = am.getRoleDescriptorsFromProcess("ff8081810f771b31010f771b50500001");
		System.out.println("liste = "+liste+" "+liste.get(0).getGuid());
		System.out.println("liste.size="+liste.size());
	}
}
