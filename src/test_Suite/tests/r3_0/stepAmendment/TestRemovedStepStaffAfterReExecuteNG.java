/**
 * 
 */
package test_Suite.tests.r3_0.stepAmendment;

import java.util.ArrayList;
import java.util.List;

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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
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
public class TestRemovedStepStaffAfterReExecuteNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users financial1;
	
	Users clerk1;
	
	Users clerk2;
	
	List<String> ss;
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	boolean add = false;
	boolean remove = true;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			fopp = new FundingOpportunity("A","-StepAmendment-PA-","");

			foProj = new FOProject(fopp, "", true, 2, "-Removed-SS-After-ReExec");
			
			ss = new ArrayList<String>();
			
			ss.add(IPreTestConst.Groups[20][0][0]);
			
			addOrRemoveStepStaffToApprovalStep(add);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		GeneralUtil.switchToPO();
		
		addOrRemoveStepStaffToApprovalStep(remove);
		
		fopp = null;
		foProj = null;
		lbf = null;
		clerk1 = null;
		clerk2 = null;
		financial1 = null;
		ss =  null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createFOProjectNG() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front2");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG")
	public void testAccessToEvaluationStep() throws Exception {
		try {
			
			GeneralUtil.switchToPOWithClerk("1");
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "No Propreties Fields LBF", false,"1", IFiltersConst.versionNumber_EqualTo);
			
			Assert.assertTrue(GeneralUtil.isSelectListEnabledById("/0:numericDropdown/"), "FAIL: The Form should be in Read Write mode!");
			
			ClicksUtil.returnFromAnyForm();			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testAccessToEvaluationStep")
	public void testRemovedStepStaffGroup() throws Exception {
		
		GeneralUtil.switchToPO();
		
		addOrRemoveStepStaffToApprovalStep(remove);	
		
		GeneralUtil.switchToPOWithClerk("1");
		
		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
		
		ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_InProgress_StatusSubView, "No Propreties Fields LBF", false,"1", IFiltersConst.versionNumber_EqualTo);
		
		Assert.assertFalse(GeneralUtil.isSelectListEnabledById("/0:numericDropdown/"), "FAIL: The Form should be in Read Only mode!");	
	}
	
	private void addOrRemoveStepStaffToApprovalStep(boolean removeStaff) throws Exception {
		try {
			
			fopp.openFundingOppPlanner();
			
			fopp.expandAnObject("Steps");
			
			foProj.initializeStep("Approval-CRQA");
			
			fopp.setCurrentStepIdent(foProj.getCurrentStepIdent());
			
			fopp.expandAnObject(foProj.getCurrentStepIdent());
			
			fopp.manageStepStaff(ss, removeStaff);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
