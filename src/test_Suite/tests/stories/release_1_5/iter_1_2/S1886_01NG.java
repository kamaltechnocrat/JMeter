/*
 * Test Case for Story #1886 Function: Calculated Allow Submission (Calculated Submission Condition)
 * Steps:
 * 1. Create two eForms (Submission, Approval,) type,
 * 2. From the Submission Bring Forward a value of Numeric field
 * 3. Set the Functions in Approval Formlets
 * 4. have different Approval Limits to 2 Different Approvers (One should be able to Submit)
 */

package test_Suite.tests.stories.release_1_5.iter_1_2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import test_Suite.lib.workflow.*;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.preTest.*;
import org.testng.annotations.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.cases.*;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1886_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = "-CSC-BF-";
	char portaltype = 'P';

	Program prog;
	Project proj;

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String approverGrp = IPreTestConst.Groups[3][0][0];

	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "Z-Submission BF-CSC" } };

	String approvalStep[][] = { { "Approval-A", "", "Approval", "true", "No" },
			{ "Z-Approval CSC-BF", "Majority", "", "true" } };

	String postFix = "-S1886_01";

	// ------------- End of Global Vars
	// -----------------------------------------

	@BeforeClass(groups = {"EFormsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			initializeFOPP();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"EFormsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		proj = null;
		prog = null; 

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = {"EFormsNG"  })
	public void submitPOProject() throws Exception {
		
		try {

			proj = new Project(prog, true);

			proj.createPOProject(newOrg);

			Assert.assertTrue(proj.submitFromApplicantSubList(submissionStep[0][0],
					true), "FAIL: could not submit Project;"
					+ proj.getProgFullIdent());

			proj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
					"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView),
					"FAIL: Could not Review Submission!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"EFormsNG"  },dependsOnMethods="submitPOProject")
	public void testBelowApprovalLimitNG() throws Exception {

		try {

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(proj.approveSubmission(approvalStep[0][0], false, "Ready", false, false));

			Assert.assertTrue(ProjectUtil
					.openEvaluateSubmissionFormletInList(proj,
							approvalStep[0][0], "In Progress",
							approvalStep[0][2], false),
					"FAIL: Could not open Approval submission!");

			ClicksUtil.clickLinks("Summary");

			Assert.assertFalse(GeneralUtil
					.isButtonEnabled(IClicksConst.submitBtn),
					"Failed: The Submit Button shouldn't be Enabled");

			Assert.assertTrue(GeneralUtil
					.FindTextOnPage("You Exceeded your limit"),
					"Failed: No Limit Exceeded Message!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"EFormsNG"  },dependsOnMethods="submitPOProject")
	public void testAboveApprovalLimitNG() throws Exception {

		try {
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("2");

			Assert.assertTrue(proj.approveSubmission(approvalStep[0][0], false, "Ready", false, false));

			Assert.assertTrue(ProjectUtil
					.openEvaluateSubmissionFormletInList(proj,
							approvalStep[0][0], "In Progress",
							approvalStep[0][2], false),
					"FAIL: Could not open Approval submission!");

			ClicksUtil.clickLinks("Summary");

			Assert.assertFalse(GeneralUtil
					.FindTextOnPage("You Exceeded your limit"),
					"Failed: Limit Exceeded Message not applicable in this case!");

			Assert.assertTrue(GeneralUtil
					.isButtonEnabled(IClicksConst.submitBtn),
					"Failed: The Submit Button should be Enabled");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void initializeFOPP() throws Exception {

		try {

			prog = new Program(preFix, portaltype, programForm, newProgram,
					false);

			prog.setProgPostfix(postFix);

			prog.initializeProgram("A");

			prog.setProgAdmin(progAdmin);

			prog.setProgOfficers(progOfficers);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
