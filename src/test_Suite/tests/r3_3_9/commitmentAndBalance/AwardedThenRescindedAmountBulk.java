/**
 * 
 */
package test_Suite.tests.r3_3_9.commitmentAndBalance;

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
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AwardedThenRescindedAmountBulk {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

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
			
			foProj = new FOProject(fopp, "AwardedAmount-Bulk-", true, orgIndex, "");
			
			Assert.assertTrue(foProj.createFOProjectNewNew(),"FAIL: could not create new FO Project!");
			
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
			
			GeneralUtil.takeANap(2.0);
			
			GeneralUtil.loginReviewer("5");
			
			Assert.assertTrue(ProjectUtil.openBulkEvalStepsForEditing(foProj, "Awarded-Review-CRQA"), "FAIL: could not oepn Bulk");
			
			Assert.assertTrue(ProjectUtil.runScoringAlgorithm(), "FAIL: Could not Run Scoring Algorithm!");
			
			Assert.assertTrue(ProjectUtil.saveBulkEvaluation(), "FAIL: Could not Save Bulk");
			
			Assert.assertTrue(ProjectUtil.submitBulkEvaluation(), "FAIL: Could not Submit Bulk!");
			
			ClicksUtil.clickButtons(IClicksConst.bulkEval_BackToBulkEvalListBtn);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
	
	
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="openAwardedBulkEval")
	public void openRescindedBulkEval() throws Exception {
		try {
			
			Assert.assertTrue(ProjectUtil.openBulkEvalStepsForEditing(foProj, "Rescinded-Review-CRQA"), "FAIL: could not oepn Bulk");
			
			Assert.assertTrue(ProjectUtil.saveBulkEvaluation(), "FAIL: could not Save Bulk!");
			
			Assert.assertTrue(ProjectUtil.submitBulkEvaluation(), "FAIL: could not Submit Bulk!");
			
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

}
