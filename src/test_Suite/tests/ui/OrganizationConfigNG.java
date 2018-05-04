/**
 * 
 */
package test_Suite.tests.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IOrgConst;
import test_Suite.constants.cases.IOrgConst.EOrgPlannerObjects;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.OrgUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class OrganizationConfigNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "UI_AdminNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			OrgUtil.openParentOrgPlanner(IOrgConst.org_G3Root_Id);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "UI_AdminNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "UI_AdminNG" },dataProvider="OrgsObject")
	public void testOrgConfig(EOrgPlannerObjects eOrgObj) throws Exception {
		try {
			
			OrgUtil.openObjectDetailsInOrgPlanner(eOrgObj);
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveBtn), "Couldn't click Save button!");
			
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.backBtn), "Couldn't click Back button!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "UI_AdminNG" })
	public void testAddingLookupMapping() throws Exception {
		try {
			
			Assert.assertTrue(OrgUtil.addLookupMappingToRootOrg());

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@DataProvider(name="OrgsObject")
	public Object[][] generateObjects() {
		Object[][] result = null;
		try {
			
			result = new Object[][] {
					new Object[] {IOrgConst.EOrgPlannerObjects.documents},
					new Object[] {IOrgConst.EOrgPlannerObjects.forms},
					new Object[] {IOrgConst.EOrgPlannerObjects.FundingOpps},
					new Object[] {IOrgConst.EOrgPlannerObjects.groups},
					new Object[] {IOrgConst.EOrgPlannerObjects.lookups},
					new Object[] {IOrgConst.EOrgPlannerObjects.programs},
					new Object[] {IOrgConst.EOrgPlannerObjects.reports},
					new Object[] {IOrgConst.EOrgPlannerObjects.users}};
			
		}catch (Exception e) {

			log.error("Unexpected Exception in createChildOrgHierarchyNG() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
		
		return result;
	}

}
