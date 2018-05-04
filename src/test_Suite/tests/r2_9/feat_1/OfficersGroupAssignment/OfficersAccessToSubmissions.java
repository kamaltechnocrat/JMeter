/**
 * 
 */
package test_Suite.tests.r2_9.feat_1.OfficersGroupAssignment;

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
public class OfficersAccessToSubmissions {

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
			
			fopp = new FundingOpportunity("A","-Officers-PA-","");
			
			proj = new Project(fopp,"Access-", true,true,EFormsUtil.createAnyRandomString(5));

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

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void testAssignEvaluatorsList() throws Exception {
		
		proj.initializeStep(IGeneralConst.approvQuoCritManu[0][0]);
		
		Assert.assertTrue(ProjectUtil.openAssignEvaluators(proj,IProgramsConst.EProjectType.pre_Award.ordinal()), "FAIL: Approval Step not found for PROJECT OFFICER 1");
		
		GeneralUtil.switchToPOWithProjOfficer("5");
		
		Assert.assertTrue(ProjectUtil.openAssignEvaluators(proj,IProgramsConst.EProjectType.pre_Award.ordinal()), "FAIL: Approval Step not found for PROJECT OFFICER 5");
		
		GeneralUtil.switchToPOWithProjOfficer("3");
		
		Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
	}	

	@Test(groups = { "WorkflowNG" },dependsOnMethods="testAssignEvaluatorsList")
	public void testAwardSubmission() throws Exception {
		
		GeneralUtil.switchToPOWithProjOfficer("3");
		
		proj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
		
		Assert.assertTrue(ProjectUtil.openAwardInList(proj),"FAIL: could not find award submission in List for PROJECT OFFICER 3");		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the Award Submission in Read Only Mode for PROJECT OFFICER 3");
		
		GeneralUtil.switchToPOWithProjOfficer("2");
		
		Assert.assertTrue(ProjectUtil.openAwardInList(proj),"FAIL: could not find award submission in List for PROJECT OFFICER 2");		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the Award Submission in Read Only Mode for PROJECT OFFICER 2");
		
		GeneralUtil.switchToPOWithProjOfficer("4");
		
		Assert.assertTrue(ProjectUtil.openAwardInList(proj),"FAIL: could not find award submission in List for PROJECT OFFICER 4");		
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the Award Submission in Read Only Mode for PROJECT OFFICER 4");
		
		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);
		
		Assert.assertTrue(proj.submitAward("Standard", 2, true), "FAIL: could not submit Award");
	}

	@Test(groups = { "WorkflowNG" },dependsOnMethods="testAwardSubmission")
	public void testPostAwardSubmission() throws Exception {
		
		proj.setClaimNumber(2);
		
		Assert.assertTrue(ProjectUtil.openInitialClaimForm(proj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView, IFiltersConst.submissionVersion_LatestVersion, IFiltersConst.submissionsStatus_Ready_StatusSubView),"FAIL: Could not Open Initial Claim for PROJECT OFFICER 4");
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the Award Submission in Read Only Mode for PROJECT OFFICER 4");
		
		GeneralUtil.switchToPOWithProjOfficer("1");
		
		Assert.assertTrue(ProjectUtil.openInitialClaimForm(proj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView, IFiltersConst.submissionVersion_LatestVersion, IFiltersConst.submissionsStatus_InProgress_StatusSubView),"FAIL: Could not Open Initial Claim for PROJECT OFFICER 1");
		Assert.assertFalse(GeneralUtil.isTextFieldReadOnly(0),"FAIL: the Award Submission in Read Only Mode for PROJECT OFFICER 1");
				
	}

}
