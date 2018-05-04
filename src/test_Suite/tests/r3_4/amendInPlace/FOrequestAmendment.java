/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IAmendmentsConst.EAmendmentOptions;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;


/**
 * @author k.sharma
 * 
 */
public class FOrequestAmendment {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	FOProject foProj;

	String submissionStep = "Submission";
	String reason = "Request Step Amend";
	String comment = "";

	String amender;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-PA-", "");

			foProj = new FOProject(fopp, "FO-Request-Amendment-", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}
/*
 * The below method will create and submit a project at Front Office
 */
	@Test(groups = { "WorkflowNG" })
	public void createAndSubmiProj() throws Exception {
		try {
			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");
			amender = "Applicant:".concat(foProj.getFoOrgName()).concat("(")
					.concat(foProj.getFoOrgNumber()).concat(")");
			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
/*
 * Method will verify existence of AmendmentCategory drop down at FO while front office
 * user make an amendment request 
 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "createAndSubmiProj" })
	public void verifyAmendmentCategoryField() throws Exception {
		try {

			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

			Assert.assertTrue(AmendmentsUtil.openApplicantAmendment(
					foProj.getCurrentStepName(), EAmendmentOptions.REQUEST),
					"FAIL: Could not open the Applicant Amedment");
			Assert.assertFalse(
					GeneralUtil
							.isSelectListExists(IAmendmentsConst.amendRequest_Category_DropDwnFld_ID),
					"PASS: Amendment Type Field  is available When Applicant (user) make an request an Amendment Request");

			ClicksUtil
					.clickButtons(IClicksConst.amedmentBackToSubmissionsListBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
/*
 * In the below method, FO user will request an amendment and will verify
 * default front office default amendment category in amendment list at program Office
 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentCategoryField" })
	public void verifyFODefaultAmendmentCategoryType() throws Exception {
		try {

			Assert.assertTrue(AmendmentsUtil.requestApplicantAmendment(
					foProj.getCurrentStepName(), amender, "This is a Reason"),
					"FAIL: could not Request Applicant Amendment see previous error!");

			GeneralUtil.switchToPO();

			Assert.assertTrue(AmendmentsUtil
					.isDefaultFOAmendmentCategaoryTypeExists(foProj,
							IGeneralConst.gnrl_SubmissionA[0][0]),
					"FAIL: Default FO Amendment Category is not available");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
