/**
 * 
 */
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
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SrcProjAfterTransfer {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends SrcProjAfterTransfer> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FOProject srcProj;
	FOProject trgtProj;
	
	EProjNames epNames = EProjNames.FOTRNSFR;

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
		
		BfFoppUtil.submitFOApplication(srcProj);
		
		GeneralUtil.switchToPO();
		
		BfFoppUtil.assignOfficers(srcProj);
		
		srcProj.setTrnsfrToOrgFullName("Ouia 3");
		
		Assert.assertTrue(ProjActivUtil.trnasferProjectToApplicant(srcProj, "Open Projects"), "FAIL: could not transfer Project: " + srcProj.getProjFullName() + " to " + srcProj.getTrnsfrToOrgFullName() );
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG")
	public void testTransferedSourceProjectAvailabilityNG() throws Exception {
		
		GeneralUtil.switchToFO();
		
		Assert.assertFalse(BfFoppUtil.createFOProject(trgtProj), "FAIL: Able to select Submitted Source Project: " + srcProj.getProjFullName());
	}

}
