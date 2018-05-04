/**
 * 
 */
package test_Suite.tests.stories.release_1_5.manageReports;


import java.util.ArrayList;

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

/* Test Case _01 For Story #1940  View Reports: List
 * Steps:
 * 1. Log in As a Super User
 * 2. Create User With Read Only My Reports (Groups UAP)
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values 
 * 5. for this story the fields can be any thing.
 * 6. Used all params in the system, except (Dropdwon fromlist)
 * 7. Test adding Paramters, validate Paramater exists in planner
 * Result expected:
 * Able to add Paramaters to the report
 * 
 * 8. Test, User with My Reports UAP can view Report in My Reports List
 * Result expected
 * User able to
 */

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1940_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1940_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Reports report;
	int UserIndex = 24;
	Users user;
	boolean addUserAccess = true;
	boolean addParameters = true;
	ArrayList<String[]> uapList;
	int reportIndex = 6;

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

	@AfterClass(groups = { "ReportsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		report = null;
		user = null;
		uapList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"ReportsNG" })
	public void initializeAndCreateReportNG() throws Exception {
		
		try {

			report = new Reports();
			report.initReprots();
			
			report.setReportLetter(EFormsUtil.createAnyRandomString(5));
			report.setReportIdent(report.getReportLetter() + IReportsConst.reportId
					+ "-S1940_01NG");
			report.setAccessUser(new String[] { IUsersConst.powerUser[1][0], IUsersConst.users[UserIndex][1][0] });

			report.setReportName(report.getReportIdent());
			report.setReportTitle(report.getReportLetter() + "-"
					+ IReportsConst.withParamsReportsTitles[reportIndex]);

			report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
			report.setReportOrgAccess(IGeneralConst.org_Access_Public);
			
			report.setParamName(new String[] {IReportsConst.reportParamName[reportIndex]});
			report.setParamType(new String[] {IReportsConst.withParamsReportsType[reportIndex]});
			report.setParamLabels(new String[] { IReportsConst.reportParamLabels[reportIndex]});

			Assert.assertTrue(report.addOneNewReport(),
					"FAIL: User with Create UAPs could not add new Report");
			
			Assert.assertTrue(report.addReportParamsAndAccess(addUserAccess, addParameters),
			"FAIL: User with Create UAPs could not add Parameters");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"ReportsNG" },dependsOnMethods="initializeAndCreateReportNG")
	public void testReportIsVisibleInListWithMyReportsUAPsNG() throws Exception {
		
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
			Assert.assertTrue(ReportsUtil.isReportInMyReportsList(report), "FAIL: User with My Reports UAP did unable to find Report in List!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
