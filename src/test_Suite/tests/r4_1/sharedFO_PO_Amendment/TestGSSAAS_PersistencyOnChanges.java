/**
 * 
 */
package test_Suite.tests.r4_1.sharedFO_PO_Amendment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestGSSAAS_PersistencyOnChanges {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		try {	
			Assert.assertTrue(GeneralUtil.setGrantStepStaffAccess("Yes"), "FAIL: Could not Change the Setting!");
			
			fopp = null;
			foProj = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void changeSettingToNoAndVerifyNG() throws Exception {
		try {
			
			Assert.assertTrue(GeneralUtil.setGrantStepStaffAccess("No"), "FAIL: Could not Change the Setting!");
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openApplicationSettings), "FAIL: Could not open Application Settings Page!");
			
			Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IGeneralConst.appSettings_GSSAAS_DropdownId), "No", "FAIL: The Value should be No!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "WorkflowNG" })
	public void changeSettingToYesAndVerifyNG() throws Exception {
		try {
			
			Assert.assertTrue(GeneralUtil.setGrantStepStaffAccess("Yes"), "FAIL: Could not Change the Setting!");
			
			GeneralUtil.switchToPO();
			
			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openApplicationSettings), "FAIL: Could not open Application Settings Page!");
			
			Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IGeneralConst.appSettings_GSSAAS_DropdownId), "Yes", "FAIL: The Value should be No!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
