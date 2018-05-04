/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.reporting_Functions.EtlUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ETL_RebuildAndExecuteDatamart {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EtlNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EtlNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EtlNG" })
	public void testRebuildDatamartNG() throws Exception {
		try {
			
			Assert.assertTrue((EtlUtil.rebuildDatamart()), "FAIL: error occured while Rebuilding Datamart");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EtlNG" },dependsOnMethods="testRebuildDatamartNG")
	public void testExecuteDatamartNG() throws Exception {
		try {
			
			Assert.assertTrue((EtlUtil.executeDatamart()), "FAIL: error occured while Executing Datamart");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
