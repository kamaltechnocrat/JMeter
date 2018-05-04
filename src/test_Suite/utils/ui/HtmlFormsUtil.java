/**
 * 
 */
package test_Suite.utils.ui;

import static watij.finders.FinderFactory.*;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.name;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.alt;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.ErrorUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.lib.cases.*;
import test_Suite.lib.workflow.*;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProgStepsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.cases.*;
import test_Suite.constants.users.*;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.*;
import test_Suite.lib.users.*;
import watij.runtime.ie.IE;
import watij.dialogs.ConfirmDialog;
import watij.elements.HtmlElement;


/**
 * @author apankov
 * Generic fill HTML forms methods
 * All the methods in this class take ArrayList object as a Parameter and differentiate between
 * Update and Submit by another parameter
 */
@SuppressWarnings("rawtypes")
public class HtmlFormsUtil {
	private static Log log = LogFactory.getLog(HtmlFormsUtil.class);

	public enum ECreateUpdate
	{
		objectCreate,  // = 0
		objectUpdate,  // = 1
		objectDelete   // = 2
	}
	
	public static final String staffShowAll = "showallgroups";
	public static final String staffOrg     = "org";
	public static final String staffGroups  = "groups";
	public static final String staffUsers   = "users";
	public static final String staffSearch  = "search";
	 
	public static void fillProgApproveDetails(ProgramApprove pap)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();

