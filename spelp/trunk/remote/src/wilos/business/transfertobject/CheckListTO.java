package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.section.Section;



public class CheckListTO extends CheckList implements Serializable {
	
	
	/** Creates a new instance of CheckList */
    public CheckListTO() {

    }    
  
    public CheckListTO(CheckList myCheckList) {
        this.setName(myCheckList.getName());
        this.setGuid(myCheckList.getGuid());
        this.setDescription(myCheckList.getDescription());
        this.setType(myCheckList.getType());
        this.setPresentationName(myCheckList.getPresentationName());
        this.setAttachment(myCheckList.getAttachment());
        
        // set the sections 
		Set<Section> sections = new HashSet<Section>();
        for (Section s : myCheckList.getSections()) {
        	sections.add(new SectionTO(s));	        	
        }
        this.setSections(sections);
    }
}
