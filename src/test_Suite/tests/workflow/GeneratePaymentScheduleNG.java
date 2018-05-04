/**
 * This Test case requires Preperations
 * 1. Make sure Generate Payment Schedule classes and properties file are all ready
 * 2. Import a Zip file Called GeneratePaymentSchedule.zip, this contains Lookups, Forms and Program
 * 3. Due to this can not be run remotely....
 */
package test_Suite.tests.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.GpsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class GeneratePaymentScheduleNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean newProgram = false;
	boolean programForm = true;
	boolean newOrg = true;

	String preFix = "-GPS-";
	char portaltype = 'F';
	String postFix = "-ProgForm";
	Integer numOfPayments = 2;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	// Steps
	String progForm = "Program Form";
	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "Sub" } };

	String paAwardStep[][] = { { "Award-Crit", "", "Award", "true", "Yes" },
			{ "GPS-CALC-BF-Funding-Grid", "GPS CALC BF Funding Grid" } };
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim1[][] = {
			{ "Initial-Claim", "", "Initial Post Award Submission", "true",
					"No" }, { "Post Award Submission", "Claim", "true" } };
	String paInitialClaim2[][] = {
			{ "Initial-Claim", "", "Initial Post Award Submission", "true",
					"No" }, { "BF Post Award Submission", "Claim ", "true" } };

	Program prog;
	Project proj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// #####--------------- END OF GLOBAL PARAMS VARS

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			// -----------------------------
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		proj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {
		
		prog = new Program(preFix, portaltype, programForm, newProgram,
				false);

		prog.setProgPostfix(postFix);

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		prog.setProgramFormName(progForm);
	}
	
	@Test(groups= {"WorkflowNG"}, dependsOnMethods = "initialize")
	public void managePaymentScheduleInAdminEForm() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.initProgram();

			ClicksUtil.clickLinks(prog.getProgFullIdent());

			prog.managePaymentScheduleProgramForm(numOfPayments,
					paInitialClaim1[1][1]);
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="managePaymentScheduleInAdminEForm")
	public void createProjectAndAssignOfficers() throws Exception {
		try {

			proj = new Project(prog, true);

			proj.setNewProject(true);

			proj.createPOProject(newOrg);

			proj.submitProject(true);

			proj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createProjectAndAssignOfficers")
	public void testCalcBF_PaymentScheduleList_FromAdminEForm() {

		try {

			ProjectUtil.openPOAwardFormletInList(proj, paAwardStep[0][0],
					"Milestone");

			TablesUtil.openInTableByImage("/data/", "Claim 1", 1);
			
			Assert.assertEquals(GeneralUtil.getTextFieldContentsByTitle("Milestone Name"),"Claim 1", "FAIL: CalcBF Text in List Details !");

			Assert.assertEquals(GeneralUtil.getTextFieldContentsByTitle("Milestone Start Date"), prog.getStartDate(), "FAIL: CalcBF Text in List Details !");

			Assert.assertEquals(GeneralUtil.getTextFieldContentsByTitle("Milestone End Date"), prog.getEndDate(), "FAIL: CalcBF Text in List Details !");

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "testCalcBF_PaymentScheduleList_FromAdminEForm" })
	public void testCalcValues_G3Utils_GridViewOfList() throws Exception {

		try {

			GpsUtil.openAndFillGPSAwardForm(proj);

			ClicksUtil.clickLinks("Result Formlet");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(0),
					"2,000.00");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(1),
					"6,000.00");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(2),
					"$2,000.00");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(3),
					"$4,000.00");

			ClicksUtil.clickLinks("Summary");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "testCalcValues_G3Utils_GridViewOfList" })
	public void testProjectTokenBeforeCompletingPostAward() throws Exception {
		try {

			Assert.assertFalse(ProjectUtil
					.openSubmissionInMyAssignedSubmissionList(proj,
							"POSS Closing Step", IFiltersConst.openProjView));

			ClicksUtil
					.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "testProjectTokenBeforeCompletingPostAward" })
	public void testDeletingClaimAfterAmendingAward() throws Exception {

		requestAmendmentAndRemoveEntry();

		ClicksUtil.clickLinks("Result Formlet");

	}

	@Test(groups = { "WorkflowNG" },dataProvider="ResultFormlet", dependsOnMethods = { "testDeletingClaimAfterAmendingAward" })
	public void testCalcValues_G3Utils_GridViewOfListAfterValuesChanged(String string,int index)
			throws Exception {

		try {
			
			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(index),string);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "testCalcValues_G3Utils_GridViewOfListAfterValuesChanged" })
	public void testProjectTokenWhenSubProjectNotRequired() throws Exception {
		try {

			ClicksUtil.clickLinks("Summary");
			
			GeneralUtil.takeANap(0.5);

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

			proj.setClaimNumber(2);

			Assert.assertTrue(proj.submitFromApplicantSubList(paInitialClaim1[0][0] + newPASuffix, false), "FAIL: could not open Initial Claim 2");

			proj.setClaimNumber(0);

			Assert.assertTrue(ProjectUtil
					.openSubmissionInMyAssignedSubmissionList(proj,
							"POSS Closing Step", IFiltersConst.openProjView));

			ClicksUtil
					.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}
	
	@DataProvider(name="ResultFormlet")
	public Object[][] generateResultValues()
	{
		return new Object[][] {
				new Object[] {"1,000.00",0},
				new Object[] {"3,000.00",1},
				new Object[] {"$1,000.00",2},
				new Object[] {"$2,000.00",3}
		};
	}

	private void requestAmendmentAndRemoveEntry() throws Exception {

		String dd = GeneralUtil.setDayofMonth(3);
		
		proj.setClaimNumber(0);

		Assert.assertTrue(
				AmendmentsUtil.issueAmendment(new String[] {
						proj.getProjFullName(), paAwardStep[0][0],
						IPreTestConst.Groups[0][1][0], dd,
						"Test Re-Execute on Previous Amendment",
						"This is a Comment" }, proj),
				"Fail: Could not Issue an Amendment Against Award Form!");

		Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(proj,
				paAwardStep[0][0], IClicksConst.openMilestoneLnk),
				"FAIL: Could not Open the Award eForm!");
		
		proj.setClaimNumber(1);

		Assert.assertTrue(proj.removeScheduleEntryFromMilestones(true),
				"Fail: Could not Remove a Schedule Entry after Amendment!");

	}
}
