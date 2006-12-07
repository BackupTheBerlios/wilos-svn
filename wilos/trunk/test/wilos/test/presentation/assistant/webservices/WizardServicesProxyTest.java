/*
 * WizardServicesProxyTest.java
 * JUnit based test
 *
 * Created on 25 novembre 2006, 17:58
 */

package wilos.test.presentation.assistant.webservices;

import junit.framework.*;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.webservices.*;

/**
 *
 * @author Nicolas
 */
public class WizardServicesProxyTest extends TestCase {
    
    public WizardServicesProxyTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getRolesByUser method, of class webservices.WizardServicesProxy.
     */
    public void testGetRolesByUser() {
        System.out.println("getRolesByUser");
        
        String login = "";
        String password = "";
        String adresseServeur = "localhost:8084/remote";
        

        ArrayList<RoleDescriptor> result = WizardServicesProxy.getRolesByUser(login, password, adresseServeur);
        assertNotNull(result);
        assertTrue(result.size()==0);       
        
        login = "testSansBD";
        password = "testSansBD";
        adresseServeur = "http://localhost:8084/remote";        

        result = WizardServicesProxy.getRolesByUser(login, password, adresseServeur);
        assertNotNull(result);
        assertTrue(result.size()>=1);       

    }
    
}
