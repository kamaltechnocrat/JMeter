/**
 * Test Case depends on PreTest02NG
 * uses two preconfigured eForms
 * 1. Submission BF-DG
 * 2. Approval BF-DG
 * Steps:
 * 1. Fill the Submission Budget Grid,
 * 2. On save the total of first row ((1+2) * 3) expression
 * 3. Total of first column (foreach()) expression
 * 4. then the total Brought Forward to the Approval form
 * 5. All totals cells and fields are read only.
 */
package test_Suite.tests.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class DataGrid_BF_CalcValuesNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;
	String preFix = "-DG-"; // Data-Grid
	char portaltype = 'F';
	String postFix = "-BF-Funding";
	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String approversGrp[] = { IPreTestConst.Groups[0][0][0] };
	Program prog;
	FOProject foProj;
	Project proj;

	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "Submission BF-DG" } };

	String approvStep[][] = {
			{ "Approval-CRQA", "/approvalStep/", "Approval", "true", "Yes" },
			{ "Approval-CALC-BF-DG", "Quorum", "1", "true" } };

	// #####--------------- END OF GLOBAL PARAMS VARS

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

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
	public void initialize() throws Exception {
		
		prog = new Program(preFix, portaltype, programForm, newProgram,
				false);

		prog.setProgPostfix(postFix);
		prog.setProgOfficers(progOfficers);
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		log.info("FOPP Initialized");

	}
	
	@Test(groups= {"WorkflowNG"}, dependsOnMethods = "initialize")
	public void testNumeric_CalcBF_FromDGCells() {
		try {

			GeneralUtil.switchToFO();

			submitFOProject();

			GeneralUtil.switchToPO();

			foProj.assignOfficers(new String[][] { {
					IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					foProj, approvStep[0][0], "Ready", "Approval", false));

			Assert.assertTrue(ProjectUtil.checkBFInTextField(0, "10.00"),
					"FAIL: Bring Forward Failed");

			Assert.assertTrue(ProjectUtil.checkBFInTextField(1, "9.00"));

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}

	}

	@Test(groups = { "WorkflowNG" })
	public void testNumericCalcValues_OnDGCells() {

		try {

			proj = new Project(prog, true);
			proj.setNewProject(true);
			proj.createPOProject(newOrg);

			ProjectUtil.openPOSubmissionForm(proj, submissionStep[0][0],
					IFiltersConst.openProjView, "Latest Version", "All");

			int cellsIndex[][] = new int[4][3];

			String cellsValue[][] = new String[4][3];

			for (int x = 0; x < cellsIndex[0].length; x++) {
				cellsIndex[0][x] = x;
				cellsValue[0][x] = Integer.toString(x + 1);
			}

			for (int x = 0; x < cellsIndex.length; x++) {
				cellsIndex[x][0] = x;
				cellsValue[x][0] = Integer.toString(x + 1);
			}

			ProjectUtil.fillDataGrid(cellsIndex, cellsValue);

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.nextBtn), "FAIL: Unable to Click Next Button");
			
			Assert.assertTrue(ClicksUtil.clickLinks("Formlet"), "FAIL: Could not click Link Formlet!");

			Assert.assertTrue(ProjectUtil.checkCellValueInGrid(0, 3, "$9.00"),
					"FAIL: the Calculated Value is not as Expected");

			Assert.assertTrue(ProjectUtil.checkCellValueInGrid(4, 0, "$10.00"),
					"FAIL: the Calculated Value is not as Expected");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			Assert.assertTrue(GeneralUtil
					.isButtonEnabled(IClicksConst.submitBtn),
					"Fail: The Submit Button is disabled");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	private void submitFOProject() throws Exception {

		foProj = new FOProject(prog);

		/*******************************************************************
		 * ** Register To Program ****
		 ******************************************************************/

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()), "No "
				+ foProj.getFoOrgName());
		foProj.createFOProject();

		ProjectUtil.openSubmissionForm(foProj, submissionStep[0][0], "",
				"Latest Version");

		int cellsIndex[][] = new int[4][3];

		String cellsValue[][] = new String[4][3];

		for (int x = 0; x < cellsIndex[0].length; x++) {
			cellsIndex[0][x] = x;
			cellsValue[0][x] = Integer.toString(x + 1);
		}

		for (int x = 0; x < cellsIndex.length; x++) {
			cellsIndex[x][0] = x;
			cellsValue[x][0] = Integer.toString(x + 1);
		}

		ProjectUtil.fillDataGrid(cellsIndex, cellsValue);

		ClicksUtil.clickButtons(IClicksConst.nextBtn);
		ClicksUtil.clickLinks("Formlet");

		Assert.assertTrue(ProjectUtil.checkCellValueInGrid(0, 3, "$9.00"),
				"FAIL: the Calculated Value is not as Expected");

		Assert.assertTrue(ProjectUtil.checkCellValueInGrid(4, 0, "$10.00"),
				"FAIL: the Calculated Value is not as Expected");

		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

		Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),
				"Fail: The Submit Button is disabled");

		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
	}

}
