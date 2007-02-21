package wilos.model.spem2.section;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.element.Element;

public class Section extends Element {
	
	/* the attached CheckList */
	private CheckList checklist;

	public Section() {
		super();
	}

	/**
	 * @return the checklist
	 */
	public CheckList getChecklist() {
		return checklist;
	}

	/**
	 * @param checklist the checklist to set
	 */
	public void setChecklist(CheckList checklist) {
		this.checklist = checklist;
	}
	
}
