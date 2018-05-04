/**
 * 
 */
package test_Suite.tests.stories.release_2_7_1.authentication_API_Enhancements;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test_Suite.constants.authentications.IAuthenConst;
import test_Suite.constants.authentications.IAuthenConst.EAuthProviderTypes;
import test_Suite.lib.authentications.Authens;
import test_Suite.utils.authentications.AuthenUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

/**
 * @author mshakshouki
 * 
 */

@Test(singleThreaded = true)
public class S3523_POUserLogsIn_AuthExtension_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3523_POUserLogsIn_AuthExtension_02NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	static Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3523_POUserLogsIn_AuthExtension_02NG Has been Started!");

		eAPO = EAuthProviderTypes.valueOf(po);
		eAFO = EAuthProviderTypes.valueOf(fo);

		log.info("Provider for Program Office: " + eAPO.toString());
		log.info("Provider for Front Office: " + eAFO.toString());

		auth = new Authens(eAPO, eAFO);

		auth.setPoUserName("shak");

		AuthenUtil.switchToPOOnly(auth);
	}

	@AfterClass(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void tearDown() throws Exception {
		
		auth = null;

		AuthenUtil.logOFF();
		IEUtil.closeBrowser();

		log.warn("S3523_POUserLogsIn_AuthExtension_02NG Has been Ended!");
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" }, dataProvider = "Auth-Credintials")
	public void testValidationBothCredintialsInPONG(String uName, String pwd,
			boolean expected) throws Exception {

		Assert.assertEquals(AuthenUtil.loginPO(uName, pwd), expected,
				"Fail: should be a Validation error");
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" }, dependsOnMethods="testValidationBothCredintialsInPONG")
	public void testValidationUntilUserLockedOutInPONG() throws Exception {

		AuthenUtil.switchToPOOnly(auth);

		Assert.assertEquals(GeneralUtil.LoginAnyUnsuccessfully("Reviewer1", 5),
				true);
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" }, dependsOnMethods = "testValidationUntilUserLockedOutInPONG")
	public void unlockPOUser() throws Exception {

		AuthenUtil.switchToPOOnly(auth);

		GeneralUtil.loginPO();

		UsersAndGroupsUtil.unlockUser("Reviewer1");

		AuthenUtil.switchToPOOnly(auth);
	}

	@DataProvider(name = "Auth-Credintials")
	public static Object[][] generateAuthCredintials() throws Exception {

		if (auth.getEPO().equals(EAuthProviderTypes.Def)) {
			auth.setPoPassword(IAuthenConst.defPwd);
		} else if (auth.getEPO().equals(EAuthProviderTypes.GUI)) {
			auth.setPoPassword(IAuthenConst.guiPwd);
		}

		return new Object[][] {
				new Object[] { auth.getPoUserName(), "wrong", false },
				new Object[] { "wrong", auth.getPoPassword(), false },
				new Object[] { "wrong", "wrong", false },
				new Object[] { auth.getPoUserName(), auth.getPoPassword(), true } };
	}

}
