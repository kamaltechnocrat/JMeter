package test_Suite.utils.cases;

/**
 *
 * @author mshakshouki
 */

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.name;
import static watij.finders.SymbolFactory.src;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.w3c.dom.Element;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IConfigConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjectUtil;
import watij.dialogs.ConfirmDialog;
import watij.dialogs.FileDownloadDialog;
import watij.elements.Button;
import watij.elements.Buttons;
import watij.elements.Div;
import watij.elements.Divs;
import watij.elements.Form;
import watij.elements.Forms;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Label;
import watij.elements.Link;
import watij.elements.Links;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.Table;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.elements.Tables;
import watij.elements.Options;
import watij.elements.Option;
import watij.runtime.ie.IE;

public class GeneralUtil {

	private static Log log = LogFactory.getLog(GeneralUtil.class);

	private static boolean retValue = false;

	//private static Tables tables;
	//private static Table table;
	private static Span span;
	private static Spans spans;
	static ResultSet rstSet;

	static Links links;

	private final static int BUFFSIZE = 1024;
	private static byte buff1[] = new byte[BUFFSIZE];
	private static byte buff2[] = new byte[BUFFSIZE];

	private static ArrayList<String> errorSmalls;


	/************************
	 *  Screen Shot with Java
	 *************************/
	public static void screen2image(String filename) throws Exception {
		Robot robot = new Robot();

		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(screenShot, "JPG", new File("test-output\\" + filename + ".jpg"));

	}

	public static void whoCallMe() throws Exception
	{
		String st = new Throwable().fillInStackTrace().getStackTrace()[1].getClassName();

		log.warn(st);

		log.warn(st.split("\\.")[st.split("\\.").length - 1]);

		log.warn(st.substring(st.lastIndexOf(".")));
	}


	public static boolean isSpanExistsById(String spanId) throws Exception {
		IE ie = IEUtil.getActiveIE();		

		return ie.span(id,spanId).exists();
	}


	/***************************
	 * ERROR checking
	 ****************************/

	public static ArrayList<String> checkForErrorMessages() throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorSmalls = new ArrayList<String>();

		List<Element> eles;

		if (ie.htmlElement(attribute("class", "ui-message-error-detail")).exists() && !ie.htmlElement(attribute("class", "ui-message-error-detail")).htmlElements().elements().isEmpty())
		{
			eles = ie.htmlElement(attribute("class", "ui-message-error-detail")).htmlElements().elements();

		}
		else if (ie.htmlElement(attribute("class", "errorSmall")).exists() && !ie.htmlElement(attribute("class", "errorSmall")).htmlElements().elements().isEmpty())
		{
			eles = ie.htmlElement(attribute("class", "errorSmall")).htmlElements().elements();
		}
		
		else if (ie.htmlElement(attribute("class", "/ui-messages-error/")).exists() && !ie.htmlElement(attribute("class", "/ui-messages-error/")).htmlElements().elements().isEmpty())
		{
			eles = ie.htmlElement(attribute("class", "/ui-messages-error/")).htmlElements().elements();
		}
		else
		{
			log.error("Could not find any error messages!");
			return errorSmalls;
		}

		for (Element element : eles) {

			if(element.getTextContent().trim() != "")
			{

				log.error("Validation Error: ".concat(element.getTextContent().trim()));

				errorSmalls.add(element.getTextContent().trim());
				return errorSmalls;

			}

		}

