/**
 * 
 */
package test_Suite.tests.fugBugz;

/**
 * @author mshakshouki
 *
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.lib.users.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;

public class FB3961CaseNG {
	
	private static Log log = LogFactory.getLog(FB3961CaseNG.class);
	
	Users user;
	
	
	@BeforeClass  
	public void setUp() {    
		// code that will be invoked when this test is instantiated  
	} 
	
	@Test(groups = { "FBCases" })
    public void fB3961CaseNG() throws Exception {
		
		try{
			
			log.info("Opening New Window and Login as Admin");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();

			
			user = new Users(1, new String[][] {{"fb3961", "FB3961", "FB3961", "@mail.com"}, {"G FB3961", "0", "1","2"}}, "Users", "Program Office Users");

			user.createUser();
			user.createGroup();
			user.addUsersToGroup();			
			
			user.addGroupsToGroup(new String[] {user.getGroup()}, "Super");
			
			ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
			
			ClicksUtil.clickLinks(user.getUserFullId());
			
			ClicksUtil.clickLinks(IClicksConst.userCompleteRightsLnk);			


		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} finally {
			user = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}
}
