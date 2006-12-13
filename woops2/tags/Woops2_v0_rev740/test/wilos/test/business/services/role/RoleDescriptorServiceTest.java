/**
 * 
 */
package wilos.test.business.services.role;

import junit.framework.TestCase;
import wilos.business.services.role.RoleDescriptorService;
import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.test.TestConfiguration;

/**
 * @author Administrateur
 *
 */
public class RoleDescriptorServiceTest extends TestCase {
	
	private RoleDescriptorService roleDescriptorService;
	
	private RoleDescriptor roleDescriptor;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityService Singleton for managing Activity data
		this.roleDescriptorService = (RoleDescriptorService) TestConfiguration.getInstance().getApplicationContext().getBean("RoleDescriptorService") ;

		// Create empty Activity
		this.roleDescriptor = new RoleDescriptor() ;

	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		

		// Delete the tmp activity from the database.
		//this.roleDescriptorService.getRoleDescriptorDao().deleteRoleDescriptor(this.roleDescriptor) ;
	}

	/**
	 * Test method for {@link wilos.business.services.role.RoleDescriptorService#getRoleDescriptorsFromProcess(java.lang.String)}.
	 */
	public void testGetRoleDescriptorsFromProcess() {
		
		fail("Not yet implemented") ;
	}

	/**
	 * Test method for {@link wilos.business.services.role.RoleDescriptorService#getRoleDescriptorDao()}.
	 */
	public void testGetRoleDescriptorDao() {
//		 Rk: the setUp method is called here.

		// Add prooperties to the roleDescriptor.
		this.roleDescriptor.setName("name");
		this.roleDescriptor.setDescription("description");
		this.roleDescriptor.setPrefix("prefix");
		this.roleDescriptor.setHasMultipleOccurrences(true);
		this.roleDescriptor.setIsOptional(true);
		this.roleDescriptor.setIsPlanned(true);
		
		RoleDefinition role = new RoleDefinition();
		role.setName("pouette!");
		
		RoleDefinitionDao rdefdao = (RoleDefinitionDao) TestConfiguration.getInstance().getApplicationContext().getBean("RoleDefinitionDao");
		rdefdao.saveOrUpdateRoleDefinition(role);
		
		String idRole = role.getId();
		System.out.println("######## C'est moi : " + idRole) ;
		assertNotNull(idRole);
		
		this.roleDescriptor.addRoleDefinition(role);

		// Save the roleDescriptor into the database.
		this.roleDescriptorService.getRoleDescriptorDao().saveOrUpdateRoleDescriptor(this.roleDescriptor);
		String id = this.roleDescriptor.getId();
		assertNotNull(id);
		assertNotNull(this.roleDescriptor.getRoleDefinition().getId());

		// Test the method getRoleDescriptor with an existing roleDescriptor.
		RoleDescriptor roleDescriptorTmp = this.roleDescriptorService.getRoleDescriptorDao()
				.getRoleDescriptor(id);
		assertNotNull(roleDescriptorTmp);
		System.out.println("######## C'est toi : " + roleDescriptorTmp.getRoleDefinition().getId()) ;
		
		// Test the method getRoleDescriptor with an unexisting roleDescriptor.
		this.roleDescriptorService.getRoleDescriptorDao().getHibernateTemplate().delete(roleDescriptor);
		roleDescriptorTmp = this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor(id);
		assertNull(roleDescriptorTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.business.services.role.RoleDescriptorService#setRoleDescriptorDao(wilos.hibernate.spem2.role.RoleDescriptorDao)}.
	 */
	public void testSetRoleDescriptorDao() {
		fail("Not yet implemented") ;
	}

}
