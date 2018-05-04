/**
 * 
 */
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
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.ErrorUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class PA_TooManyEvalForms_AutoAssignNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	private String approvalProcess = "Approval Process";

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = "-Many-Evals-";
	String postFix = "-PA-Auto";
	char portaltype = 'F';

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// ----- End of Global Params -------------
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// #####*******************************************

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
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
	public void continueProjectWorkflowNG() throws Exception {

		try {

			GeneralUtil.switchToFO();
			submitInitialClaim(1);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflowNG")
	public void testForTooManyEvalForms() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("2");

		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		Assert.assertFalse(ErrorUtil.checkForUnexpectedError(approvalProcess
				+ " - " + IClicksConst.openMyAssignedSubmissionListLnk));

		FiltersUtil
		.filterListByLabel(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj.getProjFullName(),IFiltersConst.exact);

		log.info(foProj.getProjFullName());

		Assert.assertEquals(TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_myAssignedSubmissionsTableId, foProj
				.getProjFullName()), 0,
				"FAIL: Shouldn't be any Submissions in My assigned Submission");
	}

	private void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
	}

	private void submitFOProject() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not Register to Funding Opp.!");
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

		Assert.assertTrue(foProj.submitAward("Standard", 1, true),
				"FAIL: Could not Submit Award Form");

	}

	private void submitInitialClaim(int claimNumber) throws Exception {

		foProj.setClaimNumber(claimNumber);

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.initialClaim[0][0] + newPASuffix, true),
				"Fail: Could not Find Project In FO Submission List!");

	}
}
