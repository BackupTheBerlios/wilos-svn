
package woops2.test.model.task ;

import junit.framework.TestCase ;
import woops2.model.task.Step ;
import woops2.model.task.TaskDefinition ;

/**
 * This class represents the class test of the Step class.
 * @author Sebastien
 * @author eperico 
 * 
 */
public class StepTest extends TestCase {

	private Step step ;

	public static final String IDEPF = "idEPF" ;

	public static final String NAME = "name" ;

	public static final String DESCRIPTION = "description" ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.step = new Step() ;
		this.step.setDescription(DESCRIPTION) ;
		this.step.setName(NAME) ;
		this.step.setIdEPF(IDEPF) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.step.clone(), this.step) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.model.task.Step#hashCode()}.
	 */
	public void testHashCode() {
		Step stp = new Step() ;
		stp.setDescription(DESCRIPTION) ;
		stp.setName(NAME) ;
		stp.setIdEPF(IDEPF) ;

		assertNotNull(this.step.hashCode()) ;
		assertNotNull(stp.hashCode()) ;
		assertEquals(this.step.hashCode(), stp.hashCode()) ;
	}

	/**
	 * Test method for {@link woops2.model.task.Step#equals(Object obj)}.
	 */
	public void testEquals() {

		// Assert if it's equal by references.
		assertTrue("By references", this.step.equals(this.step)) ;

		// Assert if it's equal field by field.
		Step step = new Step() ;
		step.setDescription(DESCRIPTION) ;
		step.setName(NAME) ;
		step.setIdEPF(IDEPF) ;

		assertTrue("Field by field", this.step.equals(step)) ;

		// Assert if it's not equal.
		Step step2 = new Step() ;
		step2.setDescription("description2") ;
		step2.setName("name2") ;
		step2.setIdEPF("idEPF2") ;

		assertFalse("Not equals", this.step.equals(step2)) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.Step#addToTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testAddToTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setDescription(DESCRIPTION) ;
		taskDefinition.setName(NAME) ;
		taskDefinition.setIdEPF(IDEPF) ;

		this.step.addToTaskDefinition(taskDefinition) ;

		assertNotNull(this.step.getTaskDefinition()) ;
		assertFalse(taskDefinition.getSteps().isEmpty()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.Step#removeFromTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testRemoveFromTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setDescription(DESCRIPTION) ;
		taskDefinition.setName(NAME) ;
		taskDefinition.setIdEPF(IDEPF) ;

		this.step.addToTaskDefinition(taskDefinition) ;
		this.step.removeFromTaskDefinition(taskDefinition) ;

		assertNull(this.step.getTaskDefinition()) ;
		assertTrue(taskDefinition.getSteps().isEmpty()) ;
	}

}
