/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class POviewsProSubmissionHistory {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String amendmentIcontext = null;

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

			foProj = new FOProject(fopp, "Project-Submission-History-", true, 1);

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

			Assert.assertTrue(FrontOfficeUtil.createAndSubmitStandardAward(
					fopp, foProj, 1), "FAIL: could not complete workflow!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/*
	 * Project Officer will open the assigned Project from
	 * MyProjstcSubmissionList and will request an amendment
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "createAndSubmiProj" })
	public void amendmentRequestByPO() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.pa_AwardCrit[0][0], "Open Projects"),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.approvQuoCritAuto[0][0],
									IPreTestConst.Groups[0][0][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj, "Corrective"),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/*
	 * Method will open amended step in Project Submission History ,return the
	 * Requested amendment icon format and will verify the AmendmentIcon text
	 * format
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "amendmentRequestByPO" }, enabled = true)
	public void verifyProjectSubmissonHistory() throws Exception {
		try {

			Assert.assertTrue(
					ProjectUtil
							.getAmendedStepInProjectSubmissionHistory(
									foProj,
									IFiltersConst.projects_SubmisisonHistory_StepStatus),
					"FAIL: Could not open then project submission history");

			amendmentIcontext = ProjectUtil
					.getAmendedStepDetailInProjectSubmissionHistory(foProj);

			Assert.assertTrue(
					AmendmentsUtil.amendmentIconTextFormat(amendmentIcontext),
					"FAIL: The entire text format doesn't matched");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	/*
	 * Method will open amended step in Project Submission History ,return the
	 * Cancelled Request amendment icon format and will verify the AmendmentIcon
	 * text format
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyProjectSubmissonHistory" }, enabled = true)
	public void cancelAmendmentRequestAndVerifyInProjectSubmissionHistory()
			throws Exception {
		try {

			Assert.assertTrue(AmendmentsUtil.cancelStepAmendment(foProj,
					IGeneralConst.pa_AwardCrit[0][0], "2",
					IFiltersConst.versionNumber_EqualTo),
					"FAIL: could not Cancel Step Amendment");

			Assert.assertTrue(
					ProjectUtil
							.getAmendedStepInProjectSubmissionHistory(
									foProj,
									IFiltersConst.projects_SubmisisonHistory_StepStatus,
									IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");

			amendmentIcontext = ProjectUtil
					.getAmendedStepDetailInProjectSubmissionHistory(foProj);

			Assert.assertTrue(AmendmentsUtil
					.cancelRequestAmendmentIconTextFormat(amendmentIcontext),
					"FAIL: The entire text format doesn't matched");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
}
