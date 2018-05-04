/**
 * 
 */
package test_Suite.tests.notifications.matrixReload.A_Notif_PA_FOPP_S1905_04;

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
 * @author k.sharma
 *
 */
@GUITest
@Test(singleThreaded = true)
public class PAReviewStepDueNotif_1stDay {
	// #####***********************************************************************
				// ### To set up the Global Params Name Vars
				// #####***********************************************************************
				Class<?> ownClass = this.getClass();

				private Log log = LogFactory.getLog(ownClass);

				FundingOpportunity fopp;

				FOProject foProj;

				String propKey = "scenario99";
				
				private static final String newPASuffix = "-pa";

				/*------ End of Global Vars --------------*/

				@BeforeClass(groups = { "NotificationWorkFlow" })
				public void setUp() throws Exception {

					try {

						log.warn("Starting: " + this.getClass().getSimpleName());

						IEUtil.openNewBrowser();
						GeneralUtil.navigateToPO();
						GeneralUtil.logInSuper();
						// -----------------------------------

						fopp = new FundingOpportunity("A", "-Notif-PA-", "-S1905_04");

						foProj = new FOProject(fopp, "PAReview-Step-Due-", true, 1, "-99");
						
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

				// Initialise Project

				@Test(groups = { "NotificationWorkFlow" }, enabled = true)
				public void initializeProject() throws Exception {
					try {

						String projFullName = GeneralUtil.getNotifMatrixPropValue(propKey);

						Assert.assertNotNull(projFullName,
								"FAIL: could not get the Value for Key: ".concat(propKey));

						Assert.assertNotSame(projFullName, "",
								"FAIL: this is an empty value for the Key: "
										.concat(propKey));

						foProj.setProjFOFullName(projFullName);

						foProj.setProjFullName(projFullName);

						Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(
								foProj, IGeneralConst.postAwardCrit[0][0]),
								"FAIL: Could not open then project submission history");

					} catch (Exception e) {
						Assert.fail("Unexpected Exception: ", e);
					}
				}

				@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "initializeProject" }, dataProvider = "NotifNameIndex", enabled = true)
				public void verifyStepDueNotifNextDayBeforeEvalPAReview(int NotifIndex, int entryCount,
						boolean resultExpected) throws Exception {
					try {
						Assert.assertEquals(
								NotificationsUtil
										.verifyNotificationLogEntries(
												INotificationsConst.notifPAFOPPS1905_04_NotifName[NotifIndex],
												IFiltersConst.exact), entryCount,
								"FAIL: wrong Notification in Notification Log");

					} catch (Exception e) {
						Assert.fail("Unexpected Exception: ", e);
					}
				}

				@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "verifyStepDueNotifNextDayBeforeEvalPAReview", enabled = true)
				public void evaluateInitialClaim() throws Exception {
					try {

						GeneralUtil.switchToPOOnly();
						GeneralUtil.loginReviewer("1");
						
						foProj.setClaimNumber(1);

						Assert.assertFalse(foProj.reviewSubmission(
								IGeneralConst.reviewQuoCritAuto[0][0] + newPASuffix, true,
								"Yes", false,
								IFiltersConst.evaluateSubmissions_Ready_StatusSubView), "FAILED: the Step should be ended already!");
					} catch (Exception e) {
						Assert.fail("Unexpected Exception: ", e);
					}

				}
				
				@DataProvider(name = "NotifNameIndex")
				public static Object[][] generateNotifNameIndex() throws Exception {

					return new Object[][] { { 6, 31, true }

					};
				}
}
