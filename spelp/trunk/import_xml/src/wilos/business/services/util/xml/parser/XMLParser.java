package wilos.business.services.util.xml.parser;


import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.fillers.FillerActivity;
import wilos.business.services.util.xml.fillers.FillerElement;
import wilos.business.services.util.xml.fillers.FillerGuideline;
import wilos.business.services.util.xml.fillers.FillerIteration;
import wilos.business.services.util.xml.fillers.FillerPhase;
import wilos.business.services.util.xml.fillers.FillerProcess;
import wilos.business.services.util.xml.fillers.FillerRole;
import wilos.business.services.util.xml.fillers.FillerRoleDescriptor;
import wilos.business.services.util.xml.fillers.FillerStep;
import wilos.business.services.util.xml.fillers.FillerTask;
import wilos.business.services.util.xml.fillers.FillerTaskDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guideline;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * Class XMLParser
 * a static class designed to Parse an XML File
 * @author SPELP Team
 */
public class XMLParser {
	/* Constants used to parse the XML File */
	// XPaths Paths
	private static final String xpath_roleDescriptor = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:RoleDescriptor']";
	private static final String xpath_taskDescriptor = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:TaskDescriptor']";
	private static final String xpath_roleDefinition =  "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Role' ]";
	private static final String xpath_taskDefinition  = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Task']";
	private static final String xpath_deliveryProcess = "//Process[@*[namespace-uri() and local-name()='type']='uma:DeliveryProcess']";
	private static final String xpath_iteration = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:Iteration']";
	private static final String xpath_phase = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:Phase']";
	private static final String xpath_activity = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:Activity']";
	private static final String xpath_guideline = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Guideline']";
	
	// Sections
	private static final String task = "Task";
	private static final String role = "Role";
	private static final String performedPrimarilyBy = "PerformedPrimarilyBy";
	private static final String additionallyPerformedBy = "AdditionallyPerformedBy";
	private static final String step = "Section";
	private static final String presentation = "Presentation";
	private static final String breakdownElement = "BreakdownElement";
	private static final String guideline = "Guideline"; 
	
	// Types
	private static final String process = "uma:DeliveryProcess";
	private static final String phase = "uma:Phase";
	private static final String activity = "uma:Activity";
	private static final String task_descriptor = "uma:TaskDescriptor";
	private static final String role_descriptor = "uma:RoleDescriptor";
	private static final String iteration = "uma:Iteration";
	
	// Attributes Names
	private static final String id = "id";
	private static final String attr_name_xsitype = "xsi:type";
	private static final String attr_name_variabilityBasedOnElement = "variabilityBasedOnElement" ;
	
