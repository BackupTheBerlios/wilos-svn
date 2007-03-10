package wilos.presentation.assistant.view.panels;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;



public class HourTextField extends JFormattedTextField {
	public final static String pattern = "[\\d]{2}:[\\d]{2}:[\\d]{2}" ;	
	
	public HourTextField (AbstractFormatter format){
			super(format);
	}

	
		
}
	
	