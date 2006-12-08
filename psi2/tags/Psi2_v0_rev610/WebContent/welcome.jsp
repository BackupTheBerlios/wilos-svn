<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<head>
<title>Welcome</title>
</head>
<body>
<f:view>
	<h:form id="form">
		<h1>Bienvenue dans Wilos</h1>
		<br>
		-<h:commandLink id="createProject"
	                   action="project_create"
	                   immediate="true">
	   		<h:outputText
	                   value="Créer un nouveau Projet"/>
	  	</h:commandLink>
		<br>
		  	-<h:commandLink id="logout"
		                   action="#{LoginBean.logoutAction}"
		                   immediate="true">
		   		<h:outputText
		                   value="Se déconnecter"/>
		  	</h:commandLink>
	 </h:form>
</f:view>
</body>
</html>