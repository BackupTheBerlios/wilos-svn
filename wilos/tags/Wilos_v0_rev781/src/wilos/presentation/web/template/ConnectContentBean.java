package wilos.presentation.web.template;

import com.icesoft.faces.component.tree.IceUserObject;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * TODO
 */
public class ConnectContentBean extends IceUserObject {

    private String templateName = "";

    // True indicates that there is content associated with link and as a
    // result templateName and contentPanelName can be used
    private boolean pageContent = true;

    // message bundle for component.
    private static ResourceBundle messages = null;

    // view reference to control the visible content
    private ConnectViewBean navigationBean;

    /**
     * TODO
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
     * TODO.
     *
     * @return panel stack template name.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * TODO
     *
     * @param templateName valid panel name
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * TODO
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
     * TODO
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
}