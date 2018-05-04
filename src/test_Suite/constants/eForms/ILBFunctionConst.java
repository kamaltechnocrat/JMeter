/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 * 
 */
public interface ILBFunctionConst {

	/**** FOPP preFixes *********/

	public static final String lbf_Fopp_name = "FOPP";
	public static final String lbf_FoppPreFix = "-LBF-";
	public static final String lbf_PA_Fopp_PreFix = "-LBF-PA-";
	public static final String lbf_FOPP_PostFix = "eLists";
	public static final String lbf_Proj_Prefix = "LBF.";

	/********* LBF Steps Arrays *****************/
	
	public static final String[] userProfileEForm = {
		"User Profile", "User Profile eList", "User-Profile-eList"};

	public static final String[] lbf_ProfileEFormSource = {
			"Applicant Profile", "LBF Applicant Profile Source eLists",
			"LBF-Applicant Profile-Source-eLists" };

	public static final String[] lbf_AdminEFormSource = { "Administration",
			"LBF Administration Source eLists",
			"LBF-Administration-Source-eLists" };

	public static final String[][] lbf_SubmissionFilteringELists = {
			{ "Submission", "LBF Applicant Submission Filtering eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-Filtering-eLists",
					"LBF Applicant Submission Filtering eLists" } };

	public static final String[][] lbf_SubmissionNoSyncELists = {
			{ "Submission", "LBF Applicant Submission NoSync eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-NoSync-eLists",
					"LBF Applicant Submission NoSync eLists" } };

	public static final String[][] lbf_SubmissionEqualELists = {
			{ "Submission", "LBF Applicant Submission Equal eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-Equal-eLists",
					"LBF Applicant Submission Equal eLists" } };

	public static final String[][] lbf_SubmissionLessAttachments = {
			{ "Submission", "LBF Applicant Submission Less Attachments Lists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-Less-Attachments-Lists",
					"LBF Applicant Submission Less Attachments Lists" } };

	public static final String[][] lbf_SubmissionMoreElists = {
			{ "Submission", "LBF Applicant Submission More eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-More-eLists",
					"LBF Applicant Submission More eLists" } };

	public static final String[][] lbf_SubmissionSource = {
			{ "Submission", "LBF Applicant Submission Source eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-Source-eLists",
					"LBF Applicant Submission Source eLists" } };

	public static final String[][] lbf_POSubmissionFilteringElists = {
			{ "PO Submission", "LBF PO Submission Filtering eLists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-Filtering-eLists",
					"LBF PO Submission Filtering eLists" } };

	public static final String[][] lbf_POSubmissionNoSyncElists = {
			{ "PO Submission", "LBF PO Submission NoSync eLists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-NoSync-eLists",
					"LBF PO Submission NoSync eLists" } };

	public static final String[][] lbf_POSubmissionEqualSchedules = {
			{ "PO Submission", "LBF PO Submission Equal Schedules Lists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-Equal-Schedules-Lists",
					"LBF PO Submission Equal Schedules Lists" } };

	public static final String[][] lbf_POSubmissionLessELists = {
			{ "PO Submission", "LBF PO Submission Less eLists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-Less-eLists", "LBF PO Submission Less eLists" } };

	public static final String[][] lbf_POSubmissionMoreAttachments = {
			{ "PO Submission", "LBF PO Submission More Attachments Lists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-More-Attachments-Lists",
					"LBF PO Submission More Attachments Lists" } };

	public static final String[][] lbf_POSubmissionSource = {
			{ "PO Submission", "LBF PO Submission Source eLists",
					"Program Office Submission", "true", "Yes" },
			{ "LBF-PO Submission-Source-eLists",
					"LBF PO Submission Source eLists" } };

	public static final String[][] lbf_AwardEqualAttachments = {
			{ "Award", "LBF Award Equal Attachments Lists", "Award", "true",
					"Yes" },
			{ "LBF-Award-Equal-Attachments-Lists",
					"LBF Award Equal Attachments Lists" } };

