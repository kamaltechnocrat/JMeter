/**
 * 
 */
package test_Suite.tests.notifications.matrix.A_Due_Notif_PA_FOPP_Notif;

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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.QuartzControlUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class VerifyAwardAmendmentAloneNotif {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario12";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Due-Notif-PA-", "");

			foProj = new FOProject(fopp, "Award-Step-Amendment-Alone-", true,1, "-12");

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

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
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

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void approveSubmission() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0], true, "Ready",
					false, false));
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "approveSubmission", enabled = true)
	public void submitAward() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");

			Assert.assertTrue(foProj.submitAward("Standard", 1, true),
					"FAIL: Could not Submit Standard Award Form");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "submitAward" }, enabled = true)
	public void amendmentRequestByPO() throws Exception {
		try {

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.gnrl_AwardCrit[0][0],
							"Open Projects"),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.gnrl_AwardCrit[0][0],
									IPreTestConst.Groups[6][1][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj, "Corrective"),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.gnrl_AwardCrit[0][0]),
					"FAIL: Could not open then project submission history");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "amendmentRequestByPO" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifAmendBeforeJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), 0,
					"FAIL: Wrong Notification in Notification Log!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifAmendBeforeJobRun", enabled = true)
	public void runNotificationQuartzJob() throws Exception {
		try {
			
			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.gnrl_AwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifAmendAfterJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: No RepeatApplicant Amendment Requested Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{3,1,true}
				
		};		
	}
}
