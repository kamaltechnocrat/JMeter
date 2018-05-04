
package test_Suite.tests.stories.release_1_5.iter_1_5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;

/* Test Case _01 For Story #2030   Request Amendment UAP
 * Steps:
 * 1. Test depends on PreTest
 * 2. Project Officer Setup
 * 		a. ProjectOfficer1 (Request Amendment + Proejct Submission)
 * 		b. ProjectOfficer2 ( Project Submission UAP)
 * 		c. ProjectOfficer3 (Request Amendment UAP)
 * 3. Create Program
 *  	a. Submission (Shak)
 * 		b. Review	(PojectOfficer1)
 * 		c. Approval (PojectOfficer3)
 * 		d. Award	(PojectOfficer2)
 * 4. First Submission, Set P1 as Project officer
 * 5. Second Submision, Set P2 as Project officer
 * 6. Third submission, Set P3 as Project officer
 * 7. For each Submission Complete Project.
 * Result.
 * 1. First Submission, P1 Should be able to Request Amendment
 * 2. Second Submission, P2 Should not be able to Request Amendment
 * 3. Third Submission, p3 Should not be able to Request Amendment
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class S2030_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	private static Log log = LogFactory.getLog(S2030_01NG.class);

	boolean newProgram = true;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = "-S2030_01-";

	char portaltype = 'F';

	String postFix = "";

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	Program prog;

	FOProject foProj;

	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "Iter_15", "UAPs" })
	public void setUp() {

		try {

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();	
			log.info("Starting: S2030_01NG Test Class");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@AfterClass(groups = { "Iter_15", "UAPs" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();	
		log.info("Starting: S2030_01NG Test Class");
	}

	@Test(groups = { "Iter_15", "UAPs" })
	public void s2030_01NG() throws Exception {
		try {

			createFundingOpp();
			GeneralUtil.switchToFO();
			createAndSubmitFOProject();
			// Assign ProjectOfficer1
			assignOfficersAndEvaluate(0);
			loginAsOfficer("1");

			// Amend Review Step
			Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
					foProj.getProjFullName(),
					IGeneralConst.reviewQuoCritAuto[0][0],
					IPreTestConst.Groups[0][1][0],
					GeneralUtil.setDayofMonth(3),
					"Test Story 2030 Request Amendment UAP",
					"This is a Comment" }, foProj));

			/* Second Submission */
			GeneralUtil.switchToFO();
			submitOtherFOProjects();
			// Assign ProjectOfficer2
			assignOfficersAndEvaluate(1);
			loginAsOfficer("2");

			// Amend Approval Step
			Assert.assertFalse(AmendmentsUtil
					.doesAmendmentRequestIconExists(new String[] {
							foProj.getProjFullName(),
							IGeneralConst.approvQuoCritAuto[0][0] }, foProj),
					"Fail: Amendment Request Icon is Available");

			/* Third Submission */
			GeneralUtil.switchToFO();
			submitOtherFOProjects();
			// Assign ProjectOfficer3
			assignOfficersAndEvaluate(2);
			loginAsOfficer("3");
			foProj.submitAward("Basic", 0, true);

			// Amend Award Step
			Assert
					.assertFalse(
							GeneralUtil
									.isLinkExistsByTxt(IClicksConst.openMyAssignedSubmissionListLnk),
							"Fail: My In Basket link is Available for ProjectOfficer3");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}

	private void createFundingOpp() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		prog.initProgram();
		if (newProgram) {
			prog.setProgAdmin(progAdmin);
			prog.setProgOfficers(progOfficers);
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			prog.createProgram();
			prog.addStep(IGeneralConst.gnrl_SubmissionA);
			prog.manageStep(new String[][] { { progOfficers[0] },
					{ IGeneralConst.gnrl_SubmissionA[0][0] } });
			prog.setStepOfficer(progOfficers[1]);
			prog.addStep(IGeneralConst.reviewQuoCritAuto);
			prog.manageStep(new String[][] { { progOfficers[0] },
					{ IGeneralConst.reviewQuoCritAuto[0][0] } });
			prog.addStep(IGeneralConst.approvQuoCritAuto);
			prog.manageStep(new String[][] { { progOfficers[0] },
					{ IGeneralConst.approvQuoCritAuto[0][0] } });
			prog.addStep(IGeneralConst.gnrl_AwardCrit);
			prog.manageStep(new String[][] { { progOfficers[1] },
					{ IGeneralConst.gnrl_AwardCrit[0][0] } });
			prog.activateProgram("Active");
		}
	}

	private void createAndSubmitFOProject() throws Exception {

		foProj = new FOProject(prog);

		// First Submission
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()), "Fail: No "
				+ foProj.getFoOrgName());
		foProj.createFOProject();
		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true));
	}

	private void submitOtherFOProjects() throws Exception {

		foProj.createFOProject();
		foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true);

	}

	private void assignOfficersAndEvaluate(int officerBeat) throws Exception {
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		GeneralUtil.takeANap(0.5);
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

		foProj.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][officerBeat] } });

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));

	}

	private void loginAsOfficer(String officerBeat) throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginProjOfficer(officerBeat);

	}
}
