/**
 * RoleDefinition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package woops2.model.role;

public class RoleDefinition  extends woops2.model.element.Element  implements java.io.Serializable {
    private java.lang.Object[] roleDescriptors;

    public RoleDefinition() {
    }

    public RoleDefinition(
           java.lang.String description,
           java.lang.String id,
           java.lang.String name,
           java.lang.Object[] roleDescriptors) {
        super(
            description,
            id,
            name);
        this.roleDescriptors = roleDescriptors;
    }


    /**
     * Gets the roleDescriptors value for this RoleDefinition.
     * 
     * @return roleDescriptors
     */
    public java.lang.Object[] getRoleDescriptors() {
        return roleDescriptors;
    }


    /**
     * Sets the roleDescriptors value for this RoleDefinition.
     * 
     * @param roleDescriptors
     */
    public void setRoleDescriptors(java.lang.Object[] roleDescriptors) {
        this.roleDescriptors = roleDescriptors;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleDefinition)) return false;
        RoleDefinition other = (RoleDefinition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.roleDescriptors==null && other.getRoleDescriptors()==null) || 
             (this.roleDescriptors!=null &&
              java.util.Arrays.equals(this.roleDescriptors, other.getRoleDescriptors())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getRoleDescriptors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRoleDescriptors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRoleDescriptors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoleDefinition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://role.model.woops2", "RoleDefinition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleDescriptors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://role.model.woops2", "roleDescriptors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://webservices.spelp.wilos", "item"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
