package wilos.test.business.services;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.business.services.checklist.CheckListService;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.section.Section;
import wilos.test.TestConfiguration;

public class CheckListServiceTest extends TestCase {
	private CheckListService checklistService ;

	private CheckList checklist ;
	private Set<Section> sections;
	
	public static final String guid = "guident" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;
	
	
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the CheckListService Singleton for managing Guideline data
		this.checklistService = (CheckListService) TestConfiguration.getInstance().getApplicationContext().getBean("CheckListService") ;

		// Create empty Checklist
		this.checklist = new CheckList() ;
		this.sections = new HashSet<Section>();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		// Delete the tmp checklist from the database.
		this.checklistService.getCheckListDao().deleteCheckList(this.checklist) ;
	}	
	
	public void testSaveCheckList() {
		// Rk: the setUp method is called here.

		// Save the checklist to test the method saveGuideline.
		this.checklist.setGuid(guid);
		this.checklist.setName(name);
		this.checklist.setDescription(description);
		
		Section tmpSection = new Section();
		tmpSection.setName("name");
		
		this.sections.add(tmpSection);
		
		//this.checklist.setSections(this.sections);
		
		
		// TODO modifier pour la nouvelle conception de guidance
		/* this.guidance.setTaskdefinition(null);
		this.guidance.setRoledefinition(null);
		this.guidance.setActivity(null);*/
		
		this.checklistService.saveCheckList(this.checklist) ;
		String id = this.checklist.getId() ;

		// Look if this checklist is also into the database.
		CheckList checklistTmp = (CheckList) this.checklistService.getCheckListDao().getCheckList(id) ;
		assertNotNull(checklistTmp) ;
		assertEquals(checklistTmp.getGuid(), guid) ;
		assertEquals(checklistTmp.getName(), name) ;
		assertEquals(checklistTmp.getDescription(), description) ;

		// Rk: the tearDown method is called here.
	}
	
	public void testGetSection() {
		
	}
}
