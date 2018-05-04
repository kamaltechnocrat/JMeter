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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst;
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
public class PO_Assigns_FOApplicant_As_Amender_To_InitialClaim {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/
	
	FundingOpportunity fopp;
	FOProject foProj;

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-PA-","");
			
			foProj = new FOProject(fopp,"Applicant-Amender-", true,1,EFormsUtil.createAnyRandomString(5));

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

	@Test(groups = { "WorkflowNG" },dependsOnMethods="completeFOProject")
	public void continueWorkflow() throws Exception {
		
		GeneralUtil.switchToPOWithProgOfficer("1");
		
		Assert.assertTrue(foProj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));		
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
		
		GeneralUtil.switchToPOWithProgOfficer("2");
		
		Assert.assertTrue(foProj.submitAward("Standard", 2, true), "FAIL: could not submit an Award");		
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueWorkflow")
	public void continuePostAwardWorkflow() throws Exception {
		
		GeneralUtil.switchToFOOnly();
		GeneralUtil.loginAnyFO("front4");
		
		foProj.setClaimNumber(2);
		
		Assert.assertTrue(foProj.submitFOProject(IGeneralConst.initialClaim[0][0], true));
		
		GeneralUtil.switchToPOWithProjOfficer("2");
		
		foProj.initializeStep(IGeneralConst.reviewQuoCritManu[0][0] + "-pa");
		
		Assert.assertTrue(ProjectUtil.openAssignEvaluators(foProj,IProgramsConst.EProjectType.post_Award.ordinal()), "FAIL: Review Step not found for PROJECT OFFICER 2");
		
		ClicksUtil.clickButtons(IClicksConst.m2mAddAllBtn);		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);	
		
		Assert.assertFalse(GeneralUtil.FindTextOnPage("An Unexpected Error has Occurred"), "FAIL: An Exception has occured ");				
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");
		
		Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0] + "-pa", true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertTrue(foProj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0] + "-pa", true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
	}		

	@Test(groups = { "WorkflowNG" },dependsOnMethods="continuePostAwardWorkflow")
	public void requestAmendments() throws Exception {
		
		GeneralUtil.switchToPOWithProgOfficer("1");
		
		String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(foProj, "/Ouia 1/", IGeneralConst.gnrl_SubmissionA[0][0]);
		
		amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(foProj, "/Ouia 1/", IGeneralConst.initialClaim[0][0]);
		
		Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, foProj));		
	}
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendments")
	public void testAmendedSubmissionsAccessableByRegistrant_4() throws Exception {
		
		GeneralUtil.switchToFOOnly();
		GeneralUtil.loginAnyFO("front4");
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.initialClaim[0][0]), "FAIL: could not Open Initial Claim!");
		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the eForm should in editable mode");
		
		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
	}
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendments")
	public void testAmendedSubmissionsAccessableByRegistrant_1() throws Exception {
		
		GeneralUtil.switchToFO();
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.initialClaim[0][0]), "FAIL: could not Open Initial Claim!");
		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the eForm should in editable mode");
		
		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);		
	}

}
