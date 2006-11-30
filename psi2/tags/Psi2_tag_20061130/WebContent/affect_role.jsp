<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<html>
<body>
<f:view>
	<h:form id="form">
	<t:dataTable value="#{ParticipantViewer.rolesList}" border="1" var="roleD">
		<h:column>
			<f:facet name="header">
				<h:outputText value="Role" />
			</f:facet>
			<h:outputText value="#{roleD.id}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText value="Role" />
			</f:facet>
			<t:selectBooleanCheckbox value="false" disabled="true"/>
		</h:column>
	</t:dataTable>
	</h:form>
</f:view>
</body>
</html>
