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
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */
@Test(singleThreaded = true)
public class S3523_POUserLogsIn_AuthExtension_03NG {

	private static Log log = LogFactory
			.getLog(S3523_POUserLogsIn_AuthExtension_03NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3523_POUserLogsIn_AuthExtension_03NG Has been Started!");

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

		log.warn("S3523_POUserLogsIn_AuthExtension_03NG has been ended");

	}

	@Parameters( { "po-ChangePassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void testPOChangePasswordLinkExistanceNG(boolean hasChgPasswordLnk)
			throws Exception {

		log.info(hasChgPasswordLnk);

		AuthenUtil.switchToPOOnly(auth);

		if (eAPO.equals(EAuthProviderTypes.Def)) {
			
			auth.setPoPassword(IAuthenConst.defPwd);
			
			AuthenUtil.loginPO(auth.getPoUserName(), auth.getPoPassword());
			
		} else if (eAPO.equals(EAuthProviderTypes.GUI)) {

			auth.setPoPassword(IAuthenConst.guiPwd);
			
			AuthenUtil.loginPO(auth.getPoUserName(), auth.getPoPassword());
		}

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByIdExists(IAuthenConst.poChangePassword_Link_id),
						hasChgPasswordLnk,
						"FAIL: Change Password link should exists Only with DEFAULT Provider, The Provider now set to: "
								+ eAPO);

	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIPO" })
	public void testAdminLoginInPONG() throws Exception {

		if (!eAPO.equals(EAuthProviderTypes.SSO)) {
			AuthenUtil.switchToPOOnly(auth);
			GeneralUtil.loginPO();
			AuthenUtil.switchToPOOnly(auth);
		}

	}

}
