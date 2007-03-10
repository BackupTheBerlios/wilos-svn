/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

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
           fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
        
        Participant pt = null;
        try {             
        	pt = assistantService.getParticipantTO(p.getLogin());            
        } catch (Exception ex) {
            fail();
        }
        assertNotNull(pt);
        assertEquals(p.getLogin(),pt.getLogin());
	}

	public void testGetParticipantDao() {
		assertNotNull(assistantService.getParticipantDao());
	}

	public void testStartConcreteTaskDescriptor() {		
		assistantService.startConcreteTaskDescriptor(ct.getId());
		ct = cts.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ct.getId());
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
