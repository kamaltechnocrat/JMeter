package test_Suite.constants.workflow;

public interface IProgramsConst {
	
	//PAP Constants
	public static final String pap_ProgPrefix = "-Generic-";
	public static final String pap_ProgPostfix = "";
	public static final String pap_ProgIdentifier = "Program Ident";
	public static final String pap_ProjPrefix = "\"pap.\"+%{G3Utils}.leftPadString(#{projectID}, 6, \"0\")";
	public static final String pap_Prog_LocalizedName = "Program Name";
	
	//PAP Program Fields Tag Id
	//Program Fields Tags Id	
	public static final String pap_Prog_Ident          = "/txtProgramIdentifier/";
	public static final String pap_ProjPrefix_TxtFld = "/projectPrefix/";
	public static final String pap_PROG_Form_Select    = "/programFormSelectBox/";
	public static final String pap_Prog_Start_Date     = "/programStartDate/";
	public static final String pap_Prog_End_Date       = "/programEndDate/";
	public static final String pap_Prog_Officer_DrpDwn   = "/programManagerMenu/";
	public static final String pap_Prog_Status_DrpDwn    = "/programStatusMenu/";
	public static final String pap_Prog_PrimOrg_DrpDwn = "/primaryOrganization/";
	public static final String pap_Prog_OrgAccess_DrpDwn = "/organizationAccess/";
	public static final String pap_Prog_Name_StartTag = "/programTexts:";
	public static final String pap_Prog_Name_EndTag           = ":programText/";
	
	public static final String pap_Details_AvailableGrp_DrpDwn = "/stepAdminGroupsList/";
	public static final String pap_Details_AvailableGrpUsers_DrpDwn = "/selectedCaseStaff/";
	public static final String pap_Details_OverrideOrgFilter_ChkBox = "/showAllGroupUsers/";
	public static final String pap_Details_FundName_TxtFld = "/poProjectName/";
	public static final String pap_Details_FundNumber_TxtFld = "/caseNumber/";
	public static final String pap_Details_Org_DrpDwn = "/projectOrganization/";
	
	public static final String pap_AssignEvals_AvailableEvals_M2M = "/assignEvaluator:availableStaff/";
	public static final String pap_AssignEvals_SelectedEvals_M2M = "/assignEvaluator:selectedStaff/";
	public static final String pap_AssignEvals_OrgOverride_ChkBox = "/showAllAvailableEvaluators/";

	
	//Funding Opp. Fields Tags Id	
	public static final String fundingOpp_prog_Ident          = "/txtProgramIdentifier/";
	public static final String fundingOpp_PROG_FORM_SELECT    = "/programFormSelectBox/";
	public static final String fundingOpp_PUBL_FORM_SELECT    = "/publicationFormSelectBox/";
	public static final String fundingOpp_PROG_START_DATE     = "/programStartDate/";
	public static final String fundingOpp_PROG_END_DATE       = "/programEndDate/";
	public static final String fundingOpp_PROG_MANAGER_MENU   = "/programManagerMenu/";
	public static final String fundingOpp_PROG_STATUS_MENU    = "/programStatusMenu/";
	public static final String fundingOpp_Prog_Primary_Org    = "/primaryOrganization/";
	public static final String fundingOpp_Prog_Org_Access	  = "/organizationAccess/";
	public static final String fundingOpp_Proj_Num_Template    = "/projectPrefix/";
	public static final String fundingOpp_PROG_PAP_SELECT     = "/papSelectBox/";
	public static final String fundingOpp_PROG_PAP_STATUS_LBL = "/papApprovalStatusLblOutput/";
	public static final String fundingOpp_PROG_REG_START_DATE = "/registrationStartDate/";	
	public static final String fundingOpp_PROG_REG_START_HH   = "/registrationStartDateHour/";
	public static final String fundingOpp_PROG_REG_START_MM   = "/registrationStartDateMinute/";
	public static final String fundingOpp_PROG_REG_END_DATE   = "/registrationEndDate/";
	public static final String fundingOpp_PROG_REG_END_HH     = "/registrationEndDateHour/";
	public static final String fundingOpp_PROG_REG_END_MM     = "/registrationEndDateMinute/";
	public static final String fundingOpp_PROG_FO_PROJ_CREATE = "/foDisable/";
	public static final String fundingOpp_PROG_FORM_COMPL_REC = "/newProjectRequiresCompleteAF/";
	public static final String fundingOpp_PROG_TEXT           = ":programText/";
	public static final String fundingOpp_Prog_Text_lacale_0  = "/programTexts:0:programText/";
	public static final String fundingOpp_Prog_Text_lacale_1  = "/programTexts:1:programText/";
	public static final String fundingOpp_Prog_Text_lacale_2  = "/programTexts:2:programText/";
	public static final String fundingOpp_Prog_Text_lacale_3  = "/programTexts:3:programText/";
	
