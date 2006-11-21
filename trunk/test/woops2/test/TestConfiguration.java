
package woops2.test ;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author deder
 * 
 * This class represents the configuration for unit tests of this project (by using spring
 * framework). Each TestCase must extend this class to use its protected properties.
 * 
 */
public class TestConfiguration extends TestCase {

	// Getback the application context from the spring configuration file
	// FIXME pb : recharge le context pour chaque test ... faut trouver un moyen plus rapide ?
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	

}
