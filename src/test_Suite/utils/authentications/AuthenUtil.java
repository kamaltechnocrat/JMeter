/**
 * 
 */
package test_Suite.utils.authentications;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.value;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.authentications.IAuthenConst;
import test_Suite.constants.authentications.IAuthenConst.EAuthProviderTypes;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.authentications.Authens;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.elements.Form;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 * 
 */
public class AuthenUtil {
	private static Log log = LogFactory.getLog(AuthenUtil.class);

	public static IAuthenConst.EAuthProviderTypes poEAuthProviderType;
	public static IAuthenConst.EAuthProviderTypes foEAuthProviderType;

	static String urlQueryString = "?username=";

	private static String poSiteBase;
	private static String foSiteBase;

	static List<String> errorsList;
	
	static Form form;
	static Tables tables;
	static Table table;

	public static void setSiteBase() throws Exception {
		try {

			Properties p = new Properties();
			p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

			poSiteBase = p.getProperty("siteBase") + IGeneralConst.url_programOffice;

			foSiteBase = p.getProperty("siteBase") + IGeneralConst.url_frontOffice;

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	public static void navigateToPO(String userName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		AuthenUtil.setSiteBase();

		urlQueryString = "?username=" + userName;

		ie.navigate(poSiteBase + urlQueryString);

	}

	public static void navigateToFO(String userName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		AuthenUtil.setSiteBase();

		urlQueryString = "?username=" + userName;

		ie.navigate(foSiteBase + urlQueryString);

	}

	public static boolean doesLoginButtonExists() throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.button(value, IAuthenConst.login_Button_value)
				.exists();
	}

	public static boolean doesLoginFieldsExist(String fieldTagId)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.textField(id, fieldTagId).exists();

	}

	public static boolean doesLinkByIdExists(String lnkId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.link(id, lnkId).exists();
	}

	public static boolean doesLinkByTextExists(String lnkText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.link(text, lnkText).exists();
	}

	public static boolean loginPO(String uName, String pwd) throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorsList = null;

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
		}

		GeneralUtil.getCurrentLocale();
		
		ie.textField(id, IGeneralConst.login_UserName_TxtId).set(uName);
		ie.textField(id, IGeneralConst.login_Password_TxtId).set(pwd);
		
		ClicksUtil.clickButtons(IClicksConst.loginBtn);

		errorsList = GeneralUtil.checkForErrorMessages();

		if ((errorsList != null) && (!errorsList.isEmpty())) {
			for (String string : errorsList) {

				log.error("Validation error Found: " + string);

			}

			return false;
		}

		return true;
	}

	public static boolean resetPassword(String eMailOrUserName)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorsList = null;
		
		tables = null;
		table = null;
		
		boolean FO = false;
		boolean PO = false;
		
		if(ie.form(id,IAuthenConst.poLoginForm_id).exists())
		{
			tables = ie.form(id,IAuthenConst.poLoginForm_id).tables();
			PO = true;
			
		}
		else if(ie.div(id,IAuthenConst.foLoginForm_id).exists())
		{
			//tables = ie.form(id,IAuthenConst.foLoginForm_id).tables();
			FO = true;
		}
		
		
		if(!FO && !PO)
		{
			log.error("Could not find any spalsh page!");
			
			return false;
		}		

		ClicksUtil.clickLinks(IAuthenConst.poForgotPassword_Link_text);
		
		if(FO)
		{
			ie.textField(id, IAuthenConst.forgotPwd_txtField_id).set(eMailOrUserName);
		}
		else
		{
			ie.textField(id, IAuthenConst.poForgotPwd_txtField_id).set(eMailOrUserName);
		}
		
		ClicksUtil.clickButtons(IAuthenConst.passwordResetBtn_value);
		
//		if(tables != null)
//		{
//			for (Table table : tables) {
//				
//				if(table.innerText().contains(IAuthenConst.poForgotPassword_Link_text))
//				{
//					table.link(text, IAuthenConst.poForgotPassword_Link_text).click();
//					
//					if(FO)
//					{
//						ie.textField(id, IAuthenConst.forgotPwd_txtField_id).set(eMailOrUserName);
//					}
//					else
//					{
//						ie.textField(id, IAuthenConst.poForgotPwd_txtField_id).set(eMailOrUserName);
//					}
//
//					ClicksUtil.clickButtons(IAuthenConst.passwordResetBtn_value);
//					
//					break;
//				}				
//			}
//		}	

		errorsList = GeneralUtil.checkForErrorMessages();

		if ((errorsList != null) && (!errorsList.isEmpty())) {
			for (String string : errorsList) {

				log.error("Validation error Found: " + string);

			}

			return false;
		}

		return true;
	}

	public static boolean LoginFO(String uName, String pwd) throws Exception {

		IE ie = IEUtil.getActiveIE();
		errorsList = null;
		
		GeneralUtil.getFOCurrentLocale();
		
		ie.textField(id, IGeneralConst.foLogin_UserName_TxtId).set(uName);
		ie.textField(id, IGeneralConst.foLogin_Password_TxtId).set(pwd);
		ClicksUtil.clickButtons(IClicksConst.loginBtn);

		errorsList = GeneralUtil.checkForErrorMessages();

		if ((errorsList != null) && (!errorsList.isEmpty())) {
			for (String string : errorsList) {

				log.error("Validation error Found: " + string);

			}

			return false;
		}

		return true;
	}

	public static void switchToFOOnly(Authens auth) throws Exception {

		IE ie = IEUtil.getActiveIE();

		logOFF();

		GeneralUtil.takeANap(3.0);

		if (auth.getEFO().equals(EAuthProviderTypes.SSO)) {
			ie.navigate((auth.getFoSiteBase().concat(urlQueryString))
					.concat(auth.getFoUserName()));

		} else {
			ie.navigate(auth.getFoSiteBase());
		}
	}

	public static void switchToPOOnly(Authens auth) throws Exception {

		IE ie = IEUtil.getActiveIE();

		logOFF();

		GeneralUtil.takeANap(3.0);

		if (auth.getEPO().equals(EAuthProviderTypes.SSO)) {
			ie.navigate((auth.getPoSiteBase().concat(urlQueryString))
					.concat(auth.getPoUserName()));

		} else {
			ie.navigate(auth.getPoSiteBase());
		}
	}

	public static void logOFF() throws Exception {		

		try {
			
			IE ie = IEUtil.getActiveIE();
			
			if (ie.link(text, "Logout").exists()) {
				
				log.warn("Found the Logout link!");
				ie.link(text, "Logout").click();
				
				GeneralUtil.takeANap(1.5);
				
				return;				
			}
			else
			{
				log.warn("DID NOT Find the Logout link!");
			}
			
			Properties p = new Properties();
			
			p.load(new FileInputStream(new File("src/test_Suite/deployment_path.properties")));

			String siteBaseUrl = p.getProperty("siteBase");

			ie.navigate(siteBaseUrl.concat("programOffice.jsf"));
			
			if(ie.link(text, "here").exists())
			{
				ie.link(text, "here").click();
			}
			
			if(ie.link(text, "here").exists())
			{
				ie.link(text, "here").click();
			}
			
			if(ie.link(text, "Logout").exists())
			{
				log.warn("Found the Logout link on The Second try!");
				ie.link(text, "Logout").click();
				GeneralUtil.takeANap(1.5);
			}	
			else
			{
				log.warn("DID NOT Find the Logout link on The Second try!");
			}
			
		} catch (Exception e) {
			log.error("No Logout Link", e);
			throw new RuntimeException("No Logout Link", e);
		}
	}
}
