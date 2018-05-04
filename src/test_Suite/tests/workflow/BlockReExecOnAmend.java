/**
 * 
 */
package test_Suite.tests.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class BlockReExecOnAmend {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String preFix = IFoppConst.fundingOpp_Prefix;
	String postFix = "";
	
	// Steps Used
	String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
	String reviewStep[][] = IGeneralConst.reviewQuoCritAuto;
	String approvStep[][] = IGeneralConst.approvQuoCritAuto;
	String awardStep[][] = IGeneralConst.gnrl_AwardCrit;

	FundingOpportunity fopp;	
	Project proj;

	// ---------------------------- End of Params
	
	@BeforeClass(groups="WorkflowNG")
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		//------------------------------

	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		proj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {

		initializeFundingOpp();
		newPOProject();
		assignOfficers();
		evaluateApplication();
		
		requestAmendment();
		
		reSubmit();
	}
	
	@Test(groups= {"WorkflowNG"}, dependsOnMethods = "initialize")
	public void testWorkflowBlocked() throws Exception {
		
		Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(proj, awardStep[0][0], "Agreement Details"), "FAIL: could not open Award Form");
		
		Assert.assertTrue(GeneralUtil.isTextFieldReadOnly(0),"FAIL: Award Form should be Blocked!");
		
		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);
		
		Assert.assertTrue(proj.approveAmendedSubmission(approvStep[0][0], true, "In Progress"));
		
		proj.submitAmendedBasicAward(true);		
	}


	private void initializeFundingOpp() throws Exception {
		
		fopp = new FundingOpportunity("A", preFix,postFix);
	}

	private void newPOProject() throws Exception {

		proj = new Project(fopp,"Block-ReExec-",true,true,EFormsUtil.createAnyRandomString(5));
		
		proj.newCreateNewOrg();
		proj.newCreateNewPOProject();	

		proj.submitProject(true);
	}

	private void assignOfficers() throws Exception {
		proj
				.assignOfficers(new String[][] {
						{ IPreTestConst.Groups[0][0][0],
								IPreTestConst.Groups[0][1][0] }});
	}

	private void evaluateApplication() throws Exception {
		
		Assert.assertTrue(proj.reviewSubmission(reviewStep[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView));

		Assert.assertTrue(proj.approveSubmission(approvStep[0][0], true,
				"Ready", false, false));
	}
	
	private void requestAmendment() throws Exception {	


		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil
				.issueAmendment(new String[] {
						proj.getProjFullName(), submissionStep[0][0],
						IPreTestConst.Groups[0][1][0], dd,
						"Test Re-Execute on Previous Amendment",
						"This a Comment" }, proj));
	}
	
	private void reSubmit() throws Exception {
		
		proj.submitFromMyAssignedSubList(proj.getProjFullName(), submissionStep[0][0], submissionStep[0][0], true, "Re-Executed", proj);
		
		Assert.assertTrue(proj.reviewAmendedSubmission(reviewStep[0][0], true));
	}
	

}
