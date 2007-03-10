/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

package wilos.business.services.util.xml.parser;

import java.io.File;
import java.util.zip.ZipException;

import wilos.model.spem2.process.Process;

public class XMLServices {

	/**
	 * getProcess : return a Process Object and 
	 * if the param is a zip file, it is extracted automatically in 
	 * a repertory named by the guid of the Process 
	 * @param XMLPath : can be a XML File or a ZIP File
	 * @return a Process Object
	 */
	public static Process getProcess (String XMLPath, String pathToExtract){
		Process p = null ;
		File f = new File(XMLPath);
		if (XMLUtils.isExtension(XMLPath,"zip")){
			try {
				ZIPUtils z = new ZIPUtils(f);
				if (!z.isEmpty()) {
					p = XMLParser.getProcess(f);
					if (p != null){
						if (pathToExtract.length() !=0 && pathToExtract.charAt(pathToExtract.length()-1) != File.separatorChar){
							pathToExtract += File.separatorChar ;
						}
						z.extract(pathToExtract + p.getGuid() + File.separatorChar);
					}
				}
			} catch (ZipException e) {
				
			}
		}
		else {
			p = XMLParser.getProcess(f);
		}
		return p ;
	}
}
