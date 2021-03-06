/**
 * 
 */
package test_Suite.tests.r3_3_9.commitmentAndBalance;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AwardThenCancelAmountSingle {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Hashtable<String, Double> amounts = new Hashtable<String, Double>();

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.commBal_PA_Prefix, "");
			
			foProj = new FOProject(fopp, "CancelAmount-Single-", true, 1, "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		amounts = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createAndSubmitFoProject() throws Exception {
		try {
			Assert.assertTrue(foProj.createFOProjectNewNew(),"FAIL: could not create new FO Project!");
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionAndSubmit(foProj,"Submission A"), "FAIL: Could not open or Submit!");
			
			GeneralUtil.switchToPO();
			
			fopp.openFundingOppPlanner();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createAndSubmitFoProject", dataProvider="Amounts-Data")
	public void checkBudgetAndBalance(String strAmntType, Double amount) throws Exception {
		try {
			
			fopp.openBudgetingEForm();
			
			String str = GeneralUtil.readTextByLabel(strAmntType).replace("$", "");
			
			str = str.replace(",", "");
			
			if(str.equals(""))
			{
				str = "0.0";
			}
			
			log.warn(strAmntType + " - " + str);
			
			Double dbl = Double.parseDouble(str);
			
			dbl = dbl + amount;
			
			log.warn(strAmntType + " - " + dbl);
			
			amounts.put(strAmntType, dbl);
			
			log.warn( strAmntType + " " + amounts.get(strAmntType));
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="checkBudgetAndBalance")
	public void reviewAndAwardValue() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Awarded-Review-CRQA", "Ready", "Awarded", false, "", "");
			
			Assert.assertTrue(ProjectUtil.enterValueAndSubmitCommBal("3000000.00", "Awarded Amount"),"FAIL: to enter and Submit Value to Awarded Amount");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="reviewAndAwardValue")
	public void reviewAndRescindValue() throws Exception {
		try {
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Rescinded-Review-CRQA", "Ready", "Rescinded", false, "", "");
			
			Assert.assertTrue(ProjectUtil.enterValueAndSubmitCommBal("500000.00", "Rescinded Amount"),"FAIL: to enter and Submit Value to Rescinded Amount");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="reviewAndRescindValue")
	public void reviewAndRefundValueTest() throws Exception {
		try {
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Refunded-Review-CRQA", "Ready", "Refunded", false, "", "");
			
			Assert.assertTrue(ProjectUtil.enterValueAndSubmitCommBal("500000.00", "Refunded Amount"),"FAIL: to enter and Submit Value to Refunded Amount");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="reviewAndRefundValueTest")
	public void approveProject() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("5");
			
			Assert.assertTrue(ProjectUtil.openBulkEvalStepsForEditing(foProj, "Approval-CRQA"), "FAIL: could not oepn Bulk");
			
			Assert.assertTrue(ProjectUtil.runScoringAlgorithm(), "FAIL: Could not Run Scoring Algorithm!");
			
			GeneralUtil.takeANap(1.5);
			
			Assert.assertTrue(ProjectUtil.saveBulkEvaluation(), "FAIL: Could not Save Bulk");
			
			Assert.assertTrue(ProjectUtil.submitBulkEvaluation(), "FAIL: Could not Submit Bulk!");
			
			ClicksUtil.clickButtons(IClicksConst.bulkEval_BackToBulkEvalListBtn);
			
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="approveProject")
	public void awardProject() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			foProj.initializStep("Award");
			
			Assert.assertTrue(ProjectUtil.openAwardInList(foProj), "FAIL: Could not open Award submission!");
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: Could not Submit Award");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="awardProject")
	public void submitInitialClaim() throws Exception {
		try {
			GeneralUtil.switchToFO();
			
			foProj.initializeSubProjectFullName("Payment");
			
			FrontOfficeUtil.openFO_SubprojectStep(foProj, IGeneralConst.initialClaim[0][0]);
			
			log.warn(foProj.getSubProjFullName());
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: Could not submit Initial Claim!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="submitInitialClaim")
	public void submitPayment() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Payment-Review-CRQA", "Ready", "Payment", false, "", "");
			
			Assert.assertTrue(ProjectUtil.enterValueAndSubmitCommBal("2000000", "Payment Amount"),"FAIL: to enter and Submit Value to Refunded Amount");

			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="submitPayment")
	public void cancelPayment() throws Exception {
		try {
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Cancel-Review-CRQA", "Ready", "Cancel", false, "", "");
			
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle("Cancel Or Not?", "Yes"), "FAIL: to Cancel Payment");
			
			Assert.assertTrue(GeneralUtil.setTextByTitle("Enter Cancelled Amount", "2000000.0"));
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: Could not Submit form!");
			
			ClicksUtil.returnFromAnyForm();
			
			GeneralUtil.switchToPO();
			
			fopp.openFundingOppPlanner();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="cancelPayment", dataProvider="Amounts-Data")
	public void verifyAmountsReflectInBudgetingForm(String strAmntType, Double amount) throws Exception {
		try {
			
			fopp.openBudgetingEForm();
			
			String str2 = GeneralUtil.readTextByLabel(strAmntType).replace("$", "");
			
			str2 = str2.replace(",", "");
			
			Assert.assertTrue(amounts.get(strAmntType).equals(Double.parseDouble(str2)), "FAIL: amount is not equal");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
		
	}
	
	
	
	@DataProvider(name = "Amounts-Data")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{IFoppConst.commBal_Budget_InitialBal_Ttl, 0.0},
				{IFoppConst.commBal_Budget_SumOfAwarded_Ttl, 3000000.0},
				{IFoppConst.commBal_Budget_Rescinded_Ttl, 500000.0},
				{IFoppConst.commBal_Budget_Refunded_Ttl, 500000.0},
				{IFoppConst.commBal_Budget_Cancel_Ttl, 2000000.0},
				{IFoppConst.commBal_Budget_CurrentBal_Ttl, 0.0}				
		};		
	}
}
