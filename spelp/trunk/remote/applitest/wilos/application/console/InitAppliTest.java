package wilos.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.process.Process;

public class InitAppliTest {
	public static void main(String[] args) {
		importXML();
		instanciation();
		affectation();	    
	}
	
	
	private static void importXML() {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProcessManagerService pser = (ProcessManagerService) ctx.getBean("ProcessManagerService");
		ProcessManager pm = new ProcessManager();
		pm.setLogin("pmtest");
		pm.setPassword("blabla");
		pm.setName("test");
		pser.saveProcessManager(pm);
		WilosUser wu  = pser.getProcessManagers().iterator().next();
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		Process scrum = am.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		am.saveProcess(scrum,wu.getWilosuser_id());
		
		/*Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		//Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openup.zip"));
		am.saveProcess(openup);*/
		
	}
	
	private static void instanciation() {		
//		 Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ProjectDao pm = (ProjectDao) ctx.getBean("ProjectDao");
		ProcessDao p = (ProcessDao) ctx.getBean("ProcessDao");
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");

		String s = am.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		Process scrum = p.getProcess(s);

		Project project = new Project();
		project.setConcreteName("Wilos");
		project.setProcess(scrum);
		pm.saveOrUpdateProject(project);

		am.projectInstanciation(project);

		String s2 = am.getProcessDao().getProcessFromGuid("_0uyGoMlgEdmt3adZL5Dmdw").getId();
		Process openup = p.getProcess(s2);

		Project project2 = new Project();
		project2.setConcreteName("IceOpenUP");
		project2.setProcess(openup);
		pm.saveOrUpdateProject(project2);

		am.projectInstanciation(project2);
	}
	
	private static void affectation () {
//		 Notre fabrique SPRING permettant l'acc�s aux beans d�clar�s
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		// import des processus
		InitAppliService am = (InitAppliService) factory.getBean("InitAppliService");
		am.initAppli();
	}
}

