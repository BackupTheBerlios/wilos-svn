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

package wilos.test.business.services.util.xml.parser;




import java.io.File;

import javax.xml.xpath.XPathConstants;

import junit.framework.TestCase;

import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.XMLUtils;

public class XMLUtilsTest extends TestCase{
	public static String path = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator +"scrum.xml";
	public String expression = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:TaskDescriptor']";

	public void testSetDocument() {
		XMLUtils.setDocument(new File(path));
		assertNotNull(XMLUtils.getDocument());
	}


	public void testEvaluate() {
		XMLUtils.setDocument(new File(path));
		NodeList n = (NodeList)XMLUtils.evaluate(expression, XPathConstants.NODESET);
		assertTrue(n.getLength() != 0);
	}
	
	public void testIsExtension(){
		String chemin = "caca.zip";
		String chemin2 = "caca.ZIP";
		String chemin3 = "caca.ZiP";
		assertTrue(XMLUtils.isExtension(chemin, "zip"));
		assertTrue(XMLUtils.isExtension(chemin2, "zip"));
		assertTrue(XMLUtils.isExtension(chemin3, "zip"));
		assertTrue(XMLUtils.isExtension(chemin, "Zip"));
		assertTrue(XMLUtils.isExtension(chemin2, "ZIP"));
		assertTrue(XMLUtils.isExtension(chemin3, "zIP"));
		
	}
}
