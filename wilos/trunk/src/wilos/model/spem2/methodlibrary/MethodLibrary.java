
package wilos.model.spem2.methodlibrary ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.spem2.methodelement.MethodElement;

/**
 * A Method Library is a physical container for Method Plugins and Method Configuration definitions.
 * All Elements are stored in a Method Library.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class MethodLibrary extends MethodElement {

	private Set<MethodConfiguration> predefinedConfigurations ;

	private Set<MethodPlugin> methodPlugins ;

	/**
	 * Default constructor.
	 */
	public MethodLibrary() {
		super() ;
		this.predefinedConfigurations = new HashSet<MethodConfiguration>() ;
		this.methodPlugins = new HashSet<MethodPlugin>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#clone()
	 */
	@ Override
	public MethodLibrary clone() throws CloneNotSupportedException {
		MethodLibrary methodLibrary = new MethodLibrary() ;
		methodLibrary.copy(this) ;
		return methodLibrary ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#copy(woops2.model.methodelement.MethodElement)
	 */
	protected void copy(MethodLibrary _methodLibrary) {
		super.copy(_methodLibrary) ;
		this.predefinedConfigurations = _methodLibrary.getPredefinedConfigurations() ;
		this.methodPlugins = _methodLibrary.getMethodPlugins() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof MethodLibrary == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		MethodLibrary methodLibrary = (MethodLibrary) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(methodLibrary)).append(this.predefinedConfigurations, methodLibrary.getPredefinedConfigurations())
				.append(this.methodPlugins, methodLibrary.getMethodPlugins()).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodelement.MethodElement#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * @return the methodPlugins
	 */
	public Set<MethodPlugin> getMethodPlugins() {
		return this.methodPlugins ;
	}

	/**
	 * Setter of methodPlugins.
	 * 
	 * @param _methodPlugins
	 *            The methodPlugins to set.
	 */
	public void setMethodPlugins(Set<MethodPlugin> _methodPlugins) {
		this.methodPlugins = _methodPlugins ;
	}

	/**
	 * @return the predefinedConfigurations
	 */
	public Set<MethodConfiguration> getPredefinedConfigurations() {
		return this.predefinedConfigurations ;
	}

	/**
	 * Setter of predefinedConfigurations.
	 * 
	 * @param _predefinedConfigurations
	 *            The predefinedConfigurations to set.
	 */
	public void setPredefinedConfigurations(Set<MethodConfiguration> _predefinedConfigurations) {
		this.predefinedConfigurations = _predefinedConfigurations ;
	}

}
