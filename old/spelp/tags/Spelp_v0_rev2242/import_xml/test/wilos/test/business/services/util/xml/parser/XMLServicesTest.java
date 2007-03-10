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
