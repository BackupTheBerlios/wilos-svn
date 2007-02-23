package wilos.presentation.assistant.view.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.utils.Constantes;

public class WizardTreeModel extends DefaultTreeModel {
	Participant participant ;
	//String root;
	
	public WizardTreeModel(Participant aParticipant,boolean principal) {
		super(null);
		this.participant = aParticipant;
		initTree(principal);
	}
	
	// TODO : actuellement prise en compte d'une super activity a voir pour plusieurs
	private ConcreteActivity getActivity (Set<ConcreteActivity> s){
		ConcreteActivity ca = null ;
		try {
			ca = s.iterator().next();
		}
		catch(Exception e){
			
		}
		
		return ca ;
	}
	
	// init tree initialize all the nodes of the jtree
	private void initTree(boolean principal) {
		//TreePanel.this.tree.removeAll();
		if (principal){
			WizardStateMachine.getInstance().deleteAllStep() ;
		}
		HashMap<ConcreteActivity, WizardMutableTreeNode> mapActivity = new HashMap<ConcreteActivity, WizardMutableTreeNode>();
		int idStep = 0 ;
		this.root = new DefaultMutableTreeNode(participant.getName());
		ArrayList<Step> tmp = new ArrayList<Step>();
		Set<ConcreteRoleDescriptor> roles = participant.getConcreteRoleDescriptors();
		
		// browse all the concrete roles
		for (ConcreteRoleDescriptor crd : roles) {
			
			DefaultMutableTreeNode precedent = null;
			WizardMutableTreeNode rdWmt = new WizardMutableTreeNode(crd);
			ConcreteActivity ca = getActivity(crd.getSuperConcreteActivities());
			WizardMutableTreeNode nodeAct = null  ;
			// getting all the superactivities from the roles
			if (ca == null){
				// if there is no super activity we add the role to the root
				((DefaultMutableTreeNode)this.root).add(rdWmt);
			}
			else {
				// if there is super activities 
				// we have to add them to the root or to an other super activity
				while (ca != null){
					// search if the activity is already treated as a node
					if (!mapActivity.containsKey(ca)){
						nodeAct = new WizardMutableTreeNode(ca);
						mapActivity.put(ca, nodeAct);
					}
					else {
						nodeAct = mapActivity.get(ca);
					}
					if (precedent == null){
						// if precedent == null we are at the first round 
						// so we had to add the role descriptor
						nodeAct.add(rdWmt);
					}
					else {
						nodeAct.add(precedent);
					}
					ca = getActivity(ca.getSuperConcreteActivities());
					if (ca == null ){
						((DefaultMutableTreeNode)this.root).add(nodeAct);
					}
					precedent = nodeAct ;
				}				
			}
			// brows all the concrete tasks
			for(ConcreteTaskDescriptor ctd : crd.getConcreteTaskDescriptors()) {
				
				WizardMutableTreeNode ctdWmt = new WizardMutableTreeNode(ctd);
				rdWmt.add(ctdWmt);
				// browse all the steps of the task def in task descriptors of concretetaskdescriptor
				if (ctd.getTaskDescriptor().getTaskDefinition() != null) {
					// cloning the steps to have same steps with different states
					for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()){
						try {
							// creating the clone and store them in a list
							Step sCloned = s.clone();
							sCloned.setGuid(String.valueOf(idStep));
							idStep++;
							tmp.add(sCloned);
							
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// removing the steps from the task def and adding the cloned
					TaskDefinition tdef = ctd.getTaskDescriptor().getTaskDefinition() ;
					tdef.removeAllSteps();
					for (Step s : tmp){
						tdef.addStep(s);
					}
					tmp.clear();
					// normal process
					for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()) {
						WizardMutableTreeNode sWmt;
						sWmt = new WizardMutableTreeNode(s);
						ctdWmt.add(sWmt);
						// managing the steps
						if (principal) {
							if (ctd.getState().equals(Constantes.State.STARTED)){
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_READY ) ;
							}
							else if (ctd.getState().equals(Constantes.State.FINISHED)){
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_FINISHED ) ;
							}
							else {
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_CREATED ) ;
							}
						}
					}
				}
			}
			//((DefaultMutableTreeNode) this.root).add(new DefaultMutableTreeNode(rd.getName()));
		}

	}
}
	

