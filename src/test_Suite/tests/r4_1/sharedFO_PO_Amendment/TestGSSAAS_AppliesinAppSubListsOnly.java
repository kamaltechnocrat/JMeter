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
public class TestGSSAAS_AppliesinAppSubListsOnly {

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
			
			Assert.assertTrue(foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true), "FAIL: Could Not Submit");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"createAndSubmitProjectNG"})
	public void requestAmendment() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
			
			String[] amenders = new String []{"Submission", "Applicant:".concat(foProj.getFoOrgName()).concat("(").concat(foProj.getFoOrgNumber()).concat(")")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, ""));
			
			GeneralUtil.switchToPOWithSubClerk("1");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"requestAmendment"})
	public void verifyAccessInAmendmentList() throws Exception {
		try {
			
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(AmendmentsUtil.openAmendmentListAndFilter(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not open Amendments List!");
			
			Assert.assertTrue(AmendmentsUtil.openAmendedSubmission(foProj,IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open the form!");
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Result_ProgTotal_TxtId));
			
			//String str =GeneralUtil.getTextInSpanByClass("textSmall");
			
			Assert.assertFalse((GeneralUtil.getTextInSpanByClass("textSmall")).startsWith(IAmendmentsConst.amendedByMessage), "Could not find the amended by message!");		
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"requestAmendment"})
	public void verifyAccessInMySubmissionsList() throws Exception {
		try {
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.openProjView));
						
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"requestAmendment"})
	public void verifyAccessInAppSubmissionsList() throws Exception {
		try {
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Result_ProgTotal_TxtId));
			
			Assert.assertFalse(GeneralUtil.FindTextOnPage(IAmendmentsConst.amendedByMessage), "Shouldn't be amended by message!");
			
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
