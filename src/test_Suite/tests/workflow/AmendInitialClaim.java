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
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AmendInitialClaim {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String preFix = IFoppConst.fundingOpp_PA_Prefix;
	String postFix = "";

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// Steps Used
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;
	String paInitialClaim[][] = IGeneralConst.initialClaim;
	String reviewSubStep[][] = IGeneralConst.reviewQuoCritManu;

	FundingOpportunity fopp;	
	FOProject foProj;

	String regirtrant = "/qa_Registrant1@g3-qa-autobuild.csdc-lan.csdcsystems.com/";

	// Any release prior to 2.0.2.0 assign empty string
	private static final String newPASuffix = "-pa";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();

			// ---------------------------------



			fopp = new FundingOpportunity("A", preFix,postFix);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		fopp = null;
		foProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups="WorkflowNG")
	public void startProject() throws Exception {

		submitFOProject();

		GeneralUtil.switchToPO();
		approveProject();
		submitAward();

		GeneralUtil.switchToFO();
		submitFOClaims(1);

		evaluatePostAward();

		requestAmendment();

	}

	@Test(groups="WorkflowNG", dependsOnMethods="startProject")
	public void doesCancelAmendmentIconExists() throws Exception {

		String[] param = new String[] {
				foProj.getProjLetter(),
				foProj.getProjFOFullName(),
				paInitialClaim[0][0],
				IPreTestConst.FO_OrgShortName + "1",
				foProj.getProgFullName()
		};

		Assert.assertTrue(AmendmentsUtil.isAmendmentCancelIconExists(param, foProj));
	}

	@Test(groups="WorkflowNG", dependsOnMethods="doesCancelAmendmentIconExists")
	public void justToSwitch() throws Exception {
		
		GeneralUtil.switchToFO();

	}

	@Test(groups="WorkflowNG", dependsOnMethods="justToSwitch")
	public void testAmendedClaim() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

		FrontOfficeUtil.openFOSubmissionForm(foProj, paInitialClaim[0][0]);

		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0), "FAIL: the TextField should be Writable!");

		Assert.assertFalse(GeneralUtil.FindTextOnPage("A submission from a previous step is under amendment."), 
				"FAIL: Should be No Warning about Amendment!");

	}

	@Test(groups="WorkflowNG", dependsOnMethods="justToSwitch")
	public void testSecondClaim() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

		foProj.setClaimNumber(2);

		FrontOfficeUtil.openFOSubmissionForm(foProj, paInitialClaim[0][0]);

		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0), "FAIL: the TextField should be Writable!");

		Assert.assertFalse(GeneralUtil.FindTextOnPage("A submission from a previous step is under amendment."), 
				"FAIL: Should be No Warning about Amendment!");		
	}

	@Test(groups="WorkflowNG", dependsOnMethods={"testAmendedClaim","testSecondClaim"})
	public void changeAmenderToOfficer() throws Exception {

		GeneralUtil.switchToPOWithProjOfficer("1");

		foProj.setClaimNumber(1);

		ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk);

		Assert.assertTrue(AmendmentsUtil.changeAmender(foProj, "LProjOfficer1"),
				"FAIL: Could not change Amender to Officer!");

	}

	@Test(groups="WorkflowNG", dependsOnMethods="changeAmenderToOfficer")
	public void testAmendmentInToDoList() throws Exception {

		Assert.assertTrue(ProjectUtil.openClaimInMyAssignedSubmissionList(foProj, paInitialClaim[0][0], IFiltersConst.openProjView),
				"FAIL: Could not find Initial Claim in ToDo List");

	}	

	@Test(groups="WorkflowNG", dependsOnMethods="changeAmenderToOfficer")
	public void testAmendedSubmissionStatusInAppSubList() throws Exception {

		foProj.setSubProjFullName(foProj.getProjFullName() + " - Claim "+ foProj.getClaimNumber());

		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		Assert.assertTrue(ProjectUtil.openApplicantSubmissionList(foProj));

		Assert.assertEquals(ProjectUtil.getProjectStatusInList(ITablesConst.applicantSubmissionTableId, foProj), "In Progress");
	}

	private void submitFOProject() throws Exception {

		foProj = new FOProject(fopp,"AmendClaim-",true,1,EFormsUtil.createAnyRandomString(5));

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()),
				"FAIL: Could not Register to Funding Opp.!");

		foProj.createFOProject();

		Assert.assertTrue(foProj
				.submitFOProject(submissionStep[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");
	}

	private void submitFOClaims(int claimNumber) throws Exception {

		foProj.setClaimNumber(claimNumber);

		Assert.assertTrue(foProj.submitFOProject(paInitialClaim[0][0], true),
				"FAIL: Could not Find Project In FO Submission List!");
	}

	private void approveProject() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(foProj.approveSubmission(approvStep[0][0], true, "Ready", false, false));
	}

	private void submitAward() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(foProj.submitAward("Standard", 2, true),
				"FAIL: Could not Submit Award Form!");
	}

	private void evaluatePostAward() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("1");

		foProj.setClaimNumber(1);

		foProj.assignEvaluators(new String[] {
				reviewSubStep[0][0] + newPASuffix,
				IPreTestConst.Groups[2][1][0] });

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");

		Assert.assertTrue(foProj.reviewSubmission(reviewSubStep[0][0] + newPASuffix, true,"Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
	}

	private void requestAmendment() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer("1");

		foProj.setClaimNumber(1);

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil
				.issueAmendment(new String[] {
						foProj.getProjFullName(), paInitialClaim[0][0],
						regirtrant, dd,
						"Test Re-Execute on Previous Amendment",
				"This is a Comment" }, foProj));

	}


}
