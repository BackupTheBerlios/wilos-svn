/**
 * RoleDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package woops2.model.role;

public class RoleDescriptor  extends woops2.model.breakdownelement.BreakdownElement  implements java.io.Serializable {
    private java.lang.Object[] additionalTasks;

    private java.lang.Object[] primaryTasks;

    private woops2.model.role.RoleDefinition roleDefinition;

    public RoleDescriptor() {
    }

    public RoleDescriptor(
           java.lang.String description,
           java.lang.String id,
           java.lang.String name,
           java.lang.Boolean hasMultipleOccurrences,
           java.lang.Boolean isOptional,
           java.lang.Boolean isPlanned,
           java.lang.String prefix,
           java.lang.Object[] superActivities,
           java.lang.Object[] additionalTasks,
           java.lang.Object[] primaryTasks,
           woops2.model.role.RoleDefinition roleDefinition) {
        super(
            description,
            id,
            name,
            hasMultipleOccurrences,
            isOptional,
            isPlanned,
            prefix,
            superActivities);
        this.additionalTasks = additionalTasks;
        this.primaryTasks = primaryTasks;
        this.roleDefinition = roleDefinition;
    }


    /**
     * Gets the additionalTasks value for this RoleDescriptor.
     * 
     * @return additionalTasks
     */
    public java.lang.Object[] getAdditionalTasks() {
        return additionalTasks;
    }


    /**
     * Sets the additionalTasks value for this RoleDescriptor.
     * 
     * @param additionalTasks
     */
    public void setAdditionalTasks(java.lang.Object[] additionalTasks) {
        this.additionalTasks = additionalTasks;
    }


    /**
     * Gets the primaryTasks value for this RoleDescriptor.
     * 
     * @return primaryTasks
     */
    public java.lang.Object[] getPrimaryTasks() {
        return primaryTasks;
    }


    /**
     * Sets the primaryTasks value for this RoleDescriptor.
     * 
     * @param primaryTasks
     */
    public void setPrimaryTasks(java.lang.Object[] primaryTasks) {
        this.primaryTasks = primaryTasks;
    }


    /**
     * Gets the roleDefinition value for this RoleDescriptor.
     * 
     * @return roleDefinition
     */
    public woops2.model.role.RoleDefinition getRoleDefinition() {
        return roleDefinition;
    }


    /**
     * Sets the roleDefinition value for this RoleDescriptor.
     * 
     * @param roleDefinition
     */
    public void setRoleDefinition(woops2.model.role.RoleDefinition roleDefinition) {
        this.roleDefinition = roleDefinition;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoleDescriptor)) return false;
        RoleDescriptor other = (RoleDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.additionalTasks==null && other.getAdditionalTasks()==null) || 
             (this.additionalTasks!=null &&
              java.util.Arrays.equals(this.additionalTasks, other.getAdditionalTasks()))) &&
            ((this.primaryTasks==null && other.getPrimaryTasks()==null) || 
             (this.primaryTasks!=null &&
              java.util.Arrays.equals(this.primaryTasks, other.getPrimaryTasks()))) &&
            ((this.roleDefinition==null && other.getRoleDefinition()==null) || 
             (this.roleDefinition!=null &&
              this.roleDefinition.equals(other.getRoleDefinition())));
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
        if (getAdditionalTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdditionalTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdditionalTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrimaryTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrimaryTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrimaryTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRoleDefinition() != null) {
            _hashCode += getRoleDefinition().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoleDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://role.model.woops2", "RoleDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://role.model.woops2", "additionalTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://webservices.spelp.wilos", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://role.model.woops2", "primaryTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://webservices.spelp.wilos", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleDefinition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://role.model.woops2", "roleDefinition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://role.model.woops2", "RoleDefinition"));
        elemField.setNillable(true);
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
