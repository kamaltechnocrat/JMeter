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
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestRemovedPOGMemberAccess {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users user;
	
	String indx = "5";
	
	String[][] projOfficer = IPreTestConst.MultiUsers[5];
	
	String[][] progOfficer = IPreTestConst.MultiUsers[4];
	
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

			Assert.assertTrue(user.setUserToGroup(indx, projOfficer[1][0], projOfficer[0][0], true));

			Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", projOfficer[0][0], false));	
			
			Assert.assertTrue(user.setUserToGroup(indx, progOfficer[1][0], progOfficer[0][0], true));	
			
			Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", progOfficer[0][0], false));
			
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
		
		Assert.assertTrue(user.setUserToGroup(indx, progOfficer[1][0], progOfficer[0][0], false));	
		
		Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", progOfficer[0][0], true));	
		
		GeneralUtil.switchToPOWithProgOfficer("5");
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedSubPOGAccessInApplicantSubmissionList() throws Exception {
		try {
	
			Assert.assertFalse(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Should not be able to open Submissions as a removed Sub-POG Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedSubPOGInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			//GeneralUtil.switchToPOWithProgOfficer("5");
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "Open Projects"),"FAIL: Should not be able to open Submissions as a removed Sub-POG Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOGMember")
	public void testRemovedSubPOGProjectCreationNG() throws Exception {
		try {
		
			//GeneralUtil.switchToPOWithProgOfficer("5");
			
			Assert.assertFalse(foProj.canCreatePOProjectOnly());
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods={"testRemovedSubPOGProjectCreationNG","testRemovedSubPOGInMyAssignedSubmissionListNG","testRemovedSubPOGAccessInApplicantSubmissionList"})
	public void removePOG2Member() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(user.setUserToGroup(indx, projOfficer[1][0], projOfficer[0][0], false));

		Assert.assertTrue(user.setUserToGroup(indx, "G07 No Officers", projOfficer[0][0], true));
		
		GeneralUtil.switchToPOWithProjOfficer("5");
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOG2Member")
	public void testRemovedPOGAccessInApplicantSubmissionList() throws Exception {
		try {
			
			//GeneralUtil.switchToPOWithProjOfficer("5");
	
			Assert.assertFalse(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "All"),"FAIL: Should Not be able to open Submissions as a removed POG Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOG2Member")
	public void testRemovedPOGInMyAssignedSubmissionListNG() throws Exception {
		try {
			
			//GeneralUtil.switchToPOWithProjOfficer("5");
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj,IGeneralConst.gnrl_SubmissionA[0][0], "Open Projects"),"FAIL: Should Not be able to open Submissions as a removed POG Member");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectNG" }, dependsOnMethods="removePOG2Member")
	public void testRemovedPOGProjectCreationNG() throws Exception {
		try {
			
			Assert.assertFalse(foProj.canCreatePOProjectOnly());
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
