package wilos.presentation.assistant.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import javax.xml.xpath.XPathConstants;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;

import wilos.presentation.assistant.model.WizardOptions;

public class OptionsParser {
	
	private static final String fic = "options.xml";
	private File XML_File;
	private int refreshTime = 5;
	private Locale locale = Locale.getDefault();
	private static final String xpath_locale ="//Locale";
	private static final String xpath_refreshtime ="//RefreshTime";
	
	public OptionsParser(String f) {
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
	
	public OptionsParser() {
		XML_File = new File (OptionsParser.fic);
		XMLUtils.setDocument(XML_File);
		if (!XML_File.exists()) {
			try {
				XML_File.createNewFile();
			} catch (Exception e) {}
		}
	}
	
	public int getRefreshTime() {	
		if (XML_File.exists() && XML_File.length() > 2) {
			Node node = (Node)XMLUtils.evaluate(xpath_refreshtime, XPathConstants.NODE);
			if(node != null) {
				refreshTime = Integer.parseInt(node.getTextContent());
			}
		}
		else
		{
			refreshTime = 0;
		}
		
		return refreshTime;
	}
	
	public Locale getLocale() {
		if (XML_File.exists() && XML_File.length() > 2) {
			Node node = (Node)XMLUtils.evaluate(xpath_locale, XPathConstants.NODE);
			if(node != null) {
				locale = new Locale(node.getTextContent());
			}
		}
		
		return locale;
	}
	
	public void saveOptions(WizardOptions wo) {
		Element options = new Element("Options");
		Element loc = new Element("Locale");
		Element ref = new Element("RefreshTime");
		Document myDocument = new  Document(options);
		
		this.locale = wo.getLocale();
		loc.addContent(wo.getLocale().getLanguage());
		options.addContent(loc);
		
		this.refreshTime = wo.getRefreshTime(); 
		ref.addContent(""+wo.getRefreshTime());
		options.addContent(ref);

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
}
