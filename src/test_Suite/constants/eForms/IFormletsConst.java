package test_Suite.constants.eForms;

/**
*
* @author mshakshouki
*/

public interface IFormletsConst {
	
	/*
	 * Holds Formlet Types, PDF Export Values, HTML Fields Ids 
	 */
	
	
	//******** Formlet Type Names ***************
	public static final String formletTypeName_eFormQuestionHolder = "e.Form Question Holder";
	
	public static final String formletTypeName_eFormList = "e.Form List";
	
	public static final String formletTypeName_SubmissionSummary = "Submission Summary";
	
	public static final String formletTypeName_MenuItemOnly = "Menu Item Only";
	
	public static final String formletTypeName_eFormQuestionHolderWithSubmissionList = "e.Form Question Holder with Submission List";
	
	public static final String formletTypeName_AttachmentsList = "Attachments List";
	
	public static final String formletTypeName_SubmissionScheduleList = "Submission Schedule List";
	
	
	//**************** PDF Export ***************
	public static final String pdfIncludeInExport[]  = {"If Formlet is visible to User", "Always","Never"};
	public static final String pdfPageBreak[]       = {"Before Formlet", "Full", "None"};
	public static final String pdfPageOrientation[]  = {"Portrait", "Landscape"};
	
	public static final String includeInPDFExport_IfFormletIsVisibleToUser = "If Formlet is visible to User";
	
	public static final String includeInPDFExport_Always = "Always";
	
	public static final String includeInPDFExport_Never = "Never";
	
	public static final String pdfPageBreak_BeforeFolrmet = "Before Formlet";
	
	public static final String pdfPageBreak_Full = "Full";
	
	public static final String pdfPageBreak_None = "None";
	
	public static final String pdfPageOrientation_Portriate = "Portrait";
	
	public static final String pdfPageOrientation_Landscape = "Landscape";
	
	//********** HTML Field Tags ID *******************
	
	public static final String formletIdentifier_TextField_Id = "/formletIdentifier_rw/";
	
	public static final String formletType_SelectList_Id = "/formletTypes_rw/";
	
	public static final String formletMenuIndent_SelectList_Id = "/formletMenuIndent/";
	
	public static final String formletDisplayInSubmissionSummary_CheckBox_Id = "/IncludeInSubmissionSummary/";
	
	public static final String formletIncludeInPDFExport_SelectList_Id = "/IncludeInPDFExport/";
	
	public static final String formletPdfPageBreak_SelectList_Id = "/PDFExport_PageBreak/";
	
	public static final String formletPdfPageOrentation_SelectList_Id = "/PDFExport_PageOrientation/";
	
	public static final String formletSummaryLastUpdated_SelectList_Id = "j_id_1v:properties:0:DropDownProperty_Integer_input";
	public static final String formletSummaryLastUpdatedBy_SelectList_Id = "j_id_1v:properties:1:DropDownProperty_Integer_input";
	
	public static final String formletTitleBarText_TextField_Id = "formletTitleBarText";
	
	public static final String formletMenuText_TextField_Id = "formletMenuText";
	
//	public static final String formletSpanId = "main:dFo:_idJsp94:fi";
	
	public static final String gpsBudgetFormletDivClass = "ui-datatable-tablewrapper";
	
	public static final String gpsBudgetFormletSpanClass = "g3-gridField"; 
	
	public static final String formletfieldexpandnode_value="Formlet: Numeric field";
	
	public static final String openformletfield_value="Numeric: Numeric Data";

}
