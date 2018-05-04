
/* Test Case _01 For Story #1948  View Reports: Launch Report Extension
 * Steps:
 * 1. Log in As a Super User
 * 2. Create User With read/Write View Report (Groups UAP)
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values (Sample Report is for UserId with Numeric id or Username to be used as a parameter)
 * 5. Assign the User created as in the Report Access
 * 6. Log in as the user
 * 7. Open My Reports List
 * 8. Open Reports Parameters
 * 9. enter UserId (Text or Numeric)
 * 10. Launch the Report
 * 
 * Result expected:
 * Able to Launch the report
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
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class S1948_01NG {
	
	//#####***********************************************************************
	//###   To set up the Global Params Name Vars
	//#####***********************************************************************
	Class<? extends S1948_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
    Reports report;
    int UserIndex = 24;
    Users user;
	int reportIndex = 1;
	//---------------------------- End of Params ---------------------------------
	
	@BeforeClass(groups = {"ReportsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
	        GeneralUtil.navigateToPO();
	        GeneralUtil.logInSuper();
	        //------------------------------------

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
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S1948_01NG");
		report.setAccessUser(new String[] {user.getGroup()});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.noParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		Assert.assertTrue(report.addOneNewReport(), "FAIL: Super User could not add new Report");		
		
		Assert.assertTrue(report.addReportParamsAndAccess(true, false), "FAIL: Could not add Parameters and/or groups");		
	}

	@Test(groups = { "ReportsNG" }, dependsOnMethods="initializeAndCreateReportNG")
	public void testViewReportInMyReportsWith_R_UAPsNG() throws Exception {

		try{

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
			Assert.assertTrue(ReportsUtil.isReportInMyReportsList(report), "Fail: Report should be listed");
	        
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods={"testViewReportInMyReportsWith_R_UAPsNG"})
	public void testLanchReportWith_R_UAPsNG() throws Exception {
		
		Assert.assertTrue(report.lanchReportWithoutParameters(), "FAIL: User with Manage Report Read could not launch report!");		
	}
	
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods={"testLanchReportWith_R_UAPsNG"})
	public void closeReportWindowNG() throws Exception {
		
		ReportsUtil.closeChildIE();
	}
}
