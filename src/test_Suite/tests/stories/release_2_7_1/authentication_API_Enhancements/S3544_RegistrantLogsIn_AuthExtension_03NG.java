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
public class S3544_RegistrantLogsIn_AuthExtension_03NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3544_RegistrantLogsIn_AuthExtension_03NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3544_RegistrantLogsIn_AuthExtension_03NG Has been Started!");

		eAPO = EAuthProviderTypes.valueOf(po);
		eAFO = EAuthProviderTypes.valueOf(fo);

		log.info("Provider for Program Office: " + eAPO.toString());
		log.info("Provider for Front Office: " + eAFO.toString());

		auth = new Authens(eAPO, eAFO);

		auth.setFoUserName("front");

		AuthenUtil.switchToFOOnly(auth);

		if (eAFO.equals(EAuthProviderTypes.Def)) {
			
			auth.setFoPassword(IAuthenConst.defPwd);
			
			AuthenUtil.LoginFO(auth.getFoUserName(), auth.getFoPassword());
			
		} else if (eAFO.equals(EAuthProviderTypes.GUI)) {
			
			auth.setFoPassword(IAuthenConst.guiPwd);
			
			AuthenUtil.LoginFO(auth.getFoUserName(), auth.getFoPassword());
		}		

	}

	@AfterClass(groups = { "AuthenticationAPING", "AuthAPIFO"  })
	public void tearDown() throws Exception {
		
		auth = null;

		AuthenUtil.logOFF();
		IEUtil.closeBrowser();

		log.warn("S3544_RegistrantLogsIn_AuthExtension_03NG Has been Ended!");

	}

	@Parameters( { "fo-ChangePassword" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO"  })
	public void testFOChangePasswordLinkExistanceNG(boolean hasChgPasswordLnk)
			throws Exception {

		log.info(hasChgPasswordLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.foChangePassword_Link_text),
						hasChgPasswordLnk,
						"FAIL: Change Password link should  exists with DEFAULT provider only, The Provider now set to: "
								+ eAFO);

	}

	@Parameters( { "fo-Profile" })
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void testFOProfileLinkInWorkspaceExistanceNG(boolean hasProfileLnk)
			throws Exception {

		log.info(hasProfileLnk);

		Assert
				.assertEquals(
						AuthenUtil
								.doesLinkByTextExists(IAuthenConst.foCreateProfile_InWorkspace_Link_text),
						hasProfileLnk,
						"FAIL: My Profile link In Workspace should exists with DEFAULT provider only, The Provider now set to: "
								+ eAFO);

	}

}
