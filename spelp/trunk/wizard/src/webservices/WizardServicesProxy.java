package webservices;

import java.util.ArrayList;
import java.util.List;

import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDescriptor;

public class WizardServicesProxy {
	public static ArrayList<RoleDescriptor> getRolesByUser(String login, String password) {		
		ArrayList<RoleDescriptor> myRoleListe;
		RoleDescriptor aTmpRole;
		TaskDescriptor aTmpTask;
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		myRoleListe = new ArrayList<RoleDescriptor>();
		
		aTmpRole.setName("Developper");
		aTmpRole.setDescription("Un gars qui développe");
		
		aTmpTask.setName("Coder le programme");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Aimer son programme");
		aTmpTask.setDescription("Un grand moment d'amour");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Passer le balai");
		aTmpTask.setDescription("Et c'est plus propre");
		aTmpRole.addPrimaryTask(aTmpTask);
		
		myRoleListe.add(aTmpRole);
		
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		aTmpRole.setName("Tester");
		aTmpRole.setDescription("Faire des essais, en gros");
		aTmpTask.setName("Tester le programme");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Detester le programme");
		aTmpTask.setDescription("Un grand moment de haine");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Passer la serpillière");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		
		myRoleListe.add(aTmpRole);
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		aTmpRole.setName("Conceptualisateur");
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Conceptualiser les concepts du programme");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Rever du programme");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Faire le café concept");
		aTmpRole.addPrimaryTask(aTmpTask);
		
		myRoleListe.add(aTmpRole);
		
		return myRoleListe;
	}
}
