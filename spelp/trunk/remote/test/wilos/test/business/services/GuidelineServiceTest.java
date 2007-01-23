package wilos.test.business.services;

import junit.framework.TestCase;
import wilos.business.services.guide.GuidelineService;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.guide.Guideline;
import wilos.test.TestConfiguration;

public class GuidelineServiceTest extends TestCase {

	private GuidelineService guidelineService ;

	private Guideline guideline ;

	//public static final String id = "";
	public static final String guid = "guident" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	
	
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the GuidelineService Singleton for managing Guideline data
		this.guidelineService = (GuidelineService) TestConfiguration.getInstance().getApplicationContext().getBean("GuidelineService") ;

		// Create empty Guideline
		this.guideline = new Guideline() ;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		// Delete the tmp guideline from the database.
		this.guidelineService.getGuidelineDao().deleteGuideline(this.guideline) ;
	}

	
	
	
	
	
	// TODO JF : mettre la javadoc
	public void testSaveGuideline() {
		// Rk: the setUp method is called here.

		// Save the guideline to test the method saveGuideline.
		this.guideline.setGuid(guid);
		this.guideline.setName(name);
		this.guideline.setDescription(description);
		this.guideline.setTaskdefinition(null);
		
		

		
		
		this.guidelineService.saveGuideline(this.guideline) ;
		String id = this.guideline.getId() ;

		// Look if this guideline is also into the database.
		Guideline guidelineTmp = (Guideline) this.guidelineService.getGuidelineDao().getGuideline(id) ;
		assertNotNull(guidelineTmp) ;
		assertEquals(guidelineTmp.getGuid(), guid) ;
		assertEquals(guidelineTmp.getName(), name) ;
		assertEquals(guidelineTmp.getDescription(), description) ;

		// Rk: the tearDown method is called here.
	}

}
