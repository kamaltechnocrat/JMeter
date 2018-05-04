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
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author k.sharma
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ApprovalStepAmendmentNotifOnly {
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
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
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

	// Create and Submit a project

	@Test(groups = { "NotificationWorkFlow" }, enabled = true)
	public void createAndSubmitProj() throws Exception {
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

	@Test(groups = { "NotificationWorkFlow" }, dependsOnMethods = "createAndSubmitProj", enabled = true)
	public void assignEvaluator() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(foProj.assignAllAvailableEvaluator(IGeneralConst.reviewQuoCritManu[0][0]),"FAIL: Couldn't assign all Evalauators");
			
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

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
				
}
