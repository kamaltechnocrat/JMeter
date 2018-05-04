/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2379_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2379_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	@Test(groups = { "Iter_21", "UsersNG" })
	public void testLimitTheActivePOUsersViaAPING() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		Reporter.log("Story 2379 - test cases has not been implemented yet!");
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

}
