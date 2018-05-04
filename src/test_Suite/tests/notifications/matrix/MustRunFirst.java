/**
 * 
 */
package test_Suite.tests.notifications.matrix;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class MustRunFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	FundingOpportunity fopp1;
	FundingOpportunity fopp2;
	FundingOpportunity fopp3;
	FundingOpportunity fopp4;
	FundingOpportunity fopp5;

	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "NotificationWorkflow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();

			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Notify-Evaluators-PA-", "");
			fopp1 = new FundingOpportunity("A", "-Decision-Notif-", "");
			fopp2 = new FundingOpportunity("A", "-Assoc-PA-", "");
			fopp3 = new FundingOpportunity("A", "-Due-Notif-PA-", "");
			fopp4 = new FundingOpportunity("A", "-Notif-Amend-", "");
			fopp5 = new FundingOpportunity("A", "-Gnrl-Notif-", "");

			foProj = new FOProject(fopp, "", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationWorkflow" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			fopp1 = null;
			fopp2 = null;
			fopp3 = null;
			fopp4 = null;
			fopp5 = null;
			foProj = null;
			

			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "NotificationWorkflow" }, enabled = true)
	public void registerToFopp() throws Exception {
		try {
			

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp1.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp2.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp3.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp4.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");
			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp5.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "NotificationWorkflow" }, dependsOnMethods = "registerToFopp", enabled = true)
	public void changeNotifyEvaluatorPAFOPPStepDate() throws Exception {
		try {
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp, "Approval-CRQA",
					GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

			Assert.assertTrue(FOPPUtil.changePAStepStartDate(fopp,
					"Approval-CRQM-pa", GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step Start Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkflow" }, dependsOnMethods = "changeNotifyEvaluatorPAFOPPStepDate", enabled = true)
	public void changeAssocPAFOPPStepDate() throws Exception {
		try {

			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp2,
					"High-School-Submission", GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkflow" }, dependsOnMethods = "changeAssocPAFOPPStepDate", enabled = true)
	public void changeDueNotifPAFOPPStepDate() throws Exception {
		try {

			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp3,
					"Approval-CRQA", GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationWorkflow" }, dependsOnMethods = "changeDueNotifPAFOPPStepDate", enabled = true)
	public void changeNotifAmendFOPPStepDate() throws Exception {
		try {

			Assert.assertTrue(FOPPUtil.changeStepStartDate(fopp4,
					"Approval-CRQA", GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
