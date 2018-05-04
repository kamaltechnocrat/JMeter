/**
 * 
 */
package test_Suite.tests.notifications.matrixReload.A_Notif_PA_FOPP_S1905_02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IQuartzJobConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
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
public class IPASSAppAmendReqRejectNotif {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	String propKey = "scenario102";

	private static final String newPASuffix = "-pa";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkFlow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Notif-PA-", "-S1905_02");

			foProj = new FOProject(fopp, "IPASS-AppAmend-Rejected-", true, 1,
					"-102");

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

			GeneralUtil.setNotificationMtrixProp(propKey,
					foProj.getProjFOFullName());

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmitProj", enabled = true)
	public void assignEvaluator() throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginPO();

			foProj.assignEvaluators(new String[] {
					IGeneralConst.reviewQuoCritManu[0][0],
					IPreTestConst.Groups[2][1][0] });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "assignEvaluator", enabled = true)
	public void evaluateSubmission() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(
					IGeneralConst.reviewQuoCritManu[0][0], true, "Yes", false,
					IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritAuto[0][0], true, "Ready",
					false, false));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void submitAward() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginPO();

			foProj.setDueDate(GeneralUtil.setDayofMonth(3));

			Assert.assertTrue(foProj.submitAward("Standard", 1, true),
					"FAIL: Could not Submit Standard Award Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "submitAward", enabled = true)
	public void submitInitialClaim() throws Exception {

		try {

			GeneralUtil.switchToFO();

			foProj.setClaimNumber(1);

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.initialClaim[0][0] + newPASuffix, true));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "submitInitialClaim" }, enabled = true)
	public void applicantAmnedRequest() throws Exception {
		try {

			foProj.initializeStep(IGeneralConst.initialClaim[0][0]
					+ newPASuffix);

			String amender = "Applicant:".concat(foProj.getFoOrgName())
					.concat("(").concat(foProj.getFoOrgNumber()).concat(")");

			Assert.assertTrue(AmendmentsUtil.requestApplicantAmendment(
					foProj.getCurrentStepName(), amender, "This is a Reason"),
					"FAIL: could not Request Applicant Amendment see previous error!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "applicantAmnedRequest", enabled = true)
	public void rejectApplicantAmendRequest() throws Exception {
		try {

			GeneralUtil.switchToPO();

			String dd = GeneralUtil.setDayofMonth(3);

			String amender = "Applicant:".concat(foProj.getFoOrgName())
					.concat("(").concat(foProj.getFoOrgNumber()).concat(")");

			Assert.assertTrue(AmendmentsUtil.approveOrRejectAmendmentRequest(
					foProj, new String[] { amender,
							IPreTestConst.Groups[0][0][0], dd, "Rejected",
							"This is Reject Reason",
							"This is Amendment Reason",
							"This is just a comment" },
					IGeneralConst.initialClaim[0][0] + newPASuffix));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "rejectApplicantAmendRequest", enabled = true)
	public void runNotifQuartzJob() throws Exception {
		try {

			Assert.assertTrue(QuartzControlUtil
					.runQuartzJob(IQuartzJobConst.quartzJobName[0]),
					"FAIL: could not run the Quartz job!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = { "runNotifQuartzJob" }, dataProvider = "NotifNameIndex", enabled = true)
	public void verifyAppAmendReqRejectNotif(int NotifIndex, int entryCount,
			boolean resultExpected) throws Exception {
		try {

			foProj.setClaimNumber(0);

			Assert.assertTrue(NotificationsUtil.openNotificationLogInProject(
					foProj, IGeneralConst.postAwardCrit[0][0]),
					"FAIL: Could not open then project submission history");

			Assert.assertEquals(
					NotificationsUtil
							.verifyNotificationLogEntries(
									INotificationsConst.notifPAFOPPS1905_02_NotifName[NotifIndex],
									IFiltersConst.exact), entryCount,
					"FAIL: wrong Notification in Notification Log");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "NotifNameIndex")
	public static Object[][] generateNotifNameIndex() throws Exception {

		return new Object[][] { { 13, 13, true }

		};
	}
}
