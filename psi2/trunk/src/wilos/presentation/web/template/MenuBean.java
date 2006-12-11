package wilos.presentation.web.template;

/**
 * <p>The MenuBean class is responsible for storing the state of the two
 * panel stacks which display dynamic content.  </p>
 *
 * @since 0.3.0
 */
public class MenuBean {

    // selected page content bean.
    private PageContentBean selectedPanel;

    /**
     * Gets the currently selected content panel.
     *
     * @return currently selected content panel.
     */
    public PageContentBean getSelectedPanel() {
        return selectedPanel;
    }

    /**
     * Sets the selected panel to the specified panel.
     *
     * @param selectedPanel a none null page content bean.
     */
    public void setSelectedPanel(PageContentBean selectedPanel) {
        if (selectedPanel != null && selectedPanel.isPageContent()) {
            this.selectedPanel = selectedPanel;
        }
    }
    
    public String welcome(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("welcome");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }
    
    public String subscribe(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("subscribe");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }
    
    public String wilos(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("wilos");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }

	public void changePage(String url) {
		PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName(url);
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
	}

}
