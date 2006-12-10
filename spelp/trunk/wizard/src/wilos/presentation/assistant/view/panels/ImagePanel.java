/*
 * ImagePanel.java
 *
 * Created on 25 novembre 2006, 17:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.presentation.assistant.view.panels;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jdesktop.swingx.JXImagePanel;
import wilos.presentation.assistant.ressources.Bundle;
       


/**
 *
 * @author toine
 */
public class ImagePanel extends JXImagePanel {
    
    private static String imagePath = "wilos/presentation/assistant/ressources/images/";
    
    /** Creates a new instance of ImagePanel */
    public ImagePanel(String pict) {
        
        init(pict);
    }
    
    public void init(String pict) {
       Image image;
       
       try {
            // récupération de l'image que l'on applique au panel
            image = ImageIO.read(ClassLoader.getSystemResource(imagePath+Bundle.getText(pict)));
            this.setImage(image);
       } catch (IOException ex) {
            ex.printStackTrace();
       }
    }    
}
