package wilos.model.spem2.section;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.element.Element;

public class Section extends Element {
	
	/* the attached CheckList */
	private CheckList checklist;

	public Section() {
		super();
	}
	
	protected void copy(final Section _section) {
		super.copy(_section) ;
		this.setChecklist(_section.getChecklist());
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Section == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		Section checklist = (Section) obj ;
		return new EqualsBuilder().appendSuper(super.equals(checklist)).append(this.getChecklist(),checklist.getChecklist()).isEquals() ;
	}
	
	
	 public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
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
