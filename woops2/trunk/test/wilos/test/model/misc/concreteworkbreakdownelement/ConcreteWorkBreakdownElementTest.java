package wilos.test.model.misc.concreteworkbreakdownelement;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
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
		this.concreteWorkBreakdownElement.setAccomplishedTime(ACCOMPLISHED_TIME);
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
		cwbe.setAccomplishedTime(ACCOMPLISHED_TIME);
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
		cwbe.setAccomplishedTime(ACCOMPLISHED_TIME);
		cwbe.setPlannedTime(PLANNED_TIME);
		cwbe.setPlannedFinishingDate(this.date);

		assertTrue(this.concreteWorkBreakdownElement.equals(cwbe)) ;

		ConcreteWorkBreakdownElement cwbe2 = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName("Another concrete name") ;
		cwbe.setAccomplishedTime(ACCOMPLISHED_TIME);
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
}
