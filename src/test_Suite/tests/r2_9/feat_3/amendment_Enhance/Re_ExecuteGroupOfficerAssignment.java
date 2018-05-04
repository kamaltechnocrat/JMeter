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
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Re_ExecuteGroupOfficerAssignment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginProjOfficer("1");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-","");
			
			proj = new Project(fopp,"Re-Execute-Group-", true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		fopp = null;
		proj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void createOrgAndProject() throws Exception {
		try {
			
			proj.newCreateNewOrg();
			proj.newCreateNewPOProject();
			proj.submitProject(true);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void continueProjectWorkflow() throws Exception {
		try {		
			
			Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_Submission_Non[0][0], "Just Edit", false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("3");	
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("4");
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			proj.submitAward("Basic", 0, true);
			
			} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
			}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void assignRestGroupOfficers() throws Exception {
		try {	
			
			ProjectUtil.openProjectOfficerTabInBasket(proj);
			
			ClicksUtil.clickButtons(IClicksConst.assignGroupBtn);
			GeneralUtil.takeANap(0.5);
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="assignRestGroupOfficers")
	public void requestAmendmentOnApplicantSubmission() throws Exception {
		try {	
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(proj, IPreTestConst.Groups[6][1][0], IGeneralConst.gnrl_SubmissionA[0][0]);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, proj));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="requestAmendmentOnApplicantSubmission")
	public void reSubmitApplicantSubmission() throws Exception {
		try {	
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			proj.submitFromMyAssignedSubListNew(IGeneralConst.gnrl_SubmissionA[0][0], " Resubmitted", true);
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="reSubmitApplicantSubmission")
	public void testRexecutedReviewStepAmender() throws Exception {
		try {	
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.reviewQuoCritAuto[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(proj,"All"));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testRexecutedReviewStepAmender")
	public void testRexecutedApprovalStepAmender() throws Exception {
		try {		
			
			GeneralUtil.switchToPOWithProgOfficer("2");
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.approvNonRexQuoAuto[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(proj,"All"));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
			
			Assert.assertTrue(proj.approveAmendedSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], true, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testRexecutedApprovalStepAmender")
	public void testRexecutedAwardStepAmender() throws Exception {
		try {		
			
			GeneralUtil.switchToPOWithProjOfficer("2");
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(proj,"PO: " + IPreTestConst.Groups[6][0][0]));

			Assert.assertTrue(AmendmentsUtil.isViewAmendmentSubmissionIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));

		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

}
