/*
 * Test Case _02 For Story #1905  Step Notifications - Notification handlings
 * Steps:
 *  1. Create Program as Follow
 *  1.1. Submission - Amendment, Notify Applicant send immed., Repeat every 2 days
 *  				- Amendment Due, Notify Applicant 3 day before, repeat daily.
 *  
 *  1.2. Review 	- Amendment, Notify Applicant, POs and Rev Groups one day after, Repeat every 2 Days
 *  			    - Amendment Due, Notify Rev and POs Groups 3 Days before is Due, Reapeat every Day.
 *  
 *  1.3. Approval 	- Amendment, Notify PO 2 Days After, Repeat Every 2 days
 *  				- Amendment Due, Notify PO and Approv Group 2 Days before , repeat every Day.
 *  
 *  1.4. Award 		- Amendment, Notify Applicant and POs Group after 2 Days, repeat every 2 day
 *  			    - Amendment Due, Notify PO and POs Group 2 Days Before is Due, Repeat every Day.
 *  
 *  1.5. Post-Award - Amendment, Notify POs Group after 1 Day, Repeat Every 2 Days
 *  			 	- Amendment Due, Notify POs Group, 3 Days Before, Repeat every Day.
 *  
 *  1.6. Initial Calim - Amendment, Notify Applicant send Immed, Repeat every day
 *  				   - Amendment Due, Notify Applicant and PO 2 days Before, Repeat Every 1 Day.
 *  
 *  1.7. Sub Review    - Amendment, Notify Applicant, PO and Rev Group one day after, Repeat every 2 Days
 *  			  	   - Amendment Due, Notify Rev and POs Groups 3 Days before is Due, Repeat every 2 Days
 *  
 *  1.8. Sub Approval  - Amendment, Notify PO Immed, Repeat Every 2 days
 *  				   - Amendment Due, Notify PO and Approv Group 3 Days Before, repeat every Day.
 */

package test_Suite.tests.stories.release_1_5.iter_1_2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.preTest.*;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;


@GUITest
@Test(singleThreaded = true)
public class S1905_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;

	String preFix = INotificationsConst.notif_Prefix;
	char portaltype = 'F';
	String postFix = "-S1905_02";
	
	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	String reviewersGrp[] = { IPreTestConst.Groups[2][0][0] };
	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	/*
	 * Notifications setting used, this for references
	 * 
	 * boolean repeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[0];
	 * boolean repeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[1];
	 * boolean repeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[2];
	 * boolean repeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[3];
	 * boolean noRepeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[4];
	 * boolean noRepeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[5];
	 * boolean noRepeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[6];
	 * boolean noRepeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[7];
	 * 
	 */

	

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String reviewStep[][] = IGeneralConst.reviewQuoCritManu;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String reviewSubStep[][] = IGeneralConst.reviewQuoCritAuto;
	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	String subProject;
	
	Program prog;
	FOProject foProj;

	//Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";
    
	// ---------------------------- End of Params

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//------------------------------
			
			initializeFOPP();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	private void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setProgPostfix(postFix);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);
		
		prog.setProgOfficers(progOfficers);
	}

	@Test(groups = { "NotificationsNG" })
	public void submitFOProject() throws Exception {
		try {
			
			GeneralUtil.switchToFO();

			foProj = new FOProject(prog);
			
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
					.getProgFullName(), foProj.getFoOrgName()), "Fail: No "
					+ foProj.getFoOrgName());

			foProj.createFOProject();

			Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods = {"submitFOProject"})
	public void assignOfficersAndEvaluators() throws Exception {
		try {			
			
			GeneralUtil.switchToPO();
			
			foProj.assignOfficers(new String[][] {
					{ IPreTestConst.Groups[0][0][0],
							IPreTestConst.Groups[0][1][0] },
					{ IPreTestConst.Groups[6][0][0],
							IPreTestConst.Groups[6][1][0] } });

			foProj.assignEvaluators(new String[] { reviewStep[0][0],
					IPreTestConst.Groups[2][1][0] });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods = {"assignOfficersAndEvaluators"})
	public void continuePreAwardWorkflow() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(reviewStep[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

			GeneralUtil.switchToPOWithProjOfficer("1");

			foProj.submitAward("Standard", 3, true);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void submitClaim(int claimNum) throws Exception {
		foProj.setClaimNumber(claimNum);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0], true),
				"Fail: Could not Find Sub-Project In FO Submission List!");

	}
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods = {"continuePreAwardWorkflow"})
	public void continuePostAwardWorkflow() throws Exception {
		
		try {

			GeneralUtil.switchToFO();

			// Submit First Claim
			submitClaim(1);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			// Amend Initial Claim
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			String dd = GeneralUtil.setDayofMonth(3);
			subProject = foProj.getProjFullName() + " - Claim "
					+ foProj.getClaimNumber().toString();
			AmendmentsUtil.issueAmendment(new String[] { subProject,
					paInitialClaim[0][0] + newPASuffix,
					"/" + IPreTestConst.FrontUsers[1][2] + "1" + "/", dd,
					"Test Notification On Initial Claim", "This a Comment" }, foProj);

			// ----------------------------------------------------------------------

			GeneralUtil.switchToFO();
			// Submit Second Claim
			submitClaim(2);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			// Amend Review Step

			subProject = foProj.getProjFullName() + " - Claim "
					+ foProj.getClaimNumber().toString();
			AmendmentsUtil.issueAmendment(new String[] { subProject, reviewSubStep[1][0] + newPASuffix, IPreTestConst.Groups[2][1][0],
							GeneralUtil.setDayofMonth(3), "Test Notification On Post-Award Review","This a Comment" }, foProj);

			// -------------------------------------------------------------------
			GeneralUtil.switchToFO();
			// Submit Third Claim
			submitClaim(3);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();			
			
			foProj.assignEvaluators(new String[] { approvSubStep[0][0] + newPASuffix, IPreTestConst.Groups[3][1][0] });

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(approvSubStep[0][0] + newPASuffix, true, "Ready", false, false));

			// Amend Approval Step
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			subProject = foProj.getProjFullName() + " - Claim "	+ foProj.getClaimNumber().toString();
			AmendmentsUtil.issueAmendment(new String[] { subProject, approvSubStep[1][0] + newPASuffix, IPreTestConst.Groups[3][1][0],
							GeneralUtil.setDayofMonth(3), "Test Notification On Post-Award Review",	"This a Comment" }, foProj);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationsNG" })
	public void testAmindingStepNotificationsNG() throws Exception {
		try {			
			
			//needed a test case to verify Notification Log

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}

}
