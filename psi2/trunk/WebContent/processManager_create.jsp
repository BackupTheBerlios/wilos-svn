<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos - Administration</h1>
		<h:outputText value="- Ajout d'un nouveau Process Manager -" style="h1" />
 		<h:messages style="color:red;" showDetail="true"/>
 		
		<h:panelGrid columns="2">
			<h:outputText value="Nom" />
			<h:inputText id="nom" value="#{ProcessManagerBean.processManager.name}" required="true"/>
					
			<h:outputText value="Prénom"/>
			<h:inputText id="prenom" value="#{ProcessManagerBean.processManager.firstname}" required="true"/>

			<h:outputText value="Pseudo"/>
			<h:inputText id="login" value="#{ProcessManagerBean.processManager.login}" required="true"/>
			
			<h:outputText value="Mot de passe"/>
			<h:inputSecret id="equal1" value="#{ProcessManagerBean.processManager.password}" required="true">		
				<f:validateLength minimum="6" />
			</h:inputSecret>
			
			<h:outputText value="Confirmer le mot de passe"/>
			<h:inputSecret id="equal2" value="#{ProcessManagerBean.passwordConfirmation}">
				<f:validateLength minimum="6" />
				<f:validator validatorId="equalValidator"/>
			</h:inputSecret>
			
		</h:panelGrid>
		<h:panelGrid columns="2">
			<h:commandButton value="S'enregistrer" action="#{ProcessManagerBean.saveProcessManagerAction}"/>
			<h:commandButton value="Retour" immediate="true" action="admin_main"/>
		</h:panelGrid>	
	</h:form>
</f:view>
</body>
</html>