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
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.lib.workflow.ProjectActivity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.NotificationsUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class VerifyAdHocNotif {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	Project proj;

	ProjectActivity projActivity;

	String propKey = "scenario108";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			;
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-", "");

			proj = new Project(fopp, "Ad-Hoc-Notif", true, true,
					EFormsUtil.createAnyRandomString(5)+ "-108");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationWorkFlow" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			proj = null;
			projActivity = null;

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

			proj.newCreateNewOrg(); // This method is always returns false value
									// though work as expected that's why i
									// haven't added assertion

			proj.newCreateNewPOProject();

			GeneralUtil.setNotificationMtrixProp(propKey,
					proj.getProjFullName());

			proj.submitProject(true);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmitProj", enabled = true)
	public void assignOfficers() throws Exception {
		try {
			proj.assignOfficers(new String[][] { {
					IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "assignOfficers")
	public void testSendProjectInstantNotificationNG() throws Exception {

		initializeProjectActivity();

		ProjActivUtil.openInstantNotificationDetails(proj.getProjFullName(),
				IFiltersConst.openProjView);

		ProjActivUtil.AddProjectInstantNotif(projActivity);

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "testSendProjectInstantNotificationNG" }, enabled = true)
	public void verifyAdHocNotif() throws Exception {
		try {

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(
					proj, IGeneralConst.reviewQuoCritAuto[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(NotificationsUtil.verifyNotificationLogEntries(
					projActivity.getActivityNotifName(), IFiltersConst.exact),
					1, "FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void initializeProjectActivity() throws Exception {

		projActivity = new ProjectActivity();

		projActivity.setProjectName(proj.getProjFullName());
		projActivity.setApplicantName(proj.getOrgFullName());
		projActivity.setProgramName(fopp.getFoppFullName());

		projActivity.setActivityNotifName(proj.getProjFullName());

		projActivity.setActivityNotifMessageLocale("/English/");
		projActivity
				.setActivityNotifRecipients(new String[] { "/Shakshouki/" });
		projActivity.setActivityNotifMessageSubjet("Testing AddHoc Notif "
				+ proj.getProjFullName());
		projActivity.setActivityNotifMessageBody("This is The Body");
		projActivity.setActivityNotifExternalRecipients("");

	}

}
