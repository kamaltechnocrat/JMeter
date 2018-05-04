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
public class S3523_POUserLogsIn_AuthExtension_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3523_POUserLogsIn_AuthExtension_01NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3523_POUserLogsIn_AuthExtension_01NG Has been Started!");

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

		log.warn("S3523_POUserLogsIn_AuthExtension_01NG has been ended");
	}

	@Parameters( { "po-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void testPOUserNameFieldsExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLoginFieldsExist(IAuthenConst.poUName_txtField_id),
						hasLoginFields,
						"FAIL: User Name Field should not exists with SSO Provider, The Provider now set to: "
								+ eAPO);

	}

	@Parameters( { "po-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void testPOPasswordFieldsExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLoginFieldsExist(IAuthenConst.poPassword_txtField_id),
						hasLoginFields,
						"FAIL: Password Field should not exists with SSO Provider, The Provider now set to: "
								+ eAPO);
	}

	@Parameters( { "po-LoginFields" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void testPOLoginButtonExistanceNG(boolean hasLoginFields)
			throws Exception {

		log.info(hasLoginFields);

		Assert
				.assertEquals(
						AuthenUtil.doesLoginButtonExists(),
						hasLoginFields,
						"FAIL: Login Button should not exists with SSO Provider, The Provider now set to: "
								+ eAPO);
	}

	@Parameters( { "po-ForgotPassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
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

}
