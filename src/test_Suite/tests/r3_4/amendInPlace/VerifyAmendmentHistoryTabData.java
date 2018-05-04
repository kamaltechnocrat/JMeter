/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
public class VerifyAmendmentHistoryTabData {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String amendmentIcontext = null;

	String amendmentID = null;

	String str1[];

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

			foProj = new FOProject(fopp, "AmendmentHistory-Tab-Data-", true, 1);

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

	// Create and Submit a project

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

	// Evaluate submission step

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void evaluateSubmission() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(
				IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false,
				false));
	}

	// Submit Award step

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		Assert.assertTrue(foProj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Basic Award Form");
	}

	// Request Amendment on submission step

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "submitAward" })
	public void requestAmendmentOnSubmission() throws Exception {
		try {

			hashSteps = new Hashtable<String, String>();

			foProj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "Yes");

			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "NO");

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

	/*
	 * This method will return amendment history data from project submission
	 * history
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "requestAmendmentOnSubmission" }, enabled = true)
	public void getAmendmentHistoryDataFromProjectSubmissionHistory()
			throws Exception {
		try {

			Assert.assertTrue(
					ProjectUtil
							.OpenAmendedStepAmendmentHistoryInProjectSubmissionHistory(
									foProj,
									IFiltersConst.projects_SubmisisonHistory_StepStatus),
					"FAIL: Could not open then project submission history");

			Assert.assertTrue(ProjectUtil.isAmendmentHistoryTabExist(),
					"FAIL: Amendement History tab is not available");

			str1 = TablesUtil
					.getDataFromTable(ITablesConst.project_AmendmentSubmissionHistoryTableId);

			ClicksUtil
					.clickButtons(IClicksConst.backToSubmissionHistoryListBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	/*
	 * This method will verify the project submission history data with
	 * amendment history data in Amendments
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "getAmendmentHistoryDataFromProjectSubmissionHistory" }, enabled = true)
	public void verifyProjectAmendmentHistoryDataWithAmendmentsListHistoryData()
			throws Exception {
		try {

			Assert.assertTrue(AmendmentsUtil.openAmendedStepAmendmentDetails(
					foProj, subStep, "", ""),
					"FAIL: Could not open amendment deatils");

			Assert.assertTrue(ProjectUtil.isAmendmentHistoryTabExist(),
					"FAIL: Amendement History tab is not available");

			Assert.assertTrue(TablesUtil.verifyDataFromTable(
					ITablesConst.project_AmendmentSubmissionHistoryTableId,
					str1), "FAIL: Both Amendment History list data is not same");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

}
