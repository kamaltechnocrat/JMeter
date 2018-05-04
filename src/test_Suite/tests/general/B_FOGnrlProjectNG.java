package test_Suite.tests.general;

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
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class B_FOGnrlProjectNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	String postFix = "";
	char portaltype = 'P';

	Program prog;
	
	FOProject foProj;

	// ----------- End of Global Parameters

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
	public void initializeFundingOppObject() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="initializeFundingOppObject")
	public void registerAndSubmitApplication() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);
		
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(
				prog.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"FAIL: Could not find Project In FO Submission List!");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="registerAndSubmitApplication")
	public void assignOfficersAndStaffs() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] },
				{ IPreTestConst.Groups[6][0][0],
						IPreTestConst.Groups[6][1][1] } });

		foProj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[0][1][0] });

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficersAndStaffs")
	public void evaluateSubmission() throws Exception {

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true,
				"Ready", false, false));
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="evaluateSubmission")
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("2");

		Assert.assertTrue(foProj.submitAward("Basic", 0, true),
				"FAIL: Could not Submit Basic Award Form");
	}
}
