/**
 * 
 */
package test_Suite.tests.r3_0.post_AwardRe_Execution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
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
public class CFGPostAward_NotApproved_StepAmendment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Users user;
	
	Users reviewer1;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front3");
			// -----------------------------------
			
			fopp = new FundingOpportunity("CFG-", "Post-Award-Step-Amendment", "", "");
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-NotApproved-PA-Step");
			
			FrontOfficeUtil.registerAndSubmitProject(fopp, foProj, "Ouia 3");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		user = null;
		reviewer1 = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void testNotApprovedNG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.NOTAPPROVED), "FAIL: something wrong check log");
						
			GeneralUtil.switchToPO();
			
			foProj.setProjFullName(foProj.getProjFOFullName());
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Could Not Open PO Submission");
			
			ClicksUtil.returnFromAnyForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testNotApprovedNG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IProjectsConst.cfgCloseOutStep);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IProjectsConst.cfgReviewStep);
			
			reviewer1 = SubAccessUtil.getUser(1,1,"Program Office Users");
			
			String[] amenders = new String []{"Submission","PO: ".concat(reviewer1.getUserFullId())};

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
	public void reSubmitSharedReview() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginReviewer("1");
			
			Assert.assertTrue(foProj.reviewAmendedSubmission(IProjectsConst.cfgReviewStep, true), "FAIL: Error Re-Submitting Grant Review");	

			GeneralUtil.switchToPO();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitSharedReview")
	public void testClosingStepFirstVersion() throws Exception {
		
		try {			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Could Not Open PO Submission");
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyByTtl("Enter Any Text"), "FAIL: the Form Should be Read Only!");
			
			ClicksUtil.returnFromAnyForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitSharedReview")
	public void testAwardFirstVersion() throws Exception {
		
		try {
			
			foProj.initializeStep(IProjectsConst.cfgAwardStep);
			
			Assert.assertTrue(ProjectUtil.openAwardInList(foProj), "FAIL: Could not Open Award Step");
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyByTtl("Enter Any Text"), "FAIL: the Form is Read Only!");
			
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_Version_Lbl, "", IFiltersConst.submissionVersion_AllVersion), "FAIL: could not change Submission Version filter!");
			
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.awardsTableId),1,"FAIL: the number of entries not as expected!");
			
			Assert.assertEquals(TablesUtil.getCellContent(ITablesConst.awardsTableId, 0, 5, 0), "1","FAIL: Version should be 1");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testAwardFirstVersion","testClosingStepFirstVersion"})
	public void continueCfgWorkflow() throws Exception {
		
		try{
			
			Assert.assertTrue(FrontOfficeUtil.submitCFGAwardSchedule(foProj), "FAIL: Error occured during submiting CFG Award Schedule!");
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgPostAward(foProj, IProjectsConst.EcfgProjectsName.NOTCOMPLETE), "FAIL: Could not complete Post Award!");
		
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}
}
