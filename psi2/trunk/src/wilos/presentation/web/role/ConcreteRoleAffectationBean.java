package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.role.ConcreteRoleAffectationService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

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
			this.logger.debug("nodeId : " + nodeId);
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
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage() ;
		message.setSummary(bundle.getString("component.project.projectroles.validationMessage")) ;
		message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		facesContext.addMessage(null, message) ;
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
}
