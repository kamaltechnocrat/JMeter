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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProgStepUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
public class multipleNotificationsAtApproval {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> Test_AdminEfromData = this.getClass();

	private Log log = LogFactory.getLog(Test_AdminEfromData);

	String preFix = "-Gnrl-PA-";

	boolean stepAllCheckboxValues[] = INotificationsConst.stepAllCheckBoxValues[0];

	String fundingOpportunityName = "A-Gnrl-PA-Notification-FOPP";

	String paCurrentStep = "Approval Step Entry";

	String paCurrentStepA = "Approval Amendment";

	String paCurrentStepB = "Approval Rejected";

	String initialPostAwardStepNameObj = "(Initial Post Award Submission)";

	String approvalStepObj = "(Approval)";

	String stepEntryNotificationName = null;

	String rejectedNotificationName = null;

	String amendmentNotificationName = null;

	String quartzJobName = "NotificationsJobDetail";

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

			foProj = new FOProject(fopp, "Project-Multiple-Notification", true,
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
	public void openFOPPAndAddNotificationAtApproval() throws Exception {
		try {
			Assert.assertTrue(NotificationsUtil
					.navigateToProjPlanner(fundingOpportunityName),
					"FAIL: Couldn't navigate to project planner");

			ProgramUtil.openNotificationDetails(
					IProgramsConst.EProjectType.pre_Award.ordinal(),
					approvalStepObj);

			Assert.assertTrue(ProgStepUtil.addNewNotification(paCurrentStep,
					INotificationsConst.stepEntryValues, stepAllCheckboxValues,
					"0", "1"));

			ProgramUtil.openNotificationDetails(
					IProgramsConst.EProjectType.pre_Award.ordinal(),
					approvalStepObj);

			Assert.assertTrue(ProgStepUtil.addNewNotification(paCurrentStepA,
					INotificationsConst.stepAmendmentRequestValues,
					stepAllCheckboxValues, "0", "1"));

			ProgramUtil.openNotificationDetails(
					IProgramsConst.EProjectType.pre_Award.ordinal(),
					approvalStepObj);

			Assert.assertTrue(ProgStepUtil.addNewNotification(paCurrentStepB,
					INotificationsConst.stepRejectedValues,
					stepAllCheckboxValues, "0", "1"));

			GeneralUtil.switchToFO();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	// Create and Submit a project

	@Test(groups = { "NotificationNG" }, dependsOnMethods = "openFOPPAndAddNotificationAtApproval", enabled = true)
	public void createAndSubmiProj() throws Exception {
		try {
			Assert.assertTrue(FrontOfficeUtil.createAndSubmitStandardAward(
					fopp, foProj, 1), "FAIL: could not complete workflow!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	// PO requestAmendment

	@Test(groups = { "NotificationNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void requestAmendment() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj,
				IGeneralConst.approvQuoCritAuto[0][0],
				IFiltersConst.openProjView);

		Assert.assertTrue(
				GeneralUtil.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
				"FAIL: Request Amendment Link not available!");

		ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil.fillOutAmendments(new String[] {
				foProj.getProjFullName(),
				IGeneralConst.approvQuoCritAuto[0][0],
				IPreTestConst.Groups[3][1][0], dd,
				"Test Issue Amendment for Amendment Notification",
				"This is a Comment" }, foProj, "Corrective"),
				"FAIL: Could not fill out amendments!");

		Assert.assertTrue(
				GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
				"FAIL: Button either does not exists or disabled!");

		ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
	}

	@Test(groups = { "NotificationNG" }, dependsOnMethods = { "requestAmendment" }, enabled = true)
	public void verifyNotificationLogInProejects() throws Exception {
		try {

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			GeneralUtil.takeANap(20.0);
			Assert.assertTrue(ProjectUtil.openNotificationLogInProject(foProj,
					IGeneralConst.approvQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			stepEntryNotificationName = INotificationsConst.stepEntryValues[1]
					+ " - " + paCurrentStep;

			Assert.assertTrue(ProjectUtil.verifyNotificationLogEntries(
					stepEntryNotificationName, IFiltersConst.exact),
					"FAIL: No Schedule due Notification in Notification Log");

			amendmentNotificationName = INotificationsConst.stepAmendmentRequestValues[1]
					+ " - " + paCurrentStepA;
			GeneralUtil.takeANap(20.0);

			Assert.assertTrue(ProjectUtil.verifyNotificationLogEntries(
					amendmentNotificationName, IFiltersConst.exact),
					"FAIL: No Schedule due Notification in Notification Log");

			rejectedNotificationName = INotificationsConst.stepRejectedValues[1]
					+ " - " + paCurrentStepB;

			Assert.assertTrue(ProjectUtil.verifyNotificationLogEntries(
					rejectedNotificationName, IFiltersConst.exact),
					"FAIL: No Schedule due Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
}
