package woops2.presentation.activity;

import java.io.File;
import java.util.EventObject;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import woops2.business.process.ProcessService;
import woops2.model.process.Process;

import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class XmlFileImportViewer {

	private int percent = -1;

	private PersistentFacesState state = null;

	private File file = null;
	
	private ProcessService processService;
	
	private TreeBean treeBean;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	
	
	public XmlFileImportViewer() {
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
		
		Process p = processService.SpelpParsingXML(file);
		//TODO save the process pour upload
		//processService.SaveImportedProcess(p);
		
		ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = theApplicationsServletContext.getRealPath("/upload");
		File f = inputFile.getFile();
		f.renameTo(new File(realPath+inputFile.getFile().getName()));
		
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
}
