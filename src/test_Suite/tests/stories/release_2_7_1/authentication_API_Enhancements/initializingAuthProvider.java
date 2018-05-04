/**
 * 
 */
package test_Suite.tests.stories.release_2_7_1.authentication_API_Enhancements;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test_Suite.constants.authentications.IAuthenConst;
import test_Suite.constants.authentications.IAuthenConst.EAuthProviderTypes;
import test_Suite.lib.authentications.Authens;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */
@Test(singleThreaded = true)
public class initializingAuthProvider {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory.getLog(initializingAuthProvider.class);

	IAuthenConst.EAuthProviderTypes poEAuthPrvdType;
	IAuthenConst.EAuthProviderTypes foEAuthPrvdType;

	Authens auth;

	// ------------- End of Global Vars
	// -----------------------------------------

	@Parameters( { "programOffice", "frontOffice" })
	@BeforeTest(groups = { "AuthenticationAPING" })
	public void setUp(String po, String fo) throws Exception {

		log.warn("initializingAuthProvider Has been Started!");
		log.info("Provider for Program Office: " + po);
		log.info("Provider for Front Office: " + fo);

		setAuthProvider(po, fo);

		IEUtil.openNewBrowser();
	}

	@AfterSuite(groups = { "AuthenticationAPING" })
	public void tearDown() throws Exception {
		log.info("Provider for Program Office: Default");
		log.info("Provider for Front Office: Default");

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("initializingAuthProvider has been ended");

	}

	private void setAuthProvider(String poProvider, String foProvider)
			throws Exception {

		poEAuthPrvdType = EAuthProviderTypes.valueOf(poProvider);
		foEAuthPrvdType = EAuthProviderTypes.valueOf(foProvider);

		auth = new Authens(poEAuthPrvdType, foEAuthPrvdType);
	}

}
