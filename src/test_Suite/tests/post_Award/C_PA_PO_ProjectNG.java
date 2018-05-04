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
public class C_PA_PO_ProjectNG {

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

	Program prog;
	
	Project proj;

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	// -------------- End of Global parameters ----------------------

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

	@Test(groups = { "WorkflowNG" })
	public void initializeFundingOpp() throws Exception {
		
		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="initializeFundingOpp")
	public void newPOProject() throws Exception {

		proj = new Project(prog, true);
		proj.createPOProject(newOrg);

		proj.submitProject(true);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="newPOProject")
	public void assignOfficers() throws Exception {
		
		proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][0] } });
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficers")
	public void evaluateApplication() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		log.debug("Approver Logged In");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="evaluateApplication")
	public void submitAward() throws Exception {
		GeneralUtil.switchToPOOnly();
		GeneralUtil.logInSuper();

		Assert.assertTrue(proj.submitAward("Standard", 2, true),
				"FAIL: Could not Submit Award Form");
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="submitAward")
	public void submitAllClaims() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("1");
		
		submitInitialClaim(2);
		submitInitialClaim(1);
	}
	
	private void submitInitialClaim(int claimNumber) throws Exception {

		proj.setClaimNumber(claimNumber);

		Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0], true));
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="submitAllClaims")
	public void assignStaff() throws Exception {
		
		proj.assignEvaluators(new String[] {
				IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0] });
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="assignStaff")
	public void evaluateInitialClaims() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0]
				+ newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0]
				+ newPASuffix, true, "Ready", false, false));

	}
}
