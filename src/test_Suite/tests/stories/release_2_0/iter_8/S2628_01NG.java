/**
 * Case 01 for Story #2628. Ability to select specific registrant(s) from the applicant as notifications target
 * 
 * Prepare:
 * 1.Setup a Program
 * 2. Submit Project from FO
 * 3. Assign Officers.
 * 
 * Steps:
 * 1. Open Project List
 * 2. Select open the Project Activity
 * 3. Fill out Activity Details
 * 4. Fill out a Activity Notification
 * 5. Select FO registrant to recieve the Notification
 * 
 * Test:
 * Wait for email.
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IFoppConst;
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
public class S2628_01NG {

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
	
	String postFix = "-S2628_01";
	
	boolean newProgram = false;
	boolean programForm = false;
	boolean publicationForm = false;
	boolean newOrg = true;
	
    String progAdmin[] = {IPreTestConst.adminGroup};
    String progOfficers[] = {IPreTestConst.Groups[0][0][0], IPreTestConst.Groups[6][0][0]};
    
    String NotifRecipients[] = {};
    
//  Steps
    String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
    String approvStep[][] = {{"Approval-A", "/approvalStep/", "Approval", "true", "Optional (No)"}, {"/Approval Form/", "Quorum", "1", "true"}};
    String reviewStep[][] = IGeneralConst.gnrl_ReviewA;
    String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
    String postAwardStep[][] = IGeneralConst.postAwardCrit;
    String paInitialClaim[][] = IGeneralConst.initialClaim;
    String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	
	
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
	
//	@Test(groups = { "NotificationsNG" })
//	public void testSelectingRegistrantAsNotificationTargetNG() throws Exception {
//		
//		try {
//
//	        TODO: rename the class to the above method name
//		} catch (Exception e) {
//			log.error("Unexpected Exception", e);
//			
//			throw new RuntimeException("Unexpected Exception", e);
//			
//		}   
//
//		
//	}

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
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"registerAndSubmitFOProject"})
	public void initializeProjectActivity() throws Exception {
		
		projActivity = new ProjectActivity();		
		projActivity.setProjectName(foProj.getProjFullName());		
		projActivity.setApplicantName(foProj.getFoOrgName());		
		projActivity.setProgramName(prog.getProgFullName());		
		projActivity.setActivityType(IProjActivConst.projActivity_Details_Type_Email);		
		projActivity.setActivityPurpose(IProjActivConst.projActivity_Details_Type_Email);		
		projActivity.setActivityDate(GeneralUtil.getTodayDate());		
		projActivity.setActivityDueDate(GeneralUtil.setDayofMonth(1));		
		projActivity.setActivityStatus(IProjActivConst.projActivity_Details_Status_Caution);		
		projActivity.setActivityDescription(IProjActivConst.projActivity_Details_Status_Caution);		
		projActivity.setActivityCompleted(false);
		
		//Setup Notification		
		projActivity.setActivityNotifActive(true);		
		projActivity.setActivityNotifDaysBefore("1");		
		projActivity.setActivityNotifDaysUntil("1");		
		projActivity.setActivityNotifRepeat(true);		
		projActivity.setActivityNotifMessageLocale("/English/");			
		projActivity.setActivityNotifRecipients(setupRecipient(1));		
		projActivity.setActivityNotifMessageSubjet(IProjActivConst.projActivity_Notif_MessageSubject);		
		projActivity.setActivityNotifMessageBody(IProjActivConst.projActivity_Notif_MessageBody);		
		projActivity.setActivityNotifExternalRecipients("");		
		
	}
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"initializeProjectActivity"})
	public void fillActivityDetails() throws Exception {
		
		Assert.assertTrue(ProjActivUtil.openProjectActivity(projActivity.getProjectName(), IFiltersConst.openProjView), "Fail: Opening Activity for Project " + projActivity.getProjectName());
		
		Assert.assertTrue(ProjActivUtil.openNewActivity(), "Fail: Openning New Activity for Project " + projActivity.getProjectName());
		
		Assert.assertTrue(ProjActivUtil.addProjectActivityDetails(projActivity), "Fail: Could not save Activity Details for " + projActivity.getProjectName());
		
	}
	
	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"fillActivityDetails"})
	public void fillActivityNotif() throws Exception {
		
		Assert.assertTrue(ProjActivUtil.openActivity(projActivity.getActivityPurpose()), "Fail: To open Activity");
		
		Assert.assertTrue(ProjActivUtil.addProjectActivityNotification(projActivity), "Fail: To Fill Notifications");

	}
	
	private String[] setupRecipient(int counts) throws Exception {
		String recipients[] = new String[counts];
		
		//recipients = new String[counts];
		
		for(int i=1; i<=counts; i++)
		{
			foUser = new FOUsers(i);
			recipients[(i-1)] = foUser.getFoRegistrantFullId();
		}
		
		return recipients;
	}
	
	private void initializeProgram() throws Exception {
		
		prog = new Program();		
		prog.setProgPreFix("-Gnrl-");  //(INotificationsConst.notif_Prefix);		
		prog.setProgPostfix(""); //(postFix);		
		prog.setNewProgram(newProgram);		
		prog.setProgPortal('F');		
		prog.setPublicationForm(publicationForm);		
		prog.setProgForm(programForm);		
		prog.setProgIdent(prog.getProgPreFix() + IFoppConst.fundingOpp_Name);		
		prog.setProgAdmin(progAdmin);    	
        prog.setProgOfficers(progOfficers);        
        prog.setProgramOfficer(IPreTestConst.ProgPOfficer);        
        prog.setStepOfficer(IPreTestConst.AppGroupName);		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);		
		prog.setEndDate(GeneralUtil.setDayofMonth(10));		
		prog.initProgram();
		
	}

}
