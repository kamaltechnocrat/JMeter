/**
 * 
 */
package test_Suite.tests.r5_2.pa_AppSub_Step;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class MustRunFirst {
	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "PAAppSubWorkflow" })
	public void setUp() throws Exception {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.LoginFO();

			// -----------------------------------

			fopp = new FundingOpportunity("A", "-PA-App-Sub-Step-", "");

			foProj = new FOProject(fopp, "", true, 1);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "PAAppSubWorkflow" }, alwaysRun = true)
	public void tearDown() throws Exception {

		try {
			
			fopp = null;
			foProj = null;

			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "PAAppSubWorkflow" }, enabled = true)
	public void registerToFopp() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

			Assert.assertTrue(
					FrontOfficeUtil.registerApplicantToProgram(
							fopp.getFoppFullName(), foProj.getFoOrgName()),
					"FAIL: Could not Register to Funding Opp.!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

}
