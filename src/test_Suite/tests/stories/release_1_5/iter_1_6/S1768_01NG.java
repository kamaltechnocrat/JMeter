
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
 * Testing the Creation of new Applicant and new Registrant in PO
 * Steps:
 * 1. Create new Applicant
 * 2. Create new Registrant that will be associated to the Applicant
 * 3. Log in to FO with the New Registrant
 * Result Expected:
 * be able to Create and Log in
 */

@GUITest
@Test(singleThreaded = true)
public class S1768_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends S1768_01NG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	int startIndex = 7;
	int endIndex = 7;
	int foUserBeat = 7;

	FOUsers foUser;

	@BeforeClass(groups = {"ApplicantsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//-------------------------

			foUser = new FOUsers(foUserBeat, startIndex, endIndex);
			
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
	public void testCreateApplicantInPONG() throws Exception {
		try {

			log.debug("Creating an Applicant from PO");

			Assert.assertTrue(foUser.createPOApplicants(),
					"Fail: Unable to Create an Applicant");

			log.info("Applicant Created");
			
		} catch (Exception e) {
			
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"ApplicantsNG" }, dependsOnMethods = {"testCreateApplicantInPONG"})
	public void testCreateRegistrantInPONG() throws Exception {
		try {
			
			log.info("Create Registrant");

			Assert.assertTrue(foUser.createPORegistrant(), "Fail: could not create or associate Registrant!");
			
			log.debug("Registrant Created");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@Test(groups = {"ApplicantsNG" }, dependsOnMethods = {"testCreateRegistrantInPONG"})
	public void testLoginFOWithRegistrantCreatedInPONG() throws Exception {
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO(foUser.getFoUserName());
	}
}
