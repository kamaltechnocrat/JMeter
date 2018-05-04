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
public class S3545_UserChangesPassword_AuthExtension_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3545_UserChangesPassword_AuthExtension_01NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3545_UserChangesPassword_AuthExtension_01NG Has been Started!");

		eAPO = EAuthProviderTypes.valueOf(po);
		eAFO = EAuthProviderTypes.valueOf(fo);

		log.info("Provider for Program Office: " + eAPO.toString());
		log.info("Provider for Front Office: " + eAFO.toString());

		auth = new Authens(eAPO, eAFO);

		auth.setPoUserName("shak");
	}

	@AfterClass(groups = { "AuthenticationAPING" })
	public void tearDown() throws Exception {
		
		auth = null; 

		AuthenUtil.logOFF();
		IEUtil.closeBrowser();

		log.warn("S3545_UserChangesPassword_AuthExtension_01NG has been Ended!");
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void switchToPO() throws Exception {

		AuthenUtil.switchToPOOnly(auth);
	}

	@Parameters( { "po-ForgotPassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" }, dependsOnMethods = "switchToPO")
	public void testPOForgotYourPasswordLinkExistanceNG(
			boolean hasfgtUrPasswordLnk) throws Exception {

		log.info(hasfgtUrPasswordLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.poForgotPassword_Link_text),
						hasfgtUrPasswordLnk,
						"FAIL: Forgot your password? link should exists only with Default Provider, The Provider now set to: "
								+ eAPO);
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" }, dataProvider = "Auth-ResetPassword", dependsOnMethods = "testPOForgotYourPasswordLinkExistanceNG")
	public void testPOValidationForgotYourPasswordNG(String resetPwdVal,
			boolean expected) throws Exception {

		Assert.assertEquals(AuthenUtil.resetPassword(resetPwdVal),
				expected, "FAIL: error resetting Password");

		log.info("test");
	}

	@DataProvider(name = "Auth-ResetPassword")
	public static Object[][] generateForgotPassword() throws Exception {

		return new Object[][] {
				new Object[] { "wrongUser", false },
				new Object[] { "WrongEmail@grant-nds-06.grantium.com", false },
				new Object[] { "qa_Approver4@g3-qa-autobuild.csdc-lan.csdcsystems.com", true },
				new Object[] { "Approver5", true }};
	}

}
