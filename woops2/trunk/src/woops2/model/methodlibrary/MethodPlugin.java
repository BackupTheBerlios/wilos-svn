
package woops2.model.methodlibrary ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.methodelement.MethodElement ;
import woops2.model.methodpackage.MethodPackage ;

/**
 * A Method Plugin is a Method Element that represents a physical container for Method Packages. It
 * defines a granularity level for the modularization and organization of method content and
 * processes.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class MethodPlugin extends MethodElement {

	private MethodLibrary methodLibrary ;

	private MethodConfiguration methodConfiguration ;

	private Set<MethodPackage> methodPackages ;

	/**
	 * Default constructor.
	 */
	public MethodPlugin() {
		super() ;
		this.methodPackages = new HashSet<MethodPackage>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#clone()
	 */
	@ Override
	public MethodPlugin clone() throws CloneNotSupportedException {
		MethodPlugin methodPlugin = new MethodPlugin() ;
		methodPlugin.copy(this) ;
		return methodPlugin ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#copy(woops2.model.methodelement.MethodElement)
	 */
	protected void copy(MethodPlugin _methodPlugin) {
		super.copy(_methodPlugin) ;
		this.methodConfiguration = _methodPlugin.getMethodConfiguration() ;
		this.methodLibrary = _methodPlugin.getMethodLibrary() ;
		this.methodPackages = _methodPlugin.getMethodPackages() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof MethodPlugin == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		MethodPlugin methodPlugin = (MethodPlugin) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(methodPlugin)).append(this.methodLibrary, methodPlugin.getMethodLibrary()).append(
				this.methodConfiguration, methodPlugin.getMethodConfiguration()).append(this.methodPackages, methodPlugin.getMethodPackages()).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.methodLibrary).append(this.methodConfiguration).toHashCode() ;
	}

	/**
	 * @return the methodConfiguration
	 */
	public MethodConfiguration getMethodConfiguration() {
		return this.methodConfiguration ;
	}

	/**
	 * Setter of methodConfiguration.
	 * 
	 * @param _methodConfiguration
	 *            The methodConfiguration to set.
	 */
	public void setMethodConfiguration(MethodConfiguration _methodConfiguration) {
		this.methodConfiguration = _methodConfiguration ;
	}

	/**
	 * @return the methodLibrary
	 */
	public MethodLibrary getMethodLibrary() {
		return this.methodLibrary ;
	}

	/**
	 * Setter of methodLibrary.
	 * 
	 * @param _methodLibrary
	 *            The methodLibrary to set.
	 */
	public void setMethodLibrary(MethodLibrary _methodLibrary) {
		this.methodLibrary = _methodLibrary ;
	}

	/**
	 * @return the methodPackages
	 */
	public Set<MethodPackage> getMethodPackages() {
		return this.methodPackages ;
	}

	/**
	 * Setter of methodPackages.
	 * 
	 * @param _methodPackages
	 *            The methodPackages to set.
	 */
	public void setMethodPackages(Set<MethodPackage> _methodPackages) {
		this.methodPackages = _methodPackages ;
	}

}
