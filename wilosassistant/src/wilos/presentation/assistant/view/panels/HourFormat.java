package wilos.presentation.assistant.view.panels;

import java.text.ParseException;

import javax.swing.text.DefaultFormatter;

public class HourFormat extends DefaultFormatter {

	
		@Override
		public Object stringToValue(String string) throws ParseException {
			// TODO Auto-generated method stub 
			Long retour = (long)0 ;
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
				int iheure,imin,isec ;
				iheure= Integer.valueOf(heure)*3600 ;
				imin = Integer.valueOf(min)*60 ;
				isec = Integer.valueOf(sec) ;
				retour = new Long(iheure + imin + isec );
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