package wilos.test.model.misc.concreteworkbreakdownelement;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkOrder;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.utils.Constantes;

public class ConcreteWorkBreakdownElementTest extends TestCase {

	private ConcreteWorkBreakdownElement concreteWorkBreakdownElement ;

	private static final String CONCRETE_NAME = "Work Concrete name" ;
	
	private static final String CONCRETE_LINK_TYPE = "concrete link type";

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
	 */
	public final void testAddConcreteSuccessor() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp = new ConcreteWorkOrder();
		this.concreteWorkBreakdownElement.addConcreteSuccessor(tmp);

		assertTrue(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp));
		assertTrue(tmp.getConcretePredecessor().equals(this.concreteWorkBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	public final void testAddConcretePredecessor() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp = new ConcreteWorkOrder();
		this.concreteWorkBreakdownElement.addConcretePredecessor(tmp);

		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp));
		assertTrue(tmp.getConcreteSuccessor().equals(this.concreteWorkBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	public final void testRemoveConcreteSuccessor() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp = new ConcreteWorkOrder();
		this.concreteWorkBreakdownElement.addConcreteSuccessor(tmp);
		this.concreteWorkBreakdownElement.removeConcreteSuccessor(tmp);

		assertFalse(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp.getConcretePredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testRemoveConcretePredecessor() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp = new ConcreteWorkOrder();
		this.concreteWorkBreakdownElement.addConcretePredecessor(tmp);
		this.concreteWorkBreakdownElement.removeConcretePredecessor(tmp);

		assertFalse(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp.getConcreteSuccessor()));

		// Rk: the tearDown method is called here.
	}
	
	public final void testAddAllConcreteSuccessors() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp1 = new ConcreteWorkOrder();
		tmp1.setConcreteLinkType(CONCRETE_LINK_TYPE);
		ConcreteWorkOrder tmp2 = new ConcreteWorkOrder();
		tmp2.setConcreteLinkType("other concrete link type");
		Set<ConcreteWorkOrder> list = new HashSet<ConcreteWorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.concreteWorkBreakdownElement.addAllConcreteSuccessors(list);

		assertTrue(this.concreteWorkBreakdownElement.getConcreteSuccessors().size() == 2);
		assertTrue(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp1));
		assertTrue(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp2));
		assertTrue(this.concreteWorkBreakdownElement.equals(tmp1.getConcretePredecessor()));
		assertTrue(this.concreteWorkBreakdownElement.equals(tmp2.getConcretePredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testAddAllConcretePredecessors() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp1 = new ConcreteWorkOrder();
		tmp1.setConcreteLinkType(CONCRETE_LINK_TYPE);
		ConcreteWorkOrder tmp2 = new ConcreteWorkOrder();
		tmp2.setConcreteLinkType("other concrete link type");
		Set<ConcreteWorkOrder> list = new HashSet<ConcreteWorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.concreteWorkBreakdownElement.addAllConcretePredecessors(list);

		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors().size() == 2);
		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp1));
		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp2));
		assertTrue(this.concreteWorkBreakdownElement.equals(tmp1.getConcreteSuccessor()));
		assertTrue(this.concreteWorkBreakdownElement.equals(tmp2.getConcreteSuccessor()));
		
		// Rk: the tearDown method is called here.
	}

	public final void testRemoveAllConcreteSuccessors() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp1 = new ConcreteWorkOrder();
		tmp1.setConcreteLinkType(CONCRETE_LINK_TYPE);
		ConcreteWorkOrder tmp2 = new ConcreteWorkOrder();
		tmp2.setConcreteLinkType("other concrete link type");
		Set<ConcreteWorkOrder> list = new HashSet<ConcreteWorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.concreteWorkBreakdownElement.addAllConcreteSuccessors(list);
		this.concreteWorkBreakdownElement.removeAllConcreteSuccessors();

		assertTrue(this.concreteWorkBreakdownElement.getConcreteSuccessors().size() == 0);
		assertFalse(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp1));
		assertFalse(this.concreteWorkBreakdownElement.getConcreteSuccessors().contains(tmp2));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp1.getConcretePredecessor()));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp2.getConcretePredecessor()));

		// Rk: the tearDown method is called here.
	}

	public final void testRemoveAllConcretePredecessors() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder tmp1 = new ConcreteWorkOrder();
		tmp1.setConcreteLinkType(CONCRETE_LINK_TYPE);
		ConcreteWorkOrder tmp2 = new ConcreteWorkOrder();
		tmp2.setConcreteLinkType("other concrete link type");
		Set<ConcreteWorkOrder> list = new HashSet<ConcreteWorkOrder>();
		list.add(tmp1);
		list.add(tmp2);

		this.concreteWorkBreakdownElement.addAllConcretePredecessors(list);
		this.concreteWorkBreakdownElement.removeAllConcretePredecessors();

		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors().size() == 0);
		assertFalse(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp1));
		assertFalse(this.concreteWorkBreakdownElement.getConcretePredecessors().contains(tmp2));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp1.getConcreteSuccessor()));
		assertFalse(this.concreteWorkBreakdownElement.equals(tmp2.getConcreteSuccessor()));

		// Rk: the tearDown method is called here.
	}
}
