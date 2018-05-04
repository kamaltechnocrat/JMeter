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
import test_Suite.constants.workflow.IProjectsConst.EcfgReportsTypes;
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
public class CFGPostAwardStepAmendment {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Users clerk1;

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
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-Amend-PA-Step");
			
			FrontOfficeUtil.registerAndSubmitProject(fopp, foProj, "Ouia 3");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		clerk1= null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void testCompleted_PANG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.COMPLETE), "FAIL: something wrong check log");
						
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
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testCompleted_PANG")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep("Closeout Approval");
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardStep);
			
			clerk1 = SubAccessUtil.getUser(1,16,"Program Office Users");
			
			String[] amenders = new String []{"Submission","PO: ".concat(clerk1.getUserFullId())};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			String paStartSchedule = "Quarterly Report1";
			
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
	public void testAmendedClaimsInMyAssignedSubList() throws Exception {
		
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
			
			FiltersUtil.filterListByProject(foProj);

			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_myAssignedSubmissionsTableId), 1);
			/* The behaviour of lists has changed as of 5.1.0.0
			Assert.assertEquals(TablesUtil.howManyEntriesInTable(ITablesConst.fundingOpp_myAssignedSubmissionsTableId), 0); */
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testAmendedClaimsInMyAssignedSubList")
	public void testAmendedClaimsInApplicantSubList() throws Exception {
		
		try {
			
			ProjectUtil.openApplicantSubmissionList(foProj);
			
			foProj.initializeStep("Post Award Submission");
			
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
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testAmendedClaimsInApplicantSubList"})
	public void testAccessToAmendedClaimsByPOG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.openCfgClaim(EcfgReportsTypes.ANNUAL, foProj, ITablesConst.applicantSubmissionTableId),"Could Not Open Claim");
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(GeneralUtil.isButtonExistsByValue(IClicksConst.submitBtn),"FAIL: Could not find Submit Button!");
			
			Assert.assertFalse(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Submit Button should be disabled!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testAccessToAmendedClaimsByPOG"})
	public void testAccessToAmendedClaimsByAmender() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithClerk("1");
			
			ProjectUtil.openApplicantSubmissionList(foProj);
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
    		
    		String projName = foProj.getProjFOFullName();
    		
    		foProj.setProjFullName(projName.concat(" - ".concat(IProjectsConst.cfgReportNames[EcfgReportsTypes.QUARTERLY.ordinal()]).concat("1")));
			
			FiltersUtil.filterListByProject(foProj);
			
			Assert.assertTrue(FrontOfficeUtil.openCfgClaim(EcfgReportsTypes.QUARTERLY, foProj, ITablesConst.applicantSubmissionTableId),"Could Not Open Claim");
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(GeneralUtil.isButtonExistsByValue(IClicksConst.submitBtn),"FAIL: Could not find Submit Button!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Submit Button should be enabled!");
			
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testAccessToAmendedClaimsByAmender"})
	public void testAccessToAmendedClaimsByAmenderInFO() throws Exception {
		
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front3");
			
			FrontOfficeUtil.openFOSubmissionAndFilter(foProj,IFiltersConst.openProjView,IFiltersConst.submissionVersion_LatestVersion);
			
			Assert.assertTrue(FrontOfficeUtil.openCfgClaim(EcfgReportsTypes.ANNUAL, foProj, ITablesConst.fOSubmissionsTableId),"Could Not Open Claim");
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(GeneralUtil.isButtonExistsByValue(IClicksConst.submitBtn),"FAIL: Could not find Submit Button!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Submit Button should be enabled!");
			
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
			ClicksUtil.returnFromAnyForm();
			
			Assert.assertTrue(FrontOfficeUtil.openCfgClaim(EcfgReportsTypes.FINANCIAL, foProj, ITablesConst.fOSubmissionsTableId),"Could Not Open Claim");
			
			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			
			Assert.assertTrue(GeneralUtil.isButtonExistsByValue(IClicksConst.submitBtn),"FAIL: Could not find Submit Button!");
			
			Assert.assertTrue(GeneralUtil.isButtonEnabled(IClicksConst.submitBtn),"FAIL: Submit Button should be enabled!");
			
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = {"testAccessToAmendedClaimsByAmenderInFO"})
	public void continueCfgPostAward() throws Exception {
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueReExecutedCfgPostAward(foProj, IProjectsConst.EcfgProjectsName.COMPLETE), "FAIL: Could not Continue Re-Execute Post Award");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
