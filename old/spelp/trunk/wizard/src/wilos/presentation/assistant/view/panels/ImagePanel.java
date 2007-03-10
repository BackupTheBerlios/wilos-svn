/*
 * ImagePanel.java
 *
 * Created on 25 novembre 2006, 17:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.presentation.assistant.view.panels;

import java.io.IOException;

import org.jdesktop.swingx.JXImagePanel;

import wilos.presentation.assistant.control.ExceptionManager;
import wilos.presentation.assistant.ressources.ImagesService;
       


/**
 *
 * @author toine
 */
public class ImagePanel extends JXImagePanel {
        
    /** Creates a new instance of ImagePanel */
    public ImagePanel(String pict) {
        
        init(pict);
    }
    
    public void init(String pict) {
       try {
            // récupération de l'image que l'on applique au panel
            this.setImage(ImagesService.getImage(pict));
       } catch (IOException ex) {
			ex.printStackTrace();
			new ExceptionManager(ex);
       }
    }    
}
