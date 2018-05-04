package test_Suite.constants.cases;

public interface IOrgConst {
	
	public static final String org_Planner_SpanId = "/planner/";
	public static final String org_Planner_Recursive_ExpandId = "/recursiveExpandAndCollapse/";
	public static final String org_NodesList_OrgAccess_DrpDwnId = "/organizationAccess/";
	public static final String org_NodesList_OrgAccess_RadioBtnTableId = "/radioButtons/";
	
	public static final String org_LookupMappings_Lookup_DrpDwnId = "/lookupType/";
	
	public static final String org_LookupMappings_AvailableLookupVals_M2MId = "/unmappedLookupValues/";
	public static final String org_LookupMappings_SelectedLookupVals_M2MId = "/mappedLookupValues/";
	
	public static final String org_Ident_TxtFld_Id      = "/orgIdentifier/";
	public static final String org_Form_DrpDwn_Id       = "/organizationForm/";
	public static final String inhParent_Chkbox_Id     = "/inheritParentChanges/";
	public static final String org_Type_Drpdwn_Id       = "/orgType/";
	public static final String org_Status_Drpdwn_Id     = "/orgStatus/";
	public static final String org_DefaultLocale_Drpdwn_Id    = "/orgDefaultLocale/";
	public static final String org_Officer_Drpdwn_Id    = "/orgOfficer/";
	public static final String org_FullName_TxtFld_EndId  = ":organizationFullName/";
	public static final String org_ShortName_TxtFld_EndId = ":organizationShortName/";
	
	public static final String org_FullName_TxtFld_StartId = "/locales:";
	public static final String org_ShortName_TxtFld_StartId = "/locales:";
	
	//Messages
	public static final String ORG_DUPLICATE_IDENT = "Organization Identifier already in use by another Organization. Organization Identifiers must be unique.";
	public static final String ORG_VALUE_REQUIRED  = "/Value is required./";
	public static final String ORG_VALUE_SELECTREQ = "A value must be selected for this property";
	
	//Organization's associated objects
	public static final String ORG_ACCESS        = "organizationAccess";
	public static final String ORG_PROGRAMS_LIST = "organizationProgramsList";
	public static final String ORG_FORMS_LIST    = "organizationFormsList";
	public static final String ORG_REPORTS_LIST  = "organizationReportsList";
	public static final String ORG_LOOKUPS_LIST  = "organizationLookupsList";
	public static final String ORG_USERS_LIST    = "organizationUsersList";
	public static final String ORG_GROUPS_LIST   = "organizationGroupsList";
	
	//Org Types
	public static final String ORG_TYPE_DEFAULT  = "Default"; 
	//Org Names for Pre-test
	public static final String ORG_EAST          = "East";
	public static final String ORG_WEST          = "West";
	public static final String ORG_EAST_BOSTON   = "Boston";
	public static final String ORG_EAST_NEWYORK  = "New-York";
	public static final String ORG_WEST_LA       = "LA";
	public static final String ORG_WEST_SEATTLE  = "Seattle";
	
	//Other
	public static final String DD_ROW               = "_row_";
	public static final String orgPlanner_PlusGif_Last_Id = "nav-plus-line-last.gif";
	public static final String orgPlanner_PlusGif_Middle_Id = "nav-plus-line-middle.gif";
	public static final String orgPlanner_MinusGif_Last_Id = "nav-minus-line-last.gif";
	public static final String orgPlanner_MinusGif_Middle_Id = "nav-minus-line-middle.gif";
	
	public static final String orgPlanner_OrgNodeInnerText = "Organization: ";
	public static final String orgPlanner_ProgramNodeInnerText = "Programs";
	public static final String orgPlanner_FundingOppNodeInnerText = "Funding Opportunities";
	public static final String orgPlanner_FormNodeInnerText = "Forms";
	public static final String orgPlanner_DocumentsNodeInnerText = "Documents";
	public static final String orgPlanner_ReportsNodeInnerText = "Reports";
	public static final String orgPlanner_LookupsNodeInnerText = "Lookups";
	public static final String orgPlanner_UsersNodeInnerText = "Users";
	public static final String orgPlanner_GroupsNodeInnerText = "Groups";
	
	public static final String orgPlanner_OrgNodeImageAlt = "View Organization";
	public static final String orgPlanner_ProgramNodeImageAlt = "View Organization Programs";
	public static final String orgPlanner_FundingOppNodeImageAlt = "View Organization Funding Opportunities";
	public static final String orgPlanner_FormNodeImageAlt = "View Organization Forms";
	public static final String orgPlanner_DocumentsNodeImageAlt = "View Organization Documents";
	public static final String orgPlanner_ReportsNodeImageAlt = "View Organization Reports";
	public static final String orgPlanner_LookupsNodeImageAlt = "View Organization Lookups";
	public static final String orgPlanner_UsersNodeImageAlt = "View Organization Users";
	public static final String orgPlanner_GroupsNodeImageAlt = "View Organization Groups";	
	
	public static final String org_G3Root_Id = "G3";
	public static final String org_G3Root_FullName = "Grantium G3";
	public static final String org_G3Root_ShortName = "G3";
	
	
	public static enum EOrgPlannerActions {
		
		add,
		view
		
	}
	
	public static enum EOrgPlannerNodeType {
		rootOrg,
		childOrg
	}
	
	public static enum EOrgPlannerObjects {
		G3,programs,FundingOpps,forms,documents,reports,lookups,users,groups
	}
	
	/*******************************************************
	 * To Access Details pages to assign Different Org
	 * Tables Ids
	 *****************************************************/
	
	public static final String org_Programs_TableId = "/organizationPAPsList/";
	public static final String org_FundingOpps_TableId = "/organizationProgramsList/";
	public static final String org_Forms_TableId = "/organizationFormsList/";
	public static final String org_Documents_TableId = "/organizationDocumentsList/";
	public static final String org_Reports_TableId = "/organizationReportsList/";
	public static final String org_Lookups_TableId = "/organizationLookupsList/";
	public static final String org_Users_TableId = "/organizationUsersList/";
	public static final String org_Groups_TableId = "/organizationGroupsList/";
	//public static final String org_Programs_TableId = "";
	
	public static final String lookupView_organizationLookupMapping = "organizationLookupMappingView:organizationLookupMappingForm:lookupType_input";
	
}
