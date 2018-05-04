/**
 * 
 */
package test_Suite.tests.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class GGCertificateNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "UI_AdminNG" })
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

	@AfterClass(groups = { "UI_AdminNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "UI_AdminNG" })
	public void testGGCertificatesConfig() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.ggCertificates);
			
			Assert.assertTrue(ClicksUtil.clickImage(IClicksConst.newImg), "Couldn't click 'New Image' Icon!");
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn), "Couldn't click Save button!");
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.ggBackToCertificateListBtn), "Couldn't click Back to Certificate List button!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
