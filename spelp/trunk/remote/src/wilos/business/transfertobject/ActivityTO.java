package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.guide.Guidance;

public class ActivityTO extends Activity implements Serializable {

	public ActivityTO() {}
	
	public ActivityTO(Activity _activity) {
		this.setDescription(_activity.getDescription());
		this.setName(_activity.getName());
		this.setGuid(_activity.getGuid());
		
		Set<Guidance> guidances = new HashSet<Guidance>();
        for (Guidance g : _activity.getGuidances()) {
        	 guidances.add(new GuidanceTO(g));
        }
        this.addAllGuidances(guidances);
	}

}
