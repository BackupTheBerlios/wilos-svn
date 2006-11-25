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
     * Test of getAllProcess method, of class wilos.spelp.webservices.WizardServices.
     */
    public void testGetAllProcess() {
        System.out.println("getAllProcess");
        System.out.println("testBD");
        WizardServices instance = new WizardServices();
        
        Process proc = new Process();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");

        ProcessService p = (ProcessService)ctx.getBean("ProcessService");
        
        p.SaveProcessService(proc);
        
        String login = "testBD";
        String pass = "testBD";
        
        List<String> result = instance.getAllProcess(login,pass);
        
        assertNotNull(result);
        assertTrue(result.size()>=1);

        p.getProcessDao().deleteProcess(proc);
        
        login = "testSansBD";
        pass = "testSansBD";
        
        result = instance.getAllProcess(login,pass);
        
        assertNotNull(result);
        assertTrue(result.size()>=1);
        
    }
    
}
