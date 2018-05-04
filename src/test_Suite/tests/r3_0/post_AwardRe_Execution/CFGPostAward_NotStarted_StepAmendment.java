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

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IProjectsConst.EcfgProjectsName;
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

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CFGPostAward_NotStarted_StepAmendment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

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
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-NotStarted-PA-Step");
			
			FrontOfficeUtil.registerAndSubmitProject(fopp, foProj, "Ouia 3");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void testNotStarted_PANG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.NOTSTARTED), "FAIL: something wrong check log");
						
			foProj.setProjFullName(foProj.getProjFOFullName());
			
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, "Closeout Approval", IFiltersConst.openProjView),"FAIL: Should Not Open PO Submission");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testNotStarted_PANG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardStep);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IProjectsConst.cfgAwardStep);
			
			String[] amenders = new String []{"Submission","PO: ".concat("Staff")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			String paStartSchedule = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, paStartSchedule));
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestStepAmendment")
	public void testClaimsInMyAssignedSubList() throws Exception {
		
		try {
			GeneralUtil.switchToPO();
			
			ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
			
			FiltersUtil.filterListByProject(foProj);
			
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_myAssignedSubmissionsTableId), 4);
			/* The behaviour of lists has changed as of 5.1.0.0
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_myAssignedSubmissionsTableId), 3); */
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestStepAmendment")
	public void testClaimsInApplicantSubList() throws Exception {
		
		try {
			GeneralUtil.switchToPO();
			
			ProjectUtil.openApplicantSubmissionList(foProj);
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
			
			FiltersUtil.filterListByProject(foProj);
			
			FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_SubmissionVersion_Lbl, "", IFiltersConst.submissionVersion_LatestVersion);

			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.applicantSubmissionTableId), 4);
			/* The behaviour of lists has changed as of 5.1.0.0
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.applicantSubmissionTableId), 3); */
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestStepAmendment")
	public void testClaimsInSubmissionsList() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front3");
			
			ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
			
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.fOSubmissionsTableId), 4);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testClaimsInSubmissionsList","testClaimsInApplicantSubList","testClaimsInMyAssignedSubList"})
	public void reSubmitAwardSchedule() throws Exception {
		
		try {
			GeneralUtil.switchToPO();
			
			ProjectUtil.openPOAwardFormletInList(foProj, IProjectsConst.cfgAwardStep, "Submission Summary");
			
			ClicksUtil.clickButtons(IClicksConst.submitBtn);			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "reSubmitAwardSchedule")
	public void continueCfgPASteps() throws Exception {
		
		Assert.assertTrue(FrontOfficeUtil.continueCfgPostAward(foProj, EcfgProjectsName.COMPLETE),"FAIL: Could not Complete Post Award!");
		
		GeneralUtil.switchToPO();
		
		foProj.setProjFullName(foProj.getProjFOFullName());
		
		Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Could Not Open PO Submission");
		
		ClicksUtil.returnFromAnyForm();
	}
}
