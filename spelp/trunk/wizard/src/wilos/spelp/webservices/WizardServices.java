/**
 * WizardServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package wilos.spelp.webservices;

public interface WizardServices extends java.rmi.Remote {
    public woops2.model.role.RoleDescriptor[] getRolesByUser(java.lang.String login, java.lang.String password) throws java.rmi.RemoteException;
}
