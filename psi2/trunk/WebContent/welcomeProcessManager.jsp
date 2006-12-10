<f:view xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ice="http://www.icesoft.com/icefaces/component">

<f:loadBundle basename="wilos.resources.messages" var="msgs" />
	<ice:outputDeclaration doctypeRoot="HTML"
		doctypePublic="-//W3C//DTD HTML 4.01 Transitional//EN"
		doctypeSystem="http://www.w3.org/TR/html4/loose.dtd" />
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
	<title>Welcome</title>

	<ice:outputText style="padding-top:0px; padding-bottom:0px;"
		value="#{styleBean.style}" escape="false" />
	<link href="css/showcase_style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	<ice:form id="connected_form">
	<h1><ice:outputText value="Bienvenue dans Wilos"/></h1>
		<br />
	  	<ice:commandLink id="addProcess" action="process_create" immediate="true">
			<ice:outputText value="Ajouter un processus"/>
	  	</ice:commandLink>
		<br />
		<ice:commandLink id="logout" action="#{LoginBean.logoutAction}" immediate="true">
			<ice:outputText value="Se déconnecter"/>
		</ice:commandLink>
	</ice:form>
</body>
</html>
</f:view>