/**
 * Test Case 01 for Story 2360 Add IPAS notification
 * 
 * Setup:
 * 1. Create Program that includes Initial Claim
 * 2. Add IPAS Notification to Initial Claim
 * 					- Step Entry Notify Applicant and PO Immed, Repeat every Day
 *  				- Step Exit Notify PO Immed, No Repeatition.
 * 
 * Steps
 * This is a happy path Test
 * 1. Reach the Award Configure Submission Schedule as Follow:
 * 			- Claim 1 Start Today ends after 3 days.
 * 			- Claim 2 Start Tomorrow Ends After One Day.
 * 			- Claim 3 Start Yesterday Ends Tomorrow.
 * 
 * Result:
 * Email Notifications for Claim 1 and Claim 3
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.*;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;

/**
 * @author mshakshouki
 * 
 *//**
 * @author mshakshouki
*
*/

@GUITest
@Test(singleThreaded = true)
public class S2360_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isItNewFOPP = true;
	boolean hasProgramForm = false;
	boolean hasPublicationForm = false;

	boolean isItNewOrg = true;

	String preFix = INotificationsConst.notif_PA_Prefix;

	char portaltype = 'F';

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	String reviewersGrp[] = { IPreTestConst.Groups[2][0][0] };
	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	String postFix = "-S2360_1";

	boolean repeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[0];
	boolean repeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[1];
	boolean repeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[2];
	boolean repeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[3];

	boolean noRepeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[4];
	boolean noRepeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[5];
	boolean noRepeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[6];
	boolean noRepeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[7];

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	Program prog;

	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	// private static final String newPASuffix = "-pa";
	// ---------------------------- End of Params
	// ---------------------------------

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// ----------------------------------
		
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "NotificationsNG" })
	public void initializeAndCreateFOPP() throws Exception {
		try {

			prog = new Program(preFix, portaltype, hasProgramForm, isItNewFOPP,
					hasPublicationForm);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			prog.initProgram();

			prog.setEndDate(GeneralUtil.setDayofMonth(10));

			prog.setProgAdmin(progAdmin);

			prog.setProgOfficers(progOfficers);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.createProgram();

			// Setting the Steps End Date 5 Days from Today

			prog.setEndDate(GeneralUtil.setDayofMonth(5));

			prog.addStep(submissionStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });

			prog.addStep(approvStep);
			prog.manageStep(new String[][] { IPreTestConst.Groups[0][0] });

			prog.addStep(paAwardStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });

			prog.setStepOfficer(IPreTestConst.Groups[0][0][0]);

			prog.addPAStep(postAwardStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });

			prog.addSubSteps(paInitialClaim);
			prog.manageSubSteps(new String[][] { { IPreTestConst.Groups[0][0][0] } });
			prog.manageSubStepNotification(
					INotificationsConst.stepPASubmissionEntry, repeatNotifyApp,
					new String[] {}, "", "1");
			prog.manageSubStepNotification(
					INotificationsConst.stepPASubmissionExit, noRepeatNotifyPO,
					new String[] {}, "", "");

			prog.addSubSteps(approvSubStep);
			prog.manageSubSteps(new String[][] { IPreTestConst.Groups[0][0] });

			prog.activateProgram("Active");
		
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"initializeAndCreateFOPP"})
	public void registerAndSubmitFOProject() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not find FOPP to register to!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true),
				"FAIL: Could not Submit Project");
	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"registerAndSubmitFOProject"})
	public void assignOfficersAndApprove() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"assignOfficersAndApprove"})
	public void submitAward() throws Exception {

		Assert.assertTrue(foProj.submitAward("Standard", 3, false),
				"FAIL: Could not Submit Award!");

		foProj.setClaimNumber(2);

		String dd = GeneralUtil.setDayofMonth(-1);

		Assert.assertTrue(foProj.changeClaimStartDate(dd, false),
				"FAIL: Could not Change Start Date");

		foProj.setClaimNumber(3);

		dd = GeneralUtil.setDayofMonth(1);

		Assert.assertTrue(foProj.changeClaimStartDate(dd, true),
				"FAIL: Could not Change Start Date");

	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"submitAward"})
	public void submitClaim() throws Exception {

		GeneralUtil.switchToFO();

		foProj.setClaimNumber(1);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0], true),
				"FAIL: Could not Submit Initial Claim!");
	}

//	private void initializeFOPP() throws Exception {
//
//		prog = new Program(preFix, portaltype, hasProgramForm, isItNewFOPP,
//				hasPublicationForm);
//
//		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
//
//		prog.setProgPostfix(postFix);
//
//		prog.initProgram();
//	}
//
//	private void createNewFOPP() throws Exception {
//
//		prog.setEndDate(GeneralUtil.setDayofMonth(10));
//
//		prog.setProgAdmin(progAdmin);
//
//		prog.setProgOfficers(progOfficers);
//
//		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
//
//		prog.createProgram();
//
//		// Setting the Steps End Date 5 Days from Today
//
//		prog.setEndDate(GeneralUtil.setDayofMonth(5));
//
//		prog.addStep(submissionStep);
//		prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });
//
//		prog.addStep(approvStep);
//		prog.manageStep(new String[][] { IPreTestConst.Groups[0][0] });
//
//		prog.addStep(paAwardStep);
//		prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });
//
//		prog.setStepOfficer(IPreTestConst.Groups[0][0][0]);
//
//		prog.addPAStep(postAwardStep);
//		prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] } });
//
//		prog.addSubSteps(paInitialClaim);
//		prog.manageSubSteps(new String[][] { { IPreTestConst.Groups[0][0][0] } });
//		prog.manageSubStepNotification(
//				INotificationsConst.stepPASubmissionEntry, repeatNotifyApp,
//				new String[] {}, "", "1");
//		prog.manageSubStepNotification(
//				INotificationsConst.stepPASubmissionExit, noRepeatNotifyPO,
//				new String[] {}, "", "");
//
//		prog.addSubSteps(approvSubStep);
//		prog.manageSubSteps(new String[][] { IPreTestConst.Groups[0][0] });
//
//		prog.activateProgram("Active");
//
//	}
}
