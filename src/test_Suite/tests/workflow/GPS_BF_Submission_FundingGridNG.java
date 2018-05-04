/**
 * 
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

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.GpsUtil;

@GUITest
@Test(singleThreaded = true)
public class GPS_BF_Submission_FundingGridNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean isNewFOPP = false;
	boolean hasAdminEForm = true;
	boolean isNewOrg = true;

	String preFix = "-GPS-";
	char portaltype = 'F';
	String postFix = "-BF-Submission-Funding";
	Integer numOfPayments = 3;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// Steps
	String progForm = "GPS Program Form";

	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "GPS-CALC-BF-Funding-Application" } };

	String approvStep[][] = {
			{ "Approval-CRQA", "Approval BF Form", "Approval", "true",
			"Optional (Yes)" },
			{ "Project Approval BF Form", "Quorum", "1", "true" } };

	String paInitialClaim1[][] = {
			{ "Initial-Claim", "", "Initial Post Award Submission", "true",
			"No" }, { "Post Award Submission", "Claim ", "true" } };

	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	// private static final String newPASuffix = "-pa";

	// #####--------------- END OF GLOBAL PARAMS VARS

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

		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {

		prog = new Program(preFix, portaltype, hasAdminEForm, isNewFOPP, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgramFormName("GPS Program Form");

		ClicksUtil.clickLinks(prog.getProgFullIdent());
		prog.managePaymentScheduleProgramForm(numOfPayments,
				paInitialClaim1[1][1]);	
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void createFOProjectAndSubmit() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not Register to Fund Opp.!");

		foProj.createFOProject();

		GpsUtil.submitApplicationFunding(foProj, true,
				submissionStep[0][0]);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createFOProjectAndSubmit")
	public void assignOfficerAndApprove() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[0][1][0] } });

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));
	}
}
