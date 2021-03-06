/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.xpath.XPathConstants;

import junit.framework.TestCase;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.XMLParser;
import wilos.business.services.util.xml.parser.XMLUtils;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class XMLParserTestAnalyst extends TestCase {
	public static File pathOPenUP =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "sortieEPF.xml");
	
	public void testOpenUPInitiateProjectContainsExpectedRoleDescriptors() {
		Iterator<BreakdownElement> itTopLevelAct;
		Activity secondLevelActivity;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Stakeholder");
		expectedResults.add("Project Manager");
		expectedResults.add("Analyst");
		expectedResults.add("Tester");
		expectedResults.add("Architect");
		
		Process openUPProcess = XMLParser.getProcess(pathOPenUP);
		
		System.out.println("==> Procedure de test des task de analyst by SPELP\n");
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = openUPProcess.getBreakdownElements().iterator();
			
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itTopLevelAct.next();
				
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
				while (BdeIterator.hasNext()) {
					tmpBde = BdeIterator.next();
					if (tmpBde instanceof Activity) {
						Activity a = (Activity)tmpBde ;
						Iterator<BreakdownElement> Actiterator = a.getBreakdownElements().iterator();
						while (Actiterator.hasNext()){
							BreakdownElement bdee = Actiterator.next();
							if (bdee instanceof RoleDescriptor && bdee.getPresentationName().equals("Analyst")){
								RoleDescriptor roro = (RoleDescriptor) bdee;
								System.out.println("Affichage du Role Descriptor");
								System.out.println("\tgetName : " + roro.getName());
								System.out.println("\tgetPresentationName : " + roro.getPresentationName());
								System.out.println("\tgetGuid : " + roro.getGuid());
								System.out.println("\t==> Affichage de ses tasks !!!");
								Iterator<TaskDescriptor> iter = roro.getPrimaryTasks().iterator();
								while (iter.hasNext()){
									TaskDescriptor td = iter.next();
									System.out.println("\t\t" + td.getPresentationName() + " " + td.getGuid());
								}
								System.out.println();
								
							}
						}
					}
				}
				
		}
		
		
	}
	
	/**
	 * It seems that OpenUP may contains multiple role descriptor that has the
	 * same id
	 * We are gonna check this
	 *
	 */
	public void determinesIfOpenUpContainsMultipleRoleDescWithSameId() {
		String xpath_roleDescriptor = "//BreakdownElement[@*[namespace-uri() and local-name()='type']='uma:RoleDescriptor']";
		String AttributeId = "id" ;
		
		String guid;
		
		Vector<String> allRoleDescGuid = new Vector<String>();
		Vector<String> duplicatedDescGuid = new Vector<String>();
		
		XMLUtils.setDocument(pathOPenUP);
		
		NodeList roleDescriptorsNodeList = (NodeList)XMLUtils.evaluate(xpath_roleDescriptor, XPathConstants.NODESET);
		
		Node aNode;
		for(int i=0;i<roleDescriptorsNodeList.getLength();i++){
			aNode = roleDescriptorsNodeList.item(i);
			
			guid = aNode.getAttributes().getNamedItem(AttributeId).getNodeValue();
			
			if (allRoleDescGuid.contains(guid)) {
				duplicatedDescGuid.add(guid);
			}
			
			allRoleDescGuid.add(guid);	
		}
		
		System.out.println("Resultat :");
		if (duplicatedDescGuid.isEmpty()) {
			System.out.println("Aucun RoleDesc dupliqué");
		}
		else {
			for (String s : duplicatedDescGuid) {
				System.out.println(s);
			}
		}
		
		assertTrue(true);
	}
	
	/**
	 * No we go further : Are there two elements with the same id ??
	 * We are gonna check this
	 *
	 */
	public void determinesIfOpenUpContainsMultipleElementsWithSameId() {
		String xpath_roleDescriptor = "//*";
		String AttributeId = "id" ;
		
		String guid;
		
		Vector<String> allRoleDescGuid = new Vector<String>();
		Vector<String> duplicatedDescGuid = new Vector<String>();
		
		XMLUtils.setDocument(pathOPenUP);
		
		NodeList roleDescriptorsNodeList = (NodeList)XMLUtils.evaluate(xpath_roleDescriptor, XPathConstants.NODESET);
		
		Node aNode;
		for(int i=0;i<roleDescriptorsNodeList.getLength();i++){
			aNode = roleDescriptorsNodeList.item(i);
			
			//if (aNode.getAttributes().)
			guid = aNode.getAttributes().getNamedItem(AttributeId).getNodeValue();
			
			if (allRoleDescGuid.contains(guid)) {
				duplicatedDescGuid.add(guid);
			}
			
			allRoleDescGuid.add(guid);	
		}
		
		System.out.println("Resultat :");
		if (duplicatedDescGuid.isEmpty()) {
			System.out.println("Aucun RoleDesc dupliqué");
		}
		else {
			for (String s : duplicatedDescGuid) {
				System.out.println(s);
			}
		}
		
		assertTrue(true);
	}
}

	