		return errorSmalls;
	}

	public static ArrayList<String> checkForInfoMessages(String divId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		ArrayList<String> infoSmalls;

		infoSmalls = new ArrayList<String>();

		if(!ie.div(id, divId).exists())
		{
			log.error("Could not find the Span on page!");
			return null;
		}

		spans = ie.div(id, divId).spans();

		for (Span span : spans) {

			if(span.innerText().length() != 0)
			{
//				log.warn(span.attribute("class"));
				log.warn(span.innerText().trim());
				infoSmalls.add(span.innerText().trim());
			}			
		}		

		return infoSmalls;
	}

	public static boolean findInErrorList(ArrayList<String> errList, String str) throws Exception {

		if(errList != null && !errList.isEmpty())
		{
			for(String string : errList)
			{
				if(string.contains(str))
				{
					log.warn(string);
					return true;
				}				
			}
		}
		return false;
	}

	/***********************************************
	 * File comparison
	 ***********************************************/
	public static boolean inputStreamEquals(InputStream is1, InputStream is2) {
		if(is1 == is2) return true;
		if(is1 == null && is2 == null) return true;
		if(is1 == null || is2 == null) return false;
		try {
			int read1 = -1;
			int read2 = -1;

			do {
				int offset1 = 0;
				while (offset1 < BUFFSIZE
						&& (read1 = is1.read(buff1, offset1, BUFFSIZE-offset1)) >= 0) {
					offset1 += read1;
				}

				int offset2 = 0;
				while (offset2 < BUFFSIZE
						&& (read2 = is2.read(buff2, offset2, BUFFSIZE-offset2)) >= 0) {
					offset2 += read2;
				}
				if(offset1 != offset2) return false;
				if(offset1 != BUFFSIZE) {
					Arrays.fill(buff1, offset1, BUFFSIZE, (byte)0);
					Arrays.fill(buff2, offset2, BUFFSIZE, (byte)0);
				}
				if(!Arrays.equals(buff1, buff2)) return false;
			} while(read1 >= 0 && read2 >= 0);
			if(read1 < 0 && read2 < 0) return true;	// both at EOF
			return false;

		} catch (Exception ei) {
			return false;
		}
	}

	public static boolean fileContentsEquals(File file1, File file2) {
		InputStream is1 = null;
		InputStream is2 = null;
		if(file1.length() != file2.length()) return false;

		try {
			is1 = new FileInputStream(file1);
			is2 = new FileInputStream(file2);

			return inputStreamEquals(is1, is2);

		} catch (Exception ei) {
			return false;
		} finally {
			try {
				if(is1 != null) is1.close();
				if(is2 != null) is2.close();
			} catch (Exception ei2) {}
		}
	}

	public static boolean fileContentsEquals(String fn1, String fn2) {
		return fileContentsEquals(new File(fn1), new File(fn2));
	}

	/***************************************************************************
	 * Configuring Some Properties
	 **************************************************************************/

	public static SimpleDateFormat sdfnew;

	public static Locale currentLocale;
	
	public static void getCurrentLocaleNew() throws Exception {
		try {
			
			navigateToPO();
			
			getCurrentLocale();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	public static void getFOCurrentLocale() throws Exception {
		try {

			IE ie = IEUtil.getActiveIE();

			String[] shortLocale = {"en_CA","en_US","fr_CA","en_GB"};
			String[] longLocale = {"English (Canada)", "English (U.S.)","Francais (Canada)", "English (UK)"};

			String localeType = "";

			for(int i=0; i<shortLocale.length;i++)
			{
				if(ie.div(id,shortLocale[i]).exists())
				{
					localeType = longLocale[i];
					break;
				}

				localeType = longLocale[i];
			}

			setCurrentLocale(localeType);			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	public static void getCurrentLocale() throws Exception {

		IE ie = IEUtil.getActiveIE();

		String localeType = "";

		if(ie.selectList(id,IGeneralConst.login_Locale_DdId).exists()) 			
		{
			Options ops = ie.selectList(id,IGeneralConst.login_Locale_DdId).options();
			
			for (Option option : ops) {
				
				if(option.attribute("selected").equals("selected"))
				{
					log.debug(option.innerText());
					
					localeType = option.innerText();
					break;
				}				
			}

		} else {

			Assert.fail("FATAL: Could not find Locale dropdown!");
		}

		setCurrentLocale(localeType);
	}

	public static boolean setCurrentLocale(String localeType) throws Exception {


		if (localeType.contains("Canada")) {

			sdfnew = new SimpleDateFormat("yyyy/MM/dd");
			if(localeType.charAt(0) == 'E' || localeType.charAt(0) == 'A')
			{
				currentLocale = Locale.CANADA;
			}
			else
			{
				currentLocale = Locale.CANADA_FRENCH;
			}

		} else if(localeType.contains("UK")) {

			sdfnew = new SimpleDateFormat("yyyy/MM/dd");

			currentLocale = Locale.UK;			

		} else if(localeType.contains("U.S.")){

			sdfnew = new SimpleDateFormat("MM/dd/yyyy");

			currentLocale = Locale.US;

		} else {
			log.error("Unable to find the proper locale");
			return false;
		}
		return true;
	}

	public static String getSiteBase() throws Exception {

		// Using Properties File
		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

		return p.getProperty("siteBase");
	}

	public static String getWorkspacePath() throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

		return p.getProperty("workSpacePath");
	}

	public static String getUserName() throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

		return p.getProperty("ntUserName");
	}

	public static String getReportBaseUrl() throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

		return p.getProperty("reportBaseUrl");
	}

	// ------------------ End of Configuring Properties
	// -------------------------------

	/***************************************************************************
	 * Navigation and Login to PO and FO Helpers
	 **************************************************************************/

	public static int howManyLinksInLeftMenu() throws Exception {

		IE ie = IEUtil.getActiveIE();
		int retLinks = -1;

		links = ie.span(id, IGeneralConst.menuLeft_SpanId).links();

		retLinks = links.length();

		return retLinks;
	}

	public static void switchToPOWithSubClerk(String clerkBeat) throws Exception {

		Logoff();

		navigateToPO();

		loginSubClerk(clerkBeat);
	}

	public static void switchToPOWithClerk(String clerkBeat) throws Exception {

		Logoff();

		navigateToPO();

		loginClerk(clerkBeat);
	}

	public static void switchToPOWithProjOfficer(String officerBeat) throws Exception {

		Logoff();

		navigateToPO();

		loginProjOfficer(officerBeat);
	}

	public static void switchToPOWithProgOfficer(String officerBeat) throws Exception {

		Logoff();

		navigateToPO();

		loginProgOfficer(officerBeat);
	}

	public static void switchToFO() throws Exception {

		log.warn("About to Switch to FO");

		Logoff();	
		
//		IEUtil.closeBrowser();

		takeANap(2.0);
		
//		IEUtil.openNewBrowser();

		log.warn("Just logged out!");

		navigateToFO();	

		log.warn("Navigated To FO Already!");

		log.warn("About to Log in to FO!");

		LoginFO();	

		log.warn("Just logged in to FO!");
	}

	public static void switchToPO() throws Exception {

		try {

			Logoff();

			navigateToPO();

			logInSuper();

		} catch (Exception e) {
			log.error("could not switch to PO", e);
			throw new RuntimeException("could not switch to PO", e);
		}
	}


	public static void switchToFOOnly() throws Exception {

		log.warn("About to Switch to FO Only");

		Logoff();		
		
		//IEUtil.closeBrowser();

		takeANap(2.0);
		
		//IEUtil.openNewBrowser();

		log.warn("Just logged out!");

		navigateToFO();	

		log.warn("Navigated To FO Already!");
	}


	public static void switchToPOOnly() throws Exception {

		Logoff();

		navigateToPO();
	}

	public static boolean logInSuper() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
			//return false;
		}

		int counter = 1;

		do {
			ie.textField(id, IGeneralConst.login_UserName_TxtId).set(IPreTestConst.PO_UsrName);
			ie.textField(id, IGeneralConst.login_Password_TxtId).set(IPreTestConst.Pwd);

			takeANap(2.0);

			log.warn("Login to PO Number: ".concat(String.valueOf(counter)));

			ClicksUtil.clickButtons(IClicksConst.loginBtn);

			if (ie.containsText(IUsersConst.poLoginErrorMessage)) 
				log.error("You have entered Invalid user name or Password!");

			counter++;

			if(counter >= 10)
			{
				log.error("After 10 trials I quit PO!");
				return false;
			}

		} while (ie.textField(id, IGeneralConst.login_UserName_TxtId).exists());
		return true;
	}

	public static boolean loginPO() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
			//			return false;
		}

		int counter = 1;

		do {
			ie.textField(id, IGeneralConst.login_UserName_TxtId).set(IPreTestConst.AdminUsrName);
			ie.textField(id, IGeneralConst.login_Password_TxtId).set(IPreTestConst.AdminPwd);

			takeANap(2.0);

			log.warn("Login to PO Number: ".concat(String.valueOf(counter)));

			if(!ClicksUtil.clickButtons(IClicksConst.loginBtn))
				log.error("could not click on button ".concat(IClicksConst.loginBtn));

			if(ie.containsText(IUsersConst.poLoginErrorMessage))
				log.error("You have entered Invalid user name or Password!");

			counter++;

			if(counter >= 10)
			{
				log.error("After 10 trials I quit PO!");
				return false;
			}

		} while (ie.textField(id, IGeneralConst.login_UserName_TxtId).exists());
		return true;
	}

	public static boolean loginFONew(String userName, String pwd) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
		}

		int counter = 1;

		do {
			ie.textField(id, IGeneralConst.foLogin_UserName_TxtId).set(userName);
			ie.textField(id, IGeneralConst.foLogin_Password_TxtId).set(pwd);

			takeANap(2.5);

			log.warn("Login to FO Number: ".concat(String.valueOf(counter)));

			ClicksUtil.clickButtons(IClicksConst.loginBtn);

			if (ie.containsText(IUsersConst.poLoginErrorMessage)) 
				log.error("You have entered Invalid user name or Password!");

			counter++;

			if(counter >= 10)
			{
				log.error("After 10 trials I quit FO!");
				return false;
			}

		} while (ie.textField(id, IGeneralConst.foLogin_UserName_TxtId).exists());

		return true;
	}

	public static boolean loginPONew(String userName, String pwd) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
			//return false;
		}

		int counter = 1;

		do {
			ie.textField(id, IGeneralConst.login_UserName_TxtId).set(userName);
			ie.textField(id, IGeneralConst.login_Password_TxtId).set(pwd);

			takeANap(2.5);

			log.warn("Login to PO Number: ".concat(String.valueOf(counter)));

			ClicksUtil.clickButtons(IClicksConst.loginBtn);

			if (ie.containsText(IUsersConst.poLoginErrorMessage))
				log.error("You have entered Invalid user name or Password!");

			counter++;

			if(counter >= 10)
			{
				log.error("After 10 trials I quit PO!");
				return false;
			}

		} while (ie.textField(id, IGeneralConst.login_UserName_TxtId).exists());




		return true;
	}

	public static boolean loginAny(String userName, String pwd) throws Exception {

		return loginPONew(userName,pwd);
	}


	public static void navigateToEP() throws Exception {


		IE ie = IEUtil.getActiveIE();

		Properties p = new Properties();

		p.load(new FileInputStream(new File("src/test_Suite/deployment_path.properties")));

		String siteBaseUrl = p.getProperty("siteBase");

		ie.waitUntilReady();

		ie.navigate(siteBaseUrl.concat("externalReview.jsf"));

		try {
			//getCurrentLocale();


		} catch (Exception e){
			log.error("Unexpected exception in navigateToPO()", e);
			String result = ie.html();
			log.debug("dumping result");
			log.debug(result);
			throw new RuntimeException("Unable to navigateToPO()");
		}
	}


	public static void navigateToPO() throws Exception {

		IE ie = IEUtil.getActiveIE();

		Properties p = new Properties();

		Date today = new Date();

		p.load(new FileInputStream(new File("src/test_Suite/deployment_path.properties")));

		String siteBaseUrl = p.getProperty("siteBase");

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		log.warn("The Time Now is: " + dateFormat.format(today) + " - " + today.getTime() + "ms");

		ie.waitUntilReady();

		log.warn("The Time Now is: " + dateFormat.format(today) + " - " + today.getTime() + "ms");

		ie.navigate(siteBaseUrl.concat("programOffice.jsf"));

		if(!ie.textField(id, IGeneralConst.login_UserName_TxtId).exists())
		{

			log.error("Could not navigate to PO!");

			//Assert.fail("Cold not navigate to PO!");

			takeANap(0.5);

//			IEUtil.closeBrowser();

			takeANap(1.5);

//			IEUtil.openNewBrowser();

			navigateToPO();
		}

		//getCurrentLocale();
	}

	public static void navigateToFO() throws Exception {
		IE ie = IEUtil.getActiveIE();

		// Using Properties File
		Properties p = new Properties();

		Date today = new Date();

		p.load(new FileInputStream(new File("src/test_Suite/deployment_path.properties")));

		String siteBaseUrl = p.getProperty("siteBase");

		//log.warn(getTodayDate());

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		log.warn("The Time Now is: " + dateFormat.format(today) + " - " + today.getTime() + "ms");

		ie.waitUntilReady();

		log.warn("The Time Now is: " + dateFormat.format(today) + " - " + today.getTime() + "ms");

		ie.navigate(siteBaseUrl.concat("frontOffice.jsf"));

		if(!ie.textField(id, IGeneralConst.foLogin_UserName_TxtId).exists())
		{
			log.error("Could not navigate to FO!");

			//Assert.fail("Cold not navigate to FO!");

			takeANap(0.5);

			IEUtil.closeBrowser();

			takeANap(1.5);

			IEUtil.openNewBrowser();

			navigateToFO();
		}

		//getFOCurrentLocale();

	}

	public static void Logoff() {

		try {

			IE ie = IEUtil.getActiveIE();

			if (ClicksUtil.clickLinks(IClicksConst.logOutLnk)) {

				log.warn("Found the Logout link!");

				takeANap(1.5);

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

			if(ClicksUtil.clickLinks(IClicksConst.HereLnk))
			{
				log.warn("Clicked on the First Here Link!");
			}

			if(ClicksUtil.clickLinks(IClicksConst.HereLnk))
			{
				log.warn("Clicked on the Second Here Link!");
			}

			if(ClicksUtil.clickLinks(IClicksConst.logOutLnk))
			{
				log.warn("Found the Logout link on The Second try!");

				takeANap(1.5);
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

	public static void logFOBack() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.htmlElement(title, "/English/").exists()) {
			ie.htmlElement(title, "/English/").click();
		}

		getFOCurrentLocale();

	}

	public static void logBack() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (ie.htmlElement(text, "here").exists()) {
			ie.htmlElement(text, "here").click();
		}
	}

	public static void LoginFO() throws Exception {

		loginFONew(IPreTestConst.FO_UsrName,IPreTestConst.Pwd);
	}

	public static void loginAnyFO(String usrName) throws Exception {

		loginFONew(usrName,IPreTestConst.Pwd);
	}

	public static void loginAnyFO(String usrName, String pwd) throws Exception {

		loginFONew(usrName,pwd);
	}

	public static void loginSanityFO(String userBeat) throws Exception {

		loginFONew("fsant" + userBeat,IPreTestConst.Pwd);
	}

	public static boolean LoginAnyUnsuccessfully(String usrName, int trialCount) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String uName = IGeneralConst.foLogin_UserName_TxtId;
		String pwd = IGeneralConst.foLogin_Password_TxtId;

		//getCurrentLocale();

		if (ie == null) {
			throw new RuntimeException("ie==null. Call OpenNewBrowser first");
		}

		if(ie.textField(id,IGeneralConst.login_UserName_TxtId).exists())
		{
			uName = IGeneralConst.login_UserName_TxtId;
			pwd = IGeneralConst.login_Password_TxtId;
		}

		for(int x=0; x<trialCount; x++)
		{
			ie.textField(id, uName).set(usrName);
			ie.textField(id, pwd).set("wrong");

			takeANap(2.0);
			ClicksUtil.clickButtons(IClicksConst.loginBtn);

			if (ie.containsText(IUsersConst.poLockedOutUserMessage)) {
				return true;
			}
		}	
		return false;
	}

	public static boolean LoginAny(String usrName) throws Exception {

		if(!loginPONew(usrName,IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a ".concat(usrName));
			return false;
		}
		return true;
	}

	public static boolean loginSanity(String userBeat) throws Exception {

		if(!loginPONew("sant".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'sant' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginApprover(String userBeat) throws Exception {

		if(!loginPONew("Approver".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'Approver' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginReviewer(String userBeat) throws Exception {

		if(!loginPONew("Reviewer".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'Reviewer' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginSubClerk(String userBeat) throws Exception {

		if(!loginPONew("SubClerk".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'SubClerk' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginClerk(String userBeat) throws Exception {

		if(!loginPONew("Clerk".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'Clerk' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginProjOfficer(String userBeat) throws Exception {

		if(!loginPONew("ProjectOfficer".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'ProjectOfficer' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginProgOfficer(String userBeat) throws Exception {

		if(!loginPONew("ProgramOfficer".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'ProgramOfficer' ".concat(userBeat));
			return false;
		}
		return true;
	}

	public static boolean loginCorrDelegates(String userBeat) throws Exception {

		if(!loginPONew("CorrDelegate".concat(userBeat),IPreTestConst.Pwd))
		{
			log.error("could not login to PO as a 'CorrDelegate' ".concat(userBeat));
			return false;
		}
		return true;
	}

	// ------------------- End Of Login Helpers --------------------------------

	/***************************************************************************
	 * GENERIC Methods
	 **************************************************************************/

	public static boolean openAnyListFromMenu(String listName) throws Exception {

		return false;
	}

	public static boolean isTextFieldReadOnly(int fieldIndex) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.textField(fieldIndex).exists())
		{
			log.error("The Text Field is Missing!");
			return false;
		}

		return ie.textField(fieldIndex).readOnly();
	}

	public static boolean isTextFieldReadOnlyById(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.textField(id, fieldId).exists())
		{
			log.error("The Text Field is Missing!");
			return false;
		}

		return ie.textField(id, fieldId).readOnly();
	}

	public static String getTextInSpanByClass(String className) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.span(attribute("class", className)).exists())
		{
			log.warn("The Span you are looking fo, does not exists!");
			return "";
		}

		return ie.span(attribute("class", className)).innerText();
	}

	public static String getTextFieldValue(String fldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.textField(id,fldId).value();		
	}

	public static boolean isTextFieldEmpty(String fldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.textField(id,fldId).exists())
		{
			log.error("Could not find the Text Field!");
			return false;
		}

		if(ie.textField(id,fldId).value().equals(""))
		{
			return true;
		}
		return false;
	}

	public static boolean isTextFieldReadOnlyByTtl(String txtTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Spans spans = ie.spans();

		for (Span span : spans) {
			
			if(span.innerText().contains(txtTtl) || span.innerText().startsWith("* ".concat(txtTtl)))
			{
				if(span.textField(0).exists())
				{
					return span.textField(0).readOnly();
				}				
			}			
		}

		log.error("Could not find Text Field For Title: ".concat(txtTtl));
		return false;
	}

	public static boolean isTextFieldExistByTitle(String txtTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(txtTtl) || div2.innerText().startsWith("* ".concat(txtTtl)))
			{
				if(div2.textField(0).exists())
				{
					return true;
				}				
			}			
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return false;
	}

	public static boolean isTextFieldEmptyByTitle(String txtTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(txtTtl) || div2.innerText().startsWith("* ".concat(txtTtl)))
			{
				if(div2.textField(0).exists())
				{
					return div2.textField(0).getContents().equalsIgnoreCase("");
				}				
			}			
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return false;
	}

	public static String getTextFieldContentsByTitle(String txtTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(txtTtl) || div2.innerText().startsWith("* ".concat(txtTtl)))
			{
				if(div2.textField(0).exists())
				{
					return div2.textField(0).getContents();
				}				
			}			
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return "";
	}

	public static boolean isTextFieldExistsById(String fielId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.textField(id, fielId).exists();
	}

	public static boolean isSelectListExists(String selectListId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.selectList(id,selectListId).exists();
	}

	public static boolean isSelectListExistsByTtl(String selectListTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(selectListTtl) || div2.innerText().startsWith("* ".concat(selectListTtl)))
			{
				if(div2.selectList(0).exists())
				{					
					return true;
				}				
			}			
		}
		log.error("Could Not Find the Dropdown or list!");
		return false;
	}

	public static boolean isSelectListEnabledByTitle(String selectListTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(selectListTtl) || div2.innerText().startsWith("* ".concat(selectListTtl)))
			{
				if(div2.selectList(0).exists() && div2.selectList(0).enabled())
				{					
					return true;
				}				
			}			
		}

		log.error("Could Not Find the Dropdown or list!");
		return false;
	}

	public static boolean isSelectListEnabledById(String selectListId) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!isSelectListExists(selectListId))
		{
			log.error("Could Not Find the Dropdown or list!");
			return false;
		}
		return ie.selectList(id, selectListId).enabled();
	}

	public static boolean isButtonExists(String buttonValue) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		Buttons btns = ie.buttons(attribute("role", IClicksConst.btnRole));
		
		for (Button btn : btns){
			if (btn.span(text, buttonValue).exists()){
				return true;
			}
		}
		return false;
	}

	public static boolean isButtonEnabled(String buttonValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Buttons btns = ie.buttons(attribute("role", IClicksConst.btnRole));
		
		for (Button btn : btns){
			if (btn.span(text, buttonValue).exists()){
				//return ie.span(text, buttonValue).enabled();
				//return btn.attribute("aria-disabled").equals("false");
				return btn.enabled();
				
			}
		}
			log.error("Could Not Find the button " + buttonValue +"!");
			return false;
	}


	public static boolean isButtonDisabled(String buttonValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Buttons btns = ie.buttons(attribute("role", IClicksConst.btnRole));
		
		for (Button btn : btns){
			if (btn.span(text, buttonValue).exists()){
				//return ie.span(text, buttonValue).disabled();	
				//return btn.attribute("disabled").equals("");
				
				return btn.disabled();
				
			}
		}
			log.error("Could Not Find the button " + buttonValue +"!");
			return false;
	}

	public static boolean isButtonExistsByValue(String buttonValue) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		Buttons btns = ie.buttons(attribute("role", IClicksConst.btnRole));
		
		for (Button btn : btns){
			if (btn.span(text, buttonValue).exists()){
				return true;
			}
		}
		return false;
	}

	public static boolean isImageExistsByID(String imageID) throws Exception {
		IE ie = IEUtil.getActiveIE();

		return ie.image(id, imageID).exists(); 
	}

	public static boolean isImageExistsBySrc(String imageSrc) throws Exception {
		IE ie = IEUtil.getActiveIE();

		return ie.image(src, imageSrc).exists();
	}

	public static boolean isImageExistsByAlt(String imageAlt) throws Exception {
		IE ie = IEUtil.getActiveIE();

		return ie.image(alt, imageAlt).exists();
	}

	public static boolean isFileFieldExistsById(String fileFieldId) throws Exception {
		IE ie = IEUtil.getActiveIE();

		return ie.fileField(id, fileFieldId).exists();
	}

	public static boolean isFileFieldExistsByIndex(int index) throws Exception {
		IE ie = IEUtil.getActiveIE();

		return ie.fileField(index).exists();
	}

//	public static void expandAllImg() throws Exception {
//		IE ie = IEUtil.getActiveIE();
//
//		// expand the drill down
//		while (ie.image(src, IGeneralConst.drillDown).exists())
//		{
//			ie.image(src, IGeneralConst.drillDown).click();
//		}
//	}
	
//	public static boolean expandAllSpans() throws Exception {
//		IE ie = IEUtil.getActiveIE();
//
//		try {
//			Spans spans = ie.spans(attribute("class", IClicksConst.collapsedSpan));
//			for (Span span : spans){
//				if(span.exists())
//					span.click();
//			}
//			
//		}catch (Exception e) {
//			log.error("Unexpected Exception: " + e);
//			return false;
//		}
//		return true;
//	}

	public static boolean isFormTagExistsById(String formId) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Forms forms;
		forms = ie.forms();

		for (Form form : forms) {

			String str = form.id();

			if(str.contains(formId)) {
				return true;
			}			
		}		
		return false;		
	}

	public static boolean isLinkExistsByTitle(String titleStr) throws Exception {		
		IE ie = IEUtil.getActiveIE();

		return ie.link(title, titleStr).exists();
	}

	public static boolean isLinkExistsByTxt(String linkTxt) throws Exception {	
		IE ie = IEUtil.getActiveIE();

		return ie.link(text, linkTxt).exists();
	}

	public static boolean isImageExistsInTableByAlt(String tableId, int rowIndex,String imgAlt) throws Exception {
		Div tDiv = TablesUtil.tableDiv();

		return tDiv.body(id, tableId).row(rowIndex).image(alt, imgAlt).exists();
	}

	public static boolean isImageExistsAnyWhereInTable(String tableId, String imageAlt) throws Exception {
		Div tDiv = TablesUtil.tableDiv();

		Images images = tDiv.body(id,tableId).images();

		for (Image image : images) {

			if(image.alt().equalsIgnoreCase(imageAlt)) {
				return true;
			}			
		}
		return false;
	}

	public static boolean findTextInTextFieldById(String fieldId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();
		retValue = false;

		if (ie.textField(id, fieldId).getContents().matches(strToFind)) {
			retValue = true;
		}
		return retValue;
	}

	public static boolean findTextInTextFieldByIndx(int fieldIndx, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();
		retValue = false;

		if (ie.textField(fieldIndx).getContents().matches(strToFind)) {
			retValue = true;
		}
		return retValue;
	}

	public static boolean verifyFixedTextBySpanID(String spanId, String toFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		span = ie.span(id, spanId);

		spans = span.spans();

		String str;

		for (Span span : spans) {

			log.debug(span.innerText().trim());

			str = span.innerText().trim();

			if(str.equalsIgnoreCase(toFind))
			{
				return true;
			}			
		}

		return false;
	}

	public static boolean verifyFixedTextByDivClassWithenTable(String Tableid,String divClass, String toFind) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		Divs divs = tDiv.body(id, Tableid).divs();
		String str;

		for (Div div2 : divs) {

			log.debug(div2.innerText().trim());

			str = div2.innerText().trim();

			if(str.equalsIgnoreCase(toFind))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean verifyFixedTextBySpanClassWithinTable(String Tableid,String spanClass, String toFind) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		Spans spans = tDiv.body(id, Tableid).spans();
		String str;

		for (Span span : spans) {

			log.debug(span.innerText().trim());

			str = span.innerText().trim();

			if(str.equalsIgnoreCase(toFind))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean verifyFixedTextByDivClass(String divClass, String toFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div div = ie.div(attribute("class", divClass));

		Divs divs = div.divs();
		String str;

		for (Div div2 : divs) {

			log.debug(div2.innerText().trim());

			str = div2.innerText().trim();

			if(str.equalsIgnoreCase(toFind))
			{
				return true;
			}			
		}

		//		span = ie.span(id, spanId);
		//		
		//		spans = span.spans();
		//		
		//		
		//		
		//		for (Span span : spans) {
		//			
		//			log.debug(span.innerText().trim());
		//			
		//			str = span.innerText().trim();
		//			
		//			if(str.equalsIgnoreCase(toFind))
		//			{
		//				return true;
		//			}			
		//		}

		return false;
	}

	public static boolean isCheckboxCheckedByTitle(String fieldTtle) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(fieldTtle) || div2.innerText().startsWith("* ".concat(fieldTtle)))
			{
				if(div2.checkbox(0).exists())
				{
					log.warn("The Checkbox was found!");
					return div2.checkbox(0).checked();
				}				
			}			
		}

		log.error("Could not find Checkbox For Title: ".concat(fieldTtle));
		return false;
	}

	public static boolean isCheckboxCheckedById(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.checkbox(id, fieldId).checked();
	}

	public static boolean isCheckboxCheckedByName(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.checkbox(name, fieldId).checked();
	}


	public static boolean isCheckboxExistsOrEnabledInTable(String tableId,int rowIndex, int cellIndex) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id,tableId).row(rowIndex).cell(cellIndex).checkbox(0).exists()) {

			return tDiv.body(id,tableId).row(rowIndex).cell(cellIndex).checkbox(0).enabled();
		}
		return false;
	}

	public static String getSelectedValueInDropDownByTitle(String fieldTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(fieldTtl) || div2.innerText().startsWith("* ".concat(fieldTtl)))
			{
				if(div2.selectList(0).exists())
				{
					log.debug("The Dropdown was found!");
					
					Options ops = div2.selectList(0).options();
					
					for (Option option : ops) {
						
						if(option.attribute("selected").equals("selected"))
						{
							log.debug(option.innerText());
							
							return option.innerText();
						}
						
					}

					Assert.assertNull("Could not fetch the selected value!");
					
				}				
			}			
		}

		log.error("Could not find Dropdown For Title: ".concat(fieldTtl));
		return "";
	}

	public static String getSelectedItemValueInDropdwonById(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(ie.selectList(id, fieldId).exists(), "FAIL: Could not Find Dropdown specified!");
		
		Options ops = ie.selectList(id, fieldId).options();
		
		for (Option option : ops) {
			
			if(option.attribute("selected").equals("selected"))
			{
				log.debug(option.innerText());
				
				return option.innerText();
			}
			
		}

		Assert.assertNull("Could not fetch the selected value!");
		
		return "";

	}

	public static String getSelectedValueInDropDownById(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(ie.selectList(id, fieldId).exists(), "FAIL: Could not Find Dropdown specified!");
		
		Options ops = ie.selectList(id, fieldId).options();
		
		for (Option option : ops) {
			
			if(option.attribute("selected").equals("selected"))
			{
				log.debug(option.innerText());
				
				return option.innerText();
			}			
		}

		Assert.assertNull("Could not fetch the selected value!");
		
		return "";
	}

	public static String getSelectedValueInDropDown(int fieldIndex) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Assert.assertTrue(ie.selectList(fieldIndex).exists(), "FAIL: Could not Find Dropdown specified!");
		
		Options ops = ie.selectList(fieldIndex).options();
		
		for (Option option : ops) {
			
			if(option.attribute("selected").equals("selected"))
			{
				log.debug(option.innerText());
				
				return option.innerText();
			}
			
		}

		Assert.assertNull("Could not fetch the selected value!");
		
		return "";

	}

	public static String getTextInTextFieldById(String fieldId) throws Exception {

		IE ie = IEUtil.getActiveIE();		

		Assert.assertTrue(ie.textField(id,fieldId).exists(), "FAIL: Could not find TextField specified!");

		return ie.textField(id, fieldId).getContents();
	}

	public static String getTextInTextFieldByIndex(int fieldIndex) throws Exception {

		IE ie = IEUtil.getActiveIE();		

		Assert.assertTrue(ie.textField(fieldIndex).exists(), "FAIL: Could not find TextField specified!");

		return ie.textField(fieldIndex).getContents();
	}

	public static boolean FindTextOnPage(String txtString) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.containsText(txtString);
	}

	public static String FindNewBaseLetter(String baseObjectName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		boolean loop = true;

		Integer pageNumber = 1;

		int arrIndex = 0;

		while (loop) {

			if(pageNumber == 1)
			{
				ie.link(text, IGeneralConst.baseLetters[arrIndex]).click();
			}			

			pageNumber += 1;

			if (ie.containsText(IGeneralConst.baseLetters[arrIndex]
					+ baseObjectName))
			{				
				arrIndex += 1;
				pageNumber = 1;				
			}				
			else if((ie.link(text, pageNumber.toString()).exists()))
			{				
				ie.link(text, pageNumber.toString()).click();				
			}
			else
			{
				loop = false;
			}

		}
		return IGeneralConst.baseLetters[arrIndex];
	}

	public static String FindUsedBaseLetter(String baseObjectName)
			throws Exception {
		IE ie = IEUtil.getActiveIE();
		boolean loop = true;

		int arrIndex = 0;

		while (loop) {

			ie.link(text, IGeneralConst.baseLetters[arrIndex]).click();

			if (ie.containsText(IGeneralConst.baseLetters[arrIndex]
					+ baseObjectName))
				loop = false;
			else
				arrIndex += 1;
		}
		return IGeneralConst.baseLetters[arrIndex];
	}

	public static String getImageAlt(String imgID) throws Exception {
		IE ie = IEUtil.getActiveIE();
		String imgAlt;
		imgAlt = ie.image(id, imgID).alt();

		return imgAlt;

	}
	
	public static boolean fillGrid() throws Exception {

		String str = getTextBoxPartId();

		for (int x = 1; x <= 5; x++) {

			for (int y = 1; y <= 3; y++) {

				String reg = "/dataGrid:" + String.valueOf(x) + ":" + str + ":"	+ String.valueOf(y) + "/";

				//ie.textField(name, reg).set(String.valueOf(IGeneralConst.arr13X4Grid[x][y]));

				if(!setTextById(reg, String.valueOf(IGeneralConst.arr13X4Grid[x][y])))
				{
					log.error("Could not fill the Grid text field!");
					return false;
				}
			}
		}
		return true;
	}

	public static String getTextBoxPartId() throws Exception {

		IE ie = IEUtil.getActiveIE();
		String str = ie.textField(0).id();
		String arr[] = str.split(":");

		return arr[arr.length - 3];
	}

	public static boolean setTextByIndex(int indx, String txtValue)	throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(ie.textField(indx).exists() && ie.textField(indx).enabled())
		{
			ie.textField(indx).set(txtValue);

			return true;
		}

		log.warn("The Text Field is either Missing or Disabled!");
		return false;
	}

	public static boolean setTextById(String txtId, String txtValue) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.textField(id,txtId).exists())
		{
			log.error("Could not find Text Field! ".concat(txtId));
			return false;
		}

		if(!ie.textField(id,txtId).enabled())
		{
			log.error("Text Field is disabled! ".concat(txtId));
			return false;
		}		

		if(ie.span(text, IClicksConst.editBtn).exists()&&ie.span(text, IClicksConst.editBtn).enabled()){
//		if(ie.button(IClicksConst.editBtn).exists()&&ie.button(IClicksConst.editBtn).enabled()){

			ClicksUtil.clickButtons(IClicksConst.editBtn);
		}

		ie.textField(id,txtId).set(txtValue);
		return true;
	}

	public static boolean enterTextByTitle(String txtTtl, String strToEnter) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Spans spans = ie.spans();

		for (Span span2 : spans) {

			if(span2.innerText().trim().startsWith(txtTtl) || span2.innerText().trim().startsWith("* ".concat(txtTtl)))
			{
				if(span2.textField(0).exists() && !span2.textField(0).readOnly())
				{
					span2.textField(0).set(strToEnter);					

					if(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn))
					{
						return true;
					}
					else if(ClicksUtil.clickButtons(IClicksConst.saveBtn))
					{
						return true;
					}
					else
					{
						log.error("Could not Click Next nor Save button");
						return false;
					}
				}				
			}			
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return false;
	}

	public static String readTextByLabel(String txtTtl) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Spans spans = ie.div(id, "g3-form:fi").spans();

		//Divs divs = ie.divs();

		for (Span span : spans) {

			if(span.innerText().startsWith(txtTtl.concat(":")) || span.innerText().startsWith("* ".concat(txtTtl.concat(":"))))
			{
				if(span.textField(0).exists())
				{
					return span.textField(0).getContents();
				}				
			}			
		}
		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return null;
	}

	public static boolean selectInDropdownList(String drpDwnId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String tempStr = null;

		List<String> lst = ie.selectList(id, drpDwnId).getAllContents();

		if (lst.size() > 0) {

			for (int i = 0; i < lst.size(); i++) {

				tempStr = lst.get(i).toString();

				log.debug(tempStr);

				if (tempStr.contains(strToFind)) {

					ie.selectList(id, drpDwnId).select("/" + strToFind + "/");

					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean selectInDropdownListByLabel(String labelTitle, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String tempStr = null;

		Tables tables = ie.tables();

		for (Table table :tables) {

			if (table.innerText().trim().contains(labelTitle)){

				List<String> lst = table.selectList(0).getAllContents();

				if (lst.size() > 0) {

					for (int i = 0; i < lst.size(); i++) {

						tempStr = lst.get(i).toString();

						log.debug(tempStr);

						if (tempStr.contains(strToFind)) {

							table.selectList(0).select("/" + strToFind + "/");

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean dropDownSingle_foApplicant(String ddLblString, String ddLstVal) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.label(id, IGeneralConst.fo_ApplicantType_LblID).exists())
		{
			log.error("Could not find Label!");
			return false;
		}

		Label label = ie.label(id,IGeneralConst.fo_ApplicantType_LblID);

		boolean retValue = false;

		log.debug(label.innerText());
		log.debug(label.innerText().split(":")[0]);


		log.warn(label.innerText().split(":")[0].trim());
		if(label.innerText().split(":")[0].trim().contains(ddLblString)) {

			retValue = true;

			if(!ddLstVal.trim().equals("")) 
			{      
				log.warn(ie.selectList());
				if(!ie.selectList().exists())
				{
					log.error("Could not find Dropdown!");
					return false;
				}
				label.selectList(0).select(ddLstVal.trim());
			}
		}
		return retValue;           
	}

	@SuppressWarnings("null")
	public static boolean selectInDropdownListTest(String drpDwnId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String[] tempStr = null;

		List<String> lst = ie.selectList(id, drpDwnId).getAllContents();

		if (lst.size() > 0) {

			for (int i = 0; i < lst.size(); i++) {

				tempStr[i]= lst.get(i).toString();

				log.debug(tempStr[i]);


				for (String item : tempStr)
				{

					if(item.equalsIgnoreCase(strToFind)) {

						ie.selectList(id, drpDwnId).select(strToFind);

						return true;

					}

				}


			}
		}

		return false;
	}

	public static boolean selectRadioButton(String valToSelect) throws Exception {		

		try {
			IE ie = IEUtil.getActiveIE();

			TableRows rows = ie.table(id,  IGeneralConst.radioButtonSet).rows();

			for (TableRow tableRow : rows) {

				if(tableRow.innerText().trim().equalsIgnoreCase(valToSelect))
				{
					tableRow.radio(0).set();

					ClicksUtil.clickButtons(IClicksConst.saveBtn);

					return true;					
				}				
			}			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return false;
	}

	public static boolean selectRadioButton(String tabId, String valToSelect) throws Exception {		

		try {
			IE ie = IEUtil.getActiveIE();

			TableRows rows = ie.table(id,  tabId).rows();

			for (TableRow tableRow : rows) {

				if(tableRow.innerText().trim().equalsIgnoreCase(valToSelect))
				{
					tableRow.radio(0).set();

					ClicksUtil.clickButtons(IClicksConst.saveBtn);

					return true;					
				}				
			}			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return false;
	}

	public static boolean selectFullStringInDropdownList(String drpDwnId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		takeANap(1.5);

		if(!ie.selectList(id,drpDwnId).exists()) {

			log.error("Could not find the dropdown to select from!");
			return false;
		}

		List<String> lst = ie.selectList(id, drpDwnId).getAllContents();

		if (lst.size() > 0) {

			for (String string : lst) {
				
				log.debug(string);
				log.debug(strToFind);

				if(string.equalsIgnoreCase(strToFind)) {

					ie.selectList(id, drpDwnId).select(strToFind);

					takeANap(1.5);

					return true;					
				}				
			}			
		}
		return false;
	}


	public static boolean verify_dropdown_List(String drpDwnId, String strToFind) throws Exception {


		IE ie = IEUtil.getActiveIE();

		if(!ie.selectList(id,drpDwnId).exists()) {

			log.error("Could not find the dropdown to select from!");
			return false;
		}

		List<String> lst = ie.selectList(id, drpDwnId).getAllContents();

		if (lst.size() > 0) {

			for (String string : lst) {

				log.debug(string);						

				if(string == strToFind) {

					log.debug("values match");

					return true;					
				}						

			}
		}
		return false;
	}



	public static boolean doesImageExistsAnyWhereInTable(String tableId, String imageAlt) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		Images images = tDiv.body(id,tableId).images();

		for (Image image : images) {

			if(image.alt().equalsIgnoreCase(imageAlt)) {
				return true;
			}			
		}

		return false;
	}

	public static boolean selectFullStringInDropdownListByTitle(String drpDwnTtl, String strToFind) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Div div = null;
		
		if(ie.div(attribute("class", IGeneralConst.class_g3mainContainer)).exists())
		{
			div = ie.div(attribute("class", IGeneralConst.class_g3mainContainer));
		}
		else
		{
			div = ie.div();
		}	

		for (Span sp : div.spans()) {

			for (Span span1 :sp.spans()){

				log.info(span1.innerText());
				
				if (!span1.div(0).exists()) {
					break;
				}

				if(span1.div(0).innerText().trim().startsWith(drpDwnTtl) || span1.div(0).innerText().trim().startsWith("* ".concat(drpDwnTtl)))
				{
					if(span1.selectList(0).exists() && span1.selectList(0).enabled())
					{
						
						List<String> lst = span1.selectList(0).getAllContents();
	
						if (lst.size() > 0) 
						{
	
							for (String string : lst) 
							{
								log.debug(string);
	
								if(string.equalsIgnoreCase(strToFind)) 
								{
	
									span1.selectList(0).select(strToFind);
	
									takeANap(1.5);
	
									return true;		
								}
							}
						}
					}				
				}
			}
		}	

		log.error("Could not find the dropdown to select from!");
		return false;
		
//		IE ie = IEUtil.getActiveIE();
//
//		Div div = null;
//		
//		if(ie.div(attribute("class", IGeneralConst.class_g3mainContainer)).exists())
//		{
//			div = ie.div(attribute("class", IGeneralConst.class_g3mainContainer));
//		}
//		else
//		{
//			div = ie.div();
//		}	
//
//		for (Span sp : div.spans()) {
//
//			for (Span span1 :sp.spans()){
//
//				log.info(span1.innerText());
//				
//				if (!span1.div(0).exists()) {
//					break;
//				}
//
//				if(span1.div(0).innerText().startsWith(drpDwnTtl) || span1.div(0).innerText().startsWith("* ".concat(drpDwnTtl)))
//				{
//					if(span1.span(1).selectList(0).exists() && span1.span(1).selectList(0).enabled())
//					{
//						
//						List<String> lst = span1.span(1).selectList(0).getAllContents();
//	
//						if (lst.size() > 0) 
//						{
//	
//							for (String string : lst) 
//							{
//								log.debug(string);
//	
//								if(string.equalsIgnoreCase(strToFind)) 
//								{
//	
//									span1.span(1).selectList(0).select(strToFind);
//	
//									takeANap(1.5);
//	
//									return true;		
//								}
//							}
//						}
//					}				
//				}
//			}
//		}	
//
//		log.error("Could not find the dropdown to select from!");
//		return false;
	}

	public static boolean selectFullStringInDropdownListByLbl(String drpDwnLbl, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		TableRows row = null;

		if(ie.div(attribute("class", "labelRight")).exists())
		{
			row = ie.row(attribute("class", "labelRight")).rows();
		}
		else
		{
			row = ie.rows();
		}	

		for (TableRow rows : row) {

			if(rows.innerText().startsWith(drpDwnLbl) || rows.innerText().startsWith("* ".concat(drpDwnLbl)))
			{
				if(rows.selectList(0).exists() && rows.selectList(0).enabled())
				{					
					List<String> lst = rows.selectList(0).getAllContents();

					if (lst.size() > 0) {

						for (String string : lst) {
							log.debug(string);

							if(string.equalsIgnoreCase(strToFind)) {

								rows.selectList(0).select(strToFind);

								takeANap(1.5);

								return true;					
							}				
						}			
					}
				}				
			}			
		}		

		log.error("Could not find the dropdown to select from!");
		return false;
	}

	public static boolean findInM2MList(int lstIndx, String strToFind)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(lstIndx).getAllContents();

		Pattern pattern = Pattern.compile(strToFind.replace("/", ""));

		if (lst.size() > 0) {
			for (int i = 0; i <= (lst.size() - 1); i++) {

				log.debug(lst.get(i).toString());

				Matcher matcher = pattern.matcher(lst.get(i).toString());

				if (matcher.find())
					return true;
			}
		}
		return false;
	}

	public static boolean findInM2MListById(String lstId, String strToFind)	throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id,lstId).getAllContents();

		Pattern pattern = Pattern.compile(strToFind.replace("/", ""));

		if (lst.size() > 0) {
			for (int i = 0; i <= (lst.size() - 1); i++) {

				log.warn(lst.get(i).toString());

				Matcher matcher = pattern.matcher(lst.get(i).toString());

				if (matcher.find())
					return true;
			}
		}
		return false;
	}

	public static boolean findExactTextInM2MListById(String lstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id,lstId).getAllContents();

		if (lst.size() > 0) {
			log.debug("String To Find: " + strToFind);
			log.debug(strToFind.length());
			for (int i = 0; i <= (lst.size() - 1); i++) {

				log.debug(lst.get(i).toString());
				log.debug(lst.get(i).length());

				if(lst.get(i).equals(strToFind))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean findStartTextInM2MListById(String lstId, String strToFind)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id,lstId).getAllContents();

		if (lst.size() > 0) {
			for (int i = 0; i <= (lst.size() - 1); i++) {

				log.debug(lst.get(i).toString());

				if(lst.get(i).startsWith(strToFind))
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean deSelectfromM2M(String selectedLstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(findInM2MListById(selectedLstId,strToFind))
		{
			ie.selectList(id,selectedLstId).select(strToFind);			

			GeneralUtil.takeANap(1.5);

			ClicksUtil.clickButtons(IClicksConst.m2MSingleBackBtn);
			
			GeneralUtil.takeANap(2.5);
		}

		return !findInM2MListById(selectedLstId,strToFind);
	}

	public static boolean selectfromM2M_NoSave(String availableLstId, String selectedLstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(findInM2MListById(selectedLstId,strToFind))
		{
			log.warn("Already exists in the Selected List!");
			return true;
		}

		if(!findInM2MListById(availableLstId,strToFind))
		{
			log.error("Could not find it in the Available List! ".concat(strToFind));
			return false;
		}

		ie.selectList(id,availableLstId).select(strToFind);		
		ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		GeneralUtil.takeANap(2.5);

		if(!findInM2MListById(selectedLstId,strToFind))
		{
			log.error("does not exist after selecting and saving!");
			return false;
		}		
		return true;
	}

	public static boolean selectfromM2M(String availableLstId, String selectedLstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(findInM2MListById(selectedLstId,strToFind))
		{
			log.warn("Already exists in the Selected List!");
			return true;
		}

		if(!findInM2MListById(availableLstId,strToFind))
		{
			log.error("Could not find it in the Available List! ".concat(strToFind));
			return false;
		}

		ie.selectList(id,availableLstId).select(strToFind);		
		ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		GeneralUtil.takeANap(1.5);
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		GeneralUtil.takeANap(2.5);

		if(!findInM2MListById(selectedLstId,strToFind))
		{
			log.error("does not exist after selecting and saving!");
			return false;
		}		
		return true;
	}

	public static boolean selectfromM2M_New(String availableLstId, String selectedLstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(findInM2MListById(availableLstId,strToFind))
		{
			ie.selectList(id,availableLstId).select(strToFind);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);

			GeneralUtil.takeANap(1.5);
		}
		else
		{
			return findInM2MListById(selectedLstId,strToFind);
		}
		return true;
	}

	public static boolean selectExactfromM2M(String availableLstId, String selectedLstId, String strToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(findExactTextInM2MListById(availableLstId,strToFind))
		{
			ie.selectList(id,availableLstId).select(strToFind);

			ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);

			GeneralUtil.takeANap(1.5);
		}
		else
		{
			return findInM2MListById(selectedLstId,strToFind);
		}
		return true;
	}

	public static boolean isObjectExistsInList(String lstId, String strToFind)
			throws Exception {

		retValue = false;

		String tmpElement;

		IE ie = IEUtil.getActiveIE();

		List<String> lst = ie.selectList(id, lstId).getAllContents();

		log.debug(strToFind);

		if (lst.size() > 0) {

			for(int i = 0; i < lst.size(); i++) {

				tmpElement = lst.get(i);
				log.debug(tmpElement);
				if (tmpElement.contains(strToFind)) {
					log.debug(tmpElement);
					return true;					
				}
			}
		}
		return false;
	}

	// #---------------------------END OF GENERIC
	// Methods---------------------------
	// #

	/***************************************************************************
	 * **** Date Functions
	 **************************************************************************/

	public static String getTodayDate() throws Exception {

		Date today = Calendar.getInstance().getTime();

		// String Today =
		// DateFormat.getDateInstance(DateFormat.SHORT).format(today);
		String Today = sdfnew.format(today);

		return Today;
	}
	
	public static String getPastDate(int days) throws Exception {

		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, days);
	
		String yester = sdfnew.format(c.getTime());

		return yester;
	}

	public static void takeANap(Double inSeconds) throws Exception {

		Double db = inSeconds*1000;

		Date today = new Date();

		long now = today.getTime();

		long quitAt = now + db.longValue();

		while (now < quitAt) {

			today = new Date();

			now = today.getTime();		
		}
	}

	public static String getNextYear() throws Exception {

		Date today = null;

		Calendar rightnow = Calendar.getInstance();

		rightnow.add(Calendar.YEAR, 2);

		today = rightnow.getTime();

		String Today = sdfnew.format(today);

		return Today;
	}

	public static String setDayofMonth(int signedVal) throws Exception {

		Calendar rightnow = Calendar.getInstance();

		rightnow.add(Calendar.DAY_OF_MONTH, signedVal);

		Date today = null;

		today = rightnow.getTime();

		String date = sdfnew.format(today);

		return date;
	}

	/* not used by anyone
	// #****************************************************************************
	// # PROGRAM'S Methods
	// #****************************************************************************

	// ###******** To Select View Filter *************
	public static boolean SelectProgramsView(String progView) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!isSelectListExists("/programCategories/"))
		{
			log.error("could not find the select list!");
			return false;
		}
		ie.selectList(name, "/programCategories/").select(progView);
		return true;
	}
	 */
	// #****************************************************************************
	// # PROJECT'S Methods
	// #****************************************************************************

	public static boolean setTextByTitle(String txtTitle, String str) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div div = null;
		//
		if(ie.div(attribute("class", IGeneralConst.class_g3mainContainer)).exists())
		{
			div = ie.div(attribute("class", IGeneralConst.class_g3mainContainer));
		}
		else
		{
			div = ie.div();
		}	

		for (Span sp : div.spans()) {

			for (Span span1 :sp.spans()){

				log.info(span1.innerText());
				
				if (!span1.div(0).exists()) {
					break;
				}

				if(span1.div(0).innerText().startsWith(txtTitle) || span1.div(0).innerText().startsWith("* ".concat(txtTitle)))
				{
					if(span1.span(1).textField(0).exists())
					{
						span1.span(1).textField(0).set(str);
						return true;
					}				
				}
			}
		}
		log.error("error finding Text Field with Title: ".concat(txtTitle));
		return false;
	}

	public static boolean SetAnyText() throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBoxId))
		{
			log.error("could not write, the text field is Read Only!");
			return false;
		}
		ie.textField(name, IGeneralConst.textBoxId).set("Test");
		return true;
	}

	public static boolean SetAnyNumber() throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBoxId))
		{
			log.error("could not write, the text field is Read Only!");
			return false;
		}		
		ie.textField(name, IGeneralConst.textBoxId).set("100");
		return true;
	}

	public static boolean SetNumberValue(String NumValue) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBoxId))
		{
			log.error("could not write, the text field is Read Only!");
			return false;
		}		
		ie.textField(name, IGeneralConst.textBoxId).set(NumValue);
		return true;
	}

	public static boolean appendToTextFieldByTtl(String txtTtl, String appendedStr) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyByTtl(txtTtl))
		{
			log.error("could not write, The Text Field is Read Only!");
			return false;
		}
		Spans spans = ie.spans();

		for (Span span : spans) {

			if(span.innerText().trim().contains(txtTtl) || span.innerText().trim().startsWith("* ".concat(txtTtl)))
			{
				if(span.textField(0).exists() && !span.textField(0).readOnly())
				{
					span.textField(0).set(appendedStr);					

					if(ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn))
					{
						return true;
					}
					else if(ClicksUtil.clickButtons(IClicksConst.saveBtn))
					{
						return true;
					}
					else
					{
						log.error("Could not Click Next nor Save button");

						return false;
					}
				}				
			}			
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return false;
	}
	
	public static boolean appendToTextFieldByLbl(String txtLbl, String appendedStr) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyByTtl(txtLbl))
		{
			log.error("Could not write, The Text Field is Read Only!");
			return false;
		}

		TableRows row = ie.rows();

		for (TableRow rows : row) {

			if(rows.innerText().startsWith(txtLbl) || rows.innerText().startsWith("* ".concat(txtLbl)))
			{
				if(rows.textField(0).exists() && !rows.textField(0).readOnly())
				{
					rows.textField(0).append(appendedStr);	
				}				
			}			
		}

		log.error("Could not find Text Field For Title: ".concat(txtLbl));
		return false;
	}


	public static boolean appendToTextFieldByTtlAndReturn(String txtTtl, String appendedStr) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyByTtl(txtTtl))
		{
			log.error("could not write, The Text Field is Read Only!");
			return false;
		}

		Divs divs = ie.divs();

		for (Div div2 : divs) {

			if(div2.innerText().startsWith(txtTtl) || div2.innerText().startsWith("* ".concat(txtTtl)))
			{
				if(div2.textField(0).exists() && !div2.textField(0).readOnly())
				{
					div2.textField(0).append(appendedStr);					

					if(ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn))
					{
						return true;
					}
					else
					{
						log.error("Could not Click Save & Back button");

						return false;
					}
				}				
			}				 

 	 
		}

		log.error("Could not find Text Filed For Title: ".concat(txtTtl));
		return false;
	}


	public static boolean AppendToText(String textId, String changedVal) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(textId))
		{
			log.error("Could not Append to text!");
			return false;
		}
		ie.textField(id, textId).append(changedVal);
		return true;
	}

	public static boolean AppendToText(String changedVal) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBoxId))
		{
			log.error("Could not Append to text!");
			return false;
		}
		ie.textField(name, IGeneralConst.textBoxId).append(changedVal);
		return true;
	}

	public static boolean AddToeFormList(String num) throws Exception 
	{
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBox0Id))
		{
			log.error("Could not write text!");
			return false;
		}			
		ie.textField(name, IGeneralConst.textBox0Id).set("Short Text_" + num);
		return true;
	}

	public static boolean ChangeNumber() throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById(IGeneralConst.textBoxId))
		{
			log.error("Could not write text!");
			return false;
		}			
		ie.textField(name, IGeneralConst.textBoxId).set("500");
		return true;
	}

	/*
	public static void toggleApprovalProcessNodeMenu(boolean toggle) throws Exception {
		IE ie = IEUtil.getActiveIE();
		tables = ie.span(id, "/left_bar:menu:menuForm:menu:0:0:0:0/").tables();

		for (int i = 0; i < tables.length(); i++) {
			table = tables.table(i);
			if (table.innerText().startsWith("Approval Process")) {
				if (table.image(id, "left_bar:menu:menuForm:menu:0:0:0:t2")
						.src().endsWith("nav-minus-line-middle.gif")) {
					table.image(id, "left_bar:menu:menuForm:menu:0:0:0:t2")
							.click();
				}
				break;
			}

		}

	}

	public static boolean toggleApprovalProcessNodeMenu(boolean toggle) throws Exception {
		IE ie = IEUtil.getActiveIE();

		tables = ie.span(id, IGeneralConst.menuForm_approval).tables();

		for (int i = 0; i < tables.length(); i++) {
			table = tables.table(i);
			if (!table.innerText().startsWith("Approval Process")) 
				return false;
			else
				if (!table.image(id, IGeneralConst.menuFormImage_approval).src().endsWith(IOrgConst.orgPlanner_MinusGif_Middle_Id))
					return false;
				else
					if (! isImageExistsByID(IGeneralConst.menuFormImage_approval))
						return false;
					else
						table.image(id, IGeneralConst.menuFormImage_approval).click();
					break;
				}
				return true;			
			}
	 */	
	public static boolean toggleCheckBoxById(String chkBoxId, boolean toggle) throws Exception {		

		IE ie = IEUtil.getActiveIE();

		if(!ie.checkbox(id, chkBoxId).exists())
		{
			log.error("Could not find Check Box");
			return false;
		}

		if(toggle)
		{
			ie.checkbox(id, chkBoxId).set();
		}
		else
		{
			ie.checkbox(id, chkBoxId).clear();
		}
		return true;
	}


	public static boolean toggleCheckBoxByTitle(String chkBoxTtl, boolean toggle) throws Exception {		

		IE ie = IEUtil.getActiveIE();

		Div div = null;

		if(ie.div(attribute("class", IGeneralConst.class_g3mainContainer)).exists())
		{
			div = ie.div(attribute("class", IGeneralConst.class_g3mainContainer));
		}
		else
		{
			div = ie.div();
		}	

		for (Span sp : div.spans()) {

			for (Span span1 :sp.spans()){

				if (!span1.div(0).exists()) {
					break;
				}

			if(span1.div(0).innerText().startsWith(chkBoxTtl) || span1.div(0).innerText().startsWith("* ".concat(chkBoxTtl)))
			{
				if(span1.checkbox(0).exists())
				{					
					if(toggle)
					{
						span1.checkbox(0).set();
					}
					else
					{
						span1.checkbox(0).clear();
					}

					return true;
				}				
			}
			}			
		}
		log.error("Could not find Check Box");
		return false;		
	}


	// #*******************************************************************************************
	// # IMPORT AND CONFIGURATION'S Methods
	// #*******************************************************************************************

	public static boolean updateUAPs() throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!ie.checkbox(0).exists())
		{
			log.error("Could not find Check Box");
			return false;
		}

		ie.checkbox(0).set(true);
		return true;
	}

	/*not used
	 public boolean appendRelations() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.checkbox(1).exists())
		{
			log.error("Could not find Check Box");
			return false;
		}

		ie.checkbox(1).set(true);
		return true;
	}
	 */
	public static boolean setUserNameMaxLength(String maxLength) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		ie.link(text, IClicksConst.openSystemSettingListLnk).click();

		int rowIndex =-1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.usernameMaxLength_Lbl);

		if(rowIndex != -1)
		{
			tDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(maxLength);
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return true;
	}

	public static boolean setNotificationMtrixProp(String propKey, String propValue) throws Exception{

		boolean append = true;

		Properties propsOut = new Properties();

		FileOutputStream out = new FileOutputStream(IGeneralConst.notifMatrixPropFile, append);

		propsOut.setProperty(propKey, propValue);

		propsOut.store(out, null);

		out.close();

		return true;
	}	

	public static String getNotifMatrixPropValue(String propKey) throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.notifMatrixPropFile)));

		if(!p.containsKey(propKey))
		{
			log.error("Could not find the Key in the prop file: ".concat(propKey));

			return null;
		}

		return p.getProperty(propKey);

	}

	public static void configureSystemSetting() throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));

		String userName = p.getProperty("ntUserName");

		String autoEmail = "automated-mailer-" + userName + "@grantium.com";

		String autoNotificationEmail = "automated-note-mailer-" + userName	+ "@grantium.com";

		String etlFilePath = p.getProperty("etlPath");

		String docUploadPath = p.getProperty("uploadDocPath");

		String baseSite = p.getProperty("siteBase");

		String auditReportingDirPath = docUploadPath.concat(IGeneralConst.auditReportingDirPath);

		String rptSrvIntegrationUrl = "-";

		if(p.containsKey(IGeneralConst.system_reportServerIntegration))
		{		

			if(!(p.getProperty(IGeneralConst.system_reportServerIntegration).isEmpty()))
			{
				rptSrvIntegrationUrl = p.getProperty(IGeneralConst.system_reportServerIntegration);
			}

		}

		String rptSrvUrl = "-";

		if(p.containsKey(IGeneralConst.system_reportServer))
		{

			if(!(p.getProperty(IGeneralConst.system_reportServer).isEmpty()))
			{
				rptSrvUrl = p.getProperty(IGeneralConst.system_reportServer);
			}
		}

		ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);

		int rowIndex =-1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.administratorEmail_Lbl);

		Div tableDiv = TablesUtil.tableDiv();
		
		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(IPreTestConst.adminEmailAddress);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;	

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.associate_applicant_access_url_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(baseSite.concat("associateAccess.jsf"));
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex = -1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.auditReortingExportDirectory_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(auditReportingDirPath);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.automatedEmail_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(autoEmail);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;		

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.automatedNotificationEmail_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(autoNotificationEmail);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;		

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.emailErrorMsgVerbosity_Lbl);

		if(rowIndex != -1)
		{			
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set("2");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex = -1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.reportServerIntegrationUrl_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(rptSrvIntegrationUrl);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex = -1;

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.reportServerUrl_Lbl);

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(rptSrvUrl);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;		

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.etlFilePath_Lbl);		

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(etlFilePath);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		rowIndex =-1;		

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.uploadDocumentationPath_Lbl);		

		if(rowIndex != -1)
		{
			tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(docUploadPath);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
		}
		
		//set up BI for Doc Build (Oracle) only		
		if(baseSite.equalsIgnoreCase("http://g3-qa-autobuild/grantiumDoc/"))
		{			
			rowIndex =-1;		

			rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.reportServerUrl_Lbl);		

			if(rowIndex != -1)
			{
				tableDiv.body(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(IConfigConst.bi_URL);
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn); //comment out when JSF2 fixed pagination issue
			}
					
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public static boolean setLicenseManager() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openLicenseManagementLnk);

		Div tableDiv = TablesUtil.tableDiv();

		if ( !TablesUtil.isTableExists(ITablesConst.license_Management_TableId) )
		{
			log.error("Could not find tablebody (" + ITablesConst.license_Management_TableId + ")!");
			return false;
		}

		//IConfigConst.licenseKey_Names.length

		for (int i = 0; i < IConfigConst.licenseKey_Names.length ; i++) {

			TableBody tBody = tableDiv.body(id,ITablesConst.license_Management_TableId);

			for (TableRow row : tBody.rows()) {

				if(row.innerText().startsWith(IConfigConst.licenseKey_Names[i]))
				{
					row.textField(0).set(IConfigConst.licenseKey_Values[i]);
					break;
				}			
			}
		}

		if (!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.saveBtn));
			return false;
		}
		return true;
	}


	public static boolean configureLicenseManagement(String licenseType, String licenseKey, boolean doSave) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		if(!tDiv.body(id,ITablesConst.license_Management_TableId).exists())
		{
			ClicksUtil.clickLinks(IClicksConst.openLicenseManagementLnk);
		}

		if(!tDiv.body(id,ITablesConst.license_Management_TableId).exists())
		{
			log.error("Could not find License Management page or Table Id changed");
			return false;
		}

		TableBody tBody = tDiv.body(id, ITablesConst.license_Management_TableId);

		for (TableRow row : tBody.rows()) {

			if(row.innerText().startsWith(licenseType))
			{
				row.textField(0).set(licenseKey);

				if(doSave)
				{					
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
				}
				return true;
			}			
		}		
		return false;
	}


	public static boolean goToNextPage() throws Exception {

		IE ie = IEUtil.getActiveIE();	

		Div tDiv = TablesUtil.tableDiv();

		links = tDiv.body(id, IGeneralConst.decadePaginator).links();			

		int pageNum = Integer.parseInt(ie.span(attribute("class", IGeneralConst.class_listFooterSelected)).innerText());

		for (Link link1 : links) {

			if(Integer.parseInt(link1.innerText()) > pageNum)
			{
				ie.link(text, link1.innerText()).click();
				return true;
			}
		}		
		return false;
	}

	public static boolean setFOConfigOption(boolean isProfileCreationEnable,
			boolean isPublicationEnable) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openFrontOfficeConfiguration))
		{
			log.error("could not click on link ".concat(IClicksConst.openFrontOfficeConfiguration));
			return false;
		}

		if(!ClicksUtil.clickButtons(IClicksConst.optionTabBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.optionTabBtn));
			return false;
		}

		ie.checkbox(id, IConfigConst.enableFOUserProfileCreationId).set(isProfileCreationEnable);

		ie.checkbox(id, IConfigConst.enablePublicationPortalId).set(isPublicationEnable);

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.saveBtn));
			return false;
		}
		return true;
	}

	public static boolean setPAPRequired(String isRequired) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
		{
			log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
			return false;
		}

		ie.selectList(id, "/papRequired/").select(isRequired);

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.saveBtn));
			return false;
		}		
		return true;
	}

	public static boolean isApplicantProfileSelected(String profileTitle) throws Exception {
		
		return GeneralUtil.getSelectedItemValueInDropdwonById(IConfigConst.appSettings_ApplicantProfile_DropDownID).equals(profileTitle);
	}

	public static boolean isUserProfileSelected(String profileTitle) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		return GeneralUtil.getSelectedItemValueInDropdwonById(IConfigConst.appSettings_UserProfile_DropDownID).equals(profileTitle);
	}

	public static boolean setApplicantWorkspace(String applicantProfileIdent) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
		{
			log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
			return false;
		}

		ie.selectList(id,IConfigConst.appSettings_AvailableEvaluators_DropDownID).select(IGeneralConst.selectNo);

		ie.selectList(id,IConfigConst.appSettings_ProjectOfficerAssignment_DropDownID).select(IGeneralConst.selectYes);

		if(!isApplicantProfileSelected(applicantProfileIdent)) {		

			Thread dialogClicker = new Thread() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();
						ConfirmDialog dialog1 = ie.confirmDialog();
						while (dialog1 == null) {
							log.debug("can't yet get handle on confirm dialog1");
							this.sleep(250);
						}

						dialog1.ok();
						log.debug("got confirm Dialog1 and clicked OK.");

						ConfirmDialog dialog2 = ie.confirmDialog();
						while (dialog2 == null) {
							Thread.currentThread().sleep(250);
							log.debug("can't get handle on confirm dialog2");
						}

						dialog2.ok();
						log.debug("got confirmDialog2 and clicked OK.");
					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();
			log.debug("started dialog clicker thread");

			ie.selectList(id, IConfigConst.appSettings_ApplicantProfile_DropDownID).select(applicantProfileIdent);

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;		


			if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_DefaultLocale_DropDownID, IGeneralConst.select_English_CA))
			{
				if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_DefaultLocale_DropDownID, IGeneralConst.select_English_US))
				{
					log.warn("The default Locale is English UK!");
				}
			}


			log.debug("clicking save new applicant workspace form");

			ClicksUtil.clickButtons(IClicksConst.saveBtn);// pops javascript dialog. Blocks
			// current thread until dialog is
			// cleared. Thus, another thread
			// must be
			// prepared before this point that will wait for the dialog and click it
			// when it appears.

			log.debug("clicked save new applicant workspace form");

			log.debug("sleeping");
		}
		return true;
	}

	public static boolean setApplicantWorkspace() throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
		{
			log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
			return false;
		}

		ie.selectList(id,IConfigConst.appSettings_AvailableEvaluators_DropDownID).select(IGeneralConst.selectNo);

		ie.selectList(id,IConfigConst.appSettings_ProjectOfficerAssignment_DropDownID).select(IGeneralConst.selectYes);

		ie.textField(id,IConfigConst.appSettings_ApplicantNumberPrefix_TextFieldID).set("QA-");

		if(!isApplicantProfileSelected("Applicant Profile")) {

			Thread dialogClicker = new Thread() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					try {
						IE ie = IEUtil.getActiveIE();
						ConfirmDialog dialog1 = ie.confirmDialog();
						while (dialog1 == null) {
							log.debug("can't yet get handle on confirm dialog1");
							this.sleep(250);
						}

						dialog1.ok();
						log.debug("got confirm Dialog1 and clicked OK.");

						ConfirmDialog dialog2 = ie.confirmDialog();
						while (dialog2 == null) {
							Thread.currentThread().sleep(250);
							log.debug("can't get handle on confirm dialog2");
						}

						dialog2.ok();
						log.debug("got confirmDialog2 and clicked OK.");
					} catch (Exception e) {
						throw new RuntimeException("Unexpected exception", e);
					}
				}
			};

			dialogClicker.start();
			log.debug("started dialog clicker thread");

			ie.selectList(id,IConfigConst.appSettings_ApplicantProfile_DropDownID).select("Applicant Profile");

			GeneralUtil.takeANap(1.000);

			dialogClicker = null;
		}		

		if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_DefaultLocale_DropDownID, IGeneralConst.select_English_CA))
		{
			if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_DefaultLocale_DropDownID, IGeneralConst.select_English_US))
			{
				log.warn("The default Locale is English UK!");
			}
		}

		log.debug("clicking save new applicant workspace form");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		log.debug("clicked save new applicant workspace form");

		log.debug("sleeping");

		return true;
	}

	public static boolean setAutoGenAppNumber(String strChoice) throws Exception {

		try {
			IE ie = IEUtil.getActiveIE();

			if (!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
			{
				log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
				return false;
			}		

			ie.selectList(id,IConfigConst.appSettings_AutoGenApplicantNumber_DropDownID).select(strChoice);

			GeneralUtil.takeANap(1.0);

			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
			{
				log.error("could not click on button ".concat(IClicksConst.saveBtn));
				return false;
			}		
			return true;

		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception", e);
		}
	}

	public static boolean setGrantStepStaffAccess(String strChoice) throws Exception {

		try {
			IE ie = IEUtil.getActiveIE();

			if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
			{
				log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
				return false;
			}		

			if(!ie.selectList(id,IGeneralConst.appSettings_GSSAAS_DropdownId).exists()){

				log.error("Could not find the Dropdown Grant Step Staff Access!");
				return false;
			}

			ie.selectList(id,IGeneralConst.appSettings_GSSAAS_DropdownId).select(strChoice);

			GeneralUtil.takeANap(0.5);

			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
			{
				log.error("could not click on button ".concat(IClicksConst.saveBtn));
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception", e);
		}
	}

	public static boolean setUserProfile(String profEForm,boolean doesBringForward) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
		{
			log.error("could not click on link ".concat(IClicksConst.openApplicationSettings));
			return false;
		}

		if(ie.selectList(id,IConfigConst.appSettings_UserProfile_DropDownID).exists())
		{

			if(doesBringForward)
			{
				ie.selectList(id,IConfigConst.appSettings_BFUserProfToAppProf_DropDownID).select(IGeneralConst.selectYes);
			}
			else
			{
				ie.selectList(id,IConfigConst.appSettings_BFUserProfToAppProf_DropDownID).select(IGeneralConst.selectNo);
			}

			if(!isUserProfileSelected(profEForm)) {

				Thread dialogClicker = new Thread() {
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							IE ie = IEUtil.getActiveIE();
							ConfirmDialog dialog1 = ie.confirmDialog();
							while (dialog1 == null) {
								log.debug("can't yet get handle on confirm dialog1");
								this.sleep(250);
							}

							dialog1.ok();
							log.debug("got confirm Dialog1 and clicked OK.");

							ConfirmDialog dialog2 = ie.confirmDialog();
							while (dialog2 == null) {
								Thread.currentThread().sleep(250);
								log.debug("can't get handle on confirm dialog2");
							}

							dialog2.ok();
							log.debug("got confirmDialog2 and clicked OK.");
						} catch (Exception e) {
							throw new RuntimeException("Unexpected exception", e);
						}
					}
				};

				dialogClicker.start();
				log.debug("started dialog clicker thread");

				ie.selectList(id,IConfigConst.appSettings_UserProfile_DropDownID).select(profEForm);

				GeneralUtil.takeANap(1.000);

				dialogClicker = null;

				log.debug("clicking save new User Profile form");

				ClicksUtil.clickButtons(IClicksConst.saveBtn);

				log.debug("clicked save new User Profile form");

				log.debug("sleeping");
			}

		}
		return true;
	}

	public static boolean newImportZipFile(String fileName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!isLinkExistsByTxt(IGeneralConst.polink_DataLoader))
		{
			log.error("Could not find File Field!");
			return false;
		}
		ie.link(text, IGeneralConst.polink_DataLoader).click();

		if(!isFileFieldExistsByIndex(0))
		{
			log.error("Could not find File Field!");
			return false;
		}
		ie.fileField(0).set("\"" + getWorkspacePath() + IGeneralConst.xmlFilesPath + fileName + "\"");

		if(!isButtonExistsByValue(IClicksConst.uploadBtn))
		{
			log.error("Could not find Upload button!");
			return false;
		}
		ie.span(text, IClicksConst.uploadBtn).click();
