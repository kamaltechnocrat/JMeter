/**
 * 
 */
package test_Suite.tests.r2_9.feat_1.OfficersGroupAssignment;

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
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubHistoryUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class NewGroupAssignmentToPAStep {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	FOProject foProj;
	
	String indx = "2";
	
	String[][] arrUser = IPreTestConst.MultiUsers[14];
	
	String newUser = arrUser[0][0] + indx;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		try {
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Officers-PA-", "");

			foProj = new FOProject(fopp, "New-Group-Assignment-", true, 1,
					EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() throws Exception {
		
		//To make sure the group is removed from PA Step
		
		changeGroupInFOPPStep();
		
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

			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp
					.getFoppFullName(), foProj.getFoOrgName()));

			Assert.assertTrue(foProj.createFOProjectNew(),
					"FAIL: could not create Source Project! "
							+ foProj.getProjFOFullName());

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"FAIL: could not submit Project");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "completeFOProject")
	public void continueProjectWorkflow() throws Exception {
		try {

			GeneralUtil.switchToPOWithProgOfficer("1");

			Assert
					.assertTrue(foProj
							.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");

			Assert.assertTrue(foProj.approveSubmission(
					IGeneralConst.approvQuoCritManu[0][0], true,
					IFiltersConst.evaluateSubmissions_Ready_StatusSubView,
					false, false));

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
	public void addNewGroupToPostAwardStep() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			fopp.changeGroupOfficerInStep(IGeneralConst.postAwardCrit[0][0], arrUser[1][0]);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "addNewGroupToPostAwardStep")
	public void testAddedNewOfficerAccessToIPASS() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			foProj.setClaimNumber(2);

			Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_Ready_StatusSubView),"FAIL: the Submission should be in the List");
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyByTtl("Enter Any Text"), "FAIL: The Submission should be in Read Only Mode!");
			
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertFalse(SubHistoryUtil.openProjectHistory(foProj.getProjFullName()));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testAddedNewOfficerAccessToIPASS")
	public void assignNewGroupToProject() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
			
			ht.put(foProj.getCurrentStepName(), new String[] {arrUser[1][0]});
			
			foProj.assignOfficersBySteps(ht);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "assignNewGroupToProject")
	public void testAssignedNewOfficerAccessToIPASS() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);

			Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			//In 3.0 Submission Access made Officers able to Submit
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyByTtl("Enter Any Text"), "FAIL: The Submission should be in Read Only Mode!");
			
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));	
			
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			
			Assert.assertTrue(SubHistoryUtil.openProjectHistory(foProj.getProjFullName()));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testAssignedNewOfficerAccessToIPASS")
	public void removeNewGroupFromPostAwardStep() throws Exception {
		try {
			
			changeGroupInFOPPStep();
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
			
			ht.put(foProj.getCurrentStepName(), new String[] {IPreTestConst.Groups[6][0][0]});
			
			foProj.assignOfficersBySteps(ht);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "removeNewGroupFromPostAwardStep")
	public void testRemovedOfficerAccessToIPASS() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);

			Assert.assertFalse(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));
			
			Assert.assertFalse(SubHistoryUtil.openProjectHistory(foProj.getProjFullName()));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	private void changeGroupInFOPPStep() throws Exception {
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(fopp.changeGroupOfficerInStep(IGeneralConst.postAwardCrit[0][0], IPreTestConst.Groups[6][0][0]));
		
		fopp.setFoppStaffs(fopp.initStaffList(new String[] {arrUser[1][0]}));
		
		fopp.removeFOPPStaff();
		
	}

}
