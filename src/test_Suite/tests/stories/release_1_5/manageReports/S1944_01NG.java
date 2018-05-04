

/* Test Case _01 For Story #1944  Reports Admin: add new report (report details)
 * Steps:
 * 1. Create User With Report Management (Groups UAP)
 * 2. Log in as the User
 * 3. Open Reports List
 * 4. Add new Reports (plus sign)
 * 5. in the Report detail enter values (Sample Report is for UserId with Numeric id or Username to be used as a parameter)
 * 6. for this story the fields can be any thing.
 * 7. Must click Save twice (bug Already logged Case 2660)
 * 8. click Back
 * Result expected:
 * The Report should appear in the Reports' List
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
public class S1944_01NG {
	
	//#####***********************************************************************
	//###   To set up the Global Params Name Vars
	//#####***********************************************************************
	Class<? extends S1944_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
    Reports report;
    int UserIndex = 21;
    Users user;
	int reportIndex = 3;
	//---------------------------- End of Params ---------------------------------
	
	@BeforeClass(groups = { "Iter_14", "ReportsNG" })
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
	
	@AfterClass(groups = { "Iter_14", "ReportsNG" }, alwaysRun=true)
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
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S1944_01NG");
		report.setAccessUser(new String[] {IUsersConst.powerUser[1][0]});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.withParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});
		
		Assert.assertTrue(report.addOneNewReport(), "FAIL: Super User could not add new Report");
		
		Assert.assertTrue(report.addReportParamsAndAccess(true, true), "FAIL: Super User could not add Parameters");
		
	}
	
	@Test(groups = { "ReportsNG" },dependsOnMethods="initializeAndCreateReportNG")
	public void testAbilityToViewReportInListWith_CRM_UAPsNG() throws Exception {
		
		try{
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
			Assert.assertTrue(ReportsUtil.isReportInReportsList(report), "FAIL: User with Create UAPS unable to view report created by another User!");
	        
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
}
