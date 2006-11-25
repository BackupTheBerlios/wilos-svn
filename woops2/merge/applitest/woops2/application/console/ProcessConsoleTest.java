package woops2.application.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.business.process.ProcessService;

public class ProcessConsoleTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		am.TestSpelpParsingXML();
		
	}
}
