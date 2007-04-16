/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.application.console;

import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.misc.project.ProjectService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;

/**
 * @author Administrateur
 * 
 * This class represents ... TODO
 * 
 */
public class AffectedParticipantsTest {

	/**
	 * TODO Method description
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		ProjectDao projectDao = (ProjectDao) ctx.getBean("ProjectDao");
		ProjectService projectService = (ProjectService) ctx
				.getBean("ProjectService");
		Project project = projectDao
				.getProject("ff8080811054e428011054e44ea60001");
		Set<Participant> partList = projectService.getParticipants(project);
		System.out.println("Taille : " + partList.size());
	}

}
