

package test_Suite.constants.users;


/**
 *
 * @author mshakshouki
 */
public interface IUAPConst {
    
	public static final String uap_Prefix   = "-UAP-";
	public static final String uap_PA_Prefix = "-UAP-PA-";	

	//###*** Form Identity *******
	public static final String formIdentUAP       = "-UAP Form";

	/****************************************************
    #             Lookup List
    #****************************************************/

	public static final String lookupLists = "-UAP-Lookup-List";
	public static final String[] lookupValues = {"-UAP-Second-Value", "-UAP-First-Value", "-UAP-Third-Value"};

	//-----------------------------------------------------------------
	
	
//	public static final String groupAccessRightsSpanId = "manageGroupView:groupTabbedForm:editGroupAccessRightsView:G:H";
	public static final String groupAccessRightsDivId = "groupTabbedForm:manageGroupTabbedPane:T";
	
//	public static final String usersAccessRightsSpanId = "manageUserView:userTabbedForm:editAccessRightsTab:editAccessRightsTab";

//	public static final String groupAccessRightsTopLevelTableId = "manageGroupView:groupTabbedForm:editGroupAccessRightsView:G:_idJsp97:T";
	public static final String groupAccessRightsTopLevelTableId = "groupTabbedForm:manageGroupTabbedPane:T_data";

	/**********************************************
	 * Main Nodes Spans Id
	 ********************************************/
	/*
	public static final String managePrograms_Span_Id = "/accessRightsTree:0:0:0:0/";
	
	public static final String manageForms_Span_Id = "/accessRightsTree:0:1:0:1/";
	
	public static final String manageProgramDocuments_Span_Id = "/accessRightsTree:0:2:0:2/";
	
	public static final String intakeSubmissions_Span_Id = "/accessRightsTree:0:3:0:3/";
	
	public static final String manageProjects_Span_Id = "/accessRightsTree:0:4:0:4/";
	
	public static final String manageProjectEvaluations_Span_Id = "/accessRightsTree:0:5:0:5/";
	
	public static final String manageAwards_Span_Id = "/accessRightsTree:0:6:0:6/";
	
	public static final String myReports_Span_Id = "/accessRightsTree:0:7:0:7/";
	
	public static final String administerG3_Span_Id = "/accessRightsTree:0:8:0:8/";
	
	public static final String customeAccessPoints_Span_Id = "/accessRightsTree:0:9:0:9/";
	
	//Group Span ID
	public static final String grp_fopp_SpanId = "/GroupAccessRightsView:G:H:0:0:0:0/";
	
	public static final String grp_userProfile_SpanId = "/GroupAccessRightsView:G:H:0:1:0:1/";
	
	public static final String grp_applicants_SpanId = "/GroupAccessRightsView:G:H:0:2:0:2/";
	
	public static final String grp_fopp_Reg_SpanId = "/GroupAccessRightsView:G:H:0:3:0:3/";
	
	public static final String grp_projects_SpanId = "/GroupAccessRightsView:G:H:0:4:0:4/";
	
	public static final String grp_submission_SpanId = "/GroupAccessRightsView:G:H:0:5:0:5/";
	*/
	
