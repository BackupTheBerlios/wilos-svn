/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

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

		
		//texte +="\n"+ classe + "\n" + msg;
		
		
		// shows the message box
		new ErrorDialog(texte);
	}
}