	public static final String[][] lbf_AwardLessAttachments = {
			{ "Award", "LBF Award Less Attachments Lists", "Award", "true",
					"Yes" },
			{ "LBF-Award-Less-Attachments-Lists",
					"LBF Award Less Attachments Lists" } };

	public static final String[][] lbf_AwardMoreElists = {
			{ "Award", "LBF Award More eLists", "Award", "true", "Yes" },
			{ "LBF-Award-More-eLists", "LBF Award More eLists" } };

	public static final String[][] lbf_AwardSource = {
			{ "Award", "LBF Award Source eLists", "Award", "true", "Yes" },
			{ "LBF-Award-Source-eLists", "LBF Award Source eLists" } };

	public static final String[][] lbf_StandardAwardFilteringELists = {
			{ "Standard Agreement", "LBF Standard Agreement Filtering eLists",
					"Award", "true", "Yes" },
			{ "LBF-Standard Agreement-Filtering-eLists",
					"LBF Standard Agreement Filtering eLists" } };

	public static final String[][] lbf_StandardAwardNoSyncELists = {
			{ "Standard Agreement", "LBF Standard Agreement NoSync eLists",
					"Award", "true", "Yes" },
			{ "LBF-Standard Agreement-NoSync-eLists",
					"LBF Standard Agreement NoSync eLists" } };

	public static final String[][] lbf_StandardAwardEqualELists = {
			{ "Standard Agreement", "LBF Standard Agreement Equal eLists",
					"Award", "true", "Yes" },
			{ "LBF-Standard Agreement-Equal-eLists",
					"LBF Standard Agreement Equal eLists" } };

	public static final String[][] lbf_StandardAwardLessElists = {
			{ "Standard Agreement",
					"LBF Standard Agreement Less eLists", "Award",
					"true", "Yes" },
			{ "LBF-Standard Agreement-Less-eLists",
					"LBF Standard Agreement Less eLists" } };

	public static final String[][] lbf_StandardAwardMoreSchedules = {
			{ "Standard Agreement",
					"LBF Standard Agreement More Schedules Lists", "Award",
					"true", "Yes" },
			{ "LBF-Standard Agreement-More-Schedules-Lists",
					"LBF Standard Agreement More Schedules Lists" } };

	public static final String[][] lbf_StandardAwardSource = {
			{ "Standard Agreement", "LBF Standard Agreement Source eLists",
					"Award", "true", "Yes" },
			{ "LBF-Standard Agreement-Source-eLists",
					"LBF Standard Agreement Source eLists" } };

	public static final String[][] lbf_IPASEqualElists = {
		{ "Initial-Claim", "LBF Post Award Submission Equal eLists","Post Award Submission", "true", "Yes" },
		{ "LBF-Post Award Submission-Equal-eLists",	"LBF Post Award Submission Equal eLists" } };

	public static final String[][] lbf_IPASFilteringElists = {
			{ "Initial-Claim", "LBF Post Award Submission Filtering eLists","Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-Filtering-eLists",	"LBF Post Award Submission Filtering eLists" } };

	public static final String[][] lbf_IPASNoSyncElists = {
			{ "Initial-Claim",
					"LBF Post Award Submission NoSync eLists",
					"Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-NoSync-eLists",
					"LBF Post Award Submission NoSync eLists" } };

	public static final String[][] lbf_IPASEqualAttachments = {
			{ "Initial-Claim",
					"LBF Post Award Submission Equal Attachments Lists",
					"Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-Equal-Attachments-Lists",
					"LBF Post Award Submission Equal Attachments Lists" } };

	public static final String[][] lbf_IPASLessAttachments = {
			{ "Initial-Claim", "LBF Post Award Submission Less Attachments Lists",
					"Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-Less-Attachments-Lists",
					"LBF Post Award Submission Less Attachments Lists" } };

