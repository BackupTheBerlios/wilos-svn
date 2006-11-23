<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos</h1>
		<h:outputText value="- Inscrivez vous -" style="h1" />
 
		<h:panelGrid columns="3">
			<h:outputText value="Nom" />
			<h:inputText id="nom" value="#{ParticipantViewer.participant.name}" required="true"/>
			<h:message for="nom" style="color:red;" showDetail="true"/>
					
			<h:outputText value="Prénom"/>
			<h:inputText id="prenom" value="#{ParticipantViewer.participant.firstname}" required="true"/>
			<h:message for="prenom" style="color:red;" showDetail="true"/>
			
			<h:outputText value="Adresse Email"/>
			<h:inputText id="email" value="#{ParticipantViewer.participant.emailAddress}" required="true">
				<f:validator validatorId="emailValidator"/>
			</h:inputText>
			<h:message for="email" style="color:red;" showDetail="true"/>

			<h:outputText value="Pseudo"/>
			<h:inputText id="login" value="#{ParticipantViewer.participant.login}" required="true"/>
			<h:message for="login" style="color:red;" showDetail="true"/>
			
			<h:outputText value="Mot de passe"/>
			<h:inputSecret id="password" value="#{ParticipantViewer.participant.password}" required="true"/>		
			<h:message for="password" style="color:red;" showDetail="true"/>
			
			<h:outputText value="Confirmer votre mot de passe"/>
			<h:inputSecret id="password2" value="#{ParticipantViewer.passwordConfirmation}">
				<f:validator validatorId="equalValidator"/>
				<f:attribute name="password" value="#{ParticipantViewer.participant.password}"/>
			</h:inputSecret>
			<h:message for="password2" style="color:red;" showDetail="true"/>
			
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton value="S'enregistrer" action="#{ParticipantViewer.saveParticipantAction}"/>
			<h:commandButton value="retour" action="activity"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>