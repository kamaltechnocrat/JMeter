/**
 * 
 */
package test_Suite.tests.ui;

/**
 * @author mshakshouki
 * 
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

@GUITest
@Test(singleThreaded = true)
public class LockedOutUsers_FiltersTestNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends LockedOutUsers_FiltersTestNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Users user;
	FOUsers foUser;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = {"UI_ListNG"})
	public void setUp() {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		try {

			String userName = "";

			for (int x = 1; x < 27; x++) {

				if (x > 9) {
					userName = "usr" + Integer.toString(x) + "1";
				} else {
					userName = "usr0" + Integer.toString(x) + "1";
				}

				IEUtil.openNewBrowser();
				GeneralUtil.navigateToPO();

				GeneralUtil.LoginAnyUnsuccessfully(userName, 5);
				IEUtil.closeBrowser();

				GeneralUtil.takeANap(0.5);
			}

			for (int x = 4; x < 12; x++) {

				userName = "front" + Integer.toString(x);

				IEUtil.openNewBrowser();
				GeneralUtil.navigateToFO();

				GeneralUtil.LoginAnyUnsuccessfully(userName, 5);
				IEUtil.closeBrowser();

				GeneralUtil.takeANap(0.5);
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = {"UI_ListNG"}, alwaysRun=true)
	public void tearDown() {
		
		user = null;
		foUser = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());		
	}

	@Test(groups = {"UI_ListNG"})
	public void lockedOutUsers_FiltersTestNG() throws Exception {
		try {

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			for (int UserIndex = 11; UserIndex <= 25; UserIndex++) {
				user = new Users();

				user.setType("Users");

				user.setUserBeat(1);

				user.setUserIndex(UserIndex);

				user.setArrUser(IUsersConst.users[UserIndex]);

				log.debug(UsersAndGroupsUtil.unlockUser(user.getArrUser()[0][0]
						+ Integer.toString(user.getUserBeat())));

			}

			for (int UserIndex = 4; UserIndex <= 12; UserIndex++) {
				foUser = new FOUsers();

				foUser.setFoRegistrants(IPreTestConst.FrontUsers[1]);

				log.debug(UsersAndGroupsUtil.unlockUser(foUser
						.getFoRegistrants()[4]));
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
}
