package wilos.business.services.util.xml.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZIPUtils
{
	static final int BUFFER = 2048;
	static final String prefix = "test_" ;
	
	private ZipFile zipfile ;
	private File file ;
	
	public ZIPUtils(File file) {
		
			System.out.println(file.exists());
			try {
				zipfile = new ZipFile (file);
				this.file = file ;
			} catch (ZipException e) {
				if (file.exists()){
					zipfile = null ;
				}
				else {
					// TODO Exception
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

	public boolean isEmpty() {
		return (zipfile == null) ;
	}
	
	public InputStream getXMLStream (){
		InputStream str = null ;
		boolean trouve = false ;
		// if the file is not empty
		if (!isEmpty()){
			ZipEntry entry = null;
			Enumeration e = zipfile.entries();
			// searching a xml file
			while(e.hasMoreElements() && !trouve) {
				entry = (ZipEntry) e.nextElement();
				trouve = isExtension(entry.getName(), "xml");
			}
			// if the file is founded
			if (trouve){
				try {
					str = zipfile.getInputStream(entry);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return str ;
	}
	
	public static boolean isExtension(String filePath, String extension){
		return filePath.substring(filePath.lastIndexOf(".")+1).equals(extension);
	}
	
	// exctraction du fichier xml et retour de son emplacement
	public File getXMLFile() {
		File fileReturned = null ;
		try {
			
			boolean trouve = false ;
			// if the file is not empty
			if (!isEmpty()){
				BufferedOutputStream dest = null;
				BufferedInputStream is = null;
				ZipEntry entry = null;
				Enumeration e = zipfile.entries();
				// searching a xml file
				while(e.hasMoreElements() && !trouve) {
					entry = (ZipEntry) e.nextElement();
					trouve = isExtension(entry.getName(), "xml");
				}
				// if the file is founded
				if (trouve){
					String path = zipfile.getName().substring(0,zipfile.getName().lastIndexOf(file.separator)+1)+ prefix + entry.getName();
					fileReturned = new File(path);
					//fileReturned.createTempFile(entry.getName().substring(0,entry.getName().lastIndexOf(".")), ".xml");
					
					fileReturned.deleteOnExit();
					is = new BufferedInputStream(zipfile.getInputStream(entry));
					
					int count;
					byte data[] = new byte[BUFFER];
					
					// writing the file from the zip in the repertory of the zipfile
					FileOutputStream fos = new FileOutputStream(fileReturned);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					
					dest.flush();
					dest.close();
					is.close();				
					
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return fileReturned;
	}

}
