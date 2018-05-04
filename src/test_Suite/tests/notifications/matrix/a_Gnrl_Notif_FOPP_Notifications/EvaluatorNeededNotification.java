/**
 * 
 */
package test_Suite.tests.notifications.matrix.a_Gnrl_Notif_FOPP_Notifications;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.QuartzControlUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;

/**
 * @author k.sharma
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class EvaluatorNeededNotification {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario03";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-Notif-", "");

			foProj = new FOProject(fopp, "Evaluator-Needed-", true,1,"-03");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationWorkFlow" }, alwaysRun = true)
	public void tearDown() throws Exception {

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

	// Create and Submit a project

	@Test(groups = { "NotificationWorkFlow" }, enabled = true)
	public void createAndSubmitProj() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");
			
			GeneralUtil.setNotificationMtrixProp(propKey, foProj.getProjFOFullName());

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmitProj")
	public void evaluateSubmission() throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(
					IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false,
					IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "evaluateSubmission" }, enabled = true)
	public void verifyNotifEvalNeededBeforeJobRun() throws Exception {
		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.approvQuoCritManu[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.gnrlNotifFOPPNotificationsName[3],
					IFiltersConst.exact), 0,
					"FAIL: wrong Notification in Notification Log");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifEvalNeededBeforeJobRun", enabled = true)
	public void runNotificationQuartzJob() throws Exception {
		try {
			
			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" }, enabled = true)
	public void verifyNotifEvalNeededAfterJobRun() throws Exception {
		try {

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.approvQuoCritManu[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.gnrlNotifFOPPNotificationsName[3],
					IFiltersConst.exact), 1,
					"FAIL: wrong Notification in Notification Log");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
