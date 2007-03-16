/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

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
    		new ExceptionManager(xpee);
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
