/**
 * 
 */
package test_Suite.constants.reporting_Functions;

/**
 * @author mshakshouki
 *
 */
public interface IEtlConst {
	
	public static final String editReportingFields_TableId = "main:etlPlanner:_idJsp26:upperPane";
	
	public static final String formIdent_dropDown_label = "e.Form Identifier";
	
	public static final String formletIdent_dropDown_label = "Formlet";
	
	public static final String eformFieldIdent_dropDown_label = "e.FormField Identifier";
	
	public static final String formType_dropDown_label = "e.Form Type";
	
	public static final String form_dropDown_label = "e.Form";
	
	public static final String fundingOpp_dropDown_label = "Funding Opportunity";
	
	public static final String Organization_dropDown_label = "Organization";
	
	public static final String plannerEFormfieldTree_spanId = "main:etlPlanner:_idJsp31:eFormFieldTree";
	
	public static final String plannerEformField_TopLi_Id = "etlPlannerForm:eFormFieldTree:0";
	
	public static final String plannerTree_spanId = "main:etlPlanner:_idJsp31:planner";
	
	public static final String activeReportingFields_TableId = "main:etlPlanner:_idJsp26:_idJsp78:etlFieldsList";
	
	public static final String reportingFieldList_TableId = "main:listEtlFields:etlFieldsListForm:_idJsp68:etlFieldsList";
	
//	public static final String plannerFirstPlusExpnd_imageId = "main:etlPlanner:_idJsp31:planner:0:t2";
	
//	public static final String plannerFirstMinusCollapse_imageId = "main:etlPlanner:_idJsp31:planner:0:t2";
	
//	public static final String plannerSecondPlusExpnd_imageId = "main:etlPlanner:_idJsp31:planner:0:10:t2";
	
//	public static final String plannerSecondMinusCollapse_imageId = "main:etlPlanner:_idJsp31:planner:0:10:t2";
	
//	public static final String plannerExpandSign_ImageSrc = "nav-plus-line-last.gif";
	
//	public static final String plannerCollapseSign_ImageSrc = "nav-minus-line-last.gif";
	
	
	
	
	/********* Reporting Table Managment*********/
	public static final String etl_RTM_FormId = "j_id_1u"; //"main:etlManage:_idJsp31";
	
	public static final String etl_RTM = "Data Mart Structure";
	
	public static final String etl_RTM_ReportingTable_StartWith = "Data Mart Structure";
	
	public static final String etl_RTM_ReportingTableRefresh_StartWith = "Data Refresh";
	
	public static String[] rptFldTypes = {"Applicant","Funding Opportunity","Organization","Submission","Any"};
	
	public static String[] rptFldPrefix = {"DA_","DF_","DO_","DS_"};
	
	public static enum EetlRptFldTypes
	{
		APPLICANT,FOPP,ORG,SUB,ANY
	}
	
	public static enum EetlRepoFieldManagementTypes
	{
		folder,add,create,delete,edit,show,expand,collapse
	}
	
	public static enum EetlEFormTypes 
	{
		org,fopp,profile,sub
	}	
	
	public static String[] etlEFormTypes = {"Organization Forms","Funding Opportunity Forms","Applicant Forms","Submission Forms"};
	
	
	public static enum EetlFoppFormTypes
	{
		sub,review,approval,poss,award,ipass
	}	
	
	public static final String[] etlFoppFormTypes = {"Applicant Submission: ", "Review: ","Project Approval: ","Program Office Submission: ", "Award: ","Post Award Submission: "};
	
	public static enum EetlFormlets {
		noProp,ddFileds,ddLookup,propFields,minMax,eList,eListDD,eListDG
	}
	
	public static final String[] etlNonProjetFormlets = {"No-Properties-eFormFields","Dropdown-eFormFields","Dropdown-Lookup-Property-eFormFields","Type-Properties-eFormFields","Min-And-Max-Properties-eFormFields","eList","eList Dropdown","eList Data Grid"};
	
	/*
	 * ***** This new after the UI changed by Kevin in 3.0
	 */
	
	public static enum EetlRepoFieldTypes {
		date,dropdown,longTxt,m2m,number,shortTxt
	}
	
	public static String[] etlDM_RepoFieldTypes = {"Date", "Dropdown","Long Text","Many To Many","Number","Short Text"};
	
	
	public static final String etlDM_RepoFieldList_TableId = "etlFieldsListForm:etlFieldsList_data";	

	
	public static final String idJspBName = "main:etlPlanner:_idJsp31";
	
	public static final String etlDM_RepoMapping_eFormType_DropdownId = idJspBName.concat(":formTypeDropdown");
	
	public static final String etlDM_RepoMapping_Org_DropdownId = idJspBName.concat(":organizationDropdown");
	
	public static final String etlDM_RepoMapping_FOPP_DropdownId = "etlPlannerForm:fundingOppDropdown_input";
	
	public static final String etlDM_RepoMapping_eForm_DropdownId = idJspBName.concat(":formDropdown");
	
	public static final String etlDM_RepoMapping_RepoType_DropdownId = idJspBName.concat(":reportingFieldDropdown");
	
	
	/********* Folder Tag id *********/
	
