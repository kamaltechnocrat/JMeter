/**
 * Test Case 02 of User Story # 2513 Auto-amendment enhancement
 * The Test Will Test Post-award Sub-Step on Re-Execute set to Yes
 * 
 * Steps
 * 1. Submit A project Continue to Post-Award
 * 2. Amend Initial Claim.
 * 3. Submit Initial Claim again(Submission Step)
 * Testing
 * 1. Should be able to Cancel Sub-approval Steps
 * 2. Open Submission History Filter to Sub-Approval Step, How many in List with In Progress? should be Double the Number of Submission
 * 3. Submit Sub-Approval Step
 * 4. Check Submission History Filter to the Last Step, is it in Progress
 */
package test_Suite.tests.stories.release_2_0.iter_7;

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
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.SubHistoryUtil;

/**
 * @author mshakshouki
 * 
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2513_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2513_02NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isItNewFOPP = false;
	boolean hasProgramForm = false;
	boolean isItNewOrg = false;
	boolean hasPublicationForm = false;

	String preFix = IAmendmentsConst.amend_PA_Prefix;
	char portaltype = 'F';
	String postFix = "-S2513_02";

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String reviewStep[][] = IGeneralConst.reviewQuoCritManu;
	String approvStep[][] = IGeneralConst.approvMajoCritAuto;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String reviewSubStep[][] = IGeneralConst.reviewQuoCritAuto;
	String approvSubStep[][] = IGeneralConst.approvMajoCritAuto;

	Program prog;
	FOProject foProj;
	FOUsers foUser;

	boolean retValue;


	private static final String newPASuffix = "-pa";
	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "Iter_27", "ProjectsNG" })
	public void setUp() throws Exception{
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

		initializeProgram();
		
		GeneralUtil.switchToFO();

		submitFOProject();
		
		GeneralUtil.switchToPO();
		
		assignOfficers();
		
		submitAward();			
		
		foProj.setOrgFullName("Ouia 1");
		
		submitClaim(1);			

		approveSubmissions();
		
		GeneralUtil.switchToPO();
		
		requestAmendment();
	}
	
	@AfterClass(groups = { "Iter_27", "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		foProj = null;
		foUser = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "Iter_27", "ProjectsNG" })
	public void testReExecuted_PA_EvaluationStepEntriesCountInSubmissionHistoryNG() throws Exception {

		try {
			
			Assert.assertTrue(SubHistoryUtil.openProjectHistory(foProj.getProjFOFullName()),"Fail: not able to Open Project!");	
			
			foProj.initializStep(approvSubStep[0][0] + newPASuffix);	
			
			Assert.assertEquals(SubHistoryUtil.getStepEntriesCount(foProj.getCurrentStepName(),"In Progress"),8);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 

	}
	
	@Test(groups = { "Iter_27", "ProjectsNG" })
	public void testCancelIconExistanceForReExecutedSubStepNG() throws Exception {
		
		String[] arrParam = {foProj.getProjLetter(), foProj.getProjFullName(),
				approvSubStep[0][0],IPreTestConst.FO_OrgShortName + foUser.getFoUserBeat(),
				foProj.getProgFullName()};
		
		Assert.assertFalse(AmendmentsUtil.isAmendmentCancelIconExists(arrParam, foProj), "Fail: Cancel Amendment Icon Exists!");
		
		
	}
	
	@Test(groups = { "Iter_27", "ProjectsNG" })
	public void testCancelIconExistanceForAmended_ISAP_AfterResubmitNG() throws Exception {
		
		String[] arrParam = {foProj.getProjLetter(), foProj.getProjFullName(),
				paInitialClaim[0][0],IPreTestConst.FO_OrgShortName + foUser.getFoUserBeat(),
				foProj.getProgFullName()};
		
		Assert.assertFalse(AmendmentsUtil.isAmendmentCancelIconExists(arrParam, foProj), "Fail: Cancel Amendment Icon Exists!");
		
		
	}

	private void initializeProgram() throws Exception {
		
		prog = new Program(preFix, portaltype, hasProgramForm, isItNewFOPP, hasPublicationForm);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setProgPostfix(postFix);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
	}

	private void loginApprover(String userBeat) throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover(userBeat);
		
		return;
	}

	private void submitFOProject() throws Exception {
		
		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog.getProgFullName(),foProj.getFoOrgName()), "FAIL: Could not Register to FOPP!");

		foProj.createFOProject();
		
		Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true), "FAIL: Could not Submit Project!");	

		return;
	}
	
	private void assignOfficers() throws Exception {

		foProj.assignOfficers(new String[][] { {
				IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });		
		
		return;
	}
	
	private void submitAward() throws Exception {
		
		Assert.assertTrue(foProj.submitAward("Standard", 2, true),"Failed to Edit Standard Agreement");
		
		return;
	}
	
	private void submitClaim(Integer claimNumber) throws Exception {
		
		foProj.setClaimNumber(claimNumber);
		
		Assert.assertTrue(foProj.submitFromApplicantSubList(paInitialClaim[0][0] + newPASuffix, true), "FAIL: could not submit Claim!");
		
		return;
	}
	
	private void approveSubmissions() throws Exception {
		
		loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0] + newPASuffix, true, "Ready", false, false));

		loginApprover("3");

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0] + newPASuffix, true, "Ready", false, false));

		loginApprover("5");

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0] + newPASuffix, true, "Ready", false, false));		

		return;
	}
	
	private void requestAmendment() throws Exception {
		
		foUser = new FOUsers(1);
		
		String dd = GeneralUtil.setDayofMonth(3);
		
		Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
				foProj.getProjFullName(), paInitialClaim[0][0] + newPASuffix,
				IPreTestConst.Groups[0][1][0], dd,
				"Test Auto-amendment for Re-Exec Previous Amendment",
				"This a Comment", prog.getProgFullName() }, foProj),
				"Failed to issue an Amendment");
		
		foProj.submitFromMyAssignedSubList(foProj.getProjFullName(), "", paInitialClaim[0][0] + newPASuffix, true, "Changed", foProj);
		
		return;
	}
}
