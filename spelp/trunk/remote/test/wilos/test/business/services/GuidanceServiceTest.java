package wilos.test.business.services;

import junit.framework.TestCase;
import wilos.business.services.guide.GuidanceService;
import wilos.model.spem2.guide.Guidance;
import wilos.test.TestConfiguration;

public class GuidanceServiceTest extends TestCase {

	private GuidanceService guidanceService ;

	private Guidance guidance ;

	//public static final String id = "";
	public static final String guid = "guident" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	
	
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the GuidelineService Singleton for managing Guideline data
		this.guidanceService = (GuidanceService) TestConfiguration.getInstance().getApplicationContext().getBean("GuidanceService") ;

		// Create empty Guideline
		this.guidance = new Guidance() ;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		// Delete the tmp guidance from the database.
		this.guidanceService.getGuidanceDao().deleteGuidance(this.guidance) ;
	}

	
	
	
	
	
	// TODO JF : mettre la javadoc
	public void testSaveGuidance() {
		// Rk: the setUp method is called here.

		// Save the guideline to test the method saveGuideline.
		this.guidance.setGuid(guid);
		this.guidance.setName(name);
		this.guidance.setDescription(description);
		this.guidance.setTaskdefinition(null);
		this.guidance.setRoledefinition(null);
		this.guidance.setActivity(null);
		
		

		
		
		this.guidanceService.saveGuidance(this.guidance) ;
		String id = this.guidance.getId() ;

		// Look if this guideline is also into the database.
		Guidance guidanceTmp = (Guidance) this.guidanceService.getGuidanceDao().getGuidance(id) ;
		assertNotNull(guidanceTmp) ;
		assertEquals(guidanceTmp.getGuid(), guid) ;
		assertEquals(guidanceTmp.getName(), name) ;
		assertEquals(guidanceTmp.getDescription(), description) ;

		// Rk: the tearDown method is called here.
	}

}
