package test_Suite.tests.r4_0.onDemandSubmission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author s.grobbelaar
 * 
 * Tests On Demand functionality and if Required is NOT set, then the project should be "In Progress" state after On Demand is done
 * Token should NOT be visible with the On Demand Payment in projects My Assigned Submissions List 
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class ODSS_PANotReqSubmission {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	private String postIdfr = "-NotReqd";

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();

			GeneralUtil.navigateToFO();

			GeneralUtil.loginAnyFO("front");

			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		foProj = null;
		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize_NotRequiredODSS() throws Exception{

		fopp = new FundingOpportunity("A", "-ODSS-PA-","");
		
		foProj = new FOProject(fopp, "", true, 1, postIdfr );
		
		Assert.assertTrue(ProjectUtil.createOneProjectAndSubmit(foProj, fopp, ""), "Fail: Couldn't create project and submit!");
		
		GeneralUtil.switchToPO();
		
		//set required to False
		Assert.assertTrue(
				foProj.submitAward_ODSS("ODSS-Standard-Award", 1, true,IGeneralConst.postAwardFormName, false, false, true),
				"FAIL: Could not Submit ODSS Standard Award Form");
	}
	
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "initialize_NotRequiredODSS")
	public void submitInitialClaim_PA() throws Exception {

			GeneralUtil.switchToFOOnly();

			GeneralUtil.loginAnyFO("front");

			Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A ODSS PA Initial Claim"), "Fail: Couldn't open FO Submission - A Gnrl PA Initial Claim");

			Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData), "Fail: Couldn't add text to text field");
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");			

			//Assert.assertTrue(ProjectUtil.checkClosingStepVisibility(fopp, foProj), "Fail: Closing Step is visible!");

	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "submitInitialClaim_PA")
	public void approveClaim_PA() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.LoginAny("Approver1");
		
		Assert.assertTrue(foProj.approveSubmission("", true, IFiltersConst.submissionsStatus_All_StatusSubView, 
				false, false));
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "approveClaim_PA")
	public void onDemandForm_PA() throws Exception {
		
		GeneralUtil.switchToFOOnly();

		GeneralUtil.loginAnyFO("front");
		
		Assert.assertTrue(foProj.foProj_fillAllOnDemands(),"Fail: Could not fill in On Demand form(s)!");
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "onDemandForm_PA")
	public void approveODClaim_PA() throws Exception {  
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.LoginAny("Approver1");	
		
		//Payment should be in "In Progress" state
		Assert.assertTrue(ProjectUtil.approveOnDemandClaim(foProj, IFiltersConst.submissionsStatus_Ready_StatusSubView));
			
	}

	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "approveODClaim_PA")
	public void checkToken() throws Exception {
		
		GeneralUtil.switchToPO();
		
		//Payment NOT visible since ODS is not required
		Assert.assertTrue(ProjectUtil.verifyVisibilityProjList_ODSS(foProj), "Fail: Payment should be visible in Projects List!");
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods = "checkToken")
	public void closeClaim_PA() throws Exception {
		
		Assert.assertTrue(ProjectUtil.openInMyAssignedSubmissionList(foProj, "Claim", "Open Projects"));
		
		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData), "Fail: Couldn't add text to text field");
		
		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");			
	}
	
	
}