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

package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.role.ConcreteRoleAffectationService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.presentation.web.project.ProjectAdvancementBean;
import wilos.presentation.web.tree.TreeBean;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ConcreteRoleAffectationBean {

	private ConcreteRoleAffectationService concreteRoleAffectationService;
	
	private ParticipantService participantService;
	
	private WebSessionService webSessionService;
	
	private WebCommonService webCommonService;
	
	private List<HashMap<String,Object>> concreteRolesDescriptorsList;
	
	private String nodeId;
	
	private String oldNodeId;
	
	private String selectRolesView;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public ConcreteRoleAffectationBean()
	{
		this.concreteRolesDescriptorsList = new ArrayList<HashMap<String,Object>>();
	}
	
	
	/**
	 * Getter of selectAffectedProjectView.
	 *
	 * @return the selectAffectedProjectView.
	 */
	public String getSelectRolesView() {
		if (this.getConcreteRolesDescriptorsList().size()==0 )
		{
			this.selectRolesView  = "no_roles_view";
		}
		else
		{
			this.selectRolesView ="roles_view";
		}
		return this.selectRolesView;
	}

	/**
	 * Setter of selectAffectedProjectView.
	 *
	 * @param _selectAffectedProjectView The selectAffectedProjectView to set.
	 */
	public void setSelectRolesView(String _selectRolesView) {
		this.selectRolesView = _selectRolesView ;
	}
	
	/**
	 * Getter of participantService.
	 *
	 * @return the participantService.
	 */
	public ParticipantService getParticipantService() {
		return this.participantService ;
	}

	/**
	 * Setter of participantService.
	 *
	 * @param _participantService The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService ;
	}
	
	/**
	 * Getter of concreteRoleAffectationService.
	 *
	 * @return the concreteRoleAffectationService.
	 */
	public ConcreteRoleAffectationService getConcreteRoleAffectationService() {
		return this.concreteRoleAffectationService ;
	}

	/**
	 * Setter of concreteRoleAffectationService.
	 *
	 * @param _concreteRoleAffectationService The concreteRoleAffectationService to set.
	 */
	public void setConcreteRoleAffectationService(ConcreteRoleAffectationService _concreteRoleAffectationService) {
		this.concreteRoleAffectationService = _concreteRoleAffectationService ;
	}

	/**
	 * Getter of webSessionService.
	 *
	 * @return the webSessionService.
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService ;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param _webSessionService The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService ;
	}

	/**
	 * Getter of concreteRolesDescriptorsList.
	 *
	 * @return the concreteRolesDescriptorsList.
	 */
	public List<HashMap<String, Object>> getConcreteRolesDescriptorsList() {
		this.concreteRolesDescriptorsList.clear();
			List<ConcreteRoleDescriptor> globalCRD = this.concreteRoleAffectationService.getAllConcreteRolesDescriptorsForActivity(this.nodeId,(String)this.webSessionService.getAttribute(WebSessionService.PROJECT_ID));
			for(Iterator iter = globalCRD.iterator(); iter.hasNext();){
				ConcreteRoleDescriptor element = (ConcreteRoleDescriptor) iter.next() ;
				HashMap<String,Object> hm = new HashMap<String,Object>();
				hm.put("concreteId",element.getId());
				hm.put("concreteName",element.getConcreteName());
				hm.put("affected", ((HashMap<String,Boolean>)this.getParticipantAffectationForConcreteRoleDescriptor((String)this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID),element.getId())).get("affected"));
				hm.put("not_allowed", ((HashMap<String,Boolean>)this.getParticipantAffectationForConcreteRoleDescriptor((String)this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID),element.getId())).get("not_allowed"));
				this.concreteRolesDescriptorsList.add(hm);
			}
		return this.concreteRolesDescriptorsList ;
	}
	
	public String saveConcreteRoleAffectation() {
		for(HashMap<String,Object> concreteRoleInfo : this.concreteRolesDescriptorsList){
			this.concreteRoleAffectationService.saveParticipantConcreteRoles(concreteRoleInfo,(String)this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID));
		}
		this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.project.projectroles.validationMessage"));
		
		//refresh the tree 
		TreeBean tb = (TreeBean) this.webCommonService.getBean("TreeBean");
		tb.refreshProjectTree();
		
		//refresh the project advancement table
		ProjectAdvancementBean pab = (ProjectAdvancementBean) this.webCommonService.getBean("ProjectAdvancementBean");
		pab.refreshProjectTable();

		return "";
	}

	
	/**
	 * TODO Method description
	 *
	 * @param _wilosUserId
	 * @param _concreteId
	 * @return
	 */
	
	private HashMap<String,Boolean> getParticipantAffectationForConcreteRoleDescriptor(String _wilosUserId, String _concreteId) {
		return this.concreteRoleAffectationService.getParticipantAffectationForConcreteRoleDescriptor(_wilosUserId,_concreteId);
	}

	/**
	 * Setter of concreteRolesDescriptorsList.
	 *
	 * @param _concreteRolesDescriptorsList The concreteRolesDescriptorsList to set.
	 */
	public void setConcreteRolesDescriptorsList(List<HashMap<String, Object>> _concreteRolesDescriptorsList) {
		this.concreteRolesDescriptorsList = _concreteRolesDescriptorsList ;
	}

	/**
	 * Getter of nodeId.
	 *
	 * @return the nodeId.
	 */
	public String getNodeId() {
		return this.nodeId ;
	}

	/**
	 * Setter of nodeId.
	 *
	 * @param _nodeId The nodeId to set.
	 */
	public void setNodeId(String _nodeId) {
		this.oldNodeId = this.nodeId;
		this.nodeId = _nodeId ;
	}

	/**
	 * Getter of oldNodeId.
	 *
	 * @return the oldNodeId.
	 */
	public String getOldNodeId() {
		return this.oldNodeId ;
	}

	/**
	 * Setter of oldNodeId.
	 *
	 * @param _oldNodeId The oldNodeId to set.
	 */
	public void setOldNodeId(String _oldNodeId) {
		this.oldNodeId = _oldNodeId ;
	}


	/**
	 * @return the webCommonService
	 */
	public WebCommonService getWebCommonService() {
		return webCommonService;
	}


	/**
	 * @param webCommonService the webCommonService to set
	 */
	public void setWebCommonService(WebCommonService webCommonService) {
		this.webCommonService = webCommonService;
	}
}
