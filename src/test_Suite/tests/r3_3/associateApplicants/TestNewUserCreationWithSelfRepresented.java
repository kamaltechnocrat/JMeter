/**
 * 
 */
package test_Suite.tests.r3_3.associateApplicants;

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
import test_Suite.constants.users.IApplicantTypesConst;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.users.IApplicantTypesConst.EAppCategories;
import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantTypesUtils;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestNewUserCreationWithSelfRepresented {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	static String foUserBeat = "";
	
	String appType = "";
	
	String typeIdent = "";

	FOUsers foUser;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			foUserBeat = EFormsUtil.createRandomNumber(3);
			
			appType = "student".concat(foUserBeat);
			
			typeIdent = "G".concat(foUserBeat).concat("_APPLICANT_TYPE_STUDENT");

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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void addingNewApplicantTypeNG() throws Exception {
		try {
			
			Assert.assertTrue(ApplicantTypesUtils.openApplicantTypesList(), "ERROR: could not open Applicant Types List");
			
			Assert.assertTrue(ApplicantTypesUtils.addNewApplicantType(typeIdent, IApplicantTypesConst.EAppCategories.selfRepresented, appType), "ERROR: Not able to add new Applicant Type!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addingNewApplicantTypeNG", dataProvider = "profile-data")
	public void testNewApplicantTypeInFONG(String fName, String lName, String email, String confEmail,String locale,String uName,String pwd,String confPassword,String question, String answer) throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			
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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testNewApplicantTypeInFONG")
	public void verifyLeftMenu() throws Exception {
		try {
			
			Assert.assertFalse(GeneralUtil.isLinkExistsByTxt(IClicksConst.openFOProjectListLnk), "ERROR: For Self-Represented Shouldn't be Projects menu");
			
			Assert.assertFalse(GeneralUtil.isLinkExistsByTxt(IClicksConst.openRegistrationListLnk), "ERROR: For Self-Represented Shouldn't be FOPP Registration menu");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testNewApplicantTypeInFONG")
	public void verifyAbilityAddingRegistrant() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);
			
			ClicksUtil.clickImage(IClicksConst.registrantListImg_src);
			
			Assert.assertFalse(GeneralUtil.isImageExistsBySrc(IClicksConst.associateImg), "ERROR: for Self-Represented shouldn't be any Associate Icon!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testNewApplicantTypeInFONG")
	public void verifyInAbilityAddingApplicant() throws Exception {
		try {
			
			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk), "FAIL: could not open Applicans Menu");
			
			Assert.assertTrue(GeneralUtil.isImageExistsBySrc(IClicksConst.newImg), "ERROR: New Icon should be available");
			
			Assert.assertTrue(ClicksUtil.clickImageByAlt(IClicksConst.newApplicant), "FAIL: Could not click plus sign to add new applicant");
			
			Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(IApplicantsConst.applicantTypeFO_DropDownId, foUser.getApplicantType()), "FAIL: could not select Self Represented Applicant Type");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			
			//****Behavior changed to not allowing Selfrep to create second applicant, so no more link! ****
			
			//Assert.assertTrue(ClicksUtil.clickLinks(foUser.getFoFName().concat(" ").concat(foUser.getFoLName())),"FAIL: Could not find the User in List");
			
			Assert.assertFalse(ClicksUtil.clickLinks(foUser.getFoFName().concat(" ").concat(foUser.getFoLName())),"FAIL: Should not find the Applicant in List as Link");
			
			//Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IApplicantsConst.applicantTypeFO_DropDownId), foUser.getApplicantType(), "FAIL: The Applicant Type is not the Same");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	
	@DataProvider(name = "profile-data")
	public static Object[][] generateRegistrantProfileData() throws Exception {
		
		return new Object[][] {
				{"Front-".concat(foUserBeat), "LRegistrant-".concat(foUserBeat),"Registrant".concat(foUserBeat).concat("@g3-qa-autobuild.csdc-lan.csdcsystems.com"),"Registrant".concat(foUserBeat).concat("@g3-qa-autobuild.csdc-lan.csdcsystems.com"),"/English/", "front".concat(foUserBeat), "a11", "a11","a11","a11"}};
		
	}

}
