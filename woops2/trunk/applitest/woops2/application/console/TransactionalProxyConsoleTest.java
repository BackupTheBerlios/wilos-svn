package woops2.application.console;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import woops2.business.activity.ActivityService;
import woops2.business.process.ProcessService;

public class TransactionalProxyConsoleTest {

	
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		//XmlBeanFactory factory = new XmlBeanFactory(res);
		ActivityService am = (ActivityService) ctx.getBean("ActivityService");
		
		ProcessService pcsServ = (ProcessService) ctx.getBean("ProcessService");
		
		pcsServ.TestSpelpParsingXML();
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
