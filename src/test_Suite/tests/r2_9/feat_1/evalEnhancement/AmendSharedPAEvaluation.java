/**
 * 
 */
package test_Suite.tests.r2_9.feat_1.evalEnhancement;

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
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.AmendmentsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AmendSharedPAEvaluation {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;
	
	private static final String newPASuffix = "-pa";

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
			
			proj = new Project(fopp,"Amend-Shared-PA-Eval-", true,true,EFormsUtil.createAnyRandomString(5));

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
	public void continueProjectWorkflow() throws Exception {
		try {	
			
			Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.approvQuoCritManu[0][0]));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritManu[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			Assert.assertTrue(proj.submitAward("Standard", 2, true));			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueProjectWorkflow")
	public void startPostAwardProjectWorkflow() throws Exception {
		try {		
			
			proj.setClaimNumber(2);
			
			Assert.assertTrue(proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0], true));
			
			Assert.assertTrue(proj.assignAllAvailableEvaluator(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix));			
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="startPostAwardProjectWorkflow")
	public void submitEvaluationSteps() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix, true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0] + newPASuffix, true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView,false, false));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="submitEvaluationSteps")
	public void amendEvaluationStep() throws Exception {
		try {	
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(proj, "All", IGeneralConst.reviewQuoCritManu[0][0] + newPASuffix);

			Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, proj));
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="amendEvaluationStep")
	public void changeAmenderEvaluationStep() throws Exception {
		try 
		{			
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.reviewQuoCritManu[0][0]));
			
			Assert.assertTrue(AmendmentsUtil.changeAmender(proj,IPreTestConst.MultiUsers[1][0][1] + "1"));
		} 
		catch (Exception e) 
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="changeAmenderEvaluationStep")
	public void checkNewAmederPersist() throws Exception {
		try 
		{	
			
			Assert.assertTrue(FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_AmendmentStatus_Lbl, "", IFiltersConst.amendments_InProgressView));
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(proj,UsersAndGroupsUtil.initializePOFullUserId(IPreTestConst.MultiUsers[1], "1")));
			
		} 
		catch (Exception e) 
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
