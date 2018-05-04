/**
 * 
 */
package test_Suite.tests.r3_0.submissionAccess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
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
public class TestStepStaffAccessToFOFacingSubmission {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users user;
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Project proj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front2");
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			fopp = new FundingOpportunity("A","-eLists-PA-","");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectNG" }, alwaysRun=true)
	public void tearDown() {
		
		lbf  = null;
		user  = null;
		fopp  = null;
		foProj  = null;
		proj  = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" })
	public void createFOProjectNG() throws Exception {
		try {

			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 2, EFormsUtil.createRandomString(5));

			foProj.createFOProject();
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testSubStepStaffInApplicantSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithSubClerk("1");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Could not open Submission as Sub-Step Staff");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Sub-Step Staff Member should have Full Access");
			
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testSubStepStaffInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithSubClerk("1");
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "Open Projects"),"FAIL: Should not be able to Open Submission as Sub-Step Staff Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testSubStepSaffProjectCreationNG() throws Exception {
		try {
		
			GeneralUtil.switchToPOWithSubClerk("1");
			
			Assert.assertTrue(foProj.createNewPOProjectOnly(true), "FAIL: Could not Create new Project as Sub-Step Staff Member!");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Could not open Submission as Sub-Step Staff Member");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Sub-Step Staff Member should have Full Access");
		
			ClicksUtil.returnFromAnyForm();
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testStepStaffInApplicantSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk("5");
	
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Could not open Submission as Step Staff Member");
		
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Step Staff Member should have Full Access");
		
			ClicksUtil.returnFromAnyForm();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testStepSaffProjectCreationNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk("5");
		
			Assert.assertTrue(foProj.createNewPOProjectOnly(true), "FAIL: Could not Create new Project as Step Staff Member!");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Could not open Submission as Step Staff Member");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Step Staff Member should have Full Access");
		
			ClicksUtil.returnFromAnyForm();
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void testStepStaffInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk("5");
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "Open Projects"),"FAIL: Should not be able to Open Submission as Removed Staff Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
