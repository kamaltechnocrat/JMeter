/**
 * 
 */
package test_Suite.tests.notifications.matrix.a_Decision_Notif_FOPP_Notifications;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.NotificationsUtil;

/**
 * @author k.sharma
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class AppAmnedRequestedNotif {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario24";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Decision-Notif-", "");

			foProj = new FOProject(fopp, "Appl-Amend-Requested-",true, 1,"-24");

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
	public void createAndSubmiProj() throws Exception {
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

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "createAndSubmiProj" }, enabled = true)
	public void applicantAmnedRequest() throws Exception {
		try {			

			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

			String amender = "Applicant:".concat(foProj.getFoOrgName())
					.concat("(").concat(foProj.getFoOrgNumber()).concat(")");

			Assert.assertTrue(AmendmentsUtil.requestApplicantAmendment(
					foProj.getCurrentStepName(), amender, "This is a Reason"),
					"FAIL: could not Request Applicant Amendment see previous error!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "applicantAmnedRequest" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifRequestedBeforeJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {
			GeneralUtil.switchToPO();

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.reviewQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.decisionNotifFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), 0,
					"FAIL: No RepeatApplicant Amendment Requested Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifRequestedBeforeJobRun", enabled = true)
	public void runNotificationQuartzJob() throws Exception {
		try {

			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifRequestedAfterJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.reviewQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.decisionNotifFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: No Applicant Amendment Requested Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{0,9,true}
				
		};		
	}
}
