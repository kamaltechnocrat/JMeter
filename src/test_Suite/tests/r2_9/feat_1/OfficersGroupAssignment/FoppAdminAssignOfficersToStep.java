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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class FoppAdminAssignOfficersToStep {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;
	
	String indx = "3";
	
	String[][] arrUser = IPreTestConst.MultiUsers[17];
	
	String newUser = arrUser[0][0] + indx;

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
			
			proj = new Project(fopp,"Step-Assignment-", true,true,EFormsUtil.createAnyRandomString(5));

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
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="createOrgAndProject")
	public void testNewOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertFalse(proj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInBasket(proj, IGeneralConst.gnrl_SubmissionA[0][0]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testNewOfficerAccess")
	public void addOfficerToGroup() throws Exception {
		try {
		
			GeneralUtil.switchToPO();
		
			Users user = new Users(1,arrUser,"User", "Program Office Users");
		
			Assert.assertTrue(user.setUserToGroup(indx, IPreTestConst.Groups[6][0][0], arrUser[0][0], true));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="addOfficerToGroup")
	public void testAddedOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.takeANap(3.0);
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(proj, IGeneralConst.gnrl_SubmissionA[0][0]));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testAddedOfficerAccess")
	public void removeOfficerFromGroup() throws Exception {
		try {	
		
			GeneralUtil.switchToPO();
		
			Users user = new Users(1,arrUser,"User", "Program Office Users");
		
			Assert.assertTrue(user.setUserToGroup(indx, IPreTestConst.Groups[6][0][0], arrUser[0][0], false));

			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="removeOfficerFromGroup")
	public void advanceProject() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="advanceProject")
	public void testRemovedOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertFalse(ProjectUtil.openAssignEvaluators(proj,IProgramsConst.EProjectType.pre_Award.ordinal()));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInBasket(proj, IGeneralConst.gnrl_SubmissionA[0][0]));			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="advanceProject")
	public void testRemovedOfficerAccessToAward() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			proj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			Assert.assertFalse(ProjectUtil.openAwardInList(proj));
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
		

}
