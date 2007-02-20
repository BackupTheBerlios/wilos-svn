package wilos.presentation.web.upload;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;

import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class XmlFileImportBean {

	private int percent = -1;

	private PersistentFacesState state = null;

	private File file = null;

	private ProcessService processService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	private String fileName = "";

	private String contentType = "";

	private String uploadStatus = "" ;

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String _uploadStatus) {
		this.uploadStatus = _uploadStatus;
	}

	public XmlFileImportBean() {
		state = PersistentFacesState.getInstance();
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getPercent() {
		return percent;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void uploadFileActionListener(ActionEvent event) {
		
		InputFile inputFile = (InputFile) event.getSource();
		
		if(inputFile.getFile() == null && file == null)
		{
			ResourceBundle bundle = ResourceBundle.getBundle(
					"wilos.resources.messages", FacesContext.getCurrentInstance()
							.getApplication().getDefaultLocale());
			FacesMessage message = new FacesMessage();
			message
					.setSummary(bundle
							.getString("XmlFileImportBean.noFile"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, message);
		}
		else
		{
			
			
			if (inputFile.getStatus() == InputFile.SAVED) {
				fileName = inputFile.getFileInfo().getFileName();
				contentType = inputFile.getFileInfo().getContentType();
				setFile(inputFile.getFile());
			}
	
			if (! contentType.equalsIgnoreCase("application/zip")) {
				if (! contentType.equalsIgnoreCase("text/xml")) {
					uploadStatus = "File type error! Expecting XML or ZIP, please select another file." ;
					file.delete() ;
					logger.debug("### XmlFileImportBean ### File type error (got '"+contentType+"') - Deleting file");
					return ;
				}
				else {
					uploadStatus = "XML file successfully uploaded to server!" ;
				}
			}
			else {
				uploadStatus = "ZIP file successfully uploaded to server!" ;
			}
	
			if (inputFile.getStatus() == InputFile.INVALID) {
				inputFile.getFileInfo().getException().printStackTrace();
			}
	
			if (inputFile.getStatus() == InputFile.SIZE_LIMIT_EXCEEDED) {
				inputFile.getFileInfo().getException().printStackTrace();
			}
	
			if (inputFile.getStatus() == InputFile.UNKNOWN_SIZE) {
				inputFile.getFileInfo().getException().printStackTrace();
			}
			ExternalContext extCtx = FacesContext.getCurrentInstance()
					.getExternalContext();
			// File destFile = new File("/upload/"+file.getName());
			logger.debug("### fichier uploade = " + file.getPath() + " => "
					+ file.getName() + " ###");
			try {
				logger.debug("### getCanonicalPath = " + file.getCanonicalPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.debug("### getAbsoluteFile = " + file.getAbsoluteFile());
			logger.debug("### getRequestContextPath = "
					+ extCtx.getRequestContextPath());
			logger.debug("### getRequestPathInfo = " + extCtx.getRequestPathInfo());
			extCtx.getResourceAsStream("");
	
	
			try {
				Process p = processService.spelpParsingXML(file);
				// save the process
				logger.debug("### XmlFileImportBean ### action -> id=" + p.getId());
				/* id = */
				this.processService.saveProcess(p);
			} catch (Exception e) {
				logger.error("### XmlFileImportBean ### action -> " + e);
			}
			
			
		}
	}

	public void progressListener(EventObject event) {
		InputFile file = (InputFile) event.getSource();
		if(file.getFile() != null)
		{
			this.percent = file.getFileInfo().getPercent();
			try {
				if (state != null) {
					state.render();
				} else {
					System.out.println("state is null");
				}
	
			} catch (RenderingException ee) {
				System.out.println(ee.getMessage());
			}
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	private int status;

	public String callAction() {
		if (status == InputFile.SAVED) {
			return "saved";
		}
		return "";
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
}
