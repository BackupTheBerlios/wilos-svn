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
		
		<h:panelGrid columns="2">
			<h:outputText value="Nom" />
			<h:inputText value="#{ParticipantViewer.participant.name}" required="true"/>
			
			<h:outputText value="Prénom"/>
			<h:inputText value="#{ParticipantViewer.participant.firstname}" required="true"/>
		</h:panelGrid>
		
		<h:panelGrid columns="3">	
			<h:outputText value="Adresse Email"/>
			<h:inputText id="email" value="#{ParticipantViewer.participant.emailAddress}" required="true">
				<f:validator validatorId="emailValidator"/>
			</h:inputText>
			<h:message for="email" style="color:red;" showDetail="true"/>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<h:outputText value="Pseudo"/>
			<h:inputText value="#{ParticipantViewer.participant.login}" required="true"/>
	
			<h:outputText value="Mot de passe"/>
			<h:inputSecret value="#{ParticipantViewer.participant.password}" required="true"/>		
			<!-- 
			<h:outputText value="Confirmez"/>
			<h:inputSecret value="#{ParticipantViewer.participant.password}" required="true"/>	
			 -->
			<h:commandButton value="S'enregistrer" action="#{ParticipantViewer.saveParticipantAction}"/>
			<h:commandButton value="retour" action="activity"/>
		</h:panelGrid>	

	</h:form>
</f:view>
</body>
</html>