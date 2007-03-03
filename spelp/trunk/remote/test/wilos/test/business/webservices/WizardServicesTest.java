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

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.util.Security;
import wilos.business.webservices.WizardServices;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author toine
 */
public class WizardServicesTest extends TestCase {
	private LoginService ls;
	private ParticipantService ps;
	private WizardServices instance;
	private ConcreteTaskDescriptor ct;
	private ConcreteTaskDescriptorService cts;
	private Participant p;
    
    public WizardServicesTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
		super.setUp();
		ls = (LoginService) TestConfiguration.getInstance().getApplicationContext().getBean("LoginService");
		ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService");
		cts = (ConcreteTaskDescriptorService) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteTaskDescriptorService");
		
		instance = (WizardServices) TestConfiguration.getInstance().getApplicationContext().getBean("WizardServices");
	 
	 	ct = new ConcreteTaskDescriptor();
		ct.setConcreteName("ConcreteTest");
		ct.setState(Constantes.State.READY);
		ct.setTaskDescriptor(null);
		cts.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(ct);
		
		p = new Participant();
		
		p.setLogin("testJunit");
	    p.setPassword(Security.encode("testJunit"));
	    p.setName("testJunit");
	    p.setEmailAddress("test@test.com");
	    p.setFirstname("testJunit");
	    ps.getParticipantDao().saveOrUpdateParticipant(p);
    }

    protected void tearDown() throws Exception {
    	super.tearDown();
		cts.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(ct);
		ps.getParticipantDao().deleteParticipant(p);         
    }  
     
    public void testGetParticipantException() {
         System.out.println("GetParticipant");
         System.out.println("testException");    	
         
         try {             
        	instance.getParticipant("qsdfqsdfqsdf", "dfqsdfqs");            
            fail();          
         } catch (Exception ex) {
            assertTrue(true);
         }
         
     }
    
     public void testGetParticipant() {
        System.out.println("GetParticipant");
        System.out.println("testBD");	
        
     
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
        assertEquals(pt.getName(),p.getName());
     }
    
}
