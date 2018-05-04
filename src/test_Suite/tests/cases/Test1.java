package test_Suite.tests.cases;

import static watij.finders.SymbolFactory.*;

import java.util.*;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.Assert;
import org.testng.annotations.*;
import com.thoughtworks.selenium.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.users.IUsersConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.cases.IOrgConst;
import test_Suite.constants.cases.ILookupsConst.ELookupsTypes;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.preTest.*;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst.EeFormsIdentifier;
import test_Suite.constants.workflow.*;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.users.FOUsers;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.*;
import test_Suite.lib.cases.Lookups;
import test_Suite.lib.eForms.*;
import test_Suite.tests.sanity.AdminSanityTestNG;
import test_Suite.utils.cases.*;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.*;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.*;
import watij.dialogs.*;
import watij.runtime.ie.IE;
import watij.elements.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Test1 {
	private static Log log = LogFactory.getLog(Test1.class);
	

	boolean isSQLDB = false;
	
	Lookups lookup;

	Lookups lookupValA;
	Lookups lookupValB;
	
	ArrayList<String> lookupParams;
	
	ILookupsConst.ELookupsTypes eLookupTypes;
	
	String dateOnList = "18-Feb-2009 12:00:00 AM";
	String dateOnField = "18/02/2009";
	Date today;
	Date listDate;
	SimpleDateFormat sdf;
	DateFormat df1;
	
	private Selenium selenium;
	
	/*IReportingFunctionsConst.EeFormsIdentifier eFormsIdentifier;
	IReportingFunctionsConst.EReportingFunctions funcName = IReportingFunctionsConst.EReportingFunctions.oraProject;
	String subCode = IReportingFunctionsConst.ReportFunc_LatestSubmittedProject;
	String eFormIdent = IReportingFunctionsConst.reportFunc_eFormsIdent[EeFormsIdentifier.submission.ordinal()];
	ReportingFunctions reportFunc;	*/
	
	@BeforeClass
	public void setUp() {
		try {
			//SeleniumServer server = new SeleniumServer();
			
			
			
			//server.start();
			
			selenium = new DefaultSelenium("localhost", 3333, "*firefox", "http://t3400-03:8080");
			selenium.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test(groups = { "tempTest" })
	public void test1() throws Exception {

		//IEUtil.openNewBrowser();
		//GeneralUtil.navigateToPO();
		//GeneralUtil.logInSuper();
		//GeneralUtil.attachGF();
		
		
		/*df1 = DateFormat.getDateInstance(DateFormat.DEFAULT);
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		today = Calendar.getInstance(Locale.CANADA).getTime();
		
		log.info("Calendar Date: " + df1.format(today));
		
		listDate = df1.parse(dateOnList);
		
		log.info("On List Date: " + df1.format(listDate));
		
		today = sdf.parse(dateOnField);
		
		log.info("On Field Date: " + df1.format(today));*/
		
		selenium.open("/grantium/programOffice.jsf");
		selenium.type("left_bar:login:frmLogin:userName", "admin");
		selenium.type("left_bar:login:frmLogin:password", "password");
		selenium.click("left_bar:login:frmLogin:loginButton");
		selenium.waitForPageToLoad("30000");
		selenium.click("left_bar:menu:menuForm:menu:0:0:2:lnkNode");
		selenium.waitForPageToLoad("30000");
		selenium.click("//img[@alt='Add e.Form']");
		selenium.waitForPageToLoad("30000");
		selenium.type("main:formDetails:_idJsp25:formIdentifier_rw", "5843");
		selenium.select("main:formDetails:_idJsp25:formgroup_rw", "label=Submission");
		selenium.click("//option[@value='Submission']");
		selenium.select("main:formDetails:_idJsp25:primaryOrganization", "label=G3");
		selenium.select("main:formDetails:_idJsp25:organizationAccess", "label=Public");
		selenium.type("main:formDetails:_idJsp25:locales:0:formTitleText", "5843");
		selenium.type("main:formDetails:_idJsp25:locales:1:formTitleText", "5843");
		selenium.type("main:formDetails:_idJsp25:locales:2:formTitleText", "5843");
		selenium.click("main:formDetails:_idJsp25:_idJsp81");
		selenium.waitForPageToLoad("30000");		
		Assert.assertTrue(selenium.isTextPresent("This e.Form cannot be Published, no Formlets have been added to this e.Form"));
		selenium.click("header_right:headerForm:logout_image");
		selenium.waitForPageToLoad("30000");
		

		
	}	
	
}
