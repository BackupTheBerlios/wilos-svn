
package wilos.test.model.misc.concreteactivity;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;

/**
 * @author Sebastien
 *
 * This class represents the class test of the Concrete Activity class.
 *
 */
public class ConcreteActivityTest  extends TestCase {

	private ConcreteActivity concreteactivity ;
	
	private static final String NAME = "Concrete Name";

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteactivity = new ConcreteActivity() ;
		this.concreteactivity.setConcreteName(NAME);
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
	 * Test method for {@link wilos.model.misc.concreteactivity.ConcreteActivity#clone()}.
	 * ;
	 * 	 */
	public void testClone() {
		try{
			assertEquals(this.concreteactivity, this.concreteactivity.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteactivity.ConcreteActivity#hashCode()}.
	 */
	public void testHashCode() {

		ConcreteActivity tmp = new ConcreteActivity() ;
		tmp.setConcreteName(NAME) ;

		assertNotNull(this.concreteactivity.hashCode()) ;
		assertNotNull(tmp.hashCode()) ;
		assertEquals(this.concreteactivity.hashCode(), tmp.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.test.model.misc.concreteactivity.ConcreteActivity#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		ConcreteActivity tmp = new ConcreteActivity() ;
		tmp.setConcreteName(NAME) ;

		assertTrue(this.concreteactivity.equals(tmp)) ;

		ConcreteActivity act = new ConcreteActivity() ;
		act.setConcreteName("name2") ;
		
		assertFalse(this.concreteactivity.equals(act)) ;
	}

	/**
	 * Test method for
	 * {@link wilos.test.model.misc.concreteactivity.Concrete.Activity#addConcreteBreakdownElement(wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement)}.
	 */
	public void testAddBreakdownElement() {
		
		ConcreteBreakdownElement concretebreakdownElement = new ConcreteBreakdownElement() ;
		concretebreakdownElement.setConcreteName("name1") ;

		this.concreteactivity.addConcreteBreakdownElement(concretebreakdownElement) ;

		assertFalse(this.concreteactivity.getConcreteBreakdownElements().isEmpty()) ;
		assertFalse(concretebreakdownElement.getSuperConcreteActivities().isEmpty()) ;
		assertTrue(this.concreteactivity.getConcreteBreakdownElements().size() == 1) ;
		assertTrue(concretebreakdownElement.getSuperConcreteActivities().size() == 1) ;
		
	}

	/**
	 * Test method for
	 * {@link wilos.test.model.misc.concreteactivity.Concrete.Activity#addAllConcreteBreakdownElements(java.util.Set)}.
	 */
	public void testAddAllConcreteBreakdownElement() {
		
		ConcreteBreakdownElement concretebreakdownElement = new ConcreteBreakdownElement() ;
		concretebreakdownElement.setConcreteName("name1") ;

		ConcreteBreakdownElement tmp = new ConcreteBreakdownElement() ;
		tmp.setConcreteName("name2") ;
		

		Set<ConcreteBreakdownElement> set = new HashSet<ConcreteBreakdownElement>() ;
		set.add(concretebreakdownElement) ;
		set.add(tmp) ;

		this.concreteactivity.addAllConcreteBreakdownElements(set) ;

		assertFalse("bdes vides", this.concreteactivity.getConcreteBreakdownElements().isEmpty()) ;
		assertTrue("bdes = 2", this.concreteactivity.getConcreteBreakdownElements().size() == 2) ;
		assertFalse("brk acts vide", concretebreakdownElement.getSuperConcreteActivities().isEmpty()) ;
		assertTrue("brk acts = 1", concretebreakdownElement.getSuperConcreteActivities().size() == 1) ;
		assertFalse("tmp acts vide", tmp.getSuperConcreteActivities().isEmpty()) ;
		assertTrue("tmp acts = 1", tmp.getSuperConcreteActivities().size() == 1) ;
		
	}

	/**
	 * Test method for
	 * {@link wilos.test.model.misc.concreteactivity.Concrete.Activity#removeConcreteBreakdownElement(wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement)}.
	 */
	public void testRemoveConcreteBreakdownElement() {
		
		ConcreteBreakdownElement concretebreakdownElement = new ConcreteBreakdownElement() ;
		concretebreakdownElement.setConcreteName("name1") ;


		this.concreteactivity.addConcreteBreakdownElement(concretebreakdownElement) ;
		this.concreteactivity.removeConcreteBreakdownElement(concretebreakdownElement) ;

		assertTrue(this.concreteactivity.getConcreteBreakdownElements().isEmpty()) ;
		assertTrue(concretebreakdownElement.getSuperConcreteActivities().isEmpty()) ;
		
	}

	/**
	 * Test method for {@link wilos.test.model.misc.concreteactivity.Concrete.Activity#removeAllConcreteBreakdownElements()}.
	 */
	public void testRemoveAllConcreteBreakdownElements() {

		ConcreteBreakdownElement concretebreakdownElement = new ConcreteBreakdownElement() ;
	
		concretebreakdownElement.setConcreteName("name1") ;
		

		ConcreteBreakdownElement tmp = new ConcreteBreakdownElement() ;

		tmp.setConcreteName("name2") ;
		

		Set<ConcreteBreakdownElement> set = new HashSet<ConcreteBreakdownElement>() ;
		set.add(concretebreakdownElement) ;
		set.add(tmp) ;

		assertTrue(set.size() == 2) ;

		this.concreteactivity.addAllConcreteBreakdownElements(set) ;
		this.concreteactivity.removeAllConcreteBreakdownElements() ;

		assertTrue("bdes", this.concreteactivity.getConcreteBreakdownElements().isEmpty()) ;
		assertTrue("bde.acts", concretebreakdownElement.getSuperConcreteActivities().isEmpty()) ;
		assertTrue("tmp.acts", tmp.getSuperConcreteActivities().isEmpty()) ;
		
	}
}
