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

import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestFOGroupRoleCreation {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	String grpName = "";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			grpName = "FO_AssocRole_".concat(EFormsUtil.createAnyRandomString(5));

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
	public void addingNewFoGroupRoleNG() throws Exception {
		try {
			
			Assert.assertTrue(UsersAndGroupsUtil.createAssociateApplicantRole(grpName), "ERROR: could not create new FO Role");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addingNewFoGroupRoleNG")
	public void testNewFoGroupRoleNG() throws Exception {
		try {
			
			Assert.assertFalse(GeneralUtil.isLinkExistsByTxt(IClicksConst.userAccessRightsLnk), "ERROR: Access Rights Tab shouldn't be available");
			
			Assert.assertFalse(GeneralUtil.isLinkExistsByTxt(IClicksConst.userCompleteRightsLnk), "ERROR: Complete Rights Tab sholdn't be available!");
			
			Assert.assertFalse(GeneralUtil.isButtonEnabled(IClicksConst.searchBtn), "ERROR: Search for users button should be disable!");
			
			Assert.assertTrue(UsersAndGroupsUtil.findGroupInList(grpName, "Front Office", "G3"), "ERROR: could not find the new group in the list");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
