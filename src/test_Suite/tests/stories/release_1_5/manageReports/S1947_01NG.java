/**
 * 
 */
package test_Suite.tests.stories.release_1_5.manageReports;

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

/* Test Case _01 For Story #1947  Reports Planner: UAPs
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

 /**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1947_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Reports report;
	int UserIndex = 27;
	Users user;
	int reportIndex = 4;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = {"ReportsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			user = new Users(1, IUsersConst.users[UserIndex], "Users", "Program Office Users");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(user.getUsrName() + "1");

			initializeReport();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "Iter_15", "ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		user = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"ReportsNG" })
	public void testAddingNewReportWithCreateReprotDetailsUAP()
			throws Exception {

		Assert.assertTrue(report.fillReportDetailsFields(),
				"FAIL: User with Create UAP unable to add new Report!");

	}
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods = "testAddingNewReportWithCreateReprotDetailsUAP")
	public void testViewReportInListWithReprotDetailsUAP() throws Exception {
	
			Assert.assertTrue(ReportsUtil.isReportInReportsList(report),
							"FAIL: User with Report Details UAP unable to view Report In List!");
	
	}
	
	@Test(groups = {"ReportsNG" }, dataProvider = "Report-Data", dependsOnMethods = "testAddingNewReportWithCreateReprotDetailsUAP")
	public void testEditingReportDetails(String reportStatus, String reportUrl,
			String reportName, String orgName, String orgAccess,
			String reportTitle) throws Exception {
		
		report.setReportStatus(reportStatus);
		report.setReportViewerFullPath(reportUrl);
		report.setReportName(reportName);
		report.setReportOrgName(orgName);
		report.setReportOrgAccess(orgAccess);
		report.setReportTitle(reportTitle);

		Assert.assertTrue(report.editReportDetails(),
				"FAIL: Error editing Report Details");
	}
			
	

	@Test(groups = { "Iter_15", "ReportsNG" }, dependsOnMethods = "testAddingNewReportWithCreateReprotDetailsUAP")
	public void testAddParameterWithMangeReportUAPs() throws Exception {
		
	}	

	/**
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "Report-Data")
	public Object[][] generateReportDetails() throws Exception {

		Object[][] result;
		result = new Object[][] { { "Inactive", "", "", "", "", "" },
				{ "", "Report Url", "", "", "", "" },
				{ "", "", "Report Name", "", "", "" },
				{ "", "", "", "G3", "", "" },
				{ "", "", "", "", "Restricted", "" },
				{ "", "", "", "", "", "Report Title" } };

		return result;
	}

	private void initializeReport() throws Exception {

		report = new Reports();
		report.initReprots();
		
		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId
				+ "-S1947_01NG");
		report.setAccessUser(new String[] { IUsersConst.powerUser[1][0] });

		report.setReportName(report.getReportIdent());
		report.setReportTitle(report.getReportLetter() + "-"
				+ IReportsConst.withParamsReportsTitles[reportIndex]);

		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});

	}

}
