package woops2.application.console;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.business.activity.ActivityManager;

public class TransactionalProxyConsoleTest {

	
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		//XmlBeanFactory factory = new XmlBeanFactory(res);
		ActivityManager am = (ActivityManager) ctx.getBean("ActivityManager");
		//System.out.println("ActivityManager="+am);
		
		/*
		Activity a = new Activity();
		a.setPrefix("test");
		am.saveActivity(a);
		*/
		
		/*
		Activity a = am.getActivityFromPrefix("test");
		BreakdownElement b = new BreakdownElement();
		am.getActivityDao().getHibernateTemplate().save(b);
		a.getBreakDownElements().add(b);
		am.saveActivity(a);
		System.out.println("size="+a.getBreakDownElements().size());
		*/
		
		am.Test();
		
	}

}
