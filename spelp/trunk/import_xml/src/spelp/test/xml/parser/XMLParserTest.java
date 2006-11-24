package spelp.test.xml.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import spelp.xml.parser.XMLParser;
import woops2.model.process.Process;

public class XMLParserTest extends XMLParser {
	public static File pathScrum = new File("ressources" + File.separator + "scrum.xml"); 
	public static File pathOPenUP =new File("ressources" + File.separator + "sortieEPF.xml");
	public static File fileError =new File("prout");
		
	@Test
	public void testGetProcess(){
		System.out.println("Test de getProcess");
		File[] f = new File[]{
				pathScrum,pathOPenUP
		};
		for (int i = 0 ; i < f.length ; i++){
			System.out.println(">>>>>>>"+f[i].getName());
			Process p;
			try {
				p = XMLParser.getProcess(f[i]);
				assertTrue(p.getBreakDownElements().size() != 0);
				if (f[i].getName().equals("scrum.xml")){
					// nombre de role dans scrium.xml
					assertTrue(RoleList.size() == 4) ;
					// nombre de task dans scrum.xml
					assertTrue(TasksList.size() == 8) ;
					// nombre de role descriptor dans scrum.xml
					assertTrue(p.getBreakDownElements().size() == 35);
				}
				else if(f[i].getName().equals("sortieEPF.xml")){
					// nombre de role dans sortieEPF.xml
					assertTrue(RoleList.size() == 13) ;
					// nombre de task dans sortieEPF.xml
					assertTrue(TasksList.size() == 23) ;
					// nombre de role descriptor dans sortieEPF.xml
					assertTrue(p.getBreakDownElements().size() == 58);
				}
			} catch (Exception e) {
				fail();
			}
			try {
				p = XMLParser.getProcess(fileError);
				fail();
			} catch (Exception e) {
				assertTrue(true);
			}
		}
	}
}
