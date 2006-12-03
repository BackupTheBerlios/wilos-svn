
package woops2.model.methodlibrary ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.methodelement.MethodElement ;
import woops2.model.methodpackage.MethodPackage ;

/**
 * A Method Configuration is a collection of selected Method Models and MethodPackages.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class MethodConfiguration extends MethodElement {

	private MethodLibrary methodLibrary ;

	private Set<MethodPlugin> methodPluginsSelection ;

	private Set<MethodPackage> methodPackagesSelection ;

	/**
	 * Default constructor.
	 */
	public MethodConfiguration() {
		super() ;
		this.methodPackagesSelection = new HashSet<MethodPackage>() ;
		this.methodPluginsSelection = new HashSet<MethodPlugin>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#clone()
	 */
	@ Override
	public MethodConfiguration clone() throws CloneNotSupportedException {
		MethodConfiguration methodConfiguration = new MethodConfiguration() ;
		methodConfiguration.copy(this) ;
		return methodConfiguration ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#copy(woops2.model.methodelement.MethodElement)
	 */
	protected void copy(MethodConfiguration _methodConfiguration) {
		super.copy(_methodConfiguration) ;
		this.methodLibrary = _methodConfiguration.getMethodLibrary() ;
		this.methodPackagesSelection = _methodConfiguration.getMethodPackagesSelection() ;
		this.methodPluginsSelection = _methodConfiguration.getMethodPluginsSelection() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof MethodConfiguration == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		MethodConfiguration methodConfiguration = (MethodConfiguration) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(methodConfiguration)).append(this.methodLibrary, methodConfiguration.getMethodLibrary()).append(
				this.methodPackagesSelection, methodConfiguration.getMethodPackagesSelection()).append(this.methodPluginsSelection,
				methodConfiguration.getMethodPluginsSelection()).isEquals() ;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.methodLibrary).toHashCode() ;
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
	 * @return the methodPackagesSelection
	 */
	public Set<MethodPackage> getMethodPackagesSelection() {
		return this.methodPackagesSelection ;
	}

	/**
	 * Setter of methodPackagesSelection.
	 * 
	 * @param _methodPackagesSelection
	 *            The methodPackagesSelection to set.
	 */
	public void setMethodPackagesSelection(Set<MethodPackage> _methodPackagesSelection) {
		this.methodPackagesSelection = _methodPackagesSelection ;
	}

	/**
	 * @return the methodPluginsSelection
	 */
	public Set<MethodPlugin> getMethodPluginsSelection() {
		return this.methodPluginsSelection ;
	}

	/**
	 * Setter of methodPluginsSelection.
	 * 
	 * @param _methodPluginsSelection
	 *            The methodPluginsSelection to set.
	 */
	public void setMethodPluginsSelection(Set<MethodPlugin> _methodPluginsSelection) {
		this.methodPluginsSelection = _methodPluginsSelection ;
	}
}
