/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfConfig;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IFormletsFunctionsConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CreateEFormsLBFF {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	EForm subForm;
	EForm adminForm;
	EForm awardForm;
	EForm ipasForm;
	EForm standardAward;
	EForm poSubmission;

	Formlet formlet;
	Formlet subFormlet;

	Formlet summaryFormlet;
	Formlet scheduleFormlet;
	Formlet attachFormlet;
	
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;
	FormletFunctions formletFunc;
	String strFormletFuncProp[];

	String tempEFormFieldId = null;
	String tempFormletId = null;
	String tempEFormId = null;

	String tempDefaultFormlet = null;

	int syncTypeOrdinal;

	ILBFunctionConst.ESyncTypes syncTypes;

	ArrayList<String> formletIdents;

	String preFix = "LBF-";
	String postFix;
	String postFixIdent;

	int formletIndex;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		postFix = ILBFunctionConst.postFixTitle[3];
		postFixIdent = ILBFunctionConst.postFixIdentifier[3];
		formletIndex = -1;

		createApplicantProfile_LBF_eFormNG();

		createAdministrator_LBF_eFormNG();

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" }, dataProvider = "SyncTypes")
	public void createAll_LBF_eFormsNG(int typeOrdinal) throws Exception {

		syncTypeOrdinal = typeOrdinal;
		postFix = ILBFunctionConst.postFixTitle[syncTypeOrdinal];
		postFixIdent = ILBFunctionConst.postFixIdentifier[syncTypeOrdinal];

		createPOSubmission_LBF_eFormNG();

		createSubmission_LBF_eFormNG();

		//createAward_LBF_eFormNG();

		createStandardAgreement_LBF_eFormNG();

		createIPAS_LBF_eFormNG();
	}

	@DataProvider(name = "SyncTypes")
	public Object[][] generateSyncType() throws Exception {

		return new Object[][] {
				new Object[] { ILBFunctionConst.ESyncTypes.source.ordinal() },
				new Object[] { ILBFunctionConst.ESyncTypes.less.ordinal() },
				new Object[] { ILBFunctionConst.ESyncTypes.more.ordinal() },
				new Object[] { ILBFunctionConst.ESyncTypes.equal.ordinal() }

		};
	}

	private void createSubmission_LBF_eFormNG() throws Exception {

		subForm = new EForm(IEFormsConst.submission_FormTypeName,
				IEFormsConst.applicantsubmission_FormTypeName, preFix);

		String temp = subForm.getEFormFullId().replace("-", " ");

		subForm.setEFormTitle(temp);

		Assert
				.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");
		
		attachFormlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(attachFormlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(attachFormlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

	}

	@SuppressWarnings("unused")
	private void createAward_LBF_eFormNG() throws Exception {

		awardForm = new EForm(IEFormsConst.award_FormTypeName,
				IEFormsConst.award_FormTypeName, preFix);

		String temp = awardForm.getEFormFullId().replace("-", " ");

		awardForm.setEFormTitle(temp);

		Assert.assertTrue(awardForm.createEForm(),
				"FAIL: Could not Create eForm");
		
		attachFormlet = new Formlet(awardForm,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(attachFormlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(attachFormlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

	}

	private void createPOSubmission_LBF_eFormNG() throws Exception {

		poSubmission = new EForm(IEFormsConst.submission_FormTypeName,
				IEFormsConst.progOfficeSubmission_FormTypeName, preFix);

		
		poSubmission.setEFormId(preFix + "PO Submission");
		poSubmission.setEFormFullId(poSubmission.getEFormId() + postFixIdent);

		String temp = poSubmission.getEFormFullId().replace("-", " ");

		poSubmission.setEFormTitle(temp);

		Assert.assertTrue(poSubmission.createEForm(),
				"FAIL: Could not Create eForm");

		scheduleFormlet = new Formlet(poSubmission,
				IFormletsConst.formletTypeName_SubmissionScheduleList);

		Assert.assertTrue(scheduleFormlet.initFormletWithDetails(
				ILBFunctionConst.EFormletTypes.subSchedule.ordinal(),
				syncTypeOrdinal),
				"FAIL: Could not add Submisison Schedule Formlet");

		formletFunc = new FormletFunctions(poSubmission, scheduleFormlet);

		Assert
				.assertTrue(formletFunc.addLBFFunction(scheduleFormlet),
						"FAIL: Could not add LBF Function to Submission Schedule List Formlet");

		EFormsUtil.returnToPlanner();
		
		attachFormlet = new Formlet(poSubmission,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(attachFormlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(attachFormlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

	}

	private void createStandardAgreement_LBF_eFormNG() throws Exception {

		standardAward = new EForm(IEFormsConst.award_FormTypeName,
				IEFormsConst.award_FormTypeName, preFix);

		
		standardAward.setEFormId(preFix + "Standard Agreement");
		standardAward.setEFormFullId(standardAward.getEFormId() + postFixIdent);

		String temp = standardAward.getEFormFullId().replace("-", " ");

		standardAward.setEFormTitle(temp);

		Assert.assertTrue(standardAward.createEForm(),
				"FAIL: Could not Create eForm");

		scheduleFormlet = new Formlet(standardAward,
				IFormletsConst.formletTypeName_SubmissionScheduleList);

		Assert.assertTrue(scheduleFormlet.initFormletWithDetails(
				ILBFunctionConst.EFormletTypes.subSchedule.ordinal(),
				syncTypeOrdinal),
				"FAIL: Could not add Submisison Summary Formlet");

		formletFunc = new FormletFunctions(standardAward, scheduleFormlet);

		Assert
				.assertTrue(formletFunc.addLBFFunction(scheduleFormlet),
						"FAIL: Could not add LBF Function to Submission Schedule List Formlet");

		EFormsUtil.returnToPlanner();
		
		attachFormlet = new Formlet(standardAward,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(attachFormlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(attachFormlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
	}

	private void createIPAS_LBF_eFormNG() throws Exception {

		ipasForm = new EForm(IEFormsConst.submission_FormTypeName,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

		String temp = ipasForm.getEFormFullId().replace("-", " ");

		ipasForm.setEFormTitle(temp);

		Assert.assertTrue(ipasForm.createEForm(),
				"FAIL: Could not Create eForm");
		
		attachFormlet = new Formlet(ipasForm,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(attachFormlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(attachFormlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
	}

	private boolean addLBFFunction(Formlet funcFormlet) throws Exception {

		// Setup Formlet Function
		formletFunc = new FormletFunctions(form, formlet.getFormletType(),
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		strFormletFuncProp = new String[4];
		strFormletFuncProp[0] = IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Values_Array[1];
		strFormletFuncProp[1] = formlet.getFormletId();

		strFormletFuncProp[2] = "yes";
		strFormletFuncProp[3] = "true";

		return formletFunc.addListBringForwardFunction(strFormletFuncProp,
				formlet);

	}

	private void initFormlet(int formletTitle) throws Exception {

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList);

		subFormlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder);

		if (LBFunctionUtil.initListFormlet(formlet, subFormlet,
				syncTypeOrdinal, formletTitle)) {
			formletIndex += 1;

			formletIdents.add(formletIndex, formlet.getFormletId());
		} else {
			Assert.fail("FAIL: could not create eList Formlet!");
		}
	}

	private void initField(String fieldType, String suffix) throws Exception {

		eFormField = new EFormField(form, subFormlet, fieldType);

		eFormField.initListDetailsField(fieldType, suffix);

	}

	private void addDataGridFormletAndFields() throws Exception {

		// Data Grid Numeric Entry Cells//
		// Data Grid Fixed Text Cells//
		// Data Grid Fixed Text Cells, Imported from lookup
		// Data Grid Text Entry Cell//
		// Data Grid Dropdown from Lookup Cell
		// Data Grid Dropdown from List Cell

		/***** start adding eFormFields ****/

		// Data Grid Numeric Entry Cells
		initField(IeFormFieldsConst.eFormFieldType_DataGridField_Name,
				"-Numeric");

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "2");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_NumericEntryCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Data Grid Text Entry Cell
		initField(IeFormFieldsConst.eFormFieldType_DataGridField_Name, "-Text");

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "2");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_TextEntryCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Data Grid Fixed Text Cells
		initField(IeFormFieldsConst.eFormFieldType_DataGridField_Name,
				"-Fixed-Text");

		eFormField.addeFormField(false, true, false, false);

		eFormField.configureDataGridEFormField("1", "1");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_FixedTextCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Data Grid Dropdown from Lookup Cell
		initField(IeFormFieldsConst.eFormFieldType_DataGridField_Name,
				"-Lookup");

		eFormField.addeFormField(true, true, true, false);

		eFormField.configureDataGridEFormField("2", "3");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField
				.setDataGridCellType_DropdownFromLookupCell("Canadian Provinces");

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

	}

	private void addMinAndMaxPropertiesFormletAndFields() throws Exception {

		Object[] obj;
		Integer[] minMaxValues;

		// * Long Text
		// * Numeric
		// * Password
		// * Review Score
		// * Short Text

		/***** start adding eFormFields ****/

		// Long Text
		initField(IeFormFieldsConst.eFormFieldType_LongTextField_Name, "");

		eFormField.setEFormFieldDescription("I'm Below!");

		eFormField.addeFormField(true, true, true, false);

		obj = new Object[4];

		obj[0] = 100;
		obj[1] = 1000000; // Reduced to allow Automated works
		obj[2] = 20;
		obj[3] = "Below, with Description Below";

		eFormField.addLongTextFieldProperties(obj);

		// Numeric
		initField(IeFormFieldsConst.eFormFieldType_NumericField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		obj = new Object[5];

		obj[0] = 1000.00;
		obj[1] = 9000000.00;
		obj[2] = 4;
		obj[3] = false;
		obj[4] = "Dollar";

		eFormField.addNumericFieldProperties(obj);

		// Password
		initField(IeFormFieldsConst.eFormFieldType_PasswordField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		minMaxValues = new Integer[2];
		minMaxValues[0] = 3;
		minMaxValues[1] = 16;

		eFormField.addShortTextFieldProperties(minMaxValues);

		// Short Text
		initField(IeFormFieldsConst.eFormFieldType_ShortTextField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		minMaxValues = new Integer[2];
		minMaxValues[0] = 100;
		minMaxValues[1] = 2000;

		eFormField.addShortTextFieldProperties(minMaxValues);

		// Review Score
		initField(IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		obj = new Object[5];
		obj[0] = 0.0;
		obj[1] = 10.0;
		obj[2] = 0;
		obj[3] = true;
		obj[4] = 50.0;

		eFormField.addReviewScoreFieldProperties(obj);

	}

	@SuppressWarnings("unused")
	private void addStyleProperitesFormletAndFields() throws Exception {

		// * Label Only
		// * Read-Only Text

		/***** start adding eFormFields ****/
		// Label Only
		initField(IeFormFieldsConst.eFormFieldType_LableOnly_Name, "");

		eFormField.setEFormFieldDescription("I'm Left Aligned!");

		eFormField.addeFormField(false, true, false, false);

		String strLabelOnlyValues[] = { "Beside, Left-Aligned" };

		eFormField.addPropertiesForFieldsWithStylesOnly(strLabelOnlyValues);

		// Read Only Text
		initField(IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name, "");

		eFormField
				.setEFormFieldDescription("I'm Left Of Center, but Right Aligned!");

		eFormField.addReadOnlyeFormField(true, "Left-Of-Center, Right-Aligned");

	}

	private void addTypeProperitesFormletAndFields() throws Exception {

		// * Phone Number
		// * Postal Code

		/***** start adding eFormFields ****/
		// Phone Number
		initField(IeFormFieldsConst.eFormFieldType_PhoneNumberField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		String[] dropValues = new String[2];

		dropValues[0] = IeFormFieldsConst.eFormField_PhoneNumber_TypesValues[2];

		dropValues[1] = IeFormFieldsConst.eFormField_PhoneNumber_NorthAmericanDisplayStyles[2];

		eFormField.addPropertiesForFieldsWithTypesOnly(dropValues);

		// Postal Code
		initField(IeFormFieldsConst.eFormFieldType_PostalCodeField_Name, "");

		eFormField.addeFormField(true, true, true, false);

		dropValues = new String[3];
		dropValues[0] = IeFormFieldsConst.eFormField_PostalCode_TypesValues[2];
		dropValues[1] = IeFormFieldsConst.eFormField_PostalCode_CanadiansDisplayCodes[1];
		dropValues[2] = IeFormFieldsConst.eFormField_PostalCode_USDisplayCodes[0];

		eFormField.addPropertiesForFieldsWithTypesOnly(dropValues);

	}

	private void addNoPropertiesFormletAndFields() throws Exception {

		/***** start adding eFormFields ****/
		// Approval DropDown
		initField(IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name,
				"");

		eFormField.addeFormField(true, false, true, false);

		// Checkbox
		initField(IeFormFieldsConst.eFormFieldType_CheckboxField_Name, "");

		eFormField.addeFormField(true, false, true, false);

		// COI
		initField(IeFormFieldsConst.eFormFieldType_COIField_Name, "");

		eFormField.addeFormField(false, false, true, false);

		// Date
		initField(IeFormFieldsConst.eFormFieldType_DateField_Name, "");

		eFormField.addeFormField(true, false, true, false);

		// EIN Number
		initField(IeFormFieldsConst.eFormFieldType_EINNumber_Name, "");

		eFormField.addeFormField(true, false, true, false);

		// Email Address
		initField(IeFormFieldsConst.eFormFieldType_EmailAddressField_Name, "");

		eFormField.addeFormField(true, false, true, false);

		// Web Address
		initField(IeFormFieldsConst.eFormFieldType_WebAddressField_Name, "");

		eFormField.addeFormField(true, false, true, false);
	}

	private void createApplicantProfile_LBF_eFormNG() throws Exception {

		form = new EForm(IEFormsConst.applicantWorkspace_FormTypeName,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);
		
		String temp = form.getEFormFullId().replace("-", " ");

		form.setEFormTitle(temp);

		Assert.assertTrue(form.createEForm(), "FAIL: Could not Create eForm");

		formletIdents = new ArrayList<String>();

		summaryFormlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary);

		Assert.assertTrue(summaryFormlet.initFormletWithDetails(
				ILBFunctionConst.EFormletTypes.subSummary.ordinal(),
				syncTypeOrdinal),
				"FAIL: Could not add Submisison Summary Formlet");

		formletIndex += 1;

		formletIdents.add(formletIndex, summaryFormlet.getFormletId());

		initFormlet(ILBFunctionConst.EFormletTypes.noProp.ordinal());
		addNoPropertiesFormletAndFields();

		Assert
				.assertTrue(addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to No Propreties eFormFields eList Formlet");

		EFormsUtil.returnToPlanner();

		initFormlet(ILBFunctionConst.EFormletTypes.typeProp.ordinal());
		addTypeProperitesFormletAndFields();

		Assert
				.assertTrue(addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to Type Propreties eFormFields eList Formlet");

		EFormsUtil.returnToPlanner();
		
		initFormlet(ILBFunctionConst.EFormletTypes.minMaxProp.ordinal());

		addMinAndMaxPropertiesFormletAndFields();

		Assert
				.assertTrue(
						addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to Min And Max Propreties eFormFields eList Formlet");

		EFormsUtil.returnToPlanner();

		initFormlet(ILBFunctionConst.EFormletTypes.dGrid.ordinal());

		addDataGridFormletAndFields();

		Assert
				.assertTrue(addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to Data Grid eFormFields eList Formlet");

		EFormsUtil.returnToPlanner();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_AttachmentsList);

		Assert
				.assertTrue(formlet.initFormletWithDetails(
						ILBFunctionConst.EFormletTypes.attchments.ordinal(),
						syncTypeOrdinal),
						"FAIL: Could not add Attachment List Formlet");

		formletIndex += 1;

		formletIdents.add(formletIndex, formlet.getFormletId());

		Assert.assertTrue(addLBFFunction(formlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

		EFormsUtil.returnToPlanner();

		LBFunctionUtil.addAttachments(formlet);

	}

	private void createAdministrator_LBF_eFormNG() throws Exception {

		adminForm = new EForm(IEFormsConst.progAdministration_FormTypeName,
				IEFormsConst.progAdministration_FormTypeName, preFix);

		String temp = adminForm.getEFormFullId().replace("-", " ");

		adminForm.setEFormTitle(temp);

		Assert.assertTrue(adminForm.createEForm(),
				"FAIL: Could not Create eForm");

		scheduleFormlet = new Formlet(adminForm,
				IFormletsConst.formletTypeName_SubmissionScheduleList);

		Assert.assertTrue(scheduleFormlet.initFormletWithDetails(
				ILBFunctionConst.EFormletTypes.subSchedule.ordinal(),
				syncTypeOrdinal),
				"FAIL: Could not add Submisison Schedule Formlet");

		formletFunc = new FormletFunctions(adminForm, scheduleFormlet);

		EFormsUtil.returnToPlanner();

		EFormsUtil.expandParentAndChildNode(scheduleFormlet.getFormletId(),
				"Submission Details");

		EFormsUtil.openObjectDetail("Submission Form Dropdown");

		EFormsUtil.taggleCheckbox(0, false);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		EFormsUtil.returnToPlanner();

		for (String string : formletIdents) {

			EFormsUtil.cloneFormlet(form.getEFormFullId(), string);

			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

	}



}
