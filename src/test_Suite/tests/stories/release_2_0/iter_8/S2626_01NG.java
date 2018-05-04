/**
 * Case 01 for Story #2626. Recurring Notifications Associated with Activity Records
 * 
 * Prepare:
 * 1.Setup a Program
 * 2. Submit Project from FO
 * 3. Assign Officers.
 * 
 * 	Steps:
 * 1. Open Project List
 * 2. Select open the Project Activity
 * 3. Fill out Activity Details
 * 
 * Tests:
 * 1. Fill out a Activity Notification to be sent Immediately
 * 2. Fill out a Activity Notification to be sent One Day before Due Date (Due Date is Tomorrow).
 * 3. Fill out a Activity Notification to be sent 2 days before Due Date(Due date is after Tomorrow).
 * 4. Fill out a Activity Notification to be repeated until completed (Check activity as Complete), no Notification.
 * 5. Select FO registrant to receive the Notification
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.workflow.IProjActivConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.lib.workflow.ProjectActivity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 * 
 */
@Test(singleThreaded = true)
public class S2626_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Program prog;
	ProgramSteps progStep;
	FOProject foProj;
	ProjectActivity projActivity;
	FOUsers foUser;

	String postFix = "";//"-S2626_01";

	boolean newProgram = false;
	boolean programForm = false;
	boolean publicationForm = false;
	boolean newOrg = true;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String NotifRecipients[] = {};

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String approvStep[][] = {
			{ "Approval-A", "/approvalStep/", "Approval", "true",
					"Optional (No)" },
			{ "/Approval Form/", "Quorum", "1", "true" } };
	String reviewStep[][] = IGeneralConst.gnrl_ReviewA;
	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
	String postAwardStep[][] = IGeneralConst.postAwardCrit;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	String notifyImmediateParams[] = {
			IProjActivConst.projActivity_Details_Type_Email,
			IProjActivConst.projActivity_Details_Type_Email
					+ "-Immediate", "0", "0",
			IProjActivConst.projActivity_Details_Status_Caution, "", "0",
			"" };

	String notify1DayBeforeParams[] = {
			IProjActivConst.projActivity_Details_Type_Email,
			IProjActivConst.projActivity_Details_Type_Email
					+ "-One Day Before", "0", "1",
			IProjActivConst.projActivity_Details_Status_Caution, "", "1",
			"" };

	String notify2DayBeforeParams[] = {
			IProjActivConst.projActivity_Details_Type_Email,
			IProjActivConst.projActivity_Details_Type_Email
					+ "-Two Day Before", "0", "2",
			IProjActivConst.projActivity_Details_Status_Caution, "", "2",
			"" };

	String notifyCompletedParams[] = {
			IProjActivConst.projActivity_Details_Type_Email,
			IProjActivConst.projActivity_Details_Type_Email
					+ "-Completed", "0", "0",
			IProjActivConst.projActivity_Details_Status_Caution, "", "0",
			"1" };

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// ----------------------------------

		initializeProgram();
		
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		prog = null;
		progStep = null;
		foProj = null;
		projActivity = null;
		foUser = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "NotificationsNG" })
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
	public void assignOfficers() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"assignOfficers"})
	public void testActivityNotificationsImmediateEmailNG() throws Exception {
		try {
			
			// Test case 1
			initializeProjectActivity(notifyImmediateParams, new boolean[] {
					false, true, false });
			fillActivityDetails();
			fillActivityNotif();
			
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="testActivityNotificationsImmediateEmailNG")
	public void testActivityNotificationsEmailOneDayBeforeNG() throws Exception {
		try {
			
			// Test case 2
			initializeProjectActivity(notify1DayBeforeParams, new boolean[] {
					false, true, false });
			fillActivityDetails();
			fillActivityNotif();
			
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="testActivityNotificationsEmailOneDayBeforeNG")
	public void testActivityNotificationsEmailTwoDayBeforeNG() throws Exception {
		try {
			
			// Test case 3
			initializeProjectActivity(notify2DayBeforeParams, new boolean[] {
					false, true, false });
			fillActivityDetails();
			fillActivityNotif();
			
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="testActivityNotificationsEmailTwoDayBeforeNG")
	public void testActivityComlpetedNotificationsEmailNG() throws Exception {

		try {

			// Test case 4
			initializeProjectActivity(notifyCompletedParams, new boolean[] {
					true, true, true });
			fillActivityDetails();
			fillActivityNotif();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	private void initializeProgram() throws Exception {

		prog = new Program();
		prog.setProgPreFix(IGeneralConst.gnrl_ProgPrefix);
		prog.setProgPostfix(postFix);
		prog.setNewProgram(newProgram);
		prog.setProgPortal('F');
		prog.setPublicationForm(publicationForm);
		prog.setProgForm(programForm);
		prog.setProgIdent(prog.getProgPreFix() + IGeneralConst.gnrl_ProgName);
		prog.setProgAdmin(progAdmin);
		prog.setProgOfficers(progOfficers);
		prog.setProgramOfficer(IPreTestConst.ProgPOfficer);
		prog.setStepOfficer(IPreTestConst.AppGroupName);
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		prog.setEndDate(GeneralUtil.setDayofMonth(10));
		prog.initProgram();

	}

	private void initializeProjectActivity(String[] params, boolean[] bolParams)
			throws Exception {

		projActivity = new ProjectActivity();
		projActivity.setProjectName(foProj.getProjFullName());
		projActivity.setApplicantName(foProj.getFoOrgName());
		projActivity.setProgramName(prog.getProgFullName());

		projActivity.setActivityType(params[0]);
		projActivity.setActivityPurpose(params[1]);
		projActivity.setActivityDate(GeneralUtil.setDayofMonth((Integer
				.parseInt(params[2]))));
		projActivity.setActivityDueDate(GeneralUtil.setDayofMonth(Integer
				.parseInt(params[3])));
		projActivity.setActivityStatus(params[4]);
		projActivity.setActivityDescription(params[5]);
		projActivity.setActivityCompleted(bolParams[0]);

		// Setup Notification
		projActivity.setActivityNotifActive(bolParams[1]);
		projActivity.setActivityNotifDaysBefore(params[6]);
		projActivity.setActivityNotifDaysUntil(params[7]);
		projActivity.setActivityNotifRepeat(bolParams[2]);

		projActivity.setActivityNotifMessageLocale("/English/");
		projActivity.setActivityNotifRecipients(setupRecipient(1));
		projActivity.setActivityNotifMessageSubjet(params[1]);
		projActivity.setActivityNotifMessageBody(params[1]);
		projActivity.setActivityNotifExternalRecipients("");

	}

	private void fillActivityDetails() throws Exception {

		Assert.assertTrue(ProjActivUtil.openProjectActivity(projActivity
				.getProjectName(), IFiltersConst.openProjView),
				"Fail: Opening Activity for Project "
						+ projActivity.getProjectName());
		Assert.assertTrue(ProjActivUtil.openNewActivity(),
				"Fail: Openning New Activity for Project "
						+ projActivity.getProjectName());
		Assert.assertTrue(ProjActivUtil
				.addProjectActivityDetails(projActivity),
				"Fail: Could not save Activity Details for "
						+ projActivity.getProjectName());
	}

	private void fillActivityNotif() throws Exception {

		Assert.assertTrue(ProjActivUtil.openActivity(projActivity
				.getActivityPurpose()), "Fail: To open Activity");
		Assert.assertTrue(ProjActivUtil
				.addProjectActivityNotification(projActivity),
				"Fail: To Fill Notifications");
	}

	private String[] setupRecipient(int counts) throws Exception {
		String recipients[] = new String[counts];

		// recipients = new String[counts];

		for (int i = 1; i <= counts; i++) {
			foUser = new FOUsers(i);
			recipients[(i - 1)] = foUser.getFoRegistrantFullId();
		}

		return recipients;
	}
}
