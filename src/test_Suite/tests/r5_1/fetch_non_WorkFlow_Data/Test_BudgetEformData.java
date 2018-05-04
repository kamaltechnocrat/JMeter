/**
 * 
 */
package test_Suite.tests.r5_1.fetch_non_WorkFlow_Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class Test_BudgetEformData {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String preFix = "-FetchData-PA-";

	String postFix = "-Budget";

	String submissionStep = "Submission-A";

	String applicantName = "Ouia 1";

	String postAwardFormName = "A-Fetch-Post-Award-Submission-Budget";

	String postSubmissionStep = "Initial-Claim";

	FundingOpportunity fopp;

	FOProject foProj;

	// private static final String newPASuffix = "-pa"; // You may not need this
	// for the current FOPP. check closing step in the post award

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "FetchNonWorkFlowData" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			// this will go to Front Office and lognin as front,
			// The Applicant will be Ouia 1, also front4 can be used as well.

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			fopp = new FundingOpportunity("A", preFix, postFix);

			foProj = new FOProject(fopp, "", true, 1, "");

			openBudgetForm(fopp);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "FetchNonWorkFlowData" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			foProj = null;

			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

			log.warn("Ending: " + this.getClass().getSimpleName());

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "FetchNonWorkFlowData" }, dataProvider = "Applicants-Data", enabled = true)
	public void setBudgetEformData(String fieldId, String fieldValues)
			throws Exception {
		try {

			Assert.assertTrue(
					EFormsUtil.setEformFieldValues(fieldId, fieldValues),
					"FAIL: Eform field doesn't exist ");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "setBudgetEformData", enabled = true)
	public void registerAndCreateFOProjectAndOpenSubmission() throws Exception {
		try {

			// this will register to the FOPP and create a project against it...

			// to open the Applicant Submission, please look for methods in
			// FrontOffice and Projects Util under under utils.workflows
			// then create one more method where you test the field and it data
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
			ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);

			GeneralUtil.switchToFOOnly();
			GeneralUtil.LoginFO();

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp.getFoppFullName(), foProj.getOrgFullName()),
					"FAIL: Could not Register to Fopp!");
			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(this.foProj,
					submissionStep), "FAIL: Couldn't open Submission Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "registerAndCreateFOProjectAndOpenSubmission", enabled = true)
	public void validateApplicantSubmissionData() throws Exception {
		try {

			Assert.assertTrue(EFormsUtil.validateBudgetFieldValue(),
					"FAIL: Values are not equal to field values ");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "validateApplicantSubmissionData", enabled = true)
	public void submitAward() throws Exception {
		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(
					foProj.submitAward("Standard", 1, true, postAwardFormName),
					"FAIL: Could not Submit Standard Award Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "submitAward", enabled = true)
	public void validatePostAwardSubmission() throws Exception {
		try {
			GeneralUtil.switchToFO();
			foProj.setClaimNumber(1);
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(this.foProj,
					postSubmissionStep), "FAIL: Couldn't open Submission Form");

			Assert.assertTrue(EFormsUtil.validateBudgetFieldValue(),
					"FAIL: Values are not equal to field values ");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateAppProfileData() throws Exception {

		Object[][] values = new Object[][]

		{
				new Object[] { IeFormFieldsConst.budgetFormField_IDs[0],
						IeFormFieldsConst.budgetFormfield_Values[0] },
				new Object[] { IeFormFieldsConst.budgetFormField_IDs[1],
						IeFormFieldsConst.budgetFormfield_Values[1] }

		};

		return values;

	}

	private void openBudgetForm(FundingOpportunity fopp) throws Exception {

		Assert.assertTrue(fopp.openFundingOppPlanner(),
				"FAIL: Could not open FOPP Planner!");

		Assert.assertTrue(fopp.openBudgetingEForm(),
				"FAIL: Could not open Budget e.Form from Planner!");

	}

}
