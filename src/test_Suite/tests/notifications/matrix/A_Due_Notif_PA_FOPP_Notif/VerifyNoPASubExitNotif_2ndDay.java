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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.QuartzControlUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifyNoPASubExitNotif_2ndDay {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario22";

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

			foProj = new FOProject(fopp, "Initial-Claim-Step-", true, 1,"-22");

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

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "initializeProject" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifIPASamendNextDay(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}		

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifIPASamendNextDay", enabled = true)
	public void reSubmitInitialClaim() throws Exception {
		
		try {

			foProj.setClaimNumber(1);

			Assert.assertTrue(
					ProjectUtil.resubmitInitialClaimInPO(
							foProj,
							IGeneralConst.initialClaim[0][0],
							IFiltersConst.openProjView, IFiltersConst.submissionVersion_LatestVersion,
							IFiltersConst.submissionsStatus_InProgress_StatusSubView),
							"FAIL: Could not resubmit the Initial post Award for proj: ".concat(foProj.getProjFOFullName()));

			foProj.setClaimNumber(0);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "reSubmitInitialClaim" },dataProvider="NotifNameIndex", enabled = true)
	public void verifySecondNotifExitBeforeJobRun(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifySecondNotifExitBeforeJobRun", enabled = true)
	public void runNotifQuartzJobForPAExit() throws Exception {
		try {
			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(foProj,
					IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotifQuartzJobForPAExit" }, enabled = true)
	public void verifySecondNotifExitAfterJobRun() throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.dueNotifPAFOPPNotificationName[6],
					IFiltersConst.exact), 20,
					"FAIL: wrong Amendment Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{7,30,true},
				{6,10,true}				
		};		
	}

}
