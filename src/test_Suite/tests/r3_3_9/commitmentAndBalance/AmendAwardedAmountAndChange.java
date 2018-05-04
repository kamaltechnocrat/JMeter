/**
 * 
 */
package test_Suite.tests.r3_3_9.commitmentAndBalance;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AmendAwardedAmountAndChange {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	ArrayList<FOProject> projNames = new ArrayList<FOProject>();
	
	Hashtable<String, Double> amounts = new Hashtable<String, Double>();

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.commBal_PA_Prefix, "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		projNames = null;
		amounts = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dataProvider="Applicants-Data")
	public void createAndSubmitFoProject(String usrName, int orgIndex) throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(usrName);
			
			foProj = new FOProject(fopp, "Amend-AwardedAmount-Bulk-", true, orgIndex, "");	
			
			Assert.assertTrue(foProj.createFOProjectNewNew(),"FAIL: could not create new FO Project!");
			
			projNames.add(foProj);
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionAndSubmit(foProj,"Submission A"), "FAIL: Could not open or Submit!");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createAndSubmitFoProject")
	public void openAwardedBulkEval() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			
			GeneralUtil.takeANap(1.5);
			
			GeneralUtil.loginReviewer("5");
			
			Assert.assertTrue(ProjectUtil.openBulkEvalStepsForEditing(foProj, "Awarded-Review-CRQA"), "FAIL: could not oepn Bulk");
			
			GeneralUtil.takeANap(1.5);	
			
			Assert.assertTrue(ProjectUtil.runScoringAlgorithm(), "FAIL: Could not Run Scoring Algorithm!");
			
			GeneralUtil.takeANap(1.5);
			
			Assert.assertTrue(ProjectUtil.saveBulkEvaluation(), "FAIL: Could not Save Bulk");
			
			Assert.assertTrue(ProjectUtil.submitBulkEvaluation(), "FAIL: Could not Submit Bulk!");
			
			ClicksUtil.clickButtons(IClicksConst.bulkEval_BackToBulkEvalListBtn);
			
			GeneralUtil.switchToPO();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dataProvider="Applicants-Data", dependsOnMethods="openAwardedBulkEval")
	public void amendAwardedAmount(String usrName, Integer orgIndex) throws Exception {
		try {
			
			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
					projNames.get(orgIndex - 1).getProjFOFullName(),
					"Awarded-Review-CRQA",
					"/LReviewer3/", dd,
					"Test Amending Awarded Amount", "This a Comment" }, foProj));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="amendAwardedAmount", dataProvider="Amounts-Data")
	public void checkBudgetAndBalance(String strAmntType, Double amount) throws Exception {
		try {
			
			fopp.openFundingOppPlanner();
			
			fopp.openBudgetingEForm();
			
			String str = GeneralUtil.readTextByLabel(strAmntType).replace("$", "");
			
			str = str.replace(",", "");
			
			log.warn(str);
			
			Double dbl = Double.parseDouble(str);
			
			dbl = dbl + amount;
			
			log.warn(dbl);
			
			amounts.put(strAmntType, dbl);
			
			log.warn(amounts.get(strAmntType));
			
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
	public void loginToPO() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("3");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dataProvider="Applicants-Data", dependsOnMethods="loginToPO")
	public void changeAwardedValue(String usrName, Integer orgIndex) throws Exception {
		try {
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(projNames.get(orgIndex - 1), "Awarded-Review-CRQA", "In Progress", "Awarded", false, "2", "Latest Version");
			
			Assert.assertTrue(ProjectUtil.enterValueAndSubmitCommBal("750000.00", "Awarded Amount"),"FAIL: to enter and Submit Value to Awarded Amount");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="changeAwardedValue")
	public void loginAgainPO() throws Exception {
		try {
			
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
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="loginAgainPO", dataProvider="Amounts-Data")
	public void verifyBalance(String strAmntType, Double amount) throws Exception {
		try {
			
			fopp.openBudgetingEForm();
			
			String str2 = GeneralUtil.readTextByLabel(strAmntType).replace("$", "");
			
			str2 = str2.replace(",", "");
			
			Assert.assertTrue(amounts.get(strAmntType).equals(Double.parseDouble(str2)), "FAIL: amount is not equal");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateRegLoginData() throws Exception{
		
		return new Object[][] {
				{"front", 1},
				{"front2", 2},
				{"front3", 3}
				
		};
		
	}	
	
	@DataProvider(name = "Amounts-Data")
	public static Object[][] generateAmountsAndLabel() throws Exception{
		
		return new Object[][] {
				{IFoppConst.commBal_Budget_InitialBal_Ttl, 0.0},
				{IFoppConst.commBal_Budget_SumOfAwarded_Ttl, -6750000.0},
				{IFoppConst.commBal_Budget_CurrentBal_Ttl, 6750000.0}
				
		};
		
	}

}
