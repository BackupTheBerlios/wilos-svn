/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.spelp.webservices;

import com.thoughtworks.xstream.XStream;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import woops2.business.process.ProcessService;
import woops2.model.activity.Activity;
import woops2.model.role.RoleDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;
import woops2.model.task.TaskDescriptor;

/**
 *
 * @author toine 
 */
@WebService()
public class WizardServices {
    @WebMethod
	public List<RoleDescriptor> getRolesByUser (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
		List<RoleDescriptor> r = new ArrayList<RoleDescriptor>();
		if(login.equals("toto") && password.equals("toto")) {
			ProcessService p = new ProcessService();
			List<Process> lp = p.getProcessesList(); 
			
			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
			for (Process pr : lp) {
				bdes = pr.getBreakDownElements();
				for (BreakdownElement bd : bdes) {
					if (bd instanceof RoleDescriptor) {
						r.add((RoleDescriptor)bd);
					}
				}
			}
		}
		return r;
	}
    
     @WebMethod
	public List<String> getRolesByUserXstream (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
                XStream xstream = new XStream();
		List<String> r = new ArrayList<String>();
//		if(login.equals("toto") && password.equals("toto")) {
//			ProcessService p = new ProcessService();
//			List<Process> lp = p.getProcessesList(); 
//			
//			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
//			for (Process pr : lp) {
//				bdes = pr.getBreakDownElements();
//				for (BreakdownElement bd : bdes) {
//					if (bd instanceof RoleDescriptor) {
//						r.add(xstream.toXML((RoleDescriptor)bd));
//					}
//				}
//			}
//		}
                RoleDescriptor rd = new RoleDescriptor();
                rd.setName("TestRoleDescriptor");
                TaskDescriptor td = new TaskDescriptor();
                td.setMainRole(rd);
                td.setName("TestTaskDescriptor");
                rd.addPrimaryTask(td);

                r.add(xstream.toXML(rd));
                return r;
	}
    
    @WebMethod
    public List<Process> getAllProcess () {
    	        List<Process> lp = new ArrayList<Process>();
                
                 // Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
                // Show what is in the factory
		System.out.println("factory => "+ctx);
                // Getback the hibernateTemplate bean
		org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");
                System.out.println("HibTemplate => "+hibTempl);
                // Get the ActivityDao Singleton for managing Activity data
		//ActivityDao dao = (ActivityDao) ctx.getBean("ActivityDao");
                
                ProcessService p = (ProcessService)ctx.getBean("ProcessService");

            // Create empty Activity
		//Activity a = new Activity();
            // Save it
		//dao.saveOrUpdateActivity(a);
            
             lp = p.getProcessesList();

		return lp;
    }
}
