package wilos.business.services.util.xml.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.fillers.FillerRole;
import wilos.business.services.util.xml.fillers.FillerRoleDescriptor;
import wilos.business.services.util.xml.fillers.FillerStep;
import wilos.business.services.util.xml.fillers.FillerTask;
import wilos.business.services.util.xml.fillers.FillerTaskDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.Step;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

/**
 * Class XMLParser
 * a static class designed to Parse an XLM File
 * @author faure
 *
 */
public class XMLParser {
	private static String roleDescriptor = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:RoleDescriptor']";
	private static String taskDescriptor = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:TaskDescriptor']";
	private static String roleDefinition =  "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Role' ]";
	private static String taskDefinition  = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Task']";
	// sections
	private static String task = "Task";
	private static String role = "Role";
	private static String performedPrimarilyBy = "PerformedPrimarilyBy";
	private static String additionallyPerformedBy = "AdditionallyPerformedBy";
	private static String step = "Section";
	private static String presentation = "Presentation";
	
	protected static Vector<TaskDefinition> TasksList = new Vector<TaskDefinition> ();
	protected static Vector<RoleDefinition> RoleList = new Vector<RoleDefinition>() ;
	
	/**
	 * Start 
	 * initializes the List in memory
	 * to launch before everything
	 */
	private static void start() {
		try {
			fillRoleDefinitionList();
			fillTaskDefinitionList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fillTaskDefinitionList
	 * fill the taskslist with task definition
	 * @throws Exception
	 */
	private static void fillTaskDefinitionList() throws Exception{
		TasksList.clear();
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(taskDefinition,XPathConstants.NODESET);
		if (nodeReturned == null){
			throw new Exception ("NO TASKS DEFINITIONS FOUND");
		}
		Node aNode;
		for(int i=0;i<nodeReturned.getLength();i++){
			aNode = nodeReturned.item(i);
			TaskDefinition  aTaskDefinition = new TaskDefinition();
			FillerTask aFiller = new FillerTask(aTaskDefinition,aNode);	
			aTaskDefinition = (TaskDefinition)aFiller.getFilledElement();
			setStepByTaskDefinition(aTaskDefinition, aNode);
			TasksList.add(aTaskDefinition);
		}	
	}
	
	/**
	 * fillRoleDefinitionList
	 * fill the RolesList with RoleDefinition
	 * @throws Exception
	 */
	private static void fillRoleDefinitionList () throws Exception {
		RoleList.clear();
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(roleDefinition,XPathConstants.NODESET);
		if (nodeReturned == null){
			throw new Exception ("NO ROLE DEFINITIONS FOUND");
		}
		Node aNode;
		for(int i=0;i<nodeReturned.getLength();i++){
			aNode = nodeReturned.item(i);
			RoleDefinition  aRoleDefinition = new RoleDefinition();
			FillerRole aFiller = new FillerRole(aRoleDefinition,aNode);	
			aRoleDefinition = (RoleDefinition)aFiller.getFilledElement();
			RoleList.add(aRoleDefinition);
		}	
	}
	
	/**
	 * getProcess
	 * Return a Process from a file
	 * @param f a XML file
	 * @return the process
	 * @throws Exception 
	 */
	public static Process getProcess(File f) throws Exception{
		if(!f.exists()){
			throw new Exception("FILE DOESN'T EXIST");
		}
		XMLUtils.setDocument(f);
		start();
		return (getProcess());
	}
	
	/**
	 * getProcess
	 * @return the process
	 */
	private static Process getProcess (){
		Process p = new Process() ;
		try{			
			// get all the roles descriptor
			Set<RoleDescriptor> ensRole = getAllRoleDescriptors() ;
			for (Iterator i = ensRole.iterator() ; i.hasNext() ;){
				p.addBreakdownElement((BreakdownElement) i.next());
			}
			// get all the tasks descriptor
			Set<TaskDescriptor> allTasks = getAllTaskDescriptors(ensRole);
			for (Iterator i = allTasks.iterator() ; i.hasNext() ;){
				p.addBreakdownElement((BreakdownElement) i.next());
			}
		}
		catch(Exception e)
		{
			
		}
		return p ;
	}

	
	/**
	 * getAllTaskDescriptors 
	 * @return all the tasks descriptors
	 * @throws Exception when no tasks descriptor are found
	 */
	private static Set<TaskDescriptor> getAllTaskDescriptors(Set<RoleDescriptor> allRoles) throws Exception {
		// gets all the roles in the file
		HashSet<TaskDescriptor> taskList = new HashSet<TaskDescriptor>();
		try {
			NodeList taskDescriptors = (NodeList)XMLUtils.evaluate(taskDescriptor,XPathConstants.NODESET);
			if (taskDescriptors == null){
				throw new Exception ("NO TASK DESCRIPTORS FOUND");
			}
			Node aNode;
			for(int i=0;i<taskDescriptors.getLength();i++){
				aNode = taskDescriptors.item(i);
				TaskDescriptor aTaskDescriptor = new TaskDescriptor();
				FillerTaskDescriptor aFiller = new FillerTaskDescriptor(aTaskDescriptor,aNode);	
				TaskDescriptor taskDescriptorfilled = (TaskDescriptor)aFiller.getFilledElement();
				
				// affect the task definition to the task descriptor
				setTaskByTaskDescriptor(taskDescriptorfilled,aNode);
				
				// affect the main role to the current task
				setMainRoleByTaskDescriptor(taskDescriptorfilled, aNode, allRoles);
				
				// affect the additional roles to the current task TODO PAUL
				setAddiotionalRoleByTaskDescriptor(taskDescriptorfilled, aNode, allRoles);
				
				taskList.add(taskDescriptorfilled);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	/**
	 * getTasksByTaskDescriptor
	 * @param t
	 * @return a task 
	 */
	private static void setTaskByTaskDescriptor(TaskDescriptor _t,Node _node) throws Exception{
		TaskDefinition taskTobereturn = null;
		// getting the id of the task
		String idTask = "" ;
		NodeList listOfTdNodes = _node.getChildNodes() ;
		boolean trouve = false ;
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(task)){
				trouve = true ;
				idTask = listOfTdNodes.item(i).getTextContent();
			}
		}
		// process if there is a task for this task desriptor
		if (trouve){
			taskTobereturn = getTaskDefinitionByID(idTask);
			// if the task doesn't exist
			if (taskTobereturn == null){
				throw new Exception ("NO TASK DEFINITION FOR THIS TASK DESCRIPTOR");
			}
			// set the task in the taskdescriptor
			_t.addTaskDefinition(taskTobereturn);
		}
	}
	
	private static TaskDefinition getTaskDefinitionByID(String _id){
		for (int i = 0 ; i < TasksList.size() ; i ++){
			if (TasksList.get(i).getIdEPF().equals(_id)){
				return TasksList.get(i);
			}
		}
		return null ;
	}
	
	
	private static void setStepByTaskDefinition(TaskDefinition _taskd,Node _n) throws Exception {
		// getting the id of the role
		NodeList listOfTdNodes = _n.getChildNodes() ;
		boolean trouve = false ;
		
		// search the nodes of the step
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(presentation)){
				for (int j = 0 ; j < listOfTdNodes.item(i).getChildNodes().getLength() ; j ++){
					if (listOfTdNodes.item(i).getChildNodes().item(j).getNodeName().equals(step)){
						Step aStep = new Step() ;
						FillerStep fs = new FillerStep(aStep,listOfTdNodes.item(i).getChildNodes().item(j));
						aStep = (Step) fs.getFilledElement();
						_taskd.addStep(aStep);
					}
				}
				trouve = true ;
			}
		}
	}
	
	/**
	 * TODO PAUL
	 * @param _t
	 * @param _n
	 * @param _s
	 * @throws Exception
	 */
	private static void setAddiotionalRoleByTaskDescriptor(TaskDescriptor _t,Node _n,Set<RoleDescriptor> _s) throws Exception {
//		 getting the id of the role
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(additionallyPerformedBy)){
				idRole = listOfTdNodes.item(i).getTextContent();
				RoleDescriptor roleToBeset ;
				roleToBeset = getRoleDescriptorById(_s, idRole);
				// if the task doesn't exist
				if (roleToBeset == null){
					throw new Exception("role " + idRole + " doesn't exist");
				}
				// set the role in the roledescriptor
				_t.addAdditionalRole(roleToBeset);
			}
		}
	}
	
	/**
	 * setMainRoleByTaskDescriptor
	 * @param _t the taskdescriptor executed
	 * @param _n the node 
	 * @param _s the set of roleDescriptor available
	 * @throws Exception
	 */
	private static void setMainRoleByTaskDescriptor(TaskDescriptor _t,Node _n,Set<RoleDescriptor> _s) throws Exception {
		// getting the id of the role
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		boolean trouve = false ;
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(performedPrimarilyBy)){
				trouve = true ;
				idRole = listOfTdNodes.item(i).getTextContent();
			}
		}
		if (trouve){
			RoleDescriptor roleToBeset ;
			roleToBeset = getRoleDescriptorById(_s,idRole);
			// if the task doesn't exist
			if (roleToBeset == null){
				throw new Exception("role " + idRole + " doesn't exist");
			}
			// set the role in the roledescriptor
			_t.addMainRole(roleToBeset);
		}
	}
	/**
	 * 
	 * @param r
	 * @param n
	 * @return
	 */
	private static void setRoleByRoleDescriptor(RoleDescriptor _r,Node _n) throws Exception {
		RoleDefinition roleTobereturn = null;
		// getting the id of the role
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		boolean trouve = false ;
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(role)){
				trouve = true ;
				idRole = listOfTdNodes.item(i).getTextContent();
			}
		}
		// process if there is a task for this role desriptor
		if (trouve){
				roleTobereturn = getRoleDefinitionByID(idRole) ;
				if (roleTobereturn == null){
					throw new Exception ("PAS DE ROLE POUR CE ROLE DESCRIPTOR");	
				}
			// set the role in the roledescriptor
			_r.addRoleDefinition(roleTobereturn);
		}
	}
	
	private static RoleDescriptor getRoleDescriptorById(Set<RoleDescriptor> aSet,String id){
		for (Iterator i = aSet.iterator() ; i.hasNext() ;){
			RoleDescriptor tmp = (RoleDescriptor) i .next();
			if (tmp.getIdEPF().equals(id)){
				return  tmp;
			}
		}
		return null ;
	}
	
	/**
	 * getAllRoleDescriptors 
	 * @return all the tasks descriptors
	 * @throws Exception when no tasks descriptor are found
	 */
	private static Set<RoleDescriptor> getAllRoleDescriptors() throws Exception {
		LinkedHashSet<RoleDescriptor> roleList = new LinkedHashSet<RoleDescriptor>();
		try {
			NodeList roleDescriptors = (NodeList)XMLUtils.evaluate(roleDescriptor,XPathConstants.NODESET);
			if (roleDescriptors == null){
				throw new Exception ("NO ROLE DESCRIPTORS FOUND");
			}
			Node aNode;
			for(int i=0;i<roleDescriptors.getLength();i++){
				aNode = roleDescriptors.item(i);
				RoleDescriptor aRoleDescriptor = new RoleDescriptor();
				FillerRoleDescriptor aFiller = new FillerRoleDescriptor(aRoleDescriptor,aNode);	
				RoleDescriptor roleDescriptorfilled = (RoleDescriptor)aFiller.getFilledElement();
				
				setRoleByRoleDescriptor(roleDescriptorfilled,aNode);
								
				roleList.add(roleDescriptorfilled) ;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return roleList;
	}
	
	private static RoleDefinition getRoleDefinitionByID(String _id){
		for (int i = 0 ; i < RoleList.size() ; i ++){
			if (RoleList.get(i).getIdEPF().equals(_id)){
				return RoleList.get(i);
			}
		}
		return null ;
	}
}


