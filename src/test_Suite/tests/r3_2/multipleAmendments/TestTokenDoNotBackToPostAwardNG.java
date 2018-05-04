/**
 * 
 */
package test_Suite.tests.r3_2.multipleAmendments;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IProjectsConst.EcfgReportsTypes;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestTokenDoNotBackToPostAwardNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Users user;
	
	Users reviewer1;
	
	Users clerk1;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front3");
			// -----------------------------------
			
			fopp = new FundingOpportunity("CFG-", "Post-Award-Step-Amendment", "", "");
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-TokenNotBackTo-PA-Step");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		reviewer1 = null;
		clerk1 = null;
		user = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void completeWorkFlowToPaStep() throws Exception {
		try {
			
			Assert.assertTrue(FrontOfficeUtil.registerAndSubmitProject(fopp, foProj, "Ouia 3"), "FAIL: could not submit new Project");
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.COMPLETE), "FAIL: something wrong check log");
			
			GeneralUtil.switchToPO();
			
			foProj.setProjFullName(foProj.getProjFOFullName());
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Not able Open PO Submission");
			
			Assert.assertTrue(GeneralUtil.isTextFieldExistsById(IProjectsConst.gps_Milestone_NameTxtId), "FAIL: Could not find The Textfield");
			
			Assert.assertFalse(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Milestone_NameTxtId), "FAIL: the Text field should be writable");
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "completeWorkFlowToPaStep")
	public void requestStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IProjectsConst.cfgCloseOutStep);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IProjectsConst.cfgAwardStep);
			
			String[] amenders = new String []{"Submission","PO: ".concat("Staff")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			String paStartSchedule = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendmentWithSubProjectReExecute(foProj,amenders,dd,reason,comment,true, paStartSchedule));
			
			Hashtable<String,Boolean> subOptional = new Hashtable<String,Boolean>();
			
			subOptional.put(IProjectsConst.cfgReportNames[EcfgReportsTypes.ANNUAL.ordinal()].concat("1"), false);
			subOptional.put(IProjectsConst.cfgReportNames[EcfgReportsTypes.FINANCIAL.ordinal()].concat("1"), true);
			subOptional.put(IProjectsConst.cfgReportNames[EcfgReportsTypes.QUARTERLY.ordinal()].concat("1"), false);
			
			Assert.assertTrue(AmendmentsUtil.configureSubProjectsReExecute(foProj, subOptional), "FAIL: Could Not Set Sub Projects Re-Execute!");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "requestStepAmendment")
	public void requestSecondStepAmendment() throws Exception {
		
		try {
			
			foProj.initializeStep(IProjectsConst.cfgAwardStep);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
			
			String[] amenders = new String []{"Submission","PO: ".concat("Staff")};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Sub Project Amendment";
			String comment = "";
			String paStartSchedule = IProjectsConst.cfgReportNames[EcfgReportsTypes.ANNUAL.ordinal()].concat("1");
			
			Assert.assertTrue(AmendmentsUtil.requestSubProjAmendment(foProj,amenders,dd,reason,comment,false, paStartSchedule));
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "requestSecondStepAmendment")
	public void reSubmitAwardStep() throws Exception {		
		try {
			
			Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(foProj, IProjectsConst.cfgAwardStep, "Submission Summary"));
			
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			
			ClicksUtil.returnFromAnyForm();
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(FrontOfficeUtil.reSubmitCfgSubProjectInPO(foProj, IProjectsConst.EcfgReportsTypes.ANNUAL), "FAIL: Could Not submit");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "reSubmitAwardStep")
	public void verifyTokenDoNotBackToPostPaStep() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			foProj.setProjFullName(foProj.getProjFOFullName());
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Should Not Open PO Submission");
			
			Assert.assertTrue(GeneralUtil.isTextFieldExistsById(IProjectsConst.gps_Milestone_NameTxtId), "FAIL: Could not find The Textfield");
			
			Assert.assertTrue(GeneralUtil.isTextFieldReadOnlyById(IProjectsConst.gps_Milestone_NameTxtId), "FAIL: the Text field should be read Only");
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
