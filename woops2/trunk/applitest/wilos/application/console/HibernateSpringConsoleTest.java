/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.spem2.activity.Activity;

/**
 * Application for testing hibernate and spring configuration with small model
 * @author garwind
 */
public class HibernateSpringConsoleTest {

	
	public static void main(String[] args) {
		// Getback the application context from the spring configuration file
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		// Show what is in the factory
		System.out.println("factory => "+ctx);
		// Getback the hibernateTemplate bean
		org.springframework.orm.hibernate3.HibernateTemplate hibTempl = (org.springframework.orm.hibernate3.HibernateTemplate) ctx.getBean("hibernateTemplate");
		System.out.println("HibTemplate => "+hibTempl);
		// Get the ActivityDao Singleton for managing Activity data
		ActivityDao dao = (ActivityDao) ctx.getBean("ActivityDao");
		// Create empty Activity
		Activity a = new Activity();
		// Save it
		dao.saveOrUpdateActivity(a);
	}

}
