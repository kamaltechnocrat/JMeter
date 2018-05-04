/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @Story #2631. Activity List Filtering
 */

/**
 * @author mshakshouki
 * 
 */
@Test(singleThreaded = true)
public class S2631_02NG {

	private static Log log = LogFactory.getLog(S2631_02NG.class);

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
	public void testActivityListFilteringNG() throws Exception {

		try {

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}

}
