package wilos.presentation.web.template;

public class AboutBean {
	
	private boolean visibleAboutPopup = false;
	
	public String okAction() {
		this.visibleAboutPopup = false;
		return "";
	}
	
	public String displayAboutPopup(){
		this.visibleAboutPopup = true;
		return "";
	}

	/**
	 * @return the visibleAboutPopup
	 */
	public boolean isVisibleAboutPopup() {
		return visibleAboutPopup;
	}

	/**
	 * @param visibleAboutPopup the visibleAboutPopup to set
	 */
	public void setVisibleAboutPopup(boolean visibleAboutPopup) {
		this.visibleAboutPopup = visibleAboutPopup;
	}
	
	

}
