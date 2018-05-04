/**
 * 
 */
package test_Suite.tests.r3_2.multipleAmendments;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestAbilityToContinuePaStepsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	Project proj;
	
	private static final String newPASuffix = "-pa";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginProjOfficer("1");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-PA-","");
			
			proj = new Project(fopp,"Continue-SubProject-", true,true,EFormsUtil.createAnyRandomString(5));

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
			
			Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertTrue(proj.submitAward("Standard", 1, true));			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void startPostAwardProjectWorkflow() throws Exception {
		try {		
			
			proj.setClaimNumber(1);
			
			Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0], true));
			
			Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix));				
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="startPostAwardProjectWorkflow")
	public void submitEvaluationSteps() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "submitEvaluationSteps")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			proj.setClaimNumber(0);
			
			proj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(proj), "FAIL: Could not open Step Amendment Request Page!");
			
			proj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			String[] amenders = new String []{"Submission","PO: ".concat("G06 Project Officers")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Award Amendment";
			String comment = "";
			String paStartSchedule = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendmentWithSubProjectReExecute(proj,amenders,dd,reason,comment,true, paStartSchedule));
			
			Hashtable<String,Boolean> subOptional = new Hashtable<String,Boolean>();
			
			subOptional.put("Claim 1", true);
			
			Assert.assertTrue(AmendmentsUtil.configureSubProjectsReExecute(proj, subOptional), "FAIL: Could Not Set Sub Projects Re-Execute!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "requestStepAmendment")
	public void continueSubProject() throws Exception {
		try {			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
		
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0] + newPASuffix, true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView,false, false));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "continueSubProject")
	public void reSubmitAwardStep() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			proj.setClaimNumber(0);
			
			proj.submitFromMyAssignedSubListNew(IGeneralConst.pa_AwardCrit[0][0], " Resubmitting", true);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "reSubmitAwardStep")
	public void verifyAbilityToContinueSubProject() throws Exception {
		try {
			
			proj.setClaimNumber(1);
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, "Closing-Step".concat(newPASuffix), IFiltersConst.openProjView), "FAIL: Could not Open Form");
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0), "FAIL: the Submission Should be Read Only");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
}
