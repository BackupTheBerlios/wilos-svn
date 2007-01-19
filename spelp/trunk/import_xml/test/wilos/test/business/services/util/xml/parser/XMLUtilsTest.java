package wilos.test.business.services.util.xml.parser;




import java.io.File;

import javax.xml.xpath.XPathConstants;

import junit.framework.TestCase;


import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.XMLUtils;
import wilos.model.spem2.process.Process;

public class XMLUtilsTest extends TestCase{
	public static String path = "ressources" + File.separator + "scrum.xml";
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
}
