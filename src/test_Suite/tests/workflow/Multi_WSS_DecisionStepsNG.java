/**
 * 
 */
package test_Suite.tests.workflow;

/**
 * @author mshakshouki
 *
 */

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
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class Multi_WSS_DecisionStepsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isItNewProgram = false;
	boolean hasProgramForm = false;
	boolean isItNewOrg = true;
	boolean hasPublicationForm = true;

	String preFix = "-Multi-WSS-Decision-";
	char portaltype = 'F';
	String postFix = "";

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	Program prog;
	Project proj;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	//private static final String newPASuffix = "-pa";

	// ------------------- End of Global Parameters
	// ----------------------------------

	@BeforeClass(groups = { "WorkflowNG" })
	public void setup() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		prog = null;
		foProj = null;
		proj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	private void initialize() throws Exception {

		prog = new Program(preFix, portaltype, hasProgramForm, isItNewProgram,
				hasPublicationForm);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		return;
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void multi_WSS_DecisionStepsNG() throws Exception {

		try {

			GeneralUtil.switchToFO();

			registerAndSubmitFOProject();

			GeneralUtil.switchToPOOnly();

			assignOfficers();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void registerAndSubmitFOProject() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"Fail: Could not Find Project In FO Submission List!");

		return;
	}

	private void assignOfficers() throws Exception {
		GeneralUtil.logInSuper();

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[0][1][0] } });

		return;
	}

}
