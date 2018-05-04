package test_Suite.tests.r5_2.DataArchive;

import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author SFatima
 * @author s.grobbelaar
 * 
 * 
 * Ages and closes all projects from one funding opportunity
 * Selects 5 projects moves them around, 1 to each different state: Closed, Ready for Archive, Scheduled for Archive, Archived, Scheduled for Deletion
 * Puts max 3.5 min timer on Quartz processing 
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class MustRunFirst_DataArchive {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isSQLDB = false;

	IRptFuncConst.EReportingFunctions funcName;

	RptFuncs reportFunc;

	FundingOpportunity fopp;

	FundingOpportunity fopp1;

	private ResultSet rsResult;

	private int numProjs = 5;
	
	private String projType = "-arcTest";

	private int numArchs;
	
	// #####*******************************************

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();	
			// -----------------------------------
				
			isSQLDB = GeneralUtil.isSQLDB();

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

	@Test(groups = { "WorkflowNG" })
	public void initializeFOPP() throws Exception {
		
		fopp = new FundingOpportunity("A", "-Gnrl-PA-","");
		
		fopp.openFundingOppPlanner();
		
		fopp.selectMultipleFOPPAdmins("G11 Report Officers");
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
	}
	
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods = "initializeFOPP")
	public void createProjects() throws Exception {
	
		Assert.assertTrue(FrontOfficeUtil.createManyProjs_PreAwardAndSubmit(numProjs, fopp, projType), "Fail: Projects could not be created!");
			
		GeneralUtil.switchToPO();
	}

	/**
	 * Closes and ages projects with queries
	 * Verifies filter works in "Manage Archive"
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods = "createProjects")
	public void close_Age_Projects() throws Exception {

		reportFunc = GeneralUtil.connectToDB(isSQLDB, funcName, reportFunc);
			
		Assert.assertTrue(GeneralUtil.runCloseAndAgeAll_Queries(isSQLDB, rsResult, reportFunc, 7, fopp.getFoppFullIdent()),
				"Fail: Couldn't run queries!");

		Assert.assertTrue(GeneralUtil.navigate_ManageArchive_Filter(fopp.getFoppFullIdent()), "Fail: Could not open FOPP in Manage Archive page!");

		int col = TablesUtil.findColumnIndex(ITablesConst.dataArchive_ManageArchiveFOPPListHead_Id, "Total Archived");
		
		numArchs = TablesUtil.manageArchive_VerifyNumberinTable(ITablesConst.dataArchive_ManageArchiveFOPPList_Id, col);

		Assert.assertTrue(numArchs != -1, "Fail: Number of projects listed in table is not right!");
	}

	/**
	 * Moves numProjs from Closed to Ready for Archiving
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods = "close_Age_Projects")
	public void closed_To_ReadyForArchive() throws Exception{
		
		int col = TablesUtil.findColumnIndex(ITablesConst.dataArchive_ManageArchiveFOPPListHead_Id, "Total Open Projects");
		
		Assert.assertEquals(TablesUtil.manageArchive_VerifyNumberinTable(ITablesConst.dataArchive_ManageArchiveFOPPList_Id, col), 0, "Fail: Not all projects were closed!");
	
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.closedProj_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by 'Closed Projects'!");
	
		numProjs--;
		
		Assert.assertTrue(GeneralUtil.da_selectAndProcessProjs(numProjs, IClicksConst.changeToReadyForArchive_Id), "Fail: Couldn't convert project(s) from 'Closed' to 'Ready for Archive'");

	}
	
	/**
	 * Moves numProjs of projects from Ready for Archiving to Scheduled for Archiving
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "closed_To_ReadyForArchive")
	public void ready_To_ScheduledForArchive() throws Exception{
		
		numProjs-=2;
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.readyArchive_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by 'Ready for Archive'!");
		
		Assert.assertTrue(GeneralUtil.da_selectAndProcessProjs(numProjs, IClicksConst.processArchive_Id), "Fail: Couldn't convert project(s) from 'Ready for Archive' to 'Scheduled for Archive'");
	}
	
	/**
	 * Moves numProjs of projects from Scheduled for Archiving to Archived
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "ready_To_ScheduledForArchive")
	public void scheduled_To_Archived() throws Exception {
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.quartz_Control_Panel), "Fail: Couldn't click on the Quartz Control Panel link");
		
		Assert.assertTrue(GeneralUtil.da_runQuartzJob(ITablesConst.quartzArchJob_lbl), "Fail: Couldn't run quartz job!");	
		
		Assert.assertTrue(GeneralUtil.clickProj_ManageArchive_Filter(fopp.getFoppFullIdent()),"Fail: Could not open FOPP in Manage Archive page!");
		
	}
	
	/**
	 * Quartz timer
	 * Moves numProjs of projects from Archived to Scheduled for Deletion
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "scheduled_To_Archived")
	public void archived_To_SchedForDeletion() throws Exception {
		
		numProjs--;
				
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.schedArchive_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by 'Scheduled for Archive'!");

		Assert.assertTrue(GeneralUtil.da_QuartzArchiveProcessDelay(0), "Fail: Did not complete archiving quartz job!");
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.archived_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by 'Archived'!");
		
		Assert.assertTrue(GeneralUtil.da_selectAndProcessProjs(numProjs, IClicksConst.processDelete_Id), "Fail: Couldn't convert project(s) from ''Archived' to 'Scheduled for Deletion'!");

			
	}

	/**
	 * Moves numProjs of projects from Ready for Archiving to Scheduled for Archiving
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "archived_To_SchedForDeletion")
	public void ready_To_SchedForArch_Again() throws Exception {

		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, IGeneralConst.readyArchive_dd,
				IGeneralConst.da_projectName_lbl, projType, "Contains"), "Fail: Couldn't filter by 'Ready for Archive'!");
		
		Assert.assertTrue(GeneralUtil.da_selectAndProcessProjs(numProjs, IClicksConst.processArchive_Id), "Fail: Couldn't convert project(s) from 'Ready for Archive' to 'Scheduled for Archive'");
	}






}


