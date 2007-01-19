
package wilos.business.services.process ;

import java.io.File ;
import java.util.ArrayList ;
import java.util.HashSet ;
import java.util.List ;
import java.util.Set ;
import java.util.SortedSet ;
import java.util.TreeSet ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.business.services.breakdownelement.BreakdownElementService ;
import wilos.business.services.task.StepService ;
import wilos.business.services.util.xml.parser.XMLParser ;
import wilos.hibernate.spem2.activity.ActivityDao ;
import wilos.hibernate.spem2.breakdownelement.BreakdownElementDao ;
import wilos.hibernate.spem2.element.ElementDao ;
import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.hibernate.spem2.process.ProcessDao ;
import wilos.hibernate.spem2.role.RoleDefinitionDao ;
import wilos.hibernate.spem2.role.RoleDescriptorDao ;
import wilos.hibernate.spem2.task.StepDao ;
import wilos.hibernate.spem2.task.TaskDefinitionDao ;
import wilos.hibernate.spem2.task.TaskDescriptorDao ;
import wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao ;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process ;
import wilos.model.spem2.role.RoleDefinition ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.Step ;
import wilos.model.spem2.task.TaskDefinition ;
import wilos.model.spem2.task.TaskDescriptor ;

/**
 * ProcessService is a transactional class, that manage operations about process, requested by web
 * pages (?)
 * 
 * @author eperico
 * @author soosuske
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessService {

	private BreakdownElementService breakdownElementService ;

	private StepService stepService ;

	private ActivityDao activityDao ;
	
	private PhaseDao phaseDao ;
	
	private IterationDao iterationDao ;

	private BreakdownElementDao breakdownElementDao ;

	private ElementDao elementDao ;

	private ProcessDao processDao ;

	private RoleDefinitionDao roleDefinitionDao ;

	private RoleDescriptorDao roleDescriptorDao ;

	private StepDao stepDao ;

	private TaskDefinitionDao taskDefinitionDao ;

	private TaskDescriptorDao taskDescriptorDao ;

	private WorkBreakdownElementDao workBreakdownElementDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	//declaration of list
	List<Step> stepList = new ArrayList<Step>() ;
	List<Step> stepListTmp = new ArrayList<Step>() ;
	List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>() ;
	List<TaskDefinition> taskDefinitionListTmp = new ArrayList<TaskDefinition>() ;
	List<TaskDescriptor> taskDescriptorList = new ArrayList<TaskDescriptor>() ;
	List<TaskDescriptor> taskDescriptorListTmp = new ArrayList<TaskDescriptor>() ;
	List<RoleDefinition> roleDefinitionList = new ArrayList<RoleDefinition>() ;
	List<RoleDefinition> roleDefinitionListTmp = new ArrayList<RoleDefinition>() ;
	List<Phase> phaseList = new ArrayList<Phase>() ;
	List<Phase> phaseListTmp = new ArrayList<Phase>() ;
	List<Iteration> iterationList = new ArrayList<Iteration>() ;
	List<Iteration> iterationListTmp = new ArrayList<Iteration>() ;
	List<RoleDescriptor> roleDescriptorList = new ArrayList<RoleDescriptor>() ;
	List<RoleDescriptor> roleDescriptorListTmp = new ArrayList<RoleDescriptor>() ;

	public Process spelpParsingXML(File _file) {
		Process spelpProcess = null;
		try{
			spelpProcess = XMLParser.getProcess(_file) ;
		}
		catch(Exception e){
			e.printStackTrace() ;
		}
		return spelpProcess;
	}

	/**
	 * 
	 * Method description test save of collections' process
	 * 
	 * @param _process
	 */
	public String saveProcess(Process _process) {

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>() ;
		bdes.addAll(_process.getBreakDownElements()) ;

		
		Process processTmp = new Process() ;

		String id_process = null ;

		int nbTD = 0 ;
		// add element of process in elements
		for(BreakdownElement breakdownElement : bdes){
			logger.debug("### TestSaveCollectionsProcess ### for bdes -> bde =" + breakdownElement) ;
			if(breakdownElement instanceof Activity){
				Activity mon = (Activity) breakdownElement;
				List<BreakdownElement> bdes1 = new ArrayList<BreakdownElement>() ;
				if(breakdownElement instanceof Phase){
					phaseList.add((Phase)breakdownElement);
					try{
						phaseListTmp.add(((Phase)breakdownElement).clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
				}
				else
				{
					if(breakdownElement instanceof Iteration){
						iterationList.add((Iteration)breakdownElement);
						try{
							iterationListTmp.add(((Iteration)breakdownElement).clone()) ;
						}
						catch(CloneNotSupportedException e){
							e.printStackTrace() ;
						}
					}
				}
				bdes1.addAll(mon.getBreakDownElements());
				this.miseAJListe(bdes1);
			}
			else
			{
				if(breakdownElement instanceof TaskDescriptor){
					logger.debug("### TestSaveCollectionsProcess ### bde instance of TaskDescriptor =" + breakdownElement) ;
					TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
					TaskDefinition taskDefinition = taskDescriptor.getTaskDefinition() ;
	
					if(taskDefinition != null){
						for(Step step : taskDefinition.getSteps()){
							logger.debug("### SaveProcessService ### for steps ... =" + taskDefinition.getSteps()) ;
							if(step != null)
								stepList.add(step) ;
							try{
								stepListTmp.add(step.clone()) ;
							}
							catch(CloneNotSupportedException e){
								e.printStackTrace() ;
							}
							logger.debug("TestSaveCollectionsProcess: stepList -> " + stepList) ;
							logger.debug("TestSaveCollectionsProcess: stepList.size -> " + stepList.size()) ;
						}
	
						taskDefinitionList.add(taskDefinition) ;
						try{
							taskDefinitionListTmp.add(taskDefinition.clone()) ;
						}
						catch(CloneNotSupportedException e){
							e.printStackTrace() ;
						}
						logger.debug("TestSaveCollectionsProcess: taskDefinitionList -> " + taskDefinitionList) ;
						logger.debug("TestSaveCollectionsProcess: taskDefinitionList.size -> " + taskDefinitionList.size()) ;
					}
					taskDescriptorList.add(taskDescriptor) ;
					try{
						taskDescriptorListTmp.add(taskDescriptor.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
					nbTD++ ;
					logger.debug("TestSaveCollectionsProcess: taskDescriptorList -> " + taskDescriptorList) ;
					logger.debug("TestSaveCollectionsProcess: taskDescriptorList.size -> " + taskDescriptorList.size()) ;
	
				}
				else if(breakdownElement instanceof RoleDescriptor){
					logger.debug("### SaveProcessService ### breakdownElement instance of RoleDescriptor =" + breakdownElement) ;
					RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement ;
					roleDescriptorList.add(roleDescriptor) ;
					try{
						roleDescriptorListTmp.add(roleDescriptor.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
	
					logger.debug("TestSaveCollectionsProcess: roleDescriptorList -> " + roleDescriptorList) ;
					logger.debug("TestSaveCollectionsProcess: roleDescriptorList.size -> " + roleDescriptorList.size()) ;
	
					RoleDefinition rd = roleDescriptor.getRoleDefinition() ;
					roleDefinitionList.add(rd) ;
					try{
						roleDefinitionListTmp.add(rd.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
					logger.debug("TestSaveCollectionsProcess: roleDefinitionList -> " + roleDefinitionList) ;
					logger.debug("TestSaveCollectionsProcess: roleDefinitionList.size -> " + roleDefinitionList.size()) ;
				}
				else{
					logger.error("### SaveProcessService ### unkown type of object from process !") ;
				}
			}
		}
		try{
			processTmp = _process.clone() ;
		}
		catch(CloneNotSupportedException e){
			e.printStackTrace() ;
		}
		logger.debug("TestSaveCollectionsProcess: processId -> " + _process.getGuid()) ;

		_process.getBreakDownElements().clear() ;
		this.processDao.saveOrUpdateProcess(_process) ;
		id_process = _process.getId() ;
		/*for(Phase ph : phaseList)
		{
			ph.getSuperActivities().clear();
			
			
		}
		for(Iteration it : iterationList)
		{
			it.getSuperActivities().clear();
			
		}*/
		for(RoleDescriptor rd : roleDescriptorList){
			rd.getAdditionalTasks().clear() ;
			rd.getSuperActivities().clear() ;
			// rd.getParticipants().clear();
			rd.getPrimaryTasks().clear() ;
			rd.setRoleDefinition(null) ;
			this.roleDescriptorDao.saveOrUpdateRoleDescriptor(rd) ;
		}
		for(RoleDefinition rdef : roleDefinitionList){
			rdef.getRoleDescriptors().clear() ;
			this.roleDefinitionDao.saveOrUpdateRoleDefinition(rdef) ;
		}
		for(TaskDescriptor td : taskDescriptorList){
			td.getAdditionalRoles().clear() ;
			td.getSuperActivities().clear() ;
			td.setMainRole(null) ;
			td.setTaskDefinition(null) ;
			this.taskDescriptorDao.saveOrUpdateTaskDescriptor(td) ;
		}
		for(TaskDefinition tdef : taskDefinitionList){
			tdef.getTaskDescriptors().clear() ;
			tdef.getSteps().clear() ;
			this.taskDefinitionDao.saveOrUpdateTaskDefinition(tdef) ;
		}
		for(Step s : stepList){
			s.setTaskDefinition(null) ;
			this.stepDao.saveOrUpdateStep(s) ;
		}

		System.out.println("TestProcessPersistence -> ca sauvegarde ") ;

		// update des steps
		for(int i = 0; i < stepListTmp.size(); i++ ){
			stepList.get(i).setTaskDefinition(stepListTmp.get(i).getTaskDefinition()) ;
		}

		for(Step s : stepList){
			this.stepDao.saveOrUpdateStep(s) ;
		}

		// update des taskDefinitions
		for(int i = 0; i < taskDefinitionListTmp.size(); i++ ){
			taskDefinitionList.get(i).addAllTaskDesciptors(taskDefinitionListTmp.get(i).getTaskDescriptors()) ;
			// taskDefinitionList.get(i).addAllSteps(taskDefinitionListTmp.get(i).getSteps());
		}

		for(TaskDefinition tdef : taskDefinitionList){
			this.taskDefinitionDao.saveOrUpdateTaskDefinition(tdef) ;
		}

		// update des taskDescriptors
		for(int i = 0; i < taskDescriptorListTmp.size(); i++ ){
			taskDescriptorList.get(i).setMainRole(taskDescriptorListTmp.get(i).getMainRole()) ;
			taskDescriptorList.get(i).setTaskDefinition(taskDescriptorListTmp.get(i).getTaskDefinition()) ;
			// taskDescriptorList.get(i).addAllAdditionalRoles(taskDescriptorListTmp.get(i).getAdditionalRoles());
			// taskDescriptorList.get(i).addAllActivities(taskDescriptorListTmp.get(i).getActivities());
		}

		for(TaskDescriptor td : taskDescriptorList){
			this.taskDescriptorDao.saveOrUpdateTaskDescriptor(td) ;
		}

		for(int i = 0; i < roleDefinitionListTmp.size(); i++ ){
			roleDefinitionList.get(i).addAllRoleDescriptors(roleDefinitionListTmp.get(i).getRoleDescriptors()) ;
		}

		for(RoleDefinition rdef : roleDefinitionList){
			this.roleDefinitionDao.saveOrUpdateRoleDefinition(rdef) ;
		}

		for(int i = 0; i < roleDescriptorListTmp.size(); i++ ){
			roleDescriptorList.get(i).setRoleDefinition(roleDescriptorListTmp.get(i).getRoleDefinition()) ;
			roleDescriptorList.get(i).addAllPrimaryTasks(roleDescriptorListTmp.get(i).getPrimaryTasks()) ;
			// roleDescriptorList.get(i).addAllParticipants(roleDescriptorListTmp.get(i).getParticipants());
			// roleDescriptorList.get(i).addAllAdditionalTasks(roleDescriptorListTmp.get(i).getAdditionalTasks());
			// roleDescriptorList.get(i).addAllActivities(roleDescriptorListTmp.get(i).getActivities());
		}
		
		for(RoleDescriptor rd : roleDescriptorList){
			this.roleDescriptorDao.saveOrUpdateRoleDescriptor(rd) ;
		}
//		for(Phase ph : phaseList)
//		{
//			this.phaseDao.saveOrUpdatePhase(ph) ;
//		}
//		for(int i = 0; i < phaseListTmp.size(); i++ ){
//			phaseList.get(i).addAllSuperActivities(phaseListTmp.get(i).getSuperActivities()) ;
//			phaseList.get(i).addAllBreakdownElements(phaseListTmp.get(i).getBreakDownElements()) ;
//		}
//		
//		for(Iteration it : iterationList)
//		{
//			this.iterationDao.saveOrUpdateIteration(it) ;
//		}
//		
//		for(int i = 0; i < iterationListTmp.size(); i++ ){
//			iterationList.get(i).addAllSuperActivities(iterationListTmp.get(i).getSuperActivities()) ;
//			iterationList.get(i).addAllBreakdownElements(iterationListTmp.get(i).getBreakDownElements()) ;
//		}
		
		
		_process.addAllBreakdownElements(processTmp.getBreakDownElements()) ;
		this.processDao.saveOrUpdateProcess(_process) ;

		System.out.println("TestProcessPersistence -> ca update ") ;

		return id_process ;
	}
	
	public void miseAJListe(List<BreakdownElement> bdes)
	{
		for(BreakdownElement breakdownElement : bdes){
			logger.debug("### TestSaveCollectionsProcess ### for bdes -> bde =" + breakdownElement) ;
			if(breakdownElement instanceof Activity){
				Activity mon = (Activity) breakdownElement;
				List<BreakdownElement> bdes1 = new ArrayList<BreakdownElement>() ;
				if(breakdownElement instanceof Phase){
					phaseList.add((Phase)breakdownElement);
					try{
						phaseListTmp.add(((Phase)breakdownElement).clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
				}
				else
				{
					if(breakdownElement instanceof Iteration){
						iterationList.add((Iteration)breakdownElement);
						try{
							iterationListTmp.add(((Iteration)breakdownElement).clone()) ;
						}
						catch(CloneNotSupportedException e){
							e.printStackTrace() ;
						}
					}
				}
				bdes1.addAll(mon.getBreakDownElements());
				this.miseAJListe(bdes1);
			}
			else
			{
				if(breakdownElement instanceof TaskDescriptor){
					logger.debug("### TestSaveCollectionsProcess ### bde instance of TaskDescriptor =" + breakdownElement) ;
					TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
					TaskDefinition taskDefinition = taskDescriptor.getTaskDefinition() ;
	
					if(taskDefinition != null){
						for(Step step : taskDefinition.getSteps()){
							logger.debug("### SaveProcessService ### for steps ... =" + taskDefinition.getSteps()) ;
							if(step != null)
								stepList.add(step) ;
							try{
								stepListTmp.add(step.clone()) ;
							}
							catch(CloneNotSupportedException e){
								e.printStackTrace() ;
							}
							logger.debug("TestSaveCollectionsProcess: stepList -> " + stepList) ;
							logger.debug("TestSaveCollectionsProcess: stepList.size -> " + stepList.size()) ;
						}
	
						taskDefinitionList.add(taskDefinition) ;
						try{
							taskDefinitionListTmp.add(taskDefinition.clone()) ;
						}
						catch(CloneNotSupportedException e){
							e.printStackTrace() ;
						}
						logger.debug("TestSaveCollectionsProcess: taskDefinitionList -> " + taskDefinitionList) ;
						logger.debug("TestSaveCollectionsProcess: taskDefinitionList.size -> " + taskDefinitionList.size()) ;
					}
					taskDescriptorList.add(taskDescriptor) ;
					try{
						taskDescriptorListTmp.add(taskDescriptor.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
					
					logger.debug("TestSaveCollectionsProcess: taskDescriptorList -> " + taskDescriptorList) ;
					logger.debug("TestSaveCollectionsProcess: taskDescriptorList.size -> " + taskDescriptorList.size()) ;
	
				}
				else if(breakdownElement instanceof RoleDescriptor){
					logger.debug("### SaveProcessService ### breakdownElement instance of RoleDescriptor =" + breakdownElement) ;
					RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement ;
					roleDescriptorList.add(roleDescriptor) ;
					try{
						roleDescriptorListTmp.add(roleDescriptor.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
	
					logger.debug("TestSaveCollectionsProcess: roleDescriptorList -> " + roleDescriptorList) ;
					logger.debug("TestSaveCollectionsProcess: roleDescriptorList.size -> " + roleDescriptorList.size()) ;
	
					RoleDefinition rd = roleDescriptor.getRoleDefinition() ;
					roleDefinitionList.add(rd) ;
					try{
						roleDefinitionListTmp.add(rd.clone()) ;
					}
					catch(CloneNotSupportedException e){
						e.printStackTrace() ;
					}
					logger.debug("TestSaveCollectionsProcess: roleDefinitionList -> " + roleDefinitionList) ;
					logger.debug("TestSaveCollectionsProcess: roleDefinitionList.size -> " + roleDefinitionList.size()) ;
				}
				else{
					logger.error("### SaveProcessService ### unkown type of object from process !") ;
				}
			}
		}
	}

	/**
	 * Return processes list
	 * 
	 * @return
	 */
	@ Transactional (readOnly = true)
	public List<Process> getProcessesList() {
		return this.processDao.getAllProcesses() ;
	}

	public Process getConcreteTaskDescriptorByProcess(String _processId) {
		/*TODO Process process = this.processDao.getProcess(_processId) ;
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>() ;
		bdes.addAll(this.breakdownElementService.getBreakdownElementsFromProcess(_processId)) ;
		Set<BreakdownElement> taskDescriptors = new HashSet<BreakdownElement>() ;
		for(BreakdownElement bde : bdes){
			if(bde instanceof TaskDescriptor){
				taskDescriptors.add(bde);
			}
		}
		process.addAllBreakdownElements(taskDescriptors) ;
		return process ;*/
		return null;
	}
	
	@ Transactional (readOnly = true)
	public Process getAffectedConcreteTaskDescriptorsByProcess(String _processId, String _roleId) {
		/*TODO Process process = this.processDao.getProcess(_processId) ;
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>() ;
		bdes.addAll(this.breakdownElementService.getBreakdownElementsFromProcess(_processId)) ;
		Set<BreakdownElement> taskDescriptors = new HashSet<BreakdownElement>() ;
		for(BreakdownElement bde : bdes){
			if(bde instanceof TaskDescriptor){
				taskDescriptors.add(bde);
			}
		}
		process.addAllBreakdownElements(taskDescriptors) ;
		return process ;*/
		return null;
	}
	/**
	 * @param _processId
	 * @return
	 */
	@ Transactional (readOnly = true)
	public Process getEntireProcess(String _processId) {
		Process process = this.processDao.getProcess(_processId) ;
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>() ;
		bdes.addAll(this.breakdownElementService.getBreakdownElementsFromProcess(_processId)) ;
		process.addAllBreakdownElements(bdes) ;
		for(BreakdownElement bde : bdes){
			if(bde instanceof TaskDescriptor){
				TaskDescriptor tdor = (TaskDescriptor) bde ;
				SortedSet<Step> steps = new TreeSet<Step>() ;
				if(tdor.getTaskDefinition() != null){
					steps.addAll(this.stepService.getStepsFromTask(tdor.getTaskDefinition().getId())) ;
					tdor.getTaskDefinition().addAllSteps(steps) ;
				}
			}
		}
		return process ;
	}
	
	// TODO PROBLEME : fonction peut etre inutile ?!
	@ Transactional (readOnly = true)
	public Process getProcessWithOnlyTaskDescriptors(String _processId) {
		Process process = this.processDao.getProcess(_processId) ;
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>() ;
		bdes.addAll(this.breakdownElementService.getBreakdownElementsFromProcess(_processId)) ;
		Set<BreakdownElement> taskDescriptors = new HashSet<BreakdownElement>() ;
		for(BreakdownElement bde : bdes){
			if(bde instanceof TaskDescriptor){
				taskDescriptors.add(bde);
			}
		}
		process.addAllBreakdownElements(taskDescriptors) ;
		return process ;
	}

	/**
	 * Getter of processDao.
	 * 
	 * @return the processDao.
	 */
	public ProcessDao getProcessDao() {
		return processDao ;
	}

	/**
	 * Setter of processDao.
	 * 
	 * @param _processDao
	 *            The processDao to set.
	 */
	public void setProcessDao(ProcessDao _processDao) {
		this.processDao = _processDao ;
	}

	/**
	 * Getter of activityDao.
	 * 
	 * @return the activityDao.
	 */
	public ActivityDao getActivityDao() {
		return activityDao ;
	}

	/**
	 * Setter of activityDao.
	 * 
	 * @param activityDao
	 *            The activityDao to set.
	 */
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao ;
	}

	/**
	 * Getter of breakdownElementDao.
	 * 
	 * @return the breakdownElementDao.
	 */
	public BreakdownElementDao getBreakdownElementDao() {
		return breakdownElementDao ;
	}

	/**
	 * Setter of breakdownElementDao.
	 * 
	 * @param breakdownElementDao
	 *            The breakdownElementDao to set.
	 */
	public void setBreakdownElementDao(BreakdownElementDao breakdownElementDao) {
		this.breakdownElementDao = breakdownElementDao ;
	}

	/**
	 * Getter of elementDao.
	 * 
	 * @return the elementDao.
	 */
	public ElementDao getElementDao() {
		return elementDao ;
	}

	/**
	 * Setter of elementDao.
	 * 
	 * @param elementDao
	 *            The elementDao to set.
	 */
	public void setElementDao(ElementDao elementDao) {
		this.elementDao = elementDao ;
	}

	/**
	 * Getter of roleDefinitionDao.
	 * 
	 * @return the roleDefinitionDao.
	 */
	public RoleDefinitionDao getRoleDefinitionDao() {
		return roleDefinitionDao ;
	}

	/**
	 * Setter of roleDefinitionDao.
	 * 
	 * @param roleDefinitionDao
	 *            The roleDefinitionDao to set.
	 */
	public void setRoleDefinitionDao(RoleDefinitionDao roleDefinitionDao) {
		this.roleDefinitionDao = roleDefinitionDao ;
	}

	/**
	 * Getter of roleDescriptorDao.
	 * 
	 * @return the roleDescriptorDao.
	 */
	public RoleDescriptorDao getRoleDescriptorDao() {
		return roleDescriptorDao ;
	}

	/**
	 * Setter of roleDescriptorDao.
	 * 
	 * @param roleDescriptorDao
	 *            The roleDescriptorDao to set.
	 */
	public void setRoleDescriptorDao(RoleDescriptorDao roleDescriptorDao) {
		this.roleDescriptorDao = roleDescriptorDao ;
	}

	/**
	 * Getter of stepDao.
	 * 
	 * @return the stepDao.
	 */
	public StepDao getStepDao() {
		return stepDao ;
	}

	/**
	 * Setter of stepDao.
	 * 
	 * @param stepDao
	 *            The stepDao to set.
	 */
	public void setStepDao(StepDao stepDao) {
		this.stepDao = stepDao ;
	}

	/**
	 * Getter of taskDefinitionDao.
	 * 
	 * @return the taskDefinitionDao.
	 */
	public TaskDefinitionDao getTaskDefinitionDao() {
		return taskDefinitionDao ;
	}

	/**
	 * Setter of taskDefinitionDao.
	 * 
	 * @param taskDefinitionDao
	 *            The taskDefinitionDao to set.
	 */
	public void setTaskDefinitionDao(TaskDefinitionDao taskDefinitionDao) {
		this.taskDefinitionDao = taskDefinitionDao ;
	}

	/**
	 * Getter of taskDescriptorDao.
	 * 
	 * @return the taskDescriptorDao.
	 */
	public TaskDescriptorDao getTaskDescriptorDao() {
		return taskDescriptorDao ;
	}

	/**
	 * Setter of taskDescriptorDao.
	 * 
	 * @param taskDescriptorDao
	 *            The taskDescriptorDao to set.
	 */
	public void setTaskDescriptorDao(TaskDescriptorDao taskDescriptorDao) {
		this.taskDescriptorDao = taskDescriptorDao ;
	}

	/**
	 * Getter of workBreakdownElementDao.
	 * 
	 * @return the workBreakdownElementDao.
	 */
	public WorkBreakdownElementDao getWorkBreakdownElementDao() {
		return workBreakdownElementDao ;
	}

	/**
	 * Setter of workBreakdownElementDao.
	 * 
	 * @param workBreakdownElementDao
	 *            The workBreakdownElementDao to set.
	 */
	public void setWorkBreakdownElementDao(WorkBreakdownElementDao workBreakdownElementDao) {
		this.workBreakdownElementDao = workBreakdownElementDao ;
	}

	/**
	 * @return the breakdownElementService
	 */
	public BreakdownElementService getBreakdownElementService() {
		return this.breakdownElementService ;
	}

	/**
	 * Setter of breakdownElementService.
	 * 
	 * @param _breakdownElementService
	 *            The breakdownElementService to set.
	 */
	public void setBreakdownElementService(BreakdownElementService _breakdownElementService) {
		this.breakdownElementService = _breakdownElementService ;
	}

	/**
	 * @return the stepService
	 */
	public StepService getStepService() {
		return this.stepService ;
	}

	/**
	 * Setter of stepService.
	 * 
	 * @param _stepService
	 *            The stepService to set.
	 */
	public void setStepService(StepService _stepService) {
		this.stepService = _stepService ;
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
}
