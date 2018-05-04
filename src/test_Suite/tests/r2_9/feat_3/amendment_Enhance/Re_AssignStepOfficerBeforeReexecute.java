/**
 * 
 */
package test_Suite.tests.r2_9.feat_3.amendment_Enhance;

import java.util.Hashtable;

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
public class Re_AssignStepOfficerBeforeReexecute {

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
			
			proj = new Project(fopp,"Re-Assign-Officer-", true,true,EFormsUtil.createAnyRandomString(5));

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
			
			Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_Submission_Non[0][0], "Just Edit", true));
			
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
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(proj, IPreTestConst.Groups[10][1][0], IGeneralConst.gnrl_SubmissionA[0][0]);

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
	public void reAssignProjectOfficers() throws Exception {
		try {
			
			Hashtable<String, String[]> hs = new Hashtable<String, String[]>();
			
			hs.put(IGeneralConst.PO_Submission_Non[0][2], new String[] {IPreTestConst.Groups[6][0][0],IPreTestConst.Groups[6][1][0]});
			
			hs.put(IGeneralConst.gnrl_AwardCrit[0][2], new String[] {IPreTestConst.Groups[6][0][0],IPreTestConst.Groups[6][1][0]});
			
			Assert.assertTrue(proj.assignOfficersBySteps(hs));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="reAssignProjectOfficers")
	public void reSubmitApplicantSubmission() throws Exception {
		
		try {
			
			Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.gnrl_SubmissionA[0][0], ", Resubmitted", true));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="reSubmitApplicantSubmission")
	public void testReExecutedPOSubInLists() throws Exception {
		
		try {
			
			Assert.assertFalse(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.PO_Submission_Non[0][0]));
			
			Assert.assertFalse(AmendmentsUtil.isViewAmendmentSubmissionIconExists(proj,IGeneralConst.PO_Submission_Non[0][0]));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testReExecutedPOSubInLists")
	public void continueReExecutedSteps() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertTrue(proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_Submission_Non[0][0], ", Resubmitted", false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true));
			
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
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueReExecutedSteps")
	public void testReExecutedAwardStepsForNonAssignedOfficer() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertFalse(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));
			
			Assert.assertFalse(AmendmentsUtil.isViewAmendmentSubmissionIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.gnrl_AwardCrit[0][0], IFiltersConst.openProjView));
			
			Assert.assertFalse(ProjectUtil.openPOAwardFormletInList(proj, IGeneralConst.gnrl_AwardCrit[0][0], "Submission Summary"));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testReExecutedAwardStepsForNonAssignedOfficer")
	public void testReExecutedAwardStepsForAssignedOfficer() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentSubmissionIconExists(proj,IGeneralConst.gnrl_AwardCrit[0][0]));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.gnrl_AwardCrit[0][0], IFiltersConst.openProjView));
			
			ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);
			
			Assert.assertTrue(proj.submitAmendedBasicAward(true));
			
		} catch (Exception e) {
		Assert.fail("Unexpected Exception: ", e);
		}
	}

}
