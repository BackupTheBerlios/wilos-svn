package wilos.utils;

import java.text.SimpleDateFormat;

public class Constantes {

	public static final String DATE_PATTERN = "dd/MM/yyyy hh:mm" ;
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN) ;
	
	public static class State {

		public static final String CREATED = "Created";

		public static final String READY = "Ready";

		public static final String STARTED = "Started";

		public static final String SUSPENDED = "Suspended";

		public static final String FINISHED = "Finished";
	}
}
