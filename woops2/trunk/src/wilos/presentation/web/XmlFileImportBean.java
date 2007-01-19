package wilos.presentation.web;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.presentation.web.icefaces.tree.TreeBean;

import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class XmlFileImportBean {

	private int percent = -1;

	private PersistentFacesState state = null;

	private File file = null;

	private ProcessService processService;

	private TreeBean treeBean;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private String choosenExtensionFile;
		
	private SelectItem[] fileExtensions = new SelectItem[]{
        new SelectItem("xml"),
        new SelectItem("zip"),
	};

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

	public void action(ActionEvent event) {
		InputFile inputFile = (InputFile) event.getSource();
		if (inputFile.getStatus() == InputFile.SAVED) {
			fileName = inputFile.getFileInfo().getFileName();
			contentType = inputFile.getFileInfo().getContentType();
			setFile(inputFile.getFile());
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
		logger.debug("### fichier = " + file.getPath() + " => "
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

		//String id = null;
		/*
		TreeViewBean treeBean = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		treeBean = (TreeViewBean) facesContext.getApplication()
				.getVariableResolver().resolveVariable(facesContext,
						"TreeViewBean");
		*/
		try {
			Process p = processService.spelpParsingXML(file);
			// save the process
			logger.debug("###XmlFileImportBean ### action -> id=" + p.getId());
			/* id = */
			this.processService.saveProcess(p);
		} catch (Exception e) {
			logger.error("### XmlFileImportBean ### action -> " + e);
		}

		//treeBean.setProcessId(id);
	}

	public void progress(EventObject event) {
		InputFile file = (InputFile) event.getSource();
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

	private String fileName = "";

	private String contentType = "";

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

	public TreeBean getTreeBean() {
		return treeBean;
	}

	public void setTreeBean(TreeBean treeBean) {
		this.treeBean = treeBean;
	}

	public SelectItem[] getFileExtensions() {
		return fileExtensions;
	}

	public String getChoosenExtensionFile() {
		return choosenExtensionFile;
	}

	public void setChoosenExtensionFile(String _choosenExtensionFile) {
		this.choosenExtensionFile = _choosenExtensionFile;
	}
}
