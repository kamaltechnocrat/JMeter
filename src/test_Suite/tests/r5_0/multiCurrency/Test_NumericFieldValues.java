/**
 * 
 */
package test_Suite.tests.r5_0.multiCurrency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class Test_NumericFieldValues {

	// *********** Variables Declaration Section ********************
	Class<? extends Test_NumericFieldValues> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	// ----------- End of Variables Declaration ---------------------

	@BeforeClass(groups = { "MultiCurrencyNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

	}

	@AfterClass(groups = { "MultiCurrencyNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	/*
	 * Method will search the published efrom which is created by executing the
	 * MustRunFirst java class and will preview the eform
	 */
	@Test(groups = { "MultiCurrencyNG" }, enabled = true)
	public void seachAndPreviewEForm() throws Exception {
		try {
			Assert.assertTrue(EFormsUtil
					.searchEforms(IeFormFieldsConst.EformName[1],
							IeFormFieldsConst.eformType),
					"FAIL: Searched eform doesn't exist");
			ClicksUtil.clickButtons(IClicksConst.previewFormBtn);
		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);
		}

	}

	/*
	 * The method will put the numeric values in numeric fields and data grid
	 * numeric fields
	 */

	@Test(groups = { "MultiCurrencyNG" }, dataProvider = "fieldValues", dependsOnMethods = "seachAndPreviewEForm", enabled = true)
	public void setNumericFieldValues(String fieldId, Integer fieldValues)
			throws Exception {
		try {

			Assert.assertTrue(EFormsUtil.setFieldValues(fieldId, fieldValues),
					"FAIL: Eform field doesn't exist ");

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	/*
	 * The below method will validate the currency symbol get associated with
	 * the numeric value after saving the numeric field values
	 */
	@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "setNumericFieldValues", enabled = true)
	public void validateEformFieldValues() throws Exception {
		try {

			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			Assert.assertTrue(EFormsUtil.validateFieldValue(),
					"FAIL: Field Values are not equal to expected values");
			ClicksUtil.clickButtons(IClicksConst.backToEFormPlanner);
		} catch (Exception e) {

			Assert.fail("Unexpected Exception:", e);
		}
	}

	@DataProvider(name = "fieldValues")
	public Object[][] generateFieldValues() {

		Object[][] values = null;

		values = new Object[][] {
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[0], 1001 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[1], 1002 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[2], 1003 },

				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[3], 1004 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[4], 1005 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[5], 1006 },

				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[6], 1007 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[7], 1008 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[8], 1009 },

				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[9], 1010 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[10], 1011 },
				new Object[] {
						IeFormFieldsConst.eFormFiled_Numeric_fieldIds[11], 1012 }

		};

		return values;

	}

}
