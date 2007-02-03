package wilos.test.model.spem2.workbreakdownelement;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 * @author deder
 * 
 */
public class WorkBreakdownElementTest extends TestCase {

	private WorkBreakdownElement workBreakdownElement;

	public WorkBreakdownElementTest(){
		this.workBreakdownElement = new WorkBreakdownElement() ;
		this.workBreakdownElement.setGuid("idEPF") ;
		this.workBreakdownElement.setName("name1") ;
		this.workBreakdownElement.setDescription("description1") ;
		this.workBreakdownElement.setPrefix("prefix1") ;
		this.workBreakdownElement.setIsOptional(true) ;
		this.workBreakdownElement.setIsPlanned(false) ;
		this.workBreakdownElement.setHasMultipleOccurrences(false) ;
		this.workBreakdownElement.setIsOngoing(true);
		this.workBreakdownElement.setIsPlanned(true);
		this.workBreakdownElement.setIsRepeatable(true);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		this.workBreakdownElement.getPredecessors().clear();
		this.workBreakdownElement.getSuccessors().clear();
	}

	/**
	 * Test method for {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#clone()}.
	 */
	public void testClone() {
		try {
			assertEquals(this.workBreakdownElement, this.workBreakdownElement.clone());
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}
	
	/**
	 * Test method for
	 * {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		assertNotNull(this.workBreakdownElement.hashCode());
		assertNotNull(workBreakdownElement.hashCode());
		assertEquals(this.workBreakdownElement.hashCode(),workBreakdownElement.hashCode());

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.workBreakdownElement
				.equals(this.workBreakdownElement));

		// Assert if it's equal field by field.
		WorkBreakdownElement bdeTmp1 = null;
		try {
			bdeTmp1 = this.workBreakdownElement.clone();
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testEquals method");
		}
		assertTrue("Field by field", this.workBreakdownElement.equals(bdeTmp1));

		// Assert if it's not equal.
		WorkBreakdownElement bdeTmp2 = new WorkBreakdownElement();
		workBreakdownElement.setGuid("idEPF2") ;
		bdeTmp2.setName("name2") ;
		bdeTmp2.setDescription("description2") ;
		bdeTmp2.setPrefix("prefix2") ;
		bdeTmp2.setIsOptional(true) ;
		bdeTmp2.setIsPlanned(false) ;
		bdeTmp2.setHasMultipleOccurrences(false) ;
		bdeTmp2.setIsOngoing(true);
		bdeTmp2.setIsPlanned(true);
		bdeTmp2.setIsRepeatable(true);
		assertFalse("Not equals", this.workBreakdownElement.equals(bdeTmp2));

		// Rk: the tearDown method is called here.
	}
	
	public final void testAddConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp = new ConcreteWorkBreakdownElement();
		this.workBreakdownElement.addConcreteWorkBreakdownElement(tmp);
		
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp));
		assertTrue(tmp.getWorkbreakdownelement().equals(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testRemoveConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp = new ConcreteWorkBreakdownElement();
		this.workBreakdownElement.addConcreteBreakdownElement(tmp);
		this.workBreakdownElement.removeConcreteWorkBreakdownElement(tmp);
		
		assertFalse(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp));
		assertFalse(tmp.getWorkbreakdownelement().equals(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testAddAllConcreteWorkBreakdownElements() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp1 = new ConcreteWorkBreakdownElement();
		tmp1.setConcreteName("tmp1");
		ConcreteWorkBreakdownElement tmp2 = new ConcreteWorkBreakdownElement();
		tmp2.setConcreteName("tmp2");
		Set<ConcreteWorkBreakdownElement> list = new HashSet<ConcreteWorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllConcreteWorkBreakdownElements(list);
		
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().size() == 2);
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp1));
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp2));
		assertTrue(tmp1.getWorkbreakdownelement().equals(this.workBreakdownElement));
		assertTrue(tmp2.getWorkbreakdownelement().equals(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testRemoveAllConcreteWorkBreakdownElements() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp1 = new ConcreteWorkBreakdownElement();
		tmp1.setConcreteName("tmp1");
		ConcreteWorkBreakdownElement tmp2 = new ConcreteWorkBreakdownElement();
		tmp2.setConcreteName("tmp2");
		Set<ConcreteWorkBreakdownElement> list = new HashSet<ConcreteWorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllConcreteWorkBreakdownElements(list);
		this.workBreakdownElement.removeAllConcreteWorkBreakdownElements();
		
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().size() == 0);
		assertFalse(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp1));
		assertFalse(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp2));
		assertFalse(tmp1.getWorkbreakdownelement().equals(this.workBreakdownElement));
		assertFalse(tmp2.getWorkbreakdownelement().equals(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testAddSuccessor() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp = new WorkBreakdownElement();
		this.workBreakdownElement.addSuccessor(tmp);
		
		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertTrue(tmp.getPredecessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testAddPredecessor() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp = new WorkBreakdownElement();
		this.workBreakdownElement.addPredecessor(tmp);
		
		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertTrue(tmp.getSuccessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}

	public final void testRemoveSuccessor() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp = new WorkBreakdownElement();
		this.workBreakdownElement.addSuccessor(tmp);
		this.workBreakdownElement.removeSuccessor(tmp);
		
		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertFalse(tmp.getPredecessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testRemovePredecessor() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp = new WorkBreakdownElement();
		this.workBreakdownElement.addPredecessor(tmp);
		this.workBreakdownElement.removePredecessor(tmp);
		
		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertFalse(tmp.getSuccessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testAddAllPredecessors() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp1 = new WorkBreakdownElement();
		tmp1.setName("tmp1");
		WorkBreakdownElement tmp2 = new WorkBreakdownElement();
		tmp2.setName("tmp2");
		Set<WorkBreakdownElement> list = new HashSet<WorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllPredecessors(list);
		
		assertTrue(this.workBreakdownElement.getPredecessors().size() == 2);
		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp1));
		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp2));
		assertTrue(tmp1.getSuccessors().contains(this.workBreakdownElement));
		assertTrue(tmp2.getSuccessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testAddAllSuccessors() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp1 = new WorkBreakdownElement();
		tmp1.setName("tmp1");
		WorkBreakdownElement tmp2 = new WorkBreakdownElement();
		tmp2.setName("tmp2");
		Set<WorkBreakdownElement> list = new HashSet<WorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllSuccessors(list);
		
		assertTrue(this.workBreakdownElement.getSuccessors().size() == 2);
		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp1));
		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp2));
		assertTrue(tmp1.getPredecessors().contains(this.workBreakdownElement));
		assertTrue(tmp2.getPredecessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testRemoveSuccessors() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp1 = new WorkBreakdownElement();
		tmp1.setName("tmp1");
		WorkBreakdownElement tmp2 = new WorkBreakdownElement();
		tmp2.setName("tmp2");
		Set<WorkBreakdownElement> list = new HashSet<WorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllSuccessors(list);
		this.workBreakdownElement.removeAllSuccessors();
		
		assertTrue(this.workBreakdownElement.getSuccessors().size() == 0);
		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp1));
		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp2));
		assertFalse(tmp1.getPredecessors().contains(this.workBreakdownElement));
		assertFalse(tmp2.getPredecessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public final void testRemovepredecessors() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement tmp1 = new WorkBreakdownElement();
		tmp1.setName("tmp1");
		WorkBreakdownElement tmp2 = new WorkBreakdownElement();
		tmp2.setName("tmp2");
		Set<WorkBreakdownElement> list = new HashSet<WorkBreakdownElement>();
		list.add(tmp1);
		list.add(tmp2);
		
		this.workBreakdownElement.addAllPredecessors(list);
		this.workBreakdownElement.removeAllPredecessors();
		
		assertTrue(this.workBreakdownElement.getPredecessors().size() == 0);
		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp1));
		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp2));
		assertFalse(tmp1.getSuccessors().contains(this.workBreakdownElement));
		assertFalse(tmp2.getSuccessors().contains(this.workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
}
