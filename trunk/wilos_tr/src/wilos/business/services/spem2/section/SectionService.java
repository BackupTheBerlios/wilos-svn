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

package wilos.business.services.spem2.section;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.section.SectionDao;
import wilos.model.spem2.section.Section;

@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class SectionService {
	private SectionDao sectionDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Section getSection(String _id) {
		return this.sectionDao.getSection(_id) ;
	}

	public List<Section> getAllSections() {
		return this.sectionDao.getAllSection() ;
	}

	public void saveSection(Section _section) {
		this.sectionDao.saveOrUpdateSection(_section) ;
	}

	public void deleteSection(Section _section) {
		this.sectionDao.deleteSection(_section) ;
	}

	/**
	 * Getter of sectionDAO.
	 * 
	 * @return the sectionDAO.
	 */
	public SectionDao getSectionDao() {
		return this.sectionDao ;
	}

	/**
	 * Setter of sectionDao.
	 * 
	 * @param _sectionDAO
	 *            The sectionDAO to set.
	 */
	public void setSectionDao(SectionDao _sectionDAO) {
		this.sectionDao = _sectionDAO ;
	}
}

