package test_Suite.tests.r3_4.referenceTable;

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
import test_Suite.constants.cases.IRefTablesConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.ReferenceTablesUtil;
import test_Suite.utils.workflow.StepsUtil;

/**
 * @author sfatima
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class test_ReferenceTables_In_WorkFlow {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();

			GeneralUtil.navigateToFO();

			GeneralUtil.loginAnyFO("front");
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Reference-PA-", "");

			foProj = new FOProject(fopp, "", true, 1,
					EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {

		foProj = null;
		fopp = null;

		 GeneralUtil.Logoff();
		 IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void createNewProjectAndSubmit() throws Exception {

		foProj.createFOProjectNewNew();

		foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(
				foProj, foProj.getCurrentStepName()),
				"Fail: Couldn't open Submission Step");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(
				IRefTablesConst.enterAnyTextFiledTtl,
				IRefTablesConst.textFieldData),
				"Fail: Couldn't add text to the text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
				"Fail: Couldn't find Submit Button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createNewProjectAndSubmit")
	public void modifyDataInRefTable() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(
				ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk),
				"Fail:Couldn't click on Reference Table Link");

		Assert.assertTrue(ReferenceTablesUtil.openReferenceTable_Add_Data(
				IRefTablesConst.budgetRefTable_Name,
				IRefTablesConst.budgetYear2019,
				IRefTablesConst.expense_Capital,
				IRefTablesConst.capital_Equipment),
				"Fail: Couldn't add data to Reference Table");

		Assert.assertTrue(ReferenceTablesUtil.openReferenceTable_DeleteRow(
				IRefTablesConst.budgetRefTable_Name,
				IRefTablesConst.budgetYear2018,
				IRefTablesConst.expense_Capital,
				IRefTablesConst.capital_Equipment),
				"Fail: Couldn't delete data from Reference Table");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "modifyDataInRefTable")
	public void submitReview1() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.loginReviewer("1");

		foProj.initializeStep(IGeneralConst.reviewAQuoCritAuto[0][0]);

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(
				foProj, IGeneralConst.reviewAQuoCritAuto[0][2],
				IRefTablesConst.statusReady, "Budget Limits",
				false, IRefTablesConst.versionNumber1, ""),
				"Fail: Couldn't open Evaluation Form");

		Assert.assertFalse(
				ReferenceTablesUtil
						.insertTo_SingleFilterEListTest(IRefTablesConst.budgetYear_Fields_eList[10]),
				"Fail: Data should not be in the list");

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk),
				"Fail: Couldn't click on Clear Filters Link");

		Assert.assertTrue(ReferenceTablesUtil.insertDataToSingleFilterEList(),
				"Fail: Couldn't insert data to Single Filter eList");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview1", dataProvider = "BudgetData")
	public void verifySingleFilterEList(String[] strsToFind) throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil.verify_singleFilterEList_data(strsToFind),
				"Fail: Couldn't verify Single Filter eList data");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifySingleFilterEList")
	public void submitReview2() throws Exception {

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));

		foProj.initializeStep(IGeneralConst.reviewBQuoCritAuto[0][0]);

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(
				foProj, IGeneralConst.reviewBQuoCritAuto[0][2],
				IRefTablesConst.statusReady, foProj.getCurrentStepName(),
				false, IRefTablesConst.versionNumber1, ""),
				"FAIL: Couldn't open Evaluate Submissions Formlet");

		Assert.assertFalse(
				ReferenceTablesUtil
						.insertTo_DoubleFilterEListTest(IRefTablesConst.yearlyLimit_Fields_eList[10]),
				"Fail: Data should not be listed");

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk),
				"Fail: Couldn't click on Clear Filters Link");

		Assert.assertTrue(ReferenceTablesUtil.insertDataToDoubleFilterEList(),
				"Fail: Couldn't add data to double Filter eList");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitReview2", dataProvider = "BudgetData")
	public void verifyDoubleFilterEList(String[] strsToFind) throws Exception {

		Assert.assertTrue(
				ReferenceTablesUtil.verify_doubleFilterEList_data(strsToFind),
				"Fail: Couldn't verify Double Filter eList data");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyDoubleFilterEList")
	public void submitAward() throws Exception {

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn));

		GeneralUtil.switchToPO();

		foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);

		Assert.assertTrue(
				ProjectUtil.openPOAwardFormletInList(foProj,
						IGeneralConst.gnrl_AwardCrit[0][2],
						foProj.getCurrentStepName()),
				"Fail: Couldn't open Award Formlet");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(
				IRefTablesConst.enterAnyTextFiledTtl,
				IRefTablesConst.textFieldData),
				"Fail: Couldn't add data to text field");

		Assert.assertTrue(StepsUtil.insertTo_SubScheduleListFormlet1(
				"PEF-Post Award Submission-PushBack",
				ILBFunctionConst.lbf_SubSchedules_Fields_eList[0]),
				"Fail: Couldn't add data to Submission Schedule List formlet");

		// Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
		// "Fail: Couldn't find Submit button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "submitAward" })
	public void initialClaimSubmit() throws Exception {

		GeneralUtil.switchToFOOnly();

		GeneralUtil.loginAnyFO("front");

		foProj.initializeStep(IGeneralConst.initialClaim[0][0]);

		Assert.assertTrue(FrontOfficeUtil.openFO_SubmissionWithStepFullName(
				foProj, foProj.getCurrentStepName()),
				"Fail: Couldn't find FO Submission in the list");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(
				IRefTablesConst.enterAnyTextFiledTtl,
				IRefTablesConst.textFieldData),
				"Fail: Couldn't add data to text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
				"Fail: Couldn't find Submit button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "initialClaimSubmit" })
	public void postAwardReview1() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.loginReviewer("1");

		foProj.initializeStep(IGeneralConst.reviewQuoCritAutoA[0][0]);

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(
				foProj, IGeneralConst.reviewQuoCritAutoA[0][2],
				IRefTablesConst.statusReady, foProj.getCurrentStepName(),
				false, IRefTablesConst.versionNumber1, ""),
				"Fail: Couldn't open Evaluate Submissions Formlet 1");

		Assert.assertFalse(
				ReferenceTablesUtil
						.insertTo_SFilterTest(IRefTablesConst.budgetYear_Fields_eList[10]),
				"Fail: Data should not be listed in the list");

		Assert.assertTrue(
				ReferenceTablesUtil.insertDataToSingleFilterAndVerify(),
				"Fail: Couldn't add or verify data");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
				"Fail: Couldn't find Submit button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "postAwardReview1" })
	public void postAwardReview2() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.loginReviewer("1");

		foProj.initializeStep(IGeneralConst.reviewQuoCritAutoB[0][0]);

		Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList_New(
				foProj, IGeneralConst.reviewQuoCritAutoB[0][2],
				IRefTablesConst.statusReady, foProj.getCurrentStepName(),
				false, IRefTablesConst.versionNumber1, ""),
				"Fail: Couldn't open Evaluate Submissions Formlet 2");

		Assert.assertFalse(
				ReferenceTablesUtil
						.insertTo_Db_Filter_Test(IRefTablesConst.yearlyLimit_Fields_eList[10]),
				"Fail: Data should not be listed in the list");

		Assert.assertTrue(
				ReferenceTablesUtil.insertDataToDoubleFilterAndVerify(),
				"Fail: Couldn't add or verify data");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
				"Fail: Couldn't find Submit button");

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "postAwardReview2")
	public void openFormInMyAssignedSubmissionsNSubmit() throws Exception {
		GeneralUtil.switchToPO();

		Assert.assertTrue(ProjectUtil
				.openSubmissionInMyAssignedSubmissionList_New(

				foProj, IGeneralConst.gnrl_Closing_Step[0][0],
						IRefTablesConst.openProjects),
				"Fail: Couldn't open Submission in My Assigned Submissions List");

		Assert.assertTrue(GeneralUtil.appendToTextFieldByTtl(
				IRefTablesConst.enterAnyTextFiledTtl,
				IRefTablesConst.textFieldData),
				"Fail: Couldn't add data to text field");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn),
				"Fail: Couldn't find Submit button");

	}

	@DataProvider(name = "BudgetData")
	public Object[][] generateFormletTypes(Method method) {
		Object[][] result = null;

		if (method.getName() == "verifySingleFilterEList"
				|| method.getName() == "verifyDoubleFilterEList") {
			result = new Object[][] {

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[0] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[1] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[4] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[3] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[6] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[5] },

			{ IRefTablesConst.verify_yearlyLimit_Fields_eList[8] },

			};
		}

		return result;
	}

}