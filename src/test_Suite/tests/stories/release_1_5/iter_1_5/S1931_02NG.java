/*
 * Test Case _02 For Story #1931  Step Notification - Step entry from a paused step
 * Steps:
 *  1. Create Program as Follow
 *  1.1. Submission
 *  
 *  1.2. Approval - Step Entry from a Paused step, Notify Approvers and PO Groups 3 Days before step start, Repeat Every day
 *  		Step Paused for 2 Days, this should result to Notify Immed.
 *  1.3. Award
 *    
 *  
 */
package test_Suite.tests.stories.release_1_5.iter_1_5;

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
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;

@GUITest
@Test(singleThreaded = true)
public class S1931_02NG {

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

	String approversGrp[] = { IPreTestConst.Groups[3][0][0] };

	String postFix = "-S1931NG_02";

	boolean repeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[0];

	boolean repeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[1];

	boolean repeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[2];

	boolean repeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[3];

	// Steps
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;

	String approvStep[][] = IGeneralConst.approvQuoCritAuto;

	String paAwardStep[][] = IGeneralConst.pa_AwardCrit;

	boolean noRepeatNotifyBoth[] = INotificationsConst.stepAllCheckBoxValues[4];

	boolean noRepeatNotifyApp[] = INotificationsConst.stepAllCheckBoxValues[5];

	boolean noRepeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[6];

	boolean noRepeatNotifyNon[] = INotificationsConst.stepAllCheckBoxValues[7];

	Program prog;

	FOProject foProj;

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

			createFundingOpp();
			
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

	@Test(groups = { "NotificationsNG" })
	public void testStepEntryFromAPausedStepNotificationImmediatelyNG() throws Exception {
		try {
			
			/*************************************
			 * Continue workflow
			 *************************************/
			
			GeneralUtil.switchToFO();
			
			submitFOProject();
			
			GeneralUtil.switchToPO();

			foProj.assignOfficers(new String[][] {
					{ IPreTestConst.Groups[0][0][0],
							IPreTestConst.Groups[0][1][0] },
					{ IPreTestConst.Groups[6][0][0],
							IPreTestConst.Groups[6][1][0] } });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 

	}

	private void createFundingOpp() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		prog.setProgPostfix(postFix);
		prog.initProgram();
		
		if (newProgram) {
			prog.setProgAdmin(progAdmin);
			prog.setProgOfficers(progOfficers);
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			prog.createProgram();

			prog.addStep(submissionStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[0][0][0] }});
			
			prog.setStepOfficer(IPreTestConst.Groups[6][0][0]);
			
			// Setting Approval Step start Date 2 Days from Today
			prog.setStartDate(GeneralUtil.setDayofMonth(2));

			prog.addStep(approvStep);
			prog
					.manageStep(new String[][] { approversGrp});
			prog.manageStepNotification(
					INotificationsConst.stepEntryFromPausedValues,
					repeatNotifyPO, new String[] { approversGrp[0] }, "3", "1");

			prog.addStep(paAwardStep);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[6][0][0] }});
			
			prog.setStepOfficer(IPreTestConst.Groups[0][0][0]);
			
			prog.activateProgram("Active");
		}

	}

	private void submitFOProject() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(foProj
				.getProgFullName(), foProj.getFoOrgName()), "Fail: No "
				+ foProj.getFoOrgName());
		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(submissionStep[0][0], true),
				"Fail: Could not Submit Project!");

	}
}
