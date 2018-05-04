/**
 * 
 */
package test_Suite.tests.eForms;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestAllEFormFieldsPDFNG {

	// *********** Variables Declaration Section ********************
	Class<? extends TestAllEFormFieldsPDFNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;

	ArrayList<EFormFieldFunctions> eFormFieldFuncList;

	String preFix = "";
	String postFix = "";

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			log.info("Opening New Window and Login as Admin");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

			form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormTitle("Test Create All eFormFields");

			form.setEFormId("Test-Create-All-eFormFields");

			form.createEForm();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		form = null;
		formlet = null;
		eFormField = null;
		eFormFieldFunc = null;
		eFormFieldFuncList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" })
	public void testNoPropertiesEFormFields() throws Exception {

		// * Approval Dropdwon
		// * Checkbox
		// * Conflict of Interest Selector
		// * Date
		// * EIN Number
		// * Email Address
		// * Web Address

		// adding One Formlet for all No Properties eFormFields

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("No Propreties eFormFields");

		formlet.setFormletMenuText("No Propreties eFormFields");

		formlet.setFormletId("No-Properties-eFormFields");

		formlet.createFormlet(false, false);

		// start adding eFormFields
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		// Checkbox
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_CheckboxField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_CheckboxField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_CheckboxField_Name);
		eFormField.addeFormField(true, false, false, false);

		// COI
		//Not supported any more
//		eFormField
//				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_COIField_Name);
//		eFormField
//				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_COIField_Name);
//		eFormField
//				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_COIField_Name);
//		eFormField.addeFormField(false, false, false, false);

		// Date
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_DateField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_DateField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_DateField_Name);
		eFormField.addeFormField(true, false, false, false);

		// EIN Number
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_EINNumber_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_EINNumber_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_EINNumber_Name);
		eFormField.addeFormField(true, false, false, false);

		// Email Address
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_EmailAddressField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_EmailAddressField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_EmailAddressField_Name);
		eFormField.addeFormField(true, false, false, false);

		// Web Address
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_WebAddressField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_WebAddressField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_WebAddressField_Name);
		eFormField.addeFormField(true, false, false, false);

		testNoPropertiesEFormFieldsData();

	}

	private void testNoPropertiesEFormFieldsData() throws Exception {

		ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		ClicksUtil.clickLinks("No Propreties eFormFields");

		EFormsUtil.selectFromDropDown(0, "Approved");

		EFormsUtil.taggleCheckbox(0, true);

		EFormsUtil.enterDateToDateField(1);

		EFormsUtil.enterTextToTextField(2, "12-1234567");

		EFormsUtil.enterTextToTextField(3, "noMail@grantium.com");

		EFormsUtil.enterTextToTextField(4, "http://www.grantium.com");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToEFormPlanner);

	}

	@Test(groups = { "EFormsNG" })
	public void testDropdownEFormFields() throws Exception {

		// Child Dropdown
		// Dropdown
		// Dropdown From List
		// Dropdown From List (Text Value from Applicant Profile)

		String parentField;

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Dropdown eFormFields");

		formlet.setFormletMenuText("Dropdown eFormFields");

		formlet.setFormletId("Dropdown-eFormFields");

		formlet.createFormlet(false, false);

		// start adding eFormFields Dropdown
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		parentField = eFormField.getEFormFieldId();

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, true, false, false);

		String strDropdownValues[] = { "Activity Types", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		// Child Dropdown
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_ChildDropdownField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ChildDropdownField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ChildDropdownField_Name);
		eFormField.addeFormField(true, true, false, false);

		String strChildDropdownValues[] = { parentField, "No" };

		eFormField.addDropdwonFieldProperties(strChildDropdownValues);

		testDropdownEFormFieldsData();
	}

	private void testDropdownEFormFieldsData() throws Exception {

		ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		ClicksUtil.clickLinks("Dropdown eFormFields");

		EFormsUtil.selectFromDropDown(0, "E-mail");

		GeneralUtil.takeANap(0.5);

		EFormsUtil.selectFromDropDown(1, "Green");

		// TODO: More Dropdown to do

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToEFormPlanner);

	}

	@Test(groups = { "EFormsNG" })
	public void testEFormFieldsWithStylesProp() throws Exception {

		// * Button
		// * Instructions
		// * Label Only
		// * Read-Only Text

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Styles Property eFormFields");

		formlet.setFormletMenuText("Styles Property eFormFields");

		formlet.setFormletId("Styles-Property-eFormFields");

		formlet.createFormlet(false, false);

		// start adding eFormFields Button
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ButtonField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ButtonField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ButtonField_Name);

		eFormField.setEFormFieldDescription("I'm Above the Button!");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		String strButtonValues[] = { "Largest",
				"Centered, with Description Above" };

		eFormField.addPropertiesForFieldsWithStylesOnly(strButtonValues);

		// Instruction
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_InstructionsField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_InstructionsField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_InstructionsField_Name);
		eFormField.setEFormFieldDescription("I'm Center and Highlighted!");
		eFormField.addeFormField(true, true, false, false);

		String strInstructionValues[] = { "Centered, Highlighted" };

		eFormField.addPropertiesForFieldsWithStylesOnly(strInstructionValues);

		// Label Only
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_LableOnly_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_LableOnly_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_LableOnly_Name);
		eFormField.setEFormFieldDescription("I'm Left Aligned!");
		eFormField.addeFormField(true, true, false, false);

		String strLabelOnlyValues[] = { "Beside, Left-Aligned" };

		eFormField.addPropertiesForFieldsWithStylesOnly(strLabelOnlyValues);

		// Read Only Text
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);
		eFormField
				.setEFormFieldDescription("I'm Left Of Center, but Right Aligned!");
		eFormField.addReadOnlyeFormField(true, "Left-Of-Center, Right-Aligned");

		// String strReadOnlyValues[] = { "Left-Of-Center, Right-Aligned" };

		// eFormField.addPropertiesForFieldsWithStylesOnly(strReadOnlyValues);
	}

	@Test(groups = { "EFormsNG" })
	public void testEFormFieldsWithMinAndMaxProp() throws Exception {
		Object[] obj;
		Integer[] minMaxValues;

		// * Long Text
		// * Numeric
		// * Password
		// * Review Score
		// * Short Text

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Min And Max Properties eFormFields");

		formlet.setFormletMenuText("Min And Max Properties eFormFields");

		formlet.setFormletId("Min-And-Max-Properties-eFormFields");

		formlet.createFormlet(false, false);

		// start adding eFormFields Long Text
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldDescription("I'm Below!");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		obj = new Object[4];

		obj[0] = 100;
		obj[1] = 1000000; // Reduced to allow Automated works
		obj[2] = 20;
		obj[3] = "Below, with Description Below";

		eFormField.addLongTextFieldProperties(obj);

		// Numeric
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_NumericField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_NumericField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_NumericField_Name);
		eFormField.setEFormFieldDescription("");
		eFormField.addeFormField(true, true, false, false);

		obj = new Object[6];

		obj[0] = 1000.00;
		obj[1] = 9000000.00;
		obj[2] = 4;
		obj[3] = false;
		obj[4] = "Currency";
		obj[5] = "$";

		eFormField.addNumericFieldProperties(obj);

		// Password
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_PasswordField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_PasswordField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_PasswordField_Name);
		eFormField.setEFormFieldDescription("");
		eFormField.addeFormField(true, true, false, false);

		minMaxValues = new Integer[2];

		minMaxValues[0] = 3;
		minMaxValues[1] = 16;

		eFormField.addShortTextFieldProperties(minMaxValues);

		// Short Text
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_ShortTextField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ShortTextField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ShortTextField_Name);
		eFormField.setEFormFieldDescription("");
		eFormField.addeFormField(true, true, false, false);

		minMaxValues = new Integer[2];

		minMaxValues[0] = 100;
		minMaxValues[1] = 2000;

		eFormField.addShortTextFieldProperties(minMaxValues);

		// Numeric
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_NumericField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_NumericField_Name
						+ "2");
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_NumericField_Name
						+ "2");
		eFormField.setEFormFieldDescription("");
		eFormField.addeFormField(true, true, false, false);

		obj = new Object[6];

		obj[0] = 1000;
		obj[1] = 9000000.00;
		obj[2] = 4;
		obj[3] = false;
		obj[4] = "None";
		obj[5] = "$";

		eFormField.addNumericFieldProperties(obj);

		// Review Score
		eFormField
				.setEFormFieldType(IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name);
		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name);
		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name);
		eFormField.setEFormFieldDescription("");
		eFormField.addeFormField(true, true, false, false);

		obj = new Object[5];

		obj[0] = 0.0;
		obj[1] = 10.0;
		obj[2] = 0;
		obj[3] = true;
		obj[4] = 50.0;

		eFormField.addReviewScoreFieldProperties(obj);

		testEFormFieldsWithMinAndMaxData();

	}

	private void testEFormFieldsWithMinAndMaxData() throws Exception {

		ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		ClicksUtil.clickLinks("Min And Max Properties eFormFields");

		EFormsUtil.enterTextToTextField(0, EFormsUtil.createRandomString(4000));

		Integer val = EFormsUtil.createRandomString(4000).length();

		EFormsUtil.enterTextToTextField(1, val.toString());

		EFormsUtil.enterTextToTextField(2, "abcdefghi");

		EFormsUtil.enterTextToTextField(3, EFormsUtil.createRandomString(2000));

		val = EFormsUtil.createRandomString(2000).length();

		EFormsUtil.enterTextToTextField(4, val.toString());

		EFormsUtil.enterTextToTextField(5, "10");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToEFormPlanner);

	}

	/*
	 * @Test(groups = { "EFormsNG" }) public void testEFormFieldsWithGrids()
	 * throws Exception {
	 * 
	 * 
	 * // Data-Grid // Grid View of List Fields // Numeric Data-Grid // Text
	 * Data-Grid
	 * 
	 * 
	 * formlet = new Formlet(form,
	 * IFormletsConst.formletTypeName_eFormQuestionHolder);
	 * 
	 * formlet.setFormletTitleText("Grids eFormFields");
	 * 
	 * formlet.setFormletMenuText("Grids eFormFields");
	 * 
	 * formlet.setFormletId("Grids-eFormFields");
	 * 
	 * formlet.createFormlet(false, false); }
	 */
	@Test(groups = { "EFormsNG" })
	public void testEFormFieldsWithDropdwonProp() throws Exception {

		// * Many-to-Many
		// * Radio Button

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Dropdown Lookup Property eFormFields");

		formlet.setFormletMenuText("Dropdown Lookup Property eFormFields");

		formlet.setFormletId("Dropdown-Lookup-Property-eFormFields");

		formlet.createFormlet(false, false);

		// Start Adding eFormField Many 2 Many
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ManyToManyField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_ManyToManyField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_ManyToManyField_Name);

		eFormField.setEFormFieldDescription("Check List M2M");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		String[] dropdownValues = new String[2];

		dropdownValues[0] = "Canadian Provinces";

		dropdownValues[1] = "Check List";

		eFormField.addPropertiesForFieldsWithDropdownOnly(dropdownValues);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_RadioButton_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_RadioButton_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_RadioButton_Name);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		dropdownValues = new String[1];

		dropdownValues[0] = "US States";

		eFormField.addPropertiesForFieldsWithDropdownOnly(dropdownValues);

		testEFormFieldsWithDropdwonData();
	}

	private void testEFormFieldsWithDropdwonData() throws Exception {

		ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		ClicksUtil.clickLinks("Dropdown Lookup Property eFormFields");

		for (int x = 0; x < 13; x += 2) {
			EFormsUtil.taggleCheckbox(x, true);
		}

		EFormsUtil.taggleRadioButton(7, true);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToEFormPlanner);
	}

	@Test(groups = { "EFormsNG" })
	public void testEFormFieldsWithTypesProp() throws Exception {

		// * Phone Number
		// * Postal Code

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Types Propreties eFormFields");

		formlet.setFormletMenuText("Types Properties eFormFields");

		formlet.setFormletId("Type-Properties-eFormFields");

		formlet.createFormlet(false, false);

		// Start Adding eFormField Phone Number
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_PhoneNumberField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_PhoneNumberField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_PhoneNumberField_Name);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		String[] dropValues = new String[2];

		dropValues[0] = IeFormFieldsConst.eFormField_PhoneNumber_TypesValues[2];

		dropValues[1] = IeFormFieldsConst.eFormField_PhoneNumber_NorthAmericanDisplayStyles[2];

		eFormField.addPropertiesForFieldsWithTypesOnly(dropValues);

		// Postal Code
		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_PostalCodeField_Name);

		eFormField
				.setEFormFieldId(IeFormFieldsConst.eFormFieldType_PostalCodeField_Name);

		eFormField
				.setEFormFieldLabel(IeFormFieldsConst.eFormFieldType_PostalCodeField_Name);

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(false, true, false, false);

		dropValues = new String[3];

		dropValues[0] = IeFormFieldsConst.eFormField_PostalCode_TypesValues[2];

		dropValues[1] = IeFormFieldsConst.eFormField_PostalCode_CanadiansDisplayCodes[1];

		dropValues[2] = IeFormFieldsConst.eFormField_PostalCode_USDisplayCodes[0];

		eFormField.addPropertiesForFieldsWithTypesOnly(dropValues);

		testEFormFieldsWithTypesData();
	}

	private void testEFormFieldsWithTypesData() throws Exception {

		ClicksUtil.clickButtons(IClicksConst.previewFormBtn);

		ClicksUtil.clickLinks("Types Properties eFormFields");

		EFormsUtil.enterTextToTextField(0, "001613 2307890");

		EFormsUtil.enterTextToTextField(1, "K1P-5J9");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ClicksUtil.clickLinks(IClicksConst.backToEFormPlanner);
	}

}
