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

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import wilos.presentation.web.template.MenuBean;
import wilos.presentation.web.template.PageContentBean;
import wilos.utils.Constantes.State;

public class WebCommonService {
	
	/* Manage Bundle */
	
	public String getStringFromBundle(String _bundleValue){
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext
						.getCurrentInstance().getApplication()
						.getDefaultLocale());
		return bundle.getString(_bundleValue);
	}
	
	/* Manage Beans */
	
	public Object getBean(String _beanName){
		return this.getCurrentFacesContext()
		.getApplication().getVariableResolver()
		.resolveVariable(this.getCurrentFacesContext(), _beanName);
	}
	
	/* Manage pages */
	
	public void changeContentPage(String _pageUrl) {
		this.getMenuBean().changePage(_pageUrl);
	}

	public PageContentBean getSelectedPanel(){
		return this.getMenuBean().getSelectedPanel();
	}
	
	/* Manage messages */
	
	public void addInfoMessage(String _message){
		FacesMessage message = new FacesMessage();
		message.setSummary(_message);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		this.getCurrentFacesContext().addMessage(null, message);
	}
	
	public void addErrorMessage(String _message){
		FacesMessage message = new FacesMessage();
		message.setSummary(_message);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		this.getCurrentFacesContext().addMessage(null, message);
	}
	
	/* Manage constante translation according the langue */
	
	public String getDisplayedState(String _state) {
		String stateInString = "";
		if (_state.equals(State.CREATED)) {
			stateInString = this.getStringFromBundle("constantes.state.created");
		} else if (_state.equals(State.READY)) {
			stateInString = this.getStringFromBundle("constantes.state.ready");
		} else if (_state.equals(State.STARTED)) {
			stateInString = this.getStringFromBundle("constantes.state.started");
		} else if (_state.equals(State.SUSPENDED)) {
			stateInString = this.getStringFromBundle("constantes.state.suspended");
		} else if (_state.equals(State.FINISHED)) {
			stateInString = this.getStringFromBundle("constantes.state.finished");
		}
		return stateInString;
	}
	
	/* Private methods */
	
	private FacesContext getCurrentFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	private MenuBean getMenuBean(){
		MenuBean menuBean = (MenuBean) this.getCurrentFacesContext().getApplication()
				.createValueBinding("#{menu}").getValue(this.getCurrentFacesContext());
		return menuBean;
	}
}
