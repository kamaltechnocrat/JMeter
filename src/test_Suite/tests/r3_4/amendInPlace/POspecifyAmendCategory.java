/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.LookupUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class POspecifyAmendCategory {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;

	FOProject foProj;
	
	FOProject foProj1;

	String submissionStep = "Submission";
	
	String reason = "Request Step Amend";
	
	String comment = "";
	
	String[] values = new String[] { "Administrative", "Legal", "Corrective" };

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-PA-", "");

			foProj = new FOProject(fopp, "Specify-Amend-Category-", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		foProj1 = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void createAndSubmiProj() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/*
	 * Reviewer will verify amendment category type when amendment categories
	 * lookup is active
	 */

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void reviewerSpecifyAmendmentCategory() throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj,
					IGeneralConst.gnrl_SubmissionA[0][0],
					IFiltersConst.openProjView);

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			Assert.assertTrue(
					GeneralUtil
							.isSelectListExists(IAmendmentsConst.amendRequest_Category_DropDwnFld_ID),
					"FAIL: Amendment Type Field  is not available");

			for (String string : values) {

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(
						IAmendmentsConst.amendRequest_Category_DropDwnFld_ID,
						string), "FAIL: No Amendment Category to specify: "
						.concat(string));
			}

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.gnrl_SubmissionA[0][0],
									IPreTestConst.Groups[0][0][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj, "Corrective"),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	// Deactivate the amendment categories to verify the second scenario

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "reviewerSpecifyAmendmentCategory", enabled = true)
	public void deActivateAmendmentCategory() throws Exception {

		try {

			GeneralUtil.switchToPO();

			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);

			LookupUtil.taggleLookupActiveness(
					IAmendmentsConst.amendCategory_LookupName,
					ITablesConst.lookupListTableId);

			GeneralUtil.switchToFO();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	// This method will create and submit a project

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "deActivateAmendmentCategory", enabled = true)
	public void createAndSubmiProjSecondScenario() throws Exception {
		try {

			Assert.assertTrue(foProj.createFOProjectNewNew(),
					"FAIL: Could not create FO Project");

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/*
	 * Reviewer will verify amendment category type when amendment categories
	 * lookup is inactive
	 */

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createAndSubmiProjSecondScenario", enabled = true)
	public void reviewerSpecifyAmendmentCategorySecondScenario()
			throws Exception {
		try {
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			ProjectUtil.openSubmissionInMyProjectSubmissionsList(foProj,
					IGeneralConst.gnrl_SubmissionA[0][0],
					IFiltersConst.openProjView);

			Assert.assertTrue(GeneralUtil
					.isLinkExistsByTxt(IClicksConst.requestAmendmentLnk),
					"FAIL: Request Amendment Link not available!");

			ClicksUtil.clickLinks(IClicksConst.requestAmendmentLnk);

			Assert.assertFalse(
					GeneralUtil
							.isSelectListExists(IAmendmentsConst.amendRequest_Category_DropDwnFld_ID),
					"PASS: Amendment Type Field  is available where as Amendment Category Lookup is inactive");

			String dd = GeneralUtil.setDayofMonth(3);

			Assert.assertTrue(
					AmendmentsUtil.fillOutAmendments(
							new String[] { foProj.getProjFullName(),
									IGeneralConst.gnrl_SubmissionA[0][0],
									IPreTestConst.Groups[0][0][0], dd,
									"Test Issue Amendment with Amend In Place",
									"This a Comment" }, foProj),
					"FAIL: Could not fill out amendments!");

			Assert.assertTrue(
					GeneralUtil.isButtonEnabled(IClicksConst.issueAmendBtn),
					"FAIL: Button either does not exists or disabled!");

			ClicksUtil.clickButtons(IClicksConst.issueAmendBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/*
	 * This method will verify default Program Office amendment category
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "reviewerSpecifyAmendmentCategorySecondScenario", enabled = true)
	public void verifyDefaultAmendmentCategory() throws Exception {

		try {

			Assert.assertTrue(AmendmentsUtil
					.isDefaultPOAmendmentCategaoryExists(foProj,
							IGeneralConst.gnrl_SubmissionA[0][0]),
					"FAIL: Default PO Amendment Category is not available");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	/*
	 * The below method will activate the Lookup after verifying the deactivate
	 * lookup functionality so that default work flow will not be affected
	 */

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "verifyDefaultAmendmentCategory", enabled = true)
	public void activateAmendmentCategory() throws Exception {

		try {
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
			LookupUtil.taggleLookupActiveness(
					IAmendmentsConst.amendCategory_LookupName,
					ITablesConst.lookupListTableId);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
}
