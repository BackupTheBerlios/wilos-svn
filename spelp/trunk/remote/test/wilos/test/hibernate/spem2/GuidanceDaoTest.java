package wilos.test.hibernate.spem2;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.guide.GuidanceDao;
import wilos.model.spem2.guide.Guidance;
import wilos.test.TestConfiguration;

public class GuidanceDaoTest extends TestCase {

	public static final String guid = "le_guid" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	private GuidanceDao guidanceDAO;
	private Guidance guidance;

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityDao Singleton for managing Activity data
		this.guidanceDAO = (GuidanceDao) TestConfiguration.getInstance().getApplicationContext().getBean("GuidanceDao") ;

		// Create empty Activity.
		this.guidance = new Guidance() ;
		this.guidance.setTaskdefinition(null);
		this.guidance.setActivity(null);
		this.guidance.setRoledefinition(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.guidanceDAO.deleteGuidance(this.guidance) ;
	}

	public void testSaveOrUpdateGuidance() {
		// Rk: the setUp method is called here.

		//Save the guideline with the method to test.
		this.guidanceDAO.saveOrUpdateGuidance(this.guidance) ;
		
		// Check the saving.
		String id = this.guidance.getId() ;
		Guidance guidanceTmp = (Guidance) this.guidanceDAO.getGuidance(id) ;
		assertNotNull(guidanceTmp) ;
		
		// Rk: the tearDown method is called here.
	}
	
	
	
	public void testGetAllGuidances() {
		//Rk: the setUp method is called here.
		
		//Save the guideline with the method to test.
		this.guidanceDAO.saveOrUpdateGuidance(this.guidance) ;
		
		// Look if this guideline is also into the database and look if the size of the set is >= 1.
		List<Guidance> guidances = this.guidanceDAO.getAllGuidances();
		assertNotNull(guidances) ;
		assertTrue(guidances.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	
	public void testGetGuidance() {
		// Rk: the setUp method is called here.

		// Add properties to the guideline.
		this.guidance.setGuid(guid);
		this.guidance.setName(name);
		this.guidance.setDescription(description);
		
		// Save the guideline into the database.
		this.guidanceDAO.saveOrUpdateGuidance(this.guidance);
		String id = guidance.getId();
		
		// Test the method getGuideline with an existing guideline.
		Guidance guidanceTmp = this.guidanceDAO.getGuidance(id);
		assertNotNull(guidanceTmp);
		assertEquals("GUID", guidanceTmp.getGuid(), guid) ;
		assertEquals("Name", guidanceTmp.getName(), name) ;
		assertEquals("Name", guidanceTmp.getDescription(), description) ;
		
		// Test the method getGuideline with an unexisting guideline.
		this.guidanceDAO.deleteGuidance(this.guidance) ;
		guidanceTmp = this.guidanceDAO.getGuidance(id) ;
		assertNull(guidanceTmp) ;

		// Rk: the tearDown method is called here.
	}

	public void testDeleteGuidance() {
		// Rk: the setUp method is called here.

		// Save the guideline into the database.
		this.guidanceDAO.saveOrUpdateGuidance(this.guidance) ;
		String id = this.guidance.getId() ;

		// Test the method deleteGuideline with an guideline existing into the db.
		this.guidanceDAO.deleteGuidance(this.guidance) ;

		// See if this.guideline is now absent in the db.
		Guidance guidanceTmp = (Guidance) this.guidanceDAO.getGuidance(id) ;
		assertNull(guidanceTmp) ;

		// Test the method deleteGuideline with an guideline unexisting into the db.
		// Normally here there are no exception thrown.
		this.guidanceDAO.deleteGuidance(this.guidance) ;

		// Rk: the tearDown method is called here.

	}
}
