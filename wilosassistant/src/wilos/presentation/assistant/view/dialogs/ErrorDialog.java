/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

/*
 * ErrorDialog.java
 *
 * Created on 9 decembre 2006, 22:52
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
        
        JOptionPane.showMessageDialog(this,mess,Bundle.getText("ErrorDialog.title"),JOptionPane.ERROR_MESSAGE);
        this.setVisible(true);           
    }
}