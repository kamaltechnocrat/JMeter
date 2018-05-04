/**
 * 
 */
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
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class I_FO_FOPP_Notifications {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isNewProgram = false;
	
	boolean hasProgramForm = false;
	
	boolean hasPublicationForm = false;
	
	boolean isNewOrg = true;

	String preFix = INotificationsConst.notif_Prefix;

	String postFix = "";
	
	char portaltype = 'F';
	
	Program fndOpp;
	
	FOProject foProj;

	String notifyWhen = "0";

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	boolean notifyApplicantAndAllOfficers[] = { true, true, false, true, false,
			false };
	boolean notifyApplicantAndAssignedOfficer[] = { true, true, true, false,
			false, false };
	boolean notifyAssignedEvaluators[] = { true, false, false, false, true,
			false };
	boolean notifyAllOfficers[] = { true, false, false, true, false, false };

	String awardStepFullName;
	
	String submissionStepFullName;
	
	String reviewStepFullName;
	
	String approvalStepFullName;
	
	String poSubmissionStepFullName;

	// --------------- End of Global Parameters
	// ---------------------------------

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			initializeFundingOpp();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fndOpp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	

	@Test(groups = { "PapNG" })
	public void initialize() throws Exception {
		initializeFundingOpp();
	}
	
	
	@Test(groups = { "PapNG" }, dependsOnMethods = "initialize")
	public void submitFOProject() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(fndOpp);
		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
				.getProgFullName(), foProj.getFoOrgName()),
				"Fail: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="submitFOProject")
	public void assignOfficers() throws Exception {

		GeneralUtil.switchToPO();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="assignOfficers")
	public void testNotificationLogforAutoAssignedEvals()
			throws Exception {

		Assert.assertTrue(ProjActivUtil.openProjActivityNotificationLog(
				foProj.getProjFOFullName(), "Open Projects"),
				"FAIL: Could not Open Notification Log!");

		log.info(ProjActivUtil
				.getHowManyInteriesInNotificationLogList(INotificationsConst.stepEvaluatorAssignedValues[1] + " - "
						+ fndOpp.getProgLetter() + fndOpp.getProgPreFix()
						+ IGeneralConst.reviewQuoCritAuto[0][0] + fndOpp.getProgPostfix()));

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="testNotificationLogforAutoAssignedEvals")
	public void assignStaff() throws Exception {

		foProj.assignEvaluators(new String[] {
				IGeneralConst.approvQuoCritManu[0][0],
				IPreTestConst.Groups[3][1][0] }); 

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="assignStaff")
	public void testNotificationLogForManualAssignedEvals()
			throws Exception {

		Assert.assertTrue(ProjActivUtil.openProjActivityNotificationLog(
				foProj.getProjFOFullName(), "Open Projects"),
				"FAIL: Could not Open Notification Log!");

		log.info(ProjActivUtil
				.getHowManyInteriesInNotificationLogList(INotificationsConst.stepEvaluatorAssignedValues[1] + " - "
						+ fndOpp.getProgLetter() + fndOpp.getProgPreFix()
						+ IGeneralConst.approvQuoCritManu[0][0] + fndOpp.getProgPostfix()));

		return;
	}

	@Test(groups = { "NotificationsNG" },dependsOnMethods="testNotificationLogForManualAssignedEvals")
	public void evaluateSubmissions() throws Exception {

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true,
				"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("1");

		foProj.rejectSubmission(IGeneralConst.approvQuoCritManu[0][0]);

		return;
	}

	private void initializeFundingOpp() throws Exception {

		fndOpp = new Program();

		fndOpp.setProgPreFix(preFix);
		fndOpp.setProgPostfix(postFix);
		fndOpp.setProgPortal(portaltype);
		fndOpp.setProgForm(hasProgramForm);
		fndOpp.setPublicationForm(hasPublicationForm);
		fndOpp.setNewProgram(isNewProgram);
		fndOpp.setProgIdent(fndOpp.getProgPreFix()
				+ IFoppConst.fundingOpp_Name);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		fndOpp.initProgram();

		fndOpp.setProgAdmin(progAdmin);
		fndOpp.setProgOfficers(progOfficers);
		fndOpp.setStepOfficer(IPreTestConst.AppGroupName);

		return;
	}

}
