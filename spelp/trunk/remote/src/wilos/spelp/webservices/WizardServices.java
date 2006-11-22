package wilos.spelp.webservices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import woops2.business.activity.ActivityManager;
import woops2.business.process.ProcessService;
import woops2.hibernate.role.RoleDescriptorDao;
import woops2.model.role.RoleDescriptor;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;

public class WizardServices {
	public RoleDescriptor [] getRolesByUser (String login, String password) {
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

		return (RoleDescriptor [] )r.toArray();
	}	
}
