package wilos.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.process.ProcessService;
import wilos.model.spem2.process.Process;

public class ProcessConsoleTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		//am.TestSpelpParsingXML();
		Process p = am.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		System.out.println("p= "+p);
		am.saveProcess(p);
	}
}
