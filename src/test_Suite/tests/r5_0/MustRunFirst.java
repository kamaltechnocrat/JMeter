/**
 * 
 */
package test_Suite.tests.r5_0;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author k.sharma
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class MustRunFirst {

	// *********** Variables Declaration Section ********************
	Class<? extends MustRunFirst> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	
	Formlet formlet;

	EFormField eFormField;

	public static String SubmissionFormId = null;

	String tempDefaultFormlet = null;

	String preFix = "MC-";
	
	String postFix = "-Test";

	// ----------- End of Variables Declaration ---------------------

	@BeforeClass(groups = { "MultiCurrencyNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

		form = new EForm(IEFormsConst.submission_FormTypeName,
				IEFormsConst.applicantsubmission_FormTypeName, preFix, postFix);

		form.setEFormTitle("General Submission");

		form.setEFormFullId("Test-MultiCurrency-NumericFieldValues");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

	}

	@AfterClass(groups = { "MultiCurrencyNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		form = null;
		formlet = null;
		eFormField = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	// Below method will create an eform

	@Test(groups = { "MultiCurrencyNG" })
	public void createEForm() throws Exception {

		Assert.assertTrue(form.createEForm(), "FAIL: Could not create e.Form!");

		formlet.setFormletTitleText("MultiCurrency Test Formlet");

		formlet.setFormletMenuText(" MutliCurrency Formlet");

		Assert.assertTrue(formlet.createFormlet(true, true),
				"FAIL: could not create Formlet!");

		tempDefaultFormlet = formlet.getFormletId();
	}

	/*
	 * This method will add numeric and data grid numeric field which is only
	 * for this test case only
	 */
	@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "createEForm", enabled = true)
	public void addNumericAndDataGridNumericFieldAndPublishEForm()
			throws Exception {
		try {

			addNumericField();
			addDataGridNumericField();

			Assert.assertTrue(form.publishForm(tempDefaultFormlet),
					"FAIL: Could not publish e.Form!");

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	/*
	 * The below method will add three numeric field with numeric properties
	 * value
	 */
	private void addNumericField() throws Exception {

		Object[] obj;

		obj = new Object[6];

		int totalnumbers = 3;

		for (int i = 0; i < totalnumbers; i++) {

			eFormField = new EFormField(form, formlet,
					IeFormFieldsConst.eFormFieldType_NumericField_Name);

			eFormField
					.setEFormFieldLabel(IeFormFieldsConst.eFormField_Numericfieldlabel_Value[i]);

			eFormField.setEFormFieldDescription("");

			eFormField.setEFormFieldTooltip("");

			Assert.assertTrue(
					eFormField.addeFormField(true, true, false, false),
					"FAIL: Couldn't add numeric field");

			obj[0] = 1000.00;
			obj[1] = 9000000.00;
			obj[2] = 2;
			obj[3] = false;
			obj[4] = "Currency";
			obj[5] = IeFormFieldsConst.eFormField_CurrencySymbol_Value[i];

			Assert.assertTrue(eFormField.addNumericFieldProperties(obj),
					"FAIL: Field properties couldn't add");

		}

	}

	private void addDataGridNumericField() throws Exception {

		// Data Grid Numeric Entry Cells

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DataGridField_Name);

		eFormField
				.initListDetailsField(
						IeFormFieldsConst.eFormFieldType_DataGridField_Name,
						"-Numeric");

		Assert.assertTrue(eFormField.addeFormField(true, true, false, false),
				"FAIL: Could not added eformField ");

		Assert.assertTrue(eFormField.configureDataGridEFormField("2", "2"),
				"FAIL:  Could not configured Datagrid numeric field  ");

		Assert.assertTrue(
				eFormField
						.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
								.ordinal() }),
				"FAIL: Could not select the data gird cell");

		Assert.assertTrue(eFormField.setDataGridCellType_NumericEntryCell(),
				"FAIL, Couldn't set the datagrid cell type");

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

	}

}
