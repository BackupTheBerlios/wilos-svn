package wilos.test.business.services;

import junit.framework.TestCase;
import wilos.business.services.assistant.AssistantService;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.util.Security;
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
	private Participant p;

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
		cts.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(ct);
		
		p = new Participant();
		
		p.setLogin("test");
	    p.setPassword(Security.encode("testtest"));
	    p.setName("test");
	    p.setEmailAddress("test@test.com");
	    p.setFirstname("test");
	    ps.getParticipantDao().saveOrUpdateParticipant(p);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		cts.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(ct);
		ps.getParticipantDao().deleteParticipant(p);         
	}

	public void testGetParticipantTO() {
		System.out.println("GetParticipant");  		
        
        try {             
        	assistantService.getParticipantTO("n'importe quoi");            
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
		cts.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ct.getId());
		assertEquals(Constantes.State.STARTED, ct.getState());		
	}
	
	public void testSuspendConcreteTaskDescriptor() {		
		assistantService.suspendConcreteTaskDescriptor(ct.getId());	
		ct = cts.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ct.getId());
		assertEquals(Constantes.State.SUSPENDED, ct.getState());		
	}
	
	public void testFinishConcreteTaskDescriptor() {		
		assistantService.finishConcreteTaskDescriptor(ct.getId());	
		ct = cts.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ct.getId());
		assertEquals(Constantes.State.FINISHED, ct.getState());		
	}
	
	public void testResumeConcreteTaskDescriptor() {		
		assistantService.resumeConcreteTaskDescriptor(ct.getId());	
		ct = cts.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ct.getId());
		assertEquals(Constantes.State.STARTED, ct.getState());		
	}
}
