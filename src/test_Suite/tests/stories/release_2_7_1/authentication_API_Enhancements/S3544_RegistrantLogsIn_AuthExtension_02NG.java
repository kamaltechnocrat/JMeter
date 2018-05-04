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
public class S3544_RegistrantLogsIn_AuthExtension_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory
			.getLog(S3544_RegistrantLogsIn_AuthExtension_02NG.class);

	IAuthenConst.EAuthProviderTypes eAPO;

	IAuthenConst.EAuthProviderTypes eAFO;

	static Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeClass(groups = { "AuthenticationAPING", "AuthAPIFO" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("S3544_RegistrantLogsIn_AuthExtension_02NG Has been Started!");

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

		log.warn("S3544_RegistrantLogsIn_AuthExtension_02NG Has been Ended!");
	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" }, dataProvider = "Auth-Credintials")
	public void testValidationBothCredintialsInFONG( String uName, String pwd, boolean expected) throws Exception {
		
		Assert.assertEquals(AuthenUtil.LoginFO(uName,pwd), expected, "FAIL: Should be a Validation errors");
		log.info("test");

	}

	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" },dependsOnMethods="testValidationBothCredintialsInFONG")
	public void testValidationUntilUserLockedOutInFONG() throws Exception {

		AuthenUtil.switchToFOOnly(auth);
		
		Assert.assertEquals(GeneralUtil.LoginAnyUnsuccessfully("front3", 5), true);

		log.info("test");

	}
	
	@Test(groups = { "AuthenticationAPING", "AuthAPIFO" }, dependsOnMethods = "testValidationUntilUserLockedOutInFONG")
	public void unluckFOUsers() throws Exception {
		
		auth.setPoUserName("admin");

		AuthenUtil.switchToPOOnly(auth);
		
		if(!auth.getEPO().equals(EAuthProviderTypes.SSO))
		{			
			GeneralUtil.loginPO();
		}
		
		UsersAndGroupsUtil.unlockUser("front3");
		
		AuthenUtil.switchToFOOnly(auth);
	}	

	@DataProvider(name = "Auth-Credintials")
	public static Object[][] generateAuthCredintials() throws Exception {
		
		if(auth.getEFO().equals(EAuthProviderTypes.Def))
		{
			auth.setFoPassword(IAuthenConst.defPwd);
		}
		else if(auth.getEFO().equals(EAuthProviderTypes.GUI))
		{
			auth.setFoPassword(IAuthenConst.guiPwd);
		}	

		return new Object[][] {
				new Object[] { auth.getFoUserName(), "wrong", false },
				new Object[] { "wrong", auth.getFoPassword(),false },
				new Object[] { "wrong", "wrong", false},
				new Object[] { auth.getFoUserName(), auth.getFoPassword(), true}};
	}

}
