package test_Suite.tests.r5_2.DataArchive.Group_UAPs_Set_Verify;

import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst.EReportingFunctions;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author sFatima
 * 
 * Verifies UAPs for G11 ReportOfficer group - can read but not modify
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Verify_UAP_Implementation {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	private RptFuncs reportFunc;

	private boolean isSQLDB = false;

	private EReportingFunctions funcName;

	private ResultSet rsResult;

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
	public void connectToDB() throws Exception{

		fopp = new FundingOpportunity("A", "-Gnrl-PA-","");

		foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5));
		
		isSQLDB = GeneralUtil.isSQLDB();
		
		reportFunc = GeneralUtil.connectToDB(isSQLDB, funcName, reportFunc);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods = "connectToDB")
	public void createNewProjectAndSubmit() throws Exception {

		Assert.assertTrue(foProj.createFOProjectNewNew(), "Fail: Couldn't create FO Project");

		foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()), "Fail: Could't open Submission-A");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData), "Fail: Couldn't add text to text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createNewProjectAndSubmit")
	public void approveSubmission() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false, false), "Fail: Couldn't submit Approval step ");


	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "approveSubmission", enabled = true)
	public void submitAward() throws Exception {
		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(
					foProj.submitAward("Standard", 1, true,IGeneralConst.postAwardFormName),
					"FAIL: Could not Submit Standard Award Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"submitAward"})
	public void initialClaimSubmit() throws Exception{

		GeneralUtil.switchToFOOnly();

		GeneralUtil.loginAnyFO("front");

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A Gnrl PA Initial Claim"), "Fail: Couldn't open FO Submission - A Gnrl PA Initial Claim");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn), "Fail: Couldn't click Next button");	

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),  "Fail: Couldn't click Submit button");	

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialClaimSubmit")
	public void assignEvaluator() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		Assert.assertTrue(foProj.assignAllAvailableEvaluator_Claim1(IGeneralConst.reviewQuoCritManu[0][0] + IGeneralConst.newPASuffix), "Fail: Failed to assign Evaluator");

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"assignEvaluator"})
	public void submitReview() throws Exception{

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Reviewer1");

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "Ready", "", false, "1", ""), "Fail: Failed to open Review form");

		Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score), "Fail: Failed to fill review form");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");


	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview")
	public void pa_Approval() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false, false), "Fail: Failed to submit Approval step");


	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "pa_Approval")
	public void pa_ClosingStep() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList_Claim1(foProj, "Closing Step", "Open Projects"), "Fail: Failed to open Closing Step");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl("Enter Any Text:", "Submit"), "Fail: Couldn't add text to text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");
	}

	/**
	 * This method is used to close the project from the project list.
	 * @throws Exception
	 */

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "pa_ClosingStep")
	public void closeProjects() throws Exception{
		
		Assert.assertTrue(GeneralUtil.runCloseAndAgeAll_Queries(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent()),
				"Fail: Couldn't run queries!");

		Assert.assertTrue(GeneralUtil.test_ReadOnlyProperty_ReadyForArchive_Projects(fopp.getFoppFullIdent(), foProj, "Closed Projects"));

	}

	/**
	 * This method filters for the closed projects and then mark them as Ready for Archive.
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "closeProjects")
	public void closed_to_readyForArchive() throws Exception{

		GeneralUtil.switchToPO();

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()));

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.closedProj_dd, IClicksConst.changeToReadyForArchive_Id),
				"Fail: Could not change the state of the project!");

		Assert.assertTrue(GeneralUtil.test_ReadOnlyProperty_ReadyForArchive_Projects(fopp.getFoppFullIdent(), foProj, "Ready for Archive"));
	}

	/**
	 * This method filters for the Ready For Archive projects and then mark them as Scheduled for Archive.
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "closed_to_readyForArchive")
	public void ready_scheduledForArchive() throws Exception{

		GeneralUtil.switchToPO();

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()));

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.readyArchive_dd, IClicksConst.processArchive_Id),
				"Fail: Could not change the state of the project!");

		Assert.assertTrue(GeneralUtil.test_ReadOnlyProperty_ReadyForArchive_Projects(fopp.getFoppFullIdent(), foProj, "Scheduled for Archive"));

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "ready_scheduledForArchive")
	public void sched_toArchived() throws Exception{
		
		GeneralUtil.switchToPO();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), "Fail: Couldn't click on the Quartz Control Panel link");

		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzArchJob_lbl), "Fail: Couldn't run quartz job!");	

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedArchive_dd), "Fail: Couldn't filter by 'Scheduled for Archive'!");

		Assert.assertTrue(GeneralUtil.da_QuartzArchiveProcessDelay(0), "Fail: Did not complete archiving quartz job!");

		Assert.assertTrue(GeneralUtil.test_ReadOnlyProperty_ReadyForArchive_Projects(fopp.getFoppFullIdent(), foProj, "Archived"));

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "sched_toArchived")
	public void archived_to_SchedForDeletion() throws Exception{

		GeneralUtil.switchToPO();

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()));

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.archived_dd, IClicksConst.processDelete_Id),
				"Fail: Could not change the state of the project!");
		
		Assert.assertTrue(GeneralUtil.test_ReadOnlyProperty_ReadyForArchive_Projects(fopp.getFoppFullIdent(), foProj, "Scheduled for Deletion"));
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "archived_to_SchedForDeletion")
	public void deleteProject() throws Exception{

		GeneralUtil.switchToPO();

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), "Fail: Couldn't click on the Quartz Control Panel link");

		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzDelArchJob_lbl), "Fail: Couldn't run quartz job!");	
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedDeletion_dd,
				IGeneralConst.da_projectName_lbl, foProj.getProjFOFullName(), IGeneralConst.da_foppIdentifier_dropDown_value), 
				"Fail: Couldn't filter by Project name and status!");

		Assert.assertTrue(GeneralUtil.da_QuartzDeleteProcessDelay(foProj.getProjFOFullName()), "Fail: Did not complete deletion quartz job!");
		
	}


}