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

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class PO_Assigns_FOApplicant_As_Amender {

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
			
			fopp = new FundingOpportunity("A","-Officers-","");
			
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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="completeFOProject")
	public void continueProjectWorkflow() throws Exception {
		try {			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("3");	
			
			Assert.assertTrue(foProj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			foProj.submitAward("Basic", 0, true);

			foProj
					.assignOfficers(new String[][] {
							{ IPreTestConst.Groups[6][0][0],
									IPreTestConst.Groups[10][1][0] } });
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void requestAmendments() throws Exception {
		
		GeneralUtil.switchToPOWithProgOfficer("1");
		
		String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(foProj, "/Ouia 1/", IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, foProj));	
	}
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendments")
	public void testAmendedSubmissionsAccessableByRegistrant_4() throws Exception {
		
		GeneralUtil.switchToFOOnly();
		GeneralUtil.loginAnyFO("front4");
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: could not Open Applicant Submission!");
		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the eForm should be in editable mode");
		
		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
	}
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendments")
	public void testAmendedSubmissionsAccessableByRegistrant_1() throws Exception {
		
		GeneralUtil.switchToFO();
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: could not Open Applicant Submission!");
		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the eForm should be in editable mode");
		
		ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);	
	}
}
