/**
 * 
 */
package test_Suite.tests.r2_9.feat_3.amendment_Enhance;

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
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.AmendmentsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class OpenSubmissionIconInAmendList {

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
			
			proj = new Project(fopp,"Open-In-Amendment-", true,true,EFormsUtil.createAnyRandomString(5));

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

	@Test(groups = { "WorkflowNG" },dependsOnMethods="createOrgAndProject")
	public void continueWorkflow() throws Exception {
		
		proj.submitFromMyAssignedSubListNew(IGeneralConst.PO_Submission_Non[0][0],"First Submit", true);
		
		proj.assignOfficersBySteps(initOfficers());
	
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginReviewer("1");
		
		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));
		
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvNonRexQuoAuto[0][0], true, IFiltersConst.evaluateSubmissions_Ready_StatusSubView, false, false));		
	}
	
	@Test(groups = { "WorkflowNG" },dependsOnMethods="continueWorkflow")
	public void testAssignGroupAsAmender() throws Exception {
		
		GeneralUtil.switchToPOWithProjOfficer("1");
		
		String[] amendParams = AmendmentsUtil.initializeAmendmentParamsNoOptionalRex(proj, "PO: " + IPreTestConst.Groups[6][0][0], IGeneralConst.gnrl_SubmissionA[0][0]);
		
		Assert.assertTrue(AmendmentsUtil.issueAmendment(amendParams, proj));
		
		Assert.assertTrue(AmendmentsUtil.isViewAmendmentSubmissionIconExists(proj,IGeneralConst.gnrl_SubmissionA[0][0]));
		
		Assert.assertTrue(AmendmentsUtil.isViewAmendmentIconExists(proj,IGeneralConst.gnrl_SubmissionA[0][0]));
		
		Assert.assertEquals(TablesUtil.findHowManyInTable(ITablesConst.amendmentsTableId, proj.getCurrentStepName()), 1, "FAIL: wrong Number of entries!");
	}
	
	private Hashtable<String,String[]> initOfficers() throws Exception {
		
		Hashtable<String,String[]> ht = new Hashtable<String,String[]>();
		
		proj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
		
		ht.put(proj.getCurrentStepName(), new String[] {IPreTestConst.Groups[6][0][0]});
		
		proj.initializeStep(IGeneralConst.reviewQuoCritAuto[0][0]);
		
		ht.put(proj.getCurrentStepName(), new String[] {IPreTestConst.Groups[6][0][0]});
		
		proj.initializeStep(IGeneralConst.approvNonRexQuoAuto[0][0]);
		
		ht.put(proj.getCurrentStepName(), new String[] {IPreTestConst.Groups[6][0][0]});
		
		return ht;
		
	}

}
