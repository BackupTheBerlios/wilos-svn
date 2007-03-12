package wilos.presentation.assistant.ressources;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;


public class Bundle {

	static private final String BASENAME = "wilos/presentation/assistant/ressources/label";
	static private ResourceBundle bundle = ResourceBundle.getBundle(Bundle.BASENAME, Locale.getDefault());
	static String [] availablesLocales = {"en", "fr"};
	
	
	/**
	 * procedure setCurrentLocale
	 * @param locale
	 */
	static public void setCurrentLocale(Locale locale)
	  {
	    if (locale != null)
	    {
	      Locale.setDefault(locale);
	      Bundle.bundle = ResourceBundle.getBundle(Bundle.BASENAME, locale);
	     }
	  }

	/**
	 * function getText
	 * @param key
	 * @return String : the key in the properties file
	 */
	static public String getText(String key)
	{
		try
	    {
	      return Bundle.bundle.getString(key);
	    }
	    catch (MissingResourceException e)
	    {
	      return key;
	    }
	}
	
	/**
	 * function getChar
	 * @param key
	 * @return char: the mnemonic characters
	 */
	static public char getChar(String key)
	{
		return Bundle.getText(key).charAt(0);
	}
	
	/**
	 * function getCode
	 * @param key
	 * @return integer: the mnemonic code
	 */
	static public Integer getCode(String key)
	{
		return new Integer((int)Character.toUpperCase(Bundle.getChar(key)));
	}
	
	/**
	 * function getAvailableLocales
	 * @return Vector<Locale>: availables locale 
	 */
	static public Vector<Locale> getAvailableLocales()
	{
		
		Vector<Locale> locs = new Vector<Locale>();		
		/*		
		URL url = ClassLoader.getSystemResource("wilos/presentation/assistant/ressources");
		File locsDir = null;
		try {
			System.out.println(url.toExternalForm());
			System.out.println(url.toURI());
			locsDir = new File(url.getFile());
			
			JarURLConnection jarCon = null;
			try {
				jarCon = (JarURLConnection)url.openConnection();
				System.out.println(jarCon.getContentType());
				System.out.println(jarCon.getContent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String [] namesList = locsDir.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.endsWith(".properties");
				}				
			});
			if (namesList != null) {
				for (String n : namesList)
				{
					locs.add (new Locale (n.substring(6,8)));
				}
			}

			/*File [] ficLocs = locsDir.listFiles(new FileFilter(){
				public boolean accept (File file){
			      String nomFichier = file.getName().toLowerCase(); 
			      return nomFichier.endsWith(".properties");
				}
			});
			if (ficLocs != null) {
				for (File f : ficLocs)
				{
					locs.add (new Locale (f.getName().substring(6,8)));
				}
			}*/	
		/*} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		for (String s : availablesLocales) {
			locs.add (new Locale (s));
		}
	
		
		return locs;
	}
	
}
