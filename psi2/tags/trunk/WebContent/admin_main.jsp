<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration Wilos</title>
</head>
<body>
<f:view>
	<h:form id="form">
		<h1>Wilos - Administration</h1>
		<h:outputText value="- Menu principal -" style="h1" />
		<br><br>
		-<h:commandLink id="createProcessManager"
	                   action="processManager_create"
	                   immediate="true">
	   		<h:outputText
	                   value="Créer un nouveau Process Manager"/>
	  	</h:commandLink>
	  	<br>
	  	-<h:commandLink id="createProjectDirector"
	                   action="projectDirector_create"
	                   immediate="true">
	   		<h:outputText
	                   value="Créer un nouveau Project Director"/>
	  	</h:commandLink>
	  	<br><br>
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