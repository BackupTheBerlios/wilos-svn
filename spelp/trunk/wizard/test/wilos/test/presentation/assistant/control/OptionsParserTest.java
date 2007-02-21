package wilos.test.presentation.assistant.control;

import java.util.Locale;

import wilos.presentation.assistant.control.OptionsParser;
import junit.framework.TestCase;

public class OptionsParserTest extends TestCase {
	
	public void testGetRefreshTime() {
		OptionsParser op1 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_ok.xml");
		Locale loc = op1.getLocale();
		assertTrue(loc.getLanguage() .equals("french"));
		assertTrue( op1.getRefreshTime() == 5);
	}
}
