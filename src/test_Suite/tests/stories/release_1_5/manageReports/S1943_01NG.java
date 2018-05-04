/**
 * 
 */
package test_Suite.tests.stories.release_1_5.manageReports;


import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.ReportsUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/* Test Case _01 For Story #1943  Reports Planner: Main Planner(Report Details)
 * Steps:
 * 1. Log in As a Super User
 * 2. Create User Group With Read Only My Reports (Groups UAP)
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values 
 * 5. for this story the fields can be any thing.
 * 6. In the Report Access
 * 7. Test Verify the Group is Available in M2M to be selected
 * Result expected:
 * Able to Find the Group
 * 
 * 8. Select the Group, save, navigate away then come back to planner
 * 9. Test, Group is in Selected Groups M2M
 * Result expected
 * Group is in Selected M2M
 */

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1943_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1943_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Reports report;
	int UserIndex = 27;
	Users user;
	int reportIndex = 5;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = {"ReportsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			user = new Users(1, IUsersConst.users[UserIndex], "Users", "Program Office Users");
			
			report = new Reports();
			report.initReprots();

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(user.getUsrName() + "1");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		user = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"ReportsNG" }, dataProvider = "Report-Data")
	public void testMandatoryFieldsInReportPlanner(String reportIdent, String reportUrl, String reportName, String orgName, String reportTitle) throws Exception {		
	
		report.setReportIdent(reportIdent);
		report.setAccessUser(new String[] {IUsersConst.users[UserIndex][1][0] });

		report.setReportViewerFullPath(reportUrl);
		report.setReportName(reportName);
		report.setReportTitle(reportTitle);

		report.setReportOrgName(orgName);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		Assert.assertFalse(report.fillReportDetailsFields());
	}
	
	@Test(groups = {"ReportsNG" })
	public void testUserWithCreateAbleToAddNewReport() throws Exception {
		
		initializeReport();
		
		Assert.assertTrue(report.addOneNewReport(), "FAIL: User with Create UAPs could not add new Report");
		
		Assert.assertTrue(ReportsUtil.isReportInReportsList(report), "FAIL: Could not find Report in List");
	}
	
	@Test(groups = {"ReportsNG" })
	public void testUserWithDeleteAbleToDeleteReport() throws Exception {
		
		initializeReport();
		
		Assert.assertTrue(report.addOneNewReport(), "FAIL: User with Create UAPs could not add new Report");
		
		Assert.assertTrue(ReportsUtil.deleteReport(report.getReportIdent()), "FAIL: User with Delete UAP unable to delete Report");
	}

	private void initializeReport() throws Exception {

		report = new Reports();
		report.initReprots();
		
		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S1943_01NG");
		report.setAccessUser(new String[] {IUsersConst.powerUser[1][0]});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.withParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);		

	}
	
	
	/**
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "Report-Data")
	public Object[][] generateReportData(Method method) throws Exception {

		Object[][] result;
		result = new Object[][] {
				{ "", "Report URL", "Report Name", "G3","Report Title" },
				{ "Some Report Ident", "", "Report Name", "G3","Report Title"},
				{"Some Report Ident", "Report URL", "", "G3","Report Title"},
				{ "Some Report Ident", "Report URL", "Report Name", "","Report Title"},
				{ "Some Report Ident", "Report URL", "Report Name", "G3","" }
				};

		return result;
	}
}
