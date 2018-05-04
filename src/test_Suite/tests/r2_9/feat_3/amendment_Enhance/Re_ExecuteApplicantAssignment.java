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
public class Re_ExecuteApplicantAssignment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-","");
			
			foProj = new FOProject(fopp,"ReExecuted-Applicant-", true,1,EFormsUtil.createAnyRandomString(5));

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
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void submitSecondFOSubmission() throws Exception {
		try {		
			
			GeneralUtil.switchToFO();
			
			Assert.assertTrue(foProj.submitFOProject(IGeneralConst.gnrl_SubmissionB[0][0], true), "FAIL: could not submit Project");
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitSecondFOSubmission")
	public void requestAmendmentOnAward() throws Exception {
		try {	
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(foProj, "PO: " + IPreTestConst.Groups[6][0][0], IGeneralConst.gnrl_AwardCrit[0][0]);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, foProj));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendmentOnAward")
	public void reSubmitAward() throws Exception {
		try {	
			
			Assert.assertTrue(foProj.submitAmendedBasicAward(true));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="reSubmitAward")
	public void testAmenderDefaultedToApplicant() throws Exception {
		try {	
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(foProj,IGeneralConst.gnrl_SubmissionB[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(foProj, IPreTestConst.fo_AmenderNameInList));
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentSubmissionIconExists(foProj,IGeneralConst.gnrl_SubmissionB[0][0]));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testAmenderDefaultedToApplicant")
	public void reSubmitSecondFOSubmission() throws Exception {
		try {	
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front4");
			
			Assert.assertTrue(foProj.submitAmendedFOProject(IGeneralConst.gnrl_SubmissionB[0][0], true));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}
}