			if(pap.getCreateOrUpdate() == ECreateUpdate.objectCreate.ordinal())
				ClicksUtil.clickImageByAlt(IClicksConst.newProgram);
			else
			{
				ClicksUtil.clickLinks(pap.getProgApproveId());
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + pap.getProgApproveId());
			}
			if(pap.getProgApproveId() != null)
				ie.textField(id, IProgramsConst.fundingOpp_prog_Ident).set(pap.getProgApproveId());
			if(pap.getProgApproveStartDate() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_START_DATE).set(pap.getProgApproveStartDate());
			if(pap.getProgApproveEndDate() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_END_DATE).set(pap.getProgApproveEndDate());
			if(pap.getProgApproveOfficer() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_MANAGER_MENU).select(pap.getProgApproveOfficer());
			if(pap.getProgApprovePrOrg() != null)
			{
				final String aprovePrOrg = pap.getProgApprovePrOrg();
				log.info("Primary org: " + aprovePrOrg);
				new Thread(new Runnable() { 
					public void run() { 
						try { 
								IE ie = IEUtil.getActiveIE();
								ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(aprovePrOrg);
    						} 
						catch (Exception e) { 
						} 
					} 
				}).start();

				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
			}
			if(pap.getProgApproveOrgAccess() != null)
			{
				final String aproveOrgAccess = pap.getProgApproveOrgAccess();
				new Thread(new Runnable() { 
					public void run() { 
						try { 
								IE ie = IEUtil.getActiveIE();
								ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(aproveOrgAccess);
    						} 
						catch (Exception e) { 
						} 
					} 
				}).start();

				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
			}
			if(pap.getProgApproveStatus() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).select(pap.getProgApproveStatus());
			if(pap.getProgApproveForm() != null)
				ie.selectList(name, IProgramsConst.fundingOpp_PROG_FORM_SELECT).select(pap.getProgApproveForm());
			if(pap.getProgApproveNames() != null && pap.getProgApproveNames().length > 0)
			{
				for(int j=0; j<pap.getProgApproveNames().length; j++)
				{
					if(ie.textField(id, "/" + j + IProgramsConst.fundingOpp_PROG_TEXT).exists())
						ie.textField(id, "/" + j + IProgramsConst.fundingOpp_PROG_TEXT).set(pap.getProgApproveNames()[j]);		
				}
			}
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ErrorUtil.checkForRecordExistsError(IErrorConst.msgMandatoryFieldOmited);
			ErrorUtil.checkForRecordExistsError(IErrorConst.msgPrimaryOrgRequired);

			if(pap.getProgApproveAdmin() != null && pap.getProgApproveAdmin().length > 0)
				setProgramAdministrators(IClicksConst.openProgramApprovalProcess,
						pap.getProgApproveId(), pap.getProgApproveAdmin());
			if(pap.getProgApproveStaff() != null)
				setProgramStaff(IClicksConst.openProgramApprovalProcess,
						pap.getProgApproveId(), pap.getProgApproveStaff());
			if(pap.getProgApproveSteps() != null && pap.getProgApproveSteps().size() > 0)
				setProgramSteps(IClicksConst.openProgramApprovalProcess, 
						pap.getProgApproveId(), pap.getProgApproveSteps());
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillProgApprovForm()  " + ex.getMessage());
		}
	}
	
	public static void fillProgramDetails(Program prog)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();

			if(prog.getCreateOrUpdate() == ECreateUpdate.objectCreate.ordinal())
				ClicksUtil.clickImageByAlt(IClicksConst.newProgram);
			else
			{
				ClicksUtil.clickLinks(prog.getProgName());
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + prog.getProgName());
			}
			if(prog.getProgName() != null)
				ie.textField(id, IProgramsConst.fundingOpp_prog_Ident).set(prog.getProgName());
			if(prog.getStartDate() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_START_DATE).set(prog.getStartDate());
			if(prog.getEndDate() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_END_DATE).set(prog.getEndDate());
			if(prog.getProgramOfficer() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_MANAGER_MENU).select(prog.getProgramOfficer());
			if(prog.getPrimaryOrg() != null)
			{
				final String aprovePrOrg = prog.getPrimaryOrg();
				log.info("Primary org: " + aprovePrOrg);
				new Thread(new Runnable() { 
					public void run() { 
						try { 
								IE ie = IEUtil.getActiveIE();
								ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(aprovePrOrg);
    						} 
						catch (Exception e) { 
						} 
					} 
				}).start();

				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
			}
			if(prog.getOrgAccess() != null)
			{
				final String aproveOrgAccess = prog.getOrgAccess();
				log.info("Org access: " + aproveOrgAccess);
				new Thread(new Runnable() { 
					public void run() { 
						try { 
								IE ie = IEUtil.getActiveIE();
								ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(aproveOrgAccess);
    						} 
						catch (Exception e) { 
						} 
					} 
				}).start();

				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
			}
			if(prog.getProgStatus() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).select(prog.getProgStatus());
			if(prog.getProgPreFix() != null)
				ie.textField(id, IProgramsConst.fundingOpp_Proj_Num_Template).set(prog.getProgPreFix());
			if(prog.getProgRegistrOpens() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_REG_START_DATE).set(prog.getProgRegistrOpens());
			if(prog.getProgFundOpportunityProcess() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_PAP_SELECT).select(prog.getProgFundOpportunityProcess());
			if(prog.getProgRegistrOpenHH() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_REG_START_HH).select(prog.getProgRegistrOpenHH());
			if(prog.getProgRegistrOpenMM() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_REG_START_MM).select(prog.getProgRegistrOpenMM());
			if(prog.getProgRegistrCloses() != null)
				ie.textField(id, IProgramsConst.fundingOpp_PROG_REG_END_DATE).set(prog.getProgRegistrCloses());
			if(prog.getProgRegistrCloseHH() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_REG_END_HH).select(prog.getProgRegistrCloseHH());
			if(prog.getProgRegistrCloseMM() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_REG_END_MM).select(prog.getProgRegistrCloseMM());
			if(prog.isCreationFOProjDisable() == true)
				ie.checkbox(id, IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).set();
			else
				ie.checkbox(id, IProgramsConst.fundingOpp_PROG_FO_PROJ_CREATE).clear();
			if(prog.isCompleteAFRequired() == true)
				ie.checkbox(id, IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).set();
			else
				ie.checkbox(id, IProgramsConst.fundingOpp_PROG_FORM_COMPL_REC).clear();
			if(prog.getProgFullNames() != null && prog.getProgFullNames().length > 0)
			{
				for(int j=0; j<prog.getProgFullNames().length; j++)
				{
					if(ie.textField(id, "/" + j + IProgramsConst.fundingOpp_PROG_TEXT).exists())
						ie.textField(id, "/" + j + IProgramsConst.fundingOpp_PROG_TEXT).set(prog.getProgFullNames()[j]);		
				}
			}
			if(prog.getProgramFormName() != null)
				ie.selectList(name, IProgramsConst.fundingOpp_PROG_FORM_SELECT).select(prog.getProgramFormName());
			if(prog.getPublicationFormName() != null)
				ie.selectList(id, IProgramsConst.fundingOpp_PUBL_FORM_SELECT).select(prog.getPublicationFormName());
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ErrorUtil.checkForRecordExistsError(IErrorConst.msgMandatoryFieldOmited);
			ErrorUtil.checkForRecordExistsError(IErrorConst.msgPrimaryOrgRequired);
			
			if(prog.getProgAdmin() != null && prog.getProgAdmin().length > 0)
				setProgramAdministrators(IClicksConst.openFundingOppsListLnk,
						prog.getProgName(), prog.getProgAdmin());
			if(prog.getProgStaff() != null)
				setProgramStaff(IClicksConst.openFundingOppsListLnk, prog.getProgName(),
						prog.getProgStaff());
			if(prog.getProgSteps() != null && prog.getProgSteps().size() > 0)
				setProgramSteps(IClicksConst.openFundingOppsListLnk, 
						prog.getProgName(), prog.getProgSteps());
			
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillProgramDetails()  " + ex.getMessage());
		}
	}
	
	public static void setProgramAdministrators(String programType, String programId, String [] programAdmins)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(programType);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				ClicksUtil.clickLinks(programId);
				log.info("Start creating/updating Program Administrators");
				ClicksUtil.clickImageByAlt(IProgramsConst.MANAGE_PROG_ADMIN);
				for(int i=0; i<programAdmins.length; i++)
				{
					if(GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_SELECTED, 
							programAdmins[i]) == false)
					{
						if(GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_AVAILABL, 
								programAdmins[i]) == true)
						{
							ie.selectList(id, IProgramsConst.PROG_STAFF_AVAILABL).select(programAdmins[i]);
							ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
						}
						else
							log.warn(IErrorConst.tstTestUnknown + 
								" Requested Program Admin does not exist in both lists:  " + programAdmins[i]);
					}
				}
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickLinks(programType);
			}
			else
				log.warn("Program link not found: " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setProgramAdministrators()  " + ex.getMessage());
		}
	}
	
	public static void setProgramStaff(String programType, String programId, Hashtable programStaff)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(programType);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				ClicksUtil.clickLinks(programId);
				log.info("Start creating/updating Program Staff");
				ClicksUtil.clickImageByAlt(IProgramsConst.MANAGE_PROG_STAFF);
			     boolean showall = Boolean.parseBoolean(programStaff.get(staffShowAll).toString());
			     String  org     = (String)programStaff.get(staffOrg);
			     String [] groups = (String[])(programStaff.get(staffGroups));
			     
			     if (showall == true) 
			         ie.checkbox(id, IProgramsConst.PROG_SHAW_ALLGROUPS).set();
			     else
			    	 ie.checkbox(id, IProgramsConst.PROG_SHAW_ALLGROUPS).clear();
			     if((org != null) && 
			    		 ie.selectList(id, IProgramsConst.PROG_STAFF_ORG).enabled())
			    	 ie.selectList(id, IProgramsConst.PROG_STAFF_ORG).select(org);
			     
				for(int i=0; i<groups.length; i++)
				{
					if(GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_SELECTED, 
							groups[i]) == false)
					{
						if(GeneralUtil.isObjectExistsInList(IProgramsConst.PROG_STAFF_AVAILABL, 
								groups[i]) == true)
						{
							ie.selectList(id, IProgramsConst.PROG_STAFF_AVAILABL).select(groups[i]);
							ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
						}
						else
							log.warn(IErrorConst.tstTestUnknown + 
								" Requested Program Staff group does not exist in both lists:  " + groups[i]);
					}
				}
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickLinks(programType);
			}
			else
				log.warn("Program link not found: " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setProgramAdministrators()  " + ex.getMessage());
		}
	}
	
	public static void setProgramSteps(String programType, String programId, ArrayList<ProgramSteps> programSteps)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(programType);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				log.info("Start creating/updating Program Steps");
				for(int i=0; i<programSteps.size(); i++)
				{
					ClicksUtil.clickLinks(programId);
					ProgramSteps step = (ProgramSteps)programSteps.get(i);
				
					HtmlElement ele = ie.htmlElement(id, IClicksConst.plannerExpanderTopSpan);
					if (ele.span(1).exists())
						ele.span(1).click();
					
					if(step.getStepAction() == ECreateUpdate.objectCreate.ordinal())
					{
						log.info("Creating new Step: " + step.getStepId());
						ClicksUtil.clickImageByAlt(IProgStepsConst.STEP_ADD_NEW);
						fillProgramStepDetails(step);
					}
					else if(step.getStepAction() == ECreateUpdate.objectUpdate.ordinal())
					{
						log.info("Updating existing Step: " + step.getStepId());
						findProgStepsNodeId();
						String imgAltPortion = "/" + IProgStepsConst.STEP_VIEW_DETAILS + " " + step.getStepId() + "/";
						ClicksUtil.clickImageByAlt(imgAltPortion);
						fillProgramStepDetails(step);
					}
					else if(step.getStepAction() == ECreateUpdate.objectDelete.ordinal())
					{
						log.info("Deleting existing Step: " + step.getStepId());
						findProgStepsNodeId();
						String imgAltDeletePortion = "/" + IProgStepsConst.STEP_DELETE + " " + step.getStepId() + "/";
						if(ie.link(alt, imgAltDeletePortion).exists() == false)
							log.info("Image by alt does not exist: " + imgAltDeletePortion + 
										" hence unable to delete Step " + step.getStepId());
						else
						{
							Thread dialogClicker_2 = new Thread()
							{
								@Override
								public void run() {
									try
									{
										IE ie = IEUtil.getActiveIE();
										ConfirmDialog dialog1 = ie.confirmDialog();
										while (dialog1 == null)
											log.debug("can't yet get handle on confirm dialog1");								
										dialog1.ok();							
									}
									catch (Exception e)
									{
										throw new RuntimeException("Unexpected exception",e);
									}
								}
							};
							dialogClicker_2.start();
							ClicksUtil.clickImageByAlt(imgAltDeletePortion);
							
							dialogClicker_2 = null;
						}
					}
					if(step.getStepStaff() != null)
						setStepStaff(programType, step.getProgId(), step.getStepId(), i, step.getStepStaff());
				}
			}
			else
				log.warn("Program link not found: " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setProgramStep()  " + ex.getMessage());
		}
	}
	
	public static void fillProgramStepDetails(ProgramSteps stp)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			if(stp.getStepId() != null)
				ie.textField(id, IProgStepsConst.step_Id).set(stp.getStepId());
			if(stp.getStepNotes() != null)
				ie.textField(id, IProgStepsConst.step_Notes).set(stp.getStepNotes());
			if(stp.getStepType() != null)
			{
				if(ie.selectList(id, IProgStepsConst.step_Type).exists())
					ie.selectList(id, IProgStepsConst.step_Type).select(stp.getStepType());
			}
			if(stp.getStepProjOfficerGroup() != null)
				ie.selectList(id, IProgStepsConst.step_Admin).select(stp.getStepProjOfficerGroup());
	        if(stp.getStepStartDate() != null)
	        	ie.textField( id, IProgStepsConst.step_Start_Date).set(stp.getStepStartDate());
	        if(stp.getStepEndDate() != null)
	        	ie.textField( id, IProgStepsConst.step_End_Date).set(stp.getStepEndDate());
	        if(stp.isStepIsCritical() == true)
	        	ie.checkbox(id, IProgStepsConst.step_Is_Critical).set();
	        else
	        	ie.checkbox(id, IProgStepsConst.step_Is_Critical).clear();
	        if(stp.getStepReExecute() != null)
	        	ie.selectList(id, IProgStepsConst.step_Re_Execute).select(stp.getStepReExecute());
	        if(stp.getStepForm() != null)
	        	ie.selectList(id, IProgStepsConst.step_Prop_Form).select(stp.getStepForm());
	        if(stp.getStepEvaluationType() != null)
	        {
	        	if(ie.selectList(id, IProgStepsConst.step_Prop_Eval_Type).exists())
	        		ie.selectList(id, IProgStepsConst.step_Prop_Eval_Type).select(stp.getStepEvaluationType());
	        }
	        if(ie.textField(id, IProgStepsConst.step_Prop_Amount).exists())
	        {
	        	if(stp.getStepQuorumAmount() != null)
	        		ie.textField(id, IProgStepsConst.step_Prop_Amount).set(stp.getStepQuorumAmount());	
	        }
	        if(ie.checkbox(id, IProgStepsConst.step_Prop_Auto_Assign).exists())
	        {
	        	if(stp.isStepAutoAssign() == true)
	        		ie.checkbox(id, IProgStepsConst.step_Prop_Auto_Assign).set();
	        	else
	        		ie.checkbox(id, IProgStepsConst.step_Prop_Auto_Assign).clear();
	        }
	        if(stp.getStepNames() != null && stp.getStepNames().length > 0)
	        {
	        	for(int i=0; i<stp.getStepNames().length; i++)
	        	{
	        		if(ie.textField(id, "/" + i + IProgStepsConst.step_Title_End).exists())
	        			ie.textField(id, "/" + i + IProgStepsConst.step_Title_End).set(stp.getStepNames()[i]);
	        	}
	        }
	        ClicksUtil.clickButtons(IClicksConst.saveBtn);
	        ErrorUtil.checkForRecordExistsError(IErrorConst.msgMandatoryFieldOmited);
			if(ie.span(text, IErrorConst.msgQuorumGreaterThanZero).exists())
				log.warn("WARNING: Quorum field has to be filled with the number greater than zero. " +
						"Step creation or update cannot be completed");
	        
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillProgramStepForm()  " + ex.getMessage());
		}
	}
	
	public static void setStepStaff(String progType, String progId, String stepId, 
			int stepIndex, Hashtable stepStaff)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(progType);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(progId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, progId).exists())
			{
				ClicksUtil.clickLinks(progId);
				int nodeIndex = findProgStepsNodeId();
				String expanderImage = String.valueOf("");
				expanderImage = IClicksConst.plannerExpanderLeftPortion+"_"+nodeIndex+"_"+stepIndex+
					IClicksConst.plannerExpanderRightPortion;
					log.info("Step index " + stepIndex + " expander image: " + expanderImage);
					
					HtmlElement ele = ie.htmlElement(id, expanderImage);
					if (ele.span(attribute("class", IClicksConst.collapsedSpan)).exists())
						ele.span(1).click();
//					if(ie.image(id, expanderImage).src().endsWith(IClicksConst.expandNodeImg) ||
//							ie.image(id, expanderImage).src().endsWith(IClicksConst.expandNode2Img))
//						ie.image(id, expanderImage).click();
					ClicksUtil.clickImageByAlt(IProgStepsConst.MANAGE_STEP_STAFF);
					if(Boolean.parseBoolean(stepStaff.get(staffShowAll).toString()) == true)
					{
				         ie.checkbox(id, IProgramsConst.PROG_SHAW_ALLGROUPS).set();
				         if(stepStaff.get(staffOrg) != null)
				         {
				        	 String org = (String)stepStaff.get(staffOrg);
				        	 ie.selectList(id, IProgramsConst.PROG_STAFF_ORG).select(org); 
				         }
					}
				     else
				    	 ie.checkbox(id, IProgramsConst.PROG_SHAW_ALLGROUPS).clear();
					if((String)stepStaff.get(staffSearch) != null)
					{
						ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set((String)stepStaff.get(staffSearch));
						ClicksUtil.clickButtons(IClicksConst.searchBtn);
					}
					//!!!!!!!!! Here supposed to be a logic to select required Users one by one**********
					//**********For now is just a search for one user and Move ALL To Right click********
					GeneralUtil.takeANap(1.0);
					ClicksUtil.clickButtons(IClicksConst.m2MAllForBtn);
					GeneralUtil.takeANap(1.0);
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					ClicksUtil.clickButtons(IClicksConst.backBtn);

					HtmlElement ele1 = ie.htmlElement(id, expanderImage);
					if (ele1.span(attribute("class", IClicksConst.expandedSpan)).exists())
						ele1.span(1).click();					
//					if(ie.image(id, expanderImage).src().endsWith(IClicksConst.collapseNodeImg) ||
//							ie.image(id, expanderImage).src().endsWith(IClicksConst.collapseNode2Img))
//						ie.image(id, expanderImage).click();

			}
			else
				log.warn("Program link not found. Unable assign Step Staff " + progId);
				
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setStepStaff()  " + ex.getMessage());
		}
		
	}
	
	public static void setProgramStatus(String programType, String programId, String programStatus)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			ClicksUtil.clickLinks(programType);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				log.info("Start activating or deactivating Program: " + programId);
				ClicksUtil.clickLinks(programId);
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + programId);
				ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).select(programStatus);
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
			}
			else
				log.warn("Unable to activate the Program: " + programId + " link does not exist.");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in setProgramActive() " + ex.getMessage());
		}
	}
	
	
	public static void fillApplicantsForm(ArrayList<Applicants> applicants, int createOrUpdate)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			for(int i=0; i<applicants.size(); i++)
			{
				Applicants appl = (Applicants)applicants.get(i);
				String applId = appl.getApplicantName();
				if(createOrUpdate == ECreateUpdate.objectCreate.ordinal())
					ClicksUtil.clickImageByAlt(IClicksConst.newApplicant);
				else
					ClicksUtil.clickLinks(applId);
				//Fill the Form (create or update)
				if(applId != null)
					ie.textField(id, IApplicantsConst.applicantName_FldID).set(applId);
				if(appl.getApplicantNumber() != null)
					ie.textField(id, IApplicantsConst.applicantNumber_FldID).set(appl.getApplicantNumber());
				if(appl.getApplicantType() != null)
					ie.selectList(id, IApplicantsConst.applicantType_DropDownId).select(appl.getApplicantType());
				if(appl.getApplicantOrg() != null)
					ie.selectList(id, IApplicantsConst.APPLICANT_ORG).select(appl.getApplicantOrg());
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				if(appl.getArrProjects() != null && appl.getArrProjects().size() > 0)
				{
					log.info("Number of projects for applicant: " + applId + " = " + appl.getArrProjects().size());
					fillApplicantProjectsSubmission(applId, appl.getArrProjects());
				}
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillApplicantsForm()  " + ex.getMessage());
		}
	}
	
	public static void fillApplicantProjectsSubmission(String applicantName, ArrayList<POProjects> applProjects)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			for(int i=0; i<applProjects.size(); i++)
			{
				ie.textField(id, "/0" + IFiltersConst.txtFilterItem + "/").set(applicantName);
				ClicksUtil.clickButtons(IClicksConst.filterBtn);
				ClicksUtil.clickImageByAlt(IApplicantsConst.VIEW_APPL_SUBM_LIST + " " + applicantName);
				ClicksUtil.clickImageByAlt(IClicksConst.addNewProject);
				POProjects project = (POProjects)applProjects.get(i);
				ie.selectList(id, IProjectsConst.submissionApplProgram).select(project.getApplProgram());
				ie.textField(id, IProjectsConst.poProjectName).set(project.getProjectName());	
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillApplicantProjectsSubmission() " + ex.getMessage());
		}
	}
	
	public static void manageOrg(Organizations organization)
	{
		try
		{
			boolean orgFormReady = false;
			if(organization.getUpdateOrCreate() == ECreateUpdate.objectCreate.ordinal())
			{
				if(organization.getParentOrg() != null)
				{
					ClicksUtil.clickLinks(organization.getParentOrg());
					ClicksUtil.clickImageByAlt(IClicksConst.orgAddSubOrg);
					orgFormReady = true;
				}
				else
					log.warn("WARNING: in order to create a new Organization Parent Org. Id must be provided");
			}
			else if(organization.getUpdateOrCreate() == ECreateUpdate.objectUpdate.ordinal())
			{
				ClicksUtil.clickLinks(organization.getOrgId());
				ClicksUtil.clickImageByAlt(IClicksConst.orgViewOrg + " " + organization.getOrgId());
				orgFormReady = true;

			}
			if(orgFormReady == true)
			{
				fillOrgForm(organization);
				if(organization.getChildOrganizations() != null && 
						organization.getChildOrganizations().size() > 0)
					 manageOrgHierarchy(organization.getChildOrganizations());
			}
			else
				log.warn("WARNING: not enough parameters supplied to be able create or update " +
						"Organizations tree for Organization: " + organization.getOrgId());
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in manageOrgHierarchy()  " + ex.getMessage());
		}
	}
	
	public static void manageOrgHierarchy(ArrayList<Organizations> childOrgs)
	{
		try
		{
			for(int i=0; i<childOrgs.size(); i++)
			{
				Organizations childOrg = (Organizations)childOrgs.get(i);
				ClicksUtil.clickLinks(IClicksConst.openOrganizationsList);
				boolean orgFormReady = false;
				if(childOrg.getUpdateOrCreate() == ECreateUpdate.objectCreate.ordinal())
				{
					if(childOrg.getParentOrg() != null)
					{
						ClicksUtil.clickLinks(childOrg.getParentOrg());
						ClicksUtil.clickImageByAlt(IClicksConst.orgAddSubOrg);
						orgFormReady = true;
					}
					else
						log.warn("WARNING: in order to create a new Organization Parent Org. Id must be provided");
				}
				else if(childOrg.getUpdateOrCreate() == ECreateUpdate.objectUpdate.ordinal())
				{
					ClicksUtil.clickLinks(childOrg.getOrgId());
					ClicksUtil.clickImageByAlt(IClicksConst.orgViewOrg + " " + childOrg.getOrgId());
					orgFormReady = true;

				}
				if(orgFormReady == true)
				{
					fillOrgForm(childOrg);
					if(childOrg.getChildOrganizations() != null && 
							childOrg.getChildOrganizations().size() > 0)
						manageOrgHierarchy(childOrg.getChildOrganizations());
				}
				else
					log.warn("WARNING: not enough parameters supplied to be able create or update " +
						"Organizations tree for Organization: " + childOrg.getOrgId());
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in manageOrgHierarchy  " + ex.getMessage());
		}
	}
	
	public static void fillOrgForm(Organizations org)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			log.info("Organization Id: " + org.getOrgId());
			if(org.getOrgId() != null)
				ie.textField(id, IOrgConst.org_Ident_TxtFld_Id).set(org.getOrgId());
			if(ie.checkbox(id, IOrgConst.inhParent_Chkbox_Id).enabled())
			{
				if(org.isInheritParentChanges() == true)
					ie.checkbox(id, IOrgConst.inhParent_Chkbox_Id).set();
				else
					ie.checkbox(id, IOrgConst.inhParent_Chkbox_Id).clear();
			}
			if(org.getOrgType() != null)
				ie.selectList(id, IOrgConst.org_Type_Drpdwn_Id).select(org.getOrgType());
			if(org.getOrgStatus() != null)
				ie.selectList(id, IOrgConst.org_Status_Drpdwn_Id).select(org.getOrgStatus());
			if(org.getOrgDefLoc() != null)
				ie.selectList(id, IOrgConst.org_DefaultLocale_Drpdwn_Id).select(org.getOrgDefLoc());
			if(org.getOrgOfficer() != null)
				ie.selectList(id, IOrgConst.org_Officer_Drpdwn_Id).select(org.getOrgOfficer());
			if(org.getOrgFullNames() != null && org.getOrgFullNames().length > 0 &&
					org.getOrgShortNames() != null && org.getOrgShortNames().length > 0 &&
					org.getOrgFullNames().length == org.getOrgShortNames().length)
			{
				for(int i=0; i<org.getOrgFullNames().length; i++)
				{
					if(ie.textField(id, "/" + i + ":" + IOrgConst.org_FullName_TxtFld_EndId).exists())
						ie.textField(id, "/" + i + ":" + IOrgConst.org_FullName_TxtFld_EndId).set(org.getOrgFullNames()[i]);
					if(ie.textField(id, "/" + i + ":" + IOrgConst.org_ShortName_TxtFld_EndId).exists())
						ie.textField(id, "/" + i + ":" + IOrgConst.org_ShortName_TxtFld_EndId).set(org.getOrgShortNames()[i]);
				}
			}
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			if(ie.span(text, IOrgConst.ORG_VALUE_REQUIRED).exists() || 
					ie.span(text, IOrgConst.ORG_VALUE_SELECTREQ).exists())
				log.warn("WARNING: One or more property must be provided in order to add/modify Organization entry");
			if(ie.span(text, IOrgConst.ORG_DUPLICATE_IDENT).exists())
				log.warn("WARNING: Organization with the identifier already exists. Cannot add another one.");
			if(org.getOrgForm() != null)//the attempt to associate Org Form to Organization
				ie.selectList(id, IOrgConst.org_Form_DrpDwn_Id).select(org.getOrgForm());
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillOrgForm()  " + ex.getMessage());
		}
	}
	/**
	 * Fill Notification (e-mail) Details in Program Planner / Step
	 * @param notification
	 * @author apankov
	 */
	public static void fillNotificationsDetails(Notifications notification)
	{
		try
		{
			IE ie = IEUtil.getActiveIE();
			if(notification != null)
			{
				if(notification.isNtfIsActive() == true)
					ie.checkbox(id, INotificationsConst.ntfIsActive).set();
				else
					ie.checkbox(id, INotificationsConst.ntfIsActive).clear();
				if(notification.getNtfName() != null)
					ie.textField(id, INotificationsConst.ntfName).set(notification.getNtfName());
				if(notification.getNtfOnEvent() != null)
					ie.selectList(id, INotificationsConst.ntfEvent).select(notification.getNtfOnEvent());
				if(notification.isNtfInsertProjectActivity() == true)
					ie.checkbox(id, INotificationsConst.ntfInsertProjectActivities).set();
				else
					ie.checkbox(id, INotificationsConst.ntfInsertProjectActivities).clear();
				ie.textField(id, INotificationsConst.ntfNotifyEvery).set(String.valueOf(notification.getNtfDaysAfter()));
				if(notification.getNtfDaysUntil() > 0)
				{
					ie.checkbox(id, INotificationsConst.ntfRepeatable).set();
					ie.textField(id, INotificationsConst.ntfRepeatEvery).set(String.valueOf(notification.getNtfDaysUntil()));;
				}
				if(notification.isNtfApplicants() == true)
					ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set();
				else
					ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).clear();
				if(notification.isNtfProjectOfficers() == true)
					ie.checkbox(id, INotificationsConst.ntfNotifyAllProjectOfficers).set();
				else
					ie.checkbox(id, INotificationsConst.ntfNotifyAllProjectOfficers).clear();
				
				if(notification.getNtfMessageSubject() != null && 
						notification.getNtfMessageSubject().length > 0)	
				{
					for(int i=0; i<notification.getNtfMessageSubject().length; i++)
					{
						if (ie.textField(id, INotificationsConst.ntfSubject+"["+i+"]").exists())
						{
							ie.textField(id, INotificationsConst.ntfSubject+"["+i+"]").set(notification.getNtfMessageSubject()[i]);
							if(notification.getNtfMessageBody() != null)
								ie.textField(id, INotificationsConst.ntfBody+"["+i+"]").set(notification.getNtfMessageBody()[i]);
						}
					}
				}
				
				if(notification.getNtfInternalRecipients() != null &&
						notification.getNtfInternalRecipients().length > 0)
				{
					for(int j=0; j<notification.getNtfInternalRecipients().length; j++)
					{
						if(GeneralUtil.isObjectExistsInList(INotificationsConst.ntfSelectedRecipients, 
								notification.getNtfInternalRecipients()[j]) == false)
						{
							if(GeneralUtil.isObjectExistsInList(INotificationsConst.ntfAvailableRecipients, 
									notification.getNtfInternalRecipients()[j]) == true)
							{
								ie.selectList(id, INotificationsConst.ntfAvailableRecipients).select(notification.getNtfInternalRecipients()[j]);
								ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
							}
							else
								log.warn(IErrorConst.tstTestUnknown + 
										" Requested Internal Recepient does not exists " + notification.getNtfInternalRecipients()[j]);
						}
					}
				}
				if(notification.getNtfExternalLocale() != null)
					ie.selectList(id, INotificationsConst.ntfLocaleSelect).select(notification.getNtfExternalLocale());
				if(notification.getNtfExternalRecepients() != null &&
						notification.getNtfExternalRecepients().length > 0)
				{
					String extRecip = "";
					for(int j=0; j<notification.getNtfExternalRecepients().length; j++)
					{
						if(j<notification.getNtfExternalRecepients().length-1)
							extRecip += (notification.getNtfExternalRecepients()[j] + ";");
						else
							extRecip += notification.getNtfExternalRecepients()[j];
					}
					ie.textField(id, INotificationsConst.ntfExternalRecipients).set(extRecip);
				}
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ErrorUtil.checkForRecordExistsError(IErrorConst.msgNotificationExists);
				ClicksUtil.clickButtons(IClicksConst.backBtn);
			}
			else
				log.warn("WARNING: Notifications object is set to NULL - unable to proceed with filling the form");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in fillNotificationsDetails()  " + ex.getMessage());
		}
	}
	
	public static int findProgStepsNodeId() throws Exception
	{
		IE ie = IEUtil.getActiveIE();
		int expanderIndex = 0;
		boolean lastImageFound = false;
		String expanderImage = String.valueOf("");
		while(expanderIndex>=0)
			{
				try
				{

					expanderImage = IClicksConst.plannerExpanderLeftPortion+"_"+expanderIndex+
						IClicksConst.plannerExpanderRightPortion;
					HtmlElement ele = ie.htmlElement(id, expanderImage);
					if (ele.span(attribute("class", IClicksConst.collapsedSpan)).exists())
						ele.span(1).click();
							
//					expanderImage = IClicksConst.plannerExpanderLeftPortion+":"+expanderIndex+
//							IClicksConst.plannerExpanderRightPortion;
//						if(ie.image(id, expanderImage).src().endsWith(IClicksConst.expandNodeImg) ||
//							ie.image(id, expanderImage).src().endsWith(IClicksConst.collapseNodeImg))
//						{
//							lastImageFound = true;
//							if(ie.image(id, expanderImage).src().endsWith(IClicksConst.expandNodeImg))
//								ie.image(id, expanderImage).click();
//						}
				}
				catch(Exception e)
				{
					// just ignore and go ahead. There can be cases when no expander at all
				}
				if(expanderIndex>30)//just in case if someone uses it inappropriately
				{
					log.info("Too big numbers passed. Probably there is no Steps node at all, breaking.");
					break;
				}
				else
				{
					if(!lastImageFound)
						expanderIndex++;
					else
						break;
				}
			}
		return expanderIndex;
	}
}
