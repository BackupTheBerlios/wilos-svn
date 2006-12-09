package wilos.business.services.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.util.xml.parser.XMLParser;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.hibernate.spem2.breakdownelement.BreakdownElementDao;
import wilos.hibernate.spem2.element.ElementDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.hibernate.spem2.task.StepDao;
import wilos.hibernate.spem2.task.TaskDefinitionDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.web.icefaces.tree.NodeUserObject;
import wilos.presentation.web.icefaces.tree.TreeBean;

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

	public Process spelpParsingXML(File _file) {
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
	 * test save of collections' process
	 * @param _process
	 */
	public String saveProcess(Process _process) {
		
		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_process.getBreakDownElements());

		List<Step> stepList = new ArrayList<Step>();
		List<Step> stepListTmp = new ArrayList<Step>();
		List<TaskDefinition> taskDefinitionList = new ArrayList<TaskDefinition>();
		List<TaskDefinition> taskDefinitionListTmp = new ArrayList<TaskDefinition>();
		List<TaskDescriptor> taskDescriptorList = new ArrayList<TaskDescriptor>();
		List<TaskDescriptor> taskDescriptorListTmp = new ArrayList<TaskDescriptor>();
		List<RoleDefinition> roleDefinitionList = new ArrayList<RoleDefinition>();
		List<RoleDefinition> roleDefinitionListTmp = new ArrayList<RoleDefinition>();
		List<RoleDescriptor> roleDescriptorList = new ArrayList<RoleDescriptor>();
		List<RoleDescriptor> roleDescriptorListTmp = new ArrayList<RoleDescriptor>();
		Process processTmp = new Process();
		
		String id_process = null;
		
		int nbTD = 0;
		// add element of process in elements
		for (BreakdownElement breakdownElement : bdes) {
			logger.debug("### TestSaveCollectionsProcess ### for bdes -> bde ="+breakdownElement);
			if (breakdownElement instanceof TaskDescriptor) {
				logger.debug("### TestSaveCollectionsProcess ### bde instance of TaskDescriptor ="+breakdownElement);
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				TaskDefinition taskDefinition = taskDescriptor.getTaskDefinition();
				
				if (taskDefinition != null) {
					for (Step step : taskDefinition.getSteps()) {
						logger.debug("### SaveProcessService ### for steps ... ="+taskDefinition.getSteps());
						if (step != null)
							stepList.add(step);
							try{
								stepListTmp.add(step.clone());
							}
							catch(CloneNotSupportedException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.debug("TestSaveCollectionsProcess: stepList -> " + stepList);
							logger.debug("TestSaveCollectionsProcess: stepList.size -> " + stepList.size());
					}

					taskDefinitionList.add(taskDefinition);
					try{
						taskDefinitionListTmp.add(taskDefinition.clone());
					}
					catch(CloneNotSupportedException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.debug("TestSaveCollectionsProcess: taskDefinitionList -> " + taskDefinitionList);
					logger.debug("TestSaveCollectionsProcess: taskDefinitionList.size -> " + taskDefinitionList.size());
				}
				taskDescriptorList.add(taskDescriptor);
				try{
					taskDescriptorListTmp.add(taskDescriptor. clone());
				}
				catch(CloneNotSupportedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nbTD ++;
				logger.debug("TestSaveCollectionsProcess: taskDescriptorList -> " + taskDescriptorList);
				logger.debug("TestSaveCollectionsProcess: taskDescriptorList.size -> " + taskDescriptorList.size());
				
			} else if (breakdownElement instanceof RoleDescriptor) {
				logger.debug("### SaveProcessService ### breakdownElement instance of RoleDescriptor ="+breakdownElement);
				RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement;
				roleDescriptorList.add(roleDescriptor);
				try{
					roleDescriptorListTmp.add(roleDescriptor.clone());
				}
				catch(CloneNotSupportedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				logger.debug("TestSaveCollectionsProcess: roleDescriptorList -> " + roleDescriptorList);
				logger.debug("TestSaveCollectionsProcess: roleDescriptorList.size -> " + roleDescriptorList.size());
								
				RoleDefinition rd = roleDescriptor.getRoleDefinition();
				roleDefinitionList.add(rd);
				try{
					roleDefinitionListTmp.add(rd.clone());
				}
				catch(CloneNotSupportedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.debug("TestSaveCollectionsProcess: roleDefinitionList -> " + roleDefinitionList);
				logger.debug("TestSaveCollectionsProcess: roleDefinitionList.size -> " + roleDefinitionList.size());				
			}
			else {
				logger.error("### SaveProcessService ### unkown type of object from process !");
			}
		}
		try{
			processTmp = _process.clone();
		}
		catch(CloneNotSupportedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("TestSaveCollectionsProcess: processId -> " + _process.getGuid());
		
		_process.getBreakDownElements().clear();
		this.processDao.saveOrUpdateProcess(_process);
		id_process = _process.getId();

		for (RoleDescriptor rd : roleDescriptorList) {
			rd.getAdditionalTasks().clear();
			rd.getActivities().clear();
			//rd.getParticipants().clear();
			rd.getPrimaryTasks().clear();
			rd.setRoleDefinition(null);
			this.roleDescriptorDao.saveOrUpdateRoleDescriptor(rd);
		}
		for (RoleDefinition rdef : roleDefinitionList) {
			rdef.getRoleDescriptors().clear();
			this.roleDefinitionDao.saveOrUpdateRoleDefinition(rdef);
		}
		for (TaskDescriptor td : taskDescriptorList) {
			td.getAdditionalRoles().clear();
			td.getActivities().clear();
			td.setMainRole(null);
			td.setTaskDefinition(null);
			this.taskDescriptorDao.saveOrUpdateTaskDescriptor(td);
		}
		for (TaskDefinition tdef : taskDefinitionList) {
			tdef.getTaskDescriptors().clear();
			tdef.getSteps().clear();
			this.taskDefinitionDao.saveOrUpdateTaskDefinition(tdef);
		}
		for (Step s : stepList) {
			s.setTaskDefinition(null);
			this.stepDao.saveOrUpdateStep(s);
		}
		
		System.out.println("TestProcessPersistence -> ca sauvegarde ") ;
		
		// update des steps		
		for (int i = 0;i < stepListTmp.size();i++) {
			stepList.get(i).setTaskDefinition(stepListTmp.get(i).getTaskDefinition());
		}
		
		for (Step s : stepList) {
			this.stepDao.saveOrUpdateStep(s);
		}
		
		// update des taskDefinitions		
		for (int i = 0;i < taskDefinitionListTmp.size();i++) {
			taskDefinitionList.get(i).addAllTaskDesciptors(taskDefinitionListTmp.get(i).getTaskDescriptors());
			//taskDefinitionList.get(i).addAllSteps(taskDefinitionListTmp.get(i).getSteps());
		}
		
		for (TaskDefinition tdef : taskDefinitionList) {
			this.taskDefinitionDao.saveOrUpdateTaskDefinition(tdef);
		}
		
		// update des taskDescriptors		
		for (int i = 0;i < taskDescriptorListTmp.size();i++) {
			taskDescriptorList.get(i).setMainRole(taskDescriptorListTmp.get(i).getMainRole());
			taskDescriptorList.get(i).setTaskDefinition(taskDescriptorListTmp.get(i).getTaskDefinition());
			//taskDescriptorList.get(i).addAllAdditionalRoles(taskDescriptorListTmp.get(i).getAdditionalRoles());
			//taskDescriptorList.get(i).addAllActivities(taskDescriptorListTmp.get(i).getActivities());
		}
		
		for (TaskDescriptor td : taskDescriptorList) {
			this.taskDescriptorDao.saveOrUpdateTaskDescriptor(td);
		}
		
		for (int i = 0;i < roleDefinitionListTmp.size();i++) {
			roleDefinitionList.get(i).addAllRoleDescriptors(roleDefinitionListTmp.get(i).getRoleDescriptors());
		}
		
		for (RoleDefinition rdef : roleDefinitionList) {
			this.roleDefinitionDao.saveOrUpdateRoleDefinition(rdef);
		}
		
		for (int i = 0;i < roleDescriptorListTmp.size();i++) {
			roleDescriptorList.get(i).setRoleDefinition(roleDescriptorListTmp.get(i).getRoleDefinition());
			roleDescriptorList.get(i).addAllPrimaryTasks(roleDescriptorListTmp.get(i).getPrimaryTasks());
			//roleDescriptorList.get(i).addAllParticipants(roleDescriptorListTmp.get(i).getParticipants());
			//roleDescriptorList.get(i).addAllAdditionalTasks(roleDescriptorListTmp.get(i).getAdditionalTasks());
			//roleDescriptorList.get(i).addAllActivities(roleDescriptorListTmp.get(i).getActivities());
		}
		
		for (RoleDescriptor rd : roleDescriptorList) {
			this.roleDescriptorDao.saveOrUpdateRoleDescriptor(rd);
		}
		
		_process.addAllBreakdownElements(processTmp.getBreakDownElements());
		this.processDao.saveOrUpdateProcess(_process);
		
		System.out.println("TestProcessPersistence -> ca update ") ;
		
		return id_process;
	}
	
	public Process buildProcess() {
		Process process = new Process();
		process.setGuid("myProcess");
		process.setName("TheProceSS");

		TaskDescriptor tskdes1 = new TaskDescriptor();
		tskdes1.setGuid("tsk1");
		tskdes1.setName("tsk1Name");
		process.addBreakdownElement(tskdes1);

		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setName("tskdef1Name");
		tskdes1.addTaskDefinition(taskDefinition);

		Step step1 = new Step();
		step1.setName("step1");
		taskDefinition.addStep(step1);

		TaskDescriptor tskdes2 = new TaskDescriptor();
		tskdes2.setGuid("tsk2");
		tskdes2.setName("tsk2Name");
		process.addBreakdownElement(tskdes2);

		RoleDescriptor rdes1 = new RoleDescriptor();
		rdes1.setGuid("rdes1");
		rdes1.setName("rdes1Name");
		process.addBreakdownElement(rdes1);

		RoleDefinition roleDefinition = new RoleDefinition();
		roleDefinition.setName("rdef1Name");
		rdes1.addRoleDefinition(roleDefinition);

		return process;
	}
	
	@Transactional(readOnly = true)
	public DefaultTreeModel buildTree(String _id) {
		Process process = null ; // this.buildProcess();
		TreeBean tree = null; // new TreeBean();

		logger.debug("### buildTree id = "+_id+" ###");
		
		process = this.getProcessDao().getProcess("ff8081810f5bef7c010f5bef95a70001");
		
		logger.debug("### buildTree process size = "+process.getBreakDownElements().size()+" ###");
		DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
		NodeUserObject rootObject = new NodeUserObject(rootTreeNode, tree);
		rootObject.setText(process.getName());
		rootTreeNode.setUserObject(rootObject);
		DefaultTreeModel model = new DefaultTreeModel(rootTreeNode);

		// add element of process in elements
		for (BreakdownElement breakdownElement : process.getBreakDownElements()) {
			if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				DefaultMutableTreeNode branchTaskDescriptorNode = new DefaultMutableTreeNode();
				NodeUserObject branchTaskDescriptorObject = new NodeUserObject(
						branchTaskDescriptorNode, tree);
				branchTaskDescriptorNode
						.setUserObject(branchTaskDescriptorObject);
				branchTaskDescriptorObject.setText(taskDescriptor.getName());
				rootTreeNode.insert(branchTaskDescriptorNode, 0);

				TaskDefinition taskDefinition = taskDescriptor
						.getTaskDefinition();
				if (taskDefinition != null) {
					DefaultMutableTreeNode branchTaskDefinitionNode = new DefaultMutableTreeNode();
					NodeUserObject branchTaskDefinitionObject = new NodeUserObject(
							branchTaskDefinitionNode, tree);
					branchTaskDefinitionNode
							.setUserObject(branchTaskDefinitionObject);
					branchTaskDefinitionObject
							.setText(taskDefinition.getName());
					branchTaskDescriptorNode
							.insert(branchTaskDefinitionNode, 0);

					for (Step step : taskDefinition.getSteps()) {
						if (step != null) {
							DefaultMutableTreeNode branchStepNode = new DefaultMutableTreeNode();
							NodeUserObject branchStepObject = new NodeUserObject(
									branchStepNode, tree);
							branchStepNode.setUserObject(branchStepObject);
							branchStepObject.setLeaf(true);
							branchStepObject.setText(step.getName());
							branchTaskDefinitionNode.insert(branchStepNode, 0);
						}
					}
				}
			} else if (breakdownElement instanceof RoleDescriptor) {
				RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement;
				DefaultMutableTreeNode branchRoleDescriptorNode = new DefaultMutableTreeNode();
				NodeUserObject branchRoleDescriptorObject = new NodeUserObject(
						branchRoleDescriptorNode, tree);
				branchRoleDescriptorNode
						.setUserObject(branchRoleDescriptorObject);
				branchRoleDescriptorObject.setText(roleDescriptor.getName());
				rootTreeNode.insert(branchRoleDescriptorNode, 0);

				RoleDefinition roleDefinition = roleDescriptor
						.getRoleDefinition();
				if (roleDefinition != null) {
					DefaultMutableTreeNode branchRoleDefinitionNode = new DefaultMutableTreeNode();
					NodeUserObject branchRoleDefinitionObject = new NodeUserObject(
							branchRoleDefinitionNode, tree);
					branchRoleDefinitionNode
							.setUserObject(branchRoleDefinitionObject);
					branchRoleDefinitionObject
							.setText(roleDefinition.getName());
					branchRoleDefinitionObject.setLeaf(true);
					branchRoleDescriptorNode
							.insert(branchRoleDefinitionNode, 0);
				}
			}
		}
		return model;
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
	
	
	public String TestPersistence(){
		Process p = new Process();
		p.setGuid("epf-test");
		p.setName("test_process");
		logger.debug("TestPersistence p => "+p+" id="+p.getId());
		logger.debug("TestPersistence p => "+p+" id="+p.getId());
		logger.debug("#### p -> "+p.getGuid()+" "+p.getName());
		this.processDao.saveOrUpdateProcess(p);
		logger.debug("TestPersistence p => "+p+" id="+p.getId());
		logger.debug("#### p -> "+p.getGuid()+" "+p.getName());
		return p.getId();
	}
	
	public void TestProcessPersistence(String _id){
		
		Process p = this.processDao.getProcess(_id);
		logger.debug("TestProcessPersistence p => "+p+" id="+p.getId());
		logger.debug("#### p -> "+p.getGuid()+" "+p.getName());
	}
	
	public void TestEmptyObjectDBSave() {
		
		Process p = new Process();
		p.setGuid("processGuID");
		p.setName("processName");
		p.setPrefix("processPrefix");
		logger.debug("#### p -> "+p.getGuid()+" "+p.getName());
		
		RoleDescriptor rd = new RoleDescriptor();
		rd.setGuid("roledescriptorGuID");
		rd.setName("roledescriptorName");
		rd.setPrefix("roledescriptorPrefix");
		logger.debug("#### rd -> "+rd.getGuid()+" "+rd.getName());
		
		TaskDescriptor td = new TaskDescriptor();
		td.setGuid("taskdescriptorGuID");
		td.setName("tddesriptorName");
		td.setPrefix("taskdescriptorPrefix");
		logger.debug("#### td -> "+td.getGuid()+" "+td.getName());
		
		RoleDefinition rddef = new RoleDefinition();
		rddef.setGuid("roledefinitionGuID");
		rddef.setName("roledefinitionName");
		logger.debug("#### rddef -> "+rddef.getGuid()+" "+rddef.getName());
		
		TaskDefinition tddef = new TaskDefinition();
		tddef.setGuid("taskdefinitionGuID");
		tddef.setName("taskdefinitionName");
		logger.debug("#### tddef -> "+tddef.getGuid()+" "+tddef.getName());
		
		Step s1 = new Step();
		s1.setGuid("taskdefinitionGuID");
		s1.setName("taskdefinitionName");
		logger.debug("#### s1 -> "+s1.getGuid()+" "+s1.getName());
		
		Step s2 = new Step();
		s2.setGuid("taskdefinitionGuID");
		s2.setName("taskdefinitionName");
		logger.debug("#### s2 -> "+s2.getGuid()+" "+s2.getName());
		
		this.processDao.saveOrUpdateProcess(p);
		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(rd);
		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(td);
		this.roleDefinitionDao.saveOrUpdateRoleDefinition(rddef);
		this.taskDefinitionDao.saveOrUpdateTaskDefinition(tddef);
		this.stepDao.saveOrUpdateStep(s1);
		this.stepDao.saveOrUpdateStep(s2);
		
		System.out.println("TestProcessPersistence -> ca sauvegarde") ;
		
		rddef.addRoleDescriptor(rd);
		s1.addTaskDefinition(tddef);
		s2.addTaskDefinition(tddef);
		tddef.addTaskDescriptor(td);
		rd.addAdditionalTask(td);
		p.addBreakdownElement(rd);
		p.addBreakdownElement(td);
		
		System.out.println("TestProcessPersistence -> ca update les objets") ;
		
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
