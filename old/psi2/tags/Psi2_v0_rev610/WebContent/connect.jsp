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
		<br>
		<h:messages style="color:red;" showDetail="true"/>

		<h:panelGrid columns="2">			
			<h:outputText value="Pseudo"/>
			<h:inputText value="#{LoginBean.login}"/>
			
			<h:outputText value="Mot de Passe"/>
			<h:inputSecret value="#{LoginBean.password}"/>
		</h:panelGrid>
		
		<h:panelGrid columns="2">
			<h:commandButton value="Se Connecter" action="#{LoginBean.authentificationAction}"/>
			<h:commandButton immediate="true" value="Créer un Compte" action="createParticipant"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>