	public static final String[][] lbf_IPASMoreElists = {
			{ "Initial-Claim", "LBF Post Award Submission More eLists",
					"Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-More-eLists",
					"LBF Post Award Submission More eLists" } };

	public static final String[][] lbf_IPASSource = {
			{ "Initial-Claim",
					"LBF Post Award Submission Source eLists",
					"Post Award Submission", "true", "Yes" },
			{ "LBF-Post Award Submission-Source-eLists",
					"LBF Post Award Submission Source eLists" } };

	public static enum EDocumentsFormat {
		doc, jpg, mpp, pdf, rtf, txt, wpd, xls, zip, xml, docx, xlsx, any
	}

	public static final String[][] lbf_attachments_Fields_eList = {
			{ "Word", "1 MB", "How to..", "Instruction", "doc", "Word.doc" },

			{ "Picture", "1 MB", "How to..", "Instruction", "jpg",
					"Picture.jpg" },

			{ "Power-Point", "1 MB", "How to..", "Instruction", "mpp",
					"Power-Point.mpp" },

			{ "Adobe", "1 MB", "How to..", "Instruction", "pdf", "Adobe.pdf" },

			{ "Rich-Text", "1 MB", "How to..", "Instruction", "rtf",
					"Rich-Text.rtf" },

			{ "Plain-Text", "1 MB", "How to..", "Instruction", "txt",
					"Plain-Text.txt" },

			{ "Corel", "1 MB", "How to..", "Instruction", "wpd", "Corel.wpd" },

			{ "Excel", "1 MB", "How to..", "Instruction", "xls", "Excel.xls" },
			{ "Compressed-File", "1 MB", "How to..", "Instruction", "zip",
					"Compressed-File.zip" },
			{ "Markup-Language", "1 MB", "How to..", "Instruction", "xml",
					"Markup-Language.xml" },
			{ "Word-07", "1 MB", "How to..", "Instruction", "docx",
					"Word-07.docx" },
			{ "Excel-07", "1 MB", "How to..", "Instruction", "xlsx",
					"Excel-07.xlsx" },
			{ "Any-Format", "1 MB", "How to..", "Instruction", ".*",
					"Any-Format.rb" } };

	public static final String[] attachmentsParameters_doc = { "Word", "1 MB",
			"Word", "Instruction", "doc", "Word.doc" };

	public static final String[] attachmentsParameters_jpg = { "Picture",
			"1 MB", "Picture", "Instruction", "jpg", "Picture.jpg" };

	public static final String[] attachmentsParameters_mpp = { "Power-Point",
			"1 MB", "Power-Point", "Instruction", "mpp", "Power-Point.mpp" };

	public static final String[] attachmentsParameters_pdf = { "Adobe", "1 MB",
			"Adobe", "Instruction", "pdf", "Adobe.pdf" };

	public static final String[] attachmentsParameters_rtf = { "Rich-Text",
			"1 MB", "Rich-Text", "Instruction", "rtf", "Rich-Text.rtf" };

	public static final String[] attachmentsParameters_txt = { "Plain-Text",
			"1 MB", "Plain-Text", "Instruction", "txt", "Plain-Text.txt" };

	public static final String[] attachmentsParameters_wpd = { "Corel", "1 MB",
			"Corel", "Instruction", "wpd", "Corel.wpd" };

	public static final String[] attachmentsParameters_xls = { "Excel", "1 MB",
			"Excel", "Instruction", "xls", "Excel.xls" };

	public static final String[] attachmentsParameters_zip = {
			"Compressed-File", "1 MB", "Compressed-File", "Instruction", "zip",
			"Compressed-File.zip" };

	public static final String[] attachmentsParameters_xml = {
			"Markup-Language", "1 MB", "Markup-Language", "Instruction", "xml",
			"Markup-Language.xml" };