	/*
	 * Trying something new
	 */
	

	
//	public static final String[] GrantProgramAdministrationUAP_1st  = 	{"Grant Program Administration"};
//	public static final String[] GrantProgramAdministrationUAP_Sub1st = {"/G:H/","Basic Details", "Activate", "Administrators", "Administration Form", "Administration Form, PDF Export", "Publication Form", "Publication Form, PDF Export", "Staff", "Assign Organizations", "Override Org Assignments"};
//	public static final String[] GrantProgramUAP_Program_2nd = {"/G:H:0:1:0:1/","Program List", "Import Program", "Clone Program", "Export Program", "In Basket List", "In Basket List, PDF Export", "Assign Evaluators List", "Evaluate Submission List", "Evaluate Submission List, PDF Export", "My Project Submissions List", "My Project Submissions List, PDF Export", "My Assigned Submissions", "My Assigned Submissions, PDF Export"};
//	public static final String[] GrantProgramUAP_FundingOpportunity_2nd = {"/G:H:0:0:0:0/","Funding Opportunity List", "Import Funding Opportunities", "Clone Funding Opportunities", "Export Funding Opportunity"};
//	public static final String[] GrantProgramUAP_ManageSteps_2nd = {"/G:H:0:2:0:2/","Step Details", "Reorder Steps", "Step Staff", "Step Documents", "Step Notifications", "Form Access", "Form Data", "Step Status Labels", "Submission Status Labels"};
//	
//	public static final String[] manageFormsUAP_1st = {"Manage Forms"};
//	public static final String[] manageFormsUAP_Sub1st = {"/G:H/","Form Functions"};
//	public static final String[] manageFormsUAP_FormsList_2nd = {"/G:H:0:0:0:0/","Forms", "Import Forms", "Preview Forms", "Clone Forms", "Export Forms"};
//	public static final String[] manageFormsUAP_FormDetails_3rd = {"/G:H:0:0:1:0:0:1/","Basic Details", "Assign Form Organizations"};
//	public static final String[] manageFormsUAP_ManageFormlets_2nd = {"/G:H:0:2:0:2/","Formlet Details", "Reorder Formlets", "Formlet Functions"};
//	public static final String[] manageFormsUAP_ManageEFormFields_3rd = {"/G:H:0:2:3:0:2:3/","e.Form Field Details", "e.Form Field Functions", "Reorder e.Form Fields"};
//	
//	public static final String[] manageDocumentsUAP_1st = {"Manage Documents"};
//	public static final String[] manageDocumentsUAP_Sub1st = {"/G:H/", "View Document"};
//	public static final String[] manageDocumentsUAP_GrantProgramDocuments_2nd = {"/G:H:0:0:0:0/","Basic Details","Assign Document Organizations"};
//	
	public static final String[] intakeSubmissionsUAP_1st = {"Intake Submissions"};
	public static final String[] intakeSubmissionsUAP_IntakeInBasket_2nd = {"/G:H:0:0:0:0/","Intake In Basket View Submission Forms","Intake In Basket View Submission Forms, PDF Export", "Project Details", "Intake Edit Project Organization", "Open Close Project", "Assign Project Officers", "Intake In Basket Award tab", "Override Organization Filter"};
	
	public static final String[] manageProjectsUAP_1st = {"Manage Projects"};
//	public static final String[] manageProjectsUAP_Sub1st = {"/G:H/", "Evaluate Projects List", "Evaluate Projects List, Automated Scoring"};
	public static final String[] manageProjectsUAP_AccessProjectsList_2nd = {"/G:H:0:0:0:0/","Project Activities", "Transfer Projects", "Project Details", "Project Organization",
								"Project Officer Assignments","Override Auto_assigned Groups", "Project Open/Close", "Award tab", "Override Organization Filter", "Notification Log", 
								"Instant Notification", "Request Amendment", "Project Form", "Project Form, PDF Export", "On-Demand Submission", "On-Demand Activation"};
	public static final String[] manageProjectsUAP_SubmissionHistory_3rd = {"/G:H:0:0:1:0:0:1/","View Submissions","View Submissions, PDF Export", "Manage Step Override Dates"};
	public static final String[] manageProjectsUAP_AccessMyInBasket_2nd = {"/node_0_3/"}; //{"/G:H:0:3:0:3/"};
	public static final String[] manageProjectsUAP_MyProjectSubmissions_3rd = {"/G:H:0:3:0:0:3:0/","View Submission Forms","View Submission Forms, PDF Export", "Request Amendment", 
								"Override Default Optional Step Re-execution", "Add Submission"};
	public static final String[] manageProjectsUAP_MyAssignedSubmissions_3rd = {"/G:H:0:3:1:0:3:1/","View Submission Forms","View Submission Forms, PDF Export", "Amend Submissions"};
	public static final String[] manageProjectsUAP_AccessApplicantList_2nd = {"/G:H:0:4:0:4/","Applicant", "Associates List", "Applicant Form", "Applicant Form, PDF Export"};
	public static final String[] manageProjectsUAP_ApplicantSubmissionsList_3rd = {"/G:H:0:4:1:0:4:1/","New Project", "Submission Form", "Submission Form, PDF Export"};
	public static final String[] manageProjectsUAP_RegistantsList_3rd = {"/G:H:0:4:2:0:4:2/","Manage Registrant Profiles","Manage Registrants"};
	public static final String[] manageProjectsUAP_ManageAmendments_2nd = {"/G:H:0:5:0:5/", "Approve Applicant Amendment"};
	public static final String[] manageProjectsUAP_AccessAmendmentList_3rd = {"/G:H:0:5:1:0:5:1/","Cancel Amendments","View/Edit Amendments", "View Submission Forms", "View Submission Forms, PDF Export"};
	
