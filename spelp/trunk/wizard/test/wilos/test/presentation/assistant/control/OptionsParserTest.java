package wilos.test.presentation.assistant.control;

import java.util.Locale;

import wilos.presentation.assistant.control.OptionsParser;
import wilos.presentation.assistant.model.WizardOptions;
import junit.framework.TestCase;

public class OptionsParserTest extends TestCase {
	
	public void testGetLocale() {
		OptionsParser op1 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_ok.xml");
		Locale loc1 = op1.getLocale();
		assertTrue(loc1.getLanguage() .equals("chinese"));
		
		OptionsParser op2 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_noLocale.xml");
		Locale loc2 = op2.getLocale();
		assertTrue(loc2.equals(Locale.getDefault()));
		
		OptionsParser op3 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_vide.xml");
		Locale loc3 = op3.getLocale();
		assertTrue(loc3.equals(Locale.getDefault()));
	}
	
	public void testGetRefreshTime() {
		OptionsParser op1 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_ok.xml");
		assertTrue( op1.getRefreshTime() == 12);
		
		OptionsParser op2 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_noRefreshTime.xml");
		assertTrue( op2.getRefreshTime() == 5);
		
		OptionsParser op3 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_vide.xml");
		assertTrue( op3.getRefreshTime() == 5);
	}
	
	public void testSaveOptions() {
		OptionsParser op1 = new OptionsParser("test/wilos/test/presentation/assistant/control/options_test_save.xml");
		op1.saveOptions(new WizardOptions(new Locale("english"), 10));
		Locale loc = op1.getLocale();
		assertTrue(loc.getLanguage() .equals("english"));
		assertTrue( op1.getRefreshTime() == 10);
	}
}
