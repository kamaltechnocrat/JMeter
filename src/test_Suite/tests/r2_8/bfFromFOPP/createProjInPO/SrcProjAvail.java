/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP.createProjInPO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.lib.workflow.Project;
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
	Class<? extends SrcProjAvail> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project srcProj;
	Project trgtProj;
	
	EProjNames epNames = EProjNames.POPROJ;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG","PreRegNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		srcProj = BfFoppUtil.prepareSourceData(epNames);
		
		trgtProj = BfFoppUtil.prePareTarget(srcProj,epNames);
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
	}

	@AfterClass(groups = { "EFormsNG","PreRegNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	
	
	@Test(groups = "EFormsNG")
	public void testCreatedSourceProjectAvailabilityNG() throws Exception {
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj), "FAIL: could not select Created Source Project: " + srcProj.getProjFullName());		
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testCreatedSourceProjectAvailabilityNG")
	public void testSubmittedSourceProjectAvailabilityNG() throws Exception {
		
		BfFoppUtil.submitApplication(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj), "FAIL: could not select Submitted Source Project: " + srcProj.getProjFullName());
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testSubmittedSourceProjectAvailabilityNG")
	public void testEndedSourceProjectAvailabilityNG() throws Exception {
		
		BfFoppUtil.submitReview(srcProj);
		BfFoppUtil.submitPO_Submission(srcProj);
		BfFoppUtil.submitApproval(srcProj);
		BfFoppUtil.submitAward(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj), "FAIL: could not select Ended Source Project: " + srcProj.getProjFullName());
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testEndedSourceProjectAvailabilityNG")
	public void testClosedSourceProjectAvailablibityNG() throws Exception {
		
		srcProj.closeOrOpenProject("Close Project");
		
		trgtProj.setProjName("-BF-FOPP-PO-Proj-SRC-Closed");
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj), "FAIL: could not select Closed Source Project: " + srcProj.getProjFullName());		
	}
	
	@Test(groups = "EFormsNG", dependsOnMethods="testClosedSourceProjectAvailablibityNG")
	public void testNotAwardedSourceProjectAvailabilityNG() throws Exception {
		
		srcProj = BfFoppUtil.prepareSourceData(EProjNames.POAWARDLESS);
		
		trgtProj = BfFoppUtil.prePareAwardedTarget(srcProj,EProjNames.POAWARDLESS);
		
		BfFoppUtil.submitApplication(srcProj);
		
		Assert.assertFalse(BfFoppUtil.createProject(trgtProj), "FAIL: able to select Un-Awarded Source Project: " + srcProj.getProjFullName());
	}
}