	public static final String[] manageProjectEvaluationsUAP_1st = {"Manage Project Evaluations"};
	public static final String[] manageProjectEvaluationsUAP_Sub1st = {"/G:H/","Assign Evaluators", "Evaluate Submissions", "Evaluate Submissions, PDF Export", "Override Organization Filter"};
//	public static final String[] manageProjectEvaluationsUAP_BulkEvaluationList_2nd = {"/G:H:0:4:0:4/","Export List","e.Form Field","Automated Scoring","Submit Evaluations","View Evaluation Forms","View Evaluation Forms, PDF Export"};
	
	public static final String[] manageAwardsUAP_1st = {"Manage Awards"};
	public static final String[] manageAwardsUAP_Sub1st = {"/G:H/","Access Award Forms","Access Award Forms, PDF Export", "Submit Award Forms", "Project Details", "Project Organization", "Project Officer Assignments", "Project Open/Close", "Award Tab", "Override Organization Filter"};
	
	public static final String[] myReportsUAP_1st = {"Manage Reports"};
//	public static final String[] myReportsUAP_Sub1st = {"/G:H/","Access All G3 Data", "Data Mart Reporting", "View 3rd Party Reports"};
//	public static final String[] myReportsUAP_AccessBusinessIntelligence_2nd = {"/:G:H:0:1:0:1/","Create Queries","Design Reports","My Reports and Queries","Public Shared Reports"};
	
