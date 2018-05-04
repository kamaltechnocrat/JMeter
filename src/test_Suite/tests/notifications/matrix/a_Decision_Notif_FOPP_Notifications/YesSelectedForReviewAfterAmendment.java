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
import test_Suite.utils.workflow.NotificationsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class YesSelectedForReviewAfterAmendment {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario27";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginApprover("1");
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Decision-Notif-", "");

			foProj = new FOProject(fopp, "Verify-WorkFlow-", true,	1, "-27");

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
	public void approvalSubmission() throws Exception {
		try {

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0], true, "Ready",
					false, false));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "approvalSubmission", enabled = true)
	public void runNotificationQuartzJob() throws Exception {
		try {
			GeneralUtil.switchToPO();

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

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotificationQuartzJob" },dataProvider="SecondNotifName", enabled = true)
	public void verifySecondNotificationLogInProejects(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			// To verify availability of Notifications

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.decisionNotifFOPPNotificationName[NotifIndex],
					IFiltersConst.exact),
					entryCount,
					"FAIL: No RepeatApplicant Amendment Requested Notification in Notification Log yet!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}	
	
	@DataProvider(name = "SecondNotifName")
	public static Object[][] generateSecondNotifIndexAndResult() throws Exception{
		
		return new Object[][] {
				{3,5,true},
				{5,1,true},
				{6,1,true}				
		};		
	}

}
