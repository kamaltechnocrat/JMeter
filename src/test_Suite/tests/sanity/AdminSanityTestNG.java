/**
 * 
 */
package test_Suite.tests.sanity;

/**
 * @author mshakshouki
 * 
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.preTest.*;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.eForms.IAlleFormsFunctionsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.lib.workflow.*;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.users.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.cases.*;

@GUITest
@Test(singleThreaded = true)
public class AdminSanityTestNG {

	private static Log log = LogFactory.getLog(AdminSanityTestNG.class);

	// *********** Variables Declaration Section ********************

	boolean newProgram = true;

	boolean programForm = true;

	boolean publicationForm = true;

	boolean newOrg = true;

	String preFix = "";

	String postFix = "";

	char portaltype = 'P';

	String progAdmin[] = { IPreTestConst.adminGroup };

	String progOfficers[] = { IPreTestConst.Groups[12][0][0] };

	String stepOfficers[] = { IPreTestConst.Groups[12][0][0],
			IPreTestConst.Groups[12][1][0] };

	String applicantFormId = null;

	String SubmissionFormId = null;

	String reviewFormId = null;

	String approvalFormId = null;

	String basicAgreementFormId = null;

	String standradAgreementFormId = null;

	String postAwardSubFormId = null;

	String programFormId = null;

	String publicationFormId = null;

	Integer userBeat = 1;

	Integer startIndex = 1;

	Integer endIndex = 1;

	boolean retValue;

	EForm form;

	Formlet formlet;

	EFormField eFormField;

	Users user;

	FOUsers foUser;

	Program sanityProg;

	Project proj;

	FOProject foProj;

	Object[] wizardObj;

	static String projFOLetter = null;

	// ----------- End of Variables Declaration ---------------------

	@BeforeClass
	public void setUp() {

		// code that will be invoked when this test is instantiated
	}

	@Test(groups = { "SanityNG" })
	public void adminSanityTestNG() throws Exception {

		try {

			log.info("Opening New Window and Login as Admin");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			// GeneralUtil.loginSanity("1");
			// GeneralUtil.logInSuper();

			/*******************************************************************
			 * Create Applicant Profile Form
			 ******************************************************************/
			createApplicantForm();

			// ************Setting Applicant Workspace ***********************

			GeneralUtil.setApplicantWorkspace();

			log.debug("set applicant workspace OK");

			// For Regression Test purpose adding more License Key
			log.info("Setting up Lincese Key for 1000 users");
			AuthenUtil
					.changeLicenseKey(IConfigConst.licenseKey_1000_Users);

			// -------------------------------------------------------------

			/*******************************************************************
			 * Creating Users and Applicants
			 ******************************************************************/
			initializeUsersAndApplicants();

			createPOSanityUser();

			createFOSanityApplicant();

			/*******************************************************************
			 * Creating Forms to be used in the Program planning
			 ******************************************************************/

			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginPO();

			createFundingOppApprovalForm();
			createFundingOppSubmissionForm();

			createApplicationForm();
			createReviewForm();
			createApprovalForm();
			createBasicAwardForm();
			createStandardAwardForm();
			createPostAwardSubmissionForm();

			createGPSProgramForm();
			createBasicProgramAdminForm();
			createPublicationForm();

			// --------- End of Forms ----------------------

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			// GeneralUtil.loginPO();
			GeneralUtil.loginSanity("1");

			/*******************************************************************
			 * Create a Program
			 ******************************************************************/
			// Do not Comment out the InitializeProgram()
			initializeProgram();

			createProgram();

			// --------- End of Program -----------------

			/*******************************************************************
			 * Create Projects
			 ******************************************************************/
			GeneralUtil.switchToFOOnly();

			getOrgLetter();

			initializeObject();

			GeneralUtil.switchToPOOnly();

			createPOProject();

			GeneralUtil.switchToFOOnly();

			registerAndSubmitThroughWizard();

			createFOProject();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} finally {
			
			form = null;
			formlet = null;
			eFormField = null;
			user = null;
			foUser = null;
			sanityProg = null;
			proj = null;
			foProj = null;
			wizardObj = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}

	private void createApplicantForm() throws Exception {

		form = new EForm(IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantWorkspace_FormTypeName);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		form.setEFormTitle("Sanity Applicant Workspace");

		formlet.setFormletTitleText("Sanity QH");

		formlet.setFormletMenuText("Sanity QH");

		form.createEForm();

		applicantFormId = form.getEFormId();

		formlet.createFormlet(false, true);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Name:");

		eFormField.setEFormFieldDescription("Sanity Description");

		eFormField.setEFormFieldTooltip("Sanity Tooltip");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

	}

	private void createPOSanityUser() throws Exception {

		user.createUser();
		user.createGroup();
		user.addUsersToGroup();

		user.addGroupsToGroup(new String[] { user.getGroup() }, "Super");
	}

	private void initializeUsersAndApplicants() throws Exception {
		user = new Users(2, 0, "Users");

		foUser = new FOUsers();

		foUser.setFoUserBeat(userBeat);

		foUser.setStartIndex(startIndex);
		foUser.setEndIndex(endIndex);

		foUser.setFoApplicants(ISanityConst.sanityFOuser[0]);

		foUser.setFoRegistrants(ISanityConst.sanityFOuser[1]);

		foUser.setFoOrgName(ISanityConst.santFront_OrgShortName);

		foUser.setFoRegistrantFullId("/" + foUser.getFoRegistrants()[2]
				+ foUser.getFoUserBeat() + "/");

	}

	private void createFOSanityApplicant() throws Exception {

		GeneralUtil.setFOConfigOption(true, true);

		// ************* Switch to Front Office Portal ***********************
		log.debug("logging off");
		GeneralUtil.switchToFOOnly();
		log.debug("nav'd to FO");

		foUser.createProfiles();

		foUser.createFOApplicant();
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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

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

		form.publishForm(formlet.getDefaultFormlet());

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

	}

	private void createApplicationForm() throws Exception {

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

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

		form.publishForm(formlet.getDefaultFormlet());

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
				IEFormsConst.review_FormTypeName, preFix);

		formlet.setFormletTitleText("Review");

		formlet.setFormletMenuText("Review");

		formlet.createFormlet(false, true);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DropdownField_Name);

		eFormField.setEFormFieldLabel("Requirement Fulfilled::");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, true, false, false);

		String strDropdownValues[] = { "Yes/No", "No" };

		eFormField.addDropdwonFieldProperties(strDropdownValues);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ReviewScoreField_Name);

		eFormField.setEFormFieldLabel("Score:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, true, false, false);

		Object[] obj = new Object[5];

		obj[0] = 0.0;
		obj[1] = 10.0;
		obj[2] = 0;
		obj[3] = true;
		obj[4] = 50.0;

		eFormField.addReviewScoreFieldProperties(obj);

		form.publishForm(formlet.getDefaultFormlet());

	}

	private void createApprovalForm() throws Exception {

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name);

		eFormField.setEFormFieldLabel("Status:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.setEFormFieldId("Approved");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Date Completed:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldLabel("Comments:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Text:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Text:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.award_FormTypeName, preFix);

		formlet.setFormletTitleText("PA Submission Schedule");

		formlet.setFormletMenuText("PA Submission Schedule");

		formlet.createFormlet(true, false);

		form.publishForm(formlet.getDefaultFormlet());

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("Description");

		eFormField.setEFormFieldTooltip("Tooltip");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletId("Submission Summary");

		formlet.createSubmissionSummaryTypeFormlet();

		form.publishForm(formlet.getDefaultFormlet());

	}

	private void createGPSProgramForm() throws Exception {

		form = new EForm(IEFormsConst.progAdministration_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.progAdministration_FormTypeName);

		form.setEFormTitle("GPS Program Form");

		form.setEFormId("GPS Program Form");

		form.createEForm();

		programFormId = form.getEFormId();

		formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.progAdministration_FormTypeName, preFix);

		formlet.setFormletTitleText("Payment Schedule");

		formlet.setFormletMenuText("Payment Schedule");

		formlet.setFormletId("Payment Schedule");

		formlet.createFormlet(true, true);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.progAdministration_FormTypeName, preFix);

		formlet.setFormletTitleText("Payment Schedule Details");

		formlet.setFormletId("Payment Schedule Details");

		formlet.createSubFormlet();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Payment Submission Name:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Payment Submission Name");

		eFormField.addeFormField(true, false, false, false);

		String[] eForkListColumn = new String[2];

		eForkListColumn[0] = "Submission Name";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Payment Submission Start Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Payment Submission Start Date");

		eFormField.addeFormField(true, false, false, false);

		eForkListColumn = new String[2];

		eForkListColumn[0] = "Start Date";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_DateField_Name);

		eFormField.setEFormFieldLabel("Payment Submission End Date:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.setEFormFieldId("Payment Submission End Date");

		eFormField.addeFormField(true, false, false, false);

		eForkListColumn = new String[2];

		eForkListColumn[0] = "End Date";
		eForkListColumn[1] = "---";

		eFormField.addPropertiesForEFormList(eForkListColumn);

		form.publishForm(formlet.getDefaultFormlet());
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
				IEFormsConst.progAdministration_FormTypeName, preFix);

		formlet.setFormletTitleText("Program Formlet");

		formlet.setFormletMenuText("Program Formlet");

		formlet.createFormlet(false, true);

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_NumericField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

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

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_LongTextField_Name);

		eFormField.setEFormFieldId("Keywords");

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		form.publishForm(formlet.getDefaultFormlet());

	}

	private void initializeProgram() throws Exception {
		preFix = "-Sant-";

		sanityProg = new Program(preFix, portaltype, programForm, newProgram,
				publicationForm);

		sanityProg.setProgPostfix(postFix);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		sanityProg.initProgram();

		sanityProg.setProgramOfficer(ISanityConst.santProgPOfficer);

		sanityProg.setStepOfficer(progOfficers[0]);

		sanityProg.setProgramFormName(ISanityConst.sanity_ProgForm_Name);

		sanityProg
				.setPublicationFormName(ISanityConst.sanity_PublicationForm_Name);

	}

	private void createProgram() throws Exception {

		sanityProg.setProgAdmin(progAdmin);
		sanityProg.setProgOfficers(progOfficers);

		sanityProg.createProgram();

		sanityProg.managePublicantionForm();
		sanityProg.manageBasicFundingOppForm();

		sanityProg.addStep(ISanityConst.sanity_SubmissionA);

		sanityProg.manageStep(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_SubmissionA[0][0] } });

		sanityProg.addStep(ISanityConst.sanity_SubmissionB);

		sanityProg.manageStep(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_SubmissionB[0][0] } });

		sanityProg
				.manageBFstep(new String[] { ISanityConst.sanity_SubmissionA[0][0] });

		sanityProg.addStep(ISanityConst.sanity_Approv);
		sanityProg.manageStep(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_Approv[0][0] } });

		sanityProg.addStep(ISanityConst.sanity_PA_Award);
		sanityProg.manageStep(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_PA_Award[0][0] } });

		sanityProg.addPAStep(ISanityConst.sanity_PostAward);
		sanityProg.manageStep(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_PostAward[0][0] } });

		sanityProg.addSubSteps(ISanityConst.sanity_InitialClaimA);
		sanityProg.manageSubSteps(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_InitialClaimA[0][0] } });

		sanityProg.addSubSteps(ISanityConst.sanity_PA_Review);
		sanityProg.manageSubSteps(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_PA_Review[0][0] } });

		sanityProg.addSubSteps(ISanityConst.sanity_PA_Approv);
		sanityProg.manageSubSteps(new String[][] { { progOfficers[0] },
				{ ISanityConst.sanity_PA_Approv[0][0] } });

		sanityProg.activateProgram("Active");

	}

	private void createPOProject() throws Exception {

		GeneralUtil.loginSanity("1");

		proj.createPOProject(newOrg);
		proj.submitProject(true);

		proj
				.assignOfficers(new String[][] { { stepOfficers[0],
						stepOfficers[1] } });

		proj.submitFromApplicantSubList(ISanityConst.sanity_SubmissionB[0][0],
				true);

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginSanity("2");

		Assert.assertTrue(proj.approveSubmission(ISanityConst.sanity_Approv[0][0], true, "Ready",
				false, false));
		
		GeneralUtil.Logoff();

		GeneralUtil.logBack();
		GeneralUtil.loginSanity("1");

		Assert.assertTrue(proj.submitAward("Standard", 2, true),
				"Failed To Submit Award");

		proj.setClaimNumber(2);

		proj.submitFromApplicantSubList(
				ISanityConst.sanity_InitialClaimA[0][0], true);

	}

	private void createFOProject() throws Exception {

		foProj = new FOProject(sanityProg);

		foProj.setFoOrgName(ISanityConst.santFront_OrgShortName + "1");

		GeneralUtil.switchToFOOnly();
		GeneralUtil.loginSanityFO("");

		ClicksUtil.clickLinks(IClicksConst.openWorkspaceLnk);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(sanityProg
				.getProgFullName(), foProj.getFoOrgName()), "No "
				+ foProj.getFoOrgName());

		foProj.createFOProject();

		Assert.assertTrue(foProj.submitFOProject(
				ISanityConst.sanity_SubmissionA[0][0], true),
				"Fail: Could not Find Form In FO Submission List!");

		Assert.assertTrue(foProj.submitAmendedFOProject(
				ISanityConst.sanity_SubmissionB[0][0], true),
				"Fail: Could not Find Form In FO Submission List!");

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginSanity("1");

		foProj.setOrgFullName(ISanityConst.santFront_OrgShortName + "1");

		foProj.assignOfficers(new String[][] { { stepOfficers[0],
				stepOfficers[1] } });

	}

	private void getOrgLetter() throws Exception {

		GeneralUtil.loginSanityFO("");
		ClicksUtil.clickLinks(IClicksConst.openFOProjectListLnk);

		projFOLetter = GeneralUtil.FindNewBaseLetter(preFix
				+ IGeneralConst.gnrl_FO_ProjName + postFix);

	}

	private void initializeObject() throws Exception {
		proj = new Project(sanityProg, true);

		foProj = new FOProject(sanityProg);

		foProj.setFoOrgName(ISanityConst.santFront_OrgShortName + "1");

		wizardObj = new Object[6];

		wizardObj[0] = sanityProg.getProgFullName();
		wizardObj[1] = projFOLetter + preFix + IGeneralConst.gnrl_FO_ProjName
				+ postFix;
		wizardObj[2] = (Boolean) false; // Do you want to create a new profile?
		wizardObj[3] = (Boolean) false; // Do you want to Create new
		// Organization?
		wizardObj[4] = 1; // Org and Registrant sequence
		wizardObj[5] = (Boolean) false; // Do you want to Fill Applicant
		// workspace?

		foProj.setProjFOFullName(wizardObj[1].toString());
	}

	private void registerAndSubmitThroughWizard() throws Exception {

		FrontOfficeUtil.searchForProgram(IGeneralConst.wizardsWords[6]);

		Assert.assertTrue(FrontOfficeUtil.registerThroughWizard(wizardObj,
				foUser), "Fail: To Register through the Wizard");

		Assert.assertTrue(foProj.submitAmendedFOProject(
				ISanityConst.sanity_SubmissionB[0][0], true),
				"Fail: Could not Find Form In FO Submission List!");

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginSanity("1");

		foProj.setProjFullName(foProj.getProjFOFullName());
		foProj.setOrgFullName(foProj.getFoOrgName());

		foProj.assignOfficers(new String[][] { { stepOfficers[0],
				stepOfficers[1] } });
	}

}
