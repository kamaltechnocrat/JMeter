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
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SrcProjInWizard {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<? extends SrcProjInWizard> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FOProject srcProj;
	
	FOProject trgtProj;

	EProjNames epNames = EProjNames.FOWZRD;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToFO();
		GeneralUtil.LoginFO();
		// -----------------------------------

		srcProj = BfFoppUtil.prepareFOSourceData(epNames, 1);

		trgtProj = BfFoppUtil.prePareFOTarget(srcProj, epNames, 1);

		BfFoppUtil.submitFOApplication(srcProj);

		GeneralUtil.switchToFOOnly();

		FrontOfficeUtil.processWizard(trgtProj);
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
	public void testSourceProjectAvailabilityNG() throws Exception {

		Assert.assertTrue(FrontOfficeUtil.createProjectInWizard(true,
				trgtProj.getSrcProjectFullNameWithNumber(), 
				trgtProj.getProjFOFullName()),
				"FAIL: could not create Project through a Wizard!");
	}
}