	public static final String[] administerG3UAP_1st = {"/Administer Grantium/"};
//	public static final String[] administerG3UAP_Sub1st = {"/G:H/", "Utilities"};

//	public static final String[] administerG3UAP_ManageLookupLists_2nd = {"/G:H:0:0:0:0/","Import Lookups", "Export Lookups"};
//	public static final String[] administerG3UAP_LookupDetails_3rd = {"/G:H:0:0:2:0:0:2/","Basic Details", "Assign Lookup Organizations"};;
//	public static final String[] administerG3UAP_ManageLookupValues_3rd = {"/G:H:0:0:3:0:0:3/","Basic Details","Re-Order Lookup Values"};
//
//	public static final String[] administerG3UAP_ManageRefrenceTables_2nd = {"/G:H:0:1:0:1/","Import Lookups", "Export Lookups"};
//	public static final String[] administerG3UAP_ReferenceTableDetails_3rd = {"/G:H:0:1:2:0:1:2/","Basic Details", "Assign Lookup Organizations"};;
//	public static final String[] administerG3UAP_ManageRefernceTableData_3rd = {"/G:H:0:1:3:0:1:3/","Basic Details","Re-Order Lookup Values"};
//	
//	public static final String[] administerG3UAP_ManageApplicantTypeList_2nd = {"/G:H:0:2:0:2/","Import Applicant Types","Export Applicant Types", "Applicant Type Details"};
//	
//	public static final String[] administerG3UAP_ManageUsersAndGroups_2nd = {"/G:H:0:3:0:3/","Import Users", "Export Users", "Manage User Profiles", "Import Groups", "Export Groups", "Manage Locked Out Users"};
//	public static final String[] administerG3UAP_ManageUsers_3rd = {"/G:H:0:3:2:0:3:2/","Manage Contact Information", "Manage User Access Rights"};
//	public static final String[] administerG3UAP_ManageUserAccountDetails_4th = {"/G:H:0:3:2:1:0:3:2:1/","Basic Details","Assign User Organizations"};
//	
//	public static final String[] administerG3UAP_ManageGroups_3rd = {"/G:H:0:3:5:0:3:5/","Manage Group Access Rights"};
//	public static final String[] administerG3UAP_ManageGroupMembership_4th = {"/G:H:0:3:5:0:0:3:5:0/","Basic Details","Assign Group Organizations"};;
//	
//	public static final String[] administerG3UAP_ManageOrganizations_2nd = {"/G:H:0:4:0:4/"};
//	public static final String[] administerG3UAP_OrganizationList_3rd = {"/G:H:0:4:0:0:4:0/","Import Organizations", "Export Organizations"};
//	public static final String[] administerG3UAP_Organizations_4th = {"/G:H:0:4:0:0:0:4:0:0/","Organization Details", "Organization e.Form", "Organization e.Form, PDF Export"};
//	
//	public static final String[] administerG3UAP_AdministerReports_2nd = {"/G:H:0:5:0:5/","Manage Business Intelligence", "Manage Data Mart", "Manage Reporting Fields"};
	public static final String[] administerG3UAP_Manage3rdPartyReports_3rd = {"/G:H:0:5:0:0:5:0/","Report Access", "Report Parameters"};
	public static final String[] administerG3UAP_ReportsDetails_4th = {"/G:H:0:5:0:0:0:5:0:0/","Basic Details","Assign Report Organizations"};
	
//	public static final String[] administerG3UAP_ManageGrantiumGonfiguration_2nd = {"/G:H:0:6:0:6/","Manage Licenses","Manage Configuration List", "Manage Application Settings", "Data Loader"};
//	public static final String[] administerG3UAP_ManageFrontOfficeConfigurations_3nd = {"/G:H:0:6:3:0:6:3","Options", "Pre-login Welcome Text","Post-login Welcome Text", "Contact Text","Access Rights"};
//	
//	public static final String[] administerG3UAP_SupportPackage_3nd = {"/G:H:0:6:5:0:6:5/","Download","Unmask User Data"};
//	
//	public static final String[] administerG3UAP_ManageIntegration_2nd = {"/G:H:0:7:0:7/","Manage Integration Programs", "Manage Form Mappings", "Manage Certificates", "Manage Transaction Results"};

