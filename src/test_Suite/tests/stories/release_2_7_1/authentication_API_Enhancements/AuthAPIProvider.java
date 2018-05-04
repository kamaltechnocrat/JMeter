/**
 * 
 */
package test_Suite.tests.stories.release_2_7_1.authentication_API_Enhancements;

import org.testng.annotations.DataProvider;

/**
 * @author mshakshouki
 *
 */
public class AuthAPIProvider {	
	
	@DataProvider(name = "Auth-Providers")
	public static Object[][] generateAuthProviders() throws Exception {
		
		return new Object[][] {
				new Object[] {"PO_Default", "FO_Default", true,true,true},
				new Object[] {"PO_Default", "FO_GUI", true,true,true},
				new Object[] {"PO_Default", "FO_SSO", true,true,true},
				new Object[] {"PO_GUI", "FO_GUI", true,false,false},
				new Object[] {"PO_GUI", "FO_SSO", true,false,false},
				new Object[] {"PO_GUI", "FO_Default", true,false,false},
				new Object[] {"PO_SSO", "FO_SSO", false,false,false},
				new Object[] {"PO_SSO", "FO_GUI", false,false,false},
				new Object[] {"PO_SSO", "FO_Default", false,false,false}		
		};
	}

}
