/*
 * Test Case _01 for Story 1858 Deactivate user accounts
 * Steps:
 *  1. Create 2 Evaluators and Associate them to a Group (if they do not exists already)
 *  2. Deactivate Eval_1 from the List
 * Test
 *  a. Login as Eval_1 
 * Result Expected:
 *  Login Fail
 *  b. Activate Eval_1 from the List
 *  c. Login as Eval_1
 *  Result Expected:
 *   Login Succeed
 *  d.Deactivate Eval_2 from User Account
 *  e. Login as Eval_2
 *  Result Expected:
 *   Login Fail
 *  f. Activate Eval_2 from the List
 *  g. Login as Eval_2
 *  Result Expected:
 *   Login succeed
 *  3. Create Program (Submission, Approval, Review)
 *  4. Configure the Group as Step Staff for Approval with Auto Assign
 *  5. Submit a Project.
 *  
 */
package test_Suite.tests.stories.release_1_5.iter_1_2;

import test_Suite.constants.users.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.cases.*;
import test_Suite.lib.users.*;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1858_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	private int UserIndex = 18;
	
	Integer userBeat = 2;
	
	Users user;

	boolean[] uapsRights = IUAPConst.allRightsTrue;

	UAPs uaps;
	
	ArrayList<String[]> uapList;

	// ------------- End of Global Vars
	// -----------------------------------------

	@BeforeClass(groups = {"UsersNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"UsersNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		user = null;
		uaps = null;
		uapList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"UsersNG" })
	public void initializeAndCreateUser() throws Exception {
		
		try {

			user = new Users();

			user.setType("Users");

			user.setUserBeat(userBeat);

			user.setUserIndex(UserIndex);

			user.setArrUser(IUsersConst.users[UserIndex]);

			user.setGroup(IUsersConst.users[UserIndex][1][0]);

			uaps = new UAPs();

			uapList = new ArrayList<String[]>();

			uapList.add(0, IUAPConst.administerG3UAP_1st);
			uapList.add(1, IUAPConst.administerG3UAP_Manage3rdPartyReports_3rd);
			uapList.add(2, IUAPConst.administerG3UAP_ReportsDetails_4th);

			user.setPrimaryOrg("G3");
			
			//---- Creating User and Group ------
			Assert.assertTrue(UsersAndGroupsUtil.createUsersOnly(user, uapList, uapsRights), "FAIL: Could not create Users!");
			
			Assert.assertTrue(UsersAndGroupsUtil.createGroupOnly(user, uapList, uapsRights), "FAIL: Could not create Group!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = {"UsersNG" },dependsOnMethods="initializeAndCreateUser")
	public void testDeactivateUserInUsersList() throws Exception {

		try {

			String beat = "1";

			user.taggleUserStatusInAccnt(beat, false);

			// Start Test 1
			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny(IUsersConst.users[UserIndex][0][0] + beat);

			GeneralUtil.takeANap(0.5);
			GeneralUtil.logInSuper();

			// Activate User in List
			user.taggleUserStatusInAccnt(beat, true);

			Assert.assertTrue(user.setUserToGroup(beat, user.getGroup(), user.getArrUser()[0][0], true), "Fail: Adding User to Group");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(IUsersConst.users[UserIndex][0][0] + beat);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}

	}

	@Test(groups = { "UsersNG" },dependsOnMethods="testDeactivateUserInUsersList")
	public void testDeactivateUserInUserAccount() throws Exception {

		try {

			String beat = "2";
			GeneralUtil.switchToFO();

			GeneralUtil.switchToPO();

			// Deactivate User In User Account
			user.taggleUserStatusInAccnt(beat, false);

			Assert.assertFalse(user.setUserToGroup(beat, user.getGroup(), user.getArrUser()[0][0], true), "Fail: Adding User to Group");

			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny(IUsersConst.users[UserIndex][0][0] + beat);
			
			GeneralUtil.logInSuper();

			// Activate User in List
			user.taggleUserStatusInAccnt(beat, true);

			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny(IUsersConst.users[UserIndex][0][0] + userBeat);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
}
