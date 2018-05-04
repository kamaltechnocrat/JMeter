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
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class SubmissionScheduleNotificationsNG {

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

	String preFix = "-ClaimsNotif-PA-";
	char portaltype = 'F';
	String postFix = "";

	String notifyWhen = "";

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	String reviewersGrp[] = { IPreTestConst.Groups[2][0][0] };
	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	boolean repeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[0];
	boolean repeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[1];
	boolean repeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[2];
	boolean repeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[3];

	boolean noRepeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[14];
	boolean noRepeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[5];
	boolean noRepeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[6];
	boolean noRepeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[7];
	boolean noRepeatNotifyEvals[] = INotificationsConst.stepAllCheckBoxValues[13];

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String reviewStep[][] = IGeneralConst.reviewQuoCritManu;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String reviewSubStep[][] = IGeneralConst.reviewQuoCritAuto;
	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// ---------------------------- End of Params

	@BeforeClass(groups = { "NotificationsNG" })
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

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() {
		
		prog = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "NotificationsNG" })
	public void initialize() throws Exception {
		
		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setProgPostfix(postFix);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
		
		return;
	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods = "initialize")
	public void submitFOProject() throws Exception {
		
		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);
		
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="submitFOProject")
	public void assignOfficersAndSubmitAward() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] },
				{ IPreTestConst.Groups[6][0][0],
						IPreTestConst.Groups[6][1][0] } });

		foProj.setEndDate(GeneralUtil.setDayofMonth(5));

		Assert.assertTrue(foProj.submitAward("Standard", 3, true),
				"FAIL: Could not Submit Award Form");

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="assignOfficersAndSubmitAward")
	public void submitFOClaims() throws Exception {
		
		GeneralUtil.switchToFO();

		foProj.setClaimNumber(3);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0],
				false),
				"FAIL: Should Find Initial Claim In FO Submission List!");

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0],
				true),
				"FAIL: Should Find Initial Claim In FO Submission List!");

		foProj.setClaimNumber(1);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0],
				true),
				"FAIL: Should Find Initial Claim In FO Submission List!");

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="submitFOClaims")
	public void assignStaffAndEvaluateClaims() throws Exception {

		GeneralUtil.switchToPOOnly();

		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		foProj.setClaimNumber(2);

		Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOWithProjOfficer("1");

		foProj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[3][1][0] }); // ,
													// IPreTestConst.Groups[3][1][1],
													// IPreTestConst.Groups[3][1][2]

		foProj.setClaimNumber(1);

		foProj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[3][1][0], IPreTestConst.Groups[3][1][1],
				IPreTestConst.Groups[3][1][2] });

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		foProj.rejectSubmission(approvSubStep[0][0] + newPASuffix);

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="assignStaffAndEvaluateClaims")
	public void requestAmendmentAndTest() throws Exception {

		GeneralUtil.switchToPO();

		String dd = GeneralUtil.setDayofMonth(1);
		
		foProj.setClaimNumber(0);

		Assert.assertTrue(AmendmentsUtil
				.issueAmendment(new String[] { foProj.getProjFOFullName(),
						paAwardStep[0][0], IPreTestConst.Groups[0][1][0], dd,
						"Test Notification on Request Amendment",
						"This is a Comment" }, foProj),
				"FAIL: Could not Issue an Amendment against Award Form!");
		return;
	}

}
