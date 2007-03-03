package wilos.business.services.util.xml.parser;

import java.io.File;
import java.util.zip.ZipException;

import wilos.model.spem2.process.Process;

public class XMLServices {

	/**
	 * getProcess : return a Process Object and 
	 * if the param is a zip file, it is extracted automatically in 
	 * a repertory named by the guid of the Process 
	 * @param XMLPath : can be a XML File or a ZIP File
	 * @return a Process Object
	 */
	public static Process getProcess (String XMLPath, String pathToExtract){
		Process p = null ;
		File f = new File(XMLPath);
		if (XMLUtils.isExtension(XMLPath,"zip")){
			try {
				ZIPUtils z = new ZIPUtils(f);
				if (!z.isEmpty()) {
					p = XMLParser.getProcess(f);
					if (p != null){
						if (pathToExtract.length() !=0 && pathToExtract.charAt(pathToExtract.length()-1) != File.separatorChar){
							pathToExtract += File.separatorChar ;
						}
						z.extract(pathToExtract + p.getGuid() + File.separatorChar);
					}
				}
			} catch (ZipException e) {
				
			}
		}
		else {
			p = XMLParser.getProcess(f);
		}
		return p ;
	}
}