	/* Variables recording elements */
	// Filled by fillTaskDefinitionsList and fillRoleDefinitionsList
	protected static Vector<TaskDefinition> TaskDefinitionsList = new Vector<TaskDefinition> ();
	protected static Vector<RoleDefinition> RoleDefinitionsList = new Vector<RoleDefinition>() ;
	protected static Vector<Guideline> GuidesList = new Vector<Guideline>() ;
	
	
	// this variables contain all the Elements that concern them
	protected static Set<RoleDescriptor> allRoleDescriptors ;
	protected static Set<TaskDescriptor> allTaskDescriptors ;
	protected static Set<Phase> allPhases;
	protected static Set<Iteration> allIterations;
	protected static Set<Activity> allActivities;

	
	/**
	 * initializes the Lists in memory
	 * to launch before everything by the parsing method
	 */
	private static void start() {
		try {
			RoleDefinitionsList = fillRoleDefinitionsList();
			GuidesList = fillGuidesList();
			TaskDefinitionsList = fillTaskDefinitionsList();

			
			allRoleDescriptors = getAllRoleDescriptors() ;
			allTaskDescriptors = getAllTaskDescriptors(allRoleDescriptors);
			allPhases = getAllPhases();
			allIterations = getAllIterations();
			allActivities = getAllActivities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fillGuidesList
	 * @return
	 */
	private static Vector<Guideline> fillGuidesList() {
		Vector<Guideline> theGuidelineList; // the return of the function
		
		// initializes the List
		theGuidelineList = new Vector<Guideline>();
		theGuidelineList.clear();
		
		// gets all the nodes containing guideline
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_guideline,XPathConstants.NODESET);
		if (nodeReturned.getLength() != 0) {
			// For each node...
			Node aNode;
			for(int i=0;i<nodeReturned.getLength();i++){
				aNode = nodeReturned.item(i);
				
				// Fills the RoleDefinition from the node
				Guideline  aGuideline = new Guideline();
				FillerGuideline aFiller = new FillerGuideline(aGuideline,aNode);	
				aGuideline = (Guideline)aFiller.getFilledElement();
				// add the guideline in the list
				theGuidelineList.add(aGuideline);
			}			
		}
		return theGuidelineList;
	}

	private static Set<Activity> getAllActivities() {
		Set<Activity> activitiesList = new LinkedHashSet<Activity>();
		
		/* evaluate the XPAth request and return the nodeList*/
		NodeList activities = (NodeList)XMLUtils.evaluate(xpath_activity,XPathConstants.NODESET);
		

		/* For each node */
		Node aNode;
		for(int i = 0 ; i < activities.getLength(); i++){
			/* for each list element , get the list item */
			aNode = activities.item(i);
			Activity anActivity = new Activity();
			/* Filler for the iteration and the item (node)*/
			FillerActivity itFiller = new FillerActivity(anActivity, aNode);	
			Activity returnedActivityFilled = (Activity) itFiller.getFilledElement();
			/* Add the filled object in the result List */
			activitiesList.add(returnedActivityFilled) ;
		}			

		return activitiesList;
	}

	/**
	 * fills the taskslist with task definition
	 * 
	 * Used By the start method
	 * @return a list of all the TaskDefinition of the XML File
	 * @throws Exception
	 */
	private static Vector<TaskDefinition> fillTaskDefinitionsList() {
		Vector<TaskDefinition> theTaskDefinitionsList; // the return of the function
		
		// initializes the List
		theTaskDefinitionsList = new Vector<TaskDefinition>();
		theTaskDefinitionsList.clear();
		
		// gets all the nodes containing taskDefinions
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_taskDefinition,XPathConstants.NODESET);
		
		// For each node...
		Node aNode;
		for (int i = 0; i < nodeReturned.getLength(); i++) {
			aNode = nodeReturned.item(i);
			
			// Fills the TaskDefinition from the node
			TaskDefinition aTaskDefinition = new TaskDefinition();
			FillerTask aFiller = new FillerTask(aTaskDefinition, aNode);	
			aTaskDefinition = (TaskDefinition)aFiller.getFilledElement();
			setStepsByTaskDefinition(aTaskDefinition, aNode);
			setGuideByTaskDefinition(aTaskDefinition, aNode);
			
			
			theTaskDefinitionsList.add(aTaskDefinition);
		}
		
		return theTaskDefinitionsList;
	}
	
	/**
	 * fills the RolesList with RoleDefinition
	 * Used by the start Method
	 * @return a list of all the RoleDefinition of the XML File
	 * @throws Exception
	 */
	private static Vector<RoleDefinition> fillRoleDefinitionsList () {
		Vector<RoleDefinition> theRoleDefinitionsList; // the return of the function
		
		// initializes the List
		theRoleDefinitionsList = new Vector<RoleDefinition>();
		theRoleDefinitionsList.clear();
		
		// gets all the nodes containing roleDefinions
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_roleDefinition,XPathConstants.NODESET);
		
