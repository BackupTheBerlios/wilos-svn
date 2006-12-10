/*
 * ErrorDialog.java
 *
 * Created on 9 décembre 2006, 22:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.presentation.assistant.view.dialogs;

import com.sun.imageio.plugins.jpeg.JPEG;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.J2SJAXBModel;
import com.sun.tools.xjc.api.Reference;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.namespace.QName;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.ImagePanel;

/**
 *
 * @author toine
 */
public class ErrorDialog extends JOptionPane {

    /** Creates a new instance of ErrorDialog */
    public ErrorDialog(String msg) {
        init(msg);
    }
    
    /* initialisation of the Error JDialog */
    private void init(String mess) {
        
        this.showMessageDialog(this,mess,Bundle.getText("ErrorDialog.title"),JOptionPane.ERROR_MESSAGE);
        this.setVisible(true);           
    }
}