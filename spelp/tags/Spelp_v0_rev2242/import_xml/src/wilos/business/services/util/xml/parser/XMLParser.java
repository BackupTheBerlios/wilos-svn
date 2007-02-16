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
import wilos.business.services.util.xml.fillers.FillerGuidance;
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
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkOrder;

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
	private static final String xpath_guidance = "//ContentElement[@*[namespace-uri() and local-name()='type']='uma:Guideline' or " +
			"@*[namespace-uri() and local-name()='type']='uma:Concept' or " +
			"@*[namespace-uri() and local-name()='type']='uma:SupportingMaterial' or " +
			"@*[namespace-uri() and local-name()='type']='uma:Roadmap' or " +
			"@*[namespace-uri() and local-name()='type']='uma:TermDefinition' or " +
			"@*[namespace-uri() and local-name()='type']='uma:ToolMentor' or " +
			"@*[namespace-uri() and local-name()='type']='uma:Whitepaper' or " +
			"@*[namespace-uri() and local-name()='type']='uma:ReusableAsset' or " +
			"@*[namespace-uri() and local-name()='type']='uma:Report' " +
			"]";
	
	// Sections
	public static final String task = "Task";
	public static final String role = "Role";
	public static final String performedPrimarilyBy = "PerformedPrimarilyBy";
	public static final String additionallyPerformedBy = "AdditionallyPerformedBy";
	public static final String step = "Section";
	public static final String presentation = "Presentation";
	public static final String breakdownElement = "BreakdownElement";
	public static final String predecessor = "Predecessor";
	public static final String maindescription = "MainDescription";
	
	
	private static Set<String> guidancesTypes = new LinkedHashSet<String>();

	
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
	private static final String attr_name_linkType = "linkType";
	
	/* Variables recording elements */
	// Filled by fillTaskDefinitionsList and fillRoleDefinitionsList
	protected static Vector<TaskDefinition> TaskDefinitionsList = new Vector<TaskDefinition> ();
	protected static Vector<RoleDefinition> RoleDefinitionsList = new Vector<RoleDefinition>() ;
	protected static Vector<Guidance> GuidancesList = new Vector<Guidance>() ;
	
	
	// this variables contain all the Elements that concern them
	protected static Set<RoleDescriptor> roleDescriptorsList ;
	protected static Set<TaskDescriptor> taskDescriptorsList ;
	protected static Set<Phase> phasesList;
	protected static Set<Iteration> iterationsList;
	protected static Set<Activity> activitiesList;

	
	/**
	 * Fills the Lists in memory
	 * To launch before everything by the parsing method
	 */
	private static void fillAllElementsList() {
		try {
			initGuidancesTypesList();
			
			GuidancesList = fillGuidesList();
			RoleDefinitionsList = fillRoleDefinitionsList();
			TaskDefinitionsList = fillTaskDefinitionsList();
			
			roleDescriptorsList = fillRoleDescriptorsList() ;
			taskDescriptorsList = fillTaskDescriptorsList(roleDescriptorsList);			
			setAllTaskDescriptorsDependencies(taskDescriptorsList);
			
			phasesList = fillPhasesList();
			iterationsList = fillIterationsList();
			
			activitiesList = fillActivitiesList();
			setAllActivitiesDependencies(activitiesList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * make a set with the name of each type of guidance
	 */
	private static void initGuidancesTypesList() {
		guidancesTypes.add(Guidance.checklist);
		guidancesTypes.add(Guidance.concept);
		guidancesTypes.add(Guidance.estimationConsiderations);
		guidancesTypes.add(Guidance.guideline);
		guidancesTypes.add(Guidance.practice);
		guidancesTypes.add(Guidance.report);
		guidancesTypes.add(Guidance.reusableAsset);
		guidancesTypes.add(Guidance.roadMap);
		guidancesTypes.add(Guidance.supportingMaterial);
		guidancesTypes.add(Guidance.termDefinition);
		guidancesTypes.add(Guidance.toolMentor);
		guidancesTypes.add(Guidance.whitepaper);		
	}

	/**
	 * fillGuidesList
	 * @return a Guidance vector 
	 */
	private static Vector<Guidance> fillGuidesList() {
		Vector<Guidance> theGuidanceList; // the return of the function
		
		// initializes the List
		theGuidanceList = new Vector<Guidance>();
		theGuidanceList.clear();
		
		// gets all the nodes containing all guidances
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_guidance,XPathConstants.NODESET);
		if (nodeReturned.getLength() != 0) {
			// For each node...
			Node aNode;
			for(int i=0;i<nodeReturned.getLength();i++){
				aNode = nodeReturned.item(i);
				
				// Fills the Guidance from the node
				Guidance  aGuidance = new Guidance();
				FillerGuidance aFiller = new FillerGuidance(aGuidance,aNode);	
				aGuidance = (Guidance)aFiller.getFilledElement();
				// add the guidance in the list
				theGuidanceList.add(aGuidance);
			}	
		}
		return theGuidanceList;
	}

	/**
	 * getAllActivities
	 * @return the Set of all activity
	 */
	private static Set<Activity> fillActivitiesList() {
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
			
			setGuidanceByActivity(returnedActivityFilled, aNode);
			
			/* Add the filled object in the result List */
			activitiesList.add(returnedActivityFilled) ;
			
		}			

		return activitiesList;
	}
	
	/**
	 * setGuidanceByActivity
	 * @param anActivity
	 * @param node
	 */
	private static void setGuidanceByActivity(Activity anActivity, Node node) {
		NodeList listOfTdNodes = node.getChildNodes() ;
		Guidance GuideTobereturn = null;

		String idGuide = "";
		
		// search the nodes of the guide
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if(guidancesTypes.contains(listOfTdNodes.item(i).getNodeName())) {
				// recuperation des differents id des guidelines
				idGuide = listOfTdNodes.item(i).getTextContent();				
				
				GuideTobereturn = getGuidanceById(idGuide);
				// if the guideline doesn't exist
				if (GuideTobereturn != null){
					 //set the guideline in the taskDefinition
					anActivity.addGuidance(GuideTobereturn);
				}
			}
		}	
	}

	/**
	 * setAllDependencyActivity
	 * @param aSet
	 */
	private static void setAllActivitiesDependencies(Set<Activity> _aSet) {
		
		// evaluate the XPAth request and return the nodeList
		NodeList activities = (NodeList)XMLUtils.evaluate(xpath_activity,XPathConstants.NODESET);
		
		// For each node 
		Node aNode;
		for(int i = 0 ; i < activities.getLength(); i++){
			// for each list element , get the list item 
			aNode = activities.item(i);
			Activity anActivity = new Activity();
			// Filler for the iteration and the item (node)
			FillerActivity itFiller = new FillerActivity(anActivity, aNode);	
			Activity returnedActivityFilled = (Activity) itFiller.getFilledElement();
			for (Activity act: _aSet) {
				if (act.getGuid().equals(returnedActivityFilled.getGuid())) {
					returnedActivityFilled = act;
				}
			}
			
			// affect the additional dependency to the current activity 
			setDependencyByActivity(returnedActivityFilled, aNode);
		}
	}

	/**
	 * setDependencyByActivity
	 * @param _act
	 * @param _node
	 */
	private static void setDependencyByActivity(Activity _act, Node _node) {
		// search the predecessor
		Activity ActivityTobereturn = null;
		// To get the id of the task
		String idAct_pred = "" ;
		// To get the dependency type ("FinishToFinish" for example)
		String linkType = "";
		NodeList listOfTdNodes = _node.getChildNodes() ;
		
		// for each node
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(predecessor)){
				// the node contains a tag: Predecessor from the XML file
				idAct_pred = listOfTdNodes.item(i).getTextContent();
				linkType = (listOfTdNodes.item(i).getAttributes().getNamedItem(attr_name_linkType).getNodeValue());
				
				// process if there is a task for this task desriptor			
				ActivityTobereturn = getActivityById(activitiesList, idAct_pred);
				// if the task doesn't exist
				if (ActivityTobereturn != null){
					//Add the predecessor to the activity
					WorkOrder wo = new WorkOrder();
					wo.setLinkType(linkType);
					// set all dependancy to the current activity
					ActivityTobereturn.addSuccessor(wo);
					_act.addPredecessor(wo);
				}
			}				
		}
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
			// affect the additional steps to the current task 
			setStepsByTaskDefinition(aTaskDefinition, aNode);
			// affect the additional guidances to the current task 			
			setGuidanceByTaskDefinition(aTaskDefinition, aNode);
			// add the current task to the list to be return			
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
			// affect the additional guidance to the current role 
			setGuidanceByRoleDefinition(aRoleDefinition, aNode);
			// add the filled RoleDefinition in the list to be return
			theRoleDefinitionsList.add(aRoleDefinition);
		}	
		return theRoleDefinitionsList;
	}
	
	
	/**
	 * getProcess
	 * Return a Process from a file
	 * @param f a XML file
	 * @return the process
	 * @throws Exception 
	 */
	public static Process getProcess(File XMLFilePath) {		
		// The process that will be returned by the function
		Process theProcess = null;
		
		// If the file exists and is not empty
		if (XMLFilePath.exists() && XMLFilePath.length() > 5) {
			// XMLUtils contains the low-level parsing functions and needs the file Path
			XMLUtils.setDocument(XMLFilePath);
			
			// fills the elements sets
			fillAllElementsList(); 
			
			// We get the List of all the nodes containing Processes
			NodeList processesNodeList = (NodeList)XMLUtils.evaluate(xpath_deliveryProcess,XPathConstants.NODESET);
			
			Node aNode;
			// If the file contains a process
			if ( processesNodeList.getLength() != 0 ) {
				theProcess = new Process();
				// We get the Node corresponding to the process
				aNode = processesNodeList.item(0);
				
				if (aNode != null) {
					// We get the process from this recursive function
					theProcess = (Process) getBreakDownElementsFromNode(aNode);
				}				
			}
		}
		return theProcess;
	}	
	
	/**
	 * getAllTaskDescriptors 
	 * @return all the tasks descriptors
	 * @throws Exception when no tasks descriptor are found
	 */
	private static Set<TaskDescriptor> fillTaskDescriptorsList(Set<RoleDescriptor> allRoles) {
		// List of the taskDescriptor to be return
		HashSet<TaskDescriptor> taskList = new HashSet<TaskDescriptor>();
		// XPath Request to get all TaskDescriptor nodes  
		NodeList taskDescriptors = (NodeList)XMLUtils.evaluate(xpath_taskDescriptor,XPathConstants.NODESET);
		
		Node aNode;
		// for each node
		for(int i=0;i<taskDescriptors.getLength();i++){
			aNode = taskDescriptors.item(i);
			// Fills the TaskDescriptor by the node
			TaskDescriptor aTaskDescriptor = new TaskDescriptor();
			FillerTaskDescriptor aFiller = new FillerTaskDescriptor(aTaskDescriptor,aNode);	
			TaskDescriptor taskDescriptorfilled = (TaskDescriptor)aFiller.getFilledElement();
			
			// affect the task definition to the task descriptor
			setTaskByTaskDescriptor(taskDescriptorfilled,aNode);
			
			// affect the main role to the current task
			setMainRoleByTaskDescriptor(taskDescriptorfilled, aNode, allRoles);
			
			// affect the additional roles to the current task 
			setAddiotionalRoleByTaskDescriptor(taskDescriptorfilled, aNode, allRoles);
			// add the filled taskDescriptor in the list to be return
			taskList.add(taskDescriptorfilled);
		}
		return taskList;
	}
	
	/**
	 * setDependencyByTaskDescriptor
	 * @param _aSet 
	 * @param taskDescriptorfilled
	 * @param node
	 */
	private static void setDependencyByTaskDescriptor(TaskDescriptor _t, Node _node) {
		// search the predecessor
		TaskDescriptor taskTobereturn = null;
		// to get the id of the task
		String idTask_pred = "" ;
		// to get the link for dependancy ("FinishToFinish for example")
		String linkType = ""; 
		NodeList listOfTdNodes = _node.getChildNodes() ;
		
		// for each node of listOfTdNodes
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(predecessor)){
				// the node contains a tag : Predecessor in the XML file
				idTask_pred = listOfTdNodes.item(i).getTextContent();
				// get the link of the dependancy
				linkType = (listOfTdNodes.item(i).getAttributes().getNamedItem(attr_name_linkType).getNodeValue());
				
				// process if there is a task for this task desriptor			
				taskTobereturn = getTaskDescriptorById(taskDescriptorsList, idTask_pred);
				// if the task doesn't exist
				if (taskTobereturn != null){
					WorkOrder wo = new WorkOrder();
					wo.setLinkType(linkType);					
					// set the dependency of the TaskDescriptors
					taskTobereturn.addSuccessor(wo);
					_t.addPredecessor(wo);
				}
			}			
		}
	}
	
	/**
	 * setAllTaskDescriptorsDependencies
	 * @param _allTaskD
	 */
	private static void setAllTaskDescriptorsDependencies(Set<TaskDescriptor> _aSet) {
		// gets all the roles in the file
		NodeList taskDescriptors = (NodeList)XMLUtils.evaluate(xpath_taskDescriptor,XPathConstants.NODESET);
		
		Node aNode;
		// for each node
		for(int i=0; i<taskDescriptors.getLength();i++){
			aNode = taskDescriptors.item(i);
			// Fills the current TaskDescriptor
			TaskDescriptor aTaskDescriptor = new TaskDescriptor();
			FillerTaskDescriptor aFiller = new FillerTaskDescriptor(aTaskDescriptor,aNode);	
			TaskDescriptor taskDescriptorfilled = (TaskDescriptor)aFiller.getFilledElement();
			for (TaskDescriptor td: _aSet) {
				if (td.getGuid().equals(taskDescriptorfilled.getGuid())) {
					taskDescriptorfilled = td;
				}
			}
			// affect the additional dependency to the current taskDescriptor 
			setDependencyByTaskDescriptor(taskDescriptorfilled, aNode);			
		}		
	}		

	/**
	 * setTaskByTaskDescriptor	 * 
	 * @param t
	 * @return a task 
	 */
	private static void setTaskByTaskDescriptor(TaskDescriptor _t,Node _node) {
		TaskDefinition taskTobereturn = null;
		// getting the id of the task
		String idTask = "" ;
		NodeList listOfTdNodes = _node.getChildNodes() ;
		boolean trouve = false ;
		// search the nodes of the taskDescriptor
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(task)){
				// the current node contains a tag: Task in the XML file
				trouve = true ;
				// get the id of the current task
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
		
		// search the nodes of the task
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(presentation)){
				// the current node contains the tag: Presentation
				
				// search the nodes of the step
				for (int j = 0 ; j < listOfTdNodes.item(i).getChildNodes().getLength() ; j ++){
					if (listOfTdNodes.item(i).getChildNodes().item(j).getNodeName().equals(step)){
						// the current node contains the tag: step in the XML file
						Step aStep = new Step() ;
						// fills the step
						FillerStep fs = new FillerStep(aStep,listOfTdNodes.item(i).getChildNodes().item(j));
						aStep = (Step) fs.getFilledElement();
						// add the current step to the TaskDefinition
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
	private static void setGuidanceByTaskDefinition(TaskDefinition _taskDefinition, Node _node) {
		// get the child node of the taskDefinition in the list 
		NodeList listOfTdNodes = _node.getChildNodes() ;
		Guidance GuideTobereturn = null;

		String idGuide = "";
		
		// search the nodes of the guidance
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if(guidancesTypes.contains(listOfTdNodes.item(i).getNodeName())) {
				// recuperation des differents id des guidelines
				idGuide = listOfTdNodes.item(i).getTextContent();				
				
				GuideTobereturn = getGuidanceById(idGuide);
				// if the guidance doesn't exist
				if (GuideTobereturn != null){
					 //add the current guidance to the taskDefinition
					_taskDefinition.addGuidance(GuideTobereturn);
				}				
			}
		}	
	}

	/**
	 * setGuideByTaskDefinition
	 * @param _taskDefinition
	 * @param _node
	 */
	private static void setGuidanceByRoleDefinition(RoleDefinition _roleDefinition, Node _node) {
		NodeList listOfTdNodes = _node.getChildNodes() ;
		Guidance GuideTobereturn = null;

		String idGuide = "";
		
		// search the nodes of the guide
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if(guidancesTypes.contains(listOfTdNodes.item(i).getNodeName())) {
				// get the id of the current node
				idGuide = listOfTdNodes.item(i).getTextContent();
				// get the guidance object by the id
				GuideTobereturn = getGuidanceById(idGuide);
				// if the guideline doesn't exist
				if (GuideTobereturn != null){
					 //set the guideline in the taskDefinition
					_roleDefinition.addGuidance(GuideTobereturn);
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
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		
		// search the additionalRole
		for (int i = 0 ; i < listOfTdNodes.getLength() ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(additionallyPerformedBy)){
				// the current node contains the flag: additionallyPerformedBy in the XML file
				idRole = listOfTdNodes.item(i).getTextContent();
				RoleDescriptor roleToBeset ;
				// get the RoleDescriptor Object by the id
				roleToBeset = getRoleDescriptorById(_s, idRole);				
				// set the role to the roledescriptor
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
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		boolean trouve = false ;
		// search the node of the performedPrimarilyBy
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			// the current node contains the flag: performedPrimarilyBy in the XML file
			if (listOfTdNodes.item(i).getNodeName().equals(performedPrimarilyBy)){
				trouve = true ;
				// get the id of current node
				idRole = listOfTdNodes.item(i).getTextContent();
			}
		}
		if (trouve){
			RoleDescriptor roleToBeset ;
			roleToBeset = getRoleDescriptorById(_s,idRole);
			// if the task doesn't exist
			if (roleToBeset != null){
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
		// To get the id of the role
		String idRole = "" ;
		NodeList listOfTdNodes = _n.getChildNodes() ;
		boolean trouve = false ;
		// search the node of the role
		for (int i = 0 ; i < listOfTdNodes.getLength() && !trouve ; i ++){
			if (listOfTdNodes.item(i).getNodeName().equals(role)){
				// the current node contains the flag: Role in the XML file
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
	 * @param _aSet
	 * @param _id
	 * @return RoleDescriptor
	 */
	private static RoleDescriptor getRoleDescriptorById(Set<RoleDescriptor> _aSet, String _id){
		for (Iterator i = _aSet.iterator() ; i.hasNext() ;){
			RoleDescriptor roleDescriptorTmp = (RoleDescriptor) i .next();
			if (roleDescriptorTmp.getGuid().equals(_id)){
				return roleDescriptorTmp;
			}
		}
		return null ;
	}
	
	/**
	 * getTaskDescriptorById
	 * @param _aSet
	 * @param _id
	 * @return TaskDescriptor
	 */
	private static TaskDescriptor getTaskDescriptorById(Set<TaskDescriptor> _aSet,String _id){
		for (Iterator i = _aSet.iterator() ; i.hasNext() ;){
			TaskDescriptor taskDescriptorTmp = (TaskDescriptor) i .next();
			if (taskDescriptorTmp.getGuid().equals(_id)){
				return taskDescriptorTmp;
			}
		}
		return null ;
	}
	
	/**
	 * getAllIterations
	 * @return Set
	 */
	private static Set<Iteration> fillIterationsList() {
		LinkedHashSet<Iteration> iterationList = new LinkedHashSet<Iteration>();
		// evaluate the XPAth request and return the nodeList
		NodeList iterations = (NodeList)XMLUtils.evaluate(xpath_iteration,XPathConstants.NODESET);
		if (iterations == null){
			System.out.println("Pas d'iterations");
		}
		else {
		// there is one or several iterations 
			Node aNode;
			for(int i=0;i<iterations.getLength();i++){
				// for each list element , get the list item 
				aNode = iterations.item(i);
				Iteration aIteration = new Iteration();
				// Filler for the iteration and the item (node)
				FillerIteration itFiller = new FillerIteration(aIteration, aNode);	
				Iteration returnedIterationFilled = (Iteration) itFiller.getFilledElement();
				// affect the additional Guidance to the current Activity				 
				setGuidanceByActivity(returnedIterationFilled, aNode);				
				// Add the filled object in the result List 
				iterationList.add(returnedIterationFilled) ;
			}			
		}
		return iterationList;
	}

	/**
	 * getAllPhases
	 * @return Set
	 */
	private static Set<Phase> fillPhasesList() {
		LinkedHashSet<Phase> phaseList = new LinkedHashSet<Phase>();
		/* evaluate the XPAth request and return the nodeList*/
		NodeList phases = (NodeList)XMLUtils.evaluate(xpath_phase,XPathConstants.NODESET);

		if (phases != null) {
			/* there is one or several phase */
			Node aNode;
			for(int i=0;i<phases.getLength();i++){
				/* for each list element , get the list item */
				aNode = phases.item(i);
				Phase aPhase = new Phase();
				/* Filler for the phase and the item (node)*/
				FillerPhase phFiller = new FillerPhase(aPhase, aNode);	
				Phase returnedPhaseFilled = (Phase) phFiller.getFilledElement();
				
				setGuidanceByActivity(returnedPhaseFilled, aNode);
				
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
	private static Set<RoleDescriptor> fillRoleDescriptorsList() {
		LinkedHashSet<RoleDescriptor> roleList = new LinkedHashSet<RoleDescriptor>();		
		
		// get list of nodes by the XPath expression
		NodeList roleDescriptors = (NodeList)XMLUtils.evaluate(xpath_roleDescriptor,XPathConstants.NODESET);
		
		Node aNode;
		// for each node
		for(int i=0;i<roleDescriptors.getLength();i++) {
			aNode = roleDescriptors.item(i);
			// Fills the RoleDescriptor
			RoleDescriptor aRoleDescriptor = new RoleDescriptor();
			FillerRoleDescriptor aFiller = new FillerRoleDescriptor(aRoleDescriptor,aNode);	
			RoleDescriptor roleDescriptorfilled = (RoleDescriptor)aFiller.getFilledElement();
			// affect the additionnal Role in the Descriptor
			setRoleByRoleDescriptor(roleDescriptorfilled,aNode);
			// add the current RoleDescriptor Object in the list to be return				
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
		BreakdownElement returnedBde = null;
		
		// get the filled BreakDownElement Object 
		returnedBde = getBdeFromItsList(_node);
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(activity)) {
			if (_node.getAttributes().getNamedItem(attr_name_variabilityBasedOnElement) != null) {
				// the current node contains the attribute: variabilityBasedOnElement in the XML file				
				String parentElementID = _node.getAttributes().getNamedItem(attr_name_variabilityBasedOnElement).getNodeValue();
				// Xpath request to get the activity list by the id: variabilityBasedOnElement				
				String xpath_parentElement = "//Process[@*[namespace-uri() and local-name()='type']='uma:CapabilityPattern' and @id='" + parentElementID + "']";
				// get the list of the nodes by the XPath Expression
				NodeList parentElement = (NodeList)XMLUtils.evaluate(xpath_parentElement,XPathConstants.NODESET);
				if (parentElement.getLength() == 1) {
					// the nodeList is not empty
					_node = parentElement.item(0);
				}
			}
		}
		
		// We're getting with the included elements
		if ((returnedBde != null) && returnedBde instanceof Activity) {
			BreakdownElement tmpBde; // used to receive the temp result of this function
									 // when called recursively
			NodeList listNode = _node.getChildNodes();
			// search the node of the breakdownElement
			for (int i = 0 ; i < listNode.getLength() ; i++) {
				if (listNode.item(i).getNodeName().equals(breakdownElement)) {
					// the current node contains a flag: breakdownElement in the XML file
					tmpBde = getBreakDownElementsFromNode(listNode.item(i));
					// test if the BreakDownElement exist
					if (tmpBde != null) {					
						if (tmpBde instanceof Activity) {
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
	 * getBdeFromItsList
	 * @param _node
	 * @param _nullBdeToReturn
	 * @param bdeId	 
	 * This methods gets the Bde From its List. The List and the id of the BDe are determined using the
	 * Node given in parameter. This methods fills the BreakdownElement given in parameter.
	 */
	private static BreakdownElement getBdeFromItsList(Node _node) {
		BreakdownElement _nullBdeToReturn = null;
		FillerElement BdeFiller;		
		String bdeId = _node.getAttributes().getNamedItem(id).getNodeValue();
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(phase)) {
			_nullBdeToReturn = getPhaseById(phasesList, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(process)) {
			_nullBdeToReturn = new Process();
			BdeFiller = new FillerProcess(_nullBdeToReturn, _node);	
			_nullBdeToReturn = (Process) BdeFiller.getFilledElement();
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(role_descriptor)) {			
			_nullBdeToReturn = getRoleDescriptorById(roleDescriptorsList, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(task_descriptor)) {
			_nullBdeToReturn = getTaskDescriptorById(taskDescriptorsList, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(iteration)) {
			_nullBdeToReturn = getIterationById(iterationsList, bdeId);
		}
		
		if (_node.getAttributes().getNamedItem(attr_name_xsitype).getNodeValue().equals(activity)) {
			_nullBdeToReturn = getActivityById(activitiesList, bdeId);
		}
		
		return _nullBdeToReturn;	
	}
	
	/**
	 * getActivityById
	 * @param _aSetActivity
	 * @param _bdeId
	 * @return a Activity
	 */
	private static Activity getActivityById(Set<Activity> _aSetActivity, String _bdeId) {
		for (Iterator i = _aSetActivity.iterator() ; i.hasNext() ;){
			Activity activityTmp = (Activity) i .next();
			if (activityTmp.getGuid().equals(_bdeId)){
				return  activityTmp;
			}
		}
		return null ;
	}

	/**
	 * getIterationById
	 * @param allIterations2
	 * @param _bdeId
	 * @return
	 */
	private static Iteration getIterationById(Set<Iteration> _aSetIteration, String _bdeId) {
		for (Iterator i = _aSetIteration.iterator() ; i.hasNext() ;){
			Iteration iterationTmp = (Iteration) i .next();
			if (iterationTmp.getGuid().equals(_bdeId)){
				return  iterationTmp;
			}
		}
		return null ;
	}

	/**
	 * getPhaseById
	 * @param _allPhases2
	 * @param _bdeId
	 * @return
	 */
	private static Phase getPhaseById(Set<Phase> _allPhases2, String _bdeId) {
		for (Iterator i = _allPhases2.iterator() ; i.hasNext() ;){
			Phase phaseTmp = (Phase) i .next();
			if (phaseTmp.getGuid().equals(_bdeId)){
				return phaseTmp;
			}
		}
		return null ;
	}	
	
	/**
	 * getGuidanceById
	 * @param _bdeId
	 * @return a Guidance
	 */
	private static Guidance getGuidanceById(String _bdeId) {
		for (Iterator i = GuidancesList.iterator() ; i.hasNext() ;){
			Guidance guidanceTmp = (Guidance) i .next();
			if (guidanceTmp.getGuid().equals(_bdeId)){
				return guidanceTmp;
			}
		}
		return null ;
	}	
}
