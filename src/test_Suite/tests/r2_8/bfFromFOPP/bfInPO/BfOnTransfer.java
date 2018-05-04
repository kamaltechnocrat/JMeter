/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP.bfInPO;

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
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class BfOnTransfer {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends BfOnTransfer> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Project srcProj;
	Project trgtProj;
	Project srcTrnsfProj;
	Project trgtTrnsfProj;
	
	EProjNames epNames = EProjNames.POTRNSFR;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		srcProj = BfFoppUtil.prepareSourceData(epNames);
		
		trgtProj = BfFoppUtil.prePareTarget(srcProj,epNames);	
		
		trgtProj.setProjNumber(srcProj.getProjNumber());
		
		trgtTrnsfProj = BfFoppUtil.prePareTarget(srcProj,epNames);	
		
		trgtTrnsfProj.setProjNumber(srcProj.getProjNumber());
		
		BfFoppUtil.submitApplication(srcProj);
		
		Assert.assertTrue(BfFoppUtil.createProject(trgtProj), "FAIL: Could not select Submitted Source Project: " + srcProj.getProjFullName());
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetApplicantion(trgtProj), "FAIL: could not submit eForm");
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtProj = null;
		srcTrnsfProj = null;
		trgtTrnsfProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG")
	public void testBFDataAfterTargetProjectTransfered()throws Exception {
		
		trgtProj.setTrnsfrToOrgFullName("Ouia 3");
		
		Assert.assertTrue(ProjActivUtil.trnasferProjectToApplicant(trgtProj, "Open Projects"), "FAIL: could not transfer Project: " + trgtProj.getProjFullName() + " to " + trgtProj.getTrnsfrToOrgFullName() );
		
		trgtProj.setOrgFullName("Ouia 3");
		
		Assert.assertFalse(BfFoppUtil.openAndSubmitTargetReviewSubmission(trgtProj, "Ready"), "FAIL: Data Brought Forward After Target Project had been Transfered");
	}
	
	@Test(groups="EFormsNG", dependsOnMethods="testBFDataAfterTargetProjectTransfered")
	public void testBFDataAfterSourceProjectTransfered()throws Exception {

		trgtTrnsfProj.setProjName(trgtTrnsfProj.getProjName() + "-PostSRCTransfer");
		Assert.assertTrue(BfFoppUtil.createProject(trgtTrnsfProj), "FAIL: Could not select Submitted Source Project: " + srcProj.getProjFullName());
		
		Assert.assertTrue(BfFoppUtil.openAndSubmitTargetApplicantion(trgtTrnsfProj), "FAIL: could not submit eForm");
		
		srcProj.setTrnsfrToOrgFullName("Ouia 3");
		Assert.assertTrue(ProjActivUtil.trnasferProjectToApplicant(srcProj, "Open Projects"), "FAIL: could not transfer Project: " + srcProj.getProjFullName() + " to " + srcProj.getTrnsfrToOrgFullName() );
		
		Assert.assertFalse(BfFoppUtil.openAndSubmitTargetReviewSubmission(trgtTrnsfProj, "Ready"), "FAIL: Data Brought Forward After Target Project had been Transfered");		
	}
}
