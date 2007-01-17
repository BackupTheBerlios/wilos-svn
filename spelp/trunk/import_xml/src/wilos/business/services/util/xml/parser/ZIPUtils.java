package wilos.business.services.util.xml.parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZIPUtils
{
	static final int BUFFER = 2048;
	
	private ZipFile aZipFile ;
	private File file ;
	
	public ZIPUtils(File file) {
		
			System.out.println(file.exists());
			try {
				aZipFile = new ZipFile (file);
				this.file = file ;
			} catch (ZipException e) {
				if (file.exists()){
					aZipFile = null ;
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
		return (aZipFile == null) ;
	}

	public File getXMLFile() {
		
		File fileReturned = null ;
		boolean trouve = false ;
		if (!isEmpty()){
			Enumeration entries = aZipFile.entries();
			ZipEntry e = null;
			byte data[] = new byte[BUFFER];
			/* Processing entries */
			while(entries.hasMoreElements() && !trouve) {
				e = (ZipEntry)entries.nextElement();
				trouve = e.getName().substring(e.getName().lastIndexOf(".")+1).equals("xml");
				
		   }
		   if (trouve){
			   
		   }
		}
		return fileReturned ;
		
	}

}
