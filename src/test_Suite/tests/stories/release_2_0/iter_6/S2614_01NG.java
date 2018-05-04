/**
 *  Steps:
 *  1. Open PO, ;og in as admin user
 *  2. Set PAP required to "Yes"
 *  3. Create new PAP with 2 steps: Submit, Approve
 *  4. Set PAP to Active
 *  5. Create new Grant Program with 3 steps: Submit, Approve, Award
 *  6. Associate Grant Program with PAP from step #3
 *  7. Submit G3 Program for PAP Approval
 *  8. Check G3 Program Status before and after Submission
 *  9. Assigne Project Officers for each step - one Admin user in this case
 *  10.Evaluate PAP submission and Set to Approve
 *   11 Make sure G3 Program Status is Active and Approval Process is set to Approved
 */
package test_Suite.tests.stories.release_2_0.iter_6;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IErrorConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProgStepsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramApprove;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.ErrorUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.ui.HtmlFormsUtil.ECreateUpdate;
import watij.runtime.ie.IE;
/**
 * @author apankov
 *
 */

@Test(singleThreaded = true)
public class S2614_01NG {

	private static Log log = LogFactory.getLog(S2614_01NG.class);
	IE ie;
	
	private String programId = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+
		IGeneralConst.gnrl_ProgName+"-"+IGeneralConst.baseLetters[0];
	private String papId = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+
	IGeneralConst.gnrl_PAPName+"-"+IGeneralConst.baseLetters[0];
	private String [] papNames  = {papId, papId, papId};
	private String [] progNames = {programId, programId, programId};
	private String [] programAdmin = {IPreTestConst.adminGroup, IPreTestConst.AppGroupName};
	private String [][] stepNames = {
			{"PAP Submit", "PAP Submit", "PAP Submit"},
			{"PAP Approve", "PAP Approve", "PAP Approve"},
			{"PAP Award", "PAP Award", "PAP Award"}
		};
	private String papFormCompleteText = "PAP form complete";
	private String [] stepStaff = {IPreTestConst.adminProgPOOfficer};
	private String    stepStaffSearch = "admin";
	ProgramApprove pApprove;
	Program        prog;
	private enum progType
	{
		typePAP,
		typeG3
	}
	
	@BeforeClass  
	public void setUp() {

	} 

	@Test(groups = { "Iter_26" })
	/**
	 * Parent Method
	 */
	public void s2614_01NG() throws Exception
	{
		IEUtil.openNewBrowser();
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		
		GeneralUtil.setPAPRequired("Yes"); //Program Approval Process required set to "Yes"
		createPAP();
		HtmlFormsUtil.setProgramStatus(IClicksConst.openProgramApprovalProcess, papId, IGeneralConst.statusActive);
		createProgram();
		completePAPForm();
		checkStatus1_2(); //checking the status of Program Approval Process from G3 Program Details screen
		intakePAPSubmission();
		evaluateSubmission();
		checkStatus3_4();
		pApprove = null;
		prog = null;
		closeBrowser();
	}
	
