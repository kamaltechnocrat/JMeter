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

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.GpsUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class GPS_BF_FundingGridNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*
	 * For 2.7.0.0 and above FOPP is part of g3_Watij_preTest_install.zip
	 */
	boolean newProgram = false;
	boolean programForm = true;
	boolean newOrg = true;

	String preFix = "-GPS-";
	char portaltype = 'F';
	String postFix = "-BF-Funding";
	Integer numOfPayments = 2;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	// Steps
	String progForm = "GPS Program Form";
	String submissionStep[][] = {
			{ "Submission-A", "", "Applicant Submission", "true", "No" },
			{ "GPS-CALC-BF-Funding-Application" } };

	String paInitialClaim1[][] = {
			{ "Initial-Claim", "", "Initial Post Award Submission", "true",
					"No" }, { "Post Award Submission", "Claim", "true" } };

	Program prog;
	FOProject foProj;

	// Any release prior to 2.0.2.0 assign empty string
	// private static final String newPASuffix = "-pa";

	// #####--------------- END OF GLOBAL PARAMS VARS

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		log.info("GPS_BF_FundingGrid()");
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			// ----------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
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

		initializeFOPPAndAdminForm();

		createFOProject();

		GpsUtil.openAndFillFOApplicationFunding(foProj,
				submissionStep[0][0]);

		ClicksUtil.clickLinks("Fiscal Years");

	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void testCalcBF_PaymentScheduleList_FromAdminEForm() {

		try {

			TablesUtil.openInTableByImage("/data/", "Claim 1", 1);

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(0),
					"Claim 1", "FAIL: CalcBF Text in List Details !");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(1), prog
					.getStartDate(), "FAIL: CalcBF Text in List Details !");

			Assert.assertEquals(GeneralUtil.getTextInTextFieldByIndex(2), prog
					.getEndDate(), "FAIL: CalcBF Text in List Details !");

			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

			GeneralUtil.switchToPO();

			assignOfficers();

		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
		log.info("GPS_BF_FundingGridNG complete!");
	}
	
	

	private void initializeFOPPAndAdminForm() throws Exception {

		prog = new Program(preFix, portaltype, programForm, newProgram, false);

		prog.setProgPostfix(postFix);

		prog.setProgAdmin(progAdmin);

		prog.setProgOfficers(progOfficers);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		prog.initProgram();

		prog.setProgramFormName(progForm);

		ClicksUtil.clickLinks(prog.getProgFullIdent());

		prog.managePaymentScheduleProgramForm(numOfPayments,
				paInitialClaim1[1][1]);
	}

	private void createFOProject() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Fund Opp.!");

		foProj.createFOProject();
	}

	private void assignOfficers() throws Exception {
		foProj.assignOfficers(new String[][] { { IPreTestConst.Groups[0][0][0],
				IPreTestConst.Groups[0][1][0] } });
	}
}
