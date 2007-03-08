/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.presentation.web.template;

/**
 * TODO Method description
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
     * TODO Method : methode inutilise?
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
     * TODO Method : methode inutilise?
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
	 * This method redirects to the url which is passed in parameter
	 * @param url
	 */
	public void changePage(String url) {
		PageContentBean pcb = new PageContentBean();
    	pcb.setTemplateName(url);
    	pcb.setNavigationSelection(this);
    	this.selectedPanel = pcb;
	}

}
