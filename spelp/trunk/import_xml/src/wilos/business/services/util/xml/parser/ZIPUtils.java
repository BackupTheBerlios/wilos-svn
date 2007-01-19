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
	
	public ZIPUtils(File file) throws ZipException {
			try {
				zipfile = new ZipFile (file);
				this.file = file ;
			} catch (ZipException e) {
				zipfile = null ;
				if (!file.exists() || !e.getMessage().equals("error in opening zip file")){
					// TODO Exception
					throw e;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ZipException(e.getMessage());
			}
			
	}

	public boolean isEmpty() {
		return (zipfile == null) ;
	}
	
	public InputStream getXMLStream (){
		InputStream str = null ;
		ZipEntry entry = getXMLEntry() ;
		if (entry != null){
			try {
				str = zipfile.getInputStream(entry);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return str ;
	}
	
	private ZipEntry getXMLEntry () {
		ZipEntry entry = null ;
		boolean trouve = false ;
		// if the file is not empty
		if (!isEmpty()){
			Enumeration e = zipfile.entries();
			// searching a xml file
			while(e.hasMoreElements() && !trouve) {
				entry = (ZipEntry) e.nextElement();
				trouve = XMLUtils.isExtension(entry.getName(), "xml");
			}
		}
		return entry ;
	}
	
	// exctraction du fichier xml et retour de son emplacement
	public File getXMLFile() {
		File fileReturned = null ;
		ZipEntry entry = getXMLEntry();
		BufferedOutputStream dest = null;
		BufferedInputStream is = null;
		if (entry != null){
			try {
				String path = zipfile.getName().substring(0,zipfile.getName().lastIndexOf(file.separator)+1)+ prefix + entry.getName();
				fileReturned = new File(path);
			
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
			catch (IOException io){
				
			}
		}
		return fileReturned;
	}

	/**
	 
	 **/
	public void extract(String path) {
		if (path.length() != 0 && path.charAt(path.length()-1) != File.separatorChar){
			path += File.separatorChar ;
		}
		ZipEntry entry = null ;
		// if the file is not empty
		if (!isEmpty()){
			Enumeration e = zipfile.entries();
			// searching a xml file
			while(e.hasMoreElements()) {
				entry = (ZipEntry) e.nextElement();
				// save on the disk
				BufferedOutputStream dest = null;
				BufferedInputStream is = null;
				try {
					is = new BufferedInputStream(zipfile.getInputStream(entry));
					int count;
					byte data[] = new byte[BUFFER];
					// writing the file from the zip in the repertory of the zipfile
					
					File f = new File(path + entry.getName()+File.separatorChar);
					System.out.println(f.getAbsolutePath());
					if (entry.isDirectory()){
						f.mkdirs() ;
					}
					else {
						FileOutputStream fos = new FileOutputStream(f);
						dest = new BufferedOutputStream(fos, BUFFER);
						while ((count = is.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
						is.close();	
					}
					
					
				}
				catch (IOException io){
					
				}
			}
			
		}
	}
}
