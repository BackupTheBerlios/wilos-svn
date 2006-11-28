package woops2.business.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spelp.xml.parser.XMLParser;
import woops2.hibernate.activity.ActivityDao;
import woops2.hibernate.breakdownelement.BreakdownElementDao;
import woops2.hibernate.element.ElementDao;
import woops2.hibernate.process.ProcessDao;
import woops2.hibernate.role.RoleDefinitionDao;
import woops2.hibernate.role.RoleDescriptorDao;
import woops2.hibernate.task.StepDao;
import woops2.hibernate.task.TaskDefinitionDao;
import woops2.hibernate.task.TaskDescriptorDao;
import woops2.hibernate.workbreakdownelement.WorkBreakdownElementDao;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.element.Element;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.Step;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

/**
 * ProcessService is a transactional class, that manage operations about process, requested by web
 * pages (?)
 * 
 * @author eperico
 * @author soosuske
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessService {

	private ActivityDao activityDao;

	private BreakdownElementDao breakdownElementDao;

	private ElementDao elementDao;

	private ProcessDao processDao;

	private RoleDefinitionDao roleDefinitionDao;

	private RoleDescriptorDao roleDescriptorDao;

	private StepDao stepDao;

	private TaskDefinitionDao taskDefinitionDao;

	private TaskDescriptorDao taskDescriptorDao;

	private WorkBreakdownElementDao workBreakdownElementDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public void TestSpelpParsingXML() {
		File file = new File("C:\\scrum.xml");
		Process p = this.SpelpParsingXML(file);
		System.out.println("Process = " + p);
		this.SaveProcessService(p);
	}

	public Process SpelpParsingXML(File _file) {
		// file = new File("C:\Documents and
		// Settings\Moi\Bureau\Spelp-importXML\ressources\scrum.xml");
		String absolutPath = _file.getAbsolutePath();
		System.out.println("absolutPath: " + absolutPath);

		try {
			Process spelpProcess = XMLParser.getProcess(_file);
			return spelpProcess;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param _process
	 */
	// FIXME Objets non enregistres : org.hibernate.TransientObjectException: woops2.model.breakdownelement.BreakdownElement
	public void SaveProcessService(Process _process) {
		List<Element> elements = new ArrayList<Element>();
		Set<BreakdownElement> bdes = _process.getBreakDownElements();
		int elementAdded = 0;
		logger.debug("### SaveProcessService ### elements="+elements);
		logger.debug("### SaveProcessService ### bdes="+bdes+" size="+bdes.size());
		// add element of process in elements
		for (BreakdownElement breakdownElement : bdes) {
			logger.debug("### SaveProcessService ### for bdes -> bde ="+breakdownElement);
			if (breakdownElement instanceof TaskDescriptor) {
				logger.debug("### SaveProcessService ### bde iof TaskDescriptor ="+breakdownElement);
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				TaskDefinition taskDefinition = taskDescriptor.getTaskDefinition();
				logger.debug("### SaveProcessService ### bde --> taskDefinition ="+taskDefinition);
				if (taskDefinition != null) {
					for (Step step : taskDefinition.getSteps()) {
						logger.debug("### SaveProcessService ### for steps ... ="+taskDefinition.getSteps());
						if (step != null)
							elements.add(step);
							elementAdded++;
						logger.debug("### SaveProcessService ### elements -> add step ="+step);
					}
					elements.add(taskDefinition);
					elementAdded++;
					logger.debug("### SaveProcessService ### elements -> add taskDefinition ="+taskDefinition);
				}
				elements.add(taskDescriptor);
				elementAdded++;
				logger.debug("### SaveProcessService ### elements -> add taskDescriptor ="+taskDescriptor);
			} else if (breakdownElement instanceof RoleDescriptor) {
				logger.debug("### SaveProcessService ### breakdownElement iof RoleDescriptor ="+breakdownElement);
				RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement;
				logger.debug("### SaveProcessService ### RoleDescriptor ="+roleDescriptor);
				elements.add(roleDescriptor.getRoleDefinition());
				logger.debug("### SaveProcessService ### elements -> add roleDescriptor.getRoleDefinition() ="+roleDescriptor.getRoleDefinition());
				elementAdded++;
				elements.add(roleDescriptor);
				logger.debug("### SaveProcessService ### elements -> add roleDescriptor ="+roleDescriptor);
				elementAdded++;
			}
		}
		logger.debug("### SaveProcessService ### elements -> add _process ="+_process);
		elementAdded++;
		elements.add(_process);
		
		logger.debug("### SaveProcessService ### total elements added ="+elementAdded);
		logger.debug("### SaveProcessService ### elements.size ="+elements.size());
		
		// save in the database
		for (Element element : elements) {
			if (element instanceof Step) {
				logger.debug("### SaveProcessService ### save Step ="+(Step) element);
				this.stepDao.saveOrUpdateStep((Step) element);
			} else if (element instanceof TaskDefinition) {
				logger.debug("### SaveProcessService ### save TaskDefinition ="+(TaskDefinition) element);
				this.taskDefinitionDao
						.saveOrUpdateTaskDefinition((TaskDefinition) element);
			} else if (element instanceof TaskDescriptor) {
				logger.debug("### SaveProcessService ### save TaskDescriptor ="+(TaskDescriptor) element);
				this.taskDescriptorDao
						.saveOrUpdateTaskDescriptor((TaskDescriptor) element);
			} else if (element instanceof RoleDescriptor) {
				logger.debug("### SaveProcessService ### save RoleDescriptor ="+(RoleDescriptor) element);
				this.roleDescriptorDao
						.saveOrUpdateRoleDescriptor((RoleDescriptor) element);
			} else if (element instanceof RoleDefinition) {
				logger.debug("### SaveProcessService ### save RoleDefinition ="+(RoleDefinition) element);
				this.roleDefinitionDao
						.saveOrUpdateRole((RoleDefinition) element);
			} else if (element instanceof Process) {
				logger.debug("### SaveProcessService ### save Process ="+(Process) element);
				this.processDao.saveOrUpdateProcess((Process) element);
			}
		}
	}

	/**
	 * Return processes list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Process> getProcessesList() {
		return this.processDao.getAllProcesses();
	}

	/**
	 * Save process
	 * 
	 * @param _process
	 */
	public void saveProcess(Process _process) {
		this.processDao.saveOrUpdateProcess(_process);
	}

	/**
	 * Getter of processDao.
	 * 
	 * @return the processDao.
	 */
	public ProcessDao getProcessDao() {
		return processDao;
	}

	/**
	 * Setter of processDao.
	 * 
	 * @param _processDao
	 *            The processDao to set.
	 */
	public void setProcessDao(ProcessDao _processDao) {
		this.processDao = _processDao;
	}

	/**
	 * Getter of activityDao.
	 * 
	 * @return the activityDao.
	 */
	public ActivityDao getActivityDao() {
		return activityDao;
	}

	/**
	 * Setter of activityDao.
	 * 
	 * @param activityDao
	 *            The activityDao to set.
	 */
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	/**
	 * Getter of breakdownElementDao.
	 * 
	 * @return the breakdownElementDao.
	 */
	public BreakdownElementDao getBreakdownElementDao() {
		return breakdownElementDao;
	}

	/**
	 * Setter of breakdownElementDao.
	 * 
	 * @param breakdownElementDao
	 *            The breakdownElementDao to set.
	 */
	public void setBreakdownElementDao(BreakdownElementDao breakdownElementDao) {
		this.breakdownElementDao = breakdownElementDao;
	}

	/**
	 * Getter of elementDao.
	 * 
	 * @return the elementDao.
	 */
	public ElementDao getElementDao() {
		return elementDao;
	}

	/**
	 * Setter of elementDao.
	 * 
	 * @param elementDao
	 *            The elementDao to set.
	 */
	public void setElementDao(ElementDao elementDao) {
		this.elementDao = elementDao;
	}

	/**
	 * Getter of roleDefinitionDao.
	 * 
	 * @return the roleDefinitionDao.
	 */
	public RoleDefinitionDao getRoleDefinitionDao() {
		return roleDefinitionDao;
	}

	/**
	 * Setter of roleDefinitionDao.
	 * 
	 * @param roleDefinitionDao
	 *            The roleDefinitionDao to set.
	 */
	public void setRoleDefinitionDao(RoleDefinitionDao roleDefinitionDao) {
		this.roleDefinitionDao = roleDefinitionDao;
	}

	/**
	 * Getter of roleDescriptorDao.
	 * 
	 * @return the roleDescriptorDao.
	 */
	public RoleDescriptorDao getRoleDescriptorDao() {
		return roleDescriptorDao;
	}

	/**
	 * Setter of roleDescriptorDao.
	 * 
	 * @param roleDescriptorDao
	 *            The roleDescriptorDao to set.
	 */
	public void setRoleDescriptorDao(RoleDescriptorDao roleDescriptorDao) {
		this.roleDescriptorDao = roleDescriptorDao;
	}

	/**
	 * Getter of stepDao.
	 * 
	 * @return the stepDao.
	 */
	public StepDao getStepDao() {
		return stepDao;
	}

	/**
	 * Setter of stepDao.
	 * 
	 * @param stepDao
	 *            The stepDao to set.
	 */
	public void setStepDao(StepDao stepDao) {
		this.stepDao = stepDao;
	}

	/**
	 * Getter of taskDefinitionDao.
	 * 
	 * @return the taskDefinitionDao.
	 */
	public TaskDefinitionDao getTaskDefinitionDao() {
		return taskDefinitionDao;
	}

	/**
	 * Setter of taskDefinitionDao.
	 * 
	 * @param taskDefinitionDao
	 *            The taskDefinitionDao to set.
	 */
	public void setTaskDefinitionDao(TaskDefinitionDao taskDefinitionDao) {
		this.taskDefinitionDao = taskDefinitionDao;
	}

	/**
	 * Getter of taskDescriptorDao.
	 * 
	 * @return the taskDescriptorDao.
	 */
	public TaskDescriptorDao getTaskDescriptorDao() {
		return taskDescriptorDao;
	}

	/**
	 * Setter of taskDescriptorDao.
	 * 
	 * @param taskDescriptorDao
	 *            The taskDescriptorDao to set.
	 */
	public void setTaskDescriptorDao(TaskDescriptorDao taskDescriptorDao) {
		this.taskDescriptorDao = taskDescriptorDao;
	}

	/**
	 * Getter of workBreakdownElementDao.
	 * 
	 * @return the workBreakdownElementDao.
	 */
	public WorkBreakdownElementDao getWorkBreakdownElementDao() {
		return workBreakdownElementDao;
	}

	/**
	 * Setter of workBreakdownElementDao.
	 * 
	 * @param workBreakdownElementDao
	 *            The workBreakdownElementDao to set.
	 */
	public void setWorkBreakdownElementDao(
			WorkBreakdownElementDao workBreakdownElementDao) {
		this.workBreakdownElementDao = workBreakdownElementDao;
	}
}
