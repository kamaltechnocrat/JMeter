package test_Suite.tests.stories.release_2_0.iter_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IFiltersConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S2380_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S2380_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Integer usersCount;
	Integer licenseCount = 1000; //Assuming Watij always use License key of 1000;
	String licenseOwner = "QA: ";
	String licensedMessage = " active program office users of ";
	String licensedUsersMessage = "";

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
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "UsersNG" })
	public void testCountingActiveUsersNG() throws Exception {
		try {

			usersCount = UsersAndGroupsUtil
					.getHowManyPOUsers(IFiltersConst.users_activeUsers_StatusView);
			
			licensedUsersMessage = licenseOwner + usersCount.toString() + licensedMessage + licenseCount.toString();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "UsersNG" },dependsOnMethods="testCountingActiveUsersNG")
	public void testDisplayActivePOLicensedUsersMessageNG() throws Exception {

		try {
			log.debug(licensedUsersMessage);
			
			Assert.assertEquals(GeneralUtil.FindTextOnPage(licensedUsersMessage), true);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}
}
