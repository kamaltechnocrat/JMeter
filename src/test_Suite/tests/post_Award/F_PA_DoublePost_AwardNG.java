package test_Suite.tests.post_Award;

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
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class F_PA_DoublePost_AwardNG {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	
	boolean programForm = false;
	
	boolean newOrg = true;

	String preFix = "-Double-PA-";
	
	char portaltype = 'P';

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	
	Program prog;
	
	Project proj;

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
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void initializeFundingOpp() throws Exception {
		
		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
	}

	@Test(groups = {"WorkflowNG"})
	public void initialize() throws Exception{
		initializeFundingOpp();
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void submitNewPOProject() throws Exception {
		proj = new Project(prog, true);

		proj.createPOProject(newOrg);

		proj.submitProject(true);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitNewPOProject")
	public void assignOfficersAndEvaluate() throws Exception {
		
		proj.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
							IPreTestConst.Groups[6][1][0] }});
		 

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficersAndEvaluate")
	public void submit_1st_Award() throws Exception {
		
		GeneralUtil.switchToPO();

		Assert.assertTrue(proj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Award Form");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submit_1st_Award")
	public void submit_1st_InitialClaim() throws Exception {
		
		GeneralUtil.switchToPOWithProjOfficer("1");

		proj.setClaimNumber(1);

		Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0]
				+ newPASuffix, true));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submit_1st_InitialClaim")
	public void assign_1st_PAStaffAndEvaluate() throws Exception {
		
		proj.assignEvaluators(new String[] {
				IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[6][1][0] });

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
		
		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assign_1st_PAStaffAndEvaluate")
	public void submit_2nd_Award() throws Exception {
		
		GeneralUtil.switchToPO();

		Assert.assertTrue(proj.submitAward("Standard", 1, true));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submit_2nd_Award")
	public void submit_2nd_InitialClaim() throws Exception {
		
		proj.setClaimNumber(1);

		Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaimB[0][0]
				+ newPASuffix, true));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submit_2nd_InitialClaim")
	public void assign_2nd_PAStaff() throws Exception {
		
		proj.assignEvaluators(new String[] {
				IGeneralConst.reviewMajoCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0], IPreTestConst.Groups[2][1][1],
				IPreTestConst.Groups[2][1][2] });
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assign_2nd_PAStaff")
	public void evaluateInitialClaims() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewMajoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("3");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewMajoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvMajoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("2");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvMajoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("3");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvMajoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));

	}

}
