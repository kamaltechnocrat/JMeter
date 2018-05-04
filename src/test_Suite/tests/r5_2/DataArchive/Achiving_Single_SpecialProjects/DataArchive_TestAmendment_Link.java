package test_Suite.tests.r5_2.DataArchive.Achiving_Single_SpecialProjects;

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
 * @author s.grobbelaar
 * 
 * Creates new project, tests whether project (in each Archiving state) is visible in Amendment page
 * Project should not be visible in Amendment page once it is closed and aged
 *
 *
 */

@GUITest
@Test(singleThreaded = true)
public class DataArchive_TestAmendment_Link {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	String amender = "";

	String foundProj;

	private EReportingFunctions funcName;

	private RptFuncs reportFunc;

	private boolean isSQLDB = false; //Oracle is false

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


	//*****************************CREATE A NEW PROJECT**********************************//

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
	public void approveSubmission_submitAward() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false, false), "Fail: Couldn't submit Approval step ");

		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(
					foProj.submitAward("Standard", 1, true,IGeneralConst.postAwardFormName),
					"FAIL: Could not Submit Standard Award Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods={"approveSubmission_submitAward"})
	public void initialClaimSubmit() throws Exception{

		GeneralUtil.switchToFOOnly();

		GeneralUtil.loginAnyFO("front");

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, "A Gnrl PA Initial Claim"), "Fail: Couldn't open FO Submission - A Gnrl PA Initial Claim");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn), "Fail: Couldn't click Next button");	

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),  "Fail: Couldn't click Submit button");	

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialClaimSubmit")
	public void assignEvaluator_submitReview() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		Assert.assertTrue(foProj.assignAllAvailableEvaluator_Claim1(IGeneralConst.reviewQuoCritManu[0][0] + IGeneralConst.newPASuffix), "Fail: Failed to assign Evaluator");

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Reviewer1");

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, "Review", "Ready", "", false, "1", ""), "Fail: Failed to open Review form");

		Assert.assertTrue(ProjectUtil.fillReviewForm(IGeneralConst.requirementFulfilled_Ttl,IGeneralConst.selectYes, IGeneralConst.commentId,IGeneralConst.comment,IGeneralConst.score_Ttl,IGeneralConst.score), "Fail: Failed to fill review form");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "assignEvaluator_submitReview")
	public void pa_Approval() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false, false), "Fail: Failed to submit Approval step");
	
		ProjectUtil.requestAmendmentFromPO(foProj);
	}

	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "pa_Approval")
	public void approveSubmission_Amended() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny("Approver1");

		Assert.assertTrue(foProj.approveAmendedSubmission(IGeneralConst.approvQuoCritAuto[0][0], true, "In Progress"), 
				"Fail: Failed to submit amended Approval Submission");
	}
	

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "approveSubmission_Amended")
	public void pa_ClosingStep() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList_Claim1(foProj, "Closing Step", "Open Projects"), 
				"Fail: Failed to open Closing Step in My Assigned Submissions List");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl,IRefTablesConst.textFieldData), 
				"Fail: Failed to append text to text field" );

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");
		
		foundProj = foProj.getProjFOFullName();
	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "pa_ClosingStep")
	public void readyForArch_NotInList() throws Exception{

		log.warn(foundProj);
		
		GeneralUtil.switchToPO();

		Assert.assertTrue(GeneralUtil.runCloseAndAgeAll_Queries(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent()),
				"Fail: Couldn't run queries!");
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()), "Fail: Couldn't navigate to Manage Archive Page!");
		
		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foundProj, IGeneralConst.da_closedProjStatus_value, IClicksConst.changeToReadyForArchive_Id),
				"Fail: Couldn't convert project(s) from 'Closed' to 'Ready for Archive'");

		Assert.assertTrue(GeneralUtil.isProjectListedinAmendmentsList(foundProj), "Fail: Error with project in Amendments list!");

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "readyForArch_NotInList")
	public void schedForArch_NotInList() throws Exception{

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foundProj, IGeneralConst.readyArchive_dd, IClicksConst.processArchive_Id), 
				"Fail: Couldn't convert project(s) from 'Ready for Archive' to 'Scheduled for Archive");

		Assert.assertTrue(GeneralUtil.isProjectListedinAmendmentsList(foundProj), "Fail: Error with project in Amendments list!");

	}



	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "schedForArch_NotInList")
	public void archiving_Process() throws Exception{

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), 
				"Fail: Couldn't click on the Quartz Control Panel link");

		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzArchJob_lbl), "Fail: Couldn't run quartz job!");		
	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "archiving_Process")
	public void archived_NotInList() throws Exception {

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedArchive_dd,
				IGeneralConst.da_projectName_lbl, foundProj, IGeneralConst.da_foppIdentifier_dropDown_value), 
				"Fail: Couldn't filter by Project name and status!");

		Assert.assertTrue(GeneralUtil.da_QuartzArchiveProcessDelay(0), "Fail: Did not complete archiving quartz job!");

		Assert.assertTrue(GeneralUtil.isProjectListedinAmendmentsList(foundProj), "Fail: Error with project in Amendments list!");

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "archived_NotInList")
	public void schedForDeletion_NotInList() throws Exception {

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foundProj, IGeneralConst.archived_dd, IClicksConst.processDelete_Id), 
				"Fail: Couldn't convert project(s) from 'Archived' to 'Scheduled for Deletion'");

		Assert.assertTrue(GeneralUtil.isProjectListedinAmendmentsList(foundProj), "Fail: Error with project in Amendments list!");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "schedForDeletion_NotInList")
	public void deletion_Process() throws Exception {

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), 
				"Fail: Couldn't click on the Quartz Control Panel link");

		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzDelArchJob_lbl), "Fail: Couldn't run quartz job!");	

	}


	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "deletion_Process")
	public void deleted_NotInList() throws Exception {

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedDeletion_dd,
				IGeneralConst.da_projectName_lbl, foundProj, IGeneralConst.da_foppIdentifier_dropDown_value), 
				"Fail: Couldn't filter by Project name and status!");

		Assert.assertTrue(GeneralUtil.da_QuartzDeleteProcessDelay(foundProj), "Fail: Did not complete deletion quartz job!");

		Assert.assertTrue(GeneralUtil.isProjectListedinAmendmentsList(foundProj), "Fail: Error with project in Amendments list!");
	}
	
}