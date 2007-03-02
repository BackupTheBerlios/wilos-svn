package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.phase.Phase;

public class PhaseTO extends Phase implements Serializable {

	public PhaseTO() {}
	
	public PhaseTO(Phase _phase) {
		if (_phase != null) {
			this.setDescription(_phase.getDescription());
			this.setName(_phase.getName());
			this.setGuid(_phase.getGuid());
			this.setPresentationName(_phase.getPresentationName());
			
			this.setMainDescription(_phase.getMainDescription());
	        this.setKeyConsiderations(_phase.getKeyConsiderations());
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _phase.getGuidances()) {
	        	if (g instanceof CheckList)
	        		guidances.add(new CheckListTO((CheckList)g));	
	        	else
	        		guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
	}

	
}
