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
import org.testng.annotations.Test;

import test_Suite.constants.users.IApplicantTypesConst;
import test_Suite.constants.users.IApplicantTypesConst.EAppCategories;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantTypesUtils;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestInactiveApplicantType {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	String foUserBeat = "";
	
	String appType = "";

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

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void addingInactiveApplicantTypeNG() throws Exception {
		try {
			
			Assert.assertTrue(ApplicantTypesUtils.openApplicantTypesList(), "ERROR: Could not open Applicant Types List");
			
			Assert.assertTrue(ApplicantTypesUtils.addNewApplicantType("G".concat(foUserBeat).concat("_APPLICANT_TYPE_STUDENT"), EAppCategories.selfRepresented, appType), "ERROR: Not able to add new Applicant Type!");
			
			Assert.assertTrue(ApplicantTypesUtils.changeApplicantTypeStatus(false, "G".concat(foUserBeat).concat("_APPLICANT_TYPE_STUDENT")), "ERROR: could not change the status");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	


	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addingInactiveApplicantTypeNG")
	public void verifyInactiveApplicantTypeAvailability() throws Exception {
		try {
			
			Assert.assertTrue(ApplicantTypesUtils.openNewApplicantTypesDetails(), "ERROR: Could not open New Applicant Type Details!");
			
			Assert.assertFalse(GeneralUtil.selectFullStringInDropdownList(IApplicantTypesConst.appTypePO_DrpDown, appType), "ERROR: Inactive Applicant Type shouldn't be available in dropdwon");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
