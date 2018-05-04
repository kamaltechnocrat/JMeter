/**
 * 
 */
package test_Suite.tests.general;

import java.util.Hashtable;

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
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.AmendmentsUtil.EArrParams;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class G_Re_ExecuteOnSubmissionAmendNG {

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

	String subStep = IGeneralConst.gnrl_SubmissionA[0][0];

	Hashtable<String, String> hashSteps;

	Program prog;
	
	Project proj;

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
		proj = null;
		
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

	@Test(groups = { "WorkflowNG" },dependsOnMethods="initializeFundingOpp")
	public void SubmitNewPOProject() throws Exception {
		proj = new Project(prog, true);

		proj.createPOProject(newOrg);

		proj.submitProject(true);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="SubmitNewPOProject")
	public void assignOfficersAndStaffs() throws Exception {
		proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][0] } });

		proj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[0][1][0] });
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignOfficersAndStaffs")
	public void evaluateSubmission() throws Exception {
		
		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView), 
				"FAIL: Could not evalute 'Ready' Submissions!");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true,
				"Ready", false, false), "FAIL: Could not approve 'Ready' Submissions!");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="evaluateSubmission")
	public void submitAward() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("1");

		Assert.assertTrue(proj.submitAward("Basic", 0, true),
				"FAIL: Could not Submit Basic Award Form");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitAward")
	public void requestAmendmentOnSubmission() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();

		hashSteps = new Hashtable<String, String>();

		proj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);

		hashSteps.put(proj.getCurrentStepName(), "Yes");

		proj.initializeStep(IGeneralConst.approvQuoCritManu[0][0]);

		hashSteps.put(proj.getCurrentStepName(), "No");

		proj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);

		hashSteps.put(proj.getCurrentStepName(), "Yes");

		String dd = GeneralUtil.setDayofMonth(3);
		Assert.assertTrue(AmendmentsUtil.issueAmendmentWithOptionalRe_Exec(
				new String[] { proj.getProjFullName(), subStep,
						IPreTestConst.Groups[0][1][0], dd,
						"Test Re-Execute on Previous Amendment",
						"This is a Comment" }, hashSteps, proj),
				"FAIL: Not able to Request an Amendment!");

	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendmentOnSubmission")
	public void testInAmendmentList() throws Exception {

		doTestInAmendmentList(subStep);

		reSubmitAmendedSubmission();

		doTestInAmendmentList(IGeneralConst.reviewQuoCritAuto[0][0]);

		resubmitReview();

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer("1");

		doTestInAmendmentList(IGeneralConst.gnrl_AwardCrit[0][0]);
	}
	
	private void reSubmitAmendedSubmission() throws Exception {

		Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.gnrl_SubmissionA[0][0],true), "FAIL: Could not re-submit Amendment!");

	}
	
	private void resubmitReview() throws Exception {

		Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true), "FAIL: Could not re-submit Review!");
	}
	
	private void doTestInAmendmentList(String formName) throws Exception {

		String amendParams[] = new String[5];

		amendParams[EArrParams.proj_FullName.ordinal()] = proj
				.getProjFullName();

		amendParams[EArrParams.applicantName.ordinal()] = proj.getOrgFullName();

		amendParams[EArrParams.step_FormName.ordinal()] = formName;

		amendParams[EArrParams.prog_FullName.ordinal()] = proj
				.getProgFullName();

		if (formName == subStep) {
			Assert.assertTrue(AmendmentsUtil
					.isAmendmentCancelIconExists(amendParams, proj));
		} else {
			Assert.assertFalse(AmendmentsUtil
					.isAmendmentCancelIconExists(amendParams, proj));
		}
		
	}
}
