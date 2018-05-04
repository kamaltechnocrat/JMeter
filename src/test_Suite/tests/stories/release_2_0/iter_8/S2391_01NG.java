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

/*
 * Strory #2391. Provide Localization for PO Notifications
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2391_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = true;

	boolean programForm = false;

	boolean newOrg = true;

	String preFix = INotificationsConst.notif_Prefix;

	char portaltype = 'F';

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String reviewersGrp[] = { IPreTestConst.Groups[2][0][0] };

	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	String postFix = "-Locale";

	private static final String newPASuffix = "-pa";

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

	String reviewStep[][] = IGeneralConst.reviewQuoCritManu;

	String approvStep[][] = IGeneralConst.approvQuoCritAuto;

	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;

	String postAwardStep[][] = IGeneralConst.postAwardCrit;

	String paInitialClaim[][] = IGeneralConst.initialClaim;

	String reviewSubStep[][] = IGeneralConst.reviewQuoCritAuto;

	String approvSubStep[][] = IGeneralConst.approvQuoCritManu;

	Program prog;

	FOProject foProj;

	// -------------- End of Params ----------------

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
		
			prog = new Program(preFix, portaltype, programForm, newProgram, false);
	
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

			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] },
					{ submissionStep[0][0] } });

			prog.manageStepNotification(INotificationsConst.stepEntryValues,
					repeatNotifyPO, new String[] {}, "0", "1");

			prog.addStep(approvStep);
			prog
					.manageStep(new String[][] { approversGrp,
							{ approvStep[0][0] } });

			prog.setStepOfficer(IPreTestConst.Groups[6][0][0]);

			prog.addStep(paAwardStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[6][0][0] },
					{ paAwardStep[0][0] } });

			prog.setStepOfficer(IPreTestConst.Groups[0][0][0]);

			prog.addPAStep(postAwardStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] },
					{ postAwardStep[0][0] } });
			prog.manageStepNotification(INotificationsConst.stepEntryValues,
					repeatNotifyBoth, new String[] { progOfficers[1] }, "0",
					"2");

			prog.addSubSteps(paInitialClaim);
			prog
					.manageSubSteps(new String[][] { { IPreTestConst.Groups[0][0][0] } });
			prog.manageSubStepNotification(INotificationsConst.stepEntryValues,
					repeatNotifyPO, new String[] {}, "1", "1");

			prog.addSubSteps(approvSubStep);
			prog.manageSubSteps(new String[][] { approversGrp });
			prog.manageSubStepNotification(INotificationsConst.stepEntryValues,
					repeatNotifyPO, new String[] { approversGrp[0] }, "2", "1");

			prog.activateProgram("Active");
		
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"initializeAndCreateFOPP"})
	public void registerAndSubmitFOProject() throws Exception {

		try {

			GeneralUtil.switchToFO();
	
			foProj = new FOProject(prog);
	
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
	
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
					.getProgFullName(), foProj.getFoOrgName()),
					"FAIL: Could not find FOPP to register to!");
	
			foProj.createFOProject();
	
			Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true),
					"FAIL: Could not Submit Project");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"registerAndSubmitFOProject"})
	public void assignOfficersAndApprove() throws Exception {

		try {

			GeneralUtil.switchToPO();
	
			foProj.assignOfficers(new String[][] {
					{ IPreTestConst.Groups[0][0][0],
							IPreTestConst.Groups[0][1][0] },
					{ IPreTestConst.Groups[6][0][0],
							IPreTestConst.Groups[6][1][0] } });
	
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
	
			Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"assignOfficersAndApprove"})
	public void submitAward() throws Exception {

		try {

			GeneralUtil.switchToPOWithProjOfficer("1");
	
			foProj.submitAward("Standard", 2, true);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@Test(groups = { "NotificationsNG" }, dependsOnMethods={"submitAward"})
	public void continuePostAward() throws Exception {

		try {
		
			submitClaimAndAssignEvaluators(1);
			
			submitClaimAndAssignEvaluators(2);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
		
	}
	
	private void submitClaimAndAssignEvaluators(Integer claimNum) throws Exception {

		try {
		
			GeneralUtil.switchToFO();
			
			foProj.setClaimNumber(claimNum);
	
			Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0], true),
					"Fail: Could not Find Sub-Project In FO Submission List!");
	
			GeneralUtil.switchToPO();
	
			foProj.assignEvaluators(new String[] {
					approvSubStep[0][0] + newPASuffix,
					IPreTestConst.Groups[3][1][0] });

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
		
	}

//	private void createFundingOpp() throws Exception {
//
//		try {			
//		
//			prog = new Program(preFix, portaltype, programForm, newProgram, false);
//	
//			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
//	
//			prog.setProgPostfix(postFix);
//	
//			prog.initProgram();
//	
//			prog.setEndDate(GeneralUtil.setDayofMonth(10));
//	
//			if (newProgram) {
//	
//				prog.setProgAdmin(progAdmin);
//	
//				prog.setProgOfficers(progOfficers);
//	
//				ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
//	
//				prog.createProgram();
//	
//				// Setting the Steps End Date 5 Days from Today
//	
//				prog.setEndDate(GeneralUtil.setDayofMonth(5));
//	
//				prog.addStep(submissionStep);
//	
//				prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] },
//						{ submissionStep[0][0] } });
//	
//				prog.manageStepNotification(INotificationsConst.stepEntryValues,
//						repeatNotifyPO, new String[] {}, "0", "1");
//	
//				prog.addStep(approvStep);
//				prog
//						.manageStep(new String[][] { approversGrp,
//								{ approvStep[0][0] } });
//	
//				prog.setStepOfficer(IPreTestConst.Groups[6][0][0]);
//	
//				prog.addStep(paAwardStep);
//				prog.manageStep(new String[][] { { IPreTestConst.Groups[6][0][0] },
//						{ paAwardStep[0][0] } });
//	
//				prog.setStepOfficer(IPreTestConst.Groups[0][0][0]);
//	
//				prog.addPAStep(postAwardStep);
//				prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] },
//						{ postAwardStep[0][0] } });
//				prog.manageStepNotification(INotificationsConst.stepEntryValues,
//						repeatNotifyBoth, new String[] { progOfficers[1] }, "0",
//						"2");
//	
//				prog.addSubSteps(paInitialClaim);
//				prog
//						.manageSubSteps(new String[][] { { IPreTestConst.Groups[0][0][0] } });
//				prog.manageSubStepNotification(INotificationsConst.stepEntryValues,
//						repeatNotifyPO, new String[] {}, "1", "1");
//	
//				prog.addSubSteps(approvSubStep);
//				prog.manageSubSteps(new String[][] { approversGrp });
//				prog.manageSubStepNotification(INotificationsConst.stepEntryValues,
//						repeatNotifyPO, new String[] { approversGrp[0] }, "2", "1");
//	
//				prog.activateProgram("Active");
//		}
//
//		} catch (Exception e) {
//			log.error("Unexpected Exception", e);
//
//			throw new RuntimeException("Unexpected Exception", e);
//
//		}
//	}

}
