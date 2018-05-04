/**
 * 
 */
package test_Suite.tests.notifications.matrix.a_Notif_Amend_FOPP_Notifications;

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
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ApprovalStepAmendNotifOnly_3rdDay {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario19";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginApprover("1");
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Notif-Amend-", "");

			foProj = new FOProject(fopp, "Approval-Step-Amend-",true, 1, "-19");

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

	// Initialize project

	@Test(groups = { "NotificationWorkFlow" }, enabled = true)
	public void initializeProject() throws Exception {
			try {
				
				String projFullName = GeneralUtil.getNotifMatrixPropValue(propKey);
				
				Assert.assertNotNull(projFullName, "FAIL: could not get the Value for Key: ".concat(propKey));
				
				Assert.assertNotSame(projFullName, "", "FAIL: this is an empty value for the Key: ".concat(propKey));
				
				foProj.setProjFOFullName(projFullName);
				
				foProj.setProjFullName(projFullName);
				
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "initializeProject", enabled = true)
	public void evaluateSubmission() throws Exception {
		try {

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0],
					true,
					IFiltersConst.evaluateSubmissions_Ready_StatusSubView,
					false,
					false));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void submitAward() throws Exception {
		try {

			GeneralUtil.switchToPOWithProjOfficer("1");

			Assert.assertTrue(foProj.submitAward("Basic", 0, true),
					"FAIL: Could not Submit Basic Award Form");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "submitAward" }, enabled = true)
	public void amendmentRequestByPO() throws Exception {
		try {
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.approvQuoCritAuto[0][0],
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
									IGeneralConst.approvQuoCritAuto[0][0],
									IPreTestConst.Groups[3][1][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj, "Corrective"),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.approvQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "amendmentRequestByPO" }, enabled = true)
	public void verifyNotifAmendBeforeJobRun() throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.notifAmendFOPPNotificationName[4],
					IFiltersConst.exact), 0,
					"FAIL: wrong Notification in Notification Log");
			
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
					IGeneralConst.approvQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifAmendAfterJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.notifAmendFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Notification in Notification Log");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{4,5,true}
				
		};		
	}

}
