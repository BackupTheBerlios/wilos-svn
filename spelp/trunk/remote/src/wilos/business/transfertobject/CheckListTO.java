package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.spem2.checklist.CheckList;



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
        
        // TODO: A voir avec la liste de sections
    }
}
