package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.spem2.section.Section;

public class SectionTO extends Section implements Serializable {
	
	public SectionTO() {
		
	}
	
	public SectionTO(Section mySection) {
		this.setName(mySection.getName());
		this.setDescription(mySection.getDescription());
		this.setGuid(mySection.getGuid());
		
		// TODO : avoir avec CheckList
	}
	
	
}
