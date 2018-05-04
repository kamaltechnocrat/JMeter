/**
 * 
 */
package test_Suite.tests.notifications.matrixReload.ProjectActivityNotif;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProjActivConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.ProjectActivity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.QuartzControlUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class ProjActivityScheduledNotif {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	ProjectActivity projActivity;
	
	FOUsers foUser;

	String propKey = "scenario104";

	String notifyScheduledParams[] = {
			IProjActivConst.projActivity_Details_Type_Email,
			IProjActivConst.projActivity_Details_Type_Email + "-Scheduled",
			"0", "0", IProjActivConst.projActivity_Details_Status_Caution, "",
			"0", "" };

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-", "");

			foProj = new FOProject(fopp, "ProjActivity-Scheduled-Notif", true,
					1, "-104");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationWorkFlow" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			foProj = null;
			projActivity = null;
			foUser = null;

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

	// Create and Submit a project

	@Test(groups = { "NotificationWorkFlow" }, enabled = true)
	public void createAndSubmitProj() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");

			GeneralUtil.setNotificationMtrixProp(propKey,
					foProj.getProjFOFullName());

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmitProj", enabled = true)
	public void assignOfficers() throws Exception {
		try {

			GeneralUtil.switchToPO();

			foProj.assignOfficers(new String[][] { {
					IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "assignOfficers" })
	public void testActivityNotificationsScheduledEmailNG() throws Exception {
		try {

			initializeProjectActivity(notifyScheduledParams, new boolean[] {
					false, true, false });
			fillActivityDetails();
			fillActivityNotif();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "testActivityNotificationsScheduledEmailNG" }, dataProvider = "NotifNameIndex", enabled = true)
	public void verifyProjActivityNotifBeforeJobRun(int NotifIndex,
			int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(
					foProj, IGeneralConst.reviewQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.projActivityNotifName[NotifIndex],
					IFiltersConst.exact), 0,
					"FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyProjActivityNotifBeforeJobRun", enabled = true)
	public void runNotifQuartzJob() throws Exception {
		try {
			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(
					foProj, IGeneralConst.reviewQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotifQuartzJob" }, dataProvider = "NotifNameIndex", enabled = true)
	public void verifyProjActivityNotifAfterJobRun(int NotifIndex,
			int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.projActivityNotifName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception {

		return new Object[][] { { 5, 1, true }

		};
	}

	private void initializeProjectActivity(String[] params, boolean[] bolParams)
			throws Exception {

		projActivity = new ProjectActivity();
		projActivity.setProjectName(foProj.getProjFullName());
		projActivity.setApplicantName(foProj.getFoOrgName());
		projActivity.setProgramName(fopp.getFoppFullName());

		projActivity.setActivityType(params[0]);
		projActivity.setActivityPurpose(params[1]);
		projActivity.setActivityDate(GeneralUtil.setDayofMonth((Integer
				.parseInt(params[2]))));
		projActivity.setActivityDueDate(GeneralUtil.setDayofMonth(Integer
				.parseInt(params[3])));
		projActivity.setActivityStatus(params[4]);
		projActivity.setActivityDescription(params[5]);
		projActivity.setActivityCompleted(bolParams[0]);

		// Setup Notification
		projActivity.setActivityNotifActive(bolParams[1]);
		projActivity.setActivityNotifDaysBefore(params[6]);
		projActivity.setActivityNotifDaysUntil(params[7]);
		projActivity.setActivityNotifRepeat(bolParams[2]);

		projActivity.setActivityNotifMessageLocale("/English/");
		projActivity.setActivityNotifRecipients(setupRecipient(1));
		projActivity.setActivityNotifMessageSubjet(params[1]);
		projActivity.setActivityNotifMessageBody(params[1]);
		projActivity.setActivityNotifExternalRecipients("");

	}

	private void fillActivityDetails() throws Exception {

		Assert.assertTrue(
				ProjActivUtil.openProjectActivity(
						projActivity.getProjectName(),
						IFiltersConst.openProjView),
				"Fail: Opening Activity for Project "
						+ projActivity.getProjectName());
		Assert.assertTrue(
				ProjActivUtil.openNewActivity(),
				"Fail: Openning New Activity for Project "
						+ projActivity.getProjectName());
		Assert.assertTrue(
				ProjActivUtil.addProjectActivityDetails(projActivity),
				"Fail: Could not save Activity Details for "
						+ projActivity.getProjectName());
	}

	private void fillActivityNotif() throws Exception {

		Assert.assertTrue(
				ProjActivUtil.openActivity(projActivity.getActivityPurpose()),
				"Fail: To open Activity");
		Assert.assertTrue(
				ProjActivUtil.addProjectActivityNotification(projActivity),
				"Fail: To Fill Notifications");
	}

	private String[] setupRecipient(int counts) throws Exception {
		String recipients[] = new String[counts];

		for (int i = 1; i <= counts; i++) {
			foUser = new FOUsers(i);
			recipients[(i - 1)] = foUser.getFoRegistrantFullId();
		}

		return recipients;
	}
}
