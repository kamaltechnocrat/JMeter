/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class POviewsReexecutionInfoInProSubmissionHistory {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String amendmentIcontext = null;

	Hashtable<String, String> hashSteps;

	String subStep = IGeneralConst.gnrl_SubmissionA[0][0];

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

			foProj = new FOProject(fopp, "Re-Exe-Project-Submission-History-",
					true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void createAndSubmiProj() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void evaluateSubmission() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(
				IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false,
				false));
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "evaluateSubmission")
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		Assert.assertTrue(foProj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Basic Award Form");
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "submitAward" })
	public void requestAmendmentOnSubmission() throws Exception {
		try {

			hashSteps = new Hashtable<String, String>();

			foProj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "Yes");

			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "No");

			foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "Yes");

			String dd = GeneralUtil.setDayofMonth(3);
			String amender = "Applicant:".concat(foProj.getFoOrgName())
					.concat("(").concat(foProj.getFoOrgNumber()).concat(")");

			Assert.assertTrue(AmendmentsUtil
					.issueAmendInPlaceAmendmentWithOptionalRe_Exec(
							new String[] { foProj.getProjFullName(), subStep,
									amender, dd,
									"Test Re-Execute on Previous Amendment",
									"This a Comment" }, hashSteps, foProj),
					"FAIL: Not able to Request an Amendment!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "requestAmendmentOnSubmission", enabled = true)
	public void reSubmitApplicantSubmission() throws Exception {

		GeneralUtil.switchToFOOnly();
		GeneralUtil.LoginFO();

		Assert.assertTrue(foProj.submitAmendedFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "reSubmitApplicantSubmission" }, enabled = true)
	public void verifyReExecutionProjectSubmissonHistory() throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			GeneralUtil.logInSuper();

			Assert.assertTrue(
					ProjectUtil
							.getAutoReexecutionAmendedStepInProjectSubmissionHistory(
									foProj,
									IFiltersConst.projects_SubmisisonHistory_StepStatus,
									IGeneralConst.approvQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			amendmentIcontext = ProjectUtil
					.getAmendedStepDetailInProjectSubmissionHistory(foProj);

			Assert.assertTrue(AmendmentsUtil
					.amendmentIconAutoReexexcuteTextFormat(amendmentIcontext),
					"FAIL: The entire text format doesn't matched");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

}
