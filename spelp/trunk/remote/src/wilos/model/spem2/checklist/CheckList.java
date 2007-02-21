package wilos.model.spem2.checklist;

import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.section.Section;

public class CheckList extends Guidance{
	
	/* Collection of Sections */
	Set<Section> sections;
	
	public CheckList() {		
		this.sections = new HashSet<Section>();
	}

	/**
	 * @return the sections
	 */
	public Set<Section> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Set<Section> _sections) {
		this.sections = _sections;
	}
}
