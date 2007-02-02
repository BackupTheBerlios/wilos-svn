package wilos.presentation.assistant.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPathConstants;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.presentation.assistant.model.WizardServer;

public class ServersListParser {
	
	private static final String fic = "src/wilos/presentation/assistant/ressources/servers.xml";
	private File XML_File;
	private ArrayList<WizardServer> serversList = null;
	private static final String xpath_server ="//Server";
	
	public ServersListParser(String f) {
		XML_File = new File (f);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (Exception e) {}
		}
	}
	
	public ServersListParser() {
		XML_File = new File (ServersListParser.fic);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (Exception e) {}
		}
	}

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
					
					for(int i = 0; i < nodeReturned.getLength(); i++) {
						aNode = nodeReturned.item(i);
						al = "";
						add = "";
						id = -1;
						
						NodeList attributesNode = aNode.getChildNodes();
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
						
						// Don't add the server if it's not valid
						if (!al.equals("") && !add.equals("") && id != -1) {
							WizardServer ws = new WizardServer (al, add,id);
							serversList.add(ws);
						}
					}	
				}
			}
			else serversList = new ArrayList<WizardServer>();
		}
		return serversList;
	}
	
	public void saveServersList(ArrayList<WizardServer> wsl) {
		Element data = new Element("Servers");
		Element tmp;
		Element server;
		Document myDocument = new  Document(data);
		int id = 1;
		
		WizardServer ws;
				
		Iterator it = wsl.iterator();
		while(it.hasNext())
		{
			ws = (WizardServer) it.next();
		
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
	
	public void lastUsedServer (int no) {
		int i;
		for (i = 0 ; i < serversList.size() && serversList.get(i).getId() != no ; i++) { }
		
		WizardServer elem = serversList.get(i);
		serversList.remove(i);
		
		trierAlias();
		serversList.add(0, elem);
		saveServersList(serversList);
	}
	
	private void trierAlias ()
	{
		for (int i = 1 ; i < serversList.size() ; i++)
		{
			WizardServer elem = serversList.get(i);
			serversList.remove(i);
			
			int j;
			for (j = 0 ; (j < i+1) && (elem.getAlias().compareTo(serversList.get(j).getAlias()) > 0); j++) { }
			
			serversList.add(j, elem);
		}
	}
}
