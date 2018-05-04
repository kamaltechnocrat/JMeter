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
public class AmendSharedEvaluation {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	Project proj;

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
			
			proj = new Project(fopp,"Amend-Shared-Eval-", true,true,EFormsUtil.createAnyRandomString(5));

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
			
			proj.assignOfficers(new String[][] {{IPreTestConst.Groups[6][0][0]}});

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void submitEvaluationSteps() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView,false, false));
			
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
			
			String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(proj, "All", IGeneralConst.reviewQuoCritAuto[0][0]);

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
			Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.reviewQuoCritAuto[0][0]),"FAIL: View Amendment Icon does not exist!");
			
			Assert.assertTrue(AmendmentsUtil.changeAmender(proj,IPreTestConst.MultiUsers[1][0][1] + "1"), "FAIL: Could not change Amender!");
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
			
			Assert.assertTrue(FiltersUtil.extraFilterListByLabel(IFiltersConst.gpa_AmendmentStatus_Lbl, "", IFiltersConst.amendments_InProgressView),"FAIL: Could add extra filter and filter the list");
			
			Assert.assertTrue(AmendmentsUtil.checkAmender(proj, UsersAndGroupsUtil.initializePOFullUserId(IPreTestConst.MultiUsers[1], "1")),"FAIL: error checking the amender");
			
		} 
		catch (Exception e) 
		{
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
