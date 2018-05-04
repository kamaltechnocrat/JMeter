package test_Suite.tests.r3_4.bulkEvaluation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;


/**
 * 
 * @author s.grobbelaar
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

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();

			GeneralUtil.navigateToFO();

			// -----------------------------------

			fopp = new FundingOpportunity("A", "-Gnrl-PA-", "");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		foProj = null;
		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}


	@Test(groups = { "WorkflowNG" })
	public void createPreAwardProjects() throws Exception {

		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		Assert.assertTrue(FrontOfficeUtil.createManyProjs_PreAwardAndSubmit(2, fopp, "PreAward"), "Fail: Pre-Award Projects could not be created!");
	}

	@Test(groups = { "WorkflowNG" })
	public void createPostAwardProjects() throws Exception {

		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		Assert.assertTrue(GeneralUtil.createManyProjs_postAwardProcess(1, fopp, "PostAward"), "Fail: Post-Award Projects could not be created!");
	}

	@Test(groups = { "WorkflowNG" })
	public void createPostAwardAmendedProjects() throws Exception {
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front");
		
		Assert.assertTrue(GeneralUtil.createManyProjs_PAProcessAmend(2, fopp, "PoAAmend"), "Fail: Post-Award Amendment Projects could not be created!");
	}

}