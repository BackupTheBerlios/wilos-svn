package wilos.business.services.spem2.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.phase.PhaseService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.business.services.util.xml.parser.XMLServices;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.hibernate.spem2.breakdownelement.BreakdownElementDao;
import wilos.hibernate.spem2.element.ElementDao;
import wilos.hibernate.spem2.guide.GuidanceDao;
import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.hibernate.spem2.task.StepDao;
import wilos.hibernate.spem2.task.TaskDefinitionDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * ProcessService is a transactional class, that manage operations about
 * process, requested by web pages (?)
 *
 * @author eperico
 * @author soosuske
 * @author Sebastien
 * @author deder
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessService {

	private BreakdownElementService breakdownElementService;

	private PhaseService phaseService;

	private IterationService iterationService;

	private ActivityService activityService;

	private TaskDescriptorService taskDescriptorService;

	private ActivityDao activityDao;

	private PhaseDao phaseDao;

	private IterationDao iterationDao;

	private BreakdownElementDao breakdownElementDao;

	private ElementDao elementDao;

	private ProcessDao processDao;

	private RoleDefinitionDao roleDefinitionDao;

	private RoleDescriptorDao roleDescriptorDao;

	private StepDao stepDao;

	private TaskDefinitionDao taskDefinitionDao;

	private TaskDescriptorDao taskDescriptorDao;

	private WorkBreakdownElementDao workBreakdownElementDao;

	private ProjectDao projectDao;
	
	private GuidanceDao guidanceDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	//private GuidelineDao guidelineDao;

	public Process spelpParsingXML(File _file) {
		Process spelpProcess = null;
		try {
			logger.debug("### ProcessService ### spelpParsingXML "+_file.getAbsolutePath()+" abs path = "+_file.getPath());
			spelpProcess = XMLServices.getProcess(_file.getAbsolutePath(), this.getPathFromFile(_file.getAbsolutePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spelpProcess;
	}

	private String getPathFromFile(String _path) {
		logger.debug("### ProcessService ### getPathFromFile "+_path);
		String[] pathTab = _path.split("/");
		String path = "";
		for (String s : pathTab){
			path += "/";
			path +=  s;
		}
		return path;
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _process
	 */
	public void saveProcess(Process _process) {

		Process clone = null;

		// clone creation for the save of the dependencies
		try {
			clone = _process.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		// elements of collection getting
		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_process.getBreakdownElements());

		// in function of element type
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				this.parsePhase(ph);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					this.parseIteration(it);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						this.parseActivity(act);
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							this.parseRoleDescriptor(rd);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							this.parseTaskDescriptor(td);
						}
					}
				}
			}
		}

		// dependencies erasing
		_process.getBreakdownElements().clear();
		_process.getPredecessors().clear();
		_process.getProjects().clear();
		_process.getSuccessors().clear();
		_process.getSuperActivities().clear();

		// save of the project
		this.processDao.saveOrUpdateProcess(_process);
		System.out.println("###Process sauve");

		// clone dependencies getting
		_process.addAllBreakdownElements(clone.getBreakdownElements());
		_process.addAllPredecessors(clone.getPredecessors());
		_process.addAllProjects(clone.getProjects());
		_process.addAllSuccessors(clone.getSuccessors());
		_process.addAllSuperActivities(clone.getSuperActivities());

		// update of the project
		this.processDao.saveOrUpdateProcess(_process);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _ph
	 */
	private void parsePhase(Phase _ph) {

		Phase clone = null;
		// Guides
		Set<Guidance> guidances = _ph.getGuidances();
		this.saveGuidances(guidances);
		
		try {
			clone = _ph.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_ph.getBreakdownElements());

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Iteration) {
				Iteration it = (Iteration) bde;
				this.parseIteration(it);
			} else {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					this.parseActivity(act);
				} else {
					if (bde instanceof RoleDescriptor) {
						RoleDescriptor rd = (RoleDescriptor) bde;
						this.parseRoleDescriptor(rd);
					} else {
						TaskDescriptor td = (TaskDescriptor) bde;
						this.parseTaskDescriptor(td);
					}
				}
			}
		}

		// clean of dependancies of _ph
		_ph.getBreakdownElements().clear();
		_ph.getPredecessors().clear();
		_ph.getSuccessors().clear();
		_ph.getSuperActivities().clear();
		_ph.getGuidances().clear();
		this.phaseDao.saveOrUpdatePhase(_ph);
		System.out.println("###Phase sauve");

		_ph.addAllBreakdownElements(clone.getBreakdownElements());
		_ph.addAllPredecessors(clone.getPredecessors());
		_ph.addAllSuccessors(clone.getSuccessors());
		_ph.addAllSuperActivities(clone.getSuperActivities());
		_ph.addAllGuidances(guidances);
		
		// Parse for guidances
		this.phaseDao.saveOrUpdatePhase(_ph);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _it
	 */
	private void parseIteration(Iteration _it) {

		Iteration clone = null;
		
		Set<Guidance> guidances = _it.getGuidances();
		this.saveGuidances(guidances);
		
		try {
			clone = _it.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_it.getBreakdownElements());

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				this.parseActivity(act);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					this.parseRoleDescriptor(rd);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					this.parseTaskDescriptor(td);
				}
			}
		}

		_it.getBreakdownElements().clear();
		_it.getPredecessors().clear();
		_it.getSuccessors().clear();
		_it.getSuperActivities().clear();
		_it.getGuidances().clear();
		this.iterationDao.saveOrUpdateIteration(_it);
		System.out.println("###Iteration sauve");

		_it.addAllBreakdownElements(clone.getBreakdownElements());
		_it.addAllPredecessors(clone.getPredecessors());
		_it.addAllSuccessors(clone.getSuccessors());
		_it.addAllSuperActivities(clone.getSuperActivities());
		_it.addAllGuidances(guidances);
		
		this.iterationDao.saveOrUpdateIteration(_it);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _act
	 */
	private void parseActivity(Activity _act) {

		Activity clone = null;
		Set<Guidance> guidances = _act.getGuidances();
		this.saveGuidances(guidances);
		
		try {
			clone = _act.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_act.getBreakdownElements());

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				this.parseActivity(act);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					this.parseRoleDescriptor(rd);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					this.parseTaskDescriptor(td);
				}
			}
		}

		_act.getBreakdownElements().clear();
		_act.getPredecessors().clear();
		_act.getSuccessors().clear();
		_act.getSuperActivities().clear();
		_act.getGuidances().clear();
		this.activityDao.saveOrUpdateActivity(_act);
		System.out.println("###Activity sauve");

		_act.addAllBreakdownElements(clone.getBreakdownElements());
		_act.addAllPredecessors(clone.getPredecessors());
		_act.addAllSuccessors(clone.getSuccessors());
		_act.addAllSuperActivities(clone.getSuperActivities());
		_act.addAllGuidances(guidances);

		this.activityDao.saveOrUpdateActivity(_act);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _rd
	 */
	private void parseRoleDescriptor(RoleDescriptor _rd) {

		RoleDescriptor clone = null;

		try {
			clone = _rd.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		RoleDefinition rdef = _rd.getRoleDefinition();

		if (rdef != null) {
			this.parseRoleDefinition(rdef);
		}

		_rd.getAdditionalTasks().clear();
		_rd.getConcreteRoleDescriptors().clear();
		_rd.getPrimaryTasks().clear();
		_rd.getSuperActivities().clear();
		_rd.setRoleDefinition(null);

		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(_rd);
		System.out.println("###RoleDescriptor sauve");

		_rd.addAllAdditionalTasks(clone.getAdditionalTasks());
		_rd.addAllConcreteRoleDescriptors(clone.getConcreteRoleDescriptors());
		_rd.addAllPrimaryTasks(clone.getPrimaryTasks());
		_rd.addAllSuperActivities(clone.getSuperActivities());
		_rd.setRoleDefinition(clone.getRoleDefinition());

		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(_rd);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _rdef
	 */
	private void parseRoleDefinition(RoleDefinition _rdef) {

		RoleDefinition clone = null;
		
		Set<Guidance> guidances = _rdef.getGuidances();
		this.saveGuidances(guidances);
		
		try {
			clone = _rdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		_rdef.getRoleDescriptors().clear();
		_rdef.getGuidances().clear();

		this.roleDefinitionDao.saveOrUpdateRoleDefinition(_rdef);
		System.out.println("###RoleDefinition sauve");

		_rdef.addAllRoleDescriptors(clone.getRoleDescriptors());
		_rdef.addAllGuidances(guidances);

		this.roleDefinitionDao.saveOrUpdateRoleDefinition(_rdef);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _td
	 */
	private void parseTaskDescriptor(TaskDescriptor _td) {

		TaskDescriptor clone = null;

		try {
			clone = _td.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		TaskDefinition tdef = _td.getTaskDefinition();

		if (tdef != null) {
			this.parseTaskDefinition(tdef);
		}

		_td.getAdditionalRoles().clear();
		_td.getConcreteTaskDescriptors().clear();
		_td.getPredecessors().clear();
		_td.getSuccessors().clear();
		_td.getSuperActivities().clear();
		_td.setMainRole(null);
		_td.setTaskDefinition(null);

		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(_td);
		System.out.println("###TaskDescriptor sauve");

		_td.addAllAdditionalRoles(clone.getAdditionalRoles());
		_td.addAllConcreteTaskDescriptors(clone.getConcreteTaskDescriptors());
		_td.addAllPredecessors(clone.getPredecessors());
		_td.addAllSuccessors(clone.getSuccessors());
		_td.addAllSuperActivities(clone.getSuperActivities());
		_td.setMainRole(clone.getMainRole());
		_td.setTaskDefinition(clone.getTaskDefinition());

		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(_td);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _tdef
	 */
	private void parseTaskDefinition(TaskDefinition _tdef) {

		TaskDefinition clone = null;

		Set<Guidance> guidances = _tdef.getGuidances();
		this.saveGuidances(guidances);
		
		try {
			clone = _tdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<Step> steps = new ArrayList<Step>();
		// recuperation des breakdownelements du processus
		steps.addAll(_tdef.getSteps());
		
		for (Step step : steps) {
			this.parseStep(step);
		}

		_tdef.getSteps().clear();
		_tdef.getTaskDescriptors().clear();
		_tdef.getGuidances().clear();
		
		this.taskDefinitionDao.saveOrUpdateTaskDefinition(_tdef);
		System.out.println("###TaskDefinition sauve");

		_tdef.addAllSteps(clone.getSteps());
		_tdef.addAllTaskDesciptors(clone.getTaskDescriptors());
		_tdef.addAllGuidances(guidances);
		
		this.taskDefinitionDao.saveOrUpdateTaskDefinition(_tdef);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _step
	 */
	private void parseStep(Step _step) {

		Step clone = null;

		try {
			clone = _step.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		_step.setTaskDefinition(null);
		this.stepDao.saveOrUpdateStep(_step);

		_step.setTaskDefinition(clone.getTaskDefinition());
		this.stepDao.saveOrUpdateStep(_step);
	}
	
	/**
	 *
	 * TODO Method description
	 *
	 * @param _step
	 */
	private void parseGuidance(Guidance _guidance) {

		Guidance clone = null;

		try {
			clone = _guidance.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		_guidance.setActivity(null);
		_guidance.setTaskdefinition(null);
		_guidance.setRoledefinition(null);
		

		this.guidanceDao.saveOrUpdateGuidance(_guidance);

		_guidance.setActivity(clone.getActivity());
		_guidance.setTaskdefinition(clone.getTaskdefinition());
		_guidance.setRoledefinition(clone.getRoledefinition());
		
		this.guidanceDao.saveOrUpdateGuidance(_guidance);
	}
	
	/**
	 * Method for saving all guidances in the submitted set
	 * 
	 * @param _guidances
	 */
	private void saveGuidances(Set<Guidance> _guidances) {
		if ((_guidances != null) && (_guidances.size() > 0))
			for (Guidance g : _guidances) {
				this.parseGuidance(g);
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
	 *
	 * Create and associate COncreteTaskDescriptors from TaskDescriptors of the process whose id is id_process
	 * to the project whose id is id_project
	 *
	 * @param id_process
	 * @param id_project
	 */
	public void projectInstanciation(Project _project) {

		Process p = this.processDao.getProcess(_project.getProcess().getId());

		List<TaskDescriptor> forSaving = this.getTaskDescriptors(p);

		for (TaskDescriptor td : forSaving) {
			this.taskDescriptorService.taskDescriptorInstanciation(_project, td);
		}
	}

	/**
	 *
	 * @param _act
	 * @return
	 */
	private List<TaskDescriptor> getTaskDescriptors(Activity _act) {

		// elements of collection getting
		Set<BreakdownElement> bdes = _act.getBreakdownElements();

		List<TaskDescriptor> tds = new ArrayList<TaskDescriptor>();

		// in function of element type
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				tds.addAll(this.getTaskDescriptors(ph));
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					tds.addAll(this.getTaskDescriptors(it));
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						tds.addAll(this.getTaskDescriptors(act));
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							tds.add(td);
						}
					}
				}
			}
		}
		return tds;
	}

	/**
	 * @param _processId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Process getEntireProcess(String _processId) {
		Process process = this.processDao.getProcess(_processId);
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.breakdownElementService.getBreakdownElementsFromProcess(_processId));
		process.addAllBreakdownElements(bdes);

		return process;
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

	public BreakdownElementService getBreakdownElementService() {
		return this.breakdownElementService;
	}

	public void setBreakdownElementService(BreakdownElementService _breakdownElementService) {
		this.breakdownElementService = _breakdownElementService;
	}

	public PhaseService getPhaseService() {
		return this.phaseService;
	}

	public void setPhaseService(PhaseService _phaseService) {
		this.phaseService = _phaseService;
	}

	public ActivityService getActivityService() {
		return this.activityService;
	}

	public void setActivityService(ActivityService _activityService) {
		this.activityService = _activityService;
	}

	public IterationService getIterationService() {
		return this.iterationService;
	}

	public void setIterationService(IterationService _iterationService) {
		this.iterationService = _iterationService;
	}

	public TaskDescriptorService getTaskDescriptorService() {
		return this.taskDescriptorService;
	}

	public void setTaskDescriptorService(
			TaskDescriptorService _taskDescriptorService) {
		this.taskDescriptorService = _taskDescriptorService;
	}

	public IterationDao getIterationDao() {
		return iterationDao;
	}

	public void setIterationDao(IterationDao iterationDao) {
		this.iterationDao = iterationDao;
	}

	public PhaseDao getPhaseDao() {
		return phaseDao;
	}

	public void setPhaseDao(PhaseDao phaseDao) {
		this.phaseDao = phaseDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public GuidanceDao getGuidanceDao() {
		return guidanceDao;
	}

	public void setGuidanceDao(GuidanceDao guidanceDao) {
		this.guidanceDao = guidanceDao;
	}
}
