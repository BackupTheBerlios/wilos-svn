/**
 * BreakdownElement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package woops2.model.breakdownelement;

public class BreakdownElement  extends woops2.model.element.Element  implements java.io.Serializable {
    private java.lang.Boolean hasMultipleOccurrences;

    private java.lang.Boolean isOptional;

    private java.lang.Boolean isPlanned;

    private java.lang.String prefix;

    private java.lang.Object[] superActivities;

    public BreakdownElement() {
    }

    public BreakdownElement(
           java.lang.String description,
           java.lang.String id,
           java.lang.String name,
           java.lang.Boolean hasMultipleOccurrences,
           java.lang.Boolean isOptional,
           java.lang.Boolean isPlanned,
           java.lang.String prefix,
           java.lang.Object[] superActivities) {
        super(
            description,
            id,
            name);
        this.hasMultipleOccurrences = hasMultipleOccurrences;
        this.isOptional = isOptional;
        this.isPlanned = isPlanned;
        this.prefix = prefix;
        this.superActivities = superActivities;
    }


    /**
     * Gets the hasMultipleOccurrences value for this BreakdownElement.
     * 
     * @return hasMultipleOccurrences
     */
    public java.lang.Boolean getHasMultipleOccurrences() {
        return hasMultipleOccurrences;
    }


    /**
     * Sets the hasMultipleOccurrences value for this BreakdownElement.
     * 
     * @param hasMultipleOccurrences
     */
    public void setHasMultipleOccurrences(java.lang.Boolean hasMultipleOccurrences) {
        this.hasMultipleOccurrences = hasMultipleOccurrences;
    }


    /**
     * Gets the isOptional value for this BreakdownElement.
     * 
     * @return isOptional
     */
    public java.lang.Boolean getIsOptional() {
        return isOptional;
    }


    /**
     * Sets the isOptional value for this BreakdownElement.
     * 
     * @param isOptional
     */
    public void setIsOptional(java.lang.Boolean isOptional) {
        this.isOptional = isOptional;
    }


    /**
     * Gets the isPlanned value for this BreakdownElement.
     * 
     * @return isPlanned
     */
    public java.lang.Boolean getIsPlanned() {
        return isPlanned;
    }


    /**
     * Sets the isPlanned value for this BreakdownElement.
     * 
     * @param isPlanned
     */
    public void setIsPlanned(java.lang.Boolean isPlanned) {
        this.isPlanned = isPlanned;
    }


    /**
     * Gets the prefix value for this BreakdownElement.
     * 
     * @return prefix
     */
    public java.lang.String getPrefix() {
        return prefix;
    }


    /**
     * Sets the prefix value for this BreakdownElement.
     * 
     * @param prefix
     */
    public void setPrefix(java.lang.String prefix) {
        this.prefix = prefix;
    }


    /**
     * Gets the superActivities value for this BreakdownElement.
     * 
     * @return superActivities
     */
    public java.lang.Object[] getSuperActivities() {
        return superActivities;
    }


    /**
     * Sets the superActivities value for this BreakdownElement.
     * 
     * @param superActivities
     */
    public void setSuperActivities(java.lang.Object[] superActivities) {
        this.superActivities = superActivities;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BreakdownElement)) return false;
        BreakdownElement other = (BreakdownElement) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.hasMultipleOccurrences==null && other.getHasMultipleOccurrences()==null) || 
             (this.hasMultipleOccurrences!=null &&
              this.hasMultipleOccurrences.equals(other.getHasMultipleOccurrences()))) &&
            ((this.isOptional==null && other.getIsOptional()==null) || 
             (this.isOptional!=null &&
              this.isOptional.equals(other.getIsOptional()))) &&
            ((this.isPlanned==null && other.getIsPlanned()==null) || 
             (this.isPlanned!=null &&
              this.isPlanned.equals(other.getIsPlanned()))) &&
            ((this.prefix==null && other.getPrefix()==null) || 
             (this.prefix!=null &&
              this.prefix.equals(other.getPrefix()))) &&
            ((this.superActivities==null && other.getSuperActivities()==null) || 
             (this.superActivities!=null &&
              java.util.Arrays.equals(this.superActivities, other.getSuperActivities())));
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
        if (getHasMultipleOccurrences() != null) {
            _hashCode += getHasMultipleOccurrences().hashCode();
        }
        if (getIsOptional() != null) {
            _hashCode += getIsOptional().hashCode();
        }
        if (getIsPlanned() != null) {
            _hashCode += getIsPlanned().hashCode();
        }
        if (getPrefix() != null) {
            _hashCode += getPrefix().hashCode();
        }
        if (getSuperActivities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSuperActivities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSuperActivities(), i);
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
        new org.apache.axis.description.TypeDesc(BreakdownElement.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "BreakdownElement"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasMultipleOccurrences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "hasMultipleOccurrences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isOptional");
        elemField.setXmlName(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "isOptional"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPlanned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "isPlanned"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superActivities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://breakdownelement.model.woops2", "superActivities"));
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
