/**
 * 
 */
package test_Suite.tests.r3_0.stepAmendment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestExitEvaluationStepOnNotUsedNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users user;
	
	Users approver1;
	
	Users approver2;
	
	Users approver3;
	
	Users approver4;
	
	Users approver5;
	
	Users staff;
	
	Users clerk1;
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front2");
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			fopp = new FundingOpportunity("A","-StepAmendment-PA-","");
			
			approver1 = SubAccessUtil.getUser(1,0,"Program Office Users");
		
			approver2 = SubAccessUtil.getUser(2,0,"Program Office Users");
			
			approver3 = SubAccessUtil.getUser(3,0,"Program Office Users");
		
			approver4 = SubAccessUtil.getUser(4,0,"Program Office Users");
			
			approver5 = SubAccessUtil.getUser(5,0,"Program Office Users");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		user= null;
		lbf = null;
		approver1= null;
		approver2 = null;
		approver3 = null;
		approver4 = null;
		approver5 = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createFOProjectNG() throws Exception {
		try {

			foProj = new FOProject(fopp, "", true, 2, "-ExitEvalNotUsed");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "createFOProjectNG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
			
			clerk1 = SubAccessUtil.getUser(1, 16, "Program Office Users");
			
			String[] amenders = new String []{"Submission", "PO: ".concat(clerk1.getUserFullId())};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, ""));
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestStepAmendment")
	public void reSubmitApplicantSubmission() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithClerk("1");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: Could not open Applicant Submission");
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			foProj.setClaimNumber(0);
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitApplicantSubmission", dataProvider="approvals")
	public void reSubmitEvaluationAmendedSubmission(String userBeat, String approved, String subStatus, boolean expected, boolean doSubmit) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover(userBeat);
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			Assert.assertEquals(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][0], subStatus, "No Propreties Fields LBF", false,"2",IFiltersConst.versionNumber_EqualTo), expected);
			
			if(doSubmit)
			{
				GeneralUtil.selectInDropdownList("/0:numericDropdown/", approved);
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				
				ClicksUtil.clickLinks("/Summary/");
				
				SubAccessUtil.submitAndReturnFromForm();
				
			}
			else
			{
				ClicksUtil.returnFromAnyForm();
			}
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitEvaluationAmendedSubmission")
	public void reSubmitAwardStep() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");

			foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			Assert.assertTrue(ProjectUtil.openAwardInList(foProj), "FAIL: could not find Re-executed Award");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	@DataProvider(name = "approvals")
	public Object[][] generateApproval() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {"4","Approved",IFiltersConst.evaluateSubmissions_InProgress_StatusSubView,true,true},
				new Object[] {"1","Approved",IFiltersConst.evaluateSubmissions_NotUsed_StatusSubView, true,false},
				new Object[] {"2","Approved",IFiltersConst.evaluateSubmissions_NotUsed_StatusSubView,true,false},
				new Object[] {"3","Approved",IFiltersConst.evaluateSubmissions_NotUsed_StatusSubView,true,false},
				new Object[] {"5","Approved",IFiltersConst.evaluateSubmissions_NotUsed_StatusSubView,true,false}
		};				
		return result;		
	}

	@DataProvider(name = "users")
	public Object[][] generateEvaluators() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {approver1.getUserFullId()},
				new Object[] {approver2.getUserFullId()},
				new Object[] {approver3.getUserFullId()},
				new Object[] {approver4.getUserFullId()},
				new Object[] {approver5.getUserFullId()}
		};				
		return result;		
	}
}
