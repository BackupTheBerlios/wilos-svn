package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;

public class ActivityTO extends Activity implements Serializable {

	public ActivityTO() {}
	
	public ActivityTO(Activity _activity) {
		if (_activity != null) {
			this.setDescription(_activity.getDescription());
			this.setName(_activity.getName());
			this.setGuid(_activity.getGuid());
			this.setPresentationName(_activity.getPresentationName());
			
			this.setAlternatives(_activity.getAlternatives());
			this.setHowToStaff(_activity.getHowToStaff());
			this.setPurpose(_activity.getPurpose());
			
			this.setMainDescription(_activity.getMainDescription());
	        this.setKeyConsiderations(_activity.getKeyConsiderations());
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _activity.getGuidances()) {
	        	if (g instanceof CheckList)
	        		guidances.add(new CheckListTO((CheckList)g));	
	        	else
	        		guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
		

	}

}
