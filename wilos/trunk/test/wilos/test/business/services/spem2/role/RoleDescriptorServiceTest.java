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
package wilos.test.business.services.spem2.role;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;

public class RoleDescriptorServiceTest {

	private RoleDescriptor roleDescriptor;
	
	private RoleDescriptorDao roleDescriptorDao;
	
	private RoleDescriptorService roleDescriptorService;
	
	public RoleDescriptorServiceTest() {
		// Get the ActivityDao Singleton for managing Activity data
		this.roleDescriptorService = (RoleDescriptorService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("RoleDescriptorService");
		this.roleDescriptorDao = (RoleDescriptorDao) TestConfiguration.getInstance()
		.getApplicationContext().getBean("RoleDescriptorDao");
	}
	
	@Before
	public void setUp() {
		// None
		this.roleDescriptor = new RoleDescriptor();
	}

	@After
	public void tearDown() {
		// None
		this.roleDescriptorService.getRoleDescriptorDao().deleteRoleDescriptor(this.roleDescriptor);
	}
	
	@Test
	public void getAdditionalTasksTest() {
		TaskDescriptor matache = new TaskDescriptor();
		matache.setName("nom1");
		this.roleDescriptor.addAdditionalTask(matache);
		
		Set<TaskDescriptor> list = this.roleDescriptorService.getAdditionalTasks(this.roleDescriptor);
		
		assertTrue(list.size() == 1);
		assertTrue(list.contains(matache));
	}
	
	@Test
	public void getPrimaryTasksTest() {
		TaskDescriptor matache = new TaskDescriptor();
		matache.setName("nom1");
		this.roleDescriptor.addPrimaryTask(matache);
		
		Set<TaskDescriptor> list = this.roleDescriptorService.getPrimaryTasks(this.roleDescriptor);
		
		assertTrue(list.size() == 1);
		assertTrue(list.contains(matache));
		
	}
	
	@Test
	public void getRoleDescriptorById() {
		//FIXME
		/*this.roleDescriptorDao.saveOrUpdateRoleDescriptor(this.roleDescriptor);
		System.out.println("IDDDDDDDDD"+this.roleDescriptor.getId());
		RoleDescriptor rd = this.roleDescriptorService.getRoleDescriptorById(this.roleDescriptor.getId());
		assertTrue(this.roleDescriptor.equals(rd));
		this.roleDescriptorDao.deleteRoleDescriptor(this.roleDescriptor);*/
	}
}
