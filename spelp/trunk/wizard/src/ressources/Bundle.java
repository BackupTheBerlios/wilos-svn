package ressources;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Bundle {

	static private final String BASENAME = "ressources/label";
	static private ResourceBundle bundle = ResourceBundle.getBundle(Bundle.BASENAME, Locale.getDefault());
	
	
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
	
}
