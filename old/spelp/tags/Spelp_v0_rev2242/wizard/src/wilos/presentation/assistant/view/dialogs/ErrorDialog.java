/*
 * ErrorDialog.java
 *
 * Created on 9 décembre 2006, 22:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.presentation.assistant.view.dialogs;

import javax.swing.JOptionPane;

import wilos.presentation.assistant.ressources.Bundle;

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