	/*
	public static final String[] administerG3UAP_1st = {"/Administer G3/"};
	public static final String[] administerG3UAP_ManageGrantiumGonfiguration_2nd = {"/G:H:0:0:0:0/","Manage Configuration List", "Manage Application Settings", "Manage Front Office Configurations", "Data Loader"};
	public static final String[] administerG3UAP_ManageIntegration_2nd = {"/G:H:0:1:0:1/","Manage Integration Programs", "Manage Form Mappings", "Manage Certificates", "Manage Transaction Results"};
	public static final String[] administerG3UAP_ManageLookupLists_2nd = {"/G:H:0:2:0:2/","Import Lookups", "Export Lookups"};
	public static final String[] administerG3UAP_LookupDetails_3rd = {"/G:H:0:2:2:0:2:2/","Basic Details", "Assign Lookup Organizations"};;
	public static final String[] administerG3UAP_ManageLookupValues_3rd = {"/G:H:0:2:3:0:2:3/","Basic Details","Re-Order Lookup Values"};
	public static final String[] administerG3UAP_ManageApplicantTypeList_2nd = {"/G:H:0:3:0:3/","Import Applicant Types","Export Applicant Types", "Applicant Type Details"};
	public static final String[] administerG3UAP_ManageUsersAndGroups_2nd = {"/G:H:0:4:0:4/","Import Users", "Export Users", "Manage User Profiles", "Import Groups", "Export Groups", "Manage Locked Out Users"};
	public static final String[] administerG3UAP_ManageUsers_3rd = {"/G:H:0:4:3:0:4:3/","Manage Contact Information", "Manage User Access Rights"};
	public static final String[] administerG3UAP_ManageUserAccountDetails_4rd = {"/G:H:0:4:3:1:0:4:3:1/","Basic Details","Assign User Organizations"};
	public static final String[] administerG3UAP_ManageGroups_3rd = {"/G:H:0:4:6:0:4:6/","Manage Group Access Rights"};
	public static final String[] administerG3UAP_ManageGroupMembership_4rd = {"/G:H:0:4:6:0:0:4:6:0/","Basic Details","Assign Group Organizations"};;
	public static final String[] administerG3UAP_ManageOrganizations_2nd = {"/G:H:0:5:0:5/"};
	public static final String[] administerG3UAP_OrganizationList_3rd = {"/G:H:0:5:0:0:5:0/","Import Organizations", "Export Organizations"};
	public static final String[] administerG3UAP_Organizations_4rd = {"/G:H:0:5:0:0:0:5:0:0/","Organization Details", "Organization e.Form", "Associate Objects"};
	public static final String[] administerG3UAP_ManageReports_2nd = {"/G:H:0:6:0:6/","Report Access", "Report Parameters"};
	public static final String[] administerG3UAP_ReportDetails_3rd = {"/G:H:0:6:0:0:6:0/","Basic Details","Assign Report Organizations"}; 
	*/
	
//	public static final String[] externalLinksUAP_1st = {"External Links"};
//	public static final String[] externalLinksUAP_Sub1st = {"/G:H/","Connect to Grantium Communities"};
//	
	public static final String[] fullTextSearchUAP_1st = {"Full Text Search"};
//	public static final String[] fullTextSearchUAP_Sub1st = {"/G:H/","Submissions","Submission Search Level","Applicants", "View Submission Forms, PDF Export"};
//	
//	public static final String[] customAccessPointsUAP_1st = {"Custom Access Points"};
	
	public static enum EUAPLevel {
		first,
		second,
		third,
		forth
	}
	
	//-------------------------------------------------------------------------
	
	/*
	 * UAP Rights
	 */

	public static final boolean[] allRightsTrue= {true,true,true,true};

	public static final boolean[] allRightsFalse= {false,false,false,false};
	
	//Single UAPs
	public static final boolean[] createOnly= {true,false,false,false};
	
	public static final boolean[] readOnly= {false,true,false,false};
	
	public static final boolean[] updateOnly= {false,false,true,false};
	
	public static final boolean[] deleteOnly= {false,false,false,true};
	
	//Double UAPs
	public static final boolean[] createRead= {true,true,false,false};

	public static final boolean[] createUpdate = {true,false,true,false};

	public static final boolean[] createDelete= {true,false,false,true};

	public static final boolean[] readUpdate = {false,true,true,false};

	public static final boolean[] readDelete = {false,true,false,true};
	
	public static final boolean[] updateDelete= {false,false,true,true};
	
	//Triple UAPs	
	public static final boolean[] createReadUpdate= {true,true,true,false};
	
	public static final boolean[] createReadDelete= {true,true,false,true};
	
	public static final boolean[] createUpdateDelete= {true,false,true,true};
	
	public static final boolean[] readUpdateDelete= {false,true,true,true};
	
	//Default group UAP
	public static final int[][][] defaultGrpUAP= {
		{{0,1,0,0}, {0,0,1,0},{0,1,0,0}},
		{{1,1,1,0}},
		{{0,1,0,0},{0,1,0,0},{0,0,0,0},{0,1,0,0},{0,1,1,0},{1,1,1,0},{0,1,0,0},{1,0,0,0},{0,1,1,0},{0,0,0,1}},
		{{0,1,0,0},{0,0,1,0},{0,1,0,0}},
		{{0,1,0,0},{1,0,1,0},{1,0,0,0}},
		{{0,1,0,0},{0,0,0,0},{0,0,1,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,1,0,0},{1,0,0,0}}};
	
	


    //Temp
	public static final String[][] formCreate = {{"Preview Forms", "Clone Forms"}};
 
    
}
