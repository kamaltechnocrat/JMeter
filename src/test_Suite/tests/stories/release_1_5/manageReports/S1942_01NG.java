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
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.ReportsUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/* Test Case _01 For Story #1942  Reports Planner: Configure users List
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
public class S1942_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1942_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Reports report;
	int UserIndex = 24;
	Users user;
	int reportIndex = 7;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = {"ReportsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			
			user = new Users(1, IUsersConst.users[UserIndex], "Users", "Program Office Users");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		user = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ReportsNG" })
	public void initializeAndCreateReportNG() throws Exception {

		report = new Reports();
		report.initReprots();
		
		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId
				+ "-S1942_01NG");
		report.setAccessUser(new String[] {IUsersConst.users[UserIndex][1][0] });

		report.setReportName(report.getReportIdent());
		report.setReportTitle(report.getReportLetter() + "-"
				+ IReportsConst.withParamsReportsTitles[reportIndex]);

		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);

		Assert.assertTrue(report.addOneNewReport(),
				"FAIL: User with Create UAPs could not add new Report");
		ReportsUtil.filter3rdPartyReports(report.getReportIdent());
		ClicksUtil.clickLinks(report.getReportIdent());

	}	
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods = "initializeAndCreateReportNG")
	public void testGroupWithMyReportsUAPAvailableInReportAccessNG() throws Exception {		

		Assert.assertTrue(report.doesGroupsExistsInReportAccessM2M("main:reportAccess:access:availableAccess", report.getAccessUser()[0]));
				
		Assert.assertTrue(report.addUsersAndGroupToReportAccess(), "FAIL: Group with My reports UAP can not be found in Available M2M!");
	}
	
	@Test(groups = {"ReportsNG" }, dependsOnMethods = "testGroupWithMyReportsUAPAvailableInReportAccessNG")
	public void testGroupWithMyReportsUAPSelectedInReportAccessNG() throws Exception {		

		Assert.assertTrue(report.doesGroupsExistsInReportAccessM2M("main:reportAccess:access:selectedAccess", report.getAccessUser()[0]));		
		
	}
}
