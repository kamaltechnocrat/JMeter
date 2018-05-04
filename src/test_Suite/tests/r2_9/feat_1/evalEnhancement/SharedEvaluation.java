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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SharedEvaluation {

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
			
			proj = new Project(fopp,"Shared-Eval-", true,true,EFormsUtil.createAnyRandomString(5));

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
	public void testInProgressReveiw() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");	
			
			Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], false, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("5");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("2");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("4");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("3");	
			
			Assert.assertTrue(proj.reviewAmendedSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" },dependsOnMethods="testInProgressReveiw")
	public void testInProgressApproval() throws Exception {
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");	
			
			Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView,false, false));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("5");	
			
			Assert.assertTrue(proj.approveAmendedSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], false, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("2");	
			
			Assert.assertTrue(proj.approveAmendedSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], false, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("4");	
			
			Assert.assertTrue(proj.approveAmendedSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], false, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("3");	
			
			Assert.assertTrue(proj.approveAmendedSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], false, IFiltersConst.evaluateSubmissions_InProgress_StatusSubView));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
