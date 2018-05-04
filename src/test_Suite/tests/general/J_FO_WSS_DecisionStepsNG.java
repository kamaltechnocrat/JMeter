/**
 * 
 */
package test_Suite.tests.general;

/**
 * @author mshakshouki
 * 
 */

import java.util.Hashtable;

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
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

@GUITest
@Test(singleThreaded = true)
public class J_FO_WSS_DecisionStepsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean isItNewProgram = false;

	boolean hasProgramForm = false;

	boolean isItNewOrg = true;

	boolean hasPublicationForm = true;

	String preFix = "-Decision-WSS-";

	char portaltype = 'F';

	String postFix = "";

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	Program prog;

	FOProject foProj;

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

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		prog = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {
		initializeFundingOpp();

	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void j_FO_WSS_DecisionStepsNG() throws Exception {

		try {

			// Advance the workflow

			GeneralUtil.switchToFO();

			registerAndSubmitApplication();

			GeneralUtil.switchToPO();

			assignOfficers();

			testAdvancingWSS(0);

			makeDecision("Yes");

			testAdvancingWSS(0);

			testDecision(1);

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = { "j_FO_WSS_DecisionStepsNG" })
	public void j_FO_WSS_DecisionSteps2NG() throws Exception {
		try {

			GeneralUtil.switchToFO();

			// Execute all steps - No Advancement

			registerAndSubmitApplication();

			GeneralUtil.switchToPO();

			assignOfficers();

			testAdvancingWSS(0);

			makeDecision("No");

			testAdvancingWSS(0);

			testDecision(0);

			advanceWorkflow();

			testAdvancingWSS(1);

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void initializeFundingOpp() throws Exception {
		prog = new Program(preFix, portaltype, hasProgramForm, isItNewProgram,
				hasPublicationForm);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		return;
	}

	private void registerAndSubmitApplication() throws Exception {

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				IGeneralConst.gnrl_SubmissionA[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");

		return;
	}

	private void assignOfficers() throws Exception {

		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[0][1][0] } });

		foProj.submitFromMyAssignedSubList(foProj.getProjFullName(), "",
				IGeneralConst.PO_Submission_NonD[0][0], true, "No", foProj);

		return;
	}

	private void makeDecision(String condition) throws Exception {

		foProj.submitFromMyAssignedSubList(foProj.getProjFullName(), "",
				IGeneralConst.PO_SubmissionB[0][0], true, condition, foProj);

		return;
	}

	private void advanceWorkflow() throws Exception {
		foProj.submitFromMyAssignedSubList(foProj.getProjFullName(), "",
				IGeneralConst.PO_Submission_NonC[0][0], true, "No", foProj);

		return;
	}

	private void testDecision(int expectedNumber) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, foProj
				.getProjFOFullName());

		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, foProj
				.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		foProj.initializeStep(IGeneralConst.gnrl_AwardCrit[0][0]);

		Assert.assertEquals(
				TablesUtil.findHowManyInTable(ITablesConst.awardsTableId,
						foProj.getCurrentStepName()), expectedNumber);

		return;
	}

	private void testAdvancingWSS(int expectedNumber) throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		hashTable.put(
				IFiltersConst.grantManagement_ProjectName_Lbl,
				foProj.getProjFOFullName());

		hashTable.put(
				IFiltersConst.grantManagement_FundingOppName_Lbl,
				foProj.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		foProj.initializStep(IGeneralConst.approvQuoCritAuto[0][0]);

		Assert.assertEquals(TablesUtil.findHowManyInTable(
				ITablesConst.fundingOpp_EvaluateSubmissionsTableId, foProj
				.getCurrentStepName()), expectedNumber,
				"FAIL: Could not find " + foProj.getCurrentStepName());

		return;
	}

}
