
package wilos.model.spem2.methodpackage ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.process.Process;

/**
 * Process Package is a special Method Package that contains Process Elements, only.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class ProcessPackage extends MethodPackage {

	private Set<Process> processes ;

	/**
	 * Default Constructor.
	 */
	public ProcessPackage() {
		super() ;
		this.processes = new HashSet<Process>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.MethodPackage#clone()
	 */
	@ Override
	public ProcessPackage clone() throws CloneNotSupportedException {
		ProcessPackage processPackage = new ProcessPackage() ;
		processPackage.copy(this) ;
		return processPackage ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.MethodPackage#copy(woops2.model.methodpackage.MethodPackage)
	 */
	protected void copy(ProcessPackage _processPackage) {
		super.copy(_processPackage) ;
		this.processes = _processPackage.getProcesses() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.MethodPackage#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof ProcessPackage == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProcessPackage processPackage = (ProcessPackage) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(processPackage)).append(this.processes, processPackage.getProcesses()).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.MethodPackage#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * @return the processes
	 */
	public Set<Process> getProcesses() {
		return this.processes ;
	}

	/**
	 * Setter of processes.
	 * 
	 * @param _processes
	 *            The processes to set.
	 */
	public void setProcesses(Set<Process> _processes) {
		this.processes = _processes ;
	}

}
