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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class FoppAdminAssignOfficersToPAStep {

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
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Officers-PA-", "");
			
			fopp.setFoppStaffs(fopp.initStaffList(new String[] {arrUser[1][0]}));

			foProj = new FOProject(fopp, "Step-Assignment-", true, 1,
					EFormsUtil.createAnyRandomString(5));

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
			
			Assert.assertTrue(fopp.openFundingOppPlanner(), "Failed: Could not open FOPP planner!");
			
			fopp.selectFOPPStaff();
			
			GeneralUtil.switchToFO();

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

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "continueProjectWorkflow")
	public void testNewOfficerAccessToIPASS() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			foProj.setClaimNumber(2);

			Assert.assertFalse(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_Ready_StatusSubView));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "testNewOfficerAccessToIPASS")
	public void addOfficerToGroup() throws Exception {
		try {

			GeneralUtil.switchToPO();

			Users user = new Users(1, arrUser, "User", "Program Office Users");

			Assert.assertTrue(user.setUserToGroup(indx,
					IPreTestConst.Groups[6][0][0], arrUser[0][0], true));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "addOfficerToGroup")
	public void testAddedOfficerAccessToIPASS() throws Exception {
		try {
			
			GeneralUtil.takeANap(3.0);

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);

			Assert.assertTrue(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_Ready_StatusSubView));
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyById("/textBox/"));
			
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "testAddedOfficerAccessToIPASS")
	public void removeOfficerFromGroup() throws Exception {
		try {

			GeneralUtil.switchToPO();

			Users user = new Users(1, arrUser, "User", "Program Office Users");

			Assert.assertTrue(user.setUserToGroup(indx,
					IPreTestConst.Groups[6][0][0], arrUser[0][0],false));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "removeOfficerFromGroup")
	public void testRemovedOfficerAccessToIPASS() throws Exception {
		try {

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);

			Assert.assertFalse(ProjectUtil.openInitialClaimForm(foProj,
					IGeneralConst.initialClaim[0][0],
					IFiltersConst.openProjView,
					IFiltersConst.submissionVersion_LatestVersion,
					IFiltersConst.submissionsStatus_InProgress_StatusSubView));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, IGeneralConst.gnrl_SubmissionA[0][0]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
