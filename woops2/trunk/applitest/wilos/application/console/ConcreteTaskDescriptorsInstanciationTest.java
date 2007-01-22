package wilos.application.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.spem2.process.ProcessService;

public class ConcreteTaskDescriptorsInstanciationTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		//am.projectInstanciation(_processId, _projectId);
	}
}