//		ie.button(value, IClicksConst.uploadBtn).click();
		return true;
	}

	//	public static void captureTheScreen(String fileName) throws Exception {
	//		IE ie = IEUtil.getActiveIE();
	//		ie.focus();
	//		ie.fullScreen(true);		
	//		ie.screenCapture(GeneralUtil.getWorkspacePath() + IGeneralConst.pngFilesPath + fileName + ".png");
	//		ie.fullScreen(false);
	//	}

	public static boolean saveThePDF(String fileName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.exportToPDFLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.exportToPDFLnk));
			return false;
		}

		if(!ClicksUtil.clickButtons(IClicksConst.exportToPDFBtn))
		{
			log.error("Could not click on button " .concat(IClicksConst.exportToPDFBtn));
			return false;
		}

		FileDownloadDialog dialog1 = ie.fileDownloadDialog();		
		dialog1.save(GeneralUtil.getWorkspacePath() + IGeneralConst.pdfFilesPath + fileName + ".pdf");

		//dialog1.closeThisDialogBoxWhenDownloadCompletes(true);

		return true;
	}

	public static void saveDocuments(final String docFullName) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		Thread dialogClicker = new Thread() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					FileDownloadDialog dialog1 = ie.fileDownloadDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.save(GeneralUtil.getWorkspacePath() + IGeneralConst.docsFilesPath + docFullName);
					log.debug("got confirm Dialog1 and clicked OK.");


				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();

		tDiv.body(id, ITablesConst.documentsTableId).row(0).image(1).click();

		//ie.table(id,ITablesConst.formsTableId).body(id,ITablesConst.poTableBodyId).row(0).image(2).click();

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

	}

	public static boolean addNewRefTable(String refTableEForm, String orgName) throws Exception {

		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}

		if(!ClicksUtil.clickImage(IClicksConst.newImg))
		{
			log.error("Could not open new Refernce Tables details");
			return false;
		}

		if(!setTextById(IGeneralConst.refTableIdent_TxtFieldId, refTableEForm.replace("-", " ")))
		{
			log.error("Could not enter Ref Table Identifier to Text Field");
			return false;
		}

		if(!selectFullStringInDropdownList(IGeneralConst.refTableEForm_DropdownId, refTableEForm))
		{
			log.error("Could not select the Ref Table e.Form");
			return false;
		}

		if(!selectFullStringInDropdownList(IGeneralConst.refTableOrg_DropdownId, orgName))
		{
			log.error("Could not find Org Hierarchy: ".concat(orgName));
			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		return true;
	}


	public static boolean importDataToRefTable(String refTableName, String dataFile, boolean deleteAllContent) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}

		if(!TablesUtil.openInTableByImageAlt(ITablesConst.refTablesListTableId, refTableName, IClicksConst.refTable_ImportDataAlt))
		{
			log.error("Could not open Import form!");
			return false;
		}

		if(!ie.fileField(0).exists())
		{
			log.error("could not find File Field on page!");
			return false;
		}

		ie.fileField(0).set("\"".concat(getWorkspacePath()).concat(IGeneralConst.xmlFilesPath).concat(dataFile).concat("\""));

		if(deleteAllContent)
		{
			if(!toggleCheckBoxById(IGeneralConst.refTableDeleteAllContent_ChkboxId, deleteAllContent))	
			{
				log.error("Could not Toggle the Checkbox!");
				return false;
			}			
		}		

		if(!ClicksUtil.clickButtons(IClicksConst.uploadBtn))
		{
			log.error("Could not click on button " .concat(IClicksConst.uploadBtn));
			return false;
		}		
		return true;
	}


	public static boolean newImportForms(String eforms[]) throws Exception {
		IE ie = IEUtil.getActiveIE();

		//		if(!isLinkExists("Forms")){
		//			log.error("Link 'Forms'doent exist");
		//			return false;
		//			}
		//		
		//		ie.link(text, "Forms").click();
		//		log.debug("clicked 'Forms' link");

		if(!ClicksUtil.clickLinks(IClicksConst.openFormsListLnk))
		{
			log.error("Could not click link ".concat(IClicksConst.openFormsListLnk));
			return false;
		}

		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not click image ".concat(IClicksConst.imprtImg));
			return false;
		}
		//ie.image(src, "/import.gif/").click();
		log.debug("clicked import image");

		for (int i = 0; i < eforms.length; i++) {
			ie.fileField(0).set("\"" + getWorkspacePath() + IGeneralConst.xmlFilesPath + eforms[i] + "\"");

			ClicksUtil.clickButtons(IClicksConst.uploadBtn);
		}
		return true;
	}


	public static boolean newImportUsersAndGroups(String groups) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openGroupListLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openGroupListLnk));
			return false;
		} 
		else
			log.debug("clicked Groups link");

		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not click on image " .concat(IClicksConst.imprtImg));
			return false;
		}
		else
			log.debug("clicked import image");

		ie.fileField(0).set(
				"\"" + getWorkspacePath() + IGeneralConst.xmlFilesPath + groups	+ "\"");

		if(!ClicksUtil.clickButtons(IClicksConst.uploadBtn))
		{
			log.error("Could not click on button " .concat(IClicksConst.uploadBtn));
			return false;
		}
		return true;
	}


	public static boolean newImportFOPP(String fopp) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openFundingOppsListLnk));
			return false;
		} 
		else
			log.info("clicked FOPP link");

		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not find Import Image!");
			return false;
		}
		else
			log.info("clicked import image");

		if(!ie.fileField(0).exists())
		{
			log.error("Could not find File Field!");
			return false;
		}

		ie.fileField(0).set("\"".concat(getWorkspacePath()).concat(IGeneralConst.xmlFilesPath).concat(fopp).concat("\""));

		ClicksUtil.clickButtons(IClicksConst.uploadBtn);
		return true;
	}

	public static boolean newImportOrganizations(String org) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openOrganizationsList))
		{
			log.error("Could not click on link " .concat(IClicksConst.openOrganizationsList));
			return false;
		} 
		else
			log.debug("clicked Organizations link");		

		if(ClicksUtil.clickImage(IClicksConst.imprtImg)) {

			log.debug("clicked import image");

			ie.fileField(0).set("\"" + getWorkspacePath() + IGeneralConst.xmlFilesPath + org + "\"");

			ClicksUtil.clickButtons(IClicksConst.uploadBtn);

			return true;			
		}
		return false;
	}

	public static boolean newImportLookups(String lookups) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could not click on link " .concat(IClicksConst.openLookupsListsLnk));
			return false;
		} 
		log.debug("clicked lookups link");

		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not click on image " .concat(IClicksConst.imprtImg));
			return false;
		} 
		log.debug("clicked import image");

		if(!isFileFieldExistsByIndex(0))
		{
			log.error("Could not find file field");
			return false;
		} 

		ie.fileField(0).set(
				"\"" + getWorkspacePath() + IGeneralConst.xmlFilesPath + lookups + "\"");

		if(!ClicksUtil.clickButtons(IClicksConst.uploadBtn))
		{
			log.error("Could not click on button " .concat(IClicksConst.uploadBtn));
			return false;
		}
		return true;
	}

	// #----------------------END OF IMPORT AND CONFIGURATION'S
	// Methods----------------------------

	// #####**************************************************************************************
	// #### Spell Check Methods
	// #####**************************************************************************************
	/* not used
	public static boolean SetChangeTo(String word) throws Exception {
		IE ie = IEUtil.getActiveIE();

		if(isTextFieldReadOnlyById("word"))
		{
			log.error("Could not write to text!");
			return false;
		}

		ie.textField(name, "word").set(word);
		return true;
	}
	 */
	/**
	 * Initializes the configuration from the supplied configuration file Added
	 * to GeneralUtil library by Alex Pankov
	 * 
	 * @param filename
	 *            --Full name for configuration file
	 * 
	 * @exception FileNotFoundException
	 * @exception Exception
	 */
	public static Properties initProperties(String filename)
			throws FileNotFoundException, Exception {
		Properties config = new Properties();

		// if Properties file available, parse it for parameters
		File file = new File(filename);
		FileInputStream fin = null;

		try {
			if (!file.exists())
				throw new FileNotFoundException("No property file [" + filename
						+ "] found.");

			log.info("loading properties");
			fin = new FileInputStream(file);
			config.load(fin);

			log.info("Configuration loaded");

			if (log.isDebugEnabled()) {
				Enumeration<Object> en = config.keys();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					log.debug(key + "=" + config.getProperty(key));
				}
			}
		} catch (FileNotFoundException fnfe) {
			log.error("throwing FileNotFoundException");
			throw new FileNotFoundException(fnfe.getMessage());
		} catch (IOException ioe) {
			log.error("throwing IOException");
			throw new Exception(ioe.getMessage());
		} catch (Exception ex) {
			log.error("throwing Exception");
			throw new Exception(ex.getMessage());
		} finally {
			try {
				fin.close();
				log.info("property input closed");
			} catch (Exception ec) {
				log.error("Error closing file stream: " + ec.getMessage());
				ec.printStackTrace();
			}
			fin = null;
			file = null;
		}
		return config;
	} // end of init


	public static boolean runQuartzJob(String quartzJobName) throws Exception {
		// IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openQuartzControl))
		{
			log.error("could not click on link " .concat(IClicksConst.openQuartzControl));
			return false;
		}

		if(!TablesUtil.openInTableByImageAlt(ITablesConst.quartzControlTable, quartzJobName, IClicksConst.quartzJobRun_Alt))
		{
			log.error("could not click on image " .concat(IClicksConst.quartzJobRun_Alt));
			return false;
		}

		if(!GeneralUtil.FindTextOnPage(IClicksConst.quartzJobSuccess))
		{
			log.error("The Quartz Job: ("+ quartzJobName +") did not run!");
			return false;
		}

		return true;
	}

	public static String getSQLQuery_DataArchive() throws Exception {

		return "UPDATE grantiumSQL_trunk.grantiumSQL_trunk.workflow_projects,"
				+ "SET date_created = DATEADD(YEAR, -7, date_created)"
				+" WHERE po_project_name = '%'"
				+ "AND status_id = (SELECT lookup_value_id FROM grantiumSQL_trunk.grantiumSQL_trunk.lookup_values WHERE lookup_id = ( select lookup_id FROM grantiumSQL_trunk.grantiumSQL_trunk.lookups WHERE lookup_name = 'Case Status Types') AND constant = 'G3_CASE_STATUS_CLOSED')"
				+ "AND program_id = (SELECT program_id FROM grantiumSQL_trunk.grantiumSQL_trunk.programs WHERE program_name = 'A-Gnrl-PA-FOPP')";

	}

	public static String getORAQuery_DataArchive() throws Exception {

		return "UPDATE workflow_projects,"
				+"SET date_created = date_created - numtoyminterval(7,'year')"
				+"WHERE po_project_name = '%'"
				+"AND status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = ( select lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND CONSTANT = 'G3_CASE_STATUS_CLOSED')"
				+" AND program_id = (SELECT program_id FROM programs WHERE program_name = 'A-Gnrl-PA-FOPP')";


	}


	public static RptFuncs connectToDB(boolean isSQLDB, IRptFuncConst.EReportingFunctions funcName, RptFuncs reportFunc) throws Exception{
		
		try {

			if (isSQLDB) {
				funcName = IRptFuncConst.EReportingFunctions.sqlProgram;
			} else {
				funcName = IRptFuncConst.EReportingFunctions.oraProgram;
			}

			reportFunc = new RptFuncs(isSQLDB);
			reportFunc.setFunctionName(funcName);

			log.warn("Connected to "+ funcName + " Database!");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);
			return null;

		}
		return reportFunc;
	}

	public static boolean runCloseAndAgeAll_Queries(boolean isSQLDB, ResultSet rsResult, RptFuncs reportFunc, int age, String foppName) throws Exception {
		try{

			if (isSQLDB) {				
				log.warn("Running Closing Projects Query");
				RptFuncUtil.updateQueryMethod(reportFunc, RptFuncUtil.da_SQL_CloseProjs_Query(reportFunc.getDbName(), foppName));
				log.warn("Running Aging Projects Query");
				RptFuncUtil.updateQueryMethod(reportFunc, RptFuncUtil.da_SQL_AgeProjs_Query(reportFunc.getDbName(), age, foppName));
			}
			else {
				log.warn("Running Closing Projects Query");
				RptFuncUtil.updateQueryMethod(reportFunc, RptFuncUtil.da_Ora_CloseProjs_Query(foppName));
				log.warn("Running Aging Projects Query");
				RptFuncUtil.updateQueryMethod(reportFunc, RptFuncUtil.da_Ora_AgeProjs_Query(age, foppName));
			}

		}
		catch (Exception e) {
			log.error("Unexpected Exception", e);
			return false;
		}
		return true;
	}


	public static boolean da_selectAndProcessProjs(int numProjs, String processName) throws Exception{

		IE ie = IEUtil.getActiveIE();

		Integer pageNum = 1;

		if (ie.link(text, pageNum.toString()).exists())
			ie.link(text, pageNum.toString()).click();

		for (int i=0; i < numProjs; i++){

			ClicksUtil.toggleCheckBox(i+1, true);
	//		ClicksUtil.toggleCheckBoxNew(ITablesConst.da_ProjList_DivId, i+1, true);

		}

		takeANap(5.0);
		
		if(!ClicksUtil.clickLinksById(processName)){

			log.error("Couldn't click " + processName + " image");
			takeANap(5.0);

			return false;

		}

		return true;
	}

	public static boolean da_runQuartzJob(String quartzJob) throws Exception{
		
		if(!TablesUtil.openInTableByImageAltAndIndex(ITablesConst.quartzControlTable_Id, 
				TablesUtil.getRowIndex(quartzJob), IClicksConst.run_quartz_job)){

			log.error("Couldn't run Quartz job!");

			return false;
		}

		return true;
	}

	/**
	 * Puts a timer on archiving process from Quartz
	 * @param foProj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean da_QuartzArchiveProcessDelay(int numProjs) throws Exception{
		int count = 1;

		while (TablesUtil.getTotalRows(ITablesConst.da_ProjTableList_Id) > numProjs) {

			if (count > 20){

				log.error("Quartz took longer than "+(10*count)+" seconds to complete OR no projects were being archived!");
				return false;
			}

			log.warn("Quartz still processing " + (TablesUtil.getTotalRows(ITablesConst.da_ProjTableList_Id)-numProjs) + " Projects");

			GeneralUtil.takeANap(10.0);

			ClicksUtil.clickButtons(IClicksConst.filterBtn);

			count++;
		}

		log.warn("Quartz took less than "+ (10*count) + " seconds to complete archive");
		return true;

	}

	/**
	 * Puts a timer on deletion process from Quartz 
	 * @param foProj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean da_QuartzDeleteProcessDelay(String foundProj) throws Exception{
		int count = 1;

		while (TablesUtil.getTotalRows(ITablesConst.da_ProjTableList_Id) == 1){

			if (count > 20){

				log.error("Quartz took longer than " + (count*10) +" seconds to complete OR no projects were being deleted!");
				return false;
			}

			log.warn("Quartz still processing " + foundProj);

			GeneralUtil.takeANap(10.0);

			ClicksUtil.clickButtons(IClicksConst.filterBtn);

			count++;
		}

		log.warn("Quartz took less than "+ (10*count) + " seconds to complete deletion");
		return true;

	}


	public static boolean clickProj_ManageArchive_Filter(String foppIdent)throws Exception{

		if (!ClicksUtil.clickLinks(IClicksConst.manageArchive_lbl)) {

			log.error("Failed to click Manage Archive link ");
			return false;
		}

		if (!FiltersUtil.filterListByLabel(IGeneralConst.da_foppIdentifier_lbl, foppIdent, "Starts With")){

			log.error("Couldn't filter projects for " + foppIdent);
			return false;
		}

		if (!ClicksUtil.clickImage(IClicksConst.imageIcon_src)){

			log.error("Couldn't click image");
			return false;

		}
		return true;

	}

	public static boolean navigate_ManageArchive_Filter(String foppIdent)throws Exception{

		if (!ClicksUtil.clickLinks(IClicksConst.manageArchive_lbl)) {

			log.error("Failed to click Manage Archive link ");
			return false;
		}

		if (!FiltersUtil.filterListByLabel(IGeneralConst.da_foppIdentifier_lbl, foppIdent, "Starts With")){

			log.error("Couldn't filter projects for " + foppIdent);
			return false;
		}

		return true;

	}

	/**
	 * Verifies that project is not listed in amendments list
	 * @param foProjName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isProjectListedinAmendmentsList(String foProjName) throws Exception {

		if (!ClicksUtil.clickLinks(IClicksConst.openAmendmentListLnk)){
			log.error("Fail: Failed to click Amendments link ");
			return false;
		}

		if (!FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_submissionVersion_lbl, IGeneralConst.allVersions,
				IGeneralConst.da_projectName_lbl, foProjName, "Starts With")){ 
			log.error("Fail: Couldn't filter by Project name and status!");
			return false;
		}

		if(!(TablesUtil.getTotalRows(ITablesConst.da_Amendments_SubmissionsList_Table_Id) == 0)){ 
			log.error("Fail: Project is still listed in Amendments list!");
			return false;
		}
		return true;	
	}

	public static boolean isProjectListedinBulkEvalList(String foppName) throws Exception {

		if (!ClicksUtil.clickLinks(IClicksConst.openBulkEvalLnk)){
			log.error("Fail: Failed to click Bulk Evaluations link ");
			return false;
		}

		if (!FiltersUtil.filterListByLabel(IGeneralConst.da_foppName_lbl, foppName, "Starts With")){ 
			log.error("Fail: Couldn't filter by FOPP name!");
			return false;
		}

		if(!(TablesUtil.getTotalRows(ITablesConst.bulkEvalStepsView) == 0)){ 
			log.error("Fail: Project is still listed in Bulk Evaluations list!");
			return false;
		}


		return true;	
	}

	public static String getFirstProjName(String tBodyId) throws Exception{

		if (TablesUtil.getTotalRows(tBodyId)==0){

			log.error("Couldn't find div "+ tBodyId);
			return null;
		}

		String foundProj = TablesUtil.verifyStringinTable(tBodyId, 1, 2);

		foundProj = foundProj.substring(0, foundProj.indexOf("\n")-1);

		log.warn("Project Name: "+ foundProj);

		return foundProj;
	}

	public static String getFirstProjName_EvalSubs(String tableId) throws Exception{

		if (TablesUtil.getTotalRows(tableId)==0){

			log.error("Couldn't find table "+ tableId);
			return null;
		}

		String foundProj = TablesUtil.verifyStringinTable(tableId, 0, 1);

		foundProj = foundProj.substring(0, foundProj.indexOf("\n")-1);

		log.warn("Project Name: "+ foundProj);

		return foundProj;
	}


	public static boolean da_filterProjs_ChangeState(FOProject foProj, String filterStatus, String imgId) throws Exception{

		if (!FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, filterStatus,
				IGeneralConst.da_projectName_lbl, foProj.getProjFOFullName(), IGeneralConst.da_foppIdentifier_dropDown_value)) {

			log.error("Filtering by "+ filterStatus + " did not work!")	;
			return false;
		}

		if (!GeneralUtil.da_selectAndProcessProjs(1, imgId)){

			log.error("Could not Process Project to new State!");
			return false;
		}
		return true;

	}

	public static boolean da_filterProjs_ChangeState(String foundProj,String filterStatus, String imgId) {

		try {
			if (!FiltersUtil.filterProjListByNameAndStatus(IGeneralConst.da_projectStatus_lbl, filterStatus,
					IGeneralConst.da_projectName_lbl, foundProj, IGeneralConst.da_foppIdentifier_dropDown_value)) {

				log.error("Filtering by "+ filterStatus + " did not work!")	;
				return false;
			}
		} catch (Exception e) {
			log.error("Unexpected Exception: " + e);;
		}

		try {
			if (!GeneralUtil.da_selectAndProcessProjs(1, imgId)){

				log.error("Could not Process Project to new State!");
				return false;
			}
		}  catch (Exception e) {
			log.error("Unexpected Exception: " + e);
		}

		return true;
	}

	public static boolean test_ReadOnlyProperty_ReadyForArchive_Projects(String foppIdent, FOProject foProj, String state) throws Exception{

		try {
			GeneralUtil.switchToPOOnly();

			GeneralUtil.LoginAny("ReportOfficer5");

			Assert.assertTrue(clickProj_ManageArchive_Filter(foppIdent), "Fail: Could not navigate to Manage Archive page!");

			Assert.assertTrue(foProj.test_ReadyForArchive_ReadOnlyProperty(foProj, state), "Fail: Icon should be disabled.");

		} catch (Exception e) {

			log.error("Unexpected Exception: " + e);
			return false;
		}

		return true;
	}


	public static boolean isStringExists(String string) throws Exception {

		IE ie = IEUtil.getActiveIE();

		return ie.containsText(string);
	}

	public static boolean createManyProjs_postAwardProcess(int numProjs, FundingOpportunity fopp, String projType)  throws Exception {

		try {

			for (int i=1; i <=numProjs; i++){ 
	
				FOProject foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5).concat(projType));

				log.warn("Post-Award: Starting create project number "+ i + " and submitting...");
				ProjectUtil.createOneProjectAndSubmit(foProj, fopp, projType);
				
				log.warn("Starting approve submission and submitting award...");
				ProjectUtil.approveSubmission_submitAward(foProj);

				log.warn("Starting initial Claim submit...");
				ProjectUtil.initialClaimSubmit(foProj);

				log.warn("Starting assign evaluator and submit review...");
				ProjectUtil.assignEvaluator_submitReview(foProj);
				
				foProj = null;

				if (i != numProjs){

					GeneralUtil.switchToFOOnly();
					
					GeneralUtil.loginAnyFO("front");
				}
			}


		} catch (Exception e) {

			log.error("Unexpected Exception: " + e);
			return false;
		}
		return true;
	}

	public static boolean createManyProjs_PAProcessAmend(int numProjs, FundingOpportunity fopp, String projType)  throws Exception {

		try {

			for (int i=1; i <=numProjs; i++){ 

				FOProject foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5).concat(projType));
				
				log.warn("Post-Award: Starting create project number "+ i + " and submitting...");
				ProjectUtil.createOneProjectAndSubmit(foProj, fopp, projType);

				log.warn("Starting approve submission and submitting award...");
				ProjectUtil.approveSubmission_submitAward(foProj);

				log.warn("Starting initial Claim submit...");
				ProjectUtil.initialClaimSubmit(foProj);

				log.warn("Starting assign evaluator and submit review...");
				ProjectUtil.assignEvaluator_submitReview(foProj);

				log.warn("Starting request amendment from PO...");
				ProjectUtil.requestAmendmentFromPO(foProj);

				foProj = null;
				
				if (i != numProjs){

					GeneralUtil.switchToFOOnly();
					
					GeneralUtil.loginAnyFO("front");
				}
			}


		} catch (Exception e) {

			log.error("Unexpected Exception: " + e);
			return false;
		}
		return true;
	}

	
	public static int[][] typeOfCheckBox(String tableId) throws Exception {
			
		Div tDiv = TablesUtil.tableDiv();

		int totRows = TablesUtil.getTotalRows(tableId);

		int totCols = TablesUtil.getTotalColumns(tableId); 
		
		int [][]uapObject = new int [totRows][totCols-1];
		
		
		for (int i=0; i<totRows; i++){ 
		
			for (int j=1; j<totCols; j++){

				if (tDiv.body(id,tableId).row(i).cell(j).image(src, IGeneralConst.uap_checkBox_disable_src).exists())
					uapObject[i][j-1] = 0;

				else if (tDiv.body(id,tableId).row(i).cell(j).image(src, IGeneralConst.uap_checkBox_off_src).exists())
					uapObject[i][j-1] = 1;

				else if (tDiv.body(id,tableId).row(i).cell(j).image(src, IGeneralConst.uap_checkBox_on_src).exists())
					uapObject[i][j-1] = 2;

				else 
					log.error("Check box images did not exist!");
			}
		}
		
		return uapObject;
	}

	public static boolean  isSQLDB() throws Exception {
		
		String siteBase = RptFuncUtil.getConnectionProperty("siteBase").toLowerCase();
		
		log.warn("URL used: " + siteBase);
		
		if (siteBase.contains("sql"))
			return true;
	
		else 
			return false;
	
	}


}
