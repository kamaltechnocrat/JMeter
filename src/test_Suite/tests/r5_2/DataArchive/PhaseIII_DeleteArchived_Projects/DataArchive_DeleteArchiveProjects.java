package test_Suite.tests.r5_2.DataArchive.PhaseIII_DeleteArchived_Projects;

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

/**
 * @author sFatima
 * @author s.grobbelaar
 *
 */

@GUITest
@Test(singleThreaded = true)
public class DataArchive_DeleteArchiveProjects {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	private RptFuncs reportFunc;

	private boolean isSQLDB;

	private EReportingFunctions funcName;

	private ResultSet rsResult;

	private String foundProj;

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
	public void createProjectAndSubmit() throws Exception {

		Assert.assertTrue(foProj.createFOProjectNewNew(), "Fail: Couldn't create FO Project");

		foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(foProj, foProj.getCurrentStepName()), "Fail: Could't open Submission-A");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(IRefTablesConst.enterAnyTextFiledTtl, IRefTablesConst.textFieldData), "Fail: Couldn't add text to text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "Fail: Couldn't click Submit button");

	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createProjectAndSubmit")
	public void closeProjects() throws Exception{
		
        GeneralUtil.switchToPO();
        
        foundProj = foProj.getProjFOFullName();
		          	
		Assert.assertTrue(GeneralUtil.runCloseAndAgeAll_Queries(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent()),
				"Fail: Couldn't run queries!");
		
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "closeProjects")
	public void prepProject_PhaseI() throws Exception{
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.closedProj_dd, IClicksConst.changeToReadyForArchive_Id), 
				"Fail: Could not filter and change state of Project to Ready for Archive!");
		
		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.readyArchive_dd, IClicksConst.processArchive_Id), 
				"Fail: Could not filter and change state of Project to Scheduled for Archive!");
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "prepProject_PhaseI")
	public void archiveProcess() throws Exception{
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), 
				"Fail: Couldn't click on the Quartz Control Panel link");

		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzArchJob_lbl), "Fail: Couldn't run quartz job!");	
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedArchive_dd,
				IGeneralConst.da_projectName_lbl, foProj.getProjFOFullName(), IGeneralConst.da_foppIdentifier_dropDown_value), 
				"Fail: Couldn't filter by Project name and status!");

		Assert.assertTrue(GeneralUtil.da_QuartzArchiveProcessDelay(0), "Fail: Did not complete archiving quartz job!");
		
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "archiveProcess")
	public void prepProject_PhaseII() throws Exception{
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(GeneralUtil.da_filterProjs_ChangeState(foProj, IGeneralConst.archived_dd, IClicksConst.processDelete_Id), 
				"Fail: Could not filter and change state of Project to Ready for Deletion!");
	}
	
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "prepProject_PhaseII")
	public void deletionProcess() throws Exception{

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), 
				"Fail: Couldn't click on the Quartz Control Panel link");
		
		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzDelArchJob_lbl), "Fail: Could not run 'Delete' quartz job!");
			
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedDeletion_dd,
				IGeneralConst.da_projectName_lbl, foProj.getProjFOFullName(), IGeneralConst.da_foppIdentifier_dropDown_value), 
				"Fail: Couldn't filter by Project name and status!");

		Assert.assertTrue(GeneralUtil.da_QuartzDeleteProcessDelay(foProj.getProjFOFullName()), "Fail: Did not complete deletion quartz job!");

	}
	
	
	//********************************PROGRAM OFFICE: Shak*******************************//
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "deletionProcess")
	public void verifyProjectNotListedInInBasket() throws Exception{
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(foProj.filterProjectInBasket(foundProj, "All Projects"), 
				"Fail: Project should not be listed in - In Basket link" );
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInInBasket")
	public void verifyProjectNotListedInInProjectList() throws Exception{
		
	Assert.assertTrue(foProj.filterProjectInProjectList(foundProj, "All Projects"), 
			"Fail: Project should not be listed in - Projects link" );
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInInProjectList")
	public void verifyProjectNotListedInApplicantsList() throws Exception{
			
		Assert.assertTrue(foProj.filterapplicantIn_ApplicantList(foProj, "Ouia 1"), 
				"Fail: Failed to find Applicant in the list");
		
		Assert.assertTrue(foProj.filterProjectIn_ApplicantSubmissionsList(foundProj, "All Projects", "All Versions"), 
				"Fail: Project should not be listed in Applicant Submissions list");
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInApplicantsList")
	public void verifyProjectNotListedInAwardsList() throws Exception{

		Assert.assertTrue(foProj.filterProjectInAwardLink(foundProj, "All Projects"), 
				"Fail: Project should not be listed in Award Link" );
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInAwardsList")
	public void verifyProjectNotListedInMyAssignedSubmissionsList() throws Exception{
		
		Assert.assertTrue(foProj.filterProjectInMyAssignedSubmissionsLink(foundProj, "All Projects"), 
				"Fail: Project should not be listed in My Assigned Submissions List");	
	}
	
	
	
	//*****************************PROGRAM OFFICE: Approver*****************************//
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInMyAssignedSubmissionsList")
	public void verifyProjectNotListedInMyProjectSubmissionsList() throws Exception{
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.LoginAny("Approver1");
		
		Assert.assertTrue(foProj.filterProjectInMyProjectSubmissionsList(foundProj, "All Projects"), 
				"Fail: Project should not be listed in My Project Submissions list");
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInMyProjectSubmissionsList")
	public void verifyProjectNotListedIn_EvaluateSubmissions_List() throws Exception{
		
		Assert.assertTrue(foProj.filterProjectInEvaluateSubmissionsList(foundProj, "All Projects"), 
				"Fail: Project should not be listed in Evaluate Projects List");	
	}
	
	
	
	//********************PROGRAM OFFICE: Project Officer*****************************//
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedIn_EvaluateSubmissions_List")
	public void verifyProjectNotListedInAssignEvaluatorsLink() throws Exception{
		
		GeneralUtil.switchToPOOnly();
		
		GeneralUtil.LoginAny("ProjectOfficer1");
		
		Assert.assertTrue(foProj.filterProjectInAssignEvaluatorLink(foundProj, "All Projects", "All"), 
				"Fail: Project should not be listed in Assign Evaluator List");
	}
	
	
	
	//*********************************FRONT OFFICE************************************//
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInAssignEvaluatorsLink")
	public void verifyProjectNotListedInFrontOfficeProjectsList() throws Exception{
		
		GeneralUtil.switchToFO();

		Assert.assertTrue(foProj.filterProjectIn_FrontOfficeProjectsList(foundProj,"All Projects", "A Gnrl PA FOPP"), 
				"Fail: Project should not be listed in Front Office Projects List");
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyProjectNotListedInFrontOfficeProjectsList")
	public void verifyProjectNotListedInFrontOfficeSubmissionsList() throws Exception{

		Assert.assertTrue(foProj.filterProjectIn_FrontOfficeSubmissionsList(foundProj, "All Projects", "All Versions"), 
				"Fail: Project should not be listed in Front Office Submissions List");
	}
	
}