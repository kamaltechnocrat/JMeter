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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class VerifyNoGroupsInSummaryNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project proj;
	
	FundingOpportunity fopp;
	
	String[] grpsArr = {"G03 Approvers","Staff","G06 Project Officers"};

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
			proj = new Project(fopp,"No-Groups-",true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
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
			
			proj.assignOfficers(new String[][] {{IPreTestConst.Groups[6][0][0]}});

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
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			ProjectUtil.openEvaluateSubmissionFormletInList(proj, IGeneralConst.reviewQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "Review", false);
			
			Assert.assertFalse(GeneralUtil.findInErrorList(GeneralUtil.checkForInfoMessages(IGeneralConst.evalStaffSummary_And_Buttons_DivId),"There are 5 assigned evaluators:"),"FAIL: Should be No Assigned Evaluations Summary");
			
			ClicksUtil.returnFromAnyForm();
			
			proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView);			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods="testEvalSummaryAvailabilityInReview")
	public void testEvalSummaryAvailabilityInApproval() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			ProjectUtil.openEvaluateSubmissionFormletInList(proj, IGeneralConst.approvNonRexQuoAuto[0][0], IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "Approval", false);
			
			Assert.assertFalse(GeneralUtil.findInErrorList(GeneralUtil.checkForInfoMessages(IGeneralConst.evalStaffSummary_And_Buttons_DivId),"There are 5 assigned evaluators:"),"FAIL: Should be No Assigned Evaluations Summary");
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
