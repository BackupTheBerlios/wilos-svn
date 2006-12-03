
package woops2.model.methodpackage ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.methodelement.MethodElement ;
import woops2.model.methodlibrary.MethodConfiguration ;
import woops2.model.methodlibrary.MethodPlugin ;

/**
 * A Method Package is an abstract class for packaging Elements. Note, that other packages will
 * introduce more specializations of Method Package, e.g. Process Package and Process Component.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class MethodPackage extends MethodElement {

	private Set<MethodPackage> usingMethodPackages ;

	private Set<MethodPackage> reusedMethodPackages ;

	private MethodConfiguration methodConfiguration ;

	private MethodPlugin methodPlugin ;

	/**
	 * Default Constructor.
	 */
	public MethodPackage() {
		super() ;
		this.usingMethodPackages = new HashSet<MethodPackage>() ;
		this.reusedMethodPackages = new HashSet<MethodPackage>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#clone()
	 */
	@ Override
	public MethodPackage clone() throws CloneNotSupportedException {
		MethodPackage methodPackage = new MethodPackage() ;
		methodPackage.copy(this) ;
		return methodPackage ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#copy(woops2.model.methodelement.MethodElement)
	 */
	protected void copy(MethodPackage _methodPackage) {
		super.copy(_methodPackage) ;
		this.reusedMethodPackages = _methodPackage.getReusedMethodPackages() ;
		this.usingMethodPackages = _methodPackage.getUsingMethodPackages() ;
		this.methodConfiguration = _methodPackage.getMethodConfiguration() ;
		this.methodPlugin = _methodPackage.getMethodPlugin() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof MethodPackage == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		MethodPackage methodPackage = (MethodPackage) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(methodPackage)).append(this.reusedMethodPackages, methodPackage.getReusedMethodPackages()).append(
				this.reusedMethodPackages, methodPackage.getReusedMethodPackages()).append(this.methodConfiguration, methodPackage.getMethodConfiguration())
				.append(this.methodPlugin, methodPackage.getMethodPlugin()).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.methodConfiguration).append(this.methodPlugin).toHashCode() ;
	}

	/**
	 * @return the reusedMethodPackages
	 */
	public Set<MethodPackage> getReusedMethodPackages() {
		return this.reusedMethodPackages ;
	}

	/**
	 * Setter of reusedMethodPackages.
	 * 
	 * @param _reusedMethodPackages
	 *            The reusedMethodPackages to set.
	 */
	public void setReusedMethodPackages(Set<MethodPackage> _reusedMethodPackages) {
		this.reusedMethodPackages = _reusedMethodPackages ;
	}

	/**
	 * @return the usingMethodPackages
	 */
	public Set<MethodPackage> getUsingMethodPackages() {
		return this.usingMethodPackages ;
	}

	/**
	 * Setter of usingMethodPackages.
	 * 
	 * @param _usingMethodPackages
	 *            The usingMethodPackages to set.
	 */
	public void setUsingMethodPackages(Set<MethodPackage> _usingMethodPackages) {
		this.usingMethodPackages = _usingMethodPackages ;
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
	 * @return the methodPlugin
	 */
	public MethodPlugin getMethodPlugin() {
		return this.methodPlugin ;
	}

	/**
	 * Setter of methodPlugin.
	 * 
	 * @param _methodPlugin
	 *            The methodPlugin to set.
	 */
	public void setMethodPlugin(MethodPlugin _methodPlugin) {
		this.methodPlugin = _methodPlugin ;
	}
}
