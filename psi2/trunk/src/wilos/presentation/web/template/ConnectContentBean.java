package wilos.presentation.web.template;

import com.icesoft.faces.component.tree.IceUserObject;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This bean is used to manage the display zone for the connection area.
 * @author sadasblackmilk
 */
public class ConnectContentBean extends IceUserObject {

    private String templateName = "";
    private String templateNameActions = "";

    // True indicates that there is content associated with link and as a
    // result templateName and contentPanelName can be used
    private boolean pageContent = true;

    // message bundle for component.
    private static ResourceBundle messages = null;

    // view reference to control the visible content
    private ConnectViewBean navigationBean;

    /**
     * TODO Method description
     */
    public ConnectContentBean() {
        super(null);
        init();
    }

    /**
     * Initialize internationalization.
     */
    private void init() {

        setExpanded(true);

        Locale locale =
                FacesContext.getCurrentInstance().getViewRoot().getLocale();
        // assign a default locale if the faces context has none, shouldn't happen
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        messages = ResourceBundle.getBundle(
                "wilos.resources.messages",
                locale);
    }

    /**
     * Gets the navigation callback.
     *
     * @return NavigationBean.
     */
    public ConnectViewBean getNavigationSelection() {
        return navigationBean;
    }

    /**
     * Sets the navigation callback.
     *
     * @param navigationBean controls selected panel state.
     */
    public void setNavigationSelection(ConnectViewBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Getter for TemplateName
     *
     * @return panel stack template name.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * TODO Method description
     *
     * @param templateName valid panel name
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * TODO Method description
     *
     * @return true if the page contains content; otherwise, false.
     */
    public boolean isPageContent() {
        return pageContent;
    }

    /**
     * Sets the page content.
     *
     * @param pageContent True if the page contains content; otherwise, false.
     */
    public void setPageContent(boolean pageContent) {
        this.pageContent = pageContent;
    }

    /**
     * Sets the navigationSelectionBeans selected state
     */
    public void contentVisibleAction(ActionEvent event) {
        if (isPageContent()) {
            // only toggle the branch expansion if we have already selected the node
            if (navigationBean.getSelectedPanel().equals(this)) {
                // toggle the branch node expansion
                setExpanded(!isExpanded());
            }
            navigationBean.setSelectedPanel(this);
        }
        // Otherwise toggle the node visibility, only changes the state
        // of the nodes with children.
        else {
            setExpanded(!isExpanded());
        }
    }

	/**
	 * Getter of templateNameActions.
	 *
	 * @return the templateNameActions.
	 */
	public String getTemplateNameActions() {
		return this.templateNameActions ;
	}

	/**
	 * Setter of templateNameActions.
	 *
	 * @param _templateNameActions The templateNameActions to set.
	 */
	public void setTemplateNameActions(String _templateNameActions) {
		this.templateNameActions = _templateNameActions ;
	}
}