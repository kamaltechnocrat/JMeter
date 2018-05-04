
package test_Suite.tests.stories.release_2_0.iter_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IConfigConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.AuthenUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

/* Test Case 01 for User Story #2374 Enter Key Code into G3
 * 
 * Steps:
 * 1. Login as shak
 * 2. Open Configuration Page
 * 3. Enter Pre-Generated Keys and Save
 * 4. Log off, Log Back in
 * 5. Check if the Key entered is maintained in the db
 * 
 * 
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2374_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2374_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	@BeforeClass(groups = { "UsersNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
	}
	
	@AfterClass(groups = { "UsersNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_1000_Users);
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"UsersNG" }) 
	public void testEnteringLicenseKeyNG() throws Exception {
		
		try {

			log.info("Setting the New Key");
			
			AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_100_Users);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception", e);
		}		
	}

	@Test(groups = {"UsersNG" }, dependsOnMethods = "testEnteringLicenseKeyNG")
	public void testVerifyLicenseKeyPersistNG() throws Exception {

		try {

			// Now Testing if the new key exists still

			log.info("Opening Configuration Page");
			
			ClicksUtil.clickLinks(IClicksConst.openLicenseManagementLnk);
			
			String filedId = TablesUtil.getTextFieldIdByLabel_New(ITablesConst.license_Management_TableId, IConfigConst.licenseKey_Names[0]);

			Assert.assertTrue(GeneralUtil.findTextInTextFieldById(filedId, IConfigConst.licenseKey_100_Users),
					"Fail: Entered Key Not Found!");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}
}
