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
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@Test(singleThreaded = true)
public class S3545_UserChangesPassword_AuthExtension_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3545_UserChangesPassword_AuthExtension_02NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3545_UserChangesPassword_AuthExtension_02NG Has been Started!");

		eAPO = EAuthProviderTypes.valueOf(po);
		eAFO = EAuthProviderTypes.valueOf(fo);

		log.info("Provider for Program Office: " + eAPO.toString());
		log.info("Provider for Front Office: " + eAFO.toString());

		auth = new Authens(eAPO, eAFO);

		auth.setFoUserName("front");
		
	}

	@AfterClass(groups = { "AuthenticationAPING" })
	public void tearDown() throws Exception {
		
		auth = null;

		AuthenUtil.logOFF();
		IEUtil.closeBrowser();

		log.warn("S3545_UserChangesPassword_AuthExtension_02NG has been Ended!");

	}
	
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void switchToFO() throws Exception {
		
		AuthenUtil.switchToFOOnly(auth);
	}

	@Parameters( { "fo-ForgotPassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" }, dependsOnMethods = "switchToFO")
	public void testFOForgotYourPasswordLinkExistanceNG(
			boolean hasfgtUrPasswordLnk) throws Exception {

		log.info(hasfgtUrPasswordLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.foForgotPassword_Link_text),
						hasfgtUrPasswordLnk,
						"FAIL: Forgot your password? link should exists with DEFAULT provider only, The Provider now set to: "
								+ eAFO);
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" }, dataProvider = "Auth-ResetPassword", dependsOnMethods = "testFOForgotYourPasswordLinkExistanceNG")
	public void testPOValidationForgotYourPasswordNG(String resetPwdVal, boolean expected) throws Exception {
		
		Assert.assertEquals(AuthenUtil.resetPassword(resetPwdVal),expected, "FAIL: error resetting Password");
		
		log.info("test");
	}
	
	@DataProvider(name = "Auth-ResetPassword")
	public static Object[][] generateForgotPassword() throws Exception {	
		
		return new Object[][] {	
				new Object[] { "wrongUser",false },		
				new Object[] { "front2", true },
				new Object[] { "WrongEmail@grant-nds-06.grantium.com", false},
				new Object[] { "qa_Registrant1@g3-qa-autobuild.csdc-lan.csdcsystems.com", true}};
	}

}
