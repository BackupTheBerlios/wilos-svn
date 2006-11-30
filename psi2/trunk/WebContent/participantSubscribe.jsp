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
		<h:messages style="color:red;" showDetail="true"/>
		
		<h:panelGrid columns="2">
			<h:outputText value="Nom" />
			<h:inputText id="nom" value="#{ParticipantBean.participant.name}" required="true"/>
					
			<h:outputText value="Prénom"/>
			<h:inputText id="prenom" value="#{ParticipantBean.participant.firstname}" required="true"/>
			
			<h:outputText value="Adresse Email"/>
			<h:inputText id="email" size="30" value="#{ParticipantBean.participant.emailAddress}" required="true">
				<f:validator validatorId="emailValidator"/>
			</h:inputText>

			<h:outputText value="Pseudo"/>
			<h:inputText id="login" value="#{ParticipantBean.participant.login}" required="true"/>

			<h:outputText value="Mot de passe"/>
			<h:inputSecret id="equal1" value="#{ParticipantBean.participant.password}" required="true">		
				<f:validateLength minimum="6" />
			</h:inputSecret>
			
			<h:outputText value="Confirmez votre mot de passe"/>
			<h:inputSecret id="equal2" value="#{ParticipantBean.passwordConfirmation}" required="true">
				<f:validateLength minimum="6" />
				<f:validator validatorId="equalValidator"/>
			</h:inputSecret>
			
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton value="S'enregistrer" action="#{ParticipantBean.saveParticipantAction}"/>
			<h:commandButton immediate="true" value="Retour" action="connect"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>