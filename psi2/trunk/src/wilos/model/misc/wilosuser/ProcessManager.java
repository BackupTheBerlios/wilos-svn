
package wilos.model.misc.wilosuser ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

/**
 * This class represents a process manager using Wilos.
 * 
 * @author Yoann Lopes
 */
public class ProcessManager extends WilosUser implements Cloneable {

	private Set<Process> processesManaged;
	
	/**
	 * Default Constructor.
	 * TODO
	 */
	public ProcessManager() {
		this.processesManaged = new HashSet<Process>();
	}

	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public ProcessManager(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
		this.processesManaged = new HashSet<Process>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ProcessManager clone() throws CloneNotSupportedException {
		ProcessManager processManager = new ProcessManager() ;
		processManager.copy(this) ;
		return processManager ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _processManager
	 *            The ProcessManager to copy.
	 */
	protected void copy(final ProcessManager _processManager) {
		super.copy(_processManager) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof ProcessManager == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProcessManager processManager = (ProcessManager) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(processManager)).append(this.processesManaged,processManager.processesManaged).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * Getter of processManaged.
	 *TODO
	 * @return the processManaged.
	 */
	public Set<Process> getProcessesManaged() {
		return this.processesManaged ;
	}

	/**
	 * Setter of processManaged.
	 *TODO
	 * @param _processManaged The processManaged to set.
	 */
	public void setProcessesManaged(Set<Process> _processManaged) {
		this.processesManaged = _processManaged ;
	}
	
	/**
	 * 
	 * TODO Method description
	 *
	 * @param project
	 */
	public void addManagedProcess(Process _process) {
		this.processesManaged.add(_process) ;
		_process.setProcessManager(this) ;
	}

	/**
	 * 
	 * TODO Method description
	 *
	 * @param project
	 */
	public void removeManagedProcess(Process _process) {
		_process.setProcessManager(null) ;
		this.processesManaged.remove(_process) ;		
	}

	/**
	 * 
	 * TODO Method description
	 *
	 */
	public void removeAllManagedProcess() {
		for(Process process : this.processesManaged){
			process.removeFromProcessManager(this) ;
		}
		this.processesManaged.clear() ;
	}
}
