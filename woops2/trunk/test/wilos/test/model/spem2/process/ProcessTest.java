package wilos.test.model.spem2.process;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;

/**
 * @author soosuske
 * @author deder
 * 
 */
public class ProcessTest extends TestCase {

	private Process process;

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public static final String NAME = "thisRoleDescriptor";

	public static final String NAME2 = "name1";

	public static final String DESCRIPTION = "roleDescriptor description";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.process = new Process();
		this.process.setPrefix(PREFIX);
		this.process.setIsOptional(IS_OPTIONAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link wilos.model.spem2.process.Process#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		Process proc = new Process();
		proc.setPrefix(PREFIX);
		proc.setIsOptional(IS_OPTIONAL);

		assertNotNull(this.process.hashCode());
		assertNotNull(proc.hashCode());
		assertEquals(this.process.hashCode(), proc.hashCode());

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.process.Process#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.process.equals(this.process));

		// Assert if it's equal field by field.
		Process processTmp1 = null;
		try {
			processTmp1 = this.process.clone();
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testEquals method");
		}
		assertTrue("Field by field", this.process.equals(processTmp1));

		// Assert if it's not equal.
		Process procTmp2 = new Process();
		procTmp2.setPrefix("prefixFalse");
		procTmp2.setIsOptional(true);
		assertFalse("Not equals", this.process.equals(procTmp2));

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.process.Process#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try {
			assertEquals(this.process.clone(), this.process);
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}

		// Rk: the tearDown method is called here.
	}

	public void testRemoveProjects() {
		Project proj = new Project();
		proj.setName(NAME);
		proj.setDescription(DESCRIPTION);

		this.process.addProject(proj);
		assertFalse(this.process.getProjects().isEmpty());

		this.process.removeProject(proj);
		assertTrue(this.process.getProjects().isEmpty());

	}

	public void testAddProject() {
		Project proj = new Project();
		proj.setName(NAME);
		proj.setDescription(DESCRIPTION);

		this.process.addProject(proj);
		assertFalse(this.process.getProjects().isEmpty());
		assertTrue(this.process.getProjects().size() == 1);

	}

	public void testRemoveAllProject() {
		Project proj = new Project();
		proj.setName(NAME);
		proj.setDescription(DESCRIPTION);

		Project tmp = new Project();
		tmp.setName(NAME2);
		//tmp.setDescription(DESCRIPTION);

		Set<Project> set = new HashSet<Project>();
		set.add(proj);
		set.add(tmp);

		this.process.addAllProjects(set);
		assertNotNull(proj.getProcess());
		assertNotNull(tmp.getProcess());
		assertTrue(this.process.getProjects().size() == set.size());

		this.process.removeAllProjects();
		assertNull(proj.getProcess());
		assertNull(tmp.getProcess());
		assertTrue(this.process.getProjects().isEmpty());
	}

	
	public void testAddToAllProjects() {
		Project proj = new Project();
		proj.setName(NAME);
		proj.setDescription(DESCRIPTION);

		Project tmp = new Project();
		tmp.setName(NAME2);
		tmp.setDescription(DESCRIPTION);

		Set<Project> set = new HashSet<Project>();
		set.add(proj);
		set.add(tmp);

		this.process.addAllProjects(set);

		assertFalse(this.process.getProjects().isEmpty());
		assertTrue(this.process.getProjects().size() == 2);
		assertNotNull(proj.getProcess());
		assertNotNull(tmp.getProcess());
	}
	
	
	public void testAddToProcessManager() {
		ProcessManager processManager = new ProcessManager() ;
		processManager.setName(NAME) ;

		this.process.addProcessManager(processManager) ;

		assertNotNull(this.process.getProcessManager()) ;
		assertTrue(processManager.getProcessesManaged().size() == 1) ;
	}
	
	public void testRemoveFromProcessManager() {
		ProcessManager processManager = new ProcessManager() ;
		processManager.setName(NAME) ;

		this.process.addProcessManager(processManager) ;
		assertNotNull("null", this.process.getProcessManager()) ;
		assertTrue("empty", processManager.getProcessesManaged().size() == 1) ;
		
		this.process.removeFromProcessManager(processManager);
		assertNull("null", this.process.getProcessManager()) ;
		assertTrue("empty", processManager.getProcessesManaged().isEmpty()) ;
	}

}
