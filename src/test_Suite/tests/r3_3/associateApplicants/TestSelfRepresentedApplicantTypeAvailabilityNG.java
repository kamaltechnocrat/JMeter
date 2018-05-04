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
import test_Suite.lib.users.FOUsers;
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
public class TestSelfRepresentedApplicantTypeAvailabilityNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	Integer foUserBeat;
	
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
			
			foUserBeat = Integer.parseInt(EFormsUtil.createRandomNumber(3));
			
			appType = "student".concat(foUserBeat.toString());
			
			typeIdent = "G".concat(foUserBeat.toString()).concat("_APPLICANT_TYPE_STUDENT");

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
			
			Assert.assertTrue(ApplicantTypesUtils.addNewApplicantType(typeIdent, EAppCategories.selfRepresented, appType), "ERROR: Not able to add new Applicant Type!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addingNewApplicantTypeNG")
	public void testNewApplicantTypeNG() throws Exception {
		try {
			
			Assert.assertTrue(ApplicantTypesUtils.findApplicantTypeInList(IApplicantTypesConst.applicantCategories[EAppCategories.selfRepresented.ordinal()]));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
