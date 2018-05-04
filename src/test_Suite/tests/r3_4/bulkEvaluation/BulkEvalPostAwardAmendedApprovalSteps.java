package test_Suite.tests.r3_4.bulkEvaluation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * 
 * @author s.grobbelaar
 *
 */
@GUITest
@Test(singleThreaded = true)
public class BulkEvalPostAwardAmendedApprovalSteps {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	private String foundProj;
	
	private String stepName;
	
	private String projType = "PoAAmend";


	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny("Approver1");
			// -----------------------------------

			fopp = new FundingOpportunity("A","-Gnrl-PA-", "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"WorkflowNG"})
	public void prepProject() throws Exception{
	
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk), "Fail: Could not open Assigned Submissions Link!");
		
		Assert.assertTrue(FiltersUtil.filterByFopp_projName_subStatus(fopp.getFoppFullName(), projType, IFiltersConst.contains, IFiltersConst.submissionsStatus_Ready_StatusSubView), 
				"Fail: Could not filter for Projects!");
		
		foundProj = GeneralUtil.getFirstProjName_EvalSubs(ITablesConst.evaluateSubmissions_Table_ID);
		
		stepName = TablesUtil.verifyStringinTable(ITablesConst.evaluateSubmissions_Table_ID, 0, 4);
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "prepProject")
	public void useBulkEval_oneProj() throws Exception {
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openBulkEvalLnk), "Fail: Could not open Bulk Evaluation Link!");
		
		Assert.assertTrue(FiltersUtil.filterByTwoFields(IFiltersConst.projects_FOPPName_Lbl, fopp.getFoppFullName(), 
				IFiltersConst.projects_StepName_Lbl, stepName, "Starts With"), "Fail: Could not filter to get FOPP and Step Name!");
		
		Assert.assertTrue(ClicksUtil.clickLinks(stepName), "Fail: Could not click " + stepName);
		
		Assert.assertTrue(ProjectUtil.approveProject_Bulk(projType, foundProj, false, true), "Fail: Could not approve project!");
	
	}
	
	
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "useBulkEval_oneProj")
	public void useBulkEval_allProjs() throws Exception {
		
		Assert.assertTrue(ProjectUtil.openEvalSubmitAll_Bulk(projType), "Fail: Could not complete Bulk Evaluation!");
	
		Assert.assertTrue(ProjectUtil.checkIfProjsStillExist_BulkEval(fopp, stepName, projType), "Fail: Submitted projects still exist in Bulk Evaluation!");
	
	}


}
