/**
 * 
 */
package test_Suite.tests.notifications.matrixReload;

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
 * @author k.sharma
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

			fopp = new FundingOpportunity("A", "-Notif-PA-",  "-S1905_01");
			fopp1 = new FundingOpportunity("A", "-Notif-PA-", "-S1905_02");
			fopp2 = new FundingOpportunity("A", "-Notif-PA-", "-S1905_03");
			fopp3 = new FundingOpportunity("A", "-Notif-PA-", "-S1905_04");
			fopp4 = new FundingOpportunity("A", "-Gnrl-", "");
			fopp5 = new FundingOpportunity("A","-Gnrl-PA-Notification-","");

			foProj = new FOProject(fopp, "", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationWorkflow" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			foProj = null;
			fopp1 = null;
			fopp2 = null;
			fopp3 = null;
			fopp4 = null;
			fopp5 = null;
			
			
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

	@Test(groups = { "NotificationWorkflow" }, dependsOnMethods = "registerToFopp", enabled = true)
	public void changeNotifPAFOPPS190504StepDate() throws Exception {
		try {
			GeneralUtil.switchToPO();

			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp3, "Submission-A",
					GeneralUtil.setDayofMonth(2)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp3, "Review-CRQM",
					GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp3, "Approval-CRQA",
					GeneralUtil.getTodayDate()),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
			Assert.assertTrue(FOPPUtil.changeStepEndDate(fopp3, "Post-Award",
					GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
			Assert.assertTrue(FOPPUtil.changePAStepEndDate(fopp3,
					"Initial-Claim-pa", GeneralUtil.setDayofMonth(2)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

			Assert.assertTrue(FOPPUtil.changePAStepEndDate(fopp3,
					"Review-CRQA-pa", GeneralUtil.getTodayDate()),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
			Assert.assertTrue(FOPPUtil.changePAStepEndDate(fopp3,
					"Approval-CRQM-pa", GeneralUtil.setDayofMonth(3)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
			
			Assert.assertTrue(FOPPUtil.changePAStepEndDate(fopp3,
					"Submission-pa", GeneralUtil.setDayofMonth(2)),
					"FAIL: Could not change the step End Date");

			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
