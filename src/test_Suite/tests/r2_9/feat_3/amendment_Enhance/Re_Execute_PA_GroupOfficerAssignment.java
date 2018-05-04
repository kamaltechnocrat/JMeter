/**
 * 
 */
package test_Suite.tests.r2_9.feat_3.amendment_Enhance;

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
public class Re_Execute_PA_GroupOfficerAssignment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	FOProject foProj;
	
	private static final String newPASuffix = "-pa";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-PA-","");
			
			foProj = new FOProject(fopp,"Re-Execute-PA-Group-", true,1,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void completeFOProject() throws Exception {
		try {			
			
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()));
			
			Assert.assertTrue(foProj.createFOProjectNew(), "FAIL: could not create Source Project! " + foProj.getProjFOFullName());
			
			Assert.assertTrue(foProj.submitFOProject(IGeneralConst.gnrl_SubmissionA[0][0], true), "FAIL: could not submit Project");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="completeFOProject")
	public void continueProjectWorkflow() throws Exception {
		try {	
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertTrue(foProj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertTrue(foProj.submitAward("Standard", 2, true));			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void continuePostAwardProjectWorkflow() throws Exception {
		try {	
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front4");
			
			foProj.setClaimNumber(2);
			
			Assert.assertTrue(foProj.submitFOProject(IGeneralConst.initialClaim[0][0], true));
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertTrue(foProj.submitFromMyAssignedSubListNew(IGeneralConst.PO_Submission_Non[0][0], "Initial Submission", true));
			
			Assert.assertTrue(foProj.assignAllAvailableEvaluator(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
			
			Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0] + newPASuffix, true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView,false, false));
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continuePostAwardProjectWorkflow")
	public void requestAmendmentOnIPASS() throws Exception {
		try {	
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(foProj, IPreTestConst.Groups[6][1][0], IGeneralConst.initialClaim[0][0]);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, foProj));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendmentOnIPASS")
	public void testAmendedClaimAvailablity() throws Exception {
		try {		
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front4");
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.initialClaim[0][0]), "FAIL: Could not Open FO Submission!" );	
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read Only");
			
			GeneralUtil.switchToFO();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.initialClaim[0][0]));
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read Only");
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			/*
			 * this is changed in 3.0, due to spliting up POG and Step Staff
			 */
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView));
			
			//Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read Only");
			
			//ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView, IFiltersConst.submissionVersion_LatestVersion,IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read Only");
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView));
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read/Write");
			
			ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);
			
			Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView, IFiltersConst.submissionVersion_LatestVersion,IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read/Write");
			
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testAmendedClaimAvailablity")
	public void advanceAmendedProject() throws Exception {
		try {	
			
			Assert.assertTrue(foProj.submitFromMyAssignedSubListNew(IGeneralConst.initialClaim[0][0], ", Advancing Amended Project", true));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"Fail: should be Read/Write");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

}
