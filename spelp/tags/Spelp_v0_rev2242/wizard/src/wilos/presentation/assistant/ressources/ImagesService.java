package wilos.presentation.assistant.ressources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class ImagesService {
	private final static String path = "wilos/presentation/assistant/ressources/images/"  ;
	
	public static ImageIcon getImageIcon (String key){
		ImageIcon img ; 
                
		img = new ImageIcon(ClassLoader.getSystemResource(path + Bundle.getText(key)));
		return (img);
	}
	
	public static Image getImage (String key) throws IOException{
		return(((ImageIcon) getImageIcon(key)).getImage());
	}
}