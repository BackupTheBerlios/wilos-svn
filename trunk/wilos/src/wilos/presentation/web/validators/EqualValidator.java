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

package wilos.presentation.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents a validator that allows to verify if two fields are the
 * same
 * 
 * @author l3isi25 (Martial)
 * @author Sakamakak
 */
public class EqualValidator implements Validator {

	protected final Log logger = LogFactory.getLog(this.getClass());

	public void validate(FacesContext _context, UIComponent _toValidate,
			Object _value) throws ValidatorException {

		UIComponent component = _toValidate.findComponent("equal1");
		String valeur = (String) component.getAttributes().get("value");

		if (!_value.equals(valeur) || valeur.equals("") || _value.equals("")) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setDetail("Les deux champs ne sont pas identiques");
			throw new ValidatorException(message);
		}
	}
}
