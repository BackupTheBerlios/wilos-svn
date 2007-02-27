package wilos.presentation.assistant.control;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.dialogs.ErrorDialog;

public class ExceptionManager {
	
	public ExceptionManager(Exception e) {
		
		String texte = Bundle.getText("exceptionManager.simpleError");
		
		String classe = null;
		String msg = null;
		
		classe = e.getClass().getName();
		msg = e.getMessage();
		


		// WebServices' exceptions
		if(classe.equals("javax.xml.ws.WebServiceException")) {
			String nomExep = msg.substring(0, msg.indexOf(':'));
			
			if(nomExep.equalsIgnoreCase("java.net.UnknownHostException"))
			{
				texte = Bundle.getText("exceptionManager.unknownHost");
			}
			else if (nomExep.equalsIgnoreCase("java.io.FileNotFoundException")) {
				texte = Bundle.getText("exceptionManager.unknownHost");
			}
			else if (nomExep.equalsIgnoreCase("java.net.ConnectException")) {
				texte = Bundle.getText("exceptionManager.connectionRefused");
			}
		}
		
		// Wilos' exceptions
		if(classe.substring(0, 6).equals("wilos.")) {
			if(msg.equalsIgnoreCase("le wilos user n'existe pas")) {
				texte = Bundle.getText("exceptionManager.unknownUser");
			}
		}

		// others exceptions
		if (classe.equalsIgnoreCase("java.net.MalformedURLException")) {
			texte = Bundle.getText("exceptionManager.malformedURL");
		}

		
//		texte = classe + "\n" + msg;
		
		
		// shows the message box
		new ErrorDialog(texte);
	}
}
