/**
 * 
 */
package test_Suite.tests.r3_4.subScheduleEnhancement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProgStepUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
public class VerifyTrueValueConditionalNotification {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> VerifyTrueValueConditionalNotification = this.getClass();

	private Log log = LogFactory.getLog(VerifyTrueValueConditionalNotification);

	boolean stepAllCheckboxValues[] = INotificationsConst.stepAllCheckBoxValues[0];

	String fundingOpportunityName = "A-Gnrl-PA-Notification-FOPP";

	String paCurrentStep = "Inital-PostAward Conditional Submission Schedule Due";

	String initialPostAwardStepNameObj = "(Initial Post Award Submission)";

	String dataFromStep = "A-Gnrl-PA-Notification-Award-Crit";

	String notificationName = null;

	String expression = null;

	FundingOpportunity fopp;

	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationNG" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-PA-Notification-", "");

			foProj = new FOProject(fopp, "Project-Schedule-Notification", true,
					1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {

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

	@Test(groups = { "NotificationNG" }, enabled = true)
	public void openFOPPAndAddInitialPostAwardNotificationAtPO()
			throws Exception {
		try {
			Assert.assertTrue(NotificationsUtil
					.navigateToProjPlanner(fundingOpportunityName),
					"FAIL: Couldn't navigate to project planner");

			ProgramUtil.openNotificationDetails(
					IProgramsConst.EProjectType.post_Award.ordinal(),
					initialPostAwardStepNameObj);

			Assert.assertTrue(ProgStepUtil.addConditionalNotification(
					paCurrentStep, INotificationsConst.stepPASubmissionDue,
					stepAllCheckboxValues, "2", "1", dataFromStep,
					INotificationsConst.ntfExpressionValue));

			GeneralUtil.switchToFO();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	// Create and Submit a project

	@Test(groups = { "NotificationNG" }, dependsOnMethods = "openFOPPAndAddInitialPostAwardNotificationAtPO", enabled = true)
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

	@Test(groups = { "NotificationNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void evaluateSubmission() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(
				IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false,
				false));
	}

	// Submit Award step

	@Test(groups = { "NotificationNG" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		Assert.assertTrue(foProj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Basic Award Form");
	}

	@Test(groups = { "NotificationNG" }, dependsOnMethods = { "submitAward" }, enabled = true)
	public void verifyNotificationLogInProejects() throws Exception {
		try {
			GeneralUtil.takeANap(40.0);
			Assert.assertTrue(ProjectUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open project submission history");

			notificationName = INotificationsConst.stepPASubmissionDue[1]
					+ " - " + paCurrentStep;
			GeneralUtil.takeANap(40.0);
			Assert.assertTrue(ProjectUtil.verifyNotificationLogEntries(
					notificationName, IFiltersConst.exact),
					"FAIL: No Schedule due Notification in Notification Log");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

}
