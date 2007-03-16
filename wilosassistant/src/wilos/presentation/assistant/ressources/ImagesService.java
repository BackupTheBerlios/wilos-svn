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

package wilos.presentation.assistant.ressources;

import java.awt.Image;
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
