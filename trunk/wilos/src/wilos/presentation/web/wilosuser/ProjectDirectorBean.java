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
import wilos.business.services.misc.wilosuser.ProjectDirectorService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * Managed-Bean link to projectDirector_create.jsp
 * 
 * @author Marseyeah
 */
public class ProjectDirectorBean {

	private ProjectDirectorService projectDirectorService;

	private WebCommonService webCommonService;

	private ProjectDirector projectDirector;

	private LoginService loginService;

	private String passwordConfirmation;

	private List<ProjectDirector> projectDirectorList;

	private String projectDirectorView;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public ProjectDirectorBean() {
		this.projectDirector = new ProjectDirector();
	}

	/**
	 * Method for saving projectDirector data from form
	 * 
	 * @return
	 */
	public String saveProjectDirectorAction() {
		String url = "";
		boolean error = false;
		// test if the fields are correctly completed
		if (this.projectDirector.getName().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.lastnameRequired"));
		}
		if (this.projectDirector.getFirstname().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.firstnameRequired"));
		}
		if (this.projectDirector.getLogin().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.loginRequired"));
		}
		if (this.projectDirector.getPassword().trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.passwordRequired"));
		}
		if (this.passwordConfirmation.trim().length() == 0) {
			error = true;
			this.webCommonService
					.addErrorMessage(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.confirmpasswordRequired"));
		}

		if (!error) {
			if (this.loginService.loginExist(this.projectDirector.getLogin()
					.trim())) {
				this.webCommonService
						.addErrorMessage(this.webCommonService
								.getStringFromBundle("component.projectdirectorcreate.err.loginalreadyexist"));
			} else {
				this.projectDirectorService
						.saveProjectDirector(this.projectDirector);
				this.webCommonService
						.addInfoMessage(this.webCommonService
								.getStringFromBundle("component.projectdirectorcreate.success"));
				//TODO add the return of the project director tab.
			}
		}
		this.projectDirector = new ProjectDirector();

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

		UIComponent passcomponent = _toValidate.findComponent("equal1PD");
		String passValue = (String) passcomponent.getAttributes().get("value");

		if (!_value.equals(passValue)) {
			FacesMessage message = new FacesMessage();
			message
					.setSummary(this.webCommonService
							.getStringFromBundle("component.projectdirectorcreate.err.passwordnotequals"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	/**
	 * Getter of projectDirector.
	 * 
	 * @return the projectDirector.
	 */
	public ProjectDirector getProjectDirector() {
		return this.projectDirector;
	}

	/**
	 * Setter of projectDirector.
	 * 
	 * @param _projectDirector
	 *            The projectDirector to set.
	 */
	public void setProjectDirector(ProjectDirector _projectDirector) {
		this.projectDirector = _projectDirector;
	}

	/**
	 * Getter of projectDirectorService.
	 * 
	 * @return the projectDirectorService.
	 */
	public ProjectDirectorService getProjectDirectorService() {
		return this.projectDirectorService;
	}

	/**
	 * Setter of projectDirectorService.
	 * 
	 * @param _projectDirectorService
	 *            The projectDirectorService to set.
	 */
	public void setProjectDirectorService(
			ProjectDirectorService _projectDirectorService) {
		this.projectDirectorService = _projectDirectorService;
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
	 * Getter of ProjectDirectorList.
	 * 
	 * @return the ProjectDirectorList.
	 */
	public List<ProjectDirector> getProjectDirectorList() {
		this.projectDirectorList = new ArrayList<ProjectDirector>();
		projectDirectorList.addAll(this.projectDirectorService
				.getProjectDirectors());
		return this.projectDirectorList;
	}

	/**
	 * Setter of ProcessManagerList.
	 * 
	 * @param _ProcessManagerList
	 *            The ProcessManagerList to set.
	 */
	public void setProjectDirectorList(
			List<ProjectDirector> _projectDirectorList) {
		this.projectDirectorList = _projectDirectorList;
	}

	/**
	 * Getter of projectDirectorView.
	 * 
	 * @return the projectDirectorView.
	 */
	public String getProjectDirectorView() {
		if (this.getProjectDirectorList().size() == 0) {
			this.projectDirectorView = "projectDirectorView_null";
		} else {
			this.projectDirectorView = "projectDirectorView_not_null";
		}
		return this.projectDirectorView;
	}

	/**
	 * Setter of projectDirectorView.
	 * 
	 * @param _projectDirectorView
	 *            The projectDirectorView to set.
	 */
	public void setProcessManagerView(String _projectDirectorView) {
		this.projectDirectorView = _projectDirectorView;
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