	public static final String[] attachmentsParameters_docx = { "Word-07",
			"1 MB", "Word-07", "Instruction", "docx", "Word-07.docx" };

	public static final String[] attachmentsParameters_xlsx = { "Excel-07",
			"1 MB", "Excel-07", "Instruction", "xlsx", "Excel-07.xlsx" };

	public static final String[] attachmentsParameters_any = { "Any-Format",
			"1 MB", "Any-Format", "Instruction", ".*", "Any-Format.rb" };

	public static enum ESyncTypes {
		equal, more, less, source, noSync, filtering
	}

	public static final String[] postFixIdentifier = { "-Equal-eLists",
			"-More-eLists", "-Less-eLists", "-Source-eLists", "-NoSync-eLists", "-Filtering-eLists" };

	public static final String[] postFixTitle = { " Equal eLists",
			" More eLists", " Less eLists", " Source eLists", " NoSync eLists", " Filtering eLists" };

	public static final String[] postFixIdentAttachments = {
			"-Equal-Attachments", "-More-Attachments", "-Less-Attachments",
			"-Source-Attachments" };

	public static final String[] postFixTitleAttachments = {
			" Equal Attachments", "More Attachments", " Less Attachments",
			" Source Attachments" };

	public static final String[] postFixIdentSchedules = { "-Equal-Schedules",
			"-More-Schedules", "-Less-Schedules", "-Source-Schedules" };

	public static final String[] postFixTitleSchedules = { " Equal Schedules",
			"More Schedules", " Less Schedules", " Source Schedules" };

	public static final String formleTitlePostFix[] = { " Equal Fields LBF",
			" More Fields LBF", " Less Fields LBF", " Source Fields LBF" };

	public static final String formleIdentPostFix[] = { "-Equal-Fields-LBF",
			"-More-Fields-LBF", "-Less-Fields-LBF", "-Source-Fields-LBF" };

	public static final String subFormleTitlePostFix[] = {
			" Sub Equal Fields LBF", " Sub More Fields LBF",
			" Sub Less Fields LBF", " Sub Source Fields LBF" };

	public static final String subFormleIdentPostFix[] = {
			"-Sub-Equal-Fields-LBF", "-Sub-More-Fields-LBF",
			"-Sub-Less-Fields-LBF", "-Sub-Source-Fields-LBF" };
	
	public static final String[][] applicantSubmissionFields = { 
		{"$1,000.00", "$2,000.00"}
			
		};
	
	public static final String formletTitlePostFix = " Fields LBF";
	
	public static final String formletIdentPostFix = "-Fields-LBF";
	
	public static final String subFormletTitlePostFix = " Sub Fields LBF";
	
	public static final String subFormletIdentPostFix = "-Sub-Fields-LBF";

	public static final String preFix = "LBF-";
	public static final String postFix = "";

	public static enum EfieldDataTypes {
		formlet, stringTP, dateTP, formTP, booleanTP, numericTP
	}

	public static enum EeFormsIdentifier {
		admin, profile, submission, poSubmission, award, standardAgreement, claim, userProfile,org, proj
	}

	public static enum EFormletTypes {
		noProp, typeProp, minMaxProp, dGrid, attchments, subSchedule, subSummary
	}
	
	public static enum EFormletMainTypes {
		elists, attchments, subSchedule, subSummary
	}

	public static final String[] formletTypes = { "No Propreties",
			"Type Propreties", "Min And Max Propreties",
			"Data Grid", "Attachment List", "PA Submission Schedule",
			"Submission Summary" };

	/********* Data for eLists ****************/
	public static final String[][] lbf_NoProperties_Fields_eList = {
			{ "Approved", "true", "Date", "11-1111111", "true@grantium.com",
					"http://www.grantium.com" },
			{ "Rejected", "true", "Date", "22-2222222", "false@grantium.com",
					"http://www.grantium.ca" },
			{ "Pending Approval", "true", "Date", "33-3333333", "good@grantium.com",
					"http://www.grantium.biz" },
			{ "Rejected", "true", "Date", "44-4444444", "NoMail@grantium.com",
					"http://www.grantium.org" } };
	
