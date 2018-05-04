/**
 * 
 */
package test_Suite.tests.r3_2.hideAssignedEvaluators;

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
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifyGroupsInSummaryByPogNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project proj;
	
	FundingOpportunity fopp;
	
	String[] grpsArr = {"Staff","G02 Reviewers","G06 Project Officers"};

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A","-Officers-","");
			proj = new Project(fopp,"",true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		deSelectingTheGroupsNG();
		
		proj = null;
		fopp = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void selectingTheGroupsNG() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			
			Assert.assertTrue(fopp.openFundingOppPlanner(),"FAIL: could not open FOPP Planner!");
			
			proj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);
			
			Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(proj.getCurrentStepIdent(), 0)), "FAIL: could not Open Step Details");
			
			Assert.assertTrue(FOPPUtil.selectEvaluationVisibilityGroups(grpsArr), "FAIL: could not select Groups");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods="selectingTheGroupsNG")
	public void createOrgAndProject() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			proj.newCreateNewOrg();
			proj.newCreateNewPOProject();
			proj.submitProject(true);
			
			proj.assignOfficers(new String[][] {{IPreTestConst.Groups[6][0][0]}});
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView), "FAIL: Could not Review Submission");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false), "FAIL: Could not Approve Submission");
			
			GeneralUtil.switchToPOWithProjOfficer("1");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods="createOrgAndProject")
	public void testEvalSummaryAvailabilityInReview() throws Exception {
		try {
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyProjectSubmissionsList(proj, IGeneralConst.reviewQuoCritAuto[0][0], IFiltersConst.openProjView),"FAIL: could not Open Review Submission!");
			
			Assert.assertTrue(GeneralUtil.findInErrorList(GeneralUtil.checkForInfoMessages(IGeneralConst.evalStaffSummary_And_Buttons_DivId),"There are 5 assigned evaluators:"),"FAIL: No Assigned Evaluators Summary");
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods="createOrgAndProject")
	public void testEvalSummaryAvailabilityInApproval() throws Exception {
		try {
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyProjectSubmissionsList(proj, IGeneralConst.approvNonRexQuoAuto[0][0], IFiltersConst.openProjView),"FAIL: could not Open Approval Submission!");
			
			Assert.assertFalse(GeneralUtil.findInErrorList(GeneralUtil.checkForInfoMessages(IGeneralConst.evalStaffSummary_And_Buttons_DivId),"There are 5 assigned evaluators:"),"FAIL: Should be No Assigned Evaluations Summary");
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */	
	private void deSelectingTheGroupsNG() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			
			Assert.assertTrue(fopp.openFundingOppPlanner(),"FAILE: could not open FOPP Planner!");
			
			proj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);
			
			Assert.assertTrue(ProgramUtil.openStepDetails(ProgramUtil.findStepInProgPlanner(proj.getCurrentStepIdent(), 0)), "FAIL: could Open Step Details");
			
			Assert.assertTrue(FOPPUtil.deSelectEvaluationVisibilityGroups(grpsArr), "FAIL: could not select Groups");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
