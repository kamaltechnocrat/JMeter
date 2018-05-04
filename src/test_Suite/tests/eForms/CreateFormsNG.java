/**
 * This a tool to create all forms in the system
 */

package test_Suite.tests.eForms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IAlleFormsFunctionsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class CreateFormsNG {

	// *********** Variables Declaration Section ********************
	Class<? extends CreateFormsNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;
	FormletFunctions formletFunc;

	String applicantFormId = null;
	String SubmissionFormId = null;
	String reviewFormId = null;
	String approvalFormId = null;
	String basicAgreementFormId = null;
	String standradAgreementFormId = null;
	String postAwardSubFormId = null;
	String programFormId = null;
	String publicationFormId = null;
	String strDropdownValues[];
	String strFormletFuncProp[];
	String strEFormFieldFuncProp[];

	String tempEFormFieldId = null;
	String tempFormletId = null;
	String tempEFormId = null;

	String tempDefaultFormlet = null;

	String preFix = "";
	String postFix = "";

	String[] applicantProfileForm = {
			IEFormsConst.applicantWorkspace_FormTypeName, preFix, "G3",
			"Public", "Applicant Profile" };

	// ----------- End of Variables Declaration ---------------------

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		form = null;
		formlet = null;
		eFormField = null;
		eFormFieldFunc = null;
		formletFunc = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createFormsNG() throws Exception {

		try {

			createApplicantProfileForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" })
	public void createSubmissionEForm() throws Exception {
		try {

			createApplicantSubmissionForm();
			createPostAwardSubmissionForm();
			createPOSubmissionForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createAdminEForm() throws Exception {
		try {

			createBasicProgramAdminForm();
			createPublicationForm();
			createOrganizationForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createPapEForm() throws Exception {
		try {

			createFundingOppApprovalForm();
			createFundingOppSubmissionForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createProjectEvaluationEForm() throws Exception {
		try {

			createProjectApprovalForm();
			createReviewForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createAwardEForm() throws Exception {
		try {

			createBasicAwardForm();
			createStandardAwardForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createBFDataGridEForm() throws Exception {
		try {

			createBFDataGridSubmissionForm();

			// TODO need fixing
			// createBFDataGridApprovalForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createBFCSCEForm() throws Exception {
		try {

			// ---CSC: Calculated Submission Condition------//

			// TODO need fixing
			// createBFProjectApprovalCSCForm();
			createBFApplicantSubmissionCSCForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	@Test(groups = { "EFormsNG" },enabled=false)
	public void createBFGPSEForm() throws Exception {
		try {

			// /---GPS: Generate Payment Schedule---

			createGPSProgramAdministrationForm();

			// TODO need fixing
			// createBFPostAwardSubmissionForm();
			// createGPSFundingApplicationForm();

		} catch (Exception e) {

			Assert.fail("Unexpected Exception: ", e);

		}
	}

	private void createApplicantProfileForm() throws Exception {

		form = new EForm(IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantWorkspace_FormTypeName);

		form.setEFormTitle("Applicant Profile");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		formlet.setFormletTitleText("Profile");

		formlet.setFormletMenuText("Profile");

		form.createEForm();

		applicantFormId = form.getEFormId();

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Name:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	private void createBFApplicantSubmissionCSCForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		form.setEFormTitle("Applicant Submission CSC");

		form.setEFormId("Submission BF CSC");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		form.createEForm();

		SubmissionFormId = form.getEFormId();

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_NumericField_Name);

		eFormField.setEFormFieldLabel("Enter Numeric Value:");

		eFormField.setEFormFieldId("Num Field");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);
	}

	private void createApplicantSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		form.setEFormTitle("General Submission");

		form.setEFormId("Sub");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		form.createEForm();

		SubmissionFormId = form.getEFormId();

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("General Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		EFormFieldFunctions eFormFieldFunction = new EFormFieldFunctions(
				eFormField);

		String[] str = new String[4];

		str[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		str[1] = eFormField.getEFormFieldId();
		str[2] = "";
		str[3] = "";

		eFormFieldFunction.addeFormFieldBringForwardFunc(str);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);
	}

	private void createPOSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.progOfficeSubmission_FormTypeName);

		form.setEFormTitle("POSS Submission");

		form.setEFormId("PO Sub");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		form.createEForm();

		SubmissionFormId = form.getEFormId();

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("General Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet.expandFormletNode();

		EFormFieldFunctions eFormFieldFunction = new EFormFieldFunctions(
				eFormField);

		String[] str = new String[4];

		str[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		str[1] = eFormField.getEFormFieldId();
		str[2] = "";
		str[3] = "";

		eFormFieldFunction.addeFormFieldBringForwardFunc(str);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);
	}

	private void createReviewForm() throws Exception {

		form = new EForm(IEFormsConst.review_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.review_FormTypeName);

		form.setEFormTitle("Review Form");

		form.setEFormId("Review Form");

		form.createEForm();

		reviewFormId = form.getEFormId();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Review");

		formlet.setFormletMenuText("Review");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Requirement Fulfilled::");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, true, false, false);

		String strDropdownValues[] = { "Yes/No", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name);

		eFormField.setEFormFieldLabel("Score:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, true, false, false);

		Object[] obj = new Object[5];

		obj[0] = 0.0;
		obj[1] = 10.0;
		obj[2] = 0;
		obj[3] = true;
		obj[4] = 50.0;

		eFormField.addReviewScoreFieldProperties(obj);

		form.publishForm(tempDefaultFormlet);

	}

	@SuppressWarnings("unused")
	private void createBFProjectApprovalCSCForm() throws Exception {

		form = new EForm(IEFormsConst.approval_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.projectApproval_FormTypeName);

		form.setEFormTitle("Approval Form");

		form.setEFormId("Approval CSC BF");

		form.createEForm();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.approval_FormTypeName, preFix);

		formlet.setFormletTitleText("Approval");

		formlet.setFormletMenuText("Approval");

		formlet.setFormletId("Approve");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		// Setup Formlet Function
		formletFunc = new FormletFunctions(form, formlet.getFormletType(),
				IEFormsConst.approval_FormTypeName, preFix);

		strFormletFuncProp = new String[2];
		strFormletFuncProp[0] = "${RO BF Numeric} <= %{CurrentUserApprovalLimit}";
		strFormletFuncProp[1] = "You Exceeded your limit";

		formletFunc.addCalcSubmissionConditionFunction(strFormletFuncProp);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);

		eFormField.setEFormFieldDescription("Allowable Approval Limit");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("RO BF CSC");

		eFormField.addReadOnlyeFormField(false, "");

		// setup eFormField Function
		eFormFieldFunc = new EFormFieldFunctions(eFormField);

		strEFormFieldFuncProp = new String[2];

		strEFormFieldFuncProp[0] = "";
		strEFormFieldFuncProp[1] = "true";

		eFormFieldFunc.addEFormFieldCalcVisibilityFunc(strEFormFieldFuncProp);

		// setup eFormField Function
		eFormFieldFunc = new EFormFieldFunctions();

		strEFormFieldFuncProp = new String[3];

		strEFormFieldFuncProp[0] = "";
		strEFormFieldFuncProp[1] = "%{CurrentUserApprovalLimit}";
		strEFormFieldFuncProp[2] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpened
				.ordinal()];

		eFormFieldFunc.addEFormFieldCalcValueFunc(strEFormFieldFuncProp);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approval Date");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approval Comments");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);

		eFormField.setEFormFieldDescription("RO-BF-Numeric");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("RO BF Numeric");

		eFormField.addReadOnlyeFormField(false, "");

		// setup eFormField Function
		eFormFieldFunc = new EFormFieldFunctions();

		strEFormFieldFuncProp = new String[2];

		strEFormFieldFuncProp[0] = "";
		strEFormFieldFuncProp[1] = "false";

		eFormFieldFunc.addEFormFieldCalcVisibilityFunc(strEFormFieldFuncProp);

		// setup eFormField Function
		eFormFieldFunc = new EFormFieldFunctions();

		strEFormFieldFuncProp = new String[4];

		strEFormFieldFuncProp[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		strEFormFieldFuncProp[1] = "Num Field";
		strEFormFieldFuncProp[2] = "";
		strEFormFieldFuncProp[3] = "";

		eFormFieldFunc.addeFormFieldBringForwardFunc(strEFormFieldFuncProp);

		form.publishForm(tempDefaultFormlet);
	}

	private void createProjectApprovalForm() throws Exception {

		form = new EForm(IEFormsConst.approval_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.projectApproval_FormTypeName);

		form.setEFormTitle("Approval Form");

		form.setEFormId("Approval Form");

		form.createEForm();

		approvalFormId = form.getEFormId();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.approval_FormTypeName, preFix);

		formlet.setFormletTitleText("Approval");

		formlet.setFormletMenuText("Approval");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	@SuppressWarnings("unused")
	private void createBFProjectApprovalForm() throws Exception {

		form = new EForm(IEFormsConst.approval_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.projectApproval_FormTypeName);

		form.setEFormTitle("BF-CSC Approval Form");

		form.setEFormId("BF-SCS Approval Form");

		form.createEForm();

		approvalFormId = form.getEFormId();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.approval_FormTypeName, preFix);

		formlet.setFormletTitleText("Approval");

		formlet.setFormletMenuText("Approval");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		formletFunc = new FormletFunctions(form, formlet.getFormletType(),
				IEFormsConst.approval_FormTypeName, preFix);
		strFormletFuncProp = new String[2];

		strFormletFuncProp[0] = "${RO-BF-Numeric} <= %{CurrentUserApprovalLimit}";
		strFormletFuncProp[1] = "You Exceeded your limit";

		formletFunc.addCalcSubmissionConditionFunction(strFormletFuncProp);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name);

		eFormField.setEFormFieldDescription("Allowable Approval Limit");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("RO BF-CSC");

		eFormField.addReadOnlyeFormField(false, "");

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);
	}

	private void createBasicAwardForm() throws Exception {

		form = new EForm(IEFormsConst.award_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.award_FormTypeName);

		form.setEFormTitle("Award Form");

		form.setEFormId("Basic Agreement");

		form.createEForm();

		basicAgreementFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletId("Agreement Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletTitleText("Agreement Details");

		formlet.setFormletMenuText("Agreement Details");

		formlet.setFormletId("Agreement Details");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	private void createStandardAwardForm() throws Exception {

		form = new EForm(IEFormsConst.award_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.award_FormTypeName);

		form.setEFormTitle("Award Form");

		form.setEFormId("Standard Agreement");

		form.createEForm();

		standradAgreementFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletId("Agreement Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletTitleText("Agreement Details");

		formlet.setFormletMenuText("Agreement Details");

		formlet.setFormletId("Agreement Details");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletTitleText("PA Submission Schedule");

		formlet.setFormletMenuText("PA Submission Schedule");

		formlet.createFormlet(true, false);

		form.publishForm(tempDefaultFormlet);

	}

	@SuppressWarnings("unused")
	private void createPost_AwardForm() throws Exception {

	}

	private void createFundingOppSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.fundingOpportunitySub_FormTypeName);

		form.setEFormTitle("Funding Opp Submission");

		form.setEFormId("Funding Opp Sub");

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		form.createEForm();

		SubmissionFormId = form.getEFormId();

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("FOPP Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet.expandFormletNode();

		EFormFieldFunctions eFormFieldFunction = new EFormFieldFunctions(
				eFormField);

		// eFormFieldFunction.openEFormFieldFunctionDetails();

		String[] str = new String[4];

		str[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		str[1] = eFormField.getEFormFieldId();
		str[2] = "";
		str[3] = "";

		eFormFieldFunction.addeFormFieldBringForwardFunc(str);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);

	}

	private void createFundingOppApprovalForm() throws Exception {

		form = new EForm(IEFormsConst.approval_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.fundingOppApproval_FormTypeName);

		form.setEFormTitle("Funding Opp Approval Form");

		form.setEFormId("Funding Opp Approval");

		form.createEForm();

		approvalFormId = form.getEFormId();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.approval_FormTypeName, preFix);

		formlet.setFormletTitleText("FOPP Approval");

		formlet.setFormletMenuText("FOPP Approval");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	private void createPostAwardSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.postAwardSubmission_FormTypeName);

		form.setEFormTitle("Post Award Submission");

		form.setEFormId("Post Award Submission");

		form.createEForm();

		postAwardSubFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Initial Claim");

		formlet.setFormletMenuText("Initial Claim");

		formlet.setFormletId("Initial Claim");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletId("Submission Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);
	}

	@SuppressWarnings("unused")
	private void createBFPostAwardSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.postAwardSubmission_FormTypeName);

		form.setEFormTitle("BF Post Award Submission");

		form.setEFormId("BF Post Award Submission");

		form.createEForm();

		postAwardSubFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createMenuTypeFormlet();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Initial Claim");

		formlet.setFormletMenuText("Initial Claim");

		formlet.setFormletId("Initial Claim");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		// Setting up Field Function
		String[] eFormFieldFuncValues = new String[4];

		eFormFieldFuncValues[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		eFormFieldFuncValues[1] = eFormField.getEFormFieldId();
		eFormFieldFuncValues[2] = "";
		eFormFieldFuncValues[3] = "";

		eFormFieldFunc = new EFormFieldFunctions();

		eFormFieldFunc.addeFormFieldBringForwardFunc(eFormFieldFuncValues);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletId("Submission Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);
	}

	private void createGPSProgramAdministrationForm() throws Exception {

		form = new EForm(IEFormsConst.progAdministration_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.progAdministration_FormTypeName);

		form.setEFormTitle("GPS Program Form");

		form.setEFormId("GPS Program Form");

		form.createEForm();

		programFormId = form.getEFormId();

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Payment Schedule");

		formlet.setFormletMenuText("Payment Schedule");

		formlet.setFormletId("Payment Schedule");

		tempFormletId = formlet.getFormletId();

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Payment Schedule Details");

		formlet.setFormletId("Payment Schedule Details");

		formlet.setParentFormletId(tempFormletId);

		formlet.createSubFormlet();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Fiscal Name:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Name");

		eFormField.addeFormField(true, false, false, false);

		String[] eForkListColumn = new String[2];

		eForkListColumn[0] = "Submission Name";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Fiscal Start Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Start Date");

		eFormField.addeFormField(true, false, false, false);

		eForkListColumn[0] = "Start Date";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Fiscal End Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal End Date");

		eFormField.addeFormField(true, false, false, false);

		eForkListColumn[0] = "End Date";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		form.publishForm(tempDefaultFormlet);
	}

	@SuppressWarnings("unused")
	private void createGPSFundingApplicationForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		form.setEFormTitle("GPS Funding Application");

		form.setEFormId("GPS Funding Application");

		form.createEForm();

		// programFormId = form.getEFormId();

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Years");

		formlet.setFormletMenuText("Fiscal Years");

		formlet.setFormletId("Fiscal Years");

		formlet.createFormlet(true, true);

		tempFormletId = formlet.getFormletId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Year Details");

		formlet.setFormletId("Fiscal Year Details");

		formlet.setParentFormletId(tempFormletId);

		formlet.createSubFormlet();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Fiscal Name:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Name");

		eFormField.addeFormField(true, false, false, false);

		String[] eFormListColumn = new String[2];
		eFormListColumn[0] = "Fiscal Name";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		// Setting up Field Function
		String[] eFormFieldFuncValues = new String[4];

		eFormFieldFuncValues[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		eFormFieldFuncValues[1] = "Payment Submission Name";
		eFormFieldFuncValues[2] = "";
		eFormFieldFuncValues[3] = "";

		eFormFieldFunc = new EFormFieldFunctions();

		eFormFieldFunc.addeFormFieldBringForwardFunc(eFormFieldFuncValues);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Fiscal Start Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Start Date");

		eFormField.addeFormField(true, false, false, false);

		eFormListColumn[0] = "Fiscal Start Date";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		eFormFieldFuncValues = new String[4];

		eFormFieldFuncValues[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		eFormFieldFuncValues[1] = "Payment Submission Start Date";
		eFormFieldFuncValues[2] = "";
		eFormFieldFuncValues[3] = "";

		eFormFieldFunc = new EFormFieldFunctions();

		eFormFieldFunc.addeFormFieldBringForwardFunc(eFormFieldFuncValues);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Fiscal End Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal End Date");

		eFormField.addeFormField(true, false, false, false);

		eFormListColumn[0] = "Fiscal End Date";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		eFormFieldFuncValues = new String[4];

		eFormFieldFuncValues[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		eFormFieldFuncValues[1] = "Payment Submission End Date";
		eFormFieldFuncValues[2] = "";
		eFormFieldFuncValues[3] = "";

		eFormFieldFunc = new EFormFieldFunctions();

		eFormFieldFunc.addeFormFieldBringForwardFunc(eFormFieldFuncValues);

		// ////////////////programFormId = form.getEFormId();

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Expenses");

		formlet.setFormletMenuText("Fiscal Expenses");

		formlet.setFormletId("Fiscal Expenses");

		formlet.createFormlet(true, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Expenses Details");

		formlet.setFormletId("Fiscal Expenses Details");

		formlet.setParentFormletId(tempFormletId);

		formlet.createSubFormlet();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Fiscal Expenses Category:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Expenses Category");

		eFormField.addeFormField(true, true, false, false);

		strDropdownValues = new String[] { "EXPENSE", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormListColumn = new String[2];
		eFormListColumn[0] = "Fiscal Expenses Category";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Fiscal Expenses Sub-Category:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Expenses Sub-Category");

		eFormField.addeFormField(true, true, false, false);

		strDropdownValues = new String[] { "EXPENSE_SUB", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormListColumn[0] = "Fiscal Expenses Sub-Category";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		// ////////////////programFormId = form.getEFormId();

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Funding");

		formlet.setFormletMenuText("Fiscal Funding");

		formlet.setFormletId("Fiscal Funding");

		formlet.createFormlet(true, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Funding Contributor Details");

		formlet.setFormletId("Fiscal Funding Contributor Details");

		formlet.setParentFormletId(tempFormletId);

		formlet.createSubFormlet();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Fiscal Contributor Type:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Contributor Type");

		eFormField.addeFormField(true, true, false, false);

		strDropdownValues = new String[] { "CONTRIBUTOR_TYPE", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormListColumn = new String[2];
		eFormListColumn[0] = "Fiscal Contributor Type";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Fiscal Contributor Name:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Contributor Name");

		eFormField.addeFormField(true, false, false, false);

		eFormListColumn = new String[2];
		eFormListColumn[0] = "Fiscal Contributor Type";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Fiscal Contribution Type:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Contribution Type");

		eFormField.addeFormField(true, true, false, false);

		strDropdownValues = new String[] { "CONTRIBUTION_TYPE", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormListColumn[0] = "Fiscal Contribution Type";
		eFormListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eFormListColumn);

		// ////////////////////////
		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Fiscal Budget");

		formlet.setFormletMenuText("Fiscal Budget");

		formlet.setFormletId("Fiscal Budget");

		formlet.createFormlet(true, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_GridViewOFListFields_Name);

		eFormField.setEFormFieldLabel("Fiscal Budget Grid:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Fiscal Budget Grid");

		eFormField.addeFormField(true, true, false, false);

		eFormField.addGridViewOfListFiledsProperties(setGridViewProp());

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletId("Fiscal Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);

	}

	private Object[] setGridViewProp() throws Exception {

		Object[] obj = new Object[20];

		obj[0] = 2;
		obj[1] = "Fiscal Name";
		obj[2] = "Expenses";
		obj[3] = "Fiscal Expenses Category";
		obj[4] = "Expenses Sub-Category";
		obj[5] = "";
		obj[6] = "Funding Sources";
		obj[7] = "Fiscal Contributor Type";
		obj[8] = "Fiscal Contributor Name";
		obj[9] = "Fiscal Contribution Type";
		obj[10] = "Last Column";
		obj[11] = "Totals";
		obj[12] = true;
		obj[13] = "Totals";
		obj[14] = true;
		obj[15] = "Sub-Totals";
		obj[16] = true;
		obj[17] = "Sub-Sub-Totals";
		obj[18] = true;
		obj[19] = "Variance";
		return obj;
	}

	private void createPublicationForm() throws Exception {

		form = new EForm(IEFormsConst.programPublication_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.programPublication_FormTypeName);

		form.setEFormTitle("Program Publication");

		form.setEFormId("Publication Form");

		form.createEForm();

		publicationFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.programPublication_FormTypeName, preFix);

		formlet.setFormletTitleText("Publication Formlet");

		formlet.setFormletMenuText("Publication Formlet");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldId("Keywords");

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	private void createBasicProgramAdminForm() throws Exception {

		form = new EForm(IEFormsConst.progAdministration_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.progAdministration_FormTypeName);

		form.setEFormTitle("Basic Program Form");

		form.setEFormId("Basic Program Admin");

		form.createEForm();

		programFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.programPublication_FormTypeName, preFix);

		formlet.setFormletTitleText("Program Formlet");

		formlet.setFormletMenuText("Program Formlet");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_NumericField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);

	}

	private void createOrganizationForm() throws Exception {

		form = new EForm(IEFormsConst.organization_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.organization_FormTypeName);

		form.setEFormTitle("Organization Form");

		form.setEFormId("Organization Form");

		form.createEForm();

		publicationFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.programPublication_FormTypeName, preFix);

		formlet.setFormletTitleText("Organization Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);
	}

	private void createBFDataGridSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		form.setEFormTitle("Applicant Submission BF-DG");

		form.setEFormId("Submission BF-DG");

		form.createEForm();

		// ****** Menu *******//
		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_MenuItemOnly,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createMenuTypeFormlet();

		// ***** QH Data-Grid Field**********//
		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DataGridField_Name);

		eFormField.setEFormFieldLabel("Data-Grid");

		eFormField.setEFormFieldId("Num DG Field");

		tempEFormFieldId = eFormField.getEFormFieldId();

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, true, false, false);

		eFormField.configureDataGridEFormField("3", "4");

		eFormField
				.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll
						.ordinal() });

		eFormField.setDataGridCellType_NumericEntryCell();

		GeneralUtil.takeANap(2.0);

		ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		formlet.expandFormletNode();

		EFormFieldFunctions eFormFieldFunction = new EFormFieldFunctions(
				eFormField);

		String[] funcProp = new String[3];

		funcProp[0] = "D1";
		funcProp[1] = "(${" + eFormField.getEFormFieldId() + "!A1} + ${"
				+ eFormField.getEFormFieldId() + "!B1}) * ${"
				+ eFormField.getEFormFieldId() + "!C1}";
		funcProp[2] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedORdependentFieldsChanged
				.ordinal()];

		eFormFieldFunction.addEFormFieldCalcValueFunc(funcProp);

		funcProp = new String[3];

		funcProp[0] = "A5";
		funcProp[1] = "foreach (item in ${" + eFormField.getEFormFieldId()
				+ "!A1:A4}) { x=x+item; }";// "${" +
		// eFormField.getEFormFieldId() +
		// "!A1} + ${" +
		// eFormField.getEFormFieldId() +
		// "!B1} * ${" +
		// eFormField.getEFormFieldId() +
		// "!C1}";
		funcProp[2] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedORdependentFieldsChanged
				.ordinal()];

		eFormFieldFunction.addEFormFieldCalcValueFunc(funcProp);

		funcProp = new String[2];

		funcProp[0] = "A5";
		funcProp[1] = "true";

		eFormFieldFunction.addEFormFieldCalcReadOnlyFunc(funcProp);

		funcProp = new String[2];

		funcProp[0] = "D1";
		funcProp[1] = "true";

		eFormFieldFunction.addEFormFieldCalcReadOnlyFunc(funcProp);

		// *** Submission Summary *****//
		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(tempDefaultFormlet);

	}

	@SuppressWarnings("unused")
	private void createBFDataGridApprovalForm() throws Exception {

		form = new EForm(IEFormsConst.approval_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.projectApproval_FormTypeName);

		form.setEFormTitle("Approval BF-DG");

		form.setEFormId("Approval BF-DG");

		form.createEForm();

		formlet = new Formlet(
				form,
				IFormletsConst.formletTypeName_eFormQuestionHolderWithSubmissionList,
				IEFormsConst.projectApproval_FormTypeName, preFix);

		formlet.setFormletTitleText("Approval");

		formlet.setFormletMenuText("Approval");

		formlet.createFormlet(false, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_NumericField_Name);

		eFormField.setEFormFieldLabel("Total From A5:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("A5 Total");

		eFormField.addeFormField(true, false, false, false);

		// setup eFormField Functions
		eFormFieldFunc = new EFormFieldFunctions();

		strEFormFieldFuncProp = new String[4];

		strEFormFieldFuncProp[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		strEFormFieldFuncProp[1] = tempEFormFieldId;
		strEFormFieldFuncProp[2] = "A5";
		strEFormFieldFuncProp[3] = "";

		eFormFieldFunc.addeFormFieldBringForwardFunc(strEFormFieldFuncProp);

		eFormFieldFunc = new EFormFieldFunctions(eFormField);

		strEFormFieldFuncProp = new String[2];

		strEFormFieldFuncProp[0] = "";
		strEFormFieldFuncProp[1] = "true";

		eFormFieldFunc.addEFormFieldCalcReadOnlyFunc(strEFormFieldFuncProp);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_NumericField_Name);

		eFormField.setEFormFieldLabel("Total From D1:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("D1 Total");

		eFormField.addeFormField(true, false, false, false);

		// setup eFormField Function
		eFormFieldFunc = new EFormFieldFunctions(eFormField);

		strEFormFieldFuncProp = new String[4];

		strEFormFieldFuncProp[0] = IAlleFormsFunctionsConst.eFormFieldFunction_InvokeWhen_Values_Array[IAlleFormsFunctionsConst.EInvokeWhenValues.formOpenedfortheFirstTimeORwhenDependentFormsChanged
				.ordinal()];
		strEFormFieldFuncProp[1] = tempEFormFieldId;
		strEFormFieldFuncProp[2] = "D1";
		strEFormFieldFuncProp[3] = "";

		eFormFieldFunc.addeFormFieldBringForwardFunc(strEFormFieldFuncProp);

		eFormFieldFunc = new EFormFieldFunctions(eFormField);

		strEFormFieldFuncProp = new String[2];

		strEFormFieldFuncProp[0] = "";
		strEFormFieldFuncProp[1] = "true";

		eFormFieldFunc.addEFormFieldCalcReadOnlyFunc(strEFormFieldFuncProp);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(tempDefaultFormlet);
	}

}
