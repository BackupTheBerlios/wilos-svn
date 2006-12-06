/*
 * ImagePanel.java
 *
 * Created on 25 novembre 2006, 17:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.presentation.assistant.view.main;

import com.sun.xml.bind.v2.model.annotation.ClassLocatable;
import com.thoughtworks.xstream.core.util.ClassLoaderReference;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXImagePanel;
import java.lang.Exception;
import org.omg.CORBA.portable.ApplicationException;
       


/**
 *
 * @author toine
 */
public class ImagePanel extends JXImagePanel {
    
    private static String imagePath = "wilos/presentation/assistant/ressources/logo.png";
    
    /** Creates a new instance of ImagePanel */
    public ImagePanel() {
        
        init();
    }
    
    public void init() {
       Image image;
       
       try {
            // récupération de l'image que l'on applique au panel
            image = ImageIO.read(ClassLoader.getSystemResource(imagePath));
            this.setImage(image);
       } catch (IOException ex) {
            ex.printStackTrace();
       }
    }    
}
