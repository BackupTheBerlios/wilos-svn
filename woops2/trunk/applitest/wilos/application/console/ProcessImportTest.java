package wilos.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;

public class ProcessImportTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		Process scrum = am.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		//am.saveProcess(scrum);
		
		Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		//Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openup.zip"));
		//am.saveProcess(openup);
		}
}
