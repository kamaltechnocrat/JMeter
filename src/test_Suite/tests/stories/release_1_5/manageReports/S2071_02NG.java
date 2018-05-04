/* Test Case _01 For Story #2071  Reports Planner: report parameters details (Programs related)
 * Steps:
 * 1. Log in As a Super User
 * 2. Add new Reports (plus sign)
 * 3. in the Report detail enter values 
 * 4. for this story the fields can be any thing.
 * 5. click Save.
 * 6. Add Parameters ( ProgramName, StartDate, DeletedFlag, Program_Manager_ID)
 * 7. Open My Report, select the Report and Enter Values
 * 8. Click Launch Report
 * 
 * 
 * Result expected:
 * Able to Launch the Report
 * 
 */

package test_Suite.tests.stories.release_1_5.manageReports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.ReportsUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;


@GUITest
@Test(singleThreaded = true)
public class S2071_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	int rowIndx = -1;
	boolean newProgram = false;
	boolean programForm = false;
	String preFix = "-S2071_xx-";
	char portaltype = 'P';
	Program prog;
	Reports report;
	int reportIndex = 0;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "ReportsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
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
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}



	@Test(groups = { "ReportsNG" })
	public void initializeAndCreateReportNG() throws Exception {

		report = new Reports();
		report.initReprots();

		ClicksUtil.clickLinks(IClicksConst.openReportsList);

		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId	+ "-S1971_02NG");
		report.setAccessUser(new String[] { IUsersConst.powerUser[1][0]});

		report.setReportName(report.getReportIdent());
		report.setReportTitle(report.getReportLetter() + "-"
				+ IReportsConst.withParamsReportsTitles[reportIndex]);

		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});
		
		report.setParamsValues(new String[] {prog.getProgFullName()});

		Assert.assertTrue(report.addOneNewReport(),
				"FAIL: User with Create UAPs could not add new Report");
		
		Assert.assertTrue(report.addReportParamsAndAccess(true, true),
		"FAIL: User with Create UAPs could not add Parameters");

	}

	@Test(groups = { "ReportsNG" },dependsOnMethods="initializeAndCreateReport")
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
