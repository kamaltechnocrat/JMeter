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
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class A_GnrlProgramNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	boolean newProgram = true;
	
	boolean programForm = false;
	
	boolean newOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	
	char portaltype = 'P';

	Program prog;

	// #####*******************************************

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

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		prog = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram,
				false);
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);		
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="initializeFOPP") 
	public void createFOPP() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.createProgram();		
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="createFOPP") 
	public void addSteps() throws Exception {

		prog.addStep(IGeneralConst.gnrl_SubmissionA);

		prog.manageStep(new String[][] { { progOfficers[0] },
				{ IGeneralConst.gnrl_SubmissionA[0][0] } });

		prog.addStep(IGeneralConst.reviewQuoCritAuto);
		prog.manageStep(new String[][] { { progOfficers[0] },
				{ IGeneralConst.reviewQuoCritAuto[0][0] } });

		prog.addStep(IGeneralConst.approvQuoCritManu);
		prog.manageStep(new String[][] { { progOfficers[0] },
				{ IGeneralConst.approvQuoCritManu[0][0] } });

		prog.setStepOfficer(progOfficers[1]);

		prog.addStep(IGeneralConst.gnrl_AwardCrit);
		prog.manageStep(new String[][] { { progOfficers[1] },
				{ IGeneralConst.gnrl_AwardCrit[0][0] } });
		
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods="addSteps") 
	public void addClosingStepAndActivate() throws Exception {

		prog.setStepOfficer(progOfficers[0]);

		prog.addStep(IGeneralConst.gnrl_Closing_POSS_Step);
		prog.manageStep(new String[][] { { progOfficers[0] } });

		prog.activateProgram("Active");
		log.info("General_Program_Calls complete!");
		
	}
}
