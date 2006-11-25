<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>

<f:view>
	<h:form enctype="multipart/form-data">
		<h2>Woops2 - Upload Xml File</h2>
		<br>
		<br>
		<h:outputText value="Choose file to import : " />
		<br>
		<t:inputFileUpload storage="file" value="#{UploadXmlViewer.uploadedFile}" /><br>
		<h:commandButton value="upload !" action="#{UploadXmlViewer.UploadAction}"/>
		<br>
		<br>
		<h:commandButton value="back to home" action="home" />
		
	</h:form>
	<h:messages>
		</h:messages>
</f:view>
</body>

</html>
