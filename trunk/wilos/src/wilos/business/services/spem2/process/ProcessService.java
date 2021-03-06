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

package wilos.business.services.spem2.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.checklist.CheckListService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.phase.PhaseService;
import wilos.business.services.spem2.role.RoleDefinitionService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDefinitionService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.business.services.spem2.workbreakdownelement.WorkBreakdownElementService;
import wilos.business.services.util.xml.parser.XMLServices;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.element.ElementDao;
import wilos.hibernate.spem2.guide.GuidanceDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.hibernate.spem2.section.SectionDao;
import wilos.hibernate.spem2.task.StepDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.section.Section;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * ProcessService is a transactional class, that manage operations about
 * process, requested by web pages (?)
 * 
 * @author garwind
 * @author eperico
 * @author soosuske
 * @author Sebastien
 * @author deder
 * @author almiriad
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessService {

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private BreakdownElementService breakdownElementService;

	private WorkBreakdownElementService workBreakdownElementService;

	private PhaseService phaseService;

	private IterationService iterationService;

	private ActivityService activityService;

	private TaskDescriptorService taskDescriptorService;

	private TaskDefinitionService taskDefinitionService;

	private RoleDescriptorService roleDescriptorService;

	private RoleDefinitionService roleDefinitionService;

	private WebSessionService webSessionService;

	private ProcessManagerService processManagerService;

	private CheckListService checkListService;

	private ElementDao elementDao;

	private ProcessDao processDao;

	private StepDao stepDao;

	private ProjectDao projectDao;

	private GuidanceDao guidanceDao;

	private SectionDao sectionDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public Process spelpParsingXML(File _file) {
		Process spelpProcess = null;
		try {
			logger.debug("### ProcessService ### spelpParsingXML " + _file.getAbsolutePath() + " abs path = "
					+ _file.getPath());
			String slash = System.getProperty("file.separator");

			String path = _file.getAbsolutePath().substring(0, _file.getAbsolutePath().lastIndexOf(slash));

			logger.debug("### ProcessService ### spelpParsingXML PATH == " + path);
			spelpProcess = XMLServices.getProcess(_file.getAbsolutePath(), path);
			if (spelpProcess != null) {
				spelpProcess.setFolderPath(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spelpProcess;
	}

	/**
	 * @param _processId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Project getEntireProject(String _projectId) {
		Project project = this.projectDao.getProject(_projectId);
		Set<ConcreteBreakdownElement> bdes = new HashSet<ConcreteBreakdownElement>();

		bdes.addAll(this.concreteBreakdownElementService.getAllConcreteBreakdownElementsFromProject(_projectId));
		project.removeAllConcreteBreakdownElements();
		project.addAllConcreteBreakdownElements(bdes);

		return project;
	}

	/**
	 * 
	 * Method for saving a Process
	 * 
	 * @param _process
	 */
	public void saveProcess(Process _process, String _processManagerId) {

		Process clone = null;

		// clone creation for the save of the dependencies
		try {
			clone = _process.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		// elements of collection getting
		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_process.getBreakdownElements());

		// elements of collection getting
		List<Guidance> guid = new ArrayList<Guidance>();

		// dependencies erasing
		_process.getBreakdownElements().clear();
		_process.getPredecessors().clear();
		_process.getProjects().clear();
		_process.getSuccessors().clear();
		_process.getSuperActivities().clear();

		// save of the project
		this.processDao.saveOrUpdateProcess(_process);
		System.out.println("###Process vide sauve");

		// in function of element type
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				guid = this.parsePhase(ph, guid);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					guid = this.parseIteration(it, guid);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						guid = this.parseActivity(act, guid);
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							guid = this.parseRoleDescriptor(rd, guid);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							guid = this.parseTaskDescriptor(td, guid);
						}
					}
				}
			}
		}

		// saving of the attached guidances to the process
		for (Guidance g : guid) {
			this.parseGuidance(g);
		}

		// destroy the persistance of the collections
		_process.setBreakdownElements(this.activityService.getBreakdownElements(_process));
		_process.setPredecessors(this.workBreakdownElementService.getPredecessors(_process));
		_process.setSuccessors(this.workBreakdownElementService.getSuccessors(_process));
		_process.setSuperActivities(this.breakdownElementService.getSuperActivities(_process));

		// clone dependencies getting
		_process.addAllBreakdownElements(clone.getBreakdownElements());
		_process.addAllPredecessors(clone.getPredecessors());
		_process.addAllSuccessors(clone.getSuccessors());
		_process.addAllSuperActivities(clone.getSuperActivities());

		_process.addProcessManager(this.processManagerService.getProcessManager(_processManagerId));

		// update of the project
		this.processDao.saveOrUpdateProcess(_process);
		System.out.println("###Process sauve");
	}

	/**
	 * 
	 * Method to parse a Phase
	 * 
	 * @param _ph
	 */
	private List<Guidance> parsePhase(Phase _ph, List<Guidance> guid) {

		Phase clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _ph.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_ph.getBreakdownElements());

		// Guides
		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_ph.getGuidances());

		// clean of dependancies of _ph
		_ph.getBreakdownElements().clear();
		_ph.getPredecessors().clear();
		_ph.getSuccessors().clear();
		_ph.getSuperActivities().clear();
		_ph.getGuidances().clear();
		this.phaseService.getPhaseDao().saveOrUpdatePhase(_ph);
		System.out.println("###Phase vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Iteration) {
				Iteration it = (Iteration) bde;
				tmp = this.parseIteration(it, tmp);
			} else {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					tmp = this.parseActivity(act, tmp);
				} else {
					if (bde instanceof RoleDescriptor) {
						RoleDescriptor rd = (RoleDescriptor) bde;
						tmp = this.parseRoleDescriptor(rd, tmp);
					} else {
						TaskDescriptor td = (TaskDescriptor) bde;
						tmp = this.parseTaskDescriptor(td, tmp);
					}
				}
			}
		}

		_ph.setBreakdownElements(this.activityService.getBreakdownElements(_ph));
		_ph.setPredecessors(this.workBreakdownElementService.getPredecessors(_ph));
		_ph.setSuccessors(this.workBreakdownElementService.getSuccessors(_ph));
		_ph.setSuperActivities(this.breakdownElementService.getSuperActivities(_ph));
		_ph.setGuidances(this.activityService.getGuidances(_ph));

		// clone dependencies getting
		_ph.addAllBreakdownElements(clone.getBreakdownElements());
		_ph.addAllPredecessors(clone.getPredecessors());
		_ph.addAllSuccessors(clone.getSuccessors());
		_ph.addAllSuperActivities(clone.getSuperActivities());
		_ph.setGuidances(clone.getGuidances());

		// Parse for guidances
		this.phaseService.getPhaseDao().saveOrUpdatePhase(_ph);
		System.out.println("###Phase sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse an Iteration
	 * 
	 * @param _it
	 */
	private List<Guidance> parseIteration(Iteration _it, List<Guidance> guid) {

		Iteration clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _it.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_it.getBreakdownElements());

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_it.getGuidances());

		_it.getBreakdownElements().clear();
		_it.getPredecessors().clear();
		_it.getSuccessors().clear();
		_it.getSuperActivities().clear();
		_it.getGuidances().clear();
		this.iterationService.getIterationDao().saveOrUpdateIteration(_it);
		System.out.println("###Iteration vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				tmp = this.parseActivity(act, tmp);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					tmp = this.parseRoleDescriptor(rd, tmp);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					tmp = this.parseTaskDescriptor(td, tmp);
				}
			}
		}

		_it.setBreakdownElements(this.activityService.getBreakdownElements(_it));
		_it.setPredecessors(this.workBreakdownElementService.getPredecessors(_it));
		_it.setSuccessors(this.workBreakdownElementService.getSuccessors(_it));
		_it.setSuperActivities(this.breakdownElementService.getSuperActivities(_it));
		_it.setGuidances(this.activityService.getGuidances(_it));

		// clone dependencies getting
		_it.addAllBreakdownElements(clone.getBreakdownElements());
		_it.addAllPredecessors(clone.getPredecessors());
		_it.addAllSuccessors(clone.getSuccessors());
		_it.addAllSuperActivities(clone.getSuperActivities());
		_it.setGuidances(clone.getGuidances());

		this.iterationService.getIterationDao().saveOrUpdateIteration(_it);
		System.out.println("###Iteration sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse an Activity
	 * 
	 * @param _act
	 */
	private List<Guidance> parseActivity(Activity _act, List<Guidance> guid) {

		Activity clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _act.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_act.getBreakdownElements());

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_act.getGuidances());

		_act.getBreakdownElements().clear();
		_act.getPredecessors().clear();
		_act.getSuccessors().clear();
		_act.getSuperActivities().clear();
		_act.getGuidances().clear();
		this.activityService.saveActivity(_act);
		System.out.println("###Activity vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				tmp = this.parseActivity(act, tmp);
			} else {
				if (bde instanceof RoleDescriptor) {
					RoleDescriptor rd = (RoleDescriptor) bde;
					tmp = this.parseRoleDescriptor(rd, tmp);
				} else {
					TaskDescriptor td = (TaskDescriptor) bde;
					tmp = this.parseTaskDescriptor(td, tmp);
				}
			}
		}

		_act.setBreakdownElements(this.activityService.getBreakdownElements(_act));
		_act.setPredecessors(this.workBreakdownElementService.getPredecessors(_act));
		_act.setSuccessors(this.workBreakdownElementService.getSuccessors(_act));
		_act.setSuperActivities(this.breakdownElementService.getSuperActivities(_act));
		_act.setGuidances(this.activityService.getGuidances(_act));

		// clone dependencies getting
		_act.addAllBreakdownElements(clone.getBreakdownElements());
		_act.addAllPredecessors(clone.getPredecessors());
		_act.addAllSuccessors(clone.getSuccessors());
		_act.addAllSuperActivities(clone.getSuperActivities());
		_act.setGuidances(clone.getGuidances());

		this.activityService.saveActivity(_act);
		System.out.println("###Activity sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse a RoleDescriptor
	 * 
	 * @param _rd
	 */
	private List<Guidance> parseRoleDescriptor(RoleDescriptor _rd, List<Guidance> guid) {

		RoleDescriptor clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _rd.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		RoleDefinition rdef = _rd.getRoleDefinition();

		_rd.getAdditionalTasks().clear();
		_rd.getPrimaryTasks().clear();
		_rd.getSuperActivities().clear();
		_rd.setRoleDefinition(null);

		this.roleDescriptorService.getRoleDescriptorDao().saveOrUpdateRoleDescriptor(_rd);
		System.out.println("###RoleDescriptor vide sauve");

		if (rdef != null) {
			tmp = this.parseRoleDefinition(rdef, tmp);
		}

		// _rd.setAdditionalTasks(this.roleDescriptorService.getAdditionalTasks(_rd));
		_rd.setPrimaryTasks(this.roleDescriptorService.getPrimaryTasks(_rd));
		_rd.setSuperActivities(this.breakdownElementService.getSuperActivities(_rd));

		// _rd.addAllAdditionalTasks(clone.getAdditionalTasks());
		_rd.addAllPrimaryTasks(clone.getPrimaryTasks());
		_rd.addAllSuperActivities(clone.getSuperActivities());
		_rd.setRoleDefinition(clone.getRoleDefinition());

		this.roleDescriptorService.getRoleDescriptorDao().saveOrUpdateRoleDescriptor(_rd);
		System.out.println("###RoleDescriptor sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse a RoleDefinition
	 * 
	 * @param _rdef
	 */
	private List<Guidance> parseRoleDefinition(RoleDefinition _rdef, List<Guidance> guid) {

		RoleDefinition clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _rdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_rdef.getGuidances());

		_rdef.getRoleDescriptors().clear();
		_rdef.getGuidances().clear();

		this.roleDefinitionService.getRoleDefinitionDao().saveOrUpdateRoleDefinition(_rdef);
		System.out.println("###RoleDefinition vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		// _rdef.setRoleDescriptors(this.roleDefinitionService.getRoleDescriptors(_rdef));
		_rdef.setGuidances(this.roleDefinitionService.getGuidances(_rdef));

		// _rdef.addAllRoleDescriptors(clone.getRoleDescriptors());
		_rdef.addAllGuidances(clone.getGuidances());

		this.roleDefinitionService.getRoleDefinitionDao().saveOrUpdateRoleDefinition(_rdef);
		System.out.println("###RoleDefinition sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse a TaskDescriptor
	 * 
	 * @param _td
	 */
	private List<Guidance> parseTaskDescriptor(TaskDescriptor _td, List<Guidance> guid) {

		TaskDescriptor clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _td.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		TaskDefinition tdef = _td.getTaskDefinition();

		_td.getAdditionalRoles().clear();
		_td.getPredecessors().clear();
		_td.getSuccessors().clear();
		_td.getSuperActivities().clear();
		_td.setMainRole(null);
		_td.setTaskDefinition(null);

		this.taskDescriptorService.getTaskDescriptorDao().saveOrUpdateTaskDescriptor(_td);
		System.out.println("###TaskDescriptor vide sauve");

		if (tdef != null) {
			tmp = this.parseTaskDefinition(tdef, tmp);
		}

		// To decomment when you want to manage additionalRoles
		_td.setAdditionalRoles(this.taskDescriptorService.getAdditionalRoles(_td));
		_td.setPredecessors(this.workBreakdownElementService.getPredecessors(_td));
		_td.setSuccessors(this.workBreakdownElementService.getSuccessors(_td));
		_td.setSuperActivities(this.breakdownElementService.getSuperActivities(_td));

		// clone dependencies getting
		_td.addAllAdditionalRoles(clone.getAdditionalRoles());
		_td.addAllPredecessors(clone.getPredecessors());
		_td.addAllSuccessors(clone.getSuccessors());
		_td.addAllSuperActivities(clone.getSuperActivities());
		_td.setMainRole(clone.getMainRole());
		_td.setTaskDefinition(clone.getTaskDefinition());

		this.taskDescriptorService.getTaskDescriptorDao().saveOrUpdateTaskDescriptor(_td);
		System.out.println("###TaskDescriptor sauve");

		return tmp;
	}

	/**
	 * 
	 * @param _tdef
	 */
	private List<Guidance> parseTaskDefinition(TaskDefinition _tdef, List<Guidance> guid) {

		TaskDefinition clone = null;

		List<Guidance> tmp = new ArrayList<Guidance>();
		tmp.addAll(guid);

		try {
			clone = _tdef.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		Set<Guidance> guidances = new HashSet<Guidance>();
		guidances.addAll(_tdef.getGuidances());

		List<Step> steps = new ArrayList<Step>();
		// recuperation des breakdownelements du processus
		steps.addAll(_tdef.getSteps());

		_tdef.getSteps().clear();
		_tdef.getTaskDescriptors().clear();
		_tdef.getGuidances().clear();

		this.taskDefinitionService.getTaskDefinitionDao().saveOrUpdateTaskDefinition(_tdef);
		System.out.println("###TaskDefinition vide sauve");

		for (Guidance g : guidances) {
			if (!tmp.contains(g)) {
				tmp.add(g);
			}
		}

		for (Step step : steps) {
			this.parseStep(step);
		}

		_tdef.setSteps(this.taskDefinitionService.getSteps(_tdef));
		// _tdef.setTaskDescriptors(this.roleDefinitionService.getRoleDescriptors(_rdef));
		_tdef.setGuidances(this.taskDefinitionService.getGuidances(_tdef));

		_tdef.addAllSteps(clone.getSteps());
		// _tdef.addAllTaskDesciptors(clone.getTaskDescriptors());
		_tdef.addAllGuidances(clone.getGuidances());

		this.taskDefinitionService.getTaskDefinitionDao().saveOrUpdateTaskDefinition(_tdef);
		System.out.println("###TaskDefinition sauve");

		return tmp;
	}

	/**
	 * 
	 * Method to parse a Step
	 * 
	 * @param _step
	 */
	private void parseStep(Step _step) {

		this.stepDao.saveOrUpdateStep(_step);
		System.out.println("###Step sauve");
	}

	/**
	 * 
	 * Method to parse a Guidance
	 * 
	 * @param _step
	 */
	private void parseGuidance(Guidance _guidance) {

		if (_guidance instanceof CheckList) {
			CheckList cl = (CheckList) _guidance;
			this.parseCheckList(cl);
		} else {
			this.guidanceDao.saveOrUpdateGuidance(_guidance);
			System.out.println("###Guidance sauve");
		}
	}

	/**
	 * Method to parse a CheckList
	 * 
	 * @param _checkList
	 */
	private void parseCheckList(CheckList _checkList) {

		CheckList clone = null;

		try {
			clone = _checkList.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		List<Section> sections = new ArrayList<Section>();
		sections.addAll(_checkList.getSections());

		_checkList.getSections().clear();

		this.checkListService.saveCheckList(_checkList);
		System.out.println("###CheckList vide sauvee");

		for (Section section : sections) {
			this.parseSection(section);
		}

		_checkList.setSections(this.checkListService.getSections(_checkList));

		_checkList.addAllSection(clone.getSections());

		this.checkListService.saveCheckList(_checkList);
		System.out.println("###CheckList sauvee");
	}

	/**
	 * Method to parse a section
	 * 
	 * @param _section
	 */
	private void parseSection(Section _section) {
		this.sectionDao.saveOrUpdateSection(_section);
		System.out.println("###Section sauvee");
	}

	/**
	 * Return processes list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Process> getProcessesList() {
		return this.processDao.getAllProcesses();
	}
	
	/**
	 * 
	 * @param _guid
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Process getProcessFromGuid(String _guid) {
		return this.processDao.getProcessFromGuid(_guid);
	}

	/**
	 * Project instanciation
	 * 
	 * @param _project
	 * @param _process
	 * @param list
	 */
	public void projectInstanciation(Project _project, Process _process, List<HashMap<String, Object>> list) {

		// elements of collection getting
		Set<BreakdownElement> forInstanciation = this.activityService.getInstanciableBreakdownElements(_process);

		for (BreakdownElement bde : forInstanciation) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				int occ = this.giveNbOccurences(ph.getId(), list, false);
				this.phaseService.phaseInstanciation(_project, ph, _project, list, occ, false);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					int occ = this.giveNbOccurences(it.getId(), list, false);
					this.iterationService.iterationInstanciation(_project, it, _project, list, occ, false);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						int occ = this.giveNbOccurences(act.getId(), list, false);
						this.activityService.activityInstanciation(_project, act, _project, list, occ, false);
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							int occ = this.giveNbOccurences(td.getId(), list, false);
							this.taskDescriptorService.taskDescriptorInstanciation(_project, td, _project, occ, false);
						}
					}
				}
			}
		}

		_project.setProcess(_process);

		this.projectDao.saveOrUpdateProject(_project);
		System.out.println("### Project update");
	}
	
	/**
	 * 
	 * @param _project
	 * @param _process
	 * @param list
	 */
	public void projectUpdate(Project _project, Process _process, List<HashMap<String, Object>> list) {

		// elements of collection getting
		Set<BreakdownElement> forInstanciation = this.activityService.getInstanciableBreakdownElements(_process);

		for (BreakdownElement bde : forInstanciation) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
				cacts.add(_project);
				int occ = this.giveNbOccurences(ph.getId(), list, true);
				this.phaseService.phaseUpdate(_project, ph, cacts, list, occ);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
					cacts.add(_project);
					int occ = this.giveNbOccurences(it.getId(), list, true);
					this.iterationService.iterationUpdate(_project, it, cacts, list, occ);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
						cacts.add(_project);
						int occ = this.giveNbOccurences(act.getId(), list, true);
						this.activityService.activityUpdate(_project, act, cacts, list, occ);
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
							cacts.add(_project);
							int occ = this.giveNbOccurences(td.getId(), list, true);
							this.taskDescriptorService.taskDescriptorUpdate(_project, td, cacts, occ);
						}
					}
				}
			}
		}

		_project.setProcess(_process);

		this.projectDao.saveOrUpdateProject(_project);
		System.out.println("### Project update");
	}

	/**
	 * 
	 * @param _id
	 * @param list
	 * @return
	 */
	private int giveNbOccurences(String _id, List<HashMap<String, Object>> list, boolean _isInstanciated) {

		int nb;
		if (!_isInstanciated) nb = 1;
		else nb = 0;

		for (HashMap<String, Object> hashMap : list) {
			if (((String) hashMap.get("id")).equals(_id)) {
				try {
					nb = ((Integer) hashMap.get("nbOccurences")).intValue();
				} catch (Exception e) {
				}
				break;
			}
		}

		return nb;
	}

	/**
	 * Getter of processDao.
	 * 
	 * @return the processDao.
	 */
	public ProcessDao getProcessDao() {
		return processDao;
	}

	/**
	 * Setter of processDao.
	 * 
	 * @param _processDao
	 *            The processDao to set.
	 */
	public void setProcessDao(ProcessDao _processDao) {
		this.processDao = _processDao;
	}

	/**
	 * Getter of elementDao.
	 * 
	 * @return the elementDao.
	 */
	public ElementDao getElementDao() {
		return elementDao;
	}

	/**
	 * Setter of elementDao.
	 * 
	 * @param elementDao
	 *            The elementDao to set.
	 */
	public void setElementDao(ElementDao elementDao) {
		this.elementDao = elementDao;
	}

	/**
	 * Getter of stepDao.
	 * 
	 * @return the stepDao.
	 */
	public StepDao getStepDao() {
		return stepDao;
	}

	/**
	 * Setter of stepDao.
	 * 
	 * @param stepDao
	 *            The stepDao to set.
	 */
	public void setStepDao(StepDao stepDao) {
		this.stepDao = stepDao;
	}

	public BreakdownElementService getBreakdownElementService() {
		return this.breakdownElementService;
	}

	public void setBreakdownElementService(BreakdownElementService _breakdownElementService) {
		this.breakdownElementService = _breakdownElementService;
	}

	public PhaseService getPhaseService() {
		return this.phaseService;
	}

	public void setPhaseService(PhaseService _phaseService) {
		this.phaseService = _phaseService;
	}

	public ActivityService getActivityService() {
		return this.activityService;
	}

	public void setActivityService(ActivityService _activityService) {
		this.activityService = _activityService;
	}

	public IterationService getIterationService() {
		return this.iterationService;
	}

	public void setIterationService(IterationService _iterationService) {
		this.iterationService = _iterationService;
	}

	public TaskDescriptorService getTaskDescriptorService() {
		return this.taskDescriptorService;
	}

	public void setTaskDescriptorService(TaskDescriptorService _taskDescriptorService) {
		this.taskDescriptorService = _taskDescriptorService;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public GuidanceDao getGuidanceDao() {
		return guidanceDao;
	}

	public void setGuidanceDao(GuidanceDao guidanceDao) {
		this.guidanceDao = guidanceDao;
	}

	/**
	 * @return the roleDescriptorService
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * @param roleDescriptorService
	 *            the roleDescriptorService to set
	 */
	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService;
	}

	/**
	 * Setter of concreteBreakdownElementService.
	 * 
	 * @param concreteBreakdownElementService
	 *            The concreteBreakdownElementService to set.
	 */
	public void setConcreteBreakdownElementService(ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService;
	}

	/**
	 * @return the workBreakdownElementService
	 */
	public WorkBreakdownElementService getWorkBreakdownElementService() {
		return this.workBreakdownElementService;
	}

	/**
	 * @param _workBreakdownElementService
	 *            the workBreakdownElementService to set
	 */
	public void setWorkBreakdownElementService(WorkBreakdownElementService _workBreakdownElementService) {
		this.workBreakdownElementService = _workBreakdownElementService;
	}

	/**
	 * @return the roleDefinitionService
	 */
	public RoleDefinitionService getRoleDefinitionService() {
		return this.roleDefinitionService;
	}

	/**
	 * @param _roleDefinitionService
	 *            the roleDefinitionService to set
	 */
	public void setRoleDefinitionService(RoleDefinitionService _roleDefinitionService) {
		this.roleDefinitionService = _roleDefinitionService;
	}

	/**
	 * @return the taskDefinitionService
	 */
	public TaskDefinitionService getTaskDefinitionService() {
		return this.taskDefinitionService;
	}

	/**
	 * @param _taskDefinitionService
	 *            the taskDefinitionService to set
	 */
	public void setTaskDefinitionService(TaskDefinitionService _taskDefinitionService) {
		this.taskDefinitionService = _taskDefinitionService;
	}

	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService;
	}

	public ProcessManagerService getProcessManagerService() {
		return processManagerService;
	}

	public void setProcessManagerService(ProcessManagerService _processManagerService) {
		this.processManagerService = _processManagerService;
	}

	public CheckListService getCheckListService() {
		return checkListService;
	}

	public void setCheckListService(CheckListService _checkListService) {
		this.checkListService = _checkListService;
	}

	public SectionDao getSectionDao() {
		return sectionDao;
	}

	public void setSectionDao(SectionDao _sectionDao) {
		this.sectionDao = _sectionDao;
	}
}
