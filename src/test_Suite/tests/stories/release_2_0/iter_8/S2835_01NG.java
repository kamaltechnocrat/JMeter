/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @Story #2835. Add 2 new pages - Notification Log List and Notification Log Details
 */

/**
 * @author mshakshouki
 *
 */
public class S2835_01NG {

	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {

		//IEUtil.openNewBrowser();
		//GeneralUtil.navigateToPO();
		//GeneralUtil.logInSuper();
		// ----------------------------------
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {

		//GeneralUtil.Logoff();
		//IEUtil.closeBrowser();
	}

	@Test(groups = { "NotificationsNG" }, enabled=false)
	public void testNotificationLogListNG() throws Exception {

	}

	@Test(groups = { "NotificationsNG" }, enabled=false)
	public void testNotificationLogDetailsNG() throws Exception {

	}

}
