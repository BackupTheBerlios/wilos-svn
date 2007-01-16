package wilos.test.business.services.util.xml.parser;

import java.io.File;

import wilos.business.services.util.xml.parser.ZIPUtils;

import junit.framework.TestCase;

public class ZIPUtilsTest extends TestCase
{
	public static File pathEmpty = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "empty.zip");
	public static File pathMonTest = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.zip");
	
	public void testIsEmpty (){
		ZIPUtils monZip = new ZIPUtils (pathEmpty);
		assertTrue("TEST AVEC ARCHIVE VIDE", monZip.isEmpty());
	}
}
