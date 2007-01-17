package wilos.utils;

public class Constantes {
	
	public static int CREATED = 0;
	public static int READY = 1;
	public static int STARTED = 2;
	public static int SUSPENDED = 3;
	public static int FINISHED = 4;
	
	//FIXME Probleme pour mapper en bd !?
	public static enum State{
		CREATED,
		READY,
		STARTED,
		SUSPENDED,
		FINISHED
	};

}
