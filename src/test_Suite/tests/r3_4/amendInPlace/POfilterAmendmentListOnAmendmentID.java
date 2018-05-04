/**
 * 
 */
package test_Suite.tests.r3_4.amendInPlace;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class POfilterAmendmentListOnAmendmentID {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	
	FOProject foProj;
	
	String amendmentIcontext = null;

	String amendmentID = null;

	Hashtable<String, String> hashSteps;

	String subStep = IGeneralConst.gnrl_SubmissionA[0][0];

	/*------ End of Global Vars --------------*/
	/*
	 * Before running this script, grantium application should have an amendment
	 * requested record in Amendment list
	 */
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-PA-", "");

			foProj = new FOProject(fopp,
					"Filter-AmendmentList-On-AmendmentID-", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun = true)
	public void tearDown() {

		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}
// Method will create and submit a project
	
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
// Approve the Submission
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "createAndSubmiProj", enabled = true)
	public void evaluateSubmission() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(
				IGeneralConst.approvQuoCritAuto[0][0], true, "Ready", false,
				false));
	}

	// Submit an Award step
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "evaluateSubmission", enabled = true)
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		Assert.assertTrue(foProj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Basic Award Form");
	}
// Request an amendment of submitted step
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "submitAward" })
	public void requestAmendmentOnSubmission() throws Exception {
		try {

			hashSteps = new Hashtable<String, String>();

			foProj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "Yes");

			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "NO");

			foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);

			hashSteps.put(foProj.getCurrentStepName(), "Yes");

			String dd = GeneralUtil.setDayofMonth(3);
			String amender = "Applicant:".concat(foProj.getFoOrgName())
					.concat("(").concat(foProj.getFoOrgNumber()).concat(")");

			Assert.assertTrue(AmendmentsUtil
					.issueAmendInPlaceAmendmentWithOptionalRe_Exec(
							new String[] { foProj.getProjFullName(), subStep,
									amender, dd,
									"Test Re-Execute on Previous Amendment",
									"This a Comment" }, hashSteps, foProj),
					"FAIL: Not able to Request an Amendment!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
/*
 * The below method will get an AmendmentID from the project history 
 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "requestAmendmentOnSubmission" }, enabled = true)
	public void getAmednmentIDInProjectSubmissonHistory() throws Exception {
		try {

			Assert.assertTrue(
					ProjectUtil
							.getAmendedStepInProjectSubmissionHistory(
									foProj,
									IFiltersConst.projects_SubmisisonHistory_StepStatus),
					"FAIL: Could not open then project submission history");

			amendmentIcontext = ProjectUtil
					.getAmendedStepDetailInProjectSubmissionHistory(foProj);

			amendmentID = AmendmentsUtil
					.getAmendmentIDFromTextFormat(amendmentIcontext);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
/* 
 * This method will verify the Amendment list record on the basis  Equal to operator 
 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "getAmednmentIDInProjectSubmissonHistory" }, enabled = true)
	public void verifyAmendmentListForEqualAmendmentID() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, amendmentID,
					IFiltersConst.amendmentID_EqualTo),
					"FAIL: Unable to filter Amendmentlist");

			Assert.assertTrue(TablesUtil.findInTableForEqualId(
					ITablesConst.amendmentsTableId, amendmentID),
					"FAIL: Could not find the equal Amendment ID:  "
							.concat(amendmentID));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentListForEqualAmendmentID" }, enabled = true)
	public void verifyAmendmentListForLessThanAmendmentID() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, amendmentID,
					IFiltersConst.amendmentID_LessThan),
					"FAIL: Unable to filter Amendmentlist");

			GeneralUtil.takeANap(1.0);

			Assert.assertTrue(TablesUtil.findInTableForLessThanId(
					ITablesConst.amendmentsTableId, amendmentID),
					"FAIL: Amendmentlist ID is not less than the filtered AmendmentID:  "
							.concat(amendmentID));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentListForLessThanAmendmentID" }, enabled = true)
	public void verifyAmendmentListForLessThanEqualToAmendmentID()
			throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, amendmentID,
					IFiltersConst.amendmentID_LessThanOrEqualTo),
					"FAIL: Unable to filter Amendmentlist");

			GeneralUtil.takeANap(1.0);

			Assert.assertTrue(TablesUtil.findInTableForLessThanEqualToId(
					ITablesConst.amendmentsTableId, amendmentID),
					"FAIL: Amendmentlist ID is not less than equal to the filtered AmendmentID:  "
							.concat(amendmentID));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentListForLessThanEqualToAmendmentID" }, enabled = true)
	public void verifyAmendmentListForGreaterThanAmendmentID() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			int m_amendmentId = Integer.parseInt(amendmentID) - 1;

			String new_amendmentId = Integer.toString(m_amendmentId);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, new_amendmentId,
					IFiltersConst.amendment_GreaterThan),
					"FAIL: Unable to filter AmendmentList");

			GeneralUtil.takeANap(1.0);

			Assert.assertTrue(TablesUtil.findInTableForGreaterThanId(
					ITablesConst.amendmentsTableId, new_amendmentId),
					"FAIL: Amendmentlist ID is not Greater than the filtered AmendmentID:  "
							.concat(new_amendmentId));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentListForGreaterThanAmendmentID" }, enabled = true)
	public void verifyAmendmentListForGreaterThanEqualToAmendmentID()
			throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, amendmentID,
					IFiltersConst.amendmentID_GreaterThanOrEqualTo),
					"FAIL: Unable to filter AmendmentList");

			GeneralUtil.takeANap(1.0);

			Assert.assertTrue(TablesUtil.findInTableForGreaterThanEqualToId(
					ITablesConst.amendmentsTableId, amendmentID),
					"FAIL: AmendmentID is not Greater than equal to the filtered AmendmentID:  "
							.concat(amendmentID));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "verifyAmendmentListForGreaterThanEqualToAmendmentID" }, enabled = true)
	public void verifyAmendmentListForBetweenAmendmentID() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

			Assert.assertTrue(FiltersUtil.filterListByLabel(
					IFiltersConst.amendmentId_Lbl, "1", amendmentID,
					IFiltersConst.amendment_Between),
					"FAIL: Unable to filter AmendmentList");

			GeneralUtil.takeANap(1.0);

			Assert.assertTrue(TablesUtil.findInTableForBetweenId(
					ITablesConst.amendmentsTableId, amendmentID),
					"FAIL: Amendmentlist ID is not between the filtered AmendmentID:  "
							.concat(amendmentID));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}
}
