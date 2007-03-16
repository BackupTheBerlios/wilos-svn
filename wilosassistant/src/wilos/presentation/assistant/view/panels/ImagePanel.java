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
            // r�cup�ration de l'image que l'on applique au panel
            this.setImage(ImagesService.getImage(pict));
       } catch (IOException ex) {
			ex.printStackTrace();
			new ExceptionManager(ex);
       }
    }    
}
