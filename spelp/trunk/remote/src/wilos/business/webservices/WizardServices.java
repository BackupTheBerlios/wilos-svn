/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.webservices;

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
	
	/*public WizardServices() {
       // Getback the application context from the spring configuration file
       ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       // Show what is in the factory
       System.out.println("factory => "+ctx);
       // Get the LoginService Singleton for managing Activity data  
       loginService = (LoginService) ctx.getBean("LoginService");
       assistantService = (AssistantService) ctx.getBean("AssistantService");
	}*/
	
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
    		System.out.println("le Participant est logg�");
    		assistantService.suspendConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void resumeConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : resumeConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logg�");
    		assistantService.resumeConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void stopConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : stopConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logg�");
    		assistantService.finishConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void setAccomplishedTimeByTask(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="taskGuid") String taskGuid,@WebParam(name="newTime") int newTime) throws Exception {
		System.out.println("APPEL DE METHODE : setAccomplishedTimeByTask");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logg�");
    		assistantService.setAccomplishedTimeByTask(taskGuid, newTime);
    	}
    }

    @WebMethod
    public void setRemainingTimeByTask(@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="taskGuid") String taskGuid,@WebParam(name="newTime") int newTime) throws Exception {
		System.out.println("APPEL DE METHODE : setRemainingTimeByTask");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est logg�");
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
