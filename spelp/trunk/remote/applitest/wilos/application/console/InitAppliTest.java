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
//		 Notre fabrique SPRING permettant l'acc�s aux beans d�clar�s
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		// import des processus
		InitAppliService am = (InitAppliService) factory.getBean("InitAppliService");
		am.initAppli();
		
	    
	}
}
