package wilos.business.services.util.xml.fillers;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.EncodingProcessor;
import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.section.Section;

public class FillerCheckList extends FillerGuidance {
	
	private static String Name = "name" ;
	private static String Id = "id";
	
	
	public FillerCheckList(CheckList _checklist, Node aNode) {
		super(_checklist, aNode);
		fill();
	}

	private void fill() {
		Set<Section> sections = new HashSet<Section>();		
		NodeList l = myNode.getChildNodes();
		
		//((Guidance) myElement).setType(type);
		String aName = "";
		String aDescriptionSection = "";
		String mainDescription = "";
		String aId = "";
		
		// for each node by the list
		for (int i=0; i<l.getLength(); i++) {
			if (l.item(i).getNodeName().equals(XMLParser.presentation)) {
				NodeList listItems = l.item(i).getChildNodes();
				for (int j = 0; j < listItems.getLength();j++) {
					// case where there is a MainDescription
					if (listItems.item(j).getNodeName().equals(XMLParser.maindescription)) {
						mainDescription = listItems.item(j).getTextContent();
					}
					// case where there are different sections
					if (listItems.item(j).getNodeName().equals(XMLParser.section)) {						
						aName = listItems.item(j).getAttributes().getNamedItem(Name).getNodeValue();
						aId = listItems.item(j).getAttributes().getNamedItem(Id).getNodeValue();						
						// for the description
						NodeList listDescription = listItems.item(j).getChildNodes();
						// for each node: get the description by the XML file
						for (int k = 0; k < listDescription.getLength();k++) {
							if (listDescription.item(k).getNodeName().equals(XMLParser.description)) {
								aDescriptionSection = listDescription.item(k).getTextContent();
							}
						}
						// create a new instance of Section to fill it
						Section aSection = new Section();
						// set the different attributs
						aSection.setName(aName);
						aSection.setGuid(aId);
						aSection.setDescription(EncodingProcessor.cleanString(aDescriptionSection));
						
						// add the fill element in the list of sections
						sections.add(aSection);						
					}
				}
			}			
		}		
		// set the list of sections by the element
		((CheckList) myElement).setSections(sections);
		((CheckList) myElement).setDescription(EncodingProcessor.cleanString(mainDescription));
	}
}
