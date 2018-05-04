package test_Suite.tests.post_Award;

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
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class A_PA_GnrlProgramNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = true;
	
	boolean programForm = false;
	
	boolean newOrg = true;

	String preFix = IGeneralConst.pa_ProgPrefix;
	
	char portaltype = 'P';

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };
	
	Program prog;

	// Any release prior to 2.0.2.0 assign empty string
	private String newPASuffix = "-pa";

	// ------------------- End of Global Parameters
	// ----------------------------------

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		prog = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void createNewFOPP() throws Exception {
		try {
			initializeFundingOpp();
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.createProgram();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="createNewFOPP")
	public void addPre_AwardSteps() throws Exception {
		try {

			prog.addStep(IGeneralConst.gnrl_SubmissionA);

			prog.manageStep(new String[][] { { progOfficers[0] },
					{ IGeneralConst.gnrl_SubmissionA[0][0] } });

			prog.addStep(IGeneralConst.approvQuoCritAuto);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[3][0][0] },
					{ IGeneralConst.approvQuoCritAuto[0][0] } });

			prog.addStep(IGeneralConst.pa_AwardCrit);
			prog.manageStep(new String[][] { { progOfficers[0] },
					{ IGeneralConst.pa_AwardCrit[0][0] } });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="addPre_AwardSteps")
	public void addPost_AwardSteps() throws Exception {
		try {

			prog.setStepOfficer(progOfficers[1]);

			prog.addPAStep(IGeneralConst.postAwardCrit);
			prog.manageStep(new String[][] { { IPreTestConst.Groups[6][0][0] },
					{ IGeneralConst.postAwardCrit[0][0] } });

			prog.addSubSteps(IGeneralConst.initialClaim);
			prog.manageSubSteps(new String[][] {
					{ IPreTestConst.Groups[6][0][0] },
					{ IGeneralConst.initialClaim[0][0] + newPASuffix,
							IGeneralConst.approvQuoCritAuto[0][0] } });

			prog.addSubSteps(IGeneralConst.reviewQuoCritManu);
			prog.manageSubSteps(new String[][] { { IPreTestConst.Groups[2][0][0] },
					{ IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix } });

			prog.addSubSteps(IGeneralConst.approvQuoCritAuto);
			prog.manageSubSteps(new String[][] { { IPreTestConst.Groups[3][0][0] },
					{ IGeneralConst.approvQuoCritAuto[0][0] + newPASuffix } });

			prog.addSubSteps(IGeneralConst.gnrl_Closing_POSS_Step);
			prog.manageSubSteps(new String[][] { { progOfficers[1] } });

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="addPost_AwardSteps")
	public void addClosingStepAndActivate() throws Exception {
		try {

			prog.setStepOfficer(progOfficers[0]);

			prog.addStep(IGeneralConst.gnrl_Closing_POSS_Step);
			prog.manageStep(new String[][] { { progOfficers[0] } });

			prog.activateProgram("Active");
			log.info("Post-Award_Funding Opp complete!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void initializeFundingOpp() throws Exception {
		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

     	prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
	}

}
