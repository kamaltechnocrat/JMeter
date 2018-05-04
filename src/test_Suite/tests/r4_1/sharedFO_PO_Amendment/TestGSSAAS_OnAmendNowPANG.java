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
public class TestGSSAAS_OnAmendNowPANG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String amender;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.fundingOpp_PA_Prefix, "");
			
			foProj = new FOProject(fopp, "", true, 1, "-SharedAmend");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
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
			
			Assert.assertTrue(foProj.createFOProjectNewNew(), "FAIL: Could not create FO Project");
			
			amender = "Applicant:".concat(foProj.getFoOrgName()).concat("(").concat(foProj.getFoOrgNumber()).concat(")");
			
			Assert.assertTrue(foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true), "FAIL: Could Not Submit");
			
			ClicksUtil.returnFromAnyForm();

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
			
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "continueWorkflow")
	public void AmendClaimNowToLoginApplicant() throws Exception {
		
		try {
			
			foProj.initializeStep(IGeneralConst.initialClaim[0][0]);
			
			String obj = foProj.getProjFOFullName().concat(" - ").concat("Claim 1");
			
			Assert.assertTrue(AmendmentsUtil.amendNowApplicantAmendment(obj, amender, "This is a Reason"),"FAIL: could not Request Applicant Amendment see previous error!");
			
			ClicksUtil.returnFromAnyForm();
			

		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"AmendClaimNowToLoginApplicant"})
	public void verifyAccessInAppSubmissionsList() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("3");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Result_ProgTotal_TxtId));
			
			Assert.assertTrue(GeneralUtil.FindTextOnPage(IAmendmentsConst.amendedByMessage), "Could not find the amended by message!");
			
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
