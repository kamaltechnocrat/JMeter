/*
 *  Test Case for Story #1769 Add Approval Limit Field to the User Record.
 *  Steps:
# 1. Add User, add positive Approval Limit Save
# 2. Verifiy in User Account
# 3. Change the Approval Limit to nigative Value
# 4. Verifiy in User Account
 */

package test_Suite.tests.stories.release_1_5.iter_1_1;


import org.testng.annotations.Test;



@Test(singleThreaded = true)
public class S1769_01NG {
	
	
	@Test
	public void testSkip() throws Exception {
		
		throw new SkipException();
	}

}
