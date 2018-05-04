/**
 * 
 */
package test_Suite.tests.projectList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.lib.workflow.SubmissionHistory;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.SubHistoryUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestSubmissionHistoryListNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TestSubmissionHistoryListNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = true;
	boolean newProject = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	String postFix = "";
	char portaltype = 'P';

	Program prog;
	Project proj;
	SubmissionHistory subHistory;

	// ---------------------------- End of Params

	@BeforeClass(groups = { "ProjectListNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			// ---------------------------------

			initializeFOPP();
			initializeProject();
			initializeSubHistory();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectListNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@AfterMethod(alwaysRun = true)
	public void init() {
		subHistory.setOverrideComments("Comments");
	}

	private void initializeFOPP() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();
	}

	private void initializeProject() throws Exception {

		proj = new Project(prog, newProject);

		proj.createPOProject(newOrg);

		if (newProject) {
			proj.submitProject(true);
			assignOfficers();
		}
	}

	private void assignOfficers() throws Exception {
		proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] },
						{ IPreTestConst.Groups[6][0][0],
								IPreTestConst.Groups[6][1][0] } });
	}

	private void initializeSubHistory() throws Exception {

		subHistory = new SubmissionHistory(proj);

		proj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);

		subHistory.setStepName(proj.getCurrentStepName());

		subHistory.setHistoryStatus("Complete");

		subHistory.setHistorySubVersion("All Versions");

		subHistory.setOverrideComments("Comments");

	}

	@Test(groups = { "ProjectListNG" })
	public void testOverrideDatePastNG() throws Exception {

		subHistory.setOverrideDate(GeneralUtil.setDayofMonth(-1));

		Assert.assertTrue(
				SubHistoryUtil.fillOverrideDetails(subHistory),
				"FAIL: Could edit override details!");

		Assert
				.assertEquals(SubHistoryUtil
						.compareOverrideDates(subHistory), 0,
						"FAIL: Override Date on list is not equal to Override Date Details");

	}

	@Test(groups = { "ProjectListNG" })
	public void testOverrideDateFutureNG() throws Exception {

		subHistory.setOverrideDate(GeneralUtil.setDayofMonth(1));

		Assert.assertTrue(
				SubHistoryUtil.fillOverrideDetails(subHistory),
				"FAIL: Could edit override details!");

		Assert
				.assertEquals(SubHistoryUtil
						.compareOverrideDates(subHistory), 0,
						"FAIL: Override Date on list is not equal to Override Date Details");

	}

	@Test(groups = { "ProjectListNG" })
	public void testOverrideDateReadyNG() throws Exception {

		subHistory.setOverrideDate(GeneralUtil.setDayofMonth(1));

		subHistory.setHistoryStatus("Ready");

		Assert.assertTrue(
				SubHistoryUtil.fillOverrideDetails(subHistory),
				"FAIL: Could edit override details!");

		Assert
				.assertEquals(SubHistoryUtil
						.compareOverrideDates(subHistory), 0,
						"FAIL: Override Date on list is not equal to Override Date Details");

	}

	@Test(groups = { "ProjectListNG" })
	public void testOverrideDateInProgressNG() throws Exception {

		subHistory.setOverrideDate(GeneralUtil.setDayofMonth(1));

		subHistory.setHistoryStatus("In progress");

		Assert.assertTrue(
				SubHistoryUtil.fillOverrideDetails(subHistory),
				"FAIL: Could edit override details!");

		Assert
				.assertEquals(SubHistoryUtil
						.compareOverrideDates(subHistory), 0,
						"FAIL: Override Date on list is not equal to Override Date Details");

	}

	@Test(groups = { "ProjectListNG" })
	public void testOverrideDateCompleteNG() throws Exception {

		subHistory.setOverrideDate(GeneralUtil.setDayofMonth(1));

		subHistory.setHistoryStatus("Complete");

		Assert.assertTrue(
				SubHistoryUtil.fillOverrideDetails(subHistory),
				"FAIL: Could edit override details!");

		Assert
				.assertEquals(SubHistoryUtil
						.compareOverrideDates(subHistory), 0,
						"FAIL: Override Date on list is not equal to Override Date Details");

	}

	@Test(groups = { "ProjectListNG" })
	public void testValidationOverrideDateInvalidNG() throws Exception {
		try {

			subHistory.setOverrideDate("99999");

			Assert.assertFalse(SubHistoryUtil
					.openAndFillOverrideDetails(subHistory),
					"FAIL: No Validation for Invalid Date!");

			SubHistoryUtil.returnFromOverrideDetails();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "ProjectListNG" })
	public void testValidationOverrideDateCommentsTooLongNG() throws Exception {
		try {

			subHistory.setOverrideDate(GeneralUtil.setDayofMonth(0));

			subHistory.setOverrideComments(EFormsUtil.createRandomNumber(302));

			Assert.assertFalse(SubHistoryUtil
					.openAndFillOverrideDetails(subHistory),
					"FAIL: No Validation for Comment exceded 300 Chars!");

			SubHistoryUtil.returnFromOverrideDetails();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
