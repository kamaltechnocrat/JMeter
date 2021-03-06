/**
 * 
 */
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

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class E_PA_ProgramSubmissionStepNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	
	boolean programForm = false;
	
	boolean newOrg = true;

	String preFix = "-PO-PA-";
	
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
	public void assignOfficers() throws Exception {

		proj.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] },
				{ IPreTestConst.Groups[6][0][0],
						IPreTestConst.Groups[6][1][0] } });

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficers")
	public void submmitPOSubmission() throws Exception {

		Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_SubmissionA[0][0]," Changed", true));
		
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submmitPOSubmission")
	public void submitAward() throws Exception {

		Assert.assertTrue(proj.submitAward("Standard", 2, true),
				"FAIL: Could not Submit Standard Award Form");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitAward")
	public void submitInitialClaim() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		proj.setClaimNumber(1);

		Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0], true));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitInitialClaim")
	public void assignPAStaff() throws Exception {

		proj.assignEvaluators(new String[] {
				IGeneralConst.reviewUnanCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0], IPreTestConst.Groups[2][1][1],
				IPreTestConst.Groups[2][1][2] });
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignPAStaff")
	public void evaluateInitialClaims() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewUnanCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("3");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewUnanCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("2");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewUnanCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="evaluateInitialClaims")
	public void submitPA_POSubmission() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_SubmissionC[0][0] + newPASuffix," Changed", true));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("3");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvMajoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));
		
	}
}
