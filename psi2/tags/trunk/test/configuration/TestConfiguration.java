package configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Martial
 *
 * This class represents ... TODO
 *
 */
public class TestConfiguration {

	// Getback the application context from the spring configuration file
	private ApplicationContext applicationContext = null;

	private static TestConfiguration testConfiguration = null;

	private TestConfiguration() {
		this.applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}

	public static TestConfiguration getInstance() {
		if (testConfiguration == null)
			testConfiguration = new TestConfiguration();
		return testConfiguration;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
}
