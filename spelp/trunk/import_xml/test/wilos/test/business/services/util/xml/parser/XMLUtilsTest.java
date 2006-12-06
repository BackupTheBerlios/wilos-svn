package wilos.test.business.services.util.xml.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.xml.xpath.XPathConstants;

import org.junit.Test;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.XMLUtils;

public class XMLUtilsTest {
	public static String path = "ressources" + File.separator + "scrum.xml";
	public String expression = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:TaskDescriptor']";
	@Test
	public void testSetDocument() {
		XMLUtils.setDocument(new File(path));
		assertNotNull(XMLUtils.getDocument());
	}

	@Test
	public void testEvaluate() {
		XMLUtils.setDocument(new File(path));
		NodeList n = (NodeList)XMLUtils.evaluate(expression, XPathConstants.NODESET);
		assertTrue(n.getLength() != 0);
	}

}
