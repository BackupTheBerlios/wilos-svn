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

package wilos.utils;

import java.text.SimpleDateFormat;

public class Constantes {

	public static final String DATE_PATTERN = "dd/MM/yyyy hh:mm" ;

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN) ;

	public static class State {

		public static final String CREATED = "Created";

		public static final String READY = "Ready";

		public static final String STARTED = "Started";

		public static final String SUSPENDED = "Suspended";

		public static final String FINISHED = "Finished";
	}

	public static class WorkOrderType {

		public static final String START_TO_START = "start-to-start";

		public static final String START_TO_FINISH = "start-to-finish";

		public static final String FINISH_TO_START = "finish-to-start";

		public static final String FINISH_TO_FINISH  = "finish-to-finish";
	}
}
