package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _phase.getGuidances()) {
	        	guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
	}

	
}
