package test_Suite.tests.post_Award;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.preTest.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class B_PA_FO_ProjectNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	
	boolean programForm = false;
	
	boolean newOrg = true;

	String preFix = IGeneralConst.pa_ProgPrefix;
	
	String postFix = "";
	
	char portaltype = 'P';

	private String approvalProcess = "Approval Process";
	
	Program prog;
	
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// ------------------- End of Global Parameters
	// ----------------------------------

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		prog = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void initialize() throws Exception {
		initializeFundingOpp();
	}

	private void initializeFundingOpp() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void registerAndSubmitFOProject() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="registerAndSubmitFOProject")
	public void assignOfficers() throws Exception {

		GeneralUtil.switchToPO();

		foProj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][2] } });

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficers")
	public void approveApplication() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="approveApplication")
	public void submitAward() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(foProj.submitAward("Standard", 3, true),
				"FAIL: Could not Submit Award Form");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitAward")
	public void submitInitialClaim() throws Exception {

		GeneralUtil.switchToFO();

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

		foProj.setClaimNumber(1);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

		foProj.setClaimNumber(3);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitInitialClaim")
	public void assignStaffs() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("3");

		foProj.assignEvaluators(new String[] {
				IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0] });

		foProj.setClaimNumber(2);

		foProj.assignEvaluators(new String[] {
				IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0] });

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		Assert.assertFalse(ErrorUtil.checkForUnexpectedError(approvalProcess
				+ " - " + IClicksConst.openMyAssignedSubmissionListLnk));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignStaffs")
	public void evaluateInitialClaims() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		foProj.setClaimNumber(3);

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));
	}
}
