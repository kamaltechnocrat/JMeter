/**
 * 
 */
package test_Suite.tests.notifications.matrix.a_Notify_Evaluators_PA_FOPP_Notif;

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
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ApprovalStepDueNotif_3rdDay {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	String propKey = "scenario35";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Notify-Evaluators-PA-", "");

			foProj = new FOProject(fopp, "Approval-Step-Due-", true, 1,
					"-35");

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
						IGeneralConst.approvQuoCritAuto[0][0]),
						"FAIL: Could not open then project submission history");
				
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "initializeProject" },dataProvider="NotifNameIndex", enabled = true)
	public void verifyNotifStepDueNextDay(int NotifIndex, int entryCount, boolean resultExpected) throws Exception {
		try {

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					INotificationsConst.notifyEvalPAFOPPNotificationName[NotifIndex],
					IFiltersConst.exact), entryCount,
					"FAIL: wrong Notification in Notification Log");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyNotifStepDueNextDay", enabled = true)
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
	
	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception{
		
		return new Object[][] {
				{4,15,true}
				
		};		
	}

}
