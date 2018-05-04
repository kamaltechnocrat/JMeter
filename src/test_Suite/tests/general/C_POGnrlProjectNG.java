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
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class C_POGnrlProjectNG {

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
	Project poProj;

	// --------------------- End of Global Parameters -----------------------

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
		poProj = null;
		
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

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initializeFundingOpp")
	public void SubmitNewPOProject() throws Exception {
		poProj = new Project(prog, true);

		poProj.createPOProject(newOrg);

		poProj.submitProject(true);
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "SubmitNewPOProject")
	public void assignOfficersAndStaffs() throws Exception {
		
		poProj.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][0] } });

		poProj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[0][1][0] });
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "assignOfficersAndStaffs")
	public void evaluateSubmission() throws Exception {
		
		Assert.assertTrue(poProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(poProj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true,
				"Ready", false, false));
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "evaluateSubmission")
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("1");

		Assert.assertTrue(poProj.submitAward("Basic", 0, true),
				"FAIL: Could not Submit Basic Award Form");
	}
}
