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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
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
public class TestAddedStepStaffBeforeReExecuteNG {

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

			foProj = new FOProject(fopp, "", true, 2, "-Added-SS-Before-ReExec");
			
			ss = new ArrayList<String>();
			
			ss.add(IPreTestConst.Groups[20][0][0]);
			
			ss.add(IPreTestConst.Groups[19][0][0]);
			
			addOrRemoveStepStaffToApprovalStep(remove);
			
			ss.remove(IPreTestConst.Groups[19][0][0]);

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
		clerk1= null;
		clerk2= null;
		lbf = null;
		financial1 = null;
		ss = null;
		
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
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "createFOProjectNG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IGeneralConst.postAwardCrit[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
			
			clerk1 = SubAccessUtil.getUser(1, 16, "Program Office Users");
			
			String[] amenders = new String []{"Submission", "PO: ".concat(clerk1.getUserFullId())};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, ""));
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestStepAmendment")
	public void addStepStaffToApprovalStep() throws Exception {
		
		try {

			
			GeneralUtil.switchToPO();
			addOrRemoveStepStaffToApprovalStep(add);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "addStepStaffToApprovalStep")
	public void reSubmitApplication() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithClerk("1");
			
			Assert.assertTrue(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: Could not open Applicant Submission");
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			foProj.setClaimNumber(0);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitApplication")
	public void addAnotherStepStaff() throws Exception {
		try {
			
			ss.add(IPreTestConst.Groups[19][0][0]);
			
			GeneralUtil.switchToPO();
			addOrRemoveStepStaffToApprovalStep(add);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "addAnotherStepStaff")
	public void testStepAmendmentPageAfterStepStaffAdded() throws Exception{
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			AmendmentsUtil.openStepAmendmentDetails(foProj, "Approval-CRQA", "1", "");
			
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.amendedSubmissionsTableId),15, "FAIL: Number of Entries did not match!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testStepAmendmentPageAfterStepStaffAdded")
	public void transferAmendmentToNewlyAddedStaff() throws Exception {
		
		try {
			clerk2 = SubAccessUtil.getUser(2, 16, "Program Office Users");
			
			financial1 = SubAccessUtil.getUser(2, 14, "Program Office Users");
			
			financial1.setUserFullId("PO: ".concat(financial1.getUserFullId()));
			
			Assert.assertTrue(AmendmentsUtil.changeAmenderForStepAmendment(foProj, "Approval-CRQA", clerk2, financial1),"FAIL: Error Changing an Amender!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	private void addOrRemoveStepStaffToApprovalStep(boolean removeStaff) throws Exception {
		try {
			
			Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not open FOPP Planner!");
			
			Assert.assertTrue(fopp.expandAnObject("Steps"), "FAIL: Could not expand an opject in FOPP planner!");
			
			foProj.initializeStep("Approval-CRQA");
			
			fopp.setCurrentStepIdent(foProj.getCurrentStepIdent());
			
			Assert.assertTrue(fopp.expandAnObject(foProj.getCurrentStepIdent()), "FAIL: Could not expand an opject in FOPP planner!");
			
			fopp.manageStepStaff(ss, removeStaff);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
