package wilos.test.business.services;

import junit.framework.TestCase;
import wilos.business.services.assistant.AssistantService;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.business.util.Security;
import wilos.business.webservices.WizardServices;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes;

public class AssistantServiceTest extends TestCase {
	
	private AssistantService assistantService ;
	private LoginService ls ;
	private ParticipantService ps ;
	private ConcreteTaskDescriptorService cts;
	private ConcreteTaskDescriptor ct;

	protected void setUp() throws Exception {
		super.setUp();
		ls = (LoginService) TestConfiguration.getInstance().getApplicationContext().getBean("LoginService");
   	 	ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService");
   	 	assistantService = (AssistantService) TestConfiguration.getInstance().getApplicationContext().getBean("AssistantService");
   	 	cts = (ConcreteTaskDescriptorService) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteTaskDescriptorService");
		ct = new ConcreteTaskDescriptor();
		ct.setConcreteName("ConcreteTest");
		ct.setState(Constantes.State.READY);
		ct.setTaskDescriptor(null);
		ct.setProjectId("projectId");
		cts.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(ct);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		cts.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(ct);
	}

	public void testGetParticipantTO() {
		System.out.println("GetParticipant");
        System.out.println("testException");
        
   	 Participant p = new Participant();
        p.setLogin("test");
        p.setPassword(Security.encode("testtest"));
        p.setName("test");
        p.setEmailAddress("test@test.com");
        p.setFirstname("test");
        
        if (ls.getAuthentifiedUser(p.getLogin(), p.getPassword()) != null) 
    		ps.getParticipantDao().deleteParticipant(p);         
        
        try {             
        	assistantService.getParticipantTO(p.getLogin());            
           assertFalse(true);
        } catch (Exception ex) {
            assertTrue(true);
        }
	}

	public void testGetParticipantDao() {
		assertNotNull(assistantService.getParticipantDao());
	}

	public void testStartConcreteTaskDescriptor() {		
		assistantService.startConcreteTaskDescriptor(ct.getId());	
		assertEquals(Constantes.State.STARTED, ct.getState());		
	}
	
	public void testSuspendConcreteTaskDescriptor() {		
		assistantService.suspendConcreteTaskDescriptor(ct.getId());	
		assertEquals(Constantes.State.SUSPENDED, ct.getState());		
	}
	
	public void testFinishConcreteTaskDescriptor() {		
		assistantService.finishConcreteTaskDescriptor(ct.getId());	
		assertEquals(Constantes.State.FINISHED, ct.getState());		
	}
	
	public void testResumeConcreteTaskDescriptor() {		
		assistantService.resumeConcreteTaskDescriptor(ct.getId());	
		assertEquals(Constantes.State.FINISHED, ct.getState());		
	}
}
