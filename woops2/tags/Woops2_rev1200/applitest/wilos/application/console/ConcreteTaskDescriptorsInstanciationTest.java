package wilos.application.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.spem2.process.ProcessService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

public class ConcreteTaskDescriptorsInstanciationTest {
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		ProjectDao pm = (ProjectDao) ctx.getBean("ProjectDao");
		ProcessDao p = (ProcessDao) ctx.getBean("ProcessDao");
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		
		String s = am.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		Process scrum = p.getProcess(s);	
		
		Project project = new Project();
		project.setName("Wilos");
		project.setProcess(scrum);
		pm.saveOrUpdateProject(project);
		
		String id = project.getProject_id();
		
		am.projectInstanciation(s, id);
	}
}
