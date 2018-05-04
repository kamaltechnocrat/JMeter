/**
 * 
 */
package test_Suite.tests.r3_0.stepAmendment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestRequestStepAmendmentPage {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users amender;
	
	Users approver1;
	
	Users approver2;
	
	Users approver3;
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front2");
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			fopp = new FundingOpportunity("A","-StepAmendment-PA-","");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		lbf = null;
		amender = null;
		approver1 = null;
		approver2 = null;
		approver3 =  null;
		
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

			foProj = new FOProject(fopp, "", true, 2, "-RequestAmendPage");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG", dataProvider="steps")
	public void verifyAvailableSteps(String stepName, boolean expected) throws Exception {
		
		try {
			
			foProj.initializeStep(stepName);
			
			Assert.assertEquals(GeneralUtil.isObjectExistsInList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, foProj.getCurrentStepName()), expected);
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="verifyAvailableSteps")
	public void submitPOSubmissionNon() throws Exception {
		
		try {
			
			foProj.setClaimNumber(0);
			
			foProj.initializeStep(IGeneralConst.PO_Submission_Non[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);
			
			Assert.assertTrue(FiltersUtil.filterListByProject(foProj));
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView));
			
			SubAccessUtil.submitAndReturnFromForm();
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="submitPOSubmissionNon", dataProvider="amendrs")
	public void verifyAvailableAmender(String stepName, int userBeat, int index, boolean expected) throws Exception {
		
		try {
			
			foProj.initializeStep(stepName);
			
			amender = SubAccessUtil.getUser(userBeat,index,"Program Office Users");
			
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, foProj.getCurrentStepName()),"FAIL: Could not find the Step to Amend");
			
			GeneralUtil.takeANap(2.0);
			
			Assert.assertEquals(GeneralUtil.isObjectExistsInList(IAmendmentsConst.amendRequest_Amender_DropDwnFld_ID, amender.getUserFullId()), expected);
			
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="submitPOSubmissionNon", dataProvider="evals")
	public void verifyAvailableAmenderForApproval(String stepName, int userBeat, int index, boolean expected) throws Exception {
		
		try {
			
			foProj.initializeStep(stepName);
			
			amender = SubAccessUtil.getUser(userBeat,index,"Program Office Users");
			
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, foProj.getCurrentStepName()),"FAIL: Could not find the Step to Amend");
			
			GeneralUtil.takeANap(2.0);
			
			log.warn(amender.getUserFullId());
			
			Assert.assertEquals(GeneralUtil.isObjectExistsInList(IAmendmentsConst.stepAmend_NonShared_AAFS_M2MId, amender.getUserFullId()), expected);
			
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "steps")
	public Object[][] generateSteps() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],true},
				new Object[] {IGeneralConst.PO_Submission_Non[0][0],false},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],true},
				new Object[] {IGeneralConst.pa_AwardCrit[0][0],true},
				new Object[] {IGeneralConst.initialClaim[0][0],false}
		};		
		
		return result;		
	}

	@DataProvider(name = "amendrs")
	public Object[][] generateAAFS() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],1,4,true},
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],2,5,true},
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],3,16,true},
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],4,17,true},
				new Object[] {IGeneralConst.PO_Submission_Non[0][0],2,4,true},
				new Object[] {IGeneralConst.PO_Submission_Non[0][0],5,5,true},
				new Object[] {IGeneralConst.pa_AwardCrit[0][0],5,4,true},
				new Object[] {IGeneralConst.pa_AwardCrit[0][0],4,5,true},
				new Object[] {IGeneralConst.pa_AwardCrit[0][0],3,6,false}
		};		
		
		return result;		
	}

	@DataProvider(name = "evals")
	public Object[][] generateEvals() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],1,4,false},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],2,5,false},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],1,0,true},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],2,0,true},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],3,0,true},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],4,0,true},
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],5,0,true}
		};		
		
		return result;		
	}
}
