/**
 * 
 */
package test_Suite.tests.workflow;

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
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class RegionalAssignmentNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * Due to Organization Import this must created after PreTest
	 */
	boolean isNewProgram = false;

	boolean hasProgramForm = true;

	boolean hasPublicationForm = true;

	boolean isNewOrg = true;

	String preFix = "-Regional-";

	char portaltype = 'F';

	String postFix = "-Assignement";

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[0][0][0], "G3 NEWS Group" };

	String submissionStep[][] = IGeneralConst.gnrl_RegionalSubA;

	String reviewStep[][] = IGeneralConst.reviewQuoCritAuto;

	String approvStep[][] = IGeneralConst.approvMajoCritManu;

	String possClosingStep[][] = IGeneralConst.gnrl_Closing_POSS_Step;

	Program prog;

	FOProject fo_po_Proj;

	String regionalName = "South West-L";

	String regionalUser = IPreTestConst.Groups[14][2][0];

	/*------ End of Global Vars --------------*/

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
		fo_po_Proj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {

		prog = new Program(preFix, portaltype, hasProgramForm, isNewProgram,
				hasPublicationForm);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.setProgPostfix(postFix);

		prog.initProgram();

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="initialize")
	public void submitFOProject() throws Exception {
		
		GeneralUtil.switchToFO();

		fo_po_Proj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), fo_po_Proj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		fo_po_Proj.createFOProject();

		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(fo_po_Proj,
				submissionStep[0][0]),
				"FAIL: Could not Find Project In FO Submission List!");

		GeneralUtil.setTextByIndex(0, "Start Regional Submission");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(
				"/numericDropdown/", regionalName),
				"FAIL: Could not find the Region!");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		GeneralUtil.takeANap(0.5);
		
		Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),
				"FAIL: Submit Button Disabled!");

		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitFOProject")
	public void assignOfficersAndEvaluate() throws Exception {
		
		GeneralUtil.switchToPOOnly();

		GeneralUtil.LoginAny(regionalUser + "1");

		fo_po_Proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ progOfficers[1], IPreTestConst.Groups[14][1][0] } });

		fo_po_Proj.assignEvaluators(new String[] { approvStep[0][0],
				IPreTestConst.Groups[14][1][0], IPreTestConst.Groups[14][1][1],
				IPreTestConst.Groups[14][1][2] });

		Assert.assertTrue(fo_po_Proj.reviewSubmission(reviewStep[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.LoginAny(regionalUser + "3");

		Assert.assertTrue(fo_po_Proj.approveSubmission(approvStep[0][0], true, "Ready", false, false));

	}

}
