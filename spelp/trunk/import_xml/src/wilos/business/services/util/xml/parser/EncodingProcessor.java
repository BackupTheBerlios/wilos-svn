package wilos.business.services.util.xml.parser;

public class EncodingProcessor {
	public static String cleanString (String source) {
		String resultat = source.replaceAll("’", "'");
		//String resultat = source;
		return resultat;
	}
}
