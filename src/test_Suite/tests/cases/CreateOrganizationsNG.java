/**
 * 
 */
package test_Suite.tests.cases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_Suite.lib.cases.Organizations;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.OrgUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import org.fest.swing.annotation.*;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class CreateOrganizationsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Organizations org;

	String parentOrg = "G3";

	String preFix = "Grantium ";

	String postFix = "-Id";

	String fullNames[];

	String shortNames[];

	@BeforeClass(groups = { "OrgHierArchyNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ------------------------------



		} catch (Exception e) {
			Assert.fail("Unexpected error or exception", e);
		}
	}

	/**
	 * Parent method
	 */
	@Test(groups = { "OrgHierArchyNG" },dataProvider="OrgsObject")
	public void createChildOrgHierarchyNG(String randString) throws Exception {

		try {

			initializeOrg("Test-" + randString);
			OrgUtil.addNewChildOrg(org);

		} catch (Exception e) {

			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 

	}

	@AfterClass(groups = { "OrgHierArchyNG" }, alwaysRun=true)
	public void teaDown()throws Exception {
		
		org = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void initializeOrg(String shortName) throws Exception {

		fullNames = new String[4];
		fullNames[0] = preFix + shortName;
		fullNames[1] = preFix + shortName;
		fullNames[2] = preFix + shortName;
		fullNames[3] = preFix + shortName;

		shortNames = new String[4];

		shortNames[0] = shortName;
		shortNames[1] = shortName;
		shortNames[2] = shortName;
		shortNames[3] = shortName;

		org = new Organizations();

		org.setParentOrg(parentOrg);

		org.setOrgId(shortName + postFix);
		org.setInheritParentChanges(true);
		org.setOrgFormIncluded(false);
		org.setOrgFullNames(fullNames);
		org.setOrgShortNames(shortNames);
	}
	
	@DataProvider(name="OrgsObject")
	public Object[][] generateOrgs() {
		Object[][] result = null;
		try {
			
			result = new Object[][] {
					new Object[] {EFormsUtil.createAnyRandomString(5)},
					new Object[] {EFormsUtil.createAnyRandomString(5)},
					new Object[] {EFormsUtil.createAnyRandomString(5)},
					new Object[] {EFormsUtil.createAnyRandomString(5)}};
			
		}catch (Exception e) {

			log.error("Unexpected Exception in createChildOrgHierarchyNG() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
		
		return result;
	}
}
