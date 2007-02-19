package wilos.presentation.assistant.control;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import wilos.presentation.assistant.view.dialogs.ErrorDialog;

public class ExceptionManager {
	
	public ExceptionManager(Exception e) {
		
		String classe = e.getClass().getName();
		String cause = e.getCause().toString();
		String msg = e.getMessage();
		String locMsg = e.getLocalizedMessage();
		
		String texte = "Une erreur est survennue.";
		String nomExep = cause.substring(0, cause.indexOf(':'));
		String descExep = cause.substring(cause.indexOf(':')+2,cause.length());
		
		if(nomExep.equalsIgnoreCase("java.net.UnknownHostException")) {
			texte = "Le serveur '" + descExep + "' est introuvable.";
		}
		else if (nomExep.equalsIgnoreCase("java.net.ConnectException")) {
			texte = "Connection refusée.";
		}

		
	//	texte = classe + "\n" + cause + "\n" + msg + "\n" + locMsg + "\n" + nomExep;
	//	System.out.println("\n\n\n"+texte+"\n\n\n");

		new ErrorDialog(texte);
	}
}