	public static final String etlDM_RptFolderIdent_TextField_Id = idJspBName.concat(":folderIdentifier");
	
	public static final String etlDM_RptFolderBusinessName0_TextField_Id = idJspBName.concat(":allFolderBusinessNames:0:folderBusinessName");
	
	public static final String etlDM_RptFolderBusinessName1_TextField_Id = idJspBName.concat(":allFolderBusinessNames:1:folderBusinessName");
	
	public static final String etlDM_RptFolderBusinessName2_TextField_Id = idJspBName.concat(":allFolderBusinessNames:2:folderBusinessName");
	
	public static final String etlDM_RptFolderBusinessName3_TextField_Id = idJspBName.concat(":allFolderBusinessNames:3:folderBusinessName");
	
	/********* Reporting Field Details Tags Id *************/
	
	public static final String etlDM_RptFldFolder_Dropdwon_Id = idJspBName.concat(":fieldFolder");
	
	public static final String etlDM_RptFldIdent_TextField_Id = idJspBName.concat(":fieldReportingIdentifier");
	
	public static final String etlDM_RptFldType_Dropdown_Id = idJspBName.concat(":fieldType");
	
	public static final String etlDM_RptFldUsage_Dropdown_Id = idJspBName.concat(":fieldUsage");
	
	public static final String etlDM_RptFldDesc_LongText_Id = idJspBName.concat(":fieldDescription");
	
	
	public static final String etlDM_RptFldBusinessName0_TextField_Id = "etlPlannerForm:allFieldBusinessNames:0:fieldBusinessName";
	
	public static final String etlDM_RptFldBusinessName1_TextField_Id = "etlPlannerForm:allFieldBusinessNames:1:fieldBusinessName";
	
	public static final String etlDM_RptFldBusinessName2_TextField_Id = "etlPlannerForm:allFieldBusinessNames:2:fieldBusinessName";
	
	public static final String etlDM_RptFldBusinessName3_TextField_Id = "etlPlannerForm:allFieldBusinessNames:3:fieldBusinessName";

	
	/******** Reporting Fields Filters ********/
	
	public static final String etlDM_RepoIdent_Label = "Reporting Identifier";
	
	public static final String etlDM_RepoFldIdent_Label = "Reporting Field Identifier";
	
	public static final String etlDM_RepoFieldType_Label = "Reporting Field Type";
	
	public static final String etlDM_Category_Label = "Category";
	
	public static final String etlDM_DateAdded_Label = "Date Added";
	
	public static final String etlDM_RepoFieldStatus_Label = "Reporting Field Status";
	
	public static final String etlDM_RepoMapping_eFieldIdent_Label = "e.FormField Identifier";
	
	public static final String etlDM_RepoMapping_eFormIdent_Label = "e.Form Identifier";
	
	public static final String etlDM_RepoMapping_eFormlet_Label = "Formlet Identifier";
	
	public static final String etlDM_RepoFieldGroup_Label = "Reporting Field Group";
	
	public static final String etlDM_Folder_Label = "Folder";
	
	public static final String etlDM_RepotableFieldType_Label = "Reportable Field Type";
	
	public static final String etlDM_Status_Label = "Status";
	
	public static final String etlEditReportingField = "Edit Reporting Field";
  
    public static final String etlDM_RptFieldsCSVFileSub = "src/test_Suite/ReportingFieldsSubmissionEForms.csv";
  
    public static final String etlDM_RptFieldsCSVFileSubELists = "src/test_Suite/ReportingFieldsSubmissionELists.csv";
  
    public static final String etlDM_RptFieldsCSVFileFoppELists = "src/test_Suite/ReportingFieldsFOPPELists.csv";
  
    public static final String etlDM_RptFieldsCSVFileOrgHighELists = "src/test_Suite/ReportingFieldsOrgHighELists.csv";
  
    public static final String etlDM_RptFieldsCSVFileApp = "src/test_Suite/ReportingFiledsApplicantsEForms.csv";
  
    public static final String etlDM_RptFieldsCSVFileAppELists = "src/test_Suite/ReportingFieldsApplicantELists.csv";
  
    public static final String etlDM_RptFieldsCSVFileProjELists = "src/test_Suite/ReportingFieldsProjectELists.csv";
    
    public static final String etLDM_RptLbfEqualFormsIdent[] = {"not for use","LBF-Applicant Submission-Equal-eLists","LBF-PO Submission-Equal-eLists","LBF-Standard Agreement-Equal-eLists","LBF-Post Award Submission-Equal-eLists"};
        
    public static final String etLDM_RptLbfFilteringFormsIdent[] = {"not for use","LBF-Applicant Submission-Filtering-eLists","LBF-PO Submission-Filtering-eLists","LBF-Standard Agreement-Filtering-eLists","LBF-Post Award Submission-Filtering-eLists"};

	public static final String etlDM_RepoFieldMappings_TBodyID = "etlFieldsListForm:etlFieldsList_data";

	public static final String etlEform_FormDd = "etlPlannerForm:formDropdown_input";

	public static final String etlEform_FoppDd = "etlPlannerForm:fundingOppDropdown_input";

	public static final String etlEform_OrgDd = "etlPlannerForm:organizationDropdown_input";

}
