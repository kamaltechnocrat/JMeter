/**
 * 
 */
package test_Suite.tests.post_Award;

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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

@GUITest
@Test(singleThreaded = true)
public class G_PA_DecisionStepNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isItNewProgram = false;
	
	boolean hasProgramForm = false;
	
	boolean isItNewOrg = true;
	
	boolean hasPublicationForm = true;

	String preFix = "-Decision-WSS-PA-";
	
	char portaltype = 'F';
	
	String postFix = "";

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	Program prog;
	
	Project proj;
	
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

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		proj = null;
		prog = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void initializeFundingOpp() throws Exception {
		prog = new Program(preFix, portaltype, hasProgramForm, isItNewProgram,
				hasPublicationForm);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		return;
	}

	@Test(groups = {"WorkflowNG"})
	public void initialize() throws Exception{
		initializeFundingOpp();
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

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="registerAndSubmitFOProject")
	public void assignOfficersAndSubmitAward() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

		Assert.assertTrue(foProj.submitAward("Standard", 3, true), "FAIL:");

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficersAndSubmitAward")
	public void submitAllInitialClaims() throws Exception {

		GeneralUtil.switchToFO();
		
		foProj.setClaimNumber(1);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
		
		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
		
		foProj.setClaimNumber(3);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitAllInitialClaims")
	public void review_1st_And_2nd_InitialClaims() throws Exception {

		GeneralUtil.switchToPO();

		foProj.setClaimNumber(1);

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"No", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoNonAutoA[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"No", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoNonAutoA[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="review_1st_And_2nd_InitialClaims")
	public void testWorkflowBeforeDecision() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertEquals(ProjectUtil.getEvaluationsEntries(foProj, IGeneralConst.approvMajoCritAuto[0][0] + newPASuffix, "Ready", false), 0);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="testWorkflowBeforeDecision")
	public void makeDecision() throws Exception {

		GeneralUtil.switchToPO();

		foProj.setClaimNumber(3);

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="makeDecision")
	public void testWorkflowAfterDecision() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertEquals(ProjectUtil.getEvaluationsEntries(foProj, IGeneralConst.approvMajoCritAuto[0][0] + newPASuffix, "Ready", false), 0);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="testWorkflowAfterDecision")
	public void advanceWorkflow() throws Exception {

		GeneralUtil.switchToPO();

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoNonAutoC[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		return;
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="advanceWorkflow")
	public void startTestingAdvancedWorkflow() throws Exception {

		Assert.assertEquals(ProjectUtil.getEntriesInMyAssignedSubmissionList(foProj,IGeneralConst.gnrl_Closing_POSS_Step[0][0] + newPASuffix,IFiltersConst.openProjView),1);

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertEquals(ProjectUtil.getEvaluationsEntries(foProj, IGeneralConst.approvMajoCritAuto[0][0] + newPASuffix, "Ready", false), 1);

		return;
	}

}
