package wilos.test.business.services.util.xml.parser;

import java.io.File;

import junit.framework.TestCase;
import wilos.business.services.util.xml.parser.ZIPUtils;

public class ZIPUtilsTest extends TestCase
{
	public static File pathEmpty = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "empty.zip");
	public static File pathMonTest = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.zip");
	public static File pathBizarre = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "bizarre.zip");
	public static File pathTest2Niveau = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "test2Niveau.zip");
	
	public void testIsEmpty (){
		ZIPUtils monZip = new ZIPUtils (pathEmpty);
		assertTrue("TEST AVEC ARCHIVE VIDE", monZip.isEmpty());
		
		ZIPUtils monZip2 = new ZIPUtils (pathMonTest);
		assertFalse("TEST AVEC ARCHIVE NON VIDE",monZip2.isEmpty());
		
		ZIPUtils monZip3 = new ZIPUtils (pathBizarre);
		assertFalse("TEST AVEC ARCHIVE NON VIDE",monZip2.isEmpty());
	}
	
	public void testGetXMLFile () {
		ZIPUtils monZip = new ZIPUtils(pathEmpty);
		assertNull("TEST getXML FICHIER VIDE",monZip.getXMLFile());
		
		ZIPUtils monZip2 = new ZIPUtils(pathMonTest);
		File file = monZip2.getXMLFile() ;
		assertNotNull("TEST getXML FICHIER VIDE",file);
		System.out.println(file.getName());
		
	}
	
	
}
