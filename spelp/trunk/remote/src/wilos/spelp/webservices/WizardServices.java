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
//    @WebMethod
//	public List<RoleDescriptor> getRolesByUser (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
//		List<RoleDescriptor> r = new ArrayList<RoleDescriptor>();
//		if(login.equals("toto") && password.equals("toto")) {
//			ProcessService p = new ProcessService();
//			List<Process> lp = p.getProcessesList(); 
//			
//			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
//			for (Process pr : lp) {
//				bdes = pr.getBreakDownElements();
//				for (BreakdownElement bd : bdes) {
//					if (bd instanceof RoleDescriptor) {
//						r.add((RoleDescriptor)bd);
//					}
//				}
//			}
//		}
//		return r;
//	}
//    
//     @WebMethod
//	public List<String> getRolesByUserXstream (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
//                XStream xstream = new XStream();
//		List<String> r = new ArrayList<String>();
////		if(login.equals("toto") && password.equals("toto")) {
////			ProcessService p = new ProcessService();
////			List<Process> lp = p.getProcessesList(); 
////			
////			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
////			for (Process pr : lp) {
////				bdes = pr.getBreakDownElements();
////				for (BreakdownElement bd : bdes) {
////					if (bd instanceof RoleDescriptor) {
////						r.add(xstream.toXML((RoleDescriptor)bd));
////					}
////				}
////			}
////		}
//                RoleDescriptor rd = new RoleDescriptor();
//                rd.setName("TestRoleDescriptor");
//                TaskDescriptor td = new TaskDescriptor();
//                td.setMainRole(rd);
//                td.setName("TestTaskDescriptor");
//                rd.addPrimaryTask(td);
//
//                r.add(xstream.toXML(rd));
//                return r;
//	}
    
    @WebMethod
    public List<String> getAllProcess (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
        List<String> result = new ArrayList<String>();
        if(login.equals("testBD") && password.equals("testBD")) {
            List<Process> lp = new ArrayList<Process>();
            // Getback the application context from the spring configuration file
            ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
            // Show what is in the factory
            System.out.println("factory => "+ctx);
            // Getback the hibernateTemplate bean
            org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");
            System.out.println("HibTemplate => "+hibTempl);
            // Get the ProcessService Singleton for managing Activity data   
            ProcessService p = (ProcessService)ctx.getBean("ProcessService");
            lp = p.getProcessesList();
            XStream xstream = new XStream();
            for (Process pro : lp) {
                result.add(xstream.toXML(pro));
            }          
        } else {
            if(login.equals("testSansBD") && password.equals("testSansBD")) {
                ArrayList<RoleDescriptor> myRoleListe;
		RoleDescriptor aTmpRole;
		TaskDescriptor aTmpTask;
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		myRoleListe = new ArrayList<RoleDescriptor>();
                Process pro = new Process();
		
		aTmpRole.setName("Developper");
		aTmpRole.setDescription("Un gars qui développe");
		
		aTmpTask.setName("Coder le programme");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Aimer son programme");
		aTmpTask.setDescription("Un grand moment d'amour");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Passer le balai");
		aTmpTask.setDescription("Et c'est plus propre");
		aTmpRole.addPrimaryTask(aTmpTask);
		
                pro.addBreakdownElement(aTmpRole);	
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		aTmpRole.setName("Tester");
		aTmpRole.setDescription("Faire des essais, en gros");
		aTmpTask.setName("Tester le programme");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Detester le programme");
		aTmpTask.setDescription("Un grand moment de haine");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Passer la serpillière");
		aTmpTask.setDescription("Un grand moment de solitude");
		aTmpRole.addPrimaryTask(aTmpTask);
		
		pro.addBreakdownElement(aTmpRole);	
		
		aTmpRole = new RoleDescriptor();
		aTmpTask = new TaskDescriptor();
		aTmpRole.setName("Conceptualisateur");
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Conceptualiser les concepts du programme");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Rever du programme");
		aTmpRole.addPrimaryTask(aTmpTask);
		aTmpTask = new TaskDescriptor();
		aTmpTask.setName("Faire le café concept");
		aTmpRole.addPrimaryTask(aTmpTask);		
		pro.addBreakdownElement(aTmpRole);
                XStream xstream = new XStream();
                result.add(xstream.toXML(pro));
            }
        }
        return result;
    }
}
