package test_Suite.tests.users;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.users.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;

@GUITest
@Test(singleThreaded = true)
public class CreateFOUserInPONG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	int startIndex;
	int endIndex;
	int foUserBeat;
	FOUsers foUser;

	// ---------------------------- End of Params

	@BeforeClass(groups = { "UsersNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			initializeFrontUsers();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "UsersNG" })
	public void createFOUserInPONG() throws Exception {

		try {
			creatingFrontOfficeApplicantFromPO();

			log.debug("logging off");
			GeneralUtil.Logoff();
			log.debug("closing browser");
			IEUtil.closeBrowser();
			GeneralUtil.takeANap(0.5);

			IEUtil.openNewBrowser();
			log.debug("opened new browser");
			GeneralUtil.navigateToFO();
			log.debug("nav'd to FO");

			createFrontOfficeUsers();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} 
	}

	@AfterClass(groups = { "UsersNG" }, alwaysRun=true)
	public void tearDown() {
		
		foUser = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void initializeFrontUsers() throws Exception {

		foUser = new FOUsers();

		foUser.setFoApplicants(IPreTestConst.FrontUsers[0]);

		foUser.setFoRegistrants(IPreTestConst.FrontUsers[1]);

		foUser.setFoOrgName(IPreTestConst.FO_OrgShortName);

	}

	private void creatingFrontOfficeApplicantFromPO() throws Exception {

		foUser.setStartIndex(12);
		foUser.setEndIndex(14);

		Assert.assertTrue(foUser.createPOApplicants(),
				"FAIL: Could not create an Applicant User!");

		Assert.assertTrue(foUser.createPORegistrant(),
				"FAIL: Could not create Registrant!");
	}

	private void createFrontOfficeUsers() throws Exception {

		foUser.setStartIndex(15);

		Assert.assertTrue(foUser.createProfile(),
				"FAIL: Could not create Profile!");

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		FrontOfficeUtil.setApplicant(IPreTestConst.FO_OrgShortName
				+ Integer.toString(foUser.getStartIndex()),
				IPreTestConst.FO_OrgNumber
						+ Integer.toString(foUser.getStartIndex()), true);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		log.debug("saved applicant");

		ClicksUtil.clickButtons(IClicksConst.backToApplicantListBtn);
	}
}
