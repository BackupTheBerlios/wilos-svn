package wilos.business.services.util.xml.parser;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import wilos.model.spem2.process.Process;

public class XMLUtils {
	
	
	private static File document ; 
		
	public static File getDocument(){
		return document ;
	}
	
	public static void setDocument (File _file){
		document = _file ;
	}
	
	/**
	 * Evalute : this methods evaluate an xpath expression
	 * @param document
	 * @param expression
	 * @param retour
	 * @return
	 */
	public static Object evaluateDOM(String expression, QName retour){
		Object resultat = null;
		try{
			//creation du XPath 
			XPathFactory fabrique = XPathFactory.newInstance();
			XPath xpath = fabrique.newXPath();
			
			//evaluation de l'expression XPath
			XPathExpression exp = xpath.compile(expression);
			resultat = exp.evaluate(document,retour);
			
		}catch(XPathExpressionException xpee){
			xpee.printStackTrace();
		}
		return resultat;
	}
	
	public static Object evaluate(String expression, QName retour){
		Object resultat = null;
		InputSource source = null;
		try{
			//creation de la source
			if (isExtension(document.getName(), "xml")) {
				source = new InputSource(new FileInputStream(document));
			}
			else if (isExtension(document.getName(), "zip")) {
				source = new InputSource(new ZIPUtils(document).getXMLStream());
			}
			if (source != null) {
				//creation du XPath 
				XPathFactory fabrique = XPathFactory.newInstance();
				XPath xpath = fabrique.newXPath();
				
				//evaluation de l'expression XPath
				XPathExpression exp = xpath.compile(expression);
				resultat = exp.evaluate(source,retour);
			}
		}catch(XPathExpressionException xpee){
			xpee.printStackTrace();
		}catch(IOException  ioe){
			ioe.printStackTrace();	
		}
		return resultat;
	}
	
	public static boolean isExtension(String filePath, String extension){
		String s1 = new String(filePath).toLowerCase();
		String s2 = new String(extension).toLowerCase();
		return s1.substring(s1.lastIndexOf(".")+1).equals(s2);
	}
}
