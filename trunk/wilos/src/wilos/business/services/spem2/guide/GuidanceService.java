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

package wilos.business.services.spem2.guide;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.guide.GuidanceDao;
import wilos.model.spem2.guide.Guidance;

/**
 * 
 * @author Nicolas CASTEL
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class GuidanceService {
	private GuidanceDao guidanceDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Guidance getGuidance(String _id) {
		return this.guidanceDao.getGuidance(_id) ;
	}

	public List<Guidance> getAllGuidances() {
		return this.guidanceDao.getAllGuidances() ;
	}

	public void saveGuidance(Guidance _guidance) {
		this.guidanceDao.saveOrUpdateGuidance(_guidance) ;
	}

	public void deleteGuidance(Guidance _guidance) {
		this.guidanceDao.deleteGuidance(_guidance) ;
	}
	
	public Guidance getGuidanceFromGuid(String _guid) {
		return this.guidanceDao.getGuidanceFromGuid(_guid);
	}

	/**
	 * Getter of guidelineDao.
	 * 
	 * @return the guidelineDao.
	 */
	public GuidanceDao getGuidanceDao() {
		return this.guidanceDao ;
	}

	/**
	 * Setter of guidelineDao.
	 * 
	 * @param _guidanceDao
	 *            The guidelineDao to set.
	 */
	public void setGuidanceDao(GuidanceDao _guidanceDao) {
		this.guidanceDao = _guidanceDao ;
	}
}
