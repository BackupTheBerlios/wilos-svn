package woops2.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.business.process.ProcessService;
import woops2.model.process.Process;

public class ProcessConsoleTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		//am.TestSpelpParsingXML();
		Process p = am.SpelpParsingXML(new File("applitest/woops2/application/console/scrum.xml"));
		System.out.println("p= "+p);
		am.SaveProcessService(p);
	}
}
