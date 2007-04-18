/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>

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

import junit.framework.TestCase;

import wilos.business.services.util.xml.parser.XMLServices;
import wilos.model.spem2.process.Process;

public class XMLServicesTest extends TestCase{
	public static String pathEmpty = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "empty.zip";
	public static String pathMonTest = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.zip";
	public static String pathBizarre = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "bizarre.zip";
	public static String pathTest2Niveau = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "test2Niveau.zip";
	public static String pathTestCorrompu = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "corrompu.zip";
	public static String pathTest2XML = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "deuxXML.zip";
	public static String pathTestOpenUp = "test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "openup.zip";
	
	public void testGetProcess(){
		Process p = null ;
		
		p = XMLServices.getProcess(pathEmpty,"") ;
		assertNull("TEST FICHIER VIDE",p);
		
		p = XMLServices.getProcess(pathMonTest,"") ;
		assertNotNull("TEST FICHIER SIMPLE",p) ;
		System.out.println(p.getName());
		
		p = XMLServices.getProcess(pathBizarre,"") ;
		assertNull("TEST BIZARRE",p);
		
		p = XMLServices.getProcess(pathTest2Niveau,"") ;
		assertNotNull("TEST 2 NIVEAUX",p);
		System.out.println(p.getName());
		
		p = XMLServices.getProcess(pathTestCorrompu,"") ;
		assertNull("TEST CORROMPU",p);
	
		p = XMLServices.getProcess(pathTest2XML,"") ;
		assertNotNull("TEST 2 XML",p);
		System.out.println(p.getName());
		
		p = XMLServices.getProcess(pathMonTest, "");
		assertNotNull("TEST 2 XML",p);
		
		p = XMLServices.getProcess(pathTestOpenUp, "");
		assertNotNull("TEST OPEN UP",p);
	}
}