	//Funding Opportunity planners
	public static final String fundingOpp_Manage_Admins_Title = "Funding Opportunity Administrators";
	public static final String fundingOpp_Manage_Staffs_Title = "Funding Opportunity Staff";
	public static final String fundingOpp_Public_EForm_Title  = "Publication e.Form";
	public static final String fundingOpp_Admins_EForm_Title  = "Administration e.Form";
	public static final String fundingOpp_ProjectConfig_Title = "Project Configuration";
	public static final String fundingOpp_ExpandCollapseSubItems_Id = "/recursiveExpandAndCollapse/";
	
	//Program Administrators Staff
	public static final String PROG_STAFF_AVAILABL = "/availableStaff/";
	public static final String PROG_STAFF_SELECTED = "/selectedStaff/";
	public static final String PROG_SHAW_ALLGROUPS = "/showAllGroups/";
	public static final String PROG_STAFF_ORG      = "/organization/";

	//Program Details
	public static final String OPEN_PROG_PLANNER   = "Open Program Planner";
	public static final String OPEN_PROG_FORM      = "Open Program e.Form";
	public static final String OPEN_PROG_APPR_FORM = "Open Program Approval e.Form";
	public static final String OPEN_PROG_PUBL_FORM = "Open Publication e.Form";
	public static final String MANAGE_PROG_ADMIN   = "Manage Program Administrators";
	public static final String MANAGE_PROG_STAFF   = "Manage Program Staff";
	public static final String VIEW_STEP_DETAILS   = "View Step Details";
	
	//Program Approval Status
	public static final String APPR_STATUS_PENDING = "Pending Approval";
	public static final String APPR_STATUS_NEW	   = "New";
	public static final String APPR_STATUS_APPROVED = "Approved";
	public static final String APPR_STATUS_REJECTED = "Rejected";
	
	//Program Planner
	public static final String progPlanner_Span_Id = "/programPlanner/";
	public static final String progPlanner_PlusGif_Last_Id = "nav-plus-line-last.gif";
	public static final String progPlanner_PlusGif_Middle_Id = "nav-plus-line-middle.gif";
	public static final String progPlanner_MinusGif_Last_Id = "nav-minus-line-last.gif";
	public static final String progPlanner_MinusGif_Middle_Id = "nav-minus-line-middle.gif";
	
	public static final String progPlanner_RecursiveExpandCollapse_Id = "j_id_1u:recursiveExpandAndCollapse_input";
	
	public static final String progPlanner_availForms = "/availableForms/";
	
	
	
	public static enum EstepManagment {
		
		stepStart,		//0 decoy Enum
		stepStaff,
		documents,
		notification,
		formAccess,
		formData,
		stepStatusLabels,
		submissionStatusLabels		
		
	}
	
	public static enum EstepManagmentPA {
		
		stepStart,		//0 decoy index
		stepStaff,
		documents,
		notification,		
		stepStatusLabels,
		submissionStatusLabels		
		
	}
	
	
	public static enum eProgramList {
		
		pap, 
		fop
	}
	
	public static enum EProjectType {
		
		pre_Award,
		post_Award
	}
	
	
}
