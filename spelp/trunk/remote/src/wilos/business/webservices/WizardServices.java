/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
import wilos.business.services.guide.GuidanceService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.process.Process;

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
    public boolean isFileExistOnRemote(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="idGuidance") String idGuidance) throws Exception    
    {
    	String filePath = "";
    	boolean exist = false;
    	Participant wu = new Participant();
    	System.out.println("APPEL DE LA METHODE isFileExistOnRemote");
    	
    	wu =  getAuthentifiedParticipant(login,password);
        if (wu != null) {
        	System.out.println("Le user "+wu.getName()+" est logge");
        	filePath = assistantService.getAttachmentFilePath(idGuidance);
        	
        	File myfile = new File(filePath);
        	exist = myfile.exists();
        }
    	return exist;
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
