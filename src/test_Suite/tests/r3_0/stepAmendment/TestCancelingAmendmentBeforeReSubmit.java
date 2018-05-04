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
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IAmendmentsConst;
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
public class TestCancelingAmendmentBeforeReSubmit {

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

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		user= null;
		approver1= null;
		lbf = null;
		approver2 = null;
		approver3 = null;
		
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

			foProj = new FOProject(fopp, "", true, 2, "-CancelBeforeRe-Submit");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "createFOProjectNG")
	public void rejectApprovalNG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("2");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "No Propreties Fields LBF", false,"1", IFiltersConst.versionNumber_EqualTo);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Rejected");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "rejectApprovalNG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			approver1 = SubAccessUtil.getUser(1,0,"Program Office Users");
		
			approver2 = SubAccessUtil.getUser(2,0,"Program Office Users");
			
			approver3 = SubAccessUtil.getUser(3,0,"Program Office Users");
			
			String[] amenders = new String []{"Step",
					approver1.getUserFullId() + IAmendmentsConst.stepAmend_Approved_String,
					approver2.getUserFullId() + IAmendmentsConst.stepAmend_Rejected_String,
					approver3.getUserFullId() + IAmendmentsConst.stepAmend_NotUsed_String
			};

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
	public void cancelAmendmentBeforeResubmit() throws Exception {
		
		try {
			
			foProj.setClaimNumber(0);
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertTrue(AmendmentsUtil.cancelAmendedSubmission(foProj, approver3,IGeneralConst.approvQuoCritAuto[0][0],"1", IFiltersConst.versionNumber_EqualTo), "FAIL: Error Occur during Canceling the Amended Submission");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "cancelAmendmentBeforeResubmit")
	public void reSubmitStepAmendment() throws Exception {
		
		try{
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_InProgress_StatusSubView, "No Propreties Fields LBF", false);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Rejected");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("2");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_InProgress_StatusSubView, "No Propreties Fields LBF", false);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Approved");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitStepAmendment")
	public void reExecuteNextStepAward() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");

			foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			Assert.assertTrue(ProjectUtil.openAwardInList(foProj), "FAIL: could not find Re-executed Award");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}


}
