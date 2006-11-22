package applitest;

import java.rmi.RemoteException;

import webservices.TestWebServicesProxy;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// on instancie le proxy local
		TestWebServicesProxy px = new TestWebServicesProxy();
		// on specifie l'adresse du serveur
		px.setEndpoint("http://localhost:8080/remote/services/TestWebServices");
		// on appelle la méthode sur le proxy local comme on le ferait sur l'objet distant
		try {
			System.out.println(px.getExample());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
