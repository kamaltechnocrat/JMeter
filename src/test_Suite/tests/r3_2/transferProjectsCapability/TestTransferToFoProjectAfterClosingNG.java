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
import test_Suite.lib.workflow.Project;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestTransferToFoProjectAfterClosingNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	
	Project proj;
	FundingOpportunity fopp;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			fopp = new FundingOpportunity("A-", "Gnrl-PA-FOPP", "", "");
			proj = new Project(fopp,"",true,true,EFormsUtil.createAnyRandomString(5));
			proj.setTrnsfrToOrgFullName("Ouia 1");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
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
	@Test(groups = { "ProjectsNG" })
	public void testSubmittingFOProjectNG() throws Exception {
		try {			

			proj.createPOProject(true);

			proj.submitProject(true);

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
			
			Assert.assertTrue(proj.closeOrOpenProject("Close Project"),"FAIL: Could Not Close Project!");
			
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
					proj, IFiltersConst.closeProjView),"FAIL: could not transfer Project");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
