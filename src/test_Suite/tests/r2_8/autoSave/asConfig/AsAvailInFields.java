/**
 * 
 */
package test_Suite.tests.r2_8.autoSave.asConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IAutoSaveConst;
import test_Suite.constants.eForms.IAutoSaveConst.EFieldType;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.AutoSaveUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AsAvailInFields {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Formlet formlet;
	
	EFormField efield;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		formlet = AutoSaveUtil.initializeEForm(IAutoSaveConst.EFormletsPostfix.dropdown,Boolean.TRUE);
		
		efield = AutoSaveUtil.initializeEFormField(formlet, IAutoSaveConst.EFieldType.dropdown);
		
		efield.addeFormField(true, true, false, false);

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		EFormsUtil.returnToPlanner();
		
		formlet = null;
		efield = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "EFormsNG" },dataProvider="eFieldsTypes")
	public void testAutoSaveAvailableIn_DropdownFiledsNG(String val, EFieldType fieldType) throws Exception {
		
		Assert.assertTrue(efield.verifyAutoSaveTypePropertyExistsForFieldType(val,IAutoSaveConst.fieldsType[fieldType.ordinal()]), "FAIL: Auto-Save On change Property should be available for Dropdown type field!");
	}
	
	@Test(groups = { "EFormsNG" })
	public void testAutoSaveAvailableIn_CheckboxFiledsNG() throws Exception {
		
		Assert.assertTrue(efield.verifyAutoSaveTypePropertyExistsForFieldType("Yes",IAutoSaveConst.fieldsType[IAutoSaveConst.EFieldType.checkbox.ordinal()]), "FAIL: Auto-Save On change Property should be available for Checkbox type field!");
	}
	
	@Test(groups = { "EFormsNG" })
	public void testAutoSaveAvailableIn_RadioButtonFiledsNG() throws Exception {
		
		Assert.assertTrue(efield.verifyAutoSaveTypePropertyExistsForFieldType("Yes",IAutoSaveConst.fieldsType[IAutoSaveConst.EFieldType.radio.ordinal()]), "FAIL: Auto-Save On change Property should be available for Radio Button type field!");
	}
	
	@DataProvider(name="eFieldsTypes")
	public Object[][] generateEFieldTypes()
	{
		Object[][] result = null;		
			
		result = new Object[][] {
				new Object[] {"Yes",IAutoSaveConst.EFieldType.dropdown},
				new Object[] {"Yes",IAutoSaveConst.EFieldType.dropdownFromList},
				new Object[] {"Yes",IAutoSaveConst.EFieldType.dropdownFromProfile},
				new Object[] {"Yes",IAutoSaveConst.EFieldType.child}
		};				
		
		return result;
	}

}
