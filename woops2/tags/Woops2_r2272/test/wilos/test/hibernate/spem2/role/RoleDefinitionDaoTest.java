package wilos.test.hibernate.spem2.role;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.model.spem2.role.RoleDefinition;
import wilos.test.TestConfiguration;

/**
 * Unit test for RoleDefinitionDao
 * 
 * @author Soosuske
 */
public class RoleDefinitionDaoTest extends TestCase {
	
	private RoleDefinitionDao roleDefinitionDao = null;

	private RoleDefinition roleDefinition = null;

	public static final String NAME = "thisRole";

	public static final String DESCRIPTION = "roleDefinition description";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Get the RoleDefinitionDao Singleton for managing RoleDefinition data
		this.roleDefinitionDao = (RoleDefinitionDao) TestConfiguration.getInstance().getApplicationContext().getBean("RoleDefinitionDao");

		// Create empty RoleDefinition
		this.roleDefinition = new RoleDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		this.roleDefinitionDao.deleteRoleDefinition(this.roleDefinition);
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.role.RoleDefinitionDao#saveOrUpdateRole(wilos.model.spem2.role.RoleDefinition)}.
	 * 
	 * PRINCIPLE Create a tmp roleDefinition, save it into the database with the method to
	 * test. Then look for the database to check if this tmp roleDefinition exists. To
	 * finish delete this tmp roleDefinition from the database.
	 */
	public void testSaveOrUpdateRole() {
		// Rk: the setUp method is called here.

		// Save the roleDefinition with the method to test.
		this.roleDefinitionDao.saveOrUpdateRoleDefinition(this.roleDefinition);

		// Check the saving.
		String id = roleDefinition.getId();
		RoleDefinition roleTmp = (RoleDefinition) this.roleDefinitionDao.getHibernateTemplate().load(
				RoleDefinition.class, id);
		assertNotNull(roleTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.role.RoleDefinitionDao#getAllRole()}.
	 * 
	 * PRINCIPLE Create a tmp roleDefinition, save it into the database. Then get all
	 * roles from the database with the method to test, and look if the size of
	 * the roles set got is >= 1. To finish delete this tmp roleDefinition from the
	 * database.
	 */
	public void testGetAllRole() {
		// Rk: the setUp method is called here.
		// Save the roleDefinition into the database.
		this.roleDefinitionDao.getHibernateTemplate().saveOrUpdate(this.roleDefinition);

		// Look if this roleDefinition is also into the database and look if the size of
		// the set is >= 1.
		List<RoleDefinition> roleDefinitions = this.roleDefinitionDao.getAllRoleDefinitions();
		assertNotNull(roleDefinitions);
		assertTrue(roleDefinitions.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.role.RoleDefinitionDao#getRole()}.
	 * 
	 */
	public void testGetRole() {
		// Rk: the setUp method is called here.

		// Add prooperties to the roleDefinition.
		this.roleDefinition.setName(NAME);
		this.roleDefinition.setDescription(DESCRIPTION);

		// Save the roleDefinition into the database.
		this.roleDefinitionDao.getHibernateTemplate().saveOrUpdate(this.roleDefinition);
		String id = this.roleDefinition.getId();

		// Test the method getActivity with an existing activity.
		RoleDefinition roleTmp = this.roleDefinitionDao.getRoleDefinition(id);
		assertNotNull(roleTmp);
		assertEquals("Name", roleTmp.getName(), NAME);
		assertEquals("Description", roleTmp.getDescription(), DESCRIPTION);

		// Test the method getRole with an unexisting roleDefinition.
		this.roleDefinitionDao.getHibernateTemplate().delete(roleDefinition);
		roleTmp = this.roleDefinitionDao.getRoleDefinition(id);
		assertNull(roleTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.role.RoleDefinitionDao#deleteRole()}.
	 * 
	 */
	public void testDeleteRole() {
		// Rk: the setUp method is called here.

		// Save the roleDefinition into the database.
		this.roleDefinitionDao.getHibernateTemplate().saveOrUpdate(this.roleDefinition);
		String id = this.roleDefinition.getId();

		// Test the method deleteRole with an roleDefinition existing into the db.
		this.roleDefinitionDao.deleteRoleDefinition(this.roleDefinition);

		// See if this.role is now absent in the db.
		RoleDefinition roleTmp = (RoleDefinition) this.roleDefinitionDao.getHibernateTemplate().get(
				RoleDefinition.class, id);
		assertNull(roleTmp);

		// Test the method deleteRole with an roleDefinition unexisting into the db.
		// Normally here there are no exception thrown.
		this.roleDefinitionDao.deleteRoleDefinition(this.roleDefinition);

		// Rk: the tearDown method is called here.
	}
}