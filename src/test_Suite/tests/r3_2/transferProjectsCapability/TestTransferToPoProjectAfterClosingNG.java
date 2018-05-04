/**
 * 
 */
package test_Suite.tests.r3_2.transferProjectsCapability;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestTransferToPoProjectAfterClosingNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FOProject foProj;
	FundingOpportunity fopp;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front4");
			// -----------------------------------
			fopp = new FundingOpportunity("A-", "Gnrl-PA-FOPP", "", "");
			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 1, "-TransferToPo");
			foProj.setTrnsfrToOrgFullName("Ouia 3");

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
	public void testSubmittingFOProjectNG() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");

			foProj.createFOProject();

			Assert.assertTrue(foProj.submitFOProject(
					IGeneralConst.gnrl_SubmissionA[0][0], true),
					"Fail: Could not Find Project In FO Submission List!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" },dependsOnMethods="testSubmittingFOProjectNG")
	public void testClosingProject() throws Exception {
		try {
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(foProj.closeOrOpenProject("Close Project"),"FAIL: Could Not Close Project!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" },dependsOnMethods="testClosingProject")
	public void testTransferProjAfterClosingIt() throws Exception {
		try {


			Assert.assertFalse(ProjActivUtil.trnasferProjectToApplicant(
					foProj, IFiltersConst.closeProjView),"FAIL: could not transfer Project");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
