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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IProjectsConst.EcfgReportsTypes;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
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
public class CFGPostAward_NotCompleted_StepAmendment {

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
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-NotCompleted-PA-Step");
			
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
	public void doNotCompletePostAwardNG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.NOTCOMPLETE), "FAIL: something wrong check log");
						
			GeneralUtil.switchToPO();
			
			foProj.setProjFullName(foProj.getProjFOFullName());			
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" },dependsOnMethods="doNotCompletePostAwardNG")
	public void testNotCompletedPostAwardNG() throws Exception {
		
		try{
			Assert.assertFalse(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IProjectsConst.cfgCloseOutStep, IFiltersConst.openProjView),"FAIL: Should Not Open PO Submission");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" },dependsOnMethods="testNotCompletedPostAwardNG", dataProvider="names")
	public void requestAmendmentInPostAwardNG(EcfgReportsTypes eCfgType) throws Exception {
		try {
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardStep);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_RequestStepAmendmentAt_DropdownId, IProjectsConst.cfgReportNames[eCfgType.ordinal()].concat("1"));
			
			GeneralUtil.takeANap(2.0);
			
			foProj.initializeStep(IProjectsConst.cfgPostAwardSubStep);
			
			clerk1 = SubAccessUtil.getUser(1,16,"Program Office Users");
			
			String[] amenders = new String []{"Submission","PO: ".concat(clerk1.getUserFullId())};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			String paStartSchedule = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, paStartSchedule));
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@DataProvider(name = "names")
	public Object[][] generateEvaluators() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {EcfgReportsTypes.FINANCIAL},
				new Object[] {EcfgReportsTypes.ANNUAL},
				new Object[] {EcfgReportsTypes.QUARTERLY}
		};				
		return result;		
	}

}
