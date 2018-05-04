
/* Test Case _01 For Story #1941  Reports Planner: report parameters details
 * Steps:
 * 1. Log in As a Super User
 * 2. Create User With read/Write Report Management (Groups UAP)
 * 3. Log in as the user
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values (Sample Report is for UserId with Numeric id or Username to be used as a parameter)
 * 5. for this story the fields can be any thing.
 * 6. Must click Save twice (bug Already logged Case 2660)
 * 7. click Back To Planner (UI Bug)
 * 8. Add Parameters ( All Five)
 * 
 * Result expected:
 * Able to add Paramaters the report
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
public class S1941_01NG {
	
	//#####***********************************************************************
	//###   To set up the Global Params Name Vars
	//#####***********************************************************************
	Class<? extends S1941_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
    Reports report;
    int UserIndex = 21;
    Users user;
	int reportIndex = 2;
	//---------------------------- End of Params ---------------------------------


	@BeforeClass(groups = { "Iter_14", "ReportsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
	        
			user = new Users(1, IUsersConst.users[UserIndex], "Users", "Program Office Users");
			
			IEUtil.openNewBrowser();
	        GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
	        //-----------------------------------
	        
	        initializeReport();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	} 
	
	@AfterClass(groups = { "Iter_14", "ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		user = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "Iter_14", "ReportsNG" })
	public void testAddReportAndParametersWithCreateUAPsNG() throws Exception {

		try{
			
			Assert.assertTrue(report.addOneNewReport(), "FAIL: User with Create UAPs could not add new Report");
			
			Assert.assertTrue(report.addReportParamsAndAccess(true, true), "FAIL: User with Create UAPs could not add Parameters");
	        
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 

	}
	
	@Test(groups = { "Iter_14", "ReportsNG" }, dependsOnMethods={"testAddReportAndParametersWithCreateUAPsNG"})
	public void testReportExistsInListWithCreateUAPsNG() throws Exception {
		
		Assert.assertTrue(ReportsUtil.isReportInReportsList(report), "FAIL: User with Create UAPS Created Report but not displying in List!");
		
	}

	private void initializeReport() throws Exception {
		
		report = new Reports();
		report.initReprots();
		
		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S1941_01NG");
		report.setAccessUser(new String[] {IUsersConst.powerUser[1][0]});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.withParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});
		
	}
}
