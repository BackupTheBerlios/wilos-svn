/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.spelp.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import woops2.business.process.ProcessService;
import woops2.model.role.RoleDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;

/**
 *
 * @author toine
 */
@WebService()
public class WizardServices {
        @WebMethod
	public List<RoleDescriptor> getRolesByUser (@WebParam(name="login") String login,@WebParam(name="password")  String password) {
		List<RoleDescriptor> r = new ArrayList<RoleDescriptor>();
		if(login.equals("toto") && password.equals("toto")) {
			ProcessService p = new ProcessService();
			List<Process> lp = p.getProcessesList(); 
			
			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
			for (Process pr : lp) {
				bdes = pr.getBreakDownElements();
				for (BreakdownElement bd : bdes) {
					if (bd instanceof RoleDescriptor) {
						r.add((RoleDescriptor)bd);
					}
				}
			}
		}

		return r;
	}	
}
