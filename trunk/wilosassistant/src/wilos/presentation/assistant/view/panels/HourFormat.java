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

package wilos.presentation.assistant.view.panels;

import java.text.ParseException;

import javax.swing.text.DefaultFormatter;

import org.apache.commons.lang.math.NumberUtils;

public class HourFormat extends DefaultFormatter {

	
		@Override
		public Object stringToValue(String string) throws ParseException {
			// TODO Auto-generated method stub 
			Long retour = (long)0 ;
			int iheure,imin,isec = 0 ;
			if (string.matches(HourTextField.pattern)){
				String time = string ;
				String heure = "0", min="0",sec = "0" ;
				for (int i=0,occu = 0 ;i	<time.length();i++) 
				{
					if (time.charAt(i)==':')occu++;
					if(occu==0&&time.charAt(i)!=':') heure+=time.charAt(i);
					if(occu==1&&time.charAt(i)!=':') min+=time.charAt(i);
					if(occu==2&&time.charAt(i)!=':') sec+=time.charAt(i);
				}
				
				iheure= Integer.valueOf(heure)*3600 ;
				imin = Integer.valueOf(min)*60 ;
				isec = Integer.valueOf(sec) ;
				retour = new Long(iheure + imin + isec );
			}
			else if (NumberUtils.isDigits(string)){
				switch (string.length()){
					case 2 :
					case 1 :retour = new Long(Integer.valueOf(string)*3600);		
						break ;
					case 6 :isec = Integer.valueOf(string.substring(4,6));
					case 4 :iheure = Integer.valueOf(string.substring(0,2))*3600;
							imin = Integer.valueOf(string.substring(2,4))*60;
							retour = new Long(iheure + imin + isec );
							break ;
					default : retour = (long)0 ; 
							break ;
					
						
				}
			}
			return retour;
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			String retour = "" ;
			if (value instanceof Long){
				long val = (Long)value;
				float hour = (float)val/3600 ;
				float min =  ((hour-(int)hour)*60)%60 ;
				int sec = (Math.round((min - (int)min)*60))%60 ;
				if (hour < 10){
					retour += ("0" + (int)hour); 
				}
				else {
					retour += (int)hour ;
				}
				retour += ":";
				if (min < 10){
					retour += ("0" + (int)min);
				}
				else {
					retour += (int)min;
				}
				retour += ":";
				if (sec < 10){
					retour += ("0" + sec);
				}
				else {
					retour += sec ;
				}  
			}
			return retour ;
		}
		
	}