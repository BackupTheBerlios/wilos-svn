/*
 * WizardServicesTest.java
 * JUnit based test
 *
 * Created on 24 novembre 2006, 16:03
 */

package wilos.test.business.webservices;

import java.security.Security;
import javax.servlet.jsp.jstl.sql.Result;
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
import wilos.business.services.process.ProcessService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.business.transfertobject.ParticipantTO;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import  wilos.business.webservices.WizardServices;
import wilos.model.spem2.task.TaskDescriptor;

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
    /*public void testGetAllProcess() {
        System.out.println("getAllProcess");
        System.out.println("testBD");
        WizardServices instance = new WizardServices();
        
        Process proc = new Process();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");

        ProcessService p = (ProcessService)ctx.getBean("ProcessService");
        
        p.saveProcess(proc);
        
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
        
    }*/
    
     public void testGetParticipant() {
        System.out.println("getAllProcess");
        System.out.println("testBD");
        
        WizardServices instance = new WizardServices();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        ParticipantService ps = (ParticipantService)ctx.getBean("ParticipantService");
        
        Participant p = new Participant();
        p.setLogin("test");
        p.setPassword("testtest");
        p.setName("test");
        p.setEmailAddress("test@test.com");
        p.setFirstname("test");
        
        RoleDescriptor rd = new RoleDescriptor();
        rd.setName("testRole");
        
        TaskDescriptor td = new TaskDescriptor();
        td.setName("testTask");
        
        rd.addPrimaryTask(td);
        p.addToRoleDescriptor(rd);
        
        ps.saveParticipant(p);
        
        
        
        String passCrypt = wilos.business.util.Security.encode("test");
        ParticipantTO result = null;
        try {
            
            result = instance.getParticipant("test", passCrypt);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertNotNull(result);
        assertEquals(result.getName(),"test");
        
        //assert
     }
    
}