		// For each node...
		Node aNode;
		for(int i=0;i<nodeReturned.getLength();i++){
			aNode = nodeReturned.item(i);
			
			// Fills the RoleDefinition from the node
			RoleDefinition  aRoleDefinition = new RoleDefinition();
			FillerRole aFiller = new FillerRole(aRoleDefinition,aNode);	
			aRoleDefinition = (RoleDefinition)aFiller.getFilledElement();
			
			theRoleDefinitionsList.add(aRoleDefinition);
		}	
		return theRoleDefinitionsList;
	}
	
	
	/**
	 * getProcess
	 * Return a Process from a file
	 * Deprecated, use getAllProcesses instead
	 * @param f a XML file
	 * @return the process
	 * @throws Exception 
	 */
	public static Process getProcess(File XMLFilePath) {		
		Process theProcess = null;
		if (XMLFilePath.exists()) {
			XMLUtils.setDocument(XMLFilePath);
			start(); // initializes the elements sets
			
			// The List of all the nodes containing Processes
			NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_deliveryProcess,XPathConstants.NODESET);
			
			Node aNode;			
			if ( nodeReturned.getLength()!= 0) {
				theProcess = new Process();
				aNode = nodeReturned.item(0);
				
				if (aNode != null) {
					theProcess = (Process) getBreakDownElementsFromNode(aNode);
				}				
			}
		}
		return theProcess;
	}
	
	/**
	 * getProcess
	 * @deprecated
	 * @return the process
	 */
	private static Process getProcess (){
		Process p = new Process() ;
		try{			
			// get all the roles descriptor
			Set<RoleDescriptor> ensRole = allRoleDescriptors ;
			for (Iterator i = ensRole.iterator() ; i.hasNext() ;) {
				p.addBreakdownElement((BreakdownElement) i.next());
			}
			// get all the tasks descriptor
			Set<TaskDescriptor> allTasks = allTaskDescriptors;
			for (Iterator i = allTasks.iterator() ; i.hasNext() ;) {
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
	private static Set<TaskDescriptor> getAllTaskDescriptors(Set<RoleDescriptor> allRoles) {
		// gets all the roles in the file
		HashSet<TaskDescriptor> taskList = new HashSet<TaskDescriptor>();

		NodeList taskDescriptors = (NodeList)XMLUtils.evaluate(xpath_taskDescriptor,XPathConstants.NODESET);
		
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
			
			// affect the additional roles to the current task 
			setAddiotionalRoleByTaskDescriptor(taskDescriptorfilled, aNode, allRoles);
			
			taskList.add(taskDescriptorfilled);
		}
		return taskList;
	}
	
	/**
	 * getTasksByTaskDescriptor
	 * @param t
	 * @return a task 
	 */
	private static void setTaskByTaskDescriptor(TaskDescriptor _t,Node _node) {
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
			if (taskTobereturn != null){
				//set the task in the taskdescriptor
				_t.addTaskDefinition(taskTobereturn);
			}
		}
	}
	
	/**
	 * getTaskDefinitionByID
	 * @param _id
	 * @return
	 */
	private static TaskDefinition getTaskDefinitionByID(String _id){
		for (int i = 0 ; i < TaskDefinitionsList.size() ; i ++){
			if (TaskDefinitionsList.get(i).getGuid().equals(_id)){
				return TaskDefinitionsList.get(i);
			}
		}
		return null ;
	}
	
	/**
	 * setStepByTaskDefinition
	 * @param _taskd
	 * @param _n
	 * @throws Exception
	 */
	private static void setStepsByTaskDefinition(TaskDefinition _taskd,Node _n)  {
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
	 * setGuideByTaskDefinition
	 * @param _taskDefinition
	 * @param _node
	 */
	private static void setGuideByTaskDefinition(TaskDefinition _taskDefinition, Node _node) {
		NodeList listOfTdNodes = _node.getChildNodes() ;
		Guideline GuideTobereturn = null;

		String idGuide = "";
		
		// search the nodes of the guide
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(guideline)){				
				// recuperation des differents id des guidelines
				idGuide = listOfTdNodes.item(i).getTextContent();				
				
				GuideTobereturn = getGuidelineById(idGuide);
				// if the guideline doesn't exist
				if (GuideTobereturn != null){
					 //set the guideline in the taskDefinition
					_taskDefinition.addGuideline(GuideTobereturn);
				}
				
			}
		}	
		

	}
	
	/**
	 * setAddiotionalRoleByTaskDescriptor
	 * @param _t
	 * @param _n
	 * @param _s
	 * @throws Exception
	 */
	private static void setAddiotionalRoleByTaskDescriptor(TaskDescriptor _t,Node _n,Set<RoleDescriptor> _s) {
//		 getting the id of the role
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(additionallyPerformedBy)){
				idRole = listOfTdNodes.item(i).getTextContent();
				RoleDescriptor roleToBeset ;
				roleToBeset = getRoleDescriptorById(_s, idRole);
				// if the task doesn't exist
//				if (roleToBeset == null){
//					throw new Exception("role " + idRole + " doesn't exist");
//				}
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
	private static void setMainRoleByTaskDescriptor(TaskDescriptor _t, Node _n, Set<RoleDescriptor> _s) {
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
			if (roleToBeset != null){
//				throw new Exception("role " + idRole + " doesn't exist");
//				 set the role in the roledescriptor
				_t.addMainRole(roleToBeset);
			}
			
		}
	}
	/**
	 * setRoleByRoleDescriptor
	 * @param r
	 * @param n
	 * @return
	 */
	private static void setRoleByRoleDescriptor(RoleDescriptor _r,Node _n) {
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
			if (roleTobereturn != null){
			// set the role in the roledescriptor
				_r.addRoleDefinition(roleTobereturn);
			}
		}
	}
	
	/**
	 * getRoleDescriptorById
	 * @param aSet
	 * @param id
	 * @return RoleDescriptor
	 */
	private static RoleDescriptor getRoleDescriptorById(Set<RoleDescriptor> aSet, String id){
		for (Iterator i = aSet.iterator() ; i.hasNext() ;){
			RoleDescriptor tmp = (RoleDescriptor) i .next();
			if (tmp.getGuid().equals(id)){
				return  tmp;
			}
		}
		return null ;
	}
	
	/**
	 * getTaskDescriptorById
	 * @param aSet
	 * @param id
	 * @return TaskDescriptor
	 */
	private static TaskDescriptor getTaskDescriptorById(Set<TaskDescriptor> aSet,String id){
		for (Iterator i = aSet.iterator() ; i.hasNext() ;){
			TaskDescriptor tmp = (TaskDescriptor) i .next();
			if (tmp.getGuid().equals(id)){
				return  tmp;
			}
		}
		return null ;
	}
	
	/**
	 * getAllIterations
	 * @return Set
	 */
	private static Set<Iteration> getAllIterations() {
		LinkedHashSet<Iteration> iterationList = new LinkedHashSet<Iteration>();
		/* evaluate the XPAth request and return the nodeList*/
		NodeList iterations = (NodeList)XMLUtils.evaluate(xpath_iteration,XPathConstants.NODESET);
		if (iterations == null){
			System.out.println("Pas d'iterations");
		}
		else {
		/* there is one or several iterations */
			Node aNode;
			for(int i=0;i<iterations.getLength();i++){
				/* for each list element , get the list item */
				aNode = iterations.item(i);
				Iteration aIteration = new Iteration();
				/* Filler for the iteration and the item (node)*/
				FillerIteration itFiller = new FillerIteration(aIteration, aNode);	
				Iteration returnedIterationFilled = (Iteration) itFiller.getFilledElement();
				/* Add the filled object in the result List */
				iterationList.add(returnedIterationFilled) ;
			}			
		}
		return iterationList;
	}

	/**
	 * getAllPhases
	 * @return Set
	 */
	private static Set<Phase> getAllPhases() {
		LinkedHashSet<Phase> phaseList = new LinkedHashSet<Phase>();
		/* evaluate the XPAth request and return the nodeList*/
		NodeList phases = (NodeList)XMLUtils.evaluate(xpath_phase,XPathConstants.NODESET);
		if (phases == null){
			System.out.println("Pas de phases");
		}
		else {
		/* there is one or several phase */
			Node aNode;
			for(int i=0;i<phases.getLength();i++){
				/* for each list element , get the list item */
				aNode = phases.item(i);
				Phase aPhase = new Phase();
				/* Filler for the phase and the item (node)*/
				FillerPhase phFiller = new FillerPhase(aPhase, aNode);	
				Phase returnedPhaseFilled = (Phase) phFiller.getFilledElement();
				/* Add the filled object in the result List */
				phaseList.add(returnedPhaseFilled) ;
			}			
		}
		return phaseList;
	}
	
	/**
	 * getAllRoleDescriptors 
	 * @return all the tasks descriptors
	 * @throws Exception when no tasks descriptor are found
	 */
	private static Set<RoleDescriptor> getAllRoleDescriptors() {
		LinkedHashSet<RoleDescriptor> roleList = new LinkedHashSet<RoleDescriptor>();
		
		
		NodeList roleDescriptors = (NodeList)XMLUtils.evaluate(xpath_roleDescriptor,XPathConstants.NODESET);
		
		Node aNode;
		for(int i=0;i<roleDescriptors.getLength();i++) {
			aNode = roleDescriptors.item(i);
			RoleDescriptor aRoleDescriptor = new RoleDescriptor();
			FillerRoleDescriptor aFiller = new FillerRoleDescriptor(aRoleDescriptor,aNode);	
			RoleDescriptor roleDescriptorfilled = (RoleDescriptor)aFiller.getFilledElement();
			
			setRoleByRoleDescriptor(roleDescriptorfilled,aNode);
							
			roleList.add(roleDescriptorfilled) ;
		}
		
		return roleList;
	}
	
	/**
	 * getRoleDefinitionByID
	 * @param _id
	 * @return
	 */
	private static RoleDefinition getRoleDefinitionByID(String _id) {
		for (int i = 0 ; i < RoleDefinitionsList.size() ; i ++){
			if (RoleDefinitionsList.get(i).getGuid().equals(_id)){
				return RoleDefinitionsList.get(i);
			}
		}
		return null ;
	}
	
	/**
	 * Returns the BreakdownElement filled from the node, with all the BreakdownElements that
	 * belong to it
	 * @param _node The Node containing the main info of the BreakdownElement
	 * @return the BreakdownELement filled
	 */
	private static BreakdownElement getBreakDownElementsFromNode (Node _node) {
		//BreakdownElement returnedBde ; // the BreakdownElement Returned by the function
		
		 // Filler used to fill Elements;
		// bdeId; // used when calling one of the getElementById functions (ex : getPhase by id);
		
		BreakdownElement returnedBde = null;
		//FillerElement BdeFiller;
		
		
		returnedBde = getFillerBDE(_node, returnedBde);
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(activity)) {
			if (_node.getAttributes().getNamedItem(attr_name_variabilityBasedOnElement) != null) {
				String parentElementID = _node.getAttributes().getNamedItem(attr_name_variabilityBasedOnElement).getNodeValue();
				/* Xpath request to get the activity list by the id: variabilityBasedOnElement*/
				
				String xpath_parentElement = "//Process[@*[namespace-uri() and local-name()='type']='uma:CapabilityPattern' and @id='" + parentElementID + "']";
				
				NodeList parentElement = (NodeList)XMLUtils.evaluate(xpath_parentElement,XPathConstants.NODESET);
				
				if (parentElement.getLength() == 1) {
					_node = parentElement.item(0);
				}
			}
		}
		
		// We're getting with the included elements
		if ((returnedBde != null) && returnedBde instanceof Activity) {
			BreakdownElement tmpBde; // used to receive the temp result of this function
											// when called recursively
			NodeList listNode = _node.getChildNodes();
			
			for (int i = 0 ; i < listNode.getLength() ; i++) {
				if (listNode.item(i).getNodeName().equals(breakdownElement)) {
					tmpBde = getBreakDownElementsFromNode(listNode.item(i));
					
					if (tmpBde != null) {					
						if (tmpBde instanceof Activity) {
							//((Activity) returnedBde).addActivity((Activity) tmpBde);
							((Activity) returnedBde).addBreakdownElement((Activity) tmpBde);
						}
						else {
							((Activity) returnedBde).addBreakdownElement(tmpBde);
						}
					}
				}
			}
		}
		
		return returnedBde;
	}

	/**
	 * getFillerBDE
	 * @param _node
	 * @param returnedBde
	 * @param bdeId
	 * @return BreakdownElement
	 */
	private static BreakdownElement getFillerBDE(Node _node, BreakdownElement returnedBde) {
		FillerElement BdeFiller;		
		String bdeId = _node.getAttributes().getNamedItem(id).getNodeValue();
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(phase)) {
			returnedBde = getPhaseById(allPhases, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(process)) {
			returnedBde = new Process();
			BdeFiller = new FillerProcess(returnedBde, _node);	
			returnedBde = (Process) BdeFiller.getFilledElement();
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(role_descriptor)) {			
			returnedBde = getRoleDescriptorById(allRoleDescriptors, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(task_descriptor)) {
			returnedBde = getTaskDescriptorById(allTaskDescriptors, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(iteration)) {
			returnedBde = getIterationById(allIterations, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(activity)) {
			returnedBde = getActivityById(allActivities, bdeId);
		}
		
		return returnedBde;
	}
	
	private static Activity getActivityById(Set<Activity> aSetActivity, String bdeId) {
		for (Iterator i = aSetActivity.iterator() ; i.hasNext() ;){
			Activity tmp = (Activity) i .next();
			if (tmp.getGuid().equals(bdeId)){
				return  tmp;
			}
		}
		return null ;
	}

	/**
	 * getIterationById
	 * @param allIterations2
	 * @param bdeId
	 * @return
	 */
	private static Iteration getIterationById(Set<Iteration> aSetIteration, String bdeId) {
		for (Iterator i = aSetIteration.iterator() ; i.hasNext() ;){
			Iteration tmp = (Iteration) i .next();
			if (tmp.getGuid().equals(bdeId)){
				return  tmp;
			}
		}
		return null ;
	}

	/**
	 * getPhaseById
	 * @param allPhases2
	 * @param bdeId
	 * @return
	 */
	private static Phase getPhaseById(Set<Phase> allPhases2, String bdeId) {
		for (Iterator i = allPhases2.iterator() ; i.hasNext() ;){
			Phase tmp = (Phase) i .next();
			if (tmp.getGuid().equals(bdeId)){
				return tmp;
			}
		}
		return null ;
	}
	
	
	/**
	 * getGuidelineById
	 * @param bdeId
	 * @return
	 */
	private static Guideline getGuidelineById(String bdeId) {
		for (Iterator i = GuidesList.iterator() ; i.hasNext() ;){
			Guideline tmp = (Guideline) i .next();
			if (tmp.getGuid().equals(bdeId)){
				return tmp;
			}
		}
		return null ;
	}

	/**
	 * getAllProcesses
	 * Returns a Set of the processes contained in the XML File
	 * @deprecated
	 * @param XMLFilePath Path of the XML File
	 * @return Set of the processes
	 * @throws Exception
	 */
	public static Set<Process> getAllProcesses(File XMLFilePath) {
		Set<Process> processesReturned = new HashSet<Process>() ;
		if (XMLFilePath.exists()) {
			XMLUtils.setDocument(XMLFilePath);
			start(); // initializes the elements sets
			
			// The List of all the nodes containing Processes
			NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_deliveryProcess,XPathConstants.NODESET);
			
			Node aNode;			
			for(int i = 0; i < nodeReturned.getLength(); i++) {
				Process tmpProcess = new Process();
				aNode = nodeReturned.item(i);
				
				if (aNode != null) {
					tmpProcess = (Process) getBreakDownElementsFromNode(aNode);
					processesReturned.add(tmpProcess);
				}				
			}
		}
		return processesReturned;
	}
	
}

