/* Test Case _04 For Story #2071  Reports Planner: report parameters details (Programs related)
 * Steps:
 * 1. Log in As a Super User
 * 2. Add new Reports (plus sign)
 * 3. in the Report detail enter values 
 * 4. for this Case Testing (Programs)
 * 5. click Save.
 * 6. Add Parameters ( ProgramId)
 * 7. Open My Report, select the Report and Enter Value
 * 8. Click Launch Report
 * 
 * Result expected:
 * Able to Launch the Report
 * 
 */

package test_Suite.tests.stories.release_1_5.manageReports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.ReportsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
@Test(singleThreaded = true)
public class S2071_06NG {
	
	//#####***********************************************************************
	//###   To set up the Global Params Name Vars
	//#####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	boolean newProgram = false;
	boolean programForm = false;
	String preFix = "-S2071_xx-";	
	char portaltype = 'P';	
	Program prog;
	Reports report;
	FOProject foProj;
	int reportIndex = 1; //Program_Registered_Applicants.rpt
	
	//---------------------------- End of Params ---------------------------------

	@BeforeClass(groups = { "ReportsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			prog = new Program(preFix, portaltype, programForm, newProgram, false);
			
			prog.initializeProgram("A");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		prog = null; 
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "ReportsNG" })
	public void submitFOProjectsAndAssignOfficersNG() throws Exception {
       //Switch to Front Office
        
        foProj = new FOProject(prog);
        foProj.createFOProject();            
        foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true);        
        
        GeneralUtil.switchToPO();
		
		foProj.assignOfficers(new String[][] { {
				IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });
	}

	@Test(groups = { "ReportsNG" },dependsOnMethods="submitFOProjectsAndAssignOfficersNG")
	public void initializeAndCreateReportNG() throws Exception {

		report = new Reports();
		report.initReprots();

		ClicksUtil.clickLinks(IClicksConst.openReportsList);

		report.setReportLetter(foProj.getProjFOLetter());
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId	+ "-S1971_06NG");
		report.setAccessUser(new String[] { IUsersConst.powerUser[1][0]});

		report.setReportName(report.getReportIdent());
		report.setReportTitle(report.getReportLetter() + "-"
				+ IReportsConst.withParamsReportsTitles[reportIndex]);

		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});
		
		report.setParamsValues(new String[] {prog.getProgFullName(), foProj.getProjFOFullName()});

		Assert.assertTrue(report.addOneNewReport(),
				"FAIL: User with Create UAPs could not add new Report");
		
		Assert.assertTrue(report.addReportParamsAndAccess(true, true),
		"FAIL: User with Create UAPs could not add Parameters");

	}

	@Test(groups = { "ReportsNG" },dependsOnMethods="initializeAndCreateReportNG")
	public void launchReportNG() throws Exception {
		try {

    		Assert.assertTrue(report.lanuchReport());
    		
    		ReportsUtil.closeChildIE();
    		
    		
		} catch (Exception e) {
			log.error("Unexpected Exception", e);
	
			throw new RuntimeException("Unexpected Exception", e);	
		} 
	}
}
