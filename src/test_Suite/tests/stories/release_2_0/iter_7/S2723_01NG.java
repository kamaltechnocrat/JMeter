/**
 * Story #2723. Modify UI and model to handle optional step re-execute
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
public class S2723_01NG {

	/***************************************************************************
	 * To set up the Global Params Name Vars
	 **************************************************************************/
	Class<? extends S2723_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*-------------------------------------------------------------------*/
	
	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		//IEUtil.openNewBrowser();
       // GeneralUtil.navigateToPO();
       // GeneralUtil.logInSuper();
        //--------------------------
	}
	
	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		//GeneralUtil.Logoff();
		//IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ProjectsNG" })
	public void testUIChangesForOptionalStepReExecute() throws Exception {
		
		Reporter.log("No Test Cases Implemented yet for testUIChangesForOptionalStepReExecute");
		
	}

}
