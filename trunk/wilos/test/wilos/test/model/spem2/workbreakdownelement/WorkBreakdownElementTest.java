/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package wilos.test.model.spem2.workbreakdownelement;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;

public class WorkBreakdownElementTest {

	private WorkBreakdownElement workBreakdownElement;

	private String LINK_TYPE = "link type";

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

	@Before
	public void setUp() {
		//None.
	}

	@After
	public void tearDown() {
		this.workBreakdownElement.getPredecessors().clear();
		this.workBreakdownElement.getSuccessors().clear();
	}

	@Test
	public void testClone() {
		try {
			assertEquals(this.workBreakdownElement, this.workBreakdownElement.clone());
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}

	@Test
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		assertNotNull(this.workBreakdownElement.hashCode());
		assertNotNull(workBreakdownElement.hashCode());
		assertEquals(this.workBreakdownElement.hashCode(),workBreakdownElement.hashCode());

		// Rk: the tearDown method is called here.
	}

	@Test
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

	@Test
	public final void testAddConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp = new ConcreteWorkBreakdownElement();
		tmp.setConcreteName("Concrete Name");
		this.workBreakdownElement.addConcreteWorkBreakdownElement(tmp);

		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp));
		assertTrue(tmp.getWorkBreakdownElement().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testRemoveConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		ConcreteWorkBreakdownElement tmp = new ConcreteWorkBreakdownElement();
		this.workBreakdownElement.addConcreteBreakdownElement(tmp);
		this.workBreakdownElement.removeConcreteWorkBreakdownElement(tmp);

		assertFalse(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp));
		assertFalse(this.workBreakdownElement.equals(tmp.getWorkBreakdownElement()));

		// Rk: the tearDown method is called here.
	}

	@Test
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

		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().size() >= 2);
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp1));
		assertTrue(this.workBreakdownElement.getConcreteWorkBreakdownElements().contains(tmp2));
		assertTrue(tmp1.getWorkBreakdownElement().equals(this.workBreakdownElement));
		assertTrue(tmp2.getWorkBreakdownElement().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
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
		assertFalse(this.workBreakdownElement.equals(tmp1.getWorkBreakdownElement()));
		assertFalse(this.workBreakdownElement.equals(tmp2.getWorkBreakdownElement()));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testAddSuccessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addSuccessor(tmp);

		assertTrue(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertTrue(tmp.getPredecessor().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testAddPredecessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addPredecessor(tmp);

		assertTrue(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertTrue(tmp.getSuccessor().equals(this.workBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testRemoveSuccessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addSuccessor(tmp);
		this.workBreakdownElement.removeSuccessor(tmp);

		assertFalse(this.workBreakdownElement.getSuccessors().contains(tmp));
		assertFalse(this.workBreakdownElement.equals(tmp.getPredecessor()));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testRemovePredecessor() {
		// Rk: the setUp method is called here.

		WorkOrder tmp = new WorkOrder();
		this.workBreakdownElement.addPredecessor(tmp);
		this.workBreakdownElement.removePredecessor(tmp);

		assertFalse(this.workBreakdownElement.getPredecessors().contains(tmp));
		assertFalse(this.workBreakdownElement.equals(tmp.getSuccessor()));

		// Rk: the tearDown method is called here.
	}

	@Test
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

	@Test
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

	@Test
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

	@Test
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
}
