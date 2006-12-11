package wilos.presentation.web.template;

/**
 * <p>The MenuBean class is responsible for storing the state of the two
 * panel stacks which display dynamic content.  </p>
 *
 * @since 0.3.0
 */
public class ConnectViewBean {

    // selected page content bean.
    private ConnectContentBean selectedPanel;

    /**
     * Gets the currently selected content panel.
     *
     * @return currently selected content panel.
     */
    public ConnectContentBean getSelectedPanel() {
        return selectedPanel;
    }

    /**
     * Sets the selected panel to the specified panel.
     *
     * @param selectedPanel a none null page content bean.
     */
    public void setSelectedPanel(ConnectContentBean selectedPanel) {
        if (selectedPanel != null && selectedPanel.isPageContent()) {
            this.selectedPanel = selectedPanel;
        }
    }
    
    public String connected(boolean connected){
    	ConnectContentBean connectContent = new ConnectContentBean();
    	if(connected)
    		connectContent.setTemplateName("connected");
    	else
    		connectContent.setTemplateName("not_connected");
    	connectContent.setNavigationSelection(this);
    	this.selectedPanel = connectContent;
    	return "";
    }
}
