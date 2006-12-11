package wilos.presentation.web.template;

/**
 * TODO
 */
public class MenuBean {

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
    
    
    /**
     * TODO Method description
     *
     * @return
     */
    public String welcome(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("welcome");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }
    
    
    /**
     * TODO Method description
     *
     * @return
     */
    public String subscribe(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("subscribe");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }
    
    
    /**
     * TODO Method description
     *
     * @return
     */
    public String wilos(){
    	PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName("wilos");
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
    	return "";
    }

	
	/**
	 * TODO Method description
	 *
	 * @param url
	 */
	public void changePage(String url) {
		PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName(url);
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
	}

}
