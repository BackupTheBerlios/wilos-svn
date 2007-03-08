/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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

package wilos.test.model.spem2 ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.model.spem2.element.TestSuite.class,
	wilos.test.model.spem2.activity.TestSuite.class,
	wilos.test.model.spem2.breakdownelement.TestSuite.class,
	wilos.test.model.spem2.process.TestSuite.class,
	wilos.test.model.spem2.role.TestSuite.class,
	wilos.test.model.spem2.task.TestSuite.class,
	wilos.test.model.spem2.workbreakdownelement.TestSuite.class,
	wilos.test.model.spem2.phase.TestSuite.class,
	wilos.test.model.spem2.iteration.TestSuite.class
	})
public class TestSuite {
	// None.
}
