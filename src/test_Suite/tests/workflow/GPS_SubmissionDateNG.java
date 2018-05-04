/**
 * Steps:
 * - create a budget and milestones
 * - generate submission schedule
 * - submit award
 * - start but do not submit PA1
 * - amend the award
 * - insert a new payment with start date before PA1; call it PA0
 * - change PA1 start date to a later date after today
 * - regenerate the submission schedule
 * - note that a new PA0 is created
 * - note that PA1 is changed to a later date (how?)
 * - submit the award through to post-award
 * - note that PA0 is available and is empty
 * - note that PA1 is not available
 * - wait until PA1 date
 * - note that PA1 is now available and contains old data
 */

package test_Suite.tests.workflow;

/**
 * @author mshakshouki
 *
 */

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
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

@GUITest
@Test(singleThreaded = true)
public class GPS_SubmissionDateNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = "-";
	char portaltype = 'F';
	String postFix = "-IPASS-Date";
	Integer numOfPayments = 2;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	Program prog;
	FOProject proj;
	String dd;

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

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}

		// -----------------------------
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
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

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		GeneralUtil.switchToFO();

		createSubmission();

		GeneralUtil.switchToPO();

		assignOfficers();

		submitAward();

		GeneralUtil.switchToFO();

		editClaim(1);

		GeneralUtil.switchToPO();

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void test_ChangingInitialClaimStartDate() {
		log.info("GPS_SubmissionDate()");

		try {

			dd = GeneralUtil.setDayofMonth(1);
			
			proj.setClaimNumber(0);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
					proj.getProjFullName(), IGeneralConst.pa_AwardCrit[0][0],
					IPreTestConst.Groups[0][1][0], dd,
					"Test Re-Execute on Previous Amendment",
					"This is a Comment" }, proj),
					"FAIL: Could not Issue an Amendment against Award Form!");
			
			proj.setClaimNumber(1);

			Assert.assertTrue(proj.changeClaimStartDate(
					IGeneralConst.pa_AwardCrit[0][0], dd, true, false,
					"PA Submission Schedule"));

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "test_ChangingInitialClaimStartDate" })
	public void test_InitialClaim_InFO_AfterChangingStartDateNG() {

		try {

			GeneralUtil.switchToFO();

			proj.setClaimNumber(1);

			Assert.assertFalse(FrontOfficeUtil.openFOSubmissionForm(proj,
					IGeneralConst.initialClaim[0][0] + newPASuffix),
					"FAIL: Error Opening or Submitting Claim 1");

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "test_ChangingInitialClaimStartDate" })
	public void test_InitialClaim_InPO_AfterChangingStartDateNG() {
		try {

			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil.openInitialClaimForm(proj,
					IGeneralConst.initialClaim[0][0] + newPASuffix,
					"Open Projects", "Latest Version", "In Progress"));

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}

	}

	private void createSubmission() throws Exception {

		proj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), proj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		proj.createFOProject();

		Assert.assertTrue(proj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
	}

	private void assignOfficers() throws Exception {
		proj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });
	}

	private void submitAward() throws Exception {

		Assert.assertTrue(proj.submitAward("Standard", 2, true),
				"FAIL: Could not submit Standard Award Form!");

	}

	private void editClaim(int claimNum) throws Exception {

		proj.setClaimNumber(claimNum);

		Assert.assertTrue(proj.submitFOProject(IGeneralConst.initialClaim[0][0]
				+ newPASuffix, false),
				"FAIL: Error Opening or Submitting Claim " + claimNum);
	}

}
