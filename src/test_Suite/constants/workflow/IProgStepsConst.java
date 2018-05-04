package test_Suite.constants.workflow;

public interface IProgStepsConst {
	
	// Field names
	public static final String step_StartId = "editStep:step:";
	public static final String step_Id             = step_StartId + "stepIdentifier";	
	public static final String step_Notes          = step_StartId + "stepNotes";	
	public static final String step_Type           = step_StartId + "stepTypeSelect";	
	public static final String step_Admin          = step_StartId + "stepAdministratorId";	
	public static final String step_Start_Date     = step_StartId + "stepStartDate";	
	public static final String step_End_Date       = step_StartId + "stepEndDate";	
	public static final String step_Is_Critical    = step_StartId + "criticalCheck";	
	public static final String step_Re_Execute     = step_StartId + "reExecutionOptionSelect";	
	public static final String step_Title_Start = step_StartId + "stepTexts:";	
	public static final String step_Title_End        = ":stepTitleText";	
	public static final String step_Prop_Form      = step_StartId + "properties:0:integerDropdown";		
	public static final String step_Prop_UseShared      = step_StartId + "properties:1:checkbox";
	public static final String step_Prop_Eval_Type = step_StartId + "properties:2:integerDropdown";	
	public static final String step_Prop_Amount    = step_StartId + "properties:3:textfield";	
	public static final String step_Prop_M2M_Available = step_StartId + "properties:0:M2M_AvailableItems";	
	public static final String step_Prop_M2M_Selected = step_StartId + "properties:0:M2M_SelectedItems";	
	public static final String step_Prop_Auto_Assign = step_StartId + "properties:4:checkbox";		
	public static final String step_Prop_COI = step_StartId + "properties:5:checkbox";	
	public static final String step_Decision_Prop_DataFrom = step_StartId + "properties:0:dropdown";	
	public static final String step_Decision_Prop_SkipToStep = step_StartId + "properties:1:dropdown";	
	public static final String step_Decision_Prop_Experssion = step_StartId + "properties:2:textAreafield";
	
	//Step Staff
	public static final String stepStaffShowAll_Id = "stepStaff:staff:showAllGroups_input";
	public static final String stepStaffOrg_Id = "stepStaff:staff:organization_input";
	public static final String stepStaff_AvailableStaff_Id = "stepStaff:staff:availableStaff";
	public static final String stepStaff_SelectedStaff_Id = "stepStaff:staff:selectedStaff";
	public static final String stepStaff_AccessSearch_Id = "stepStaff:staff:accessSearch";
	
	//Step Documents
	public static final String stepDocuments_AvailableDocument_Id = "/availableDocumenst/";
	public static final String stepDocuments_SelectedDocument_Id = "/selectedDocumenst/";
	
	//Form Access
	public static final String stepFormAccess_AvailableForms_Id = "/availableForms/";
	public static final String stepFormAccess_SelectedForms_Id = "/selectedForms/";
	
	//Form Data
	public static final String stepFormData_ApplicantProfile_Id = "/applicantForm/";
	public static final String stepFormData_ProgramForm_Id = "/programForm/";
	public static final String stepFormData_AvailableForms_Id = "/availableForms/";
	public static final String stepFormData_SelectedForms_Id = "/selectedForms/";
	
	// Names and Links
	public static final String STEP_ADD_NEW      = "Add Step";	
	public static final String STEP_VIEW_DETAILS = "View Step Details";	
	public static final String STEP_DELETE       = "Delete Step";	
	public static final String MANAGE_STEP_STAFF = "Manage Step Staff";	
	
	//** Step Types 
	public static final String stepType_ApplicantSubmission = "Applicant Submission";	
	public static final String stepType_POSubmission = "Program Office Submission";	
	public static final String stepType_FundOppSubmission = "Funding Opportunity Submission";	
	public static final String stepType_Review = "Review";
	public static final String stepType_Approval = "Approval";
	public static final String stepType_Award = "Award";
	public static final String stepType_PostAward = "Post-Award";
	public static final String stepType_InitialPASubmission = "Initial Post Award Submission Step";
	public static final String stepType_Decsision = "Decision";
	public static final String stepType_FundOppApproval = "Funding Opportunity Approval";
	public static final String stepType_WorkflowSuspension = "Workflow Suspension";
	
	//** Step Names in Planner 
	public static final String stepType_InPlanner_ApplicantSubmission = " (Applicant Submission)";	
	public static final String stepType_InPlanner_POSubmission = " (Program Office Submission)";	
	public static final String stepType_InPlanner_FundOppSubmission = " (Funding Opportunity Submission)";	
	public static final String stepType_InPlanner_Review = " (Review)";
	public static final String stepType_InPlanner_Approval = " (Approval)";
	public static final String stepType_InPlanner_Award = " (Award)";
	public static final String stepType_InPlanner_PostAward = " (Post-Award)";
	public static final String stepType_InPlanner_InitialPASubmission = " (Initial Post Award Submission Step)";
	public static final String stepType_InPlanner_Decsision = " (Decision)";
	public static final String stepType_InPlanner_FundOppApproval = " (Funding Opportunity Approval)";
	public static final String stepType_InPlanner_WorkflowSuspension = "(Workflow Suspension)";
	
	public static final String stepID_closingStepPA_ODSS = "j_id_1u:programPlannerTree:0_6_2_5_2_0:j_id_4g"; //"main:programPlanner:_idJsp31:planner:0:6:2:5:2:0:editStepStaffLink";
	public static final String stepID_expandStepPA_ODSS = "programPlanner:j_id_1v:planner:0:6:2:5:t242";//"main:programPlanner:_idJsp31:planner:0:6:2:5:t2";
	
	
}