	private void createPAP()
	{
		try
		{
			ClicksUtil.clickLinkBySpanId( IClicksConst.menuPAP_ApprovalProcess_Span_Id, IClicksConst.openProgramApprovalProcess);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(papId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			//For this test scenario if the PAP already exists we just update it
			// The same scenario is valid for PAP Steps
			pApprove = new ProgramApprove();
			pApprove.setProgApproveId(papId);
			//pApprove.setProgApproveForm(IUAPConst.programForm[0]);
			pApprove.setProgApproveStartDate(GeneralUtil.getTodayDate());
			pApprove.setProgApproveEndDate(GeneralUtil.getNextYear());
			pApprove.setProgApproveOfficer(IPreTestConst.adminProgPOOfficer);
			pApprove.setProgApproveStatus(IGeneralConst.statusInactive);
			pApprove.setProgApprovePrOrg(IGeneralConst.primG3_OrgRoot);
			pApprove.setProgApproveOrgAccess(IGeneralConst.org_Access_Public);
			pApprove.setProgApproveNames(papNames);
			pApprove.setProgApproveAdmin(programAdmin);
			pApprove.setProgApproveStaff(setStaff());
			pApprove.setCreateOrUpdate(ECreateUpdate.objectCreate.ordinal());
			pApprove.setProgApproveSteps(createSteps(progType.typePAP.ordinal(), papId));
			log.info("Creating new PAP");
			HtmlFormsUtil.fillProgApproveDetails(pApprove); 
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createPAP() " + ex.getMessage());
		}
	}
	
	private Hashtable<String, Object> setStaff()
	{
	     Hashtable<String, Object> staff = new Hashtable<String, Object>();
	     staff.put(HtmlFormsUtil.staffShowAll, false);
	     staff.put(HtmlFormsUtil.staffOrg, IGeneralConst.primG3_OrgRoot);
	     staff.put(HtmlFormsUtil.staffGroups, programAdmin);
	     
	     return staff;
	}
	
	private Hashtable<String, Serializable> setStepStaff()
	{
		Hashtable<String, Serializable> stepStaff = new Hashtable<String, Serializable>();
		stepStaff.put(HtmlFormsUtil.staffShowAll, false);
		//stepStaff.put(HtmlFormsUtil.staffOrg, IGeneralConst.GRANTIUM_APP);
		stepStaff.put(HtmlFormsUtil.staffUsers, stepStaff);
		stepStaff.put(HtmlFormsUtil.staffSearch, stepStaffSearch);
		
		return stepStaff;
	}
	private ArrayList<ProgramSteps> createSteps(int type, String pId)
	{
		try
		{
			ArrayList<ProgramSteps> steps = new ArrayList<ProgramSteps>();
			
			//Set Submit step
			ProgramSteps pSubmit = new ProgramSteps();
			pSubmit.setProgId(pId);
			pSubmit.setStepId(IEFormsConst.applicantsubmission_FormTypeName);
			pSubmit.setStepId(IProgStepsConst.stepType_FundOppSubmission);
			pSubmit.setStepNotes(IEFormsConst.applicantsubmission_FormTypeName);
			if(type == progType.typeG3.ordinal())
				pSubmit.setStepType(IProgStepsConst.stepType_ApplicantSubmission);
			else
				pSubmit.setStepType(IProgStepsConst.stepType_FundOppSubmission);
			pSubmit.setStepProjOfficerGroup(IPreTestConst.adminGroup);
			pSubmit.setStepStartDate(GeneralUtil.getTodayDate());
			pSubmit.setStepEndDate(GeneralUtil.getNextYear());
			pSubmit.setStepIsCritical(true);
			pSubmit.setStepForm("Sub");
			pSubmit.setStepNames(stepNames[0]);
			pSubmit.setStepStaff(setStepStaff());
			pSubmit.setStepAction(ECreateUpdate.objectCreate.ordinal());
			steps.add(pSubmit);
			
			//Set Approve step
			ProgramSteps pApprove = new ProgramSteps();
			pApprove.setProgId(pId);
			pApprove.setStepId(IEFormsConst.projectApproval_FormTypeName);
			pApprove.setStepNotes(IEFormsConst.projectApproval_FormTypeName);
			if(type == progType.typeG3.ordinal())
				pApprove.setStepType(IProgStepsConst.stepType_Approval);
			else
				pApprove.setStepType(IProgStepsConst.stepType_FundOppApproval);
			pApprove.setStepProjOfficerGroup(IPreTestConst.adminGroup);
			pApprove.setStepStartDate(GeneralUtil.getTodayDate());
			pApprove.setStepEndDate(GeneralUtil.getNextYear());
			pApprove.setStepIsCritical(true);
			pApprove.setStepForm(IEFormsConst.approval_FormTypeName2);
			pApprove.setStepEvaluationType(IEFormsConst.formReviewCrit[1][1]);
			pApprove.setStepQuorumAmount("1");
			pApprove.setStepAutoAssign(true);
			pApprove.setStepNames(stepNames[1]);
			pApprove.setStepStaff(setStepStaff());
			pApprove.setStepAction(ECreateUpdate.objectCreate.ordinal());
			steps.add(pApprove);
			
			if(type == progType.typeG3.ordinal())
			{
				ProgramSteps pAward = new ProgramSteps();
				pAward.setProgId(pId);
				pAward.setStepId(IEFormsConst.award_FormTypeName);
				pAward.setStepNotes(IEFormsConst.award_FormTypeName);
				pAward.setStepType(IProgStepsConst.stepType_Award);
				pAward.setStepProjOfficerGroup(IPreTestConst.adminGroup);
				pAward.setStepStartDate(GeneralUtil.getTodayDate());
				pAward.setStepEndDate(GeneralUtil.getNextYear());
				pAward.setStepIsCritical(true);
				pAward.setStepForm("Basic Agreement");
				pAward.setStepNames(stepNames[2]);
				pAward.setStepStaff(setStepStaff());
				pAward.setStepAction(ECreateUpdate.objectCreate.ordinal());
				steps.add(pAward);
			}
			
			return steps;
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createSteps() " + ex.getMessage());
			return null;
		}
	}

	private void createProgram()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			prog = new Program();
			prog.setProgName(programId);
			//prog.setProgramFormName(IUAPConst.programForm[0]);
			//prog.setPublicationFormName(IUAPConst.publicationForm[0]);
			prog.setStartDate(GeneralUtil.getTodayDate());
			prog.setEndDate(GeneralUtil.getNextYear());
			prog.setProgramOfficer(IPreTestConst.adminProgPOOfficer);
			prog.setProgStatus(IGeneralConst.statusInactive);
			prog.setPrimaryOrg(IGeneralConst.primG3_OrgRoot);
			prog.setOrgAccess(IGeneralConst.org_Access_Public);
			prog.setProgPreFix(IGeneralConst.gnrl_ProgPrefix);
			prog.setProgFundOpportunityProcess(papId);
			prog.setProgRegistrOpens(GeneralUtil.getTodayDate());
			prog.setProgRegistrOpenHH("07");
			prog.setProgRegistrOpenMM("01");
			prog.setProgRegistrCloses(GeneralUtil.getNextYear());
			prog.setProgRegistrCloseHH("23");
			prog.setProgRegistrCloseMM("59");
			prog.setCreationFOProjDisable(false);
			prog.setCompleteAFRequired(false);
			prog.setProgFullNames(progNames);
			prog.setProgAdmin(programAdmin);
			prog.setProgStaff(setStaff());
			prog.setCreateOrUpdate(ECreateUpdate.objectCreate.ordinal());
			prog.setProgSteps(createSteps(progType.typeG3.ordinal(), programId));
			log.info("Creating new G3 Program");
			HtmlFormsUtil.fillProgramDetails(prog); 
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createProgram() " + ex.getMessage());
		}
	}
	
	private void completePAPForm()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				log.info("Completing PAP form for " + programId);
				ClicksUtil.clickLinks(programId);
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + programId);
				ClicksUtil.clickButtons(IClicksConst.startApprovalReviewBtn);
				ErrorUtil.checkForRecordExistsError(IErrorConst.msgPAPCannotSubmitForm);
				ClicksUtil.clickButtons(IClicksConst.backBtn);
				ClicksUtil.clickImageByAlt(IProgramsConst.OPEN_PROG_APPR_FORM);
				if(ie.textField(id, "/eFormFieldsBeans:0:textBox/").exists())
				{
					ie.textField(id, "/eFormFieldsBeans:0:textBox/").set(papFormCompleteText);
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					ClicksUtil.clickLinks(IClicksConst.backTotheProgramPlannerLnk);
				}
				else
					log.warn("Unable to complete PAP form. Text field required for this test scenario not found." +
								"Unable to proceed ");

			}
			else
				log.warn("Expected Program cannot be found in the liest. Unable to proceed " +
							"with the testing scenario " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in completePAPForm() " + ex.getMessage());
		}
	}
	
	private void checkStatus1_2()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				log.info("Checking PAP Status before and after submission for Program Approval Process for " 
							+ programId);
				ClicksUtil.clickLinks(programId);
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + programId);
				
				ErrorUtil.stringComparison(IProgramsConst.APPR_STATUS_NEW, 
						ie.label(id, IProgramsConst.fundingOpp_PROG_PAP_STATUS_LBL).text(), " PAP Status: ");
				
				ClicksUtil.clickButtons(IClicksConst.startApprovalReviewBtn);
				
				ErrorUtil.stringComparison(IProgramsConst.APPR_STATUS_PENDING,
						ie.label(id, IProgramsConst.fundingOpp_PROG_PAP_STATUS_LBL).text(), " PAP Status: "); 
						 
			}
			else
				log.warn("Expected Program cannot be found in the list. Unable to proceed " +
							"with the testing scenario " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in checkStatus1_2() " + ex.getMessage());
		}
	}
	
	private void intakePAPSubmission()
	{
		try
		{
			log.info("Intake PAP Submission");
			ClicksUtil.clickLinkBySpanId( IClicksConst.menuPAP_ApprovalProcess_Span_Id, IClicksConst.openInBasketListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(papId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			String papLinkApproveText = papId + "-Approving-"+programId;
			if(ie.link(text, papLinkApproveText).exists())
			{
				ClicksUtil.clickLinks(papLinkApproveText);
				ie.textField(id, IProjectsConst.projectCaseNumber).set(String.valueOf((int)(Math.random() * 100)));
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickLinks(IClicksConst.projectOfficerLnk);
				ie.selectList(id, IProjectsConst.projectStepAdminGroups).select(programAdmin[0]);
				ie.selectList(id, IProjectsConst.projectSelCaseStaff).select(stepStaff[0]);
				ClicksUtil.clickButtons(IClicksConst.assignBtn);
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
			}
			else
				log.warn("Expected PAP Approve Link not found in the list. Unable to proceed " +
						"with the testing scenario for " + papLinkApproveText);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in intakePAPSubmission() " + ex.getMessage());
		}
	}
	
	private void evaluateSubmission()
	{
		try
		{
			log.info("Evaluating Submission");
			ClicksUtil.clickLinkBySpanId( IClicksConst.menuPAP_Evaluation_Span_Id, IClicksConst.openAssignedSubmissionListLnk);
			int rowIndex = -1;
			rowIndex = TablesUtil.getRowIndex(ITablesConst.fundingOpp_AssignEvaluatorsTableId, programId);
			if(rowIndex > -1)
			{
				ie.table(id, ITablesConst.fundingOpp_AssignEvaluatorsTableId).row(rowIndex).cell(0).image(0).click();
				ie.selectList(id, "/eFormFieldsBeans:0:numericDropdown/").select(IProgramsConst.APPR_STATUS_APPROVED);
				ie.textField(id, "/eFormFieldsBeans:1:datePicker/").set(GeneralUtil.getTodayDate());
				ie.textField(id, "/eFormFieldsBeans:2:textArea_Below/").set(IProgramsConst.APPR_STATUS_APPROVED);
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				try
				{
					ClicksUtil.clickButtons(IClicksConst.submitBtn);
				}
				catch(Exception e)
				{
					log.warn(IErrorConst.tstTestFailed + 
							" Button click fails most likely becasue button does not exists");
				}
				ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);
				
			}
			else
				log.warn("Required Program not found in the list");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in evaluateSubmission() " + ex.getMessage());
		}
	}
	
	private void checkStatus3_4()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programId).exists())
			{
				log.info("Checking PAP Status before and after submission for Program Approval Process for " 
							+ programId);
				ClicksUtil.clickLinks(programId);
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + programId);
				
				ErrorUtil.stringComparison(IProgramsConst.APPR_STATUS_APPROVED, 
						ie.label(id, IProgramsConst.fundingOpp_PROG_PAP_STATUS_LBL).text(), " PAP Status: ");
				
				if(GeneralUtil.getSelectedItemValueInDropdwonById( 
						IProgramsConst.fundingOpp_PROG_STATUS_MENU).equals(IGeneralConst.statusActive) &&
							ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).enabled())
					log.info(IErrorConst.tstTestPassed + " Program Status is set to 'Active' and Select control is enabled");
				else
					log.warn(IErrorConst.tstTestFailed + " Program Status is not set to 'Active' or Select control is disabled");	 
			}
			else
				log.warn("Expected Program cannot be found in the list. Unable to proceed " +
							"with the testing scenario " + programId);
		}
		catch(Exception ex)
		{
			log.debug("ERROR in checkStatus3_4() " + ex.getMessage());
		}
	}
	
	/**
	 * Close PO application and close web browser
	 *
	 */
	private void closeBrowser()
	{
		try
		{
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}
}
