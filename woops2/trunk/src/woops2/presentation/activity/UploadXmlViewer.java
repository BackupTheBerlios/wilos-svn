package woops2.presentation.activity;

import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadXmlViewer {
	
	private UploadedFile uploadedFile;
	
	public UploadXmlViewer(){
		
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public String UploadAction()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        try {
          InputStream stream = uploadedFile.getInputStream();
          long fSize = uploadedFile.getSize();
          byte [] buffer = new byte[(int)fSize];
          stream.read(buffer, 0, (int)fSize);
          stream.close();
        } catch (Exception ioe) {
           ioe.printStackTrace();
        }
        return "success";
    } 
	
}
