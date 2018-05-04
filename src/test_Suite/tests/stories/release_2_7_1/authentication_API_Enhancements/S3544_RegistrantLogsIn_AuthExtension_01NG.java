/**
 * 
 */
package test_Suite.tests.stories.release_2_7_1.authentication_API_Enhancements;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
public class S3544_RegistrantLogsIn_AuthExtension_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3544_RegistrantLogsIn_AuthExtension_01NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3544_RegistrantLogsIn_AuthExtension_01NG Has been Started!");

		eAPO = EAuthProviderTypes.valueOf(po);
		eAFO = EAuthProviderTypes.valueOf(fo);

		log.info("Provider for Program Office: " + eAPO.toString());
		log.info("Provider for Front Office: " + eAFO.toString());

		auth = new Authens(eAPO, eAFO);

		auth.setFoUserName("front");

		AuthenUtil.switchToFOOnly(auth);
	}

	@AfterClass(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void tearDown() throws Exception {
		
		auth = null;

		AuthenUtil.logOFF();
		IEUtil.closeBrowser();

		log.warn("S3544_RegistrantLogsIn_AuthExtension_01NG Has been Ended!");

	}

	@Parameters( { "fo-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOUserNameFieldsExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLoginFieldsExist(IAuthenConst.foUName_txtField_id),
						hasLoginFields,
						"FAIL: User Name Field should not exists with SSO provider, The Provider now set to: "
								+ eAFO);

	}

	@Parameters( { "fo-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOPasswordFieldsExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLoginFieldsExist(IAuthenConst.foPassword_txtField_id),
						hasLoginFields,
						"FAIL: Password Field should not exists with SSO provider, The Provider now set to: "
								+ eAFO);

	}

	@Parameters( { "fo-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOLoginButtonExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil.doesLoginButtonExists(),
						hasLoginFields,
						"FAIL: Login Button should not exists with SSO provider, The Provider now set to: "
								+ eAFO);

	}

	@Parameters( { "fo-ForgotPassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOForgotYourPasswordLinkExistanceNG(
			boolean hasFgtUrPassowrdLnk) throws Exception {

		log.info(hasFgtUrPassowrdLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.foForgotPassword_Link_text),
						hasFgtUrPassowrdLnk,
						"FAIL: Forgot your password? link should exists with DEFAULT provider only, The Provider now set to: "
								+ eAFO);

	}

	@Parameters( { "fo-Profile" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOProfileLinkInLoginPageExistanceNG(boolean hasProfileLnk)
			throws Exception {

		log.info(hasProfileLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.foCreateProfile_InLoginPage_Link_text),
						hasProfileLnk,
						"FAIL: Create Profile link In Login page should exists with DEFAULT provider only, The Provider now set to: "
								+ eAFO);

	}

}
