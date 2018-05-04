
/* Test Case _01 For Story #1946  Reports Admin: list all reports in the system
 * Steps:
 * 1. Log in As a Super User
 * 2. Open Reports List
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values (Sample Report is for UserId with Numeric id or Username to be used as a parameter)
 * 5. for this story the fields can be any thing.
 * 6. Must click Save twice (bug Already logged Case 2660)
 * 7. click Back
 * 8. Create User With read only Report Management (Groups UAP)
 * 9. Log in as the user
 * 10. Open Reports List
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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

@GUITest
@Test(singleThreaded = true)
public class S1946_01NG {
	
    //#####***********************************************************************
    //###   To set up the Global Params Name Vars
    //#####***********************************************************************
	Class<? extends S1946_01NG> ownClass = this.getClass();

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
	        //--------------------------
			
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
	 public void initializeAndAddReportNG() throws Exception {
		
		report = new Reports();
		report.initReprots();
		
		report.setReportLetter(EFormsUtil.createAnyRandomString(5));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S1946_01NG");
		report.setAccessUser(new String[] {user.getGroup()});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.withParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
		report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
		report
				.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});
		
		report.addOneNewReport();
		
		Assert.assertTrue(report.addReportParamsAndAccess(true, true));
	}
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods="initializeAndAddReportNG")
	 public void testReadOnlyMyReportsUAPsNG() throws Exception {
		
		try{
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
			ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk);	
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, report.getReportTitle(), IFiltersConst.exact);	
			
			Assert.assertTrue(TablesUtil.getRowIndex(ITablesConst.myReprotsTableId, report.getReportTitle()) > -1, "FAIL: User unable to view Report in My Reports List!");

		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		} 
	}
}
