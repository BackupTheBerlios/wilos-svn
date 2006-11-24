/*
 * WizardServicesTest.java
 * JUnit based test
 *
 * Created on 24 novembre 2006, 16:03
 */

package wilos.spelp.webservices;

import junit.framework.*;
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
import woops2.hibernate.activity.ActivityDao;
import woops2.hibernate.role.RoleDescriptorDao;
import woops2.model.role.RoleDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;

/**
 *
 * @author toine
 */
public class WizardServicesTest extends TestCase {
    
    public WizardServicesTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getRolesByUser method, of class wilos.spelp.webservices.WizardServices.
     */
    public void testGetRolesByUser() {
        System.out.println("getRolesByUser");
        
        String login = "";
        String password = "";
        WizardServices instance = new WizardServices();
        
        List<RoleDescriptor> result = instance.getRolesByUser(login, password);
        assertNotNull(result);
        assertTrue(result.size()==0);
        
        login = "toto";
        password = "toto";
        
        Process proc = new Process();
        RoleDescriptor r = new RoleDescriptor();
        r.setName("TestRole");
        
        proc.addToBreakdownElement(r);
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");

        ProcessService p = (ProcessService)ctx.getBean("ProcessService");
        
        p.saveActivity(proc);
        
        result = instance.getRolesByUser(login, password);
        assertNotNull(result);
        assertTrue(result.size()!=0); 
        r = result.get(0);
        assertTrue(r.getName()== "TestRole");
        
        RoleDescriptorDao rDAO = (RoleDescriptorDao)ctx.getBean("RoleDescriptorDao");
        rDAO.deleteRoleDescriptor(r);
        
        p.getprocessDao().deleteProcess(proc);
        
        
        
        
        
        
    }

    /**
     * Test of getAllProcess method, of class wilos.spelp.webservices.WizardServices.
     */
    public void testGetAllProcess() {
        System.out.println("getAllProcess");
        
        WizardServices instance = new WizardServices();
        
        Process proc = new Process();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");

        ProcessService p = (ProcessService)ctx.getBean("ProcessService");
        
        p.saveActivity(proc);

        
        List<Process> result = instance.getAllProcess();
        
        assertNotNull(result);
        assertTrue(result.size()>=1);
        
        p.getprocessDao().deleteProcess(proc);
        
    }
    
}
