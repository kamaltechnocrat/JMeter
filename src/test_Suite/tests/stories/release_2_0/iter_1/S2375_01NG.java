package test_Suite.tests.stories.release_2_0.iter_1;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IConfigConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.AuthenUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

/*
 * Test Case 01 for User Story # 2375 - Limit creation of PO users (User List page)
 * Tests:
 * "When a build has reached the maximum number of active program office users allowed,
 * display an error message and no (Add User) Icon" 
 * 
 * Steps:
 * 1. open Configuration page, add pregenerated License key for 10 Users
 * 2. PreTest will add 10 users plus admin = 11 * 
 * NOTE: has been change in the logic and no more hard coded no-license key
 * 4. Add a user
 * Result:
 *  Unable to add any user, because no "Add User" Icon
 */
/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2375_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2375_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	int UserIndex = 5; // Documnets Manager
	Users user;
	
	int usersCount;
	
	ArrayList<String> errorSmalls;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = {"UsersNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		
		initializeUsers();
		
	}

	@AfterClass(groups = { "UsersNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_1000_Users);
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		user = null;
		errorSmalls = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"UsersNG" })
	public void createMoreUsersIfNeeded() throws Exception {
		
		try {
			
			usersCount = UsersAndGroupsUtil.getHowManyPOUsers(IFiltersConst.users_activeUsers_StatusView);
			
			AuthenUtil.changeLicenseKey(UsersAndGroupsUtil.getTheProperLicenseKey(usersCount, user));	
			
			if(user.getUserBeat() != 0)
			{
				createUsers();	
			}
			
			ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"UsersNG" },dependsOnMethods="createMoreUsersIfNeeded")
	public void testAddUserIconExistanceNG() throws Exception {

		try {
			
			Assert.assertFalse(GeneralUtil.isImageExistsBySrc(IClicksConst.newImg), "Fail: Add new Users Icon should not exists!");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}
	
	@Test(groups = {"UsersNG" },dependsOnMethods="createMoreUsersIfNeeded")
	public void testValidationActiveUsersExceedLimit() throws Exception {
		errorSmalls = null;
		errorSmalls = GeneralUtil.checkForErrorMessages();
		if(errorSmalls == null || errorSmalls.isEmpty())
		{
			Assert.fail("Fail: No Validation Message for Exceeding Users Limit!");
			
			return;
		}		
		
	}

	private void initializeUsers() throws Exception {
		user = new Users(2, IUsersConst.users[UserIndex], "Users", "Program Office Users");
	}

	private void createUsers() throws Exception {
		
		Assert.assertTrue(user.createUser(), "Fail: To Create a User");
	}

}
