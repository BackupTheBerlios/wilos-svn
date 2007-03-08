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

package wilos.application.console;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.process.Process;

public class InitAppliTest {
	public static void main(String[] args) {
		importXML();
		instanciation();
		affectation();	    
	}
	
	
	private static void importXML() {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProcessManagerService pser = (ProcessManagerService) ctx.getBean("ProcessManagerService");
		ProcessManager pm = new ProcessManager();
		pm.setLogin("pmtest");
		pm.setPassword("blabla");
		pm.setName("test");
		pser.saveProcessManager(pm);
		WilosUser wu  = pser.getProcessManagers().iterator().next();
		ProcessService am = (ProcessService) ctx.getBean("ProcessService");
		Process scrum = am.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		am.saveProcess(scrum,wu.getWilosuser_id());
		
		Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		//Process openup = am.spelpParsingXML(new File("applitest/wilos/application/console/openup.zip"));
		am.saveProcess(openup,wu.getWilosuser_id());
		
	}
	
	private static void instanciation() {		
//		 Notre fabrique SPRING permettant l'acc�s aux beans d�clar�s
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		// import des processus
		InitAppliService am = (InitAppliService) factory.getBean("InitAppliService");
		am.projectInstanciation();
	}
	
	private static void affectation () {
//		 Notre fabrique SPRING permettant l'acc�s aux beans d�clar�s
		ApplicationContext factory = new ClassPathXmlApplicationContext("applicationContextTest.xml");
		// import des processus
		InitAppliService am = (InitAppliService) factory.getBean("InitAppliService");
		am.initAppli();
	}
}

