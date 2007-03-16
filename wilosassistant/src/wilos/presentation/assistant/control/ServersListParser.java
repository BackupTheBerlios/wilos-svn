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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.xpath.XPathConstants;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.presentation.assistant.model.WizardServer;

/**
 * This class allow the user to save a server list in a XML file, to
 * get this list, and to re-order this list.
 * @author Guillaume
 * @author Ramy
 */
public class ServersListParser {
	
	private static final String fic = "servers.xml";	//default file where the servers are stored
	private File XML_File;
	private ArrayList<WizardServer> serversList = null;
	private static final String xpath_server ="//Server";
	
	/**
	 * Constructor: makes a new list server associated with the file f (create it if
	 * it doesen't exists).
	 * @param f path of of the file
	 */
	public ServersListParser(String f) {
		XML_File = new File (f);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				new ExceptionManager(e);
			}
		}
	}
	
	/**
	 * Constructor: make a new list server associated with the default file (create it if
	 * it doesen't exists). Without parameters it creates it with the default location.
	 */
	public ServersListParser() {
		XML_File = new File (ServersListParser.fic);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (Exception e) {}
		}
	}

	/**
	 * Get the servers list.
	 * @return servers list
	 */
	public ArrayList<WizardServer> getServersList() {
		if (serversList == null) {
			
			if (XML_File.exists() && XML_File.length() > 2) {
				serversList = new ArrayList<WizardServer>();
				
				NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_server, XPathConstants.NODESET);
				
				if (nodeReturned != null) {
					Node aNode;
					
					String al;
					String add;
					int id;
					
					// for each server analyzes it s structure  
					for(int i = 0; i < nodeReturned.getLength(); i++) {
						aNode = nodeReturned.item(i);
						
						// default values
						al = "";
						add = "";
						id = -1;
						
						NodeList attributesNode = aNode.getChildNodes();
						
						// for each block of the server if it matches alias, address or id
						// it affects the good value
						for (int j = 0 ; j < attributesNode.getLength() ; j++) {
							if (attributesNode.item(j).getNodeName().equals("Alias")) {
								al = attributesNode.item(j).getTextContent();
							}
							if (attributesNode.item(j).getNodeName().equals("Address")) {
								add = attributesNode.item(j).getTextContent();
							}
							if (attributesNode.item(j).getNodeName().equals("Id")) {
								id = Integer.parseInt(attributesNode.item(j).getTextContent());
							}
						}
						
						// Don't add the server if one of the blocks is not valid
						if (!al.equals("") && !add.equals("") && id != -1) {
							WizardServer ws = new WizardServer (al, add,id);
							serversList.add(ws);
						}
					}	
				}
			}
			else serversList = new ArrayList<WizardServer>();
		}
		
		// copies serversList
		ArrayList<WizardServer> list = new ArrayList<WizardServer>();
		for (WizardServer ws : serversList)
		{
			list.add(new WizardServer(ws.getAlias(),ws.getAddress(),ws.getId()));
		}
		
		// returns the last made copy
		return list;
	}
	
	/**
	 * Saves the given servers list. And creates or replaces the xml file
	 * @param wsl list of servers
	 */
	public void saveServersList(ArrayList<WizardServer> wsl) {
		Element data = new Element("Servers");
		Element tmp;
		Element server;
		Document myDocument = new  Document(data);
		WizardServer ws;
		int id = 1;
		
		if (serversList == null)
		{
			serversList = new ArrayList<WizardServer>();
		}
		else
		{
			serversList.clear();
		}
		
		Iterator it = wsl.iterator();
		while(it.hasNext())
		{
			ws = (WizardServer) it.next();
			
			// for each WizardServer creates an element for the xml file
			serversList.add(new WizardServer(ws.getAlias(),ws.getAddress(),id));
			
			server = new Element("Server");
			
			tmp = new Element("Alias");
			tmp.addContent(ws.getAlias());
			server.addContent(tmp);
			
			tmp = new Element("Address");
			tmp.addContent(ws.getAddress());
			server.addContent(tmp);
			
			tmp = new Element("Id");
			tmp.addContent(""+id);
			server.addContent(tmp);
			id++;
			
			data.addContent(server);
		}
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			
			FileWriter writer = new FileWriter(XML_File);
			outputter.output(myDocument, new FileOutputStream(XML_File));
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Put the given last server used at the top of the list, orders the rest of the list on the alias,
	 * and saves it on the file.
	 * @param no (id of the given server)
	 */
	public void lastUsedServer (int no) {
		int i;
		
		// gets out of the for when there is no more server or when the good id is found
		for (i = 0 ; i < serversList.size() && serversList.get(i).getId() != no ; i++) { }
		
		// saves and removes the element from the arraylist
		WizardServer elem = serversList.get(i);
		serversList.remove(i);
		
		// orders the rest of the list
		trierAlias();
		
		// puts back the saved element 
		serversList.add(0, elem);
		
		// copy for the saving method
		ArrayList<WizardServer> list = new ArrayList<WizardServer>();
		for (WizardServer ws : serversList)
		{
			list.add(ws);
		}
		
		// saves copied list and creates an xml file from it
		saveServersList(list);
	}
	
	// orders, by alphabetic order on the alias, the server list
	private void trierAlias ()
	{
		int nb = serversList.size();
		for (int i = 1 ; i < nb ; i++)
		{
			WizardServer elem = serversList.get(i);
			serversList.remove(i);
			
			int j;
			for (j = 0 ; (j < i) && (elem.getAlias().compareTo(serversList.get(j).getAlias()) > 0); j++) { }
			
			serversList.add(j, elem);
		}
	}
}
