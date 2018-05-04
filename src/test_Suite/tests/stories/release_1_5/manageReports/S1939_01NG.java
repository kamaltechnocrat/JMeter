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
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/* Test Case _01 For Story #1939  View Reports: Select Report Parameters page
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
 * 8. Test, User with My Reports UAP can view Report and Parameters
 * Result expected
 * User able to
 * 
 * 9. Test Parameters exists in My Reports.Reprot Parameters
 * Result Expected:
 * Parameters Exists
 */

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class S1939_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1939_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Reports report;
	int UserIndex = 24;
	Users user;
	boolean addUserAccess = false;
	boolean addParameters = true;
	int reportIndex = 7;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "ReportsNG" })
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
				+ "-S1939_01NG");
		report.setAccessUser(new String[] { IUsersConst.powerUser[1][0], IUsersConst.users[UserIndex][1][0] });

		report.setReportName(report.getReportIdent());
		report.setReportTitle(report.getReportLetter() + "-"
				+ IReportsConst.withParamsReportsTitles[reportIndex]);

		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);

		Assert.assertTrue(report.addOneNewReport(),
				"FAIL: User with Create UAPs could not add new Report");
		ReportsUtil.filter3rdPartyReports(report.getReportIdent());
		ClicksUtil.clickLinks(report.getReportIdent());
		
		report.addUsersAndGroupToReportAccess();

	}

	/**
	 * @param paramName
	 * @param paramType
	 * @param paramLabel
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" },dependsOnMethods="initializeAndCreateReportNG", dataProvider = "Parameters-Data")
	public void testAddingParametersNG(String paramName, String paramType,
			String paramLabel, boolean expected) throws Exception {
		try {

			report.setParamName(new String[] { paramName });
			report.setParamType(new String[] { paramType });
			report.setParamLabels(new String[] { paramLabel });

			log.info("Recieved Parameter Name: " + paramName + ": it's Type: "
					+ paramType + "it's Label: " + paramLabel);

			Assert.assertTrue(report.addReportParamsAndAccess(addUserAccess, addParameters),
					"FAIL: User with Create UAPs could not add Parameters");

			Assert.assertTrue(ReportsUtil.doesParameterExists(paramName),
					"FAIL: the Parameter does not listed in planner");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}

	}	
	
	@Test(groups = { "ReportsNG" }, dependsOnMethods = "testAddingParametersNG")
	public void toLoginUser() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	 

	@Test(groups = { "ReportsNG" }, dataProvider = "Parameters-Data", dependsOnMethods = "toLoginUser")
	public void testParametersAvailiableInMyReports(String paramName,
			String paramType, String paramLabel, boolean expected) throws Exception {

		report.setParamName(new String[] { paramName });
		report.setParamType(new String[] { paramType });
		report.setParamLabels(new String[] { paramLabel });
		
		Assert.assertTrue(ReportsUtil.isReportInMyReportsList(report), "FAIL: User with My Reports UAP could not find Report in List!");
		
		ClicksUtil.clickLinks(report.getReportTitle());
		
		Assert.assertEquals(ReportsUtil.isParameterExistsInReportParameters(paramLabel), expected, "FAIL: could not find Parameter in Report Parameters!");
		
		//Assert.assertTrue(ReportsUtil.isParameterExistsInReportParameters(paramLabel), "FAIL: could not find Parameter in Report Parameters!");
	}

	/**
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "Parameters-Data")
	public Object[][] generateParameters(Method method) throws Exception {

		Object[][] result;
		result = new Object[][] {
				{ "fopp", "Funding Opportunity", "Funding Opportnity", true },
				{ "proj", "Projects", "Projects", true },
				{"my proj","My Projects","My Projects", true },
				{ "text", "Text", "Text", true  },
				{ "numeric", "Numeric", "Numeric", true  },
				{ "userName", "Username", "User Name", false  },
				{ "date", "Date", "Date", true  } };

		return result;
	}

}
