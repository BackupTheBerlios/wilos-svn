package wilos.test.model.misc.concreteworkbreakdownelement;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;
import wilos.utils.Constantes;

public class ConcreteWorkBreakdownElementTest extends TestCase {

	private ConcreteWorkBreakdownElement concreteWorkBreakdownElement ;

	private static final String CONCRETE_NAME = "Work Concrete name" ;

	private Date date;

	public static final int ACCOMPLISHED_TIME = 15 ;

	public static final int PLANNED_TIME = 24 ;

	public static final String PLANNED_FINISHING_DATE_STRING = "18/01/2007 10:00" ;

	public ConcreteWorkBreakdownElementTest(){
		try {
			date = Constantes.DATE_FORMAT.parse(PLANNED_FINISHING_DATE_STRING);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteWorkBreakdownElement = new ConcreteWorkBreakdownElement() ;
		this.concreteWorkBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteWorkBreakdownElement.setPlannedTime(PLANNED_TIME);
		this.concreteWorkBreakdownElement.setPlannedFinishingDate(this.date);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		//this.concreteWorkBreakdownElement.getConcretePredecessors().clear();
		//this.concreteWorkBreakdownElement.getConcreteSuccessors().clear();
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#clone()}.
	 */
	public void testClone() {
		try{
			assertEquals(this.concreteWorkBreakdownElement, this.concreteWorkBreakdownElement.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#hashCode()}.
	 */
	public void testHashCode() {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName(CONCRETE_NAME) ;
		cwbe.setPlannedTime(PLANNED_TIME);
		cwbe.setPlannedFinishingDate(this.date);

		assertNotNull(this.concreteWorkBreakdownElement.hashCode()) ;
		assertNotNull(cwbe.hashCode()) ;
		assertEquals(this.concreteWorkBreakdownElement.hashCode(), cwbe.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName(CONCRETE_NAME) ;
		cwbe.setPlannedTime(PLANNED_TIME);
		cwbe.setPlannedFinishingDate(this.date);

		assertTrue(this.concreteWorkBreakdownElement.equals(cwbe)) ;

		ConcreteWorkBreakdownElement cwbe2 = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName("Another concrete name") ;
		cwbe.setPlannedTime(PLANNED_TIME);
		cwbe.setPlannedFinishingDate(this.date);

		assertFalse(this.concreteWorkBreakdownElement.equals(cwbe2)) ;
	}

	public void testAddWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);

		assertNotNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());
		assertTrue(this.concreteWorkBreakdownElement.getWorkBreakdownElement().equals(workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	public void testRemoveWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);

		assertNotNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());
		assertTrue(this.concreteWorkBreakdownElement.getWorkBreakdownElement().equals(workBreakdownElement));

		this.concreteWorkBreakdownElement.removeWorkBreakdownElement(workBreakdownElement);

		assertFalse(workBreakdownElement.equals(this.concreteWorkBreakdownElement.getWorkBreakdownElement()));
		assertNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());

		// Rk: the tearDown method is called here.
	}
	
	/*
	 * a modifier pour prendre en compte des concreteWorkOrder
	 * 
	public final void testAddSuccessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addSuccessor(tmp);

		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertTrue(tmp.getPredecessor().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	public final void testAddPredecessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addPredecessor(tmp);

		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertTrue(tmp.getSuccessor().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	public final void testRemoveSuccessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addSuccessor(tmp);
		this.workBreakdownElement.removeSuccessor(tmp);

		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertFalse(this.workBreakdownElement.equals(tmp.getPredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testRemovePredecessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addPredecessor(tmp);
		this.workBreakdownElement.removePredecessor(tmp);

		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertFalse(this.workBreakdownElement.equals(tmp.getSuccessor()));

		// Rk: the tearDown method is called here.
	}
	
	public final void testAddAllSuccessors() {
		// Rk: the setUp method is called here.

		WorkOrder tmp1 = new WorkOrder();
		tmp1.setLinkType(LINK_TYPE);
		WorkOrder tmp2 = new WorkOrder();
		tmp2.setLinkType("other link type");
		Set<WorkOrder> list = new HashSet<WorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.workBreakdownElement.addAllSuccessors(list);

		assertTrue(this.workBreakdownElement.getSuccessors().size() == 2);
		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp1));
		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp2));
		assertTrue(this.workBreakdownElement.equals(tmp1.getPredecessor()));
		assertTrue(this.workBreakdownElement.equals(tmp2.getPredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testAddAllPredecessors() {
		// Rk: the setUp method is called here.

		WorkOrder tmp1 = new WorkOrder();
		tmp1.setLinkType(LINK_TYPE);
		WorkOrder tmp2 = new WorkOrder();
		tmp2.setLinkType("other link type");
		Set<WorkOrder> list = new HashSet<WorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.workBreakdownElement.addAllPredecessors(list);

		assertTrue(this.workBreakdownElement.getPredecessors().size() == 2);
		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp1));
		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp2));
		assertTrue(this.workBreakdownElement.equals(tmp1.getSuccessor()));
		assertTrue(this.workBreakdownElement.equals(tmp2.getSuccessor()));
		
		// Rk: the tearDown method is called here.
	}

	public final void testRemoveAllSuccessors() {
		// Rk: the setUp method is called here.

		WorkOrder tmp1 = new WorkOrder();
		tmp1.setLinkType(LINK_TYPE);
		WorkOrder tmp2 = new WorkOrder();
		tmp2.setLinkType("other link type");
		Set<WorkOrder> list = new HashSet<WorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.workBreakdownElement.addAllSuccessors(list);
		this.workBreakdownElement.removeAllSuccessors();

		assertTrue(this.workBreakdownElement.getSuccessors().size() == 0);
		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp1));
		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp2));
		assertFalse(this.workBreakdownElement.equals(tmp1.getPredecessor()));
		assertFalse(this.workBreakdownElement.equals(tmp2.getPredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testRemoveAllPredecessors() {
		// Rk: the setUp method is called here.

		WorkOrder tmp1 = new WorkOrder();
		tmp1.setLinkType(LINK_TYPE);
		WorkOrder tmp2 = new WorkOrder();
		tmp2.setLinkType("other link type");
		Set<WorkOrder> list = new HashSet<WorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.workBreakdownElement.addAllPredecessors(list);
		this.workBreakdownElement.removeAllPredecessors();

		assertTrue(this.workBreakdownElement.getPredecessors().size() == 0);
		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp1));
		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp2));
		assertFalse(this.workBreakdownElement.equals(tmp1.getSuccessor()));
		assertFalse(this.workBreakdownElement.equals(tmp2.getSuccessor()));

		// Rk: the tearDown method is called here.
	}
	*/
}
