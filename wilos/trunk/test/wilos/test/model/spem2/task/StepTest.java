
package wilos.test.model.spem2.task ;

import junit.framework.TestCase ;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;

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
		this.step.setGuid(IDEPF) ;
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
	 * Test method for {@link wilos.model.spem2.role.RoleDescriptor#clone()}.
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
	 * Test method for {@link wilos.model.spem2.task.Step#hashCode()}.
	 */
	public void testHashCode() {
		Step stp = new Step() ;
		stp.setDescription(DESCRIPTION) ;
		stp.setName(NAME) ;
		stp.setGuid(IDEPF) ;

		assertNotNull(this.step.hashCode()) ;
		assertNotNull(stp.hashCode()) ;
		assertEquals(this.step.hashCode(), stp.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.task.Step#equals(Object obj)}.
	 */
	public void testEquals() {

		// Assert if it's equal by references.
		assertTrue("By references", this.step.equals(this.step)) ;

		// Assert if it's equal field by field.
		Step step = new Step() ;
		step.setDescription(DESCRIPTION) ;
		step.setName(NAME) ;
		step.setGuid(IDEPF) ;

		assertTrue("Field by field", this.step.equals(step)) ;

		// Assert if it's not equal.
		Step step2 = new Step() ;
		step2.setDescription("description2") ;
		step2.setName("name2") ;
		step2.setGuid("idEPF2") ;

		assertFalse("Not equals", this.step.equals(step2)) ;
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.task.Step#addTaskDefinition(wilos.model.spem2.task.TaskDefinition)}.
	 */
	public void testAddToTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setDescription(DESCRIPTION) ;
		taskDefinition.setName(NAME) ;
		taskDefinition.setGuid(IDEPF) ;

		this.step.addTaskDefinition(taskDefinition) ;

		assertNotNull(this.step.getTaskDefinition()) ;
		assertFalse(taskDefinition.getSteps().isEmpty()) ;
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.task.Step#removeTaskDefinition(wilos.model.spem2.task.TaskDefinition)}.
	 */
	public void testRemoveFromTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setDescription(DESCRIPTION) ;
		taskDefinition.setName(NAME) ;
		taskDefinition.setGuid(IDEPF) ;

		this.step.addTaskDefinition(taskDefinition) ;
		this.step.removeTaskDefinition(taskDefinition) ;

		assertNull(this.step.getTaskDefinition()) ;
		assertTrue(taskDefinition.getSteps().isEmpty()) ;
	}
	
	/**
	 * Test method for
	 * {@link wilos.model.spem2.task.Step#compare(java.lang.Object, java.lang.Object)}.
	 */
	public void testCompare() {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setDescription(DESCRIPTION) ;
		taskDefinition.setName(NAME) ;
		taskDefinition.setGuid(IDEPF) ;
		this.step.addTaskDefinition(taskDefinition) ;
		assertEquals(0, this.step.compareTo(this.step)) ;
		
		
		Step stepTmp = new Step();
		stepTmp.setName("otherName");
		assertEquals(-1, this.step.compareTo(stepTmp)) ;
	}

}
