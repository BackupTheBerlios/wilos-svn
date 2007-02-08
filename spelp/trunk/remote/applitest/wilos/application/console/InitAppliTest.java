package wilos.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.business.util.Security;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.process.Process;

public class InitAppliTest {
	public static void main(String[] args) {
//		 Notre fabrique SPRING permettant l'accès aux beans déclarés
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		// import des processus
		ProcessService am = (ProcessService) factory.getBean("ProcessService");

		Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		am.saveProcess(openup);
		Process scrum = am.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		am.saveProcess(scrum);
		
		
		// instanciation des projets
		ProjectDao pm = (ProjectDao) factory.getBean("ProjectDao");
		ProcessDao p = (ProcessDao) factory.getBean("ProcessDao");

		String s = am.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		scrum = p.getProcess(s);
		
		Project project = new Project();
		project.setConcreteName("Wilos");
		project.setProcess(scrum);
		pm.saveOrUpdateProject(project);

		am.projectInstanciation(project);

		String s2 = am.getProcessDao().getProcessFromGuid("_0uyGoMlgEdmt3adZL5Dmdw").getId();
		openup = p.getProcess(s2);

		Project project2 = new Project();
		project2.setConcreteName("IceOpenUP");
		project2.setProcess(openup);
		pm.saveOrUpdateProject(project2);

		am.projectInstanciation(project2);
		
		project2 = am.getProjectDao().getProject(project2.getId());
		project= am.getProjectDao().getProject(project.getId());
		
		// creation du participant 
		ParticipantService ps = (ParticipantService) factory.getBean("ParticipantService");
		Participant pa = new Participant();
		
		pa.setLogin("test");
	    pa.setPassword(Security.encode("testtest"));
	    pa.setName("test");
	    pa.setEmailAddress("test@test.com");
	    pa.setFirstname("test");
	    ps.getParticipantDao().saveOrUpdateParticipant(pa);
	    
	    // affectation du particpant aux concreteRoles
	    
	    ConcreteRoleDescriptorDao cs = (ConcreteRoleDescriptorDao) factory.getBean("ConcreteRoleDescriptorDao");
	 	for (ConcreteRoleDescriptor crd : cs.getAllConcreteRoleDescriptors()) {
	    	pa.addConcreteRoleDescriptor(crd);
	    }	   
	   
	    ps.getParticipantDao().saveOrUpdateParticipant(pa);
		
			
		// affectation des concreteRoles aux concreteTask
	   
	   /* pa =  ps.getParticipantDao().getParticipant(pa.getLogin());
	    if (pa != null) {
		    ConcreteTaskDescriptorService ts = (ConcreteTaskDescriptorService) ctx.getBean("ConcreteTaskDescriptorService");
			
			List<ConcreteTaskDescriptor> lctds =  ts.getConcreteTaskDescriptorDao().getAllConcreteTaskDescriptors();
				 
		    for (ConcreteTaskDescriptor ctd : lctds) {	    
		    	ts.affectedConcreteTaskDescriptor(ctd, pa);
		    }
	    }*/
	    
	    
	}
}
