/**
 * 
 */
package test_Suite.tests.r4_1.sharedFO_PO_Amendment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestGSSAAS_AppliesToReExecuted {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front");
			// -----------------------------------

			fopp = new FundingOpportunity("A", IFoppConst.fundingOpp_PA_Prefix,
					"");

			foProj = new FOProject(fopp, "", true, 1, "-SharedAmend");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {

		try {
			
			fopp = null;
			foProj = null;

			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

			log.warn("Ending: " + this.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void createAndSubmitProjectNG() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"FAIL: Could Not Submit");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "createAndSubmitProjectNG" })
	public void continueWorkflow() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0], true,
					IFiltersConst.submissionsStatus_Ready_StatusSubView, false,
					false), "FAIL: Could not approve the submission!");

			GeneralUtil.switchToPO();

			Assert.assertTrue(foProj.submitAward("Standard", 2, true),
					"FAIL: Could not submit the Award!");

			GeneralUtil.switchToFO();

			foProj.setClaimNumber(1);

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.initialClaim[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"continueWorkflow"})
	public void requestAmendment() throws Exception {
		try {
			
			GeneralUtil.switchToPO();

			foProj.setClaimNumber(0);
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
			
			String[] amenders = new String []{"Submission", "Applicant:".concat(foProj.getFoOrgName()).concat("(").concat(foProj.getFoOrgNumber()).concat(")")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, ""));
			
			foProj.setClaimNumber(0);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"requestAmendment"})
	public void reSubmitAndReExecute() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithSubClerk("1");
			
			Assert.assertTrue(foProj.submitFromApplicantSubList(IGeneralConst.gnrl_SubmissionA[0][0], true), "FAIL: could not resubmit Project!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(foProj.approveAmendedSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, IFiltersConst.submissionsStatus_InProgress_StatusSubView),"FAIL: Could not Resubmit Approval!");
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(foProj.reSubmitAnyStandardAgreement(IGeneralConst.pa_GPS_AwardCrit[0][0], true),"FAIL: Could not Resubmit Award!");
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			foProj.setClaimNumber(1);
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"reSubmitAndReExecute"})
	public void verifyAccessInAmendmentList() throws Exception {
		try {
			
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(AmendmentsUtil.openAmendmentListAndFilter(foProj, IGeneralConst.initialClaim[0][0]), "FAIL: Could not open Amendments List!");
			
			Assert.assertTrue(AmendmentsUtil.openAmendedSubmission(foProj,IGeneralConst.initialClaim[0][0]), "FAIL: Could not Open the form!");
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Result_ProgTotal_TxtId));
			
			//String str =GeneralUtil.getTextInSpanByClass("textSmall");
			
			Assert.assertTrue((GeneralUtil.getTextInSpanByClass("textSmall")).startsWith(IAmendmentsConst.amendedByMessage), "Could not find the amended by message!");		
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"reSubmitAndReExecute"})
	public void verifyAccessInMySubmissionsList() throws Exception {
		try {
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView));
						
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"reSubmitAndReExecute"})
	public void verifyAccessInAppSubmissionsList() throws Exception {
		try {
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Result_ProgTotal_TxtId));
			
			Assert.assertTrue(GeneralUtil.FindTextOnPage(IAmendmentsConst.amendedByMessage), "Could not find the amended by message!");
			
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
