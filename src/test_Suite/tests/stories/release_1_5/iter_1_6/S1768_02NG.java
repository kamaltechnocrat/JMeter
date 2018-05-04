
package test_Suite.tests.stories.release_1_5.iter_1_6;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.lib.users.FOUsers;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/*
 * Test Case 01 for Story #1768 -  Allow Creation of Front Office User Records from the Applicant Detail page (Accessible via PO Applicant list)
 * Testing the Adding Existing Registrant to a New Applicant
 * Steps:
 * 1. In Fo Create Registrant profile
 * 2. In PO Create New Applicant
 * 3. Add the Existing Registrant to the Applicant
 * 
 * Result Expected:
 * Able to
 */

@GUITest
@Test(singleThreaded = true)
public class S1768_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1768_02NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	int startIndex = 12;
	int endIndex = 12;
	int foUserBeat = 12;

	FOUsers foUser;

	@BeforeClass(groups = {"ApplicantsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			//-------------------------			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	} 
	
	@AfterClass(groups = {"ApplicantsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		foUser = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = {"ApplicantsNG" })
	public void createRegistrantInFrontOfficeNG() throws Exception {
		try {
			
			foUser = new FOUsers(foUserBeat, startIndex, endIndex);

			log.debug("Creating Profile in FO");

			Assert.assertTrue(foUser.createProfile(),
					"Fail: Unable to Create FO Profile");

			log.debug("Registrant Added");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		}
	}
	
	@Test(groups = {"ApplicantsNG" }, dependsOnMethods = { "createRegistrantInFrontOfficeNG"})
	public void testAddingFORegistrantToApplicantInPO() throws Exception {

		GeneralUtil.switchToPO();

		log.debug("Creating an Applicant from PO");

		Assert.assertTrue(foUser.createPOApplicants(),
				"Fail: to Add An Applicant");

		log.debug("Applicant Created");

		log.debug("Adding Registrant");

		Assert.assertTrue(foUser.addPORegistrant(),
				"Fail: to Add Registrant");
	}
}
