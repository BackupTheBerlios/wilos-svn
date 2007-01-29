package wilos.test.hibernate.spem2.task;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author eperico
 *
 */
public class AllTests {
	
	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.task") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(TaskDescriptorDaoTest.class) ;
		suite.addTestSuite(TaskDefinitionDaoTest.class);
		suite.addTestSuite(StepDaoTest.class);
		//$JUnit-END$
		return suite ;
	}
}
