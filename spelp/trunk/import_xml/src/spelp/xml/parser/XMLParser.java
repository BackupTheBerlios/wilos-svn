package spelp.xml.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import spelp.xml.fillers.FillerRole;
import spelp.xml.fillers.FillerRoleDescriptor;
import spelp.xml.fillers.FillerTask;
import spelp.xml.fillers.FillerTaskDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;

import woops2.model.role.RoleDescriptor;
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
	// sections
	private static String task = "Task";
	private static String role = "Role";
	
	private static Vector<TaskDefinition> TasksList = new Vector<TaskDefinition> ();
	private static Vector<RoleDefinition> RoleList = new Vector<RoleDefinition> ();
	/**
	 * setFile is the function need to be called in first
	 * @param f File to be parsed
	 */
	public static void setFile(File f){
		// put the document in memory
		XMLUtils.setDocument(f);
	}
	
	public static Process getProcess(File f){
		XMLUtils.setDocument(f);
		return (getProcess());
	}
	
	/**
	 * getProcess
	 * @return
	 */
	public static Process getProcess (){
		Process p = new Process() ;
		try{
			// get all the tasks descriptor
			Set<TaskDescriptor> allTasks = getAllTaskDescriptors();
			for (Iterator i = allTasks.iterator() ; i.hasNext() ;){
				p.addToBreakdownElement((BreakdownElement) i.next());
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
	public static Set<TaskDescriptor> getAllTaskDescriptors() throws Exception {
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
				setTaskByTaskDescriptor(taskDescriptorfilled,aNode);
				taskList.add(taskDescriptorfilled);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}
	
	/**
	 * getAllRoleDescriptors 
	 * @return all the tasks descriptors
	 * @throws Exception when no tasks descriptor are found
	 */
	public static Set<RoleDescriptor> getAllRoleDescriptors() throws Exception {
		HashSet<RoleDescriptor> roleList = new HashSet<RoleDescriptor>();
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
				roleList.add(roleDescriptorfilled);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return roleList;
	}
	
	/**
	 * getAllRolesDescriptors
	 * @param t
	 * @return a set containing the roles associated with the task descriptor
	 */
	public static Set<RoleDescriptor> getAllRolesDescriptors(){
		return null ;
	}
	
	/**
	 * getTasksByTaskDescriptor
	 * @param t
	 * @return a task 
	 */
	public static void setTaskByTaskDescriptor(TaskDescriptor _t,Node _node) throws Exception{
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
			taskTobereturn = getTaskInVector(idTask);
			// if the task doesn't exist
			if (taskTobereturn == null){
				taskTobereturn = new TaskDefinition();
				
				// getting the attributes of the task
				Node aNode = getNodeTask(idTask);
				FillerTask aFiller = new FillerTask(taskTobereturn,aNode);
				
				taskTobereturn = (TaskDefinition)aFiller.getFilledElement() ;
				TasksList.add(taskTobereturn);
			}
			// set the task in the taskdescriptor
			_t.addToTaskDefinition(taskTobereturn);
		}
	}
	
	public static Node getNodeTask (String id) throws Exception {
		String reqXpath = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Task' and @id ='"+id+"']";
		Node nodeReturned = (Node)XMLUtils.evaluate(reqXpath,XPathConstants.NODE);
		if (nodeReturned == null){
			throw new Exception ("NO TASK FOUND");
		}
		return nodeReturned ;
	}
	
	public static Node getNodeRole (String id) throws Exception {
		String reqXpath = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Role' and @id ='"+id+"']";
		Node nodeReturned = (Node)XMLUtils.evaluate(reqXpath,XPathConstants.NODE);
		if (nodeReturned == null){
			throw new Exception ("NO ROLE FOUND");
		}
		return nodeReturned ;
	}
		
	/**
	 * getTaskInVector : search in the vector if the object already exists
	 * @param id
	 * @return
	 */
	public static TaskDefinition getTaskInVector (String id){
		for (int i = 0 ; i < TasksList.size() ; i++){
			if (TasksList.get(i).getId().equals(id)){
				return (TasksList.get(i));
			}
		}
		return null ;
	}
	
	
	/**
	 * 
	 * @param r
	 * @param n
	 * @return
	 */
	public static void setRoleByRoleDescriptor(RoleDescriptor _r,Node _n) throws Exception {
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
			roleTobereturn = getRoleInVector(idRole);
			// if the task doesn't exist
			if (roleTobereturn == null){
				roleTobereturn = new RoleDefinition();
				
				// getting the attributes of the role
				Node aNode = getNodeRole(idRole);
				FillerRole aFiller = new FillerRole(roleTobereturn,aNode);
				
				roleTobereturn = (RoleDefinition)aFiller.getFilledElement() ;
				RoleList.add(roleTobereturn);
			}
			// set the role in the roledescriptor
			_r.addToRoleDefinition(roleTobereturn);
		}
	}

	/**
	 * getRoleInVector : search in the vector if the object already exists
	 * @param id
	 * @return
	 */
	public static RoleDefinition getRoleInVector (String id) {
		for (int i = 0 ; i < RoleList.size() ; i++){
			if (RoleList.get(i).getId().equals(id)){
				return (RoleList.get(i));
			}
		}
		return null ;
	}
	



}


