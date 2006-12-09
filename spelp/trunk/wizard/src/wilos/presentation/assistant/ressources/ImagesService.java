package wilos.presentation.assistant.ressources;

import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class ImagesService {
	
	public static Icon getIcon (String key){
		ImageIcon img ; 
		img = new ImageIcon("src/wilos/presentation/assistant/ressources/images/" + Bundle.getText(key));
		return (img);
	}
}
