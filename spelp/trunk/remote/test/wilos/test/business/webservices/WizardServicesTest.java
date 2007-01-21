/*
 * WizardServicesTest.java
 * JUnit based test
 *
 * Created on 24 novembre 2006, 16:03
 */

package wilos.test.business.webservices;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.business.util.Security;
import wilos.business.webservices.WizardServices;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

import com.thoughtworks.xstream.XStream;

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
    
     public void testGetParticipantSansBD() {
        System.out.println("GetParticipant");
        System.out.println("testSansBD");
        
        WizardServices instance = new WizardServices();
        
        String passCrypt = wilos.business.util.Security.encode("test");
        Participant pt = null;
        String result = null;
        try {
            
            result = instance.getParticipant("testSansBD", passCrypt);
            XStream xstream = new XStream();     
            pt = (Participant)xstream.fromXML(result);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertNotNull(result);
        assertEquals(pt.getName(),"testSansBD");
     }
     
     public void testGetParticipantException() {
         System.out.println("GetParticipant");
         System.out.println("testException");
         
         WizardServices instance = new WizardServices();

         LoginService ls = (LoginService) TestConfiguration.getInstance().getApplicationContext().getBean("LoginService");

         ParticipantService ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService");
         
    	 Participant p = new Participant();
         p.setLogin("test");
         p.setPassword(Security.encode("testtest"));
         p.setName("test");
         p.setEmailAddress("test@test.com");
         p.setFirstname("test");
         
         if (ls.getAuthentifiedUser(p.getLogin(), p.getPassword()) != null) 
     		ps.getParticipantDao().deleteParticipant(p);         
         
         try {             
        	 instance.getParticipant(p.getLogin(), p.getPassword());            
            assertFalse(true);
         } catch (Exception ex) {
             assertTrue(true);
         }
         
     }
    
     public void testGetParticipant() {
        System.out.println("GetParticipant");
        System.out.println("testBD");
        
        WizardServices instance = new WizardServices();     
        
        LoginService ls = (LoginService) TestConfiguration.getInstance().getApplicationContext().getBean("LoginService");

        ParticipantService ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService");
        

        		
        Participant p = new Participant();
        p.setLogin("test");
        p.setPassword(Security.encode("testtest"));
        p.setName("test");
        p.setEmailAddress("test@test.com");
        p.setFirstname("test");
        
        /*RoleDescriptor rd = new RoleDescriptor();
        rd.setName("testRole");
        
        TaskDescriptor td = new TaskDescriptor();
        td.setName("testTask");
        
        rd.addPrimaryTask(td);
        p.addToRoleDescriptor(rd);*/
        
        if (ls.getAuthentifiedUser(p.getLogin(), p.getPassword()) != null) 
    		ps.getParticipantDao().deleteParticipant(p);
      
        ps.getParticipantDao().saveOrUpdateParticipant(p);

        Participant pt = null;
        String result = null;
        try {
            
            result = instance.getParticipant(p.getLogin(), p.getPassword());
            XStream xstream = new XStream();     
            pt = (Participant)xstream.fromXML(result);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertNotNull(result);
        assertNotNull(pt);
        assertEquals(pt.getName(),"test");
        
        if (ls.getAuthentifiedUser(p.getLogin(), p.getPassword()) != null) 
    		ps.getParticipantDao().deleteParticipant(p);

     }
    
}
