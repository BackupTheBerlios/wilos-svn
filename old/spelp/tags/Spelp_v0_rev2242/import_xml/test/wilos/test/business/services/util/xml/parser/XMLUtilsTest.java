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
