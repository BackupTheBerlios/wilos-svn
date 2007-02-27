package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;

public class IterationTO extends Iteration implements Serializable {

	public IterationTO() {}

	public IterationTO(Iteration _iteration) {
		if (_iteration != null) {
			this.setDescription(_iteration.getDescription());
			this.setName(_iteration.getName());
			this.setGuid(_iteration.getGuid());
			this.setPresentationName(_iteration.getPresentationName());
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _iteration.getGuidances()) {
	        	guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
	}

}
