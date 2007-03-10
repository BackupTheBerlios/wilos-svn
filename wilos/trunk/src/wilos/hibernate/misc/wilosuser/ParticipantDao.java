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
package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

/**
 * ParticipantDao manage requests from the system to store Participant into the
 * database.
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
/**
 * @author mikamikaze
 *
 */
public class ParticipantDao extends HibernateDaoSupport {

	/**
	 * Save or update an element.
	 * 
	 * @param _element
	 */
	public void saveOrUpdateParticipant(Participant _participant) {
		this.getHibernateTemplate().saveOrUpdate(_participant);
	}

	/**
	 * Return a list of elements.
	 * @return
	 */
	public Set<ConcreteRoleDescriptor> getAllConcreteRoleDescriptors() {
		Set<ConcreteRoleDescriptor> loadAll = new HashSet<ConcreteRoleDescriptor>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(ConcreteRoleDescriptor.class));
		return loadAll;
	}
	
	/**
	 * Return a list of elements.
	 * TODO test + description
	 * @return
	 */
	public Set<ConcreteRoleDescriptor> getAllConcreteRolesForAParticipant(String _login) {
		Set<ConcreteRoleDescriptor> concreteRoleDescriptorsForAParticipant = new HashSet<ConcreteRoleDescriptor>();
		
		Participant participant = this.getParticipant(_login);
		List<ConcreteRoleDescriptor> concreteRoleDescriptors = new ArrayList<ConcreteRoleDescriptor>();
		concreteRoleDescriptors.addAll(this.getAllConcreteRoleDescriptors());
		
		for(Iterator iter = concreteRoleDescriptors.iterator(); iter.hasNext();){
			ConcreteRoleDescriptor concreteRoleDescriptor = (ConcreteRoleDescriptor) iter.next() ;
			Participant currentParticipant = concreteRoleDescriptor.getParticipant();
			if(currentParticipant != null){
				if(currentParticipant.getWilosuser_id().equals(participant.getWilosuser_id())){
					concreteRoleDescriptorsForAParticipant.add(concreteRoleDescriptor);
				}
			}			
		}		
		return concreteRoleDescriptorsForAParticipant;
	}

	/**
	 * Return a set of Participants.
	 * 
	 * @return
	 */
	public Set<Participant> getAllParticipants() {
		Set<Participant> loadAll = new HashSet<Participant>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(Participant.class));
		return loadAll;
	}

	
	/**
	 * Return the Participant which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Participant getParticipantById(String _id) {
		return (Participant) this.getHibernateTemplate().get(Participant.class, _id);
	}
	
	/**
	 * return a participant by giving his login
	 * @param _login
	 * @return
	 */
	public Participant getParticipant(String _login){
		ArrayList participants = (ArrayList)this.getHibernateTemplate().find("from Participant p where p.login=?",_login);
		if(participants.size()>0){
			return (Participant)participants.get(0);
		}else{
			return null;
		}
	}
	/**
	 * Delete the element.
	 * 
	 * @param _element
	 */
	public void deleteParticipant(Participant _participant) {
		try {
			this.getHibernateTemplate().delete(_participant);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting element into the db.
			logger.error("#### ERROR #### --- ParticipantDao => deleteParticipant : trying to delete unexisting object \n" + sose);
		}
	}
}
