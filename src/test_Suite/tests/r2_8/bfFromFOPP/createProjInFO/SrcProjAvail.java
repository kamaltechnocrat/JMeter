package test_Suite.tests.r2_8.bfFromFOPP.createProjInFO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.lib.workflow.FOProject;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SrcProjAvail {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FOProject srcProj;
	FOProject trgtProj;
	
	EProjNames epNames = EProjNames.FOPROJ;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToFO();
		GeneralUtil.LoginFO();
		// -----------------------------------
		
		srcProj = BfFoppUtil.prepareFOSourceData(epNames, 1);
		
		trgtProj = BfFoppUtil.prePareFOTarget(srcProj,epNames, 1);
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	
	
	@Test(groups = "EFormsNG")
	public void testCreatedSourceProjectAvailabilityNG() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.createFOProject(trgtProj));		
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testCreatedSourceProjectAvailabilityNG")
	public void testSubmittedSourceProjectAvailabilityNG() throws Exception {
		
		BfFoppUtil.submitFOApplication(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createFOProject(trgtProj), "FAIL: could not select Submitted Source Project: " + srcProj.getProjFullName());
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testSubmittedSourceProjectAvailabilityNG")
	public void testEndedSourceProjectAvailabilityNG() throws Exception {
		
		GeneralUtil.switchToPO();
		
		BfFoppUtil.assignOfficers(srcProj);
		
		BfFoppUtil.submitReview(srcProj);
		BfFoppUtil.submitPO_Submission(srcProj);
		BfFoppUtil.submitApproval(srcProj);
		BfFoppUtil.submitAward(srcProj);
		
		GeneralUtil.switchToFO();
		
		Assert.assertTrue(BfFoppUtil.createFOProject(trgtProj), "FAIL: could not select Ended Source Project: " + srcProj.getProjFullName());
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testEndedSourceProjectAvailabilityNG")
	public void testClosedSourceProjectAvailablibityNG() throws Exception {
		
		GeneralUtil.switchToPO();
		
		srcProj.closeOrOpenProject("Close Project");
		
		GeneralUtil.switchToFO();
		
		Assert.assertTrue(BfFoppUtil.createFOProject(trgtProj), "FAIL: could not select Closed Source Project: " + srcProj.getProjFullName());		
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testClosedSourceProjectAvailablibityNG")
	public void testNotAwardedSourceProjectAvailabilityNG() throws Exception {
		
		GeneralUtil.switchToFO();
		
		srcProj = BfFoppUtil.prepareFOSourceData(EProjNames.FOAWARDLESS, 1);
		
		trgtProj = BfFoppUtil.prePareAwardedFOTarget(srcProj,EProjNames.FOAWARDLESS, 1);
		
		BfFoppUtil.submitFOApplication(srcProj);
		
		Assert.assertFalse(BfFoppUtil.createFOProject(trgtProj), "FAIL: able to select Un-Awarded Source Project: " + srcProj.getProjFullName());
		
	}

}
