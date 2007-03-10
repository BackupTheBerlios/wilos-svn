package wilos.test.hibernate.spem2.task;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
		wilos.test.hibernate.spem2.task.TaskDescriptorDaoTest.class,
		wilos.test.hibernate.spem2.task.TaskDefinitionDaoTest.class,
		wilos.test.hibernate.spem2.task.StepDaoTest.class })
public class TestSuite {
	// None.
}
