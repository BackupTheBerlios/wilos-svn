package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.spem2.activity.Activity;

public class ActivityTO extends Activity implements Serializable {

	public ActivityTO() {}
	
	public ActivityTO(Activity _activity) {
		this.setGuidances(_activity.getGuidances());
		this.setDescription(_activity.getDescription());
		this.setName(_activity.getName());
		this.setGuid(_activity.getGuid());
	}

}
