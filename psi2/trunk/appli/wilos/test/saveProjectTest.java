package wilos.test ;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.project.ProjectService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

/**
 * @author Administrateur
 *
 * This class represents ... TODO
 *
 */
public class saveProjectTest {

	/**
	 * TODO Method description
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Project project = new Project();
		ProjectDao projectDao = (ProjectDao)ctx.getBean("ProjectDao") ;
		ProjectService projectService = (ProjectService)ctx.getBean("ProjectService") ;
		project.setName("projTest");
		projectDao.saveOrUpdateProject(project);
		projectService.saveProcessProjectAffectation(((Process)projectService.getProcessService().getProcessesList().get(0)),project);
	}

}
