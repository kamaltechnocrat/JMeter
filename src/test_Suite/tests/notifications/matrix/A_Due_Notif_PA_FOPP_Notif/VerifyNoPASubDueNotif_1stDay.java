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
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifyNoPASubDueNotif_1stDay {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario23";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Due-Notif-PA-", "");

			foProj = new FOProject(fopp, "Initial-Claim-Step-", true, 1,"-23");

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

	// Initialize the project a project

	@Test(groups = { "NotificationWorkFlow" }, enabled = true)
	public void initializeProject() throws Exception {
		try {
			
			String projFullName = GeneralUtil.getNotifMatrixPropValue(propKey);
			
			Assert.assertNotNull(projFullName, "FAIL: could not get the Value for Key: ".concat(propKey));
			
			Assert.assertNotSame(projFullName, "", "FAIL: this is an empty value for the Key: ".concat(propKey));
			
			foProj.setProjFOFullName(projFullName);
			
			foProj.setProjFullName(projFullName);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "initializeProject" }, enabled = true)
	public void verifyFirstNotifLogInProejects() throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[4],
					IFiltersConst.exact), 3,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyFirstNotifLogInProejects", enabled = true)
	public void submitInitialClaim() throws Exception {
		
		try {

			foProj.setClaimNumber(1);

			Assert.assertTrue(foProj.submitFromApplicantSubList(
					IGeneralConst.initialClaim[0][0], true));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}


	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "submitInitialClaim" }, enabled = true)
	public void amendmentRequestByPO() throws Exception {
		try {
			
			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyProjectSubmissionsList(foProj,
							IGeneralConst.initialClaim[0][0], "Open Projects"),
					"Could not open submission in MyProjectSubmissionList");

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.initialClaim[0][0],
									IPreTestConst.Groups[0][0][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj, "Corrective"),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "initializeProject" }, enabled = true)
	public void verifyNotifLogInBeforeJobRun() throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[7],
					IFiltersConst.exact), 0,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifLogInBeforeJobRun", enabled = true)
	public void runNotificationQuartzJob() throws Exception {
		try {
			
			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");

			foProj.setClaimNumber(0);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {			
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyFinalNotificationLogInProejects(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{7,10,true},
				{4,3,true}				
		};		
	}

}
