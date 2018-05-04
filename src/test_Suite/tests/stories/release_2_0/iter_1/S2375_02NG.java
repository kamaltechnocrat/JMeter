package test_Suite.tests.stories.release_2_0.iter_1;

/*
 * Test Case 02 for User Story # 2375 - Limit creation of PO users (User List page)
 * Tests:
 * 
 * If there is no license key entered in the Configuration page, 
 * the maximum number of Active PO user on G3 is 15.
 * 
 * Steps:
 * 1. Open Configuration Page.
 * 2. Enter 20 for License Key
 * 3. Open User List, PreTest will add 10 users plus admin = 11  
 * 4. Try to 5 more users
 * Result:
 * Should be able to add 4 more then the Add new Icon will disappear
 */
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IConfigConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.AuthenUtil;
import test_Suite.utils.ui.IEUtil;


/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2375_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2375_02NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	ArrayList<String> errorSmalls;

	// -------------- End of Global Params ---------------------------------

	@BeforeClass(groups = { "UsersNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
	}

	@AfterClass(groups = {"UsersNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_1000_Users);
		
		errorSmalls = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"UsersNG" })
	public void testEnteringInvalidKeyNG() throws Exception {
		try {	

			log.info("Enter Invalid License Key (0)");

			AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_Invalid);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"UsersNG" },dependsOnMethods="testEnteringInvalidKeyNG")
	public void testValidationWithInvalidKeyNG() throws Exception {

		try {
			errorSmalls = null;
			errorSmalls = GeneralUtil.checkForErrorMessages();
			
			if(errorSmalls == null || errorSmalls.isEmpty())
			{
				Assert.fail("Fail: No Message for Invalid License Key!");
				
				return;
			}	
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
}
