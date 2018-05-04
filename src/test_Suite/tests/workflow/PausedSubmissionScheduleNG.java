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
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

@GUITest
@Test(singleThreaded = true)
public class PausedSubmissionScheduleNG {

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

	String preFix = IGeneralConst.pa_ProgPrefix;
	char portaltype = 'F';
	String postFix = "-PausedClaim";

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	boolean repeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[0];

	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// --- End of Global Params -----

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			// #####*******************************************


		} catch (Exception e) {
			Assert.fail("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		prog = null;
		
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
		assignOfficers();
		submitAward();
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
		public void test_allClaimsAvailable_InPONG() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("2");

		foProj.setClaimNumber(1);

		Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
				IGeneralConst.initialClaim[0][0] + newPASuffix,
				"Open Projects", "Latest Version", "All"),
				"FAIL: Claim 1 Should be available in PO Applicant Sub List");

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

		foProj.setClaimNumber(2);

		Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
				IGeneralConst.initialClaim[0][0] + newPASuffix,
				"Open Projects", "Latest Version", "All"),
				"FAIL: Claim 2 Should be available in PO Applicant Sub List");

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

		foProj.setClaimNumber(3);

		Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
				IGeneralConst.initialClaim[0][0] + newPASuffix,
				"Open Projects", "Latest Version", "All"),
				"FAIL: Claim 3 Should be available in PO Applicant Sub List");

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

		foProj.setClaimNumber(4);

		Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
				IGeneralConst.initialClaim[0][0] + newPASuffix,
				"Open Projects", "Latest Version", "All"),
				"FAIL: Claim 4 Should be available in PO Applicant Sub List");

		ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="test_allClaimsAvailable_InPONG")
	public void test_POOnly_ClaimNG() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("2");

		foProj.setClaimNumber(3);

		Assert.assertTrue(ProjectUtil.openClaimInMyAssignedSubmissionList(
				foProj, IGeneralConst.initialClaim[0][0] + newPASuffix, "Open Projects"),
				"FAIL: Claim 3 Should be available in My Assigned Submission");

		ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="test_POOnly_ClaimNG")
	public void test_NotRequiredClaimNG() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("2");

		foProj.setClaimNumber(4);

		Assert
				.assertFalse(ProjectUtil.openClaimInMyAssignedSubmissionList(
						foProj, IGeneralConst.PO_SubmissionA[0][0] + newPASuffix,
						"Open Projects"),
						"FAIL: Claim 4 POSS Should not be available in My Assigned Submission");

		ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="test_NotRequiredClaimNG")
	public void testPaused_POOnly_ClaimNG() throws Exception {

		try {
			GeneralUtil.switchToFO();
			submitFOInitialCliam(1, false);
			submitFOInitialCliam(3, false);
			submitFOInitialCliam(2, true);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}
	
	private void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

	}
	
	private void submitFOProject() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");
	}
	
	private void assignOfficers() throws Exception {
		foProj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][1] } });

	}
	
	private void submitAward() throws Exception {
		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		Assert.assertTrue(foProj.submitAward("Standard", 4, false),
				"FAIL: Could not Submit Award Form");

		foProj.setClaimNumber(1);

		foProj.setStartDate(GeneralUtil.setDayofMonth(1));

		Assert.assertTrue(foProj.changeClaimCriteria(true, false, false,
				IGeneralConst.initialClaim[1][0], IClicksConst.openClaimSubmissionScheduleLnk));

		foProj.setClaimNumber(3);

		foProj.setStartDate(GeneralUtil.setDayofMonth(0));

		Assert.assertTrue(foProj.changeClaimCriteria(true, true, false,
				IGeneralConst.initialClaim[1][0], IClicksConst.openClaimSubmissionScheduleLnk));

		foProj.setClaimNumber(4);

		Assert.assertTrue(foProj.changeClaimCriteria(false, false, true,
				IGeneralConst.initialClaim[1][0], IClicksConst.openClaimSubmissionScheduleLnk));

	}
	
	private void submitFOInitialCliam(int claimNumber, boolean status)
			throws Exception {

		foProj.setClaimNumber(claimNumber);

		if (status) {
			Assert
					.assertTrue(foProj.submitFOProject(
							IGeneralConst.initialClaim[0][0] + newPASuffix,
							true),
							"FAIL: Payment Schedule Claims should be Available to FO User!");
		} else {
			Assert
					.assertFalse(foProj.submitFOProject(
							IGeneralConst.initialClaim[0][0] + newPASuffix,
							true),
							"FAIL: Payment Schedule Claims should has been Paused until Tomorrow!");
		}
	}
}
