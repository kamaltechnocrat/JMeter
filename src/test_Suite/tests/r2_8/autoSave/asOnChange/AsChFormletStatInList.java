/**
 * 
 */
package test_Suite.tests.r2_8.autoSave.asOnChange;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IAutoSaveConst;
import test_Suite.constants.eForms.IAutoSaveConst.EFormletType;
import test_Suite.constants.eForms.IAutoSaveConst.eSelectable;
import test_Suite.lib.eForms.AutoSave;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AsChFormletStatInList {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Formlet formlet;
	
	EFormField efield;
	
	AutoSave auto;
	
	String stepIdent = "A-AutoSave-Submission-Changing-eLists-Status";
	
	String stepName = "A AutoSave Submission Changing eLists Status";

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
	}
	
	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		formlet = null;
		efield = null;
		auto = null;

		EFormsUtil.returnToPlanner();
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "EFormsNG" })	
	public void settingTheTest() throws Exception {
		try {

			GeneralUtil.setApplicantWorkspace(IAutoSaveConst.autoSave_ProfileEFormSource[2]);
			
			auto = new AutoSave();
			auto.initFopp("");
			
			auto.initializeProjAndCreateOrg("-Changing-eLists-Status", true, true);	
			
			auto.fillApplicantProfile();
			
			auto.getProj().createNewPOProjectOnly(true);

			auto.submitProject("Submission", "Ready");

			auto.submitProject("Submission", "Ready");

			auto.submitProject("Submission", "Ready");
			
			auto.openSubmissionForm();
			
			auto.insertDataTo_ELists();	
			
		} catch (Exception e) {
			Assert.fail("Un-expected error: " +e.getMessage());
		}
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest", dataProvider="FormletNames")
	public void testFieldVisibility_OnDropdownChangedNG(EFormletType eFrmltName) throws Exception {
		
		auto.selectFromDropdown(eSelectable.inivisible, eFrmltName, false, false);
		
		Assert.assertFalse(auto.isFormletVisible(eFrmltName), "FAIL: Formlet should be Invisible");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest")
	public void testFieldVisibility_OnCheckboxChangedNG() throws Exception {
		
		auto.selectCheckbox(true, EFormletType.checkboxVisible, false, false);
		
		Assert.assertTrue(auto.isFormletVisible(EFormletType.checkboxVisible), "FAIL: Formlet should be Visible");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest")
	public void testFieldVisibility_OnRadioButtonChangedNG() throws Exception {
		
		auto.selectRadioButton(eSelectable.inivisible, false, false);
		
		Assert.assertFalse(auto.isFormletVisible(EFormletType.radio), "FAIL: Formlet should be Invisible");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest", dataProvider="FormletNames")
	public void testFieldReadability_OnDropdownChangedNG(EFormletType eFrmltName) throws Exception {
		
		auto.selectFromDropdown(eSelectable.readOnly, eFrmltName, false, false);
		
		Assert.assertFalse(auto.isFormletReadOnly(eFrmltName), "FAIL: Formlet should be Read Only");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest")
	public void testFieldReadability_OnCheckboxChangedNG() throws Exception {
		
		auto.selectCheckbox(true, EFormletType.checkboxRead, false, false);
		
		Assert.assertFalse(auto.isFormletReadOnly(EFormletType.checkboxRead), "FAIL: Formlet should be Read Only");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="settingTheTest")
	public void testFieldReadability_OnRadioButtonChangedNG() throws Exception {
		
		auto.selectRadioButton(eSelectable.readOnly, false, false);
		
		Assert.assertFalse(auto.isFormletReadOnly(EFormletType.radio), "FAIL: Formlet should be Read Only");
	}
	
	@DataProvider(name="FormletNames")
	public Object[][] generateNames() {
		
		Object[][] result = null;
		
		result = new Object[][] {		
				new Object[] {EFormletType.dropdown},
				new Object[] {EFormletType.dropdownFromList},
				new Object[] {EFormletType.dropdownFromProfile},
				new Object[] {EFormletType.child}};
		
		return result;
		
	}

}
