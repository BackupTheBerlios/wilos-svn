package woops2.presentation.activity;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadXmlViewer {
	
	private UploadedFile uploadedFile;
	
	public UploadXmlViewer(){
		
	}

	public UploadedFile getMyUploadedFile() {
		return uploadedFile;
	}

	public void setMyUploadedFile(UploadedFile myUploadedFile) {
		System.err.println(""+myUploadedFile);
		this.uploadedFile = myUploadedFile;
	}
	
}
