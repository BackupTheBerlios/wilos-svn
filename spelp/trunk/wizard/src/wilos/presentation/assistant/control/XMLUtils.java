package wilos.presentation.assistant.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

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
		try{
			//creation de la source
			InputSource source = new InputSource(new FileInputStream(document));
			
			//creation du XPath 
			XPathFactory fabrique = XPathFactory.newInstance();
			XPath xpath = fabrique.newXPath();
			
			//evaluation de l'expression XPath
			XPathExpression exp = xpath.compile(expression);
			
			resultat = exp.evaluate(source, retour);
			
			
		}catch(XPathExpressionException xpee){
			xpee.printStackTrace();
		}catch(IOException  ioe){
			ioe.printStackTrace();	
		}
		return resultat;
	}

	public static Node addElement(String string) {
		
		return null;
	}
}
