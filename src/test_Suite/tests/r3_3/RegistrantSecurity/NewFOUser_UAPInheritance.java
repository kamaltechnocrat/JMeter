package test_Suite.tests.r3_3.RegistrantSecurity;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IApplicantTypesConst.EAppCategories;
import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

@GUITest
@Test(singleThreaded = true)
public class NewFOUser_UAPInheritance {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);


	static String foUserBeat = "";

	String appType = "";

	FOUsers foUser;

	String appName = "front-AppTest";
	
	int [][]uapObject;
	
	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {

		try {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			// -----------------------------------

			foUserBeat = EFormsUtil.createRandomNumber(3);

			appType = "testUAP".concat(foUserBeat);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {

		foUser = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "ProjectsNG" }, dataProvider = "profile-data")
		public void createNewProfile(String fName, String lName, String email, String confEmail,String locale,String uName,String pwd,String confPassword,String question, String answer) throws Exception {
			
			try {
	
				String[] registrant = new String[] {fName,lName,email,confEmail, locale, uName, pwd,confPassword, question, answer};
				
				foUser = new FOUsers(registrant,IPreTestConst.FrontUsers[0], Integer.parseInt(foUserBeat));
				
				foUser.setApplicantType(appType);
				
				foUser.setApplicantCategory(EAppCategories.selfRepresented.toString());
							
				ClicksUtil.clickLinks(IClicksConst.createProfileLnk);
				
				Assert.assertTrue(foUser.createNewProfile(), "ERROR: Could not create new profile in FO!");
	
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}	
		}
	
	
	@Test(groups = {"ProjectsNG"}, dependsOnMethods = "createNewProfile")
	public void createApplicant() throws Exception {

		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.applicants), "Fail: Couldn't click Applicants Link!");

		Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.newApplicant), "Fail: Couldn't find image Add Applicant!");

		Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList("g3-form:applicantType_input", 
				IFiltersConst.fo_Applicant_Individual_ddVal), "Fail: Couldn't select Applicant Type!");
		
		GeneralUtil.takeANap(1.0);

		Assert.assertTrue(GeneralUtil.AppendToText("g3-form:applicantNameEditApplicant", appName),
				"Fail: Couldn't enter applicant name!");

		Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn), "Fail: Couldn't find button!");

		int row = TablesUtil.getRowIndex(ITablesConst.fOApplicantsTableId, appName);

		int column = TablesUtil.findColumnIndex(ITablesConst.fOApplicantsTableId_Header, IFiltersConst.grantManagement_ApplicantName_Lbl);

		Assert.assertEquals(TablesUtil.verifyStringinTable(ITablesConst.fOApplicantsTableId, row, column), appName, 
				"Fail: Applicant was not listed in Applicants Table!");
	}
	

	@Test(groups = {"ProjectsNG"}, dependsOnMethods = "createApplicant", dataProvider = "profile-data")
	public void checkDefaultUAPs(String fName, String lName, String email, String confEmail,String locale,String uName,String pwd,String confPassword,String question, String answer) throws Exception {
		
		GeneralUtil.switchToPO();
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openUsersListLnk), "Fail: Couldn't find Users link!");
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IFiltersConst.administration_UserType_Lbl, IFiltersConst.users_FOUsers_TypeView,
				IFiltersConst.administration_UserName_Lbl, "front", "Exact"), "Fail: Couldn't filter!");
		
		Assert.assertTrue(ClicksUtil.clickLinks("LRegistrant-1, Front-1 (qa_Registrant1@g3-qa-autobuild.csdc-lan.csdcsystems.com)"), "Fail: Couldn't click on user's link!");
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.userAccessRightsLnk), "Fail: Couldn't click user Access Rights Link!");
		
		uapObject = GeneralUtil.typeOfCheckBox(ITablesConst.foAccessRightsCheckboxTableId);
			
	}
	
	
	@Test(groups = {"ProjectsNG"}, dependsOnMethods = "checkDefaultUAPs", dataProvider = "profile-data")
	public void checkNewUAPs(String fName, String lName, String email, String confEmail,String locale,String uName,String pwd,String confPassword,String question, String answer) throws Exception {
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openUsersListLnk), "Fail: Couldn't find Users link!");
		
		Assert.assertTrue(FiltersUtil.filterProjListByNameAndStatus(IFiltersConst.administration_UserType_Lbl, IFiltersConst.users_FOUsers_TypeView,
				IFiltersConst.administration_UserName_Lbl, uName, "Exact"));
		
		Assert.assertTrue(ClicksUtil.clickLinks(lName + ", " + fName + " (" + email + ")"), "Fail: Couldn't click on user's link!");
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.userAccessRightsLnk), "Fail: Couldn't click user Access Rights button!");
		
		Assert.assertTrue(Arrays.deepEquals(GeneralUtil.typeOfCheckBox(ITablesConst.foAccessRightsCheckboxTableId), uapObject), 
				"Fail: New user did NOT inherit default UAP's!");
		
	}
	
	@DataProvider(name = "profile-data")
	public static Object[][] generateRegistrantProfileData() throws Exception {

		return new Object[][] {
				{"Front-TestUAP19", "LRegistrant-TestUAP19",
					"Registrant-TestUAP19".concat("@g3-qa-autobuild.csdc-lan.csdcsystems.com"),
					"Registrant-TestUAP19".concat("@g3-qa-autobuild.csdc-lan.csdcsystems.com"),
					"/English/", "front-TestUAP19", "a11", "a11","a11","a11"}};

	}
}
