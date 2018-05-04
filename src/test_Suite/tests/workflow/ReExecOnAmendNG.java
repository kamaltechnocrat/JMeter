package test_Suite.tests.workflow;

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
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class ReExecOnAmendNG {

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

	String preFix = IAmendmentsConst.amend_PA_Prefix;
	char portaltype = 'P';
	String postFix = "-Re-Exec";
	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String reviewStep[][] = IGeneralConst.reviewQuoCritManu;
	String approvStep[][] = IGeneralConst.approvMajoCritAuto;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String reviewSubStep[][] = IGeneralConst.reviewQuoCritManu;
	String approvSubStep[][] = IGeneralConst.approvMajoCritAuto;

	Program prog;
	FOProject fo_po_Proj;
	Project Proj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// ---------------------------- End of Params

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			// ---------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		prog = null;
		fo_po_Proj = null;
		Proj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {

		initializeFOPP();

		GeneralUtil.switchToFO();
		submitFOProject();

		GeneralUtil.switchToPO();
		assignOfficersAndApprove();
		submitAward();

		GeneralUtil.switchToFO();
		submitFOClaims(1);
		submitFOClaims(2);

		evaluatePostAward();

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void test_RequestAmendmentOnApprovalStepNG() throws Exception {

		try {

			GeneralUtil.switchToPO();

			String dd = GeneralUtil.setDayofMonth(3);
			
			fo_po_Proj.setClaimNumber(0);

			Assert.assertTrue(AmendmentsUtil
					.issueAmendment(new String[] {
							fo_po_Proj.getProjFullName(), approvStep[0][0],
							IPreTestConst.Groups[3][1][0], dd,
							"Test Re-Execute on Previous Amendment",
							"This a Comment" }, fo_po_Proj));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="test_RequestAmendmentOnApprovalStepNG")
	public void test_ReSubmitApprovalNG() throws Exception {

		try {

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, enabled = false)
	public void reExecOnAmendNG() throws Exception {

		try {

			// createFundingOpp();

			// -------Submitting From PO ----------
			/*
			 * submitPOProject(); assignOfficersAndApprove(); submitAward();
			 * submitPOInitialClaim(1); submitPOInitialClaim(2);
			 */

			// -------Submitting From FO -------
			requestAmendmentAndTest();

		} catch (Exception e) {
			log.error("Unexpected Exception: ", e);

			throw new RuntimeException("Unexpected Exception: ", e);

		}
	}

	private void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setProgPostfix(postFix);

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		prog.initProgram();

	}

	// ---- Start PO Submission ----------------------

	/*
	 * private void submitPOProject() throws Exception {
	 * 
	 * fo_po_Proj = new Project(prog);
	 * 
	 * fo_po_Proj.createPOProject(newOrg);
	 * 
	 * fo_po_Proj.submitProject(true);
	 * 
	 * }
	 * 
	 * private void submitPOInitialClaim(int claimNumber) throws Exception {
	 * GeneralUtil.Logoff(); GeneralUtil.logBack();
	 * GeneralUtil.loginProjOfficer("1");
	 * 
	 * fo_po_Proj.setClaimNumber(claimNumber);
	 * 
	 * fo_po_Proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0] +
	 * newPASuffix,true); }
	 */
	// -------- End PO Submission ---------
	// ###----- Start FO Submission ----------
	private void submitFOProject() throws Exception {

		fo_po_Proj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), fo_po_Proj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		fo_po_Proj.createFOProject();

		Assert.assertTrue(fo_po_Proj
				.submitFOProject(submissionStep[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");
	}

	private void submitFOClaims(int claimNumber) throws Exception {

		fo_po_Proj.setClaimNumber(claimNumber);

		Assert.assertTrue(fo_po_Proj.submitFOProject(paInitialClaim[0][0]
				+ newPASuffix, true),
				"Fail: Could not Find Project In FO Submission List!");
	}

	// ---- End FO Submission -----------

	private void assignOfficersAndApprove() throws Exception {

		fo_po_Proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][0] } });

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("3");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("5");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true, "Ready", false, false));
	}

	private void submitAward() throws Exception {
		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("1");

		Assert.assertTrue(fo_po_Proj.submitAward("Standard", 3, true),
				"FAIL: Could not Submit Award Form");
	}

	private void evaluatePostAward() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("1");

		fo_po_Proj.setClaimNumber(1);

		fo_po_Proj.assignEvaluators(new String[] {
				reviewSubStep[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0] });

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(fo_po_Proj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvSubStep[0][0] + newPASuffix, true,
				IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("2");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvSubStep[0][0] + newPASuffix, true,
				IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("3");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvSubStep[0][0] + newPASuffix, true,
				IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
	}

	private void requestAmendmentAndTest() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("1");

		fo_po_Proj.setClaimNumber(0);

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true,
				IFiltersConst.evaluateSubmissions_InProgress_StatusSubView,
				false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("1");

		fo_po_Proj.setClaimNumber(3);

		fo_po_Proj.removeClaimEntry(paAwardStep[0][0], true);
	}
}
