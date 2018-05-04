/**
 * 
 */
package test_Suite.tests.workflow;

/**
 * @author mshakshouki
 * 
 */

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
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.GpsUtil;



@GUITest
@Test(singleThreaded = true)
public class GPS_BF_Award_FundingGridNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean isNewFOPP = false;
	boolean hasAdminEForm = false;
	boolean isNewOrg = true;

	String preFix = "-GPS-"; // IGeneralConst.pa_ProgPrefix;
	char portaltype = 'F';
	String postFix = "-BF-Award-Funding";
	Integer numOfPayments = 3;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	// Steps
	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "GPS-CALC-BF-Funding-Application" } };

	String paAwardStep[][] = { { "Award-Crit", "", "Award", "true", "Yes" },
			{ "GPS-CALC-BF-Funding-Grid" } };

	String postAwardStep[][] = IGeneralConst.postAwardCrit;

	String paInitialClaim1[][] = {
			{ "Initial-Claim", "", "Initial Post Award Submission", "true",
					"No" }, { "Post Award BF Submission", "Claim", "true" } };
	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	// private static final String newPASuffix = "-pa";

	// #####--------------- END OF GLOBAL PARAMS VARS

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ------------------------------

			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void teaDown()throws Exception {
		
		prog= null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void initialize() throws Exception{
		initializeFOPP();

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void settingUpTheWorkflowNG()throws Exception {
		
		GeneralUtil.switchToFO();
		
		createFOProject();
		
		GeneralUtil.switchToPO();
		
		assignOfficers();
		
		GpsUtil.openAwardAgreementSchedule(foProj);

		log.info("Setup Complete");

		
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="settingUpTheWorkflowNG")
	public void testCalcBF_DropdwonInList_FromApplicantSubmissionNG()throws Exception {

		ClicksUtil.clickLinks("Budget rows (expenses)");
		TablesUtil.openInTableByImage("/data/", "EXPENSE_CAPITAL", 1);

		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDown(0), "EXPENSE_CAPITAL", "FAIL: CalcBF Dropdown in List");
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDown(1), "CAPITAL_EQUIPMENT", "FAIL: CalcBF Dropdown in List");
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods="settingUpTheWorkflowNG")
	public void openBudgetGrid() throws Exception {
		
		ClicksUtil.clickLinks("Budget Formlet");		
	}
	
	@Test(groups = { "WorkflowNG" }, dataProvider="numericValues", dependsOnMethods="openBudgetGrid")
	public void testCalcBF_NumericGridViewOfList_ExpensesFields(int cellIndex, String dollarValue, boolean expected) throws Exception {		
		
		ClicksUtil.clickLinks("Budget Formlet");
		Assert.assertEquals(EFormsUtil.compareStringInEFormField(cellIndex, dollarValue), expected,"FAIL: CalcBF Numeric in Grid View of List Expenses");		
	}
	
	@Test(groups = { "WorkflowNG" }, dataProvider="numericValues", dependsOnMethods="openBudgetGrid")
	public void testCalcBF_NumericGridViewOfList_FundingSourcesFields(int cellIndex, String dollarValue, boolean expected) throws Exception {
		
		ClicksUtil.clickLinks("Budget Formlet");
		Assert.assertEquals(EFormsUtil.compareStringInEFormField(cellIndex, dollarValue), expected,"FAIL: CalcBF Numeric in Grid View of List Funding Sources");			
	}
	
	@Test(groups = { "WorkflowNG" }, dataProvider="numericValues", dependsOnMethods="openBudgetGrid")
	public void testCalcBF_NumericGridViewOfList_VarianceFields(int cellIndex, String dollarValue, boolean expected) throws Exception {
		
		ClicksUtil.clickLinks("Budget Formlet");
		Assert.assertEquals(EFormsUtil.compareStringInEFormField(cellIndex, dollarValue), expected,"FAIL: CalcBF Numeric in Grid View of List Variance");		
	}

	@Test(groups = { "WorkflowNG" }, dataProvider="FixedText", dependsOnMethods="openBudgetGrid")
	public void testClacBF_FixedTextInGridViewOfListNG(String divId, String fixedString, boolean expected) throws Exception {
		
		ClicksUtil.clickLinks("Budget Formlet");		
		Assert.assertEquals(GeneralUtil.verifyFixedTextBySpanClassWithinTable(IProjectsConst.gps_Budget_DataGrid_TableId,divId,fixedString), expected, "FAIL: Not Expected Result!");		
	}

	
	@DataProvider(name="numericValues")
	public Object[][] generateNumeric(Method method) 
	{
		Object[][] result = null;
		
		if(method.getName().equals("testCalcBF_NumericGridViewOfList_ExpensesFields"))
		{
			result = new Object[][] {
					new Object[] {0, "$3,000.00", true},
					new Object[] {1, "$3,000.00", true},
					new Object[] {2, "$6,000.00", true}};
			
		} else if(method.getName().equals("testCalcBF_NumericGridViewOfList_FundingSourcesFields"))
		{
			result = new Object[][] {
					new Object[] {6, "$1,000.00", true},
					new Object[] {7, "$1,000.00", true},
					new Object[] {8, "$2,000.00", true}};
			
		} else if(method.getName().equals("testCalcBF_NumericGridViewOfList_VarianceFields"))
		{
			result = new Object[][] {
					new Object[] {12, "$2,000.00", true},
					new Object[] {13, "$2,000.00", true},
					new Object[] {14, "$4,000.00", true}};
			
		}		
		return result;
	}
	
	@DataProvider(name="FixedText")
	public Object[][] generateFixedText()
	{
		return new Object[][] {
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Expenses", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"EXPENSE_CAPITAL", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"CAPITAL_EQUIPMENT", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Funding Sources", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Program Funding", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Grantium Inc.", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Cash", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"Variance", true},
				new Object[]{IFormletsConst.gpsBudgetFormletSpanClass,"No_Way", false}};
	}

	private void initializeFOPP() throws Exception {

		try {

			prog = new Program(preFix, portaltype, hasAdminEForm, isNewFOPP, false);

			prog.setProgPostfix(postFix);
			prog.setProgAdmin(progAdmin);
			prog.setProgOfficers(progOfficers);
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			prog.initProgram();

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	private void createFOProject() throws Exception {

		foProj = new FOProject(prog);
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not Register to Fund Opp.!");

		foProj.createFOProject();
		GpsUtil.submitApplicationFunding(foProj, true, submissionStep[0][0]);
	}

	private void assignOfficers() throws Exception {
		
		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],	IPreTestConst.Groups[0][1][0] } });
	}

}
