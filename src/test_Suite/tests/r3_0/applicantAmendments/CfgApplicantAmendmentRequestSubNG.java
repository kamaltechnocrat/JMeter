/**
 * 
 */
package test_Suite.tests.r3_0.applicantAmendments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IAmendmentsConst.EAmendmentOptions;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CfgApplicantAmendmentRequestSubNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Users front3;

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
			
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-FO-Amendment-Sub");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		front3 = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void testCreatingProjectSubmissionNG() throws Exception {
		try {
			Assert.assertTrue(FrontOfficeUtil.createAndSubmitProject(foProj), "FAIL: Could not create and Submit new Project");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "testCreatingProjectSubmissionNG")
	public void testCompleted_PANG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.NOTSTARTED), "FAIL: something wrong check log");
						
			foProj.setProjFullName(foProj.getProjFOFullName());
			
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
	public void requestApplicantAmendment() throws Exception {
		
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front3");
			
			front3 = SubAccessUtil.getUser(1,15,"Front Office Users");
			
			foProj.initializeStep(IProjectsConst.cfgAppSubStep);
			
			Assert.assertTrue(AmendmentsUtil.requestApplicantAmendment(foProj.getCurrentStepName(), "FO: ".concat(front3.getFullId("3")), "This is a Reason"),"FAIL: could not Request Applicant Amendment see previous error!");
			

		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestApplicantAmendment")
	public void testCancelAmendmentIconExists() throws Exception {
		
		try {
			
			AmendmentsUtil.openApplicantAmendment(foProj.getCurrentStepName(), EAmendmentOptions.CANCEL);

		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "requestApplicantAmendment")
	public void testAmendmentDetailIconExists() throws Exception {
		
		try {
			
			AmendmentsUtil.openApplicantAmendment(foProj.getCurrentStepName(), EAmendmentOptions.DETAILS);

		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