	public static final String[][] lbf_NoProperties_Fields_eList_Amended = {
		
		{ "Rejected", "true", "Date", "11-1111111", "abcd@grantium.com",
				"http://www.yahoo.com" },
		{ "Pending Approval", "true", "Date", "22-2222222", "efgh@grantium.com",
				"http://www.hotmail.com" },
		{ "Approved", "true", "Date", "33-3333333", "good@grantium.com",
			   "http://www.grantium.biz" },
		{ "Rejected", "true", "Date", "44-4444444", "NoMail@grantium.com",
		"http://www.grantium.org" } };

	public static final String[][] lbf_TypeProperties_Fields_eList = {
			{ "001111 2307890", "A1A-1A1" }, { "002222 2307890", "B2B-2B2" },
			{ "003333 2307890", "C3C-3C3" }, { "003333 2307890", "D4D-4D4" } };

	public static final String[][] lbf_TypeProperties_Fields_eList_Amended = {
		{ "001111 2222222", "A1A-1A1" }, { "002222 3333333", "B2B-2B2" },
		{ "003333 2307890", "C3C-3C3" }, { "003333 2307890", "D4D-4D4" } };
	
	
	public static final String[][] lbf_MinMaxProperties_Fields_eList = {
			{ "A", "4000", "abcdefghi", "a", "10" },
			{ "B", "3000", "abcdefghi", "b", "9" },
			{ "C", "2000", "abcdefghi", "c", "8" },
			{ "D", "1000", "abcdefghi", "d", "7" } };
	
	public static final String[][] lbf_MinMaxProperties_Fields_eList_Amended = {
		{ "G", "4000", "abcdefghi", "a", "5" },
		{ "H", "3000", "abcdefghi", "b", "6" },
		{ "C", "2000", "abcdefghi", "c", "8" },
		{ "D", "1000", "abcdefghi", "d", "7" } };


	public static final String[][] lbf_DataGrid_Fields_eList = {
			{ "1", "A", "Alberta" }, { "2", "B", "Ontario" },
			{ "3", "C", "Nova Scotia" }, { "4", "D", "Yukon" } };
	
	public static final String[][] lbf_DataGrid_Fields_eList_Amended = {
		{ "5", "A", "Quebec" }, { "4", "B", "Ontario" },
		{ "3", "C", "Nova Scotia" }, { "4", "D", "Yukon" } };

	public static final String[][] lbf_Attachments_eList = {
			{ attachmentsParameters_doc[5] }, { attachmentsParameters_jpg[5] },
			{ attachmentsParameters_mpp[5] }, { attachmentsParameters_pdf[5] },
			{ attachmentsParameters_rtf[5] }, { attachmentsParameters_txt[5] },
			{ attachmentsParameters_wpd[5] }, { attachmentsParameters_xls[5] },
			{ attachmentsParameters_zip[5] }, { attachmentsParameters_xml[5] },
			{ attachmentsParameters_docx[5] },
			{ attachmentsParameters_xlsx[5] }, { attachmentsParameters_any[5] } };
	
	public static final String[][] lbf_Attachments_eList_Amended = {
		{ attachmentsParameters_doc[5] }, { attachmentsParameters_jpg[5] },
		{ attachmentsParameters_mpp[5] }, { attachmentsParameters_pdf[5] },
		{ attachmentsParameters_rtf[5] }, { attachmentsParameters_txt[5] },
		{ attachmentsParameters_wpd[5] }, { attachmentsParameters_xls[5] },
		{ attachmentsParameters_zip[5] }, { attachmentsParameters_xml[5] },
		{ attachmentsParameters_docx[5] },
		{ attachmentsParameters_xlsx[5] }, { attachmentsParameters_any[5] } };

