/*
 * Test Case _04 For Story #1905  Step Notifications - Notification handlings
 * Steps:
 *  1. Create Program as Follow
 *  1.1. Submission - Step Due, Notify Applicant Only start 2 days before, Repeat every Day
 *  
 *  1.2. Review 	- Step Due, Notify Rev Group Only start 2 days before, Repeat every Day
 *  
 *  1.3. Approval 	- Step Due, Notify Approval Group Only start 2 days before, Repeat every Day
 *  
 *  1.4. Award 		- Step Due, Notify PO Only start 2 days before, Repeat every Day
 *  
 *  1.5. Post-Award - Step Due, Notify POs Groups and Applicant start 2 days before, Repeat every 1 Day
 *  
 *  1.6. Initial Calim - Step Due, Notify Applicant and PO Group start 3 days before, Repeat every 1 Days
 *  
 *  1.7. Sub Review    - Step Due, Notify PO and Reviewer Groups start 2 days before, Repeat every 1 Day
 *  
 *  1.8. Sub Approval  - Step Due, Notify POs and Approval Groups start 3 days before, Repeat every 2 Dayss
 */

package test_Suite.tests.stories.release_1_5.iter_1_2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;


@GUITest
@Test(singleThreaded = true)
public class S1905_04NG {

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
	String postFix = "-S1905_04";

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

	Program prog;
	FOProject foProj;


	//Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";
    
	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//----------------------------
			log.info("Start of testStepDueNotificationsNG");
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
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods = {"continuePreAwardWorkflow"})
	public void continuePostAwardWorkflow() throws Exception {
		
		try {

			GeneralUtil.switchToFO();
			submitClaim(1);
			
			submitClaim(2);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, false, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

			GeneralUtil.switchToFO();
			
			submitClaim(3);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");

			Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPO();
			
			foProj.assignEvaluators(new String[] { approvSubStep[0][0] + newPASuffix,
					IPreTestConst.Groups[3][1][0] });
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void submitClaim(int claimNum) throws Exception {
		foProj.setClaimNumber(claimNum);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0], true),
				"Fail: Could not Find Sub-Project In FO Submission List!");
		
		return;

	}

	@Test(groups = { "NotificationsNG" })
	public void testStepDueNotificationsNG() throws Exception {
		try {			

			//needed a test case to verify Notification Log	

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
}
