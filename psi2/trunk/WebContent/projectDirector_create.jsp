<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos - Administration</h1>
		<h:outputText value="- Ajout d'un nouveau Project Director -" style="h1" />
 
		<h:panelGrid columns="3">
			<h:outputText value="Nom" />
			<h:inputText id="nom" value="#{ProjectDirectorViewer.projectDirector.name}" required="true"/>
			<h:message for="nom" style="color:red;" showDetail="true"/>
					
			<h:outputText value="Prénom"/>
			<h:inputText id="prenom" value="#{ProjectDirectorViewer.projectDirector.firstName}" required="true"/>
			<h:message for="prenom" style="color:red;" showDetail="true"/>

			<h:outputText value="Pseudo"/>
			<h:inputText id="login" value="#{ProjectDirectorViewer.projectDirector.login}" required="true"/>
			<h:message for="login" style="color:red;" showDetail="true"/>
			
			<h:outputText value="Mot de passe"/>
			<h:inputSecret id="equal1" value="#{ProjectDirectorViewer.projectDirector.password}" required="true"/>		
			<h:message for="equal1" style="color:red;" showDetail="true"/>
			
			<h:outputText value="Confirmer le mot de passe"/>
			<h:inputSecret id="equal2" value="#{ProjectDirectorViewer.passwordConfirmation}">
				<f:validator validatorId="equalValidator"/>
			</h:inputSecret>
			<h:message for="equal2" style="color:red;" showDetail="true"/>
			
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton value="S'enregistrer" action="#{ProjectDirectorViewer.saveProjectDirectorAction}"/>
			<h:commandButton value="retour" immediate="true" action="admin_main"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>