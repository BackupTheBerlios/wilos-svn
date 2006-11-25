/*
 * ImagePanel.java
 *
 * Created on 25 novembre 2006, 17:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package view.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXImagePanel;
import java.lang.Exception;
       


/**
 *
 * @author toine
 */
public class ImagePanel extends JXImagePanel {
    
    private static String imagePath = "ressources"+java.io.File.separator+"logo.png";
    //private static String imagePath = "ressources/logo.png";
    
    
    /** Creates a new instance of ImagePanel */
    public ImagePanel() {
        
        init();
    }
    
    public void init() {
        Image image;
       /* try {
            image = ImageIO.read(getClass().getResource(imagePath));
            this.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
       image = Toolkit.getDefaultToolkit().createImage(imagePath);
       this.setImage(image);
    }    
}
