<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos</h1>
		<h:outputText value="- Ajout d'un nouveau Projet -" style="h1" />
 		<h:messages style="color:red;" showDetail="true"/>
 		
		<h:panelGrid columns="2">
			<h:outputText value="Nom" />
			<h:inputText id="nom" value="#{ProjectBean.project.name}" required="true"/>
			
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton value="Enregistrer" action="#{ProjectBean.saveProjectAction}"/>
			<h:commandButton value="Retour" immediate="true" action="welcome"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>