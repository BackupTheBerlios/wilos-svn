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

package wilos.presentation.web.wilosuser;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.model.misc.wilosuser.ProcessManager;

/**
 * Managed-Bean link to processmanager_create.jsp
 * 
 * @author Marseyeah
 */
public class ProcessManagerBean {

	private ProcessManagerService processManagerService;

	private WebCommonService webCommonService;

	private ProcessManager processManager;

	private LoginService loginService;

	private String passwordConfirmation;

	private List<ProcessManager> processManagerList;

	private String processManagerView;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public ProcessManagerBean() {
		this.processManager = new ProcessManager();
	}

	/**
	 * Method for saving processManager data from form
	 * 
	 * @return
	 */
	public String saveProcessManagerAction() {
		String url = "";
		boolean error = false;
		// test if the fields are correctly completed
		if (this.processManager.getName().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.lastnameRequired"));
		}
		if (this.processManager.getFirstname().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.firstnameRequired"));
		}
		if (this.processManager.getLogin().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.loginRequired"));
		}
		if (this.processManager.getPassword().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.passwordRequired"));
		}
		if (this.passwordConfirmation.trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.confirmpasswordRequired"));
		}

		if (!error) {
			if (this.loginService.loginExist(this.processManager.getLogin()
					.trim())) {
				this.webCommonService
						.addErrorMessage(this.webCommonService
								.getStringFromBundle("component.processmanagercreate.err.loginalreadyexist"));
			} else {
				this.processManagerService
						.saveProcessManager(this.processManager);
				this.webCommonService
						.addInfoMessage(this.webCommonService
								.getStringFromBundle("component.processmanagercreate.success"));
				// TODO add the return of the process manager tab.
			}
		}
		this.processManager = new ProcessManager();
		this.passwordConfirmation = new String();

		return url;
	}

	/**
	 * 
	 * methode qui controle que les deux mots de passes sont identiques
	 * 
	 * @param _context
	 * @param _toValidate
	 * @param _value
	 * @throws ValidatorException
	 */
	public void passwordEqualValidation(FacesContext _context,
			UIComponent _toValidate, Object _value) throws ValidatorException {

		UIComponent passcomponent = _toValidate.findComponent("equal1PM");
		String passValue = (String) passcomponent.getAttributes().get("value");

		if (!_value.equals(passValue)) {
			FacesMessage message = new FacesMessage();
			message
					.setSummary(this.webCommonService
							.getStringFromBundle("component.processmanagercreate.err.passwordnotequals"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	/**
	 * Getter of processManager.
	 * 
	 * @return the processManager.
	 */
	public ProcessManager getProcessManager() {
		return this.processManager;
	}

	/**
	 * Setter of processManager.
	 * 
	 * @param _processManager
	 *            The processManager to set.
	 */
	public void setProcessManager(ProcessManager _processManager) {
		this.processManager = _processManager;
	}

	/**
	 * Getter of processManagerService.
	 * 
	 * @return the processManagerService.
	 */
	public ProcessManagerService getProcessManagerService() {
		return this.processManagerService;
	}

	/**
	 * Setter of processManagerService.
	 * 
	 * @param _processManagerService
	 *            The processManagerService to set.
	 */
	public void setProcessManagerService(
			ProcessManagerService _processManagerService) {
		this.processManagerService = _processManagerService;
	}

	/**
	 * @return
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * @param passwordConfirmation
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService;
	}

	/**
	 * Getter of ProcessManagerList.
	 * 
	 * @return the ProcessManagerList.
	 */
	public List<ProcessManager> getProcessManagerList() {
		this.processManagerList = new ArrayList<ProcessManager>();
		processManagerList.addAll(this.processManagerService
				.getProcessManagers());
		return this.processManagerList;
	}

	/**
	 * Setter of ProcessManagerList.
	 * 
	 * @param _ProcessManagerList
	 *            The ProcessManagerList to set.
	 */
	public void setProcessManagerList(List<ProcessManager> _processManagerList) {
		this.processManagerList = _processManagerList;
	}

	/**
	 * Getter of processManagerView.
	 * 
	 * @return the processManagerView.
	 */
	public String getProcessManagerView() {
		if (this.getProcessManagerList().size() == 0) {
			this.processManagerView = "processManagerView_null";
		} else {
			this.processManagerView = "processManagerView_not_null";
		}
		return this.processManagerView;
	}

	/**
	 * Setter of processManagerView.
	 * 
	 * @param _processManagerView
	 *            The processManagerView to set.
	 */
	public void setProcessManagerView(String _processManagerView) {
		this.processManagerView = _processManagerView;
	}

	/**
	 * @return the webCommonService
	 */
	public WebCommonService getWebCommonService() {
		return webCommonService;
	}

	/**
	 * @param webCommonService
	 *            the webCommonService to set
	 */
	public void setWebCommonService(WebCommonService webCommonService) {
		this.webCommonService = webCommonService;
	}

}
