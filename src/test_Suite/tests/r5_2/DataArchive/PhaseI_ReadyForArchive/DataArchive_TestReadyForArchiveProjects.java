package test_Suite.tests.r5_2.DataArchive.PhaseI_ReadyForArchive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author sFatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class DataArchive_TestReadyForArchiveProjects {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	RptFuncs reportFunc;
	
	IRptFuncConst.EReportingFunctions funcName;
	
	private String projType = "-arcTest";
	
	boolean isSQLDB = false;

	private String foundProj;
	
	Project foProj = new Project();


	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();
		
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", "-Gnrl-PA-","");


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
	
	
	@Test(groups = { "WorkflowNG" }, alwaysRun=true)
	public void prepProjects() throws Exception {

		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),
				"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.readyArchive_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by Project name and status!");
		
		foundProj = GeneralUtil.getFirstProjName(ITablesConst.da_ProjList_DivId);
	}
	
	
	//********************************PROGRAM OFFICE: Shak*******************************//
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "prepProjects")
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