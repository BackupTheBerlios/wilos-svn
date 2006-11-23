<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos</h1>
		<h:outputText value="- Connexion -" style="h1" />

		<h:panelGrid columns="2">
			<h:outputText value="login" />
			<h:inputText value="#{ParticipantViewer.participant.login}"/>
			
			<h:outputText value="password"/>
			<h:inputSecret value="#{ParticipantViewer.participant.password}"/>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<h:commandButton value="se connecter" action="createParticipant"/>
			<h:commandButton value="creer un compte" action="createParticipant"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>