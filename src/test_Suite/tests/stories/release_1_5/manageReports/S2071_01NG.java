
package test_Suite.tests.stories.release_1_5.manageReports;


import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.cases.Reports;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.ReportsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;

/* Test Case _01 For Story #2071  Reports Planner: report parameters details (Programs related)
 * Steps:
 * 1. Log in As a Super User
 * 2. Create User With Create Report Management (Groups UAP)
 * 3. Log in as the user
 * 3. Add new Reports (plus sign)
 * 4. in the Report detail enter values 
 * 5. for this story the fields can be any thing.
 * 6. Used all params in the system, except (Dropdwon fromlist)
 * 7. Test adding Paramter, validate Paramater exists in planner
 * 8. Test Mandatory Fields in Report Paramaters
 * 
 * Result expected:
 * Able to add Paramaters to the report
 * 
 */


@Test(singleThreaded = true)
public class S2071_01NG {
	
	//#####***********************************************************************
	//###   To set up the Global Params Name Vars
	//#####***********************************************************************
	Class<? extends S2071_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
    
    Reports report;
    int UserIndex = 21;
    Users user;
	int reportIndex = 9;
	//---------------------------- End of Params ---------------------------------

	@BeforeClass(groups = { "Iter_15", "ReportsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			user = new Users(1, IUsersConst.users[UserIndex], "Users", "Program Office Users");
			
			IEUtil.openNewBrowser();
	        GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(user.getUsrName() + "1");
			
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

	@Test(groups = { "Iter_15", "ReportsNG" })
	public void initializeAndCreateReport() throws Exception {
		
		report = new Reports();
		report.initReprots();
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		
		report.setReportLetter(ReportsUtil.getNewBaseLetter(IReportsConst.reportId + "-S2071_01NG"));
		report.setReportIdent(report.getReportLetter() + IReportsConst.reportId + "-S2071_01NG");
		report.setAccessUser(new String[] {IUsersConst.powerUser[1][0]});
		
		report.setReportName(report.getReportIdent());			
		report.setReportTitle(report.getReportLetter() + "-" + IReportsConst.withParamsReportsTitles[reportIndex]);
		
		report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
		report.setReportOrgAccess(IGeneralConst.org_Access_Public);
		
		Assert.assertTrue(report.addOneNewReport(), "FAIL: User with Create UAPs could not add new Report");
		
	}

	@Test(groups = { "Iter_15", "ReportsNG" }, dependsOnMethods={"initializeAndCreateReport"}, dataProvider = "Parameters-Data")
	public void testAddingParametersNG(String paramName, String paramType, String paramLabel) throws Exception {
		try{
			
			report.setParamName(new String[] {paramName});
			report.setParamType(new String[] {paramType});
			report.setParamLabels(new String[] { paramLabel});
			
			log.info("Recieved Parameter Name: " + paramName + ": it's Type: " + paramType + "it's Label: " + paramLabel);
			
			Assert.assertTrue(report.addReportParamsAndAccess(false, true), "FAIL: User with Create UAPs could not add Parameters");
			
			Assert.assertTrue(ReportsUtil.doesParameterExists(paramName), "FAIL: the Parameter does not listed in planner");
	        
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
	
	@Test(groups = { "Iter_15", "ReportsNG" }, dependsOnMethods={"initializeAndCreateReport"}, dataProvider = "Parameters-Data")
	public void testValidateMandatoryFieldsInParametersDetails(String paramName, String paramType, String paramLabel) throws Exception {
		
		report.setParamName(new String[] {paramName});
		report.setParamType(new String[] {paramType});
		report.setParamLabels(new String[] { paramLabel});
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, report.getReportName(),IFiltersConst.exact);
		ClicksUtil.clickLinks(report.getReportName());
		
		Assert.assertFalse(report.fillReportParameters());
	}
	
	@DataProvider(name = "Parameters-Data")
	public Object[][] generateParameters(Method method) throws Exception {
		
		Object[][] result;
		
		if(method.getName() == "testAddingParametersNG")
		{
			result = new Object[][] {
					{"fopp", "Funding Opportunity", "Funding Opportnity:"},
					{"proj","Projects","Projects:"},
					{"my proj","My Projects","My Projects:"},
					{"text","Text","Text:"},
					{"numeric","Numeric","Numeric:"},
					{"userName","Username","User Name:"},
					{"date","Date","Date:"}
					};
			
		}
		else
		{
			result = new Object[][] {
					{"", "Funding Opportunity", "Funding Opportnity:"},
					{"prj_id","My Projects",""}
					};
		}
		
		return result;
	}
}
