package wilos.test.hibernate.spem2;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.hibernate.spem2.guide.GuidelineDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.guide.Guideline;
import wilos.test.TestConfiguration;

public class GuidelineDaoTest extends TestCase {

	public static final String guid = "le_guid" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	private GuidelineDao guidelineDao;
	private Guideline guideline;

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityDao Singleton for managing Activity data
		this.guidelineDao = (GuidelineDao) TestConfiguration.getInstance().getApplicationContext().getBean("GuidelineDao") ;

		// Create empty Activity.
		this.guideline = new Guideline() ;
		this.guideline.setTaskdefinition(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.guidelineDao.deleteGuideline(this.guideline) ;
	}

	public void testSaveOrUpdateGuideline() {
		// Rk: the setUp method is called here.

		//Save the guideline with the method to test.
		this.guidelineDao.saveOrUpdateGuideline(this.guideline) ;
		
		// Check the saving.
		String id = this.guideline.getId() ;
		Guideline guidelineTmp = (Guideline) this.guidelineDao.getGuideline(id) ;
		assertNotNull(guidelineTmp) ;
		
		// Rk: the tearDown method is called here.
	}
	
	
	
	public void testGetAllGuidelines() {
		//Rk: the setUp method is called here.
		
		//Save the guideline with the method to test.
		this.guidelineDao.saveOrUpdateGuideline(this.guideline) ;
		
		// Look if this guideline is also into the database and look if the size of the set is >= 1.
		List<Guideline> guidelines = this.guidelineDao.getAllGuidelines();
		assertNotNull(guidelines) ;
		assertTrue(guidelines.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	
	public void testGetGuideline() {
		// Rk: the setUp method is called here.

		// Add properties to the guideline.
		this.guideline.setGuid(guid);
		this.guideline.setName(name);
		this.guideline.setDescription(description);
		
		// Save the guideline into the database.
		this.guidelineDao.saveOrUpdateGuideline(this.guideline);
		String id = guideline.getId();
		
		// Test the method getGuideline with an existing guideline.
		Guideline guidelineTmp = this.guidelineDao.getGuideline(id);
		assertNotNull(guidelineTmp);
		assertEquals("GUID", guidelineTmp.getGuid(), guid) ;
		assertEquals("Name", guidelineTmp.getName(), name) ;
		assertEquals("Name", guidelineTmp.getDescription(), description) ;
		
		// Test the method getGuideline with an unexisting guideline.
		this.guidelineDao.deleteGuideline(this.guideline) ;
		guidelineTmp = this.guidelineDao.getGuideline(id) ;
		assertNull(guidelineTmp) ;

		// Rk: the tearDown method is called here.
	}

	public void testDeleteGuideline() {
		// Rk: the setUp method is called here.

		// Save the guideline into the database.
		this.guidelineDao.saveOrUpdateGuideline(this.guideline) ;
		String id = this.guideline.getId() ;

		// Test the method deleteGuideline with an guideline existing into the db.
		this.guidelineDao.deleteGuideline(this.guideline) ;

		// See if this.guideline is now absent in the db.
		Guideline guidelineTmp = (Guideline) this.guidelineDao.getGuideline(id) ;
		assertNull(guidelineTmp) ;

		// Test the method deleteGuideline with an guideline unexisting into the db.
		// Normally here there are no exception thrown.
		this.guidelineDao.deleteGuideline(this.guideline) ;

		// Rk: the tearDown method is called here.

	}
}
