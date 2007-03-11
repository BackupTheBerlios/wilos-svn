package wilos.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.misc.project.ProjectService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.process.Process;

/**
 * @author Administrateur
 *
 * This class represents ... TODO
 *
 */
public class AffectedParticipantsTest {

	/**
	 * TODO Method description
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ProjectDao projectDao = (ProjectDao)ctx.getBean("ProjectDao") ;
		ProjectService projectService = (ProjectService)ctx.getBean("ProjectService") ;
		Project project = projectDao.getProject("ff8080811054e428011054e44ea60001");
		Set<Participant> partList = projectService.getParticipants(project);
		System.out.println("Taille : " + partList.size());
	}

}
