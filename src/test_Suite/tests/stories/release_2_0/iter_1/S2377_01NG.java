package test_Suite.tests.stories.release_2_0.iter_1;

/*
 * Test Case 01 for User Story # 2377 - Limit the activation of PO users (User Account tab)
 * 
 * Tests:
 * If there is no license key entered in the Configuration page, the maximum number of Active PO user on G3 is 15. has being tested in S2376_01NG
 * Activating Inactive users if the limit is reached.
 * 
 * Steps:
 * 1. Open Configuration Page, Enter no-License Key (0)
 * 2. Open User List, PreTest will add 10 users plus admin = 11
 * 3. Add 4 more users, then Deactivate them
 * 4. In Config page Change the no-license key to 10 Users Key
 * 5. open user Account tab to Inactive
 * 6. Try to Activate any Inactive user
 * Result:
 * No Check box available.
 */
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
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;


/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2377_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2377_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	int UserIndex = 19;
	Users user;
	Users user2;
	int usersCount;
	
	ArrayList<String> errorSmalls;

	// ------------ End of Global Params -----------

	@BeforeClass(groups = {"UsersNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

	}

	@AfterClass(groups = {"UsersNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_1000_Users);
		
		user= null;
		user2 = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}
	
	@Test(groups = {"UsersNG" })
	public void testCreatingUsersAndTaggleThemNG() throws Exception {
		try {
			
			user = new Users(1,IUsersConst.users[UserIndex], "Users", "Program Office Users");
			
			Assert.assertTrue(user.createUser(), "Fail: Creating users");
			
			Assert.assertTrue(user.taggleUserStatusInAccnt("1", false), "FAIL: Could not de-Ativate a User");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"UsersNG" }, dependsOnMethods="testCreatingUsersAndTaggleThemNG")
	public void testChangingKeyAndFilterList() throws Exception {
		try {
			
			AuthenUtil.changeLicenseKey(IConfigConst.licenseKey_15_Users);
			
			ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
			
			Assert.assertTrue(FiltersUtil.filterListByLabel(IFiltersConst.administration_StatusType_Lbl, "", IFiltersConst.users_InactiveUsers_StatusView), "FAIL: Unable to Filter Users List!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"UsersNG" }, dependsOnMethods="testChangingKeyAndFilterList")
	public void testActivationCheckBoxAvailabilityNG() throws Exception {
		try {

			Assert.assertFalse(UsersAndGroupsUtil.doesActivationCheckBoxAvailableInUserAccount(user.getUsrName() + "1"));

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

}
