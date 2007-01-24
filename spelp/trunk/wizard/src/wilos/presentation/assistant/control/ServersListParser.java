package wilos.presentation.assistant.control;

import java.util.List;

import wilos.presentation.assistant.model.WizardServer;

public class ServersListParser {
	
	private String XMLfile = "wilos/presentation/assistant/ressources/servers.xml";
	
	public List<WizardServer> getServersList() {
		
		return null;
	}
	
	public void saveServersList(List<WizardServer> wsl) {
		
	}
}


/**











package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.XMLUtils;

import com.thoughtworks.xstream.XStream;

import control.CContact;

public class Contact {
	private static final String xpath_contact = "//Contact";
	private static final String xpath_data = "//Data";
	
	private String name;
	private String number;
	private int id;
	private static File XMLFile;
	private static List<Contact> allContacts = null;
	
	public static void setXMLFile(File anXMLFile) {
		XMLFile = anXMLFile;
		XMLUtils.setDocument(XMLFile);
	}
	
	public Contact(String name, String number, int id) {
		this.name = name;
		this.number = number;
		this.id = id;
		
		allContacts.add(this);
	}
	
	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
		this.id = 0;
		
		allContacts.add(this);
	}

	public static List<Contact> getAllContacts() {
		if (allContacts == null) {
			if (XMLFile.exists() && XMLFile.length() > 2) {
				allContacts = new ArrayList<Contact>();
				
				NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_contact, XPathConstants.NODESET);
				
				if (nodeReturned != null) {
					Node aNode;
					String name = "";
					String number = "";
					int id = 0;
					for(int i = 0; i < nodeReturned.getLength(); i++) {
						aNode = nodeReturned.item(i);
						
						NodeList attributesNode = aNode.getChildNodes();
						for (int j = 0 ; j < attributesNode.getLength() ; j++) {
							//System.out.println(attributesNode.item(j).getName);
							if (attributesNode.item(j).getNodeName().equals("Name")) {
								name = attributesNode.item(j).getTextContent();
							}
							if (attributesNode.item(j).getNodeName().equals("Number")) {
								number = attributesNode.item(j).getTextContent();
							}
							if (attributesNode.item(j).getNodeName().equals("Id")) {
								Integer tmpId = new Integer(attributesNode.item(j).getTextContent());
								id = tmpId.intValue();
							}
						}
						
						Contact contact = new Contact(name, number, id);
						
						allContacts.add(contact);			
					}	
				}
			}
			else allContacts = new ArrayList<Contact>();
		}
		return allContacts;
	}

	public String getName() {
		return this.name;
	}

	public String getNumber() {
		return this.number;
	}

	public int getId() {
		return this.id;
	}

	public static void save() {
		if (this.id == 0) {			
			this.add();
		}
		else {
			this.update();
		}
		Element data = new Element("Data");
		Element tmp;
		Element contact;
		Document myDocument = new  Document(data);
		XStream xstream = new XStream();
		xstream.alias("Contact", Contact.class);
		String toadd;
		for(int i = 0; i < allContacts.size(); i++) {
//			toadd = xstream.toXML(allContacts.get(i));
//			tmp =  new Element(toadd);
//			data.addContent(tmp);
			contact = new Element("Contact");
			tmp = new Element("Name");
			tmp.addContent(allContacts.get(i).getName());
			contact.addContent(tmp);
			
			tmp = new Element("Number");
			tmp.addContent(allContacts.get(i).getNumber());
			contact.addContent(tmp);
			
			tmp = new Element("Id");
			tmp.addContent(String.valueOf(allContacts.get(i).getId()));
			contact.addContent(tmp);
			
			data.addContent(contact);
		}

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			
			FileWriter writer = new FileWriter(XMLFile);
			outputter.output(myDocument, new FileOutputStream(XMLFile));
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void add() {
		// Creation d'un Noeud Contact, et des Noeuds Name, Number et Id
		NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_data, XPathConstants.NODESET);
		System.out.println("ici");
		if (nodeReturned != null) {
//			Node aNode;
//			Node newContact;
//			for(int i = 0; i < nodeReturned.getLength(); i++) {
//				aNode = nodeReturned.item(i);
//				newContact = XMLUtils.addElement("Contact");
////				Element 
////				aNode.appendChild(arg0);		
//			}
			
			String toadd;
			XStream xstream = new XStream();
			
			xstream.alias("Contact", Contact.class);
			
			toadd = xstream.toXML(this);
			
			//nodeReturned.item(0).
			
			//System.out.println(toadd);
		}
	}
	public void delete() {
		// Suppression du Noeud Contact et de ses sous noeuds, correspondant au Contact supprime
	}
	
	public static Contact getContactById(int id) {
		Contact contact = null;
		
		if (XMLFile.exists() && XMLFile.length() > 2) {			
			NodeList nodeReturned = (NodeList)XMLUtils.evaluate(xpath_contact, XPathConstants.NODESET);
			
			if (nodeReturned != null) {
				Node aNode;
				String name = "";
				String number = "";
				int nodeId = 0;
				for(int i = 0; i < nodeReturned.getLength(); i++) {
					aNode = nodeReturned.item(i);
					
					NodeList attributesNode = aNode.getChildNodes();

					for (int j = 0 ; j < attributesNode.getLength() ; j++) {
						if (attributesNode.item(j).getNodeName().equals("Name")) {
							name = attributesNode.item(j).getTextContent();
						}
						if (attributesNode.item(j).getNodeName().equals("Number")) {
							number = attributesNode.item(j).getTextContent();
						}
						if (attributesNode.item(j).getNodeName().equals("Id")) {
							Integer tmpId = new Integer(attributesNode.item(j).getTextContent());
							nodeId = tmpId.intValue();
						}
					}
					
					if (nodeId == id) {
						contact = new Contact(name, number, id);
					}
					
					
				}	
			}
		}
		return contact;
	}
}














*/