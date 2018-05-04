/*
 * VarForms.java
 *
 * Created on January 29, 2007, 8:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test_Suite.constants.eForms;

/**
 *
 * @author mshakshouki
 */
public interface IEFormsConst {
	
	/*
	 * July 26, 2007
	 * Starting new Constants for eForms
	 */
	
	//******** eForm Type Name ****************
	
	public static final String progAdministration_FormTypeName = "Administration";
	
	public static final String applicantWorkspace_FormTypeName = "Applicant Profile";
	
	public static final String approval_FormTypeName = "Approval";
	
	public static final String approval_FormTypeName2 = "Approval Form";
	
	public static final String award_FormTypeName = "Award";
	
	public static final String organization_FormTypeName = "Organization";
	
	public static final String programPublication_FormTypeName = "Publication";
	
	public static final String referenceTable_FormTypeName = "Reference Table";
	
	public static final String review_FormTypeName = "Review";
	
	public static final String submission_FormTypeName = "Submission";
	
	public static final String userProfile_FormTypeName = "User Profile";
	
	public static final String projectEForm_FormTypeName = "Project";
	
	//******** eForm Sub-Type ***************************
	
	public static final String applicantsubmission_FormTypeName = "Applicant Submission";
	
	public static final String assocAppSub_FormTypeName = "Associate Applicant Submission";
	
	public static final String fundingOpportunitySub_FormTypeName = "Funding Opportunity Submission";
	
	public static final String postAwardSubmission_FormTypeName = "Post Award Submission";
	
	public static final String progOfficeSubmission_FormTypeName = "Program Office Submission";
	
	public static final String fundingOppApproval_FormTypeName = "Funding Opportunity Approval";
	
	public static final String projectApproval_FormTypeName = "Project Approval";
	
	public static final String applicantAmendment_Ttl = "Applicant Amendment:";
	
	
	//********** eForm Sub-Type ********************************
	public static final String submission_eFormSub_TypeNames[] = {"Applicant Submission", "Funding Opportunity Submission", "Post Award Submission", "Program Office Submission"};
	
	public static final String approval_eFormSub_TypeNames[] = {"Funding Opportunity Approval", "Project Approval"};
	
	public enum submissionEFormSubTypeEnum
	{
		applicantSubmission,
		fundingOppSubmission,
		postAwardSubmission,
		programOfficeSubmission
	}
	
	public enum approvalEFormSubTypeEnum
	{
		fundingOppApproval,
		projectApproval
	}
	
	public static final String[] displayProjInfoValues = {"Never","While viewing the Default Formlet","While viewing any Formlet"};
	
	public enum EDisplayProjInfo {
		NEVER,
		DEFFORMLET,
		ANYFORMLET
	}
	
	/********* eForm Planner HTML fields tags, images(alt, src)*******************/
	public static final String CloneFormletImageAlt = "Add Copy of Existing Formlet";
	public static final String CloneFormletImageSrc = "/copy.gif/";	

	public static final String CloneFormlet_SourceEForm_Dropdown_Id = "/sourceForm/";
	public static final String CloneFormlet_SourceFormlet_Dropdown_Id = "/sourceFormlet/";
	
	//********** Form Details HTML Field Tags ID *******************
	
	public static final String formPlnnerFunctionsLinkTitles[] = {"Add Form Function","Add Formlet Function","Add EFormField Function"};
	
	public static final String formPlannerFormsAddLinksTitles[] = {"Add Formlet","Add EFormField","Add Sub-Formlet","Add Form Function","Add Formlet Function","Add EFormField Function"};
	
	public static final String formPlanner_Div_Id = "j_id_1v:planner"; //"main:formsPlanner:_idJsp31:planner";
	
	public static final String formIdentifier_TextField_Id = "/formIdentifier_rw/";
	
	public static final String formType_SelectList_Id = "/formgroup_rw/";
	
	public static final String formSubType_SelectList_Id = "/formtypeRewrite/";
	
	public static final String formDescription_TextField_Id = "/formdescription/";
	
	public static final String formDefaultFormlet_SelectList_Id = "/defaultFormlet_rw/";
	
	public static final String formTitle_TextField_Id = "formTitleText";
	
	public static final String formPrimaryOrg_DropdownField_Id = "/primaryOrganization/";
	
	public static final String formOrgAccess_DropdownField_Id = "/organizationAccess/";
	
	public static final String formDeisplyProjectInfo_DropdwonField_Id = "/renderTombstoneData/";
	
	
	//********** Org Hierarchy *************
	
	public static final String PrimaryOrgName = "SanityPrimaryOrg";
	
	
	//*********End of New Constants ***************************
	
	public static final String pdfFormIdent    = "Sanity-";
	public static final String pA_FormIdent    = "Post Award Submission";
	public static final String pdfFormType[]     = {"Application", "Program", "Applicant Workspace", "Agreement", "Review", "Approval", "Claim"};
	public static final String pdfFormDesc     = "Testing any Type of eForms";
	public static final String pdfFormDefault  = "";
	public static final String pdfFormTitle    = "Print-Form-";

	public static final String pA_FormTitle    = "Post Award Submission";   

	public static final String pdfFormletIdent  = "-Print-Formlet-Case-";
	public static final String pdfFormletType[]   = {"e.Form Question Holder", "e.Form List", "Submission Summary", "Menu Item Only",
		"e.Form Question Holder with Submission List", "Attachments List", "Submission Schedule List"};
	public static final int PdfMenuIndent[]    = {0, 1, 2, 3};
	public static final boolean PdfSS_Display[]   = {true, false};
	public static final String pdfTitleBarText  = "-Formlet-Print-";
	public static final String pdfMenuText      = "Formlet-PDF-";

	//#PDF Export
	public static final String pdfIncludeInExport[]  = {"If Formlet is visible to User", "Always","Never"};
	public static final String pdfPageBreak[]       = {"Before Formlet", "Full", "None"};
	public static final String pdfPageOrientation[]  = {"Portrait", "Landscape"};

	//#fForm Fields
	//#EForm Field Properties
	public static final String pdfEFieldIdent     = "-EField-Pdf-";
	//#TODO: Set up Array of Properties for each eField Type
	//#to make

	public static final String pdfEFieldsType[]  = {"PdfEFieldTypeBasic1", "PdfEFieldTypeBasic2", "PdfEFieldTypeNoProp",
		"PdfEFieldTypeEvaluate", "PdfEFieldTypeOneLookup",
		"PdfEFieldTypeDisplay", "PdfEFieldTypeTwoLookup"};

	public static final String pdfEFieldTypeBasic1[]     = {"Label Only Field", "Instructions Field"};
	public static final String pdfEFieldTypeBasic2[]     = {"Numeric Field", "Short Text Field", "Long Text Field", "Password Field"};
	public static final String pdfEFieldTypeNoProp[]     = {"Date Field", "Checkbox Field", "Email Address", "Web Address", "EIN Number"};
	public static final String pdfEFieldTypeEvaluate[]   = {"Review Score Field", "Approval Dropdown Field"};
	public static final String pdfEFieldTypeOneLookup[]  = {"Many-to-Many Field", "Dropdown Field", "Dropdown Form List Field"};
	public static final String pdfEFieldTypeDisplay[]    = {"Phone Number Field", "Postal Code Field"};
	public static final String pdfEFieldTypeTwoLookup[]  = {"Numeric Data-Grid Field", "Text Data-Grid Field", "Data-Grid Field"};

	public static final boolean Mandatory[]       = {true, false};
	public static final String LookupList      = "";
	public static final String LineSpacing[]     = {"0", "1", "2", "3", "4", "5"};
	public static final String QuestionLabel   = "Do Any Thing";
	public static final String EFieldTypeGrid  = "Numeric Data-Grid Field";
	public static final String XAxisLookup[] = {"Activity Types", "Countries", "Languages","Salutations", "Prefixes", "Suffixes",
		"Provinces", "Canadian Provinces", "US States", "Yes/No", "Document Types",
		"Document Formats", "Document Sizes", "Agreement Status"};

	public static final String YAxisLookup[]     = {"Activity Types", "Countries", "Languages","Salutations", "Prefixes", "Suffixes",
		"Provinces", "Canadian Provinces", "US States", "Yes/No", "Document Types",
		"Document Formats", "Document Sizes", "Agreement Status"};

	//#Arrays that depend on Field Types
	public static final String LableOnlyInstStyle[]  = {"Beside, Right-Aligned", "Beside, Left-Aligned", "Centered", "Centered, Highlighted"};
	public static final String LongTextStyle[]       = {"Below", "Beside"};
	public static final String ManyToManyStyle[]     = {"Horizontal", "Vertical"};
	public static final String DropdwonForceValue[]  = {"Yes", "No"};
	public static final String phoneNumberType[] = {"North American only", "Foreign only", "Both"};
	public static final String phoneNumberDisplay[]  = {"(xxx) xxx-xxxx", "xxx-xxx-xxxx", "xxx.xxx.xxxx"};
	public static final String postalCodeType[] = {"Canadian Postal Codes only", "American ZIP Codes only", "Both"};
	public static final String postalCanDisplay[] = {"X0X 0X0", "X0X-0X0"};
	public static final String postalAmDisplay[] = {"12345-1234", "12345"};
	public static final boolean RowColumnSumCheck[][]   = {{true, false}, {false, true}, {true, true}, {false, false}};

	//#Arrays that will help with Formlet FUNCTIONS
	public static final String FormletFuncType[] = {"Mandatory Field Validator", "Simple Number Comparison",
	"Visible if (other) List not empty"};

	public static final String FuncPropNumComp[] = {"EFormField 1 Value < EFormField 2 Value", "EFormField 1 Value <= EFormField 2 Value",
		"EFormField 1 Value = EFormField 2 Value", "EFormField 1 Value >= EFormField 2 Value",
	"EFormField 1 Value > EFormField 2 Value"};

	public static final String ListFuncType[] = {"List Count Validator", "Simple Number Comparison",
	"Visible if (other) List not empty"};

	public static final String AttachLstMaxDocSize[] = {"100 KB", "500 KB", "1 MB", "1.5 MB", "2 MB"};
	public static final String AttachLstDocFormat[] = {"doc", "jpg", "pdf", "xls", "zip", "rtf", "txt", "wpd", "mpp", "xml"};
	public static final String AttachDocType[] = {"MS Word", "Image", "Adobe", "MS Excel", "Compressed File", "Rich Text", "Text", "Wordperfect", "MS Power Point", "XML File"};

	//Arrays that will help with eFormField Function
	public static final String AddEFormFieldLinkIdStart = "main:formsPlanner:_id21:planner:0:";
	public static final String AddEFormFieldLinkIdEnd = ":_id53";

	public static final String EFieldFuncType[] = {"Bring Forward", "Numeric Sum", "Simple User Access Grant",
		"Calculated Value", "Calculated Visibility", "Calculated Read Only",
		"Calculated Bring Forward", "Calculated User Access Grant"};

	public static final String EFieldThisFieldValue[]  = {"Replace", "Include In Sum"};

	public static final String UarBeforeOrAfter[]    = {"Prior to Submission", "During Amendment", "After Submission", "Always"};

	public static final boolean UarRWApplicant[][]              = {{true, true}, {true, false}, {false, false}, {false, true}};
	public static final boolean UarRWAllStepStaff[][]           = {{true, true}, {true, false}, {false, false}, {false, true}};
	public static final boolean UarRWAssignStepProjOfficer[][]  = {{true, true}, {true, false}, {false, false}, {false, true}};
	public static final boolean UarRWAllAssignProjOfficer[][]   = {{true, true}, {true, false}, {false, false}, {false, true}};
	public static final boolean UarRWAllProjOfficer[][]         = {{true, true}, {true, false}, {false, false}, {false, true}};

	public static final String UarReadWriteValues[][][] = {{{"Applicant"}, {"true", "true"}, {"true", "false"}, {"false", "false"}, {"false", "true"}},
		{{"All Step Staff"}, {"true", "true"}, {"true", "false"}, {"false", "false"}, {"false", "true"}},
		{{"Assigned Step Project Officers"}, {"true", "true"}, {"true", "false"}, {"false", "false"}, {"false", "true"}},
		{{"All Assigned Project Officers"}, {"true", "true"}, {"true", "false"}, {"false", "false"}, {"false", "true"}},
		{{"All Project Officers"}, {"true", "true"}, {"true", "false"}, {"false", "false"}, {"false", "true"}}};

	public static final String UarRWAccessGroups[] = {"Read Access, Groups Only", "RW Access, Groups", "No RW Access, Groups"};


	/*********************************************
     *         Progam, Projects Org etc
     *********************************************/

	public static final String form_Prefix = "-eForm-";

	public static final String form_PA_Prefix = "-eForm-PA-";

	/****************************************************
    *             Program Steps
    ***************************************************/
	public static final String formSubFormA[][]  = {{"-Submission-eForm-A", "", "Submission", "false", "No"}, {pdfFormIdent}};
	public static final String formSubFormB[][]  = {{"-Submission-eForm-B", "", "Submission", "false", "No"}, {pdfFormIdent}};
	public static final String fromAward[][]     = {{"-Award-eForm", "", "Award", "false", "No"}, {"Standard Agreement"}};

	public static final String formReviewCrit[][]     = {{"-Review-eForm", "", "Review", "false", "No"},  {"Review Form", "Quorum", "1", "false"}};
	public static final String formReviewNone[][]     = {{"-Review-eForm", "", "Review", "false", "No"}, {"Review Form", "Quorum", "1", "false"}};
	public static final String formApprovCrit[][]     = {{"-Approval-eForm", "", "Approval", "false", "No"},  {"Approval Form", "Quorum", "1", "false"}};
	public static final String formApprovNone[][]     = {{"-Approval-eForm", "", "Approval", "false", "No"}, {"Approval Form", "Quorum", "1", "false"}};


	public static final String formPostAward[][] = {{"-Post-Award-eForm", "", "Post-Award", "false", "No"}, {fromAward[0][0]}};

	public static final String form_PA_Initial_Claim[][] = {{"-Initial-Claim-Step-eForm", "", "Initial Post Award Submission Step", "false", "No"}, {"Post Award Submission"} };
	public static final String form_PA_ReviewCrit[][]     = {{"-PA-Review-eForm", "", "Review", "false", "No"},  {"Review Form", "Quorum", "1", "false"}};
	public static final String form_PA_ReviewNone[][]     = {{"-PA-Review-eForm", "", "Review", "false", "No"}, {"Review Form", "Quorum", "1", "false"}};
	public static final String form_PA_ApprovCrit[][]     = {{"-PA-Approval-eForm", "", "Approval", "false", "No"},  {"Approval Form", "Quorum", "1", "false"}};  
	public static final String form_PA_ApprovNone[][]     = {{"-PA-Approval-eForm", "", "Approval", "false", "No"}, {"Approval Form", "Quorum", "1", "false"}};
	
	
	public static final String form_Publication_TextId = "g3-form:eFormFieldList:0:textArea_Below";
	
	public static final String budgetYear_SingleFilter_Id = "g3-form:eFormFieldList:0:textBox";
	
    public static final String expense_Type_SingleFilter_Dropdown_Id ="g3-form:eFormFieldList:1:numericDropdown";
    
    public static final String budget_Limit_SingleFilter_Id ="g3-form:eFormFieldList:2:textBox";
    
    public static final String dropDownTitle_ExpenseType = "Expense Type";
    
    public static final String dropDownTitle_ExpenseCategory = "Expense Category";

	public static final String reqChbx = "ui-helper-hidden-accessible";
    
    
	
	
	
	

    
}
