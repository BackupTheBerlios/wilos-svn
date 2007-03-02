package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.process.Process;

public class ProcessTO extends Process implements Serializable {

	public ProcessTO() {}

	public ProcessTO(Process _process) {
		if (_process != null) {
			this.setDescription(_process.getDescription());
			this.setName(_process.getName());
			this.setGuid(_process.getGuid());
			this.setPresentationName(_process.getPresentationName());
			
			this.setMainDescription(_process.getMainDescription());
	        this.setKeyConsiderations(_process.getKeyConsiderations());
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _process.getGuidances()) {
	        	guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
	}
}