	public static final String[][] lbf_SubSchedules_Fields_eList = {
			{ "Claim 1", "Date", "Date", "Form", "true", "false" },
			{ "Claim 2", "Date", "Date", "Form", "true", "true" },
			{ "Claim 3", "Date", "Date", "Form", "true", "true" },
			{ "Claim 4", "Date", "Date", "Form", "true", "false" } };
	
	public static final String[][] lbf_SubSchedules_Fields_eList_Amended = {
		{ "Claim 5", "Date", "Date", "Form", "false", "true" },
		{ "Claim 2", "Date", "Date", "Form", "true", "true" },
		{ "Claim 3", "Date", "Date", "Form", "true", "true" },
		{  "Claim 4", "Date", "Date", "Form", "true", "false" } };

	/***************************************************
	 * 
	 * Formlets eLists Tables, Fields and Icons Id
	 * 
	 * **************************************************/

	public static final String lbf_FormletList_Table_Id = "g3-form:data_withLetterFilter_data"; //"g3-form:_idJsp79:data_withLetterFilter";
	public static final String lbf_FormletList_TableBody_Id = "g3-form:data_withLetterFilter_data"; //"g3-form:_idJsp79:data_withLetterFilter:tbody_element";
	
	public static final String lbf_AttachmentList_Table_Id = "g3-form:data_data";
	public static final String lbf_AttachmentList_TableBody_Id = "g3-form:data_data";

	public static final String lbf_NoProp_Approval_DropdownTtl = "Approval Dropdown";
	public static final String lbf_NoProp_Checkbox_FieldTtl = "Checkbox";
	public static final String lbf_NoProp_Date_FieldTtl = "Date";
	public static final String lbf_NoProp_EINumber_FieldTtl = "EIN Number";
	public static final String lbf_NoProp_EmailAddress_FieldTtl = "Email Address";
	public static final String lbf_NoProp_WebAddress_FieldTtl = "Web Address";

	public static final String lbf_NoProp_Approval_DropdownId = "g3-form:eFormFieldList:0:numericDropdown_input";
	public static final String lbf_NoProp_Checkbox_FieldId = "g3-form:eFormFieldList:1:booleanProperty_input";
	public static final String lbf_NoProp_Date_FieldId = "g3-form:eFormFieldList:2:datePicker_input";
	public static final String lbf_NoProp_EINumber_FieldId = "g3-form:eFormFieldList:3:textBox";
	public static final String lbf_NoProp_EmailAddress_FieldId = "g3-form:eFormFieldList:4:textBox";
	public static final String lbf_NoProp_WebAddress_FieldId = "g3-form:eFormFieldList:5:textBox";

	public static final String lbf_TypeProp_PhoneNumber_FieldTtl = "Phone Number";
	public static final String lbf_TypeProp_PostalCode_FieldTtl = "Postal Code";

	public static final String lbf_TypeProp_PhoneNumber_FieldId = "g3-form:eFormFieldList:0:textBox";
	public static final String lbf_TypeProp_PostalCode_FieldId = "g3-form:eFormFieldList:1:textBox";

	public static final String lbf_MinMaxProp_LongText_FieldTtl = "Long Text";
	public static final String lbf_MinMaxProp_Numeric_FieldTtl = "Numeric";
	public static final String lbf_MinMaxProp_Password_FieldTtl = "Password";
	public static final String lbf_MinMaxProp_ShortText_FieldTtl = "Short Text";
	public static final String lbf_MinMaxProp_ReviewScore_FieldTtl = "Review Score";

