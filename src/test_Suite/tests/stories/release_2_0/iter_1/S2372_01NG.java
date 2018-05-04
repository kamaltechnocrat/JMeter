package test_Suite.tests.stories.release_2_0.iter_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Reporter;
import org.testng.annotations.Test;

/***********************************************
 * This is Manual Test Case for User Story: 2372 Generate a License Key, License
 * Server App. The Test Case Ran Once This are the Keys Generated to be used for
 * User Story: 2374 Enter Key Code into G3 User Story: 2375 Limit creation of PO
 * users (User List page) User Story: 2376 Limit the activation of PO user (User
 * List page) User Story: 2377 Limit the activation of PO users (User Account
 * tab) User Story: 2378 Limit the number of active PO users (XML import) User
 * Story: 2379 Limit the number of active PO users (via API) User Story: 2380
 * Display Active Users/Licensed PO Users (User List page)
 * 
 * There will be three keys Generated
 * 
 * Less than no-License Key (10 Users)
 * "185-222-54-19-57-101-193-61-34-3-132-185-219-57-4-20" Equal to no-License
 * Key (15 User) "185-222-54-19-57-101-193-61-237-102-71-238-161-147-51-81" More
 * than no-License Key (20 User)
 * "185-222-54-19-57-101-193-61-204-221-169-169-173-169-61-50"
 * 
 * The Keys Stored as final strings in constants.ui.IConfigConst.java Inteface
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2372_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2372_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	@Test(groups = {"UsersNG" })
	public void testGenerateLicenseKeysNG() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		log.info("This is A manual test to Generate License Keys!");
		
		Reporter.log("Story 2372 - Generate a License key is a manual test");
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}
}
