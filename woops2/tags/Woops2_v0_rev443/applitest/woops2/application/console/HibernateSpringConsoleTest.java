package woops2.application.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.hibernate.activity.ActivityDao;
import woops2.model.activity.Activity;

/**
 * Application for testing hibernate and spring configuration with small model
 * @author garwind
 */
public class HibernateSpringConsoleTest {

	
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// Show what is in the factory
		System.out.println("factory => "+ctx);
		// Getback the hibernateTemplate bean
		org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");
		System.out.println("HibTemplate => "+hibTempl);
		// Get the ActivityDao Singleton for managing Activity data
		ActivityDao dao = (ActivityDao) ctx.getBean("ActivityDao");
		// Create empty Activity
		Activity a = new Activity();
		// Save it
		dao.saveOrUpdateActivity(a);
	}

}