	public static final String lbf_MinMaxProp_LongText_FieldId = "g3-form:eFormFieldList:0:textArea_Below";
	public static final String lbf_MinMaxProp_Numeric_FieldId = "g3-form:eFormFieldList:1:textBox";
	public static final String lbf_MinMaxProp_Password_FieldId = "g3-form:eFormFieldList:2:password";
	public static final String lbf_MinMaxProp_ShortText_FieldId = "g3-form:eFormFieldList:3:textBox";
	public static final String lbf_MinMaxProp_ReviewScore_FieldId = "g3-form:eFormFieldList:4:textBox";
	
	
	public static final String lbf_DataGrid_MiddleOf_TagId = "j_id_ap"; // New Versions: My Faces 1.1.10 and Tomahawk 1.1.14

	public static final String lbf_DataGrids_NumericCell_A1_Id = "g3-form:eFormFieldList:0:dataGrid:0:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_NumericEntry");
	public static final String lbf_DataGrids_NumericCell_B2_Id = "g3-form:eFormFieldList:0:dataGrid:1:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_NumericEntry");
	public static final String lbf_DataGrids_NumericCell_C3_Id = "g3-form:eFormFieldList:0:dataGrid:2:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_NumericEntry");
	
	public static final String lbf_DataGrids_TextCell_A1_Id = "g3-form:eFormFieldList:1:dataGrid:0:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_TextEntry");
	public static final String lbf_DataGrids_TextCell_B2_Id = "g3-form:eFormFieldList:1:dataGrid:1:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_TextEntry");
	public static final String lbf_DataGrids_TextCell_C3_Id = "g3-form:eFormFieldList:1:dataGrid:2:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_TextEntry");
	
	public static final String lbf_DataGrids_DropdownCell_A1_Id = "g3-form:eFormFieldList:3:dataGrid:0:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_DropdownFromLookup_input");
	public static final String lbf_DataGrids_DropdownCell_B2_Id = "g3-form:eFormFieldList:3:dataGrid:1:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_DropdownFromLookup_input");
	public static final String lbf_DataGrids_DropdownCell_C3_Id = "g3-form:eFormFieldList:3:dataGrid:2:".concat(lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_DropdownFromLookup_input");

	public static final String lbf_DataGrids_NumericCells_TableTtl = "/Data Grid Numeric: /";
	public static final String lbf_DataGrids_TextCells_TableTtl = "/Data Grid Text: /";
	public static final String lbf_DataGrids_DropdownCells_TableTtl = "/Data Grid Lookup: /";
	
//	public static final String lbf_AttachmentDetails_FormId = "main:displayAttachmentDetailsFormlet:attachmentDetailsForm";

	public static final String lbf_AttachmentDetails_DocDescription_FieldId = "g3-form:eFormFieldList:0:textBox";
	public static final String lbf_AttachmentDetails_FileUpload_FieldId = "g3-form:uploadedDocument";

	public static final String lbf_SubSchedules_SubName_FieldTtl = "Submission Name";
	public static final String lbf_SubSchedules_StartDate_FieldTtl = "Publication Start Date";
	public static final String lbf_SubSchedules_DueDate_FieldTtl = "Schedule Due Date";
	public static final String lbf_SubSchedules_EndDate_FieldTtl = "Publication End Date";
	public static final String lbf_SubSchedules_SubForm_DropdownTtl = "Submission Form";
	public static final String lbf_SubSchedules_Required_CheckboxTtl = "Required?";
	public static final String lbf_SubSchedules_POOnly_CheckboxTtl = "Program Office Only?";

	public static final String lbf_DocsFilesPath = "src\\test_Suite\\xml_Files\\LBF_Documents\\";
	
	public static final String lbf_DataGrids_Form_Id = "main:dFo:_idJsp5";
	
	
	public static final String applicant_amendment_dropdown_Id = "main:editStep:step:stepDetail:stepDetailTab:properties:1:integerDropdown";
	
	public static final String amendment_status = "Allow Amend Now";
	
	public static final String  equipmentTxtBox_Id = "g3-form:eFormFieldList:3:textBox"; 
	
	public static final String  operationalTxtBox_Id = "g3-form:eFormFieldList:4:textBox";

}
