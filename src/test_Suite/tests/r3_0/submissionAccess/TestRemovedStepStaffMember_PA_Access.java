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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestRemovedStepStaffMember_PA_Access {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users user;
	
	String indx = "5";
	
	String[][] clerks = IPreTestConst.MultiUsers[16];
	
	String[][] subClerks = IPreTestConst.MultiUsers[17];
	
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
			
			user = new Users();
			
			fopp = new FundingOpportunity("A","-eLists-PA-","");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectNG" }, alwaysRun=true)
	public void tearDown() {
		
		try{	
			
			GeneralUtil.switchToPO();

			Assert.assertTrue(user.setUserToGroup(indx, clerks[1][0], clerks[0][0], true));

			Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", clerks[0][0], false));	
			
			Assert.assertTrue(user.setUserToGroup(indx, subClerks[1][0], subClerks[0][0], true));	
			
			Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", subClerks[0][0], false));
			
			lbf  = null;
			user  = null;
			fopp  = null;
			foProj  = null;
			proj  = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
		
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
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="createFOProjectNG")
	public void removePOGMember() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(user.setUserToGroup(indx, clerks[1][0], clerks[0][0], false));

		Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", clerks[0][0], true));	
		
		Assert.assertTrue(user.setUserToGroup(indx, subClerks[1][0], subClerks[0][0], false));	
		
		Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", subClerks[0][0], true));	
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedSubStepStaffInApplicantSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithSubClerk(indx);
			
			Assert.assertFalse(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,ILBFunctionConst.lbf_IPASSource[0][0], "All"),"FAIL: Should not be able to Open Submission as Removed Sub-Step Staff Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedSubStepStaffInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithSubClerk(indx);
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects"),"FAIL: Should not be able to Open Submission as Removed Sub-Step Staff Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedStepStaffInApplicantSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk(indx);
	
			Assert.assertFalse(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,ILBFunctionConst.lbf_IPASSource[0][0], "All"),"FAIL: Should not be able to Open Submission as Removed Step Staff Member");
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedStepStaffInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk(indx);
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects"),"FAIL: Should not be able to Open Submission as Removed Step Staff Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
