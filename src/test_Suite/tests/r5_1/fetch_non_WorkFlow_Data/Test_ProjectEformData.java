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

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class Test_ProjectEformData {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> Test_ProjectEformData = this.getClass();

	private Log log = LogFactory.getLog(Test_ProjectEformData);

	String preFix = "-FetchData-PA-";

	String postFix = "-Project";

	String submissionStep = "Award-Crit";

	String applicantName = "Ouia 1";

	String postAwardFormName = "A-Fetch-Post-Award-Submission-Project";

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

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", preFix, postFix);

			foProj = new FOProject(fopp, "", true, 1, "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "FetchNonWorkFlowData" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {

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

	@Test(groups = { "FetchNonWorkFlowData" }, enabled = true)
	public void registerAndCreateFOProjectAndOpenPOproject() throws Exception {
		try {
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp.getFoppFullName(), foProj.getOrgFullName()),
					"FAIL: Could not Register to Fopp!");
			
     		foProj.createFOProject();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(this.foProj,
					IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Couldn't open Submission Form");
			
			Assert.assertTrue(GeneralUtil.AppendToText(IeFormFieldsConst.projectFormField_IDs[0], IeFormFieldsConst.projectFormfield_Values[0] ));
			
			Assert.assertTrue(GeneralUtil.AppendToText(IeFormFieldsConst.projectFormField_IDs[1], IeFormFieldsConst.projectFormfield_Values[1]));
						
			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

      		GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil.openPOprojectList(this.foProj,
					submissionStep, "Open Projects"),
					"FAIL: Couldn't open Submission Form");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "registerAndCreateFOProjectAndOpenPOproject", dataProvider = "Applicants-Data", enabled = true)
	public void setProjectEformData(String fieldId, String fieldValues)
			throws Exception {
		try {

			Assert.assertTrue(
					EFormsUtil.setEformFieldValues(fieldId, fieldValues),
					"FAIL: Eform field doesn't exist ");
			
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "setProjectEformData", enabled = true)
	public void openeFOSubmissionAndvalidateApplicantSubmissionData()
			throws Exception {
		try {

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn));
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.completeBtn));
			
			GeneralUtil.switchToFO();
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(this.foProj,
					IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Couldn't open Submission Form");

			Assert.assertTrue(EFormsUtil.validateProjectFieldValue(),
					"FAIL: Values are not equal to field values ");


		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "FetchNonWorkFlowData" }, dependsOnMethods = "openeFOSubmissionAndvalidateApplicantSubmissionData", enabled = true)
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

			Assert.assertTrue(EFormsUtil.validateProjectFieldValue(),
					"FAIL: Values are not equal to field values ");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "Applicants-Data")
	public static Object[][] generateAppProfileData() throws Exception {

		Object[][] values = new Object[][]

		{
				new Object[] { IeFormFieldsConst.projectFormField_IDs[0],
						IeFormFieldsConst.projectFormfield_Values[0] },
				new Object[] { IeFormFieldsConst.projectFormField_IDs[1],
						IeFormFieldsConst.projectFormfield_Values[1] } };

		return values;

	}

}
