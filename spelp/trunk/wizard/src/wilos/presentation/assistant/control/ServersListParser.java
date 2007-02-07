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
 */
public class ServersListParser {
	
	private static final String fic = "src/wilos/presentation/assistant/ressources/servers.xml";
	private File XML_File;
	private ArrayList<WizardServer> serversList = null;
	private static final String xpath_server ="//Server";
	
	/**
	 * Constructor: make a new list server associated with the file f (create it if
	 * it doesen't exists).
	 * @param f path of of the file
	 */
	public ServersListParser(String f) {
		XML_File = new File (f);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (Exception e) {}
		}
	}
	
	/**
	 * Constructor: make a new list server associated with the default file (create it if
	 * it doesen't exists).
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
	 * Get the ordered server list.
	 * @return ordererd server list
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
	
	/**
	 * Save the given server list (and order it by alphabetic alias on the alias).
	 * @param wsl new list of servers
	 */
	public void saveServersList(ArrayList<WizardServer> wsl) {
		Element data = new Element("Servers");
		Element tmp;
		Element server;
		Document myDocument = new  Document(data);
		int id = 1;
		
		WizardServer ws;
		

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
	 * Put the given last server used at the top of the list, order this list, and
	 * save it on the file.
	 * @param no
	 */
	public void lastUsedServer (int no) {
		int i;
		for (i = 0 ; i < serversList.size() && serversList.get(i).getId() != no ; i++) { }
		
		WizardServer elem = serversList.get(i);
		serversList.remove(i);
		
		trierAlias();
		serversList.add(0, elem);
		
		// copy for the saving method
		ArrayList<WizardServer> list = new ArrayList<WizardServer>();
		for (WizardServer ws : serversList)
		{
			list.add(ws);
		}
		
		saveServersList(list);
	}
	
	// order, by alphabetic order on the alias, the server list
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
