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
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubHistoryUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class NewGroupAssignmentToStep {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;
	
	String indx = "5";
	
	String[][] arrUser = IPreTestConst.MultiUsers[14];
	
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
			
			fopp = new FundingOpportunity("A","-Officers-","");
			
			proj = new Project(fopp,"New-Group-Assignment-", true,true,EFormsUtil.createAnyRandomString(5));

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
	public void addNewGroupToPOSubmissionStep() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			fopp.changeGroupOfficerInStep(IGeneralConst.PO_Submission_Non[0][0], arrUser[1][0]);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="addNewGroupToPOSubmissionStep")
	public void testNewOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(proj, IGeneralConst.gnrl_SubmissionA[0][0]));
			
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);
			
			Assert.assertFalse(SubHistoryUtil.openProjectHistory(proj.getProjFullName()));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testNewOfficerAccess")
	public void assignNewGroupToProject() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			proj.initializeStep(IGeneralConst.PO_Submission_Non[0][0]);
			
			Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
			
			ht.put(proj.getCurrentStepName(), new String[] {arrUser[1][0]});
			
			proj.assignOfficersBySteps(ht);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="assignNewGroupToProject")
	public void testAssignedNewOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
			ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);
			
			Assert.assertTrue(SubHistoryUtil.openProjectHistory(proj.getProjFullName()));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="testAssignedNewOfficerAccess")
	public void removeNewGroupToPOSubmissionStep() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(fopp.changeGroupOfficerInStep(IGeneralConst.PO_Submission_Non[0][0], IPreTestConst.Groups[6][0][0]));
			
			fopp.setFoppStaffs(fopp.initStaffList(new String[] {arrUser[1][0]}));
			
			fopp.removeFOPPStaff();
			
			proj.initializeStep(IGeneralConst.PO_Submission_Non[0][0]);
			
			Hashtable<String, String[]> ht = new Hashtable<String, String[]>();
			
			ht.put(proj.getCurrentStepName(), new String[] {IPreTestConst.Groups[6][0][0]});
			
			proj.assignOfficersBySteps(ht);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"WorkflowNG"},dependsOnMethods="removeNewGroupToPOSubmissionStep")
	public void testUnAssignedNewOfficerAccess() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(newUser);
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
			Assert.assertFalse(SubHistoryUtil.openProjectHistory(proj.getProjFullName()));
			
			Assert.assertFalse(ProjectUtil.openSubmissionInBasket(proj, IGeneralConst.gnrl_SubmissionA[0][0]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
