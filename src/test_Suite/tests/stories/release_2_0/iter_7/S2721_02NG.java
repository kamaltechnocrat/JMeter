/**
 * Case 02 for Story 2721 Add UAP for Optional Step Re-execute
 * Steps:
 * 1. Create Program with Optional Re-Execute set
 * 	1.1 Award
 * `1.2 PA 
 * 	1.3 Initial PA Submission Optional Re-execute
 * 2. Submit Award and Initial PA Submission
 * 
 * Test 1
 * 1. Make sure user has Optional Step Re-execute CRUD.
 * 2. Request An Amendment for Award.
 * Result:
 * 		Should be able to go through the Optional UI
 * 
 * Test 2
 * 1. Remove user Optional Step Re-execute CRUD.
 * 2. Request an Amendment
 * Result:
 * 		Not able to go through Optional Re-excute UI.
 * 
 */
 
package test_Suite.tests.stories.release_2_0.iter_7;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author mshakshouki
 *
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2721_02NG {

	/***************************************************************************
	 * To set up the Global Params Name Vars
	 **************************************************************************/
	Class<? extends S2721_02NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*-------------------------------------------------------------------*/
	
	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		//IEUtil.openNewBrowser();
        //GeneralUtil.navigateToPO();
        //GeneralUtil.logInSuper();
        //--------------------------
	}
	
	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		//GeneralUtil.Logoff();
		//IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ProjectsNG" })
	public void testAmendmentOnPostAwardWith_No_ReExecuteUAPNG() throws Exception {
		
		Reporter.log("No Test implemented yet for testAmendmentOnPostAwardWith_No_ReExecuteUAPNG");
		
	}

}
