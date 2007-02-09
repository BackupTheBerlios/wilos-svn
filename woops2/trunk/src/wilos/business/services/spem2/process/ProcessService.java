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

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.phase.PhaseService;
import wilos.business.services.spem2.role.RoleDescriptorService;
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
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
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

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private BreakdownElementService breakdownElementService;

	private PhaseService phaseService;

	private IterationService iterationService;

	private ActivityService activityService;

	private TaskDescriptorService taskDescriptorService;

	private RoleDescriptorService roleDescriptorService;

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

	public Process spelpParsingXML(File _file) {
		Process spelpProcess = null;
		try {
			logger.debug("### ProcessService ### spelpParsingXML "
					+ _file.getAbsolutePath() + " abs path = "
					+ _file.getPath());
			String slash = System.getProperty("file.separator");
			    
			String path = _file.getAbsolutePath().substring(0,_file.getAbsolutePath().lastIndexOf(slash));
		   
			logger.debug("### ProcessService ### spelpParsingXML PATH == "+path);
			spelpProcess = XMLServices.getProcess(_file.getAbsolutePath(), path );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spelpProcess;
	}
	
	/**
     * @param _processId
     * @return
     */
    @Transactional(readOnly = true)
    public Project getEntireProject(String _projectId) {
            Project project = this.projectDao.getProject(_projectId);
            Set<ConcreteBreakdownElement> bdes = new HashSet<ConcreteBreakdownElement>();

            bdes.addAll(this.concreteBreakdownElementService.getAllBreakdownElementsFromProject(_projectId));
            project.removeAllConcreteBreakdownElements();
            project.addAllConcreteBreakdownElements(bdes);

            return project;
    }

	/**
	 *
	 * Method for saving a Process
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

		//  elements of collection getting
		List<Guidance> guid = new ArrayList<Guidance>();

		//	 dependencies erasing
		_process.getBreakdownElements().clear();
		_process.getPredecessors().clear();
		_process.getProjects().clear();
		_process.getSuccessors().clear();
		_process.getSuperActivities().clear();

		// save of the project
		this.processDao.saveOrUpdateProcess(_process);
		System.out.println("###Process vide sauve");

		// in function of element type
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				guid = this.parsePhase(ph, guid);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					guid = this.parseIteration(it, guid);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						guid = this.parseActivity(act, guid);
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							guid = this.parseRoleDescriptor(rd, guid);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							guid = this.parseTaskDescriptor(td, guid);
						}
					}
				}
			}
		}

		// saving of the attached guidances to the process
		for (Guidance g : guid) {
			this.parseGuidance(g);
		}

		// clone dependencies getting
		_process.addAllBreakdownElements(clone.getBreakdownElements());
		_process.addAllPredecessors(clone.getPredecessors());
		_process.addAllProjects(clone.getProjects());
		_process.addAllSuccessors(clone.getSuccessors());
		_process.addAllSuperActivities(clone.getSuperActivities());

		// update of the project
		this.processDao.saveOrUpdateProcess(_process);
		System.out.println("###Process sauve");
	}

	/**
	 *
	 * Method to parse a Phase
	 *
	 * @param _ph
	 */
	private List<Guidance> parsePhase(Phase _ph, List<Guidance> guid) {

		Phase clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _ph.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_ph.getBreakdownElements());

		// Guides
		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_ph.getGuidances());

		//	 clean of dependancies of _ph
		_ph.getBreakdownElements().clear();
		_ph.getPredecessors().clear();
		_ph.getSuccessors().clear();
		_ph.getSuperActivities().clear();
		_ph.getGuidances().clear();
		this.phaseDao.saveOrUpdatePhase(_ph);
		System.out.println("###Phase vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Iteration) {
				Iteration it = (Iteration) bde;
				tmp = this.parseIteration(it, tmp);
			} else {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					tmp = this.parseActivity(act, tmp);
				} else {
					if (bde instanceof RoleDescriptor) {
						RoleDescriptor rd = (RoleDescriptor) bde;
						tmp = this.parseRoleDescriptor(rd, tmp);
					} else {
						TaskDescriptor td = (TaskDescriptor) bde;
						tmp = this.parseTaskDescriptor(td, tmp);
					}
				}
			}
		}

		_ph.addAllBreakdownElements(clone.getBreakdownElements());
		_ph.addAllPredecessors(clone.getPredecessors());
		_ph.addAllSuccessors(clone.getSuccessors());
		_ph.addAllSuperActivities(clone.getSuperActivities());
		_ph.addAllGuidances(clone.getGuidances());

		// Parse for guidances
		this.phaseDao.saveOrUpdatePhase(_ph);
		System.out.println("###Phase sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse an Iteration
	 *
	 * @param _it
	 */
	private List<Guidance> parseIteration(Iteration _it, List<Guidance> guid) {

		Iteration clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _it.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_it.getBreakdownElements());

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_it.getGuidances());

		_it.getBreakdownElements().clear();
		_it.getPredecessors().clear();
		_it.getSuccessors().clear();
		_it.getSuperActivities().clear();
		_it.getGuidances().clear();
		this.iterationDao.saveOrUpdateIteration(_it);
		System.out.println("###Iteration vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				tmp = this.parseActivity(act, tmp);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					tmp = this.parseRoleDescriptor(rd, tmp);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					tmp = this.parseTaskDescriptor(td, tmp);
				}
			}
		}

		_it.addAllBreakdownElements(clone.getBreakdownElements());
		_it.addAllPredecessors(clone.getPredecessors());
		_it.addAllSuccessors(clone.getSuccessors());
		_it.addAllSuperActivities(clone.getSuperActivities());
		_it.addAllGuidances(clone.getGuidances());

		this.iterationDao.saveOrUpdateIteration(_it);
		System.out.println("###Iteration sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse an Activity
	 *
	 * @param _act
	 */
	private List<Guidance> parseActivity(Activity _act, List<Guidance> guid) {

		Activity clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _act.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_act.getBreakdownElements());

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_act.getGuidances());

		_act.getBreakdownElements().clear();
		_act.getPredecessors().clear();
		_act.getSuccessors().clear();
		_act.getSuperActivities().clear();
		_act.getGuidances().clear();
		this.activityDao.saveOrUpdateActivity(_act);
		System.out.println("###Activity vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				tmp = this.parseActivity(act, tmp);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					tmp = this.parseRoleDescriptor(rd, tmp);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					tmp = this.parseTaskDescriptor(td, tmp);
				}
			}
		}

		_act.addAllBreakdownElements(clone.getBreakdownElements());
		_act.addAllPredecessors(clone.getPredecessors());
		_act.addAllSuccessors(clone.getSuccessors());
		_act.addAllSuperActivities(clone.getSuperActivities());
		_act.addAllGuidances(clone.getGuidances());

		this.activityDao.saveOrUpdateActivity(_act);
		System.out.println("###Activity sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse a RoleDescriptor
	 *
	 * @param _rd
	 */
	private List<Guidance> parseRoleDescriptor(RoleDescriptor _rd, List<Guidance> guid) {

		RoleDescriptor clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _rd.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		RoleDefinition rdef = _rd.getRoleDefinition();

		_rd.getAdditionalTasks().clear();
		_rd.getConcreteRoleDescriptors().clear();
		_rd.getPrimaryTasks().clear();
		_rd.getSuperActivities().clear();
		_rd.setRoleDefinition(null);

		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(_rd);
		System.out.println("###RoleDescriptor vide sauve");

		if (rdef != null) {
			tmp = this.parseRoleDefinition(rdef, tmp);
		}

		_rd.addAllAdditionalTasks(clone.getAdditionalTasks());
		_rd.addAllConcreteRoleDescriptors(clone.getConcreteRoleDescriptors());
		_rd.addAllPrimaryTasks(clone.getPrimaryTasks());
		_rd.addAllSuperActivities(clone.getSuperActivities());
		_rd.setRoleDefinition(clone.getRoleDefinition());

		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(_rd);
		System.out.println("###RoleDescriptor sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse a RoleDefinition
	 *
	 * @param _rdef
	 */
	private List<Guidance> parseRoleDefinition(RoleDefinition _rdef, List<Guidance> guid) {

		RoleDefinition clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _rdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_rdef.getGuidances());

		_rdef.getRoleDescriptors().clear();
		_rdef.getGuidances().clear();

		this.roleDefinitionDao.saveOrUpdateRoleDefinition(_rdef);
		System.out.println("###RoleDefinition vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		_rdef.addAllRoleDescriptors(clone.getRoleDescriptors());
		_rdef.addAllGuidances(clone.getGuidances());

		this.roleDefinitionDao.saveOrUpdateRoleDefinition(_rdef);
		System.out.println("###RoleDefinition sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse a TaskDescriptor
	 *
	 * @param _td
	 */
	private List<Guidance> parseTaskDescriptor(TaskDescriptor _td, List<Guidance> guid) {

		TaskDescriptor clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _td.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		TaskDefinition tdef = _td.getTaskDefinition();

		_td.getAdditionalRoles().clear();
		_td.getConcreteTaskDescriptors().clear();
		_td.getPredecessors().clear();
		_td.getSuccessors().clear();
		_td.getSuperActivities().clear();
		_td.setMainRole(null);
		_td.setTaskDefinition(null);

		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(_td);
		System.out.println("###TaskDescriptor vide sauve");

		if (tdef != null) {
			tmp = this.parseTaskDefinition(tdef, tmp);
		}

		_td.addAllAdditionalRoles(clone.getAdditionalRoles());
		_td.addAllConcreteTaskDescriptors(clone.getConcreteTaskDescriptors());
		_td.addAllPredecessors(clone.getPredecessors());
		_td.addAllSuccessors(clone.getSuccessors());
		_td.addAllSuperActivities(clone.getSuperActivities());
		_td.setMainRole(clone.getMainRole());
		_td.setTaskDefinition(clone.getTaskDefinition());

		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(_td);
		System.out.println("###TaskDescriptor sauve");

		return tmp;
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _tdef
	 */
	private List<Guidance> parseTaskDefinition(TaskDefinition _tdef, List<Guidance> guid) {

		TaskDefinition clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _tdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_tdef.getGuidances());

		List<Step> steps = new ArrayList<Step>();
		// recuperation des breakdownelements du processus
		steps.addAll(_tdef.getSteps());

		_tdef.getSteps().clear();
		_tdef.getTaskDescriptors().clear();
		_tdef.getGuidances().clear();

		this.taskDefinitionDao.saveOrUpdateTaskDefinition(_tdef);
		System.out.println("###TaskDefinition vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (Step step : steps) {
			this.parseStep(step);
		}

		_tdef.addAllSteps(clone.getSteps());
		_tdef.addAllTaskDesciptors(clone.getTaskDescriptors());
		_tdef.addAllGuidances(clone.getGuidances());

		this.taskDefinitionDao.saveOrUpdateTaskDefinition(_tdef);
		System.out.println("###TaskDefinition sauve");

		return tmp;
	}

	/**
	 *
	 * Method to parse a Step
	 *
	 * @param _step
	 */
	private void parseStep(Step _step) {

		this.stepDao.saveOrUpdateStep(_step);
		System.out.println("###Step sauve");
	}

	/**
	 *
	 * Method to parse a Guidance
	 *
	 * @param _step
	 */
	private void parseGuidance(Guidance _guidance) {

		this.guidanceDao.saveOrUpdateGuidance(_guidance);
		System.out.println("###Guidance sauve");
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
	 * Create and associate ConcreteTaskDescriptors from TaskDescriptors of the
	 * process whose id is id_process to the project whose id is id_project
	 *
	 * @param id_process
	 * @param id_project
	 */
	public void projectInstanciation(Project _project) {

		Process p = this.processDao.getProcess(_project.getProcess().getId());

		// elements of collection getting
		List<BreakdownElement> forInstanciation = new ArrayList<BreakdownElement>();
		forInstanciation.addAll(p.getBreakdownElements());

		Set<ConcreteBreakdownElement> tmp = new HashSet<ConcreteBreakdownElement>();

		for (BreakdownElement bde : forInstanciation) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				tmp.add(this.phaseService.phaseInstanciation(_project, ph));
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					tmp.add(this.iterationService.iterationInstanciation(_project, it));
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						tmp.add(this.activityService.activityInstanciation(_project, act));
					}/* else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							this.roleDescriptorService.roleDescriptorInstanciation(_project, rd);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							this.taskDescriptorService.taskDescriptorInstanciation(_project, td);
						}
					}*/
				}
			}
		}

		_project.addAllConcreteBreakdownElements(tmp);

		this.projectDao.saveOrUpdateProject(_project);
		System.out.println("### Project update");
	}

	/**
	 *
	 * @param _act
	 * @return
	 */
	/*private List<BreakdownElement> getInstanciableBreakdownElement(Activity _act) {

		// elements of collection getting
		Set<BreakdownElement> bdes = _act.getBreakdownElements();

		List<BreakdownElement> tmp = new ArrayList<BreakdownElement>();
		tmp.add(_act);

		// in function of element type
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				tmp.addAll(this.getInstanciableBreakdownElement(ph));
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					tmp.addAll(this.getInstanciableBreakdownElement(it));
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						tmp.addAll(this.getInstanciableBreakdownElement(act));
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							tmp.add(td);
						} else {
							if (bde instanceof RoleDescriptor) {
								RoleDescriptor rd = (RoleDescriptor) bde;
								tmp.add(rd);
							}
						}
					}
				}
			}
		}
		return tmp;
	}*/

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

	public void setBreakdownElementService(
			BreakdownElementService _breakdownElementService) {
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

	/**
	 * @return the roleDescriptorService
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * @param roleDescriptorService the roleDescriptorService to set
	 */
	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService ;
	}

	/**
	 * Setter of concreteBreakdownElementService.
	 *
	 * @param concreteBreakdownElementService The concreteBreakdownElementService to set.
	 */
	public void setConcreteBreakdownElementService(ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService ;
	}
}
