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

package wilos.business.webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import wilos.business.services.assistant.AssistantService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author toine 
 */
@WebService()
public class WizardServices {
	LoginService loginService;
	AssistantService assistantService;
	
    @WebMethod
    public String getParticipant(@WebParam(name="login") String login, @WebParam(name="password")  String password) throws Exception
    {
       String result = "";
       Participant wu = new Participant();
       System.out.println("APPEL DE LA METHODE getWilosUser");
       System.out.println("LOGIN : "+login);
       System.out.println("PASS : "+password);
         
       wu =  assistantService.getParticipantTO(getAuthentifiedParticipant(login,password).getLogin());       
	   System.out.println("Le user "+wu.getName()+" est logge");

       XStream xstream = new XStream(); 

       result= xstream.toXML(wu);
       
       /*File xstreamFile = new File ("xstream.xml");
       PrintStream out = new PrintStream(new FileOutputStream(xstreamFile), false, "UTF-8");
       out.println(result);*/
       
       return result;
    }
    
    @WebMethod
    public  byte []  getGuidanceAttachment(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="idGuidance") String idGuidance) throws Exception
    {
       String filePath = "";
       Participant wu = new Participant();
       byte [] contenuFichier = null;
       
       System.out.println("APPEL DE LA METHODE getGuidanceAttachment");
       
       wu =  getAuthentifiedParticipant(login,password);
       if (wu != null) {
    	   System.out.println("Le user "+wu.getName()+" est logge");
    	   
    	   filePath = assistantService.getAttachmentFilePath(idGuidance);
    	   
    	   // serialisation du fichier
    		   
		   File myfile = new File(filePath);
		   if (myfile.exists()) {
			   // ouverture du fichier
			   try {
			      // Ouverture du fichier passé en paramètre dans la ligne de commande
			      InputStream fluxFichier = new FileInputStream (filePath);
			 
			      // Lecture des n premiers octets du fichier. n est passé en paramètre
			       contenuFichier  = new byte [(int) myfile.length()];
			      fluxFichier.read (contenuFichier);
			     
			      // Fermeture du fichier
			      fluxFichier.close ();
			      
			    }
			    catch (IOException e) {
			      // Exception déclenchée si un problème survient pendant l'accès au fichier
			      System.out.println (e);
			    }
		   }
		   else
			   contenuFichier = null;
       }
       
       /*File xstreamFile = new File ("xstream.xml");
       PrintStream out = new PrintStream(new FileOutputStream(xstreamFile), false, "UTF-8");
       out.println(result);*/
       
       return contenuFichier;
    }
    
    @WebMethod
    public void startConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : startConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.startConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void suspendConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : suspendConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.suspendConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void resumeConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : resumeConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.resumeConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void stopConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : stopConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.finishConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void setAccomplishedTimeByTask(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="taskGuid") String taskGuid,@WebParam(name="newTime") float newTime) throws Exception {
		System.out.println("APPEL DE METHODE : setAccomplishedTimeByTask");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.setAccomplishedTimeByTask(taskGuid, newTime);
    	}
    }

    @WebMethod
    public void setRemainingTimeByTask(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="taskGuid") String taskGuid,@WebParam(name="newTime") float newTime) throws Exception {
		System.out.println("APPEL DE METHODE : setRemainingTimeByTask");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logge");
    		assistantService.setRemainingTimeByTask(taskGuid, newTime);
    	}
    }

    private Participant getAuthentifiedParticipant (String login,  String password) throws Exception {
    	Participant result = null;
        WilosUser tmpwu = loginService.getAuthentifiedUser(login,password);
       if (tmpwu == null)
       {
           throw new Exception("le wilos user n'existe pas");
       }
       else
       {
           if (!(tmpwu instanceof Participant))
           {
                 throw new Exception("le wilos user n'est pas un particpant");
           }
           else
           {
        	   result = (Participant) tmpwu;
           }
       }
       return result;
    }

	public AssistantService getAssistantService() {
		return assistantService;
	}

	public void setAssistantService(AssistantService assistantService) {
		this.assistantService = assistantService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
