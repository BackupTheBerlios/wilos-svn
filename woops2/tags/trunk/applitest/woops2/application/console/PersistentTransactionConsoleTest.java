package woops2.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.business.process.ProcessService;
//import woops2.model.process.Process;
import woops2.model.process.Process;

public class PersistentTransactionConsoleTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		Process p = am.SpelpParsingXML(new File("applitest/woops2/application/console/scrum.xml"));
		//am.TestSpelpParsingXML();
		/*String id = am.TestPersistence();
		am.TestProcessPersistence(id);*/
		
		/*Process p = am.getProcessDao().getProcess(id);
		System.out.println("TestProcessPersistence p => "+p+" id="+p.getId());
		System.out.println("#### p -> "+p.getIdEPF()+" "+p.getName());*/
		
		am.TestSaveCollectionsProcess(p);
	}
}
