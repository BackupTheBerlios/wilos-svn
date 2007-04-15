/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.web.upload;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.presentation.web.template.ActionBean;
import wilos.presentation.web.template.MenuBean;

import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class XmlFileImportBean {

	private int percent = -1;

	private PersistentFacesState state = null;

	private File file = null;

	private ProcessService processService;

	private WebSessionService webSessionService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	private String fileName = "";

	private String contentType = "";

	private String uploadStatus = "";

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

		if (inputFile.getFile() == null && file == null) {
			// stop the progress bar
			this.percent = -1;
			ResourceBundle bundle = ResourceBundle.getBundle(
					"wilos.resources.messages", FacesContext
							.getCurrentInstance().getApplication()
							.getDefaultLocale());
			FacesMessage message = new FacesMessage();
			message.setSummary(bundle.getString("XmlFileImportBean.noFile"));
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, message);
		} else {
			if (inputFile.getStatus() == InputFile.SAVED) {
				fileName = inputFile.getFileInfo().getFileName();
				contentType = inputFile.getFileInfo().getContentType();
				setFile(inputFile.getFile());
			}

			if (!contentType.equalsIgnoreCase("application/zip")) {
				if (!contentType.equalsIgnoreCase("text/xml")) {
					ResourceBundle bundle = ResourceBundle.getBundle(
							"wilos.resources.messages", FacesContext
									.getCurrentInstance().getApplication()
									.getDefaultLocale());
					FacesMessage message = new FacesMessage();
					message
							.setSummary(bundle
									.getString("XmlFileImportBean.noGoodExtensionFile"));
					message.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext facesContext = FacesContext
							.getCurrentInstance();
					facesContext.addMessage(null, message);
					file.delete();
					this.percent = -1;
					return;
				} else {
					// xml upload ok
					ResourceBundle bundle = ResourceBundle.getBundle(
							"wilos.resources.messages", FacesContext
									.getCurrentInstance().getApplication()
									.getDefaultLocale());
					uploadStatus = bundle
							.getString("XmlFileImportBean.xmlFileUploadOk");
				}
			} else {
				// zip upload ok
				ResourceBundle bundle = ResourceBundle.getBundle(
						"wilos.resources.messages", FacesContext
								.getCurrentInstance().getApplication()
								.getDefaultLocale());
				uploadStatus = bundle
						.getString("XmlFileImportBean.zipFileUploadOk");
			}

			if (inputFile.getStatus() == InputFile.INVALID) {
				// stop the progress bar
				this.percent = -1;
				// inputFile.getFileInfo().getException().printStackTrace();
				ResourceBundle bundle = ResourceBundle.getBundle(
						"wilos.resources.messages", FacesContext
								.getCurrentInstance().getApplication()
								.getDefaultLocale());
				FacesMessage message = new FacesMessage();
				message
						.setSummary(bundle
								.getString("XmlFileImportBean.noFile"));
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, message);
			} else {

				if (inputFile.getStatus() == InputFile.SIZE_LIMIT_EXCEEDED) {
					inputFile.getFileInfo().getException().printStackTrace();
				}

				if (inputFile.getStatus() == InputFile.UNKNOWN_SIZE) {
					inputFile.getFileInfo().getException().printStackTrace();
				}
				ExternalContext extCtx = FacesContext.getCurrentInstance()
						.getExternalContext();
				logger.debug("### fichier uploade = " + file.getPath() + " => "
						+ file.getName() + " ###");
				try {
					logger.debug("### getCanonicalPath = "
							+ file.getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				logger.debug("### getAbsoluteFile = " + file.getAbsoluteFile());
				logger.debug("### getRequestContextPath = "
						+ extCtx.getRequestContextPath());
				logger.debug("### getRequestPathInfo = "
						+ extCtx.getRequestPathInfo());
				extCtx.getResourceAsStream("");

				// generating current date for file's dedicated directory
				Format formatter = new SimpleDateFormat("_yyyy.MM.dd.HH.mm.ss");
				String stringDateId = formatter.format(new Date());
				logger
						.debug("### XmlFileImportBean ### date generation -> date="
								+ stringDateId);

				try {
					// creating new directory
					File targetDirForUploadedFile = new File(this.file
							.getAbsolutePath().substring(
									0,
									this.file.getAbsolutePath().lastIndexOf(
											File.separator))
							+ File.separator
							+ this.file.getName()
							+ stringDateId);
					targetDirForUploadedFile.mkdirs();

					if (targetDirForUploadedFile.isDirectory()) {
						logger
								.debug("### XmlFileImportBean ### target directory -> path="
										+ targetDirForUploadedFile
												.getAbsolutePath());

						// moving uploaded file to dedicated directory
						File targetFileInNewDir = new File(
								targetDirForUploadedFile.getAbsolutePath()
										+ File.separator + this.file.getName());

						if (!this.file.renameTo(targetFileInNewDir)) {
							throw new Exception("failed to move file");
						} else {
							this.file = targetFileInNewDir;
							logger.debug("### XmlFileImportBean ### "
									+ "file moved successfully -> file path="
									+ this.file.getAbsolutePath());
						}

					} else {
						logger
								.error("### XmlFileImportBean ### target directory -> "
										+ "directory has not been created!");
					}
				} catch (Exception e) {
					logger.error("### XmlFileImportBean ### file move -> " + e);
				}

				try {
					Process p = processService.spelpParsingXML(file);

					// check if the process already exists.
					boolean alreadyExists = false;
					List<Process> list = processService.getProcessesList();
					for (Process tmp : list)
						if (tmp.getGuid().equals(p.getGuid()))
							alreadyExists = true;

					// mise en place du controle du process
					if ((p != null) && (!alreadyExists)) {
						//check that the process name doesn't exist, else rename it with the guid.
						for (Process tmp : list)
							if (tmp.getPresentationName().equals(p.getPresentationName())){
								p.setPresentationName(p.getPresentationName()+ "_" + p.getGuid());
								break;
							}
						
						// save the process
						logger.debug("### XmlFileImportBean ### action -> id="
								+ p.getId());
						/* id = */
						String managerId = (String) this.webSessionService
								.getAttribute(WebSessionService.WILOS_USER_ID);
						this.processService.saveProcess(p, managerId);
						// stop the progress bar
						this.percent = -1;
						ResourceBundle bundle = ResourceBundle.getBundle(
								"wilos.resources.messages", FacesContext
										.getCurrentInstance().getApplication()
										.getDefaultLocale());
						FacesMessage message = new FacesMessage();
						message.setSummary(bundle
								.getString("XmlFileImportBean.processok"));
						message.setSeverity(FacesMessage.SEVERITY_INFO);
						FacesContext facesContext = FacesContext
								.getCurrentInstance();
						facesContext.addMessage(null, message);

						/* Forwards to the list of processes */
						FacesContext context = FacesContext
								.getCurrentInstance();
						ActionBean actionbean = (ActionBean) context
								.getApplication().getVariableResolver()
								.resolveVariable(context, "ActionBean");
						MenuBean menuBean = (MenuBean) facesContext
								.getExternalContext().getSessionMap().get(
										"menu");
						menuBean.getSelectedPanel().setTemplateName(
								actionbean.getProcessManagerMain());
						menuBean.getSelectedPanel().setTemplateNameForARole(
								actionbean.getManageProcesses());
					} else {
						this.percent = -1;
						ResourceBundle bundle = ResourceBundle.getBundle(
								"wilos.resources.messages", FacesContext
										.getCurrentInstance().getApplication()
										.getDefaultLocale());
						FacesMessage message = new FacesMessage();
						if (alreadyExists) {
							message
									.setSummary(bundle
											.getString("XmlFileImportBean.processAlreadyExists"));
							message.setSeverity(FacesMessage.SEVERITY_INFO);
						} else {
							message
									.setSummary(bundle
											.getString("XmlFileImportBean.processNull"));
							message.setSeverity(FacesMessage.SEVERITY_INFO);
						}

						FacesContext facesContext = FacesContext
								.getCurrentInstance();
						facesContext.addMessage(null, message);
					}
				} catch (Exception e) {
					logger.error("### XmlFileImportBean ### action -> " + e);
				}
			}
			state = PersistentFacesState.getInstance();

		}
	}

	public void progressListener(EventObject event) {
		// start the progress bar
		this.percent = 1;
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

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService;
	}

	/**
	 * @param _webSessionService
	 *            the webSessionService to set
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService;
	}
}
