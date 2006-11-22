package applitest;

import java.util.ArrayList;
import java.util.List;
import java.rmi.RemoteException;

import woops2.model.role.*;
import wilos.spelp.webservices.WizardServicesProxy;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// on instancie le proxy local
		WizardServicesProxy px = new WizardServicesProxy();
		// on specifie l'adresse du serveur
		px.setEndpoint("http://localhost:8080/wilos_remote/services/WizardServices");
		// on appelle la m�thode sur le proxy local comme on le ferait sur l'objet distant
		try {
			RoleDescriptor [] tabRoles = px.getRolesByUser("toto", "toto");
			for (int i=0; i<tabRoles.length; i++) {
				System.out.println(tabRoles[i].getName());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
