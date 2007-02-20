package wilos.model.spem2.checklist;

import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.guide.Guidance;

public class CheckList extends Guidance{
	
	Set<String> sections;
	
	public CheckList() {		
		this.sections = new HashSet<String>();
	}

	/**
	 * @return the sections
	 */
	public Set<String> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Set<String> _sections) {
		this.sections = _sections;
	}
}
