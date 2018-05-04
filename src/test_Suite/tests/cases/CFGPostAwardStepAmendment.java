/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;

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

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "cfgNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front3");
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "cfgNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"cfgNG"})
	public void initialize() throws Exception{
		
		fopp = new FundingOpportunity("CFG-", "Post-Award-Step-Amendment", "", "");
		
		foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 3, "-CFG-Completed-PA-Step");
		
		FrontOfficeUtil.registerAndSubmitProject(fopp, foProj, "Ouia 3");

	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "cfgNG" }, dependsOnMethods = "initialize")
	public void testCompleted_PANG() throws Exception {
		
		try {
			
			Assert.assertTrue(FrontOfficeUtil.continueCfgWorkflow(foProj, IProjectsConst.EcfgProjectsName.COMPLETE), "FAIL: something wrong check log");
						
			GeneralUtil.switchToPO();
			
			foProj.setProjFullName(foProj.getProjFOFullName());
			
			Assert.assertTrue(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, "Closeout Approval", IFiltersConst.openProjView),"FAIL: Could Not Open PO Submission");
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
