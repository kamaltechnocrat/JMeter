/**
 * 
 */
package test_Suite.tests.users;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.users.IUAPConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.users.UAPs;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CreateExternalUsersNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	ArrayList<String[]> uapList;

	UAPs uaps;
	Users user;
	FOUsers foUser;
	Integer userBeat = 5;
	Integer startIndex;
	Integer endIndex;
	int UserIndex = 2;
	String group;
	String lastName;

	String primOrg[] = {"North-Id", "East-Id", "West-Id", "South-Id"};

	boolean[] uapsRights = IUAPConst.createDelete;

	// ---------------------------- End of Params ---------------------------------

	@BeforeClass(groups = { "UsersNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// GeneralUtil.loginPO();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "UsersNG" }, alwaysRun=true)
	public void tearDown() {
		
		uaps = null;
		user = null;
		uapList = null;
		uapsRights = null;
		
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "UsersNG" })
	public void createUsersNG() throws Exception {

		try {

			for (UserIndex = 0; UserIndex <= 3; UserIndex++) {
				initializeUsers(primOrg[UserIndex]);
				createUsers();
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}
	
	private void initializeUsers(String orgName) throws Exception {
		
		user = new Users(5, IUsersConst.externalUsers[UserIndex], "Users", "External Reviewers", orgName, "Active Users", "External Reviewer");
		uaps = new UAPs();
		uapList = new ArrayList<String[]>();

		uapList.add(0, IUAPConst.manageProjectsUAP_1st);
		uapList.add(1, IUAPConst.manageProjectEvaluationsUAP_1st);
		uapList.add(2, IUAPConst.fullTextSearchUAP_1st);
		
		uapsRights = IUAPConst.createDelete;

	}
	
	private void createUsers() throws Exception {
		
		Assert.assertTrue(user.createUser(), "Fail: To Create a User");

		log.info("Start Creating Group!");
		Assert.assertTrue(user.createGroup(), "Fail: to Create a Group");

		UsersAndGroupsUtil.addAccessRightsUAPTopLevel(IUAPConst.manageProjectsUAP_1st[0], uapsRights);

		UsersAndGroupsUtil.addAccessRightsUAPTopLevel(IUAPConst.manageProjectEvaluationsUAP_1st[0], uapsRights);

		UsersAndGroupsUtil.addAccessRightsUAPTopLevel(IUAPConst.fullTextSearchUAP_1st[0], uapsRights);

		Assert.assertTrue(user.addUsersToGroup(),
				"Fail: Adding Users to Group");
	}

}
