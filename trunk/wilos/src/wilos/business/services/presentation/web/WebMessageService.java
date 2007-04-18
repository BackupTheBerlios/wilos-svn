/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
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

package wilos.business.services.presentation.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class WebMessageService {
	
	public void addInfoMessage(String _message){
		FacesMessage message = new FacesMessage();
		message.setSummary(_message);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext facesContext = FacesContext
				.getCurrentInstance();
		facesContext.addMessage(null, message);
	}
	
	public void addErrorMessage(String _message){
		FacesMessage message = new FacesMessage();
		message.setSummary(_message);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext facesContext = FacesContext
				.getCurrentInstance();
		facesContext.addMessage(null, message);
	}

}
