<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<html>
<body>
<f:view>
	<h:form id="form">
		<h2>Wilos - participant logging</h2>
		<h:outputText value="- Inscrivez vous -" style="h2" />
		
		<h:panelGrid columns="2">
			<h:outputText value="Nom : "/>
			<h:inputText value="#{ParticipantViewer.participant.name}"/>

			<h:outputText value="Prénom : "/>
			<h:inputText value="#{ParticipantViewer.participant.firstname}"/>
			
			<h:outputText value="Adresse Email : "/>
			<h:inputText value="#{ParticipantViewer.participant.emailAddress}"/>
	
			<h:outputText value="Pseudo : "/>
			<h:inputText value="#{ParticipantViewer.participant.login}"/>
	
			<h:outputText value="Mot de passe : "/>
			<h:inputText value="#{ParticipantViewer.participant.password}"/>		
			
			<h:commandButton value="save" action="#{ParticipantViewer.saveParticipantAction}"/>
			<h:commandButton value="back" action="activity"/>
		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>