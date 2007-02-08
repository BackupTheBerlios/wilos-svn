
package wilos.test.model.spem2.activity ;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;

/**
 * @author Sebastien
 *
 * This class represents the class test of the Activity class.
 *
 */
public class ActivityTest extends TestCase {

	private Activity activity ;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.activity = new Activity() ;
		this.activity.setGuid("idEPF1") ;
		this.activity.setName("name1") ;
		this.activity.setDescription("description1") ;
		this.activity.setPrefix("prefix1") ;
		this.activity.setIsOptional(true) ;
		this.activity.setIsPlanned(false) ;
		this.activity.setHasMultipleOccurrences(false) ;
		this.activity.setIsEvenDriven(true) ;
		this.activity.setIsOngoing(false) ;
		this.activity.setIsRepeatable(true) ;
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
	 * Test method for {@link wilos.model.spem2.activity.Activity#clone()}.
	 */
	public void testClone() {
		try{
			assertEquals(this.activity, this.activity.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link wilos.model.spem2.activity.Activity#hashCode()}.
	 */
	public void testHashCode() {

		Activity tmp = new Activity() ;
		tmp.setGuid("idEPF1") ;
		tmp.setName("name1") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;
		tmp.setIsEvenDriven(true) ;
		tmp.setIsOngoing(false) ;
		tmp.setIsRepeatable(true) ;

		assertNotNull(this.activity.hashCode()) ;
		assertNotNull(tmp.hashCode()) ;
		assertEquals(this.activity.hashCode(), tmp.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.activity.Activity#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		Activity tmp = new Activity() ;
		tmp.setGuid("idEPF1") ;
		tmp.setName("name1") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;
		tmp.setIsEvenDriven(true) ;
		tmp.setIsOngoing(false) ;
		tmp.setIsRepeatable(true) ;

		assertTrue(this.activity.equals(tmp)) ;

		Activity act = new Activity() ;
		act.setGuid("idEPF2") ;
		act.setName("name2") ;
		act.setDescription("description2") ;
		act.setPrefix("prefix2") ;
		act.setIsOptional(true) ;
		act.setIsPlanned(false) ;
		act.setHasMultipleOccurrences(false) ;
		act.setIsEvenDriven(true) ;
		act.setIsOngoing(false) ;
		act.setIsRepeatable(true) ;

		assertFalse(this.activity.equals(act)) ;
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.activity.Activity#addBreakdownElement(wilos.model.spem2.breakdownelement.BreakdownElement)}.
	 */
	public void testAddBreakdownElement() {

		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setGuid("idEPF1") ;
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

		this.activity.addBreakdownElement(breakdownElement) ;

		assertFalse(this.activity.getBreakdownElements().isEmpty()) ;
		assertFalse(breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(this.activity.getBreakdownElements().size() == 1) ;
		assertTrue(breakdownElement.getSuperActivities().size() == 1) ;
		assertTrue(this.activity.getBreakdownElements().contains(breakdownElement));
		assertTrue(breakdownElement.getSuperActivities().contains(this.activity));
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.activity.Activity#addAllBreakdownElements(java.util.Set)}.
	 */
	public void testAddAllBreakdownElement() {

		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setGuid("idEPF1") ;
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setGuid("idEPF2") ;
		tmp.setName("name2") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;

		Set<BreakdownElement> set = new HashSet<BreakdownElement>() ;
		set.add(breakdownElement) ;
		set.add(tmp) ;

		this.activity.addAllBreakdownElements(set) ;

		assertFalse("bdes vides", this.activity.getBreakdownElements().isEmpty()) ;
		assertTrue("bdes = 2", this.activity.getBreakdownElements().size() == 2) ;
		assertFalse("brk acts vide", breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue("brk acts = 1", breakdownElement.getSuperActivities().size() == 1) ;
		assertFalse("tmp acts vide", tmp.getSuperActivities().isEmpty()) ;
		assertTrue("tmp acts = 1", tmp.getSuperActivities().size() == 1) ;
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.activity.Activity#removeBreakdownElement(wilos.model.spem2.breakdownelement.BreakdownElement)}.
	 */
	public void testRemoveBreakdownElement() {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setGuid("idEPF1") ;
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

		this.activity.addBreakdownElement(breakdownElement) ;
		this.activity.removeBreakdownElement(breakdownElement) ;

		assertTrue(this.activity.getBreakdownElements().isEmpty()) ;
		assertTrue(breakdownElement.getSuperActivities().isEmpty()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.activity.Activity#removeAllBreakdownElements()}.
	 */
	public void testRemoveAllBreakdownElements() {

		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setGuid("idEPF1") ;
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setGuid("idEPF2") ;
		tmp.setName("name2") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;

		Set<BreakdownElement> set = new HashSet<BreakdownElement>() ;
		set.add(breakdownElement) ;
		set.add(tmp) ;

		assertTrue(set.size() == 2) ;

		this.activity.addAllBreakdownElements(set) ;
		this.activity.removeAllBreakdownElements() ;

		assertTrue("bdes", this.activity.getBreakdownElements().isEmpty()) ;
		assertTrue("bde.acts", breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue("tmp.acts", tmp.getSuperActivities().isEmpty()) ;
	}

	public void testAddConcreteActivity() {
		ConcreteActivity concreteActivity = new ConcreteActivity() ;
		concreteActivity.setConcreteName("name") ;

		this.activity.addConcreteActivity(concreteActivity) ;

		assertTrue(this.activity.getConcreteActivities().size() == 1) ;
		assertNotNull(concreteActivity.getActivity()) ;
	}

	public void testaddAllConcreteActivity() {
		ConcreteActivity ca1 = new ConcreteActivity() ;
		ca1.setConcreteName("name1") ;

		ConcreteActivity ca2 = new ConcreteActivity() ;
		ca2.setConcreteName("name2")  ;

		Set<ConcreteActivity> set = new HashSet<ConcreteActivity>() ;
		set.add(ca1) ;
		set.add(ca2) ;

		this.activity.addAllConcreteActivity(set) ;

		assertFalse(this.activity.getConcreteActivities().isEmpty()) ;
		assertEquals(2, this.activity.getConcreteActivities().size()) ;
		assertNotNull(ca1.getActivity()) ;
		assertNotNull(ca2.getActivity()) ;
	}

	public void testRemoveConcreteActivity() {
		ConcreteActivity concreteActivity = new ConcreteActivity() ;
		concreteActivity.setConcreteName("name");

		this.activity.removeConcreteActivity(concreteActivity) ;

		assertTrue(this.activity.getConcreteActivities().isEmpty()) ;
		assertNull(concreteActivity.getActivity()) ;
	}


	public void testRemoveAllConcreteActivities() {
		ConcreteActivity ca1 = new ConcreteActivity() ;
		ca1.setConcreteName("name1") ;

		ConcreteActivity ca2 = new ConcreteActivity() ;
		ca2.setConcreteName("name2")  ;

		Set<ConcreteActivity> set = new HashSet<ConcreteActivity>() ;
		set.add(ca1) ;
		set.add(ca2) ;

		this.activity.addAllConcreteActivity(set) ;
		assertTrue(this.activity.getConcreteActivities().size() == 2) ;
		assertNotNull(ca1.getActivity()) ;
		assertNotNull(ca2.getActivity()) ;

		this.activity.removeAllConcreteActivities() ;
		assertTrue(this.activity.getConcreteActivities().isEmpty()) ;
		assertNull(ca1.getActivity()) ;
		assertNull(ca2.getActivity()) ;
	}

	/**
	 * test for the relation between Guidance and Activity
	 *
	 */

	public void testAddGuidance() {
		Guidance guidance = new Guidance() ;
		guidance.setName("name") ;

		this.activity.addGuidance(guidance) ;

		assertTrue(this.activity.getGuidances().size() == 1) ;
		assertNotNull(guidance.getActivities()) ;
		// derniere maj relation avec guidance
		assertTrue(guidance.getActivities().contains(this.activity)) ;
	}

	public void testaddAllGuidances() {
		Guidance g1 = new Guidance() ;
		g1.setName("name1") ;

		Guidance g2 = new Guidance() ;
		g2.setName("name2")  ;

		Set<Guidance> set = new HashSet<Guidance>() ;
		set.add(g1) ;
		set.add(g2) ;

		this.activity.addAllGuidances(set) ;

		assertFalse(this.activity.getGuidances().isEmpty()) ;
		assertEquals(2, this.activity.getGuidances().size()) ;
//		 derniere maj relation avec guidance
		assertEquals(1, g1.getActivities().contains(this.activity)) ;
		assertEquals(1, g2.getActivities().contains(this.activity)) ;
	}

	public void testRemoveGuidance() {
		Guidance guidance = new Guidance() ;
		guidance.setName("name");

		this.activity.removeGuidance(guidance) ;

		assertTrue(this.activity.getGuidances().isEmpty()) ;
		// Derniere maj relation guidance
		assertFalse(guidance.getActivities().contains(this.activity)) ;
	}

	public void testRemoveAllGuidances() {
		Guidance g1 = new Guidance() ;
		g1.setName("name1") ;

		Guidance g2 = new Guidance() ;
		g2.setName("name2")  ;

		Set<Guidance> set = new HashSet<Guidance>() ;
		set.add(g1) ;
		set.add(g2) ;

		this.activity.addAllGuidances(set) ;
		assertTrue(this.activity.getGuidances().size() == 2) ;
		assertTrue(g1.getActivities().contains(this.activity));
		assertTrue(g2.getActivities().contains(this.activity));

		this.activity.removeAllGuidances() ;
		assertTrue(this.activity.getGuidances().isEmpty()) ;
		assertFalse(g1.getActivities().contains(this.activity));
		assertFalse(g2.getActivities().contains(this.activity));
	}
}
