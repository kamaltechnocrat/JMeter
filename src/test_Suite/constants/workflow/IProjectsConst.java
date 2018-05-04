package test_Suite.constants.workflow;


public interface IProjectsConst {
	
	
	
	public static final String reviewSubDatePicker = "g3-form:eFormFieldList:0:datePicker_input";
	public static final String reviewSubRevDropdown = "g3-form:eFormFieldList:1:numericDropdown_input";
	public static final String reviewSubCommentLongText = "g3-form:eFormFieldList:2:textArea_Below";
	public static final String reviewSubScoreTextbox = "g3-form:eFormFieldList:3:textBox";
	
	public static final String approvalSubDatePicker = "g3-form:eFormFieldList:1:datePicker_input";
	public static final String approvalSubApproveDropdown = "g3-form:eFormFieldList:0:numericDropdown_input";
	public static final String approvalSubCommentLongText = "g3-form:eFormFieldList:2:textArea_Below";
	
	public static final String submissionApplProgram  = "/submissionAppProgramMenu/";
	public static final String poProjectName          = "/poProjectName/";
	public static final String projectOrganization    = "/projectOrganization/";
	
	public static final String projectCaseNumber      = "/caseNumber/";
	public static final String projectStepAdminGroups = "/stepAdminGroupsList/";
	public static final String projectSelCaseStaff    =  "/selectedCaseStaff/";
	
	public static final String projectDataGridFormTableBodyId = "/dataGrid:tbody_element/";
	
	public static final String projectDataGridTableId =  "g3-form:eFormFieldList:0:dataGrid_data";
	
	public static final String projectDataGrid_DivClassValue = "g3-scrollingGrid";
	
//	public static final String foProjectSubmissionView_DropdownId = "main:foSubmissionsList:foSubmissionsListForm:filters:2:dropdownFilterItem";
//	public static final String foProjectSubmissionProjName_DropdownId = "main:foSubmissionsList:foSubmissionsListForm:filters:0:dropdownFilterItem";
//	public static final String foProjectSubmissionVersion_DropdownId = "main:foSubmissionsList:foSubmissionsListForm:filters:3:dropdownFilterItem";
//	public static final String foProjectStepName_TextFieldId = "main:foSubmissionsList:foSubmissionsListForm:filters:1:stringFilterItem";
//	public static final String foProjectStepName_DropdownId = "main:foSubmissionsList:foSubmissionsListForm:filters:1:stringFilterMode";
	
	public static final String project_SubmissionHistory_formId="main:caseListSubview:submissionHistoryList";
	
	public static final String project_NotificationLog_formId="main:caseNotificationLogListSubview:caseNotificationLogListForm";
	
	public static final String project_SubmissionHistory_ImageAlt="View Submission History";
	
	public static final String project_NotificationLog_ImageAlt="View Notification Log";
	
	public static final String project_AmendmentHistoryTab_Id="main:viewAmendment:viewAmendmentForm:submissionHistoryTab_headerCell";
	
	
	public static final String project_AmendmentHistory_TableId="main_viewAmendment_viewAmendmentForm_editAmendmentTabbedPane";
	
	public static final String project_AmendmentHistory_FormId="main:viewAmendment:viewAmendmentForm";
	

	public static final String project_SubmissionHistory_AmendmentIconId="main:caseListSubview:submissionHistoryList:historyDataTable:0:j_id_5w";
	
	public static final String project_NotificationLog_NotificationId="caseNotificationLogListForm:notificationLogTable:0:j_id_3t";
	
	//######## Assign Evaluators List
	
	public static final String project_AssignEval_AssignEvalLinkTtl = "Assign Evaluator";
	public static final String projectAssignEval_AvailableEvals_M2MId = "assignEvaluator:availableStaff";
	public static final String projectAssignEval_SelectedEvals_M2MId = "assignEvaluator:selectedStaff";
	
	//###### FO Projects List
	public static final String foProject_ProjectsStatus_DropdownId = "g3-form:projectViewStatus_input";
	public static final String foProject_FundingOppName_DropdownId = "g3-form:programs_input";
	public static final String poProject_FundingOppName_DropdownId = "main:evaluateProjectSubview:evaluateProjectsForm:foppField";
	public static final String foProject_ProjectName_TxtFldId = "g3-form:foProjectName";
	
	
	//#### For Project Officer Assignment
	public static final String projectOfficers_AssignUser_Dropdown_Id = "caseTabbedForm:editCaseTabbedPane:selectedCaseStaff_input"; //5.3
	
	public static final String projectOfficers_SelectAll_Checkbox_Id = "/:caseStaffAssignment:selectAllToggle/";  //main:manageCaseView:caseTabbedForm:editCaseStaffView:editCaseStaff:_idJsp97
	
	
	//###### For DataGrid_BF_CalcVal
//	public static final String dgStartTextField_Id = "main:dFo:_idJsp57:fi:0:dataGrid:";
//	public static final String dgMiddleTextField_Id = ":_idJsp164:";
//	public static final String dgEndTextField_Id = ":gridCell_NumericEntry";
//	
//	public static final String dgFieldStart_TextField_Id = "main:displaySubmissionListFormlet:_idJsp57:fi:";
	public static final String dgFieldEnd_TextField_Id = ":textBox/";
	
//	public static final String dgTableId = "/dataGrid/";
	
	
	public static enum EWizardParams {
		
		FOPP_NAME,
		SEARCH_WORD,
		IS_NEW_PROFILE,
		USER_NAME,
		IS_NEW_APPLICANT,
		ORG_TYPE,
		ORG_NAME,
		ORG_NUM,
		DO_FILL_WORKSPACE,
		CREATE_PROJ,
		SUBMIT_PROJ		
	}
	
	public static enum ERefSub {
		STEP, USER, SCORE
	}
	
	public static final String cfgFOPPIdent = "Post-Award-Step-Amendment";
	
	public static final String cfgPrefix = "CFG-";
	
	public static final String cfgAppSubStep = "Submit Application";
	
	public static final String cfgPOSubStep = "Program-Office-Submission";
	
	public static final String cfgReviewStep = "Grant Decision"	;
	
	public static final String cfgAwardStep = "Award Schedule";
	
	public static final String cfgPostAwardStep = "Grant-Administration";
	
	public static final String cfgPostAwardSubStep = "Post-Award-Submission";
	
	public static final String cfgCloseOutStep = "Closeout-Approval ";
	
	public static final String cfgInitialStep = "CFG-Post-Award-Submission";
	
	public static final String[] cfgPA_ReviewSteps = {"Quarterly Report Review Shared","Annual Report Review Quorum=2","Financial Report Review Quorum=1"};
	
	public static final String[] cfgPA_ApprovalSteps = {"Quarterly Report Approval Shared","Annual Report Approval Quorum=2","Financial Report Approval Quorum=1"};
	
	public static final String[] cfgReportNames = {"Quarterly Report","Annual Report","Financial Report"};
	
	
	
	public static enum EcfgReportsTypes {
		QUARTERLY,ANNUAL,FINANCIAL
	}
	
	public static enum EcfgProjectsName {
		COMPLETE,NOTCOMPLETE,NOTAPPROVED,NOTSTARTED
	}
	
	public static String[] cfgProjectsName = {"-CFG-Completed-PA-Step","-CFG-NotCompleted-PA-Step","-CFG-NotApproved-PA-Step","-CFG-Not-Started-PA-Step"};
	
	
	//****************************** Bulk Evaluation **********************************
	
	public static final String bulkEval_OptWarning_DivId = "bulkEvalSubmissionsForm:operationMessages";
	
	public static final String bulkEval_BackgroundWarning_SpanId = "bulkEvalSubmissionsForm:backgroundOperationMessage";
	
	//************* Schedules e.Form List Titles ******************
	
	public static final String gps_AgreementDetails_TextFldTtl = "Enter Any Text";
	
	public static final String gps_SubmissionDetails_SubNameTtl = "Submission Name";
	
	public static final String gps_SubmissionDetails_PubStartDateTtl = "Publication Start Date";
	
	public static final String gps_SubmissionDetails_ScheduleDueDateTtl = "Schedule Due Date";
	
	public static final String gps_SubmissionDetails_PubEndDateTtl = "Publication End Date";
	
	public static final String gps_SubmissionDetails_SubFormTtl = "Submission Form";
	
	public static final String gps_SubmissionDetails_RequiredTtl = "Required?";
	
	public static final String gps_SubmissionDetails_POOnlyTtl = "Program Office Only?";
	
	public static final String gps_SubmissionDetails_ActiveTtl = "Active";
	
	//*************************Schedules e.Form Variables
	
	public static final String odss_Payment_ddVal = "Payment"; 
	
	
	
	//******************************** Front Office User Account ids ***************************
	
	public static final String foWizardUserName = "g3-form:userName";
	
	public static final String foWizardPwd = "g3-form:password";
	
	public static final String fo_UserAccount_FName_TxtId = "g3-form:First_Name";
	
	public static final String fo_UserAccount_MName_TxtId = "g3-form:Middle_name";
	
	public static final String fo_UserAccount_LName_TxtId = "g3-form:Last_name";
	
	public static final String fo_UserAccount_Email_TxtId = "g3-form:email";
	
	public static final String fo_UserAccount_EmailConf_TxtId = "g3-form:emailConfirm";
	
	public static final String fo_UserAccount_Language_TxtId = "g3-form:language_input";
	
	public static final String fo_UserAccount_UName_TxtId = "g3-form:User_Name";
	
	public static final String fo_UserAccount_Pwd_TxtId = "g3-form:editPassword";
	
	public static final String fo_UserAccount_PwdConf_TxtId = "g3-form:passwordConfirm";
	
	public static final String fo_UserAccount_Question_TxtId = "g3-form:confirmQuestion";
	
	public static final String fo_UserAccount_Answer_TxtId = "g3-form:confirmAnswer";
	
	
	//****************************** PO Applicants ****************************************

	
	public static final String po_UserAccount_FName_TxtId = "poEditProfile:form:First_Name";
	
	public static final String po_UserAccount_MName_TxtId = "poEditProfile:form:Middle_name";
	
	public static final String po_UserAccount_LName_TxtId = "poEditProfile:form:Last_name";
	
	public static final String po_UserAccount_Email_TxtId = "poEditProfile:form:email";
	
	public static final String po_UserAccount_EmailConf_TxtId = "poEditProfile:form:emailConfirm";
	
	public static final String po_UserAccount_Language_DropdownId = "poEditProfile:form:language_input";
	
	public static final String po_UserAccount_UName_TxtId = "poEditProfile:form:User_Name";
	
	public static final String po_UserAccount_Pwd_TxtId = "poEditProfile:form:password";
	
	public static final String po_UserAccount_PwdConf_TxtId = "poEditProfile:form:passwordConfirm";
	
	public static final String po_UserAccount_Question_TxtId = "poEditProfile:form:confirmQuestion";
	
	public static final String po_UserAccount_Answer_TxtId = "poEditProfile:form:confirmAnswer";
	
	//*********************** GPS fields Tag Id **********************************************
	
	public static final String gps_FielsId_Prefix = "g3-form:eFormFieldList:";
	
	public static final String gps_ListTableId                    = "g3-form:data_withLetterFilter_data"; //5.2
	
	public static final String gps_Budget_DataGrid_TableId		  = "g3-form:eFormFieldList:0:dataGrid_data"; //6.0
	
	public static final String gps_Milestone_NameTxtId            = gps_FielsId_Prefix.concat("0:textBox");
	public static final String gps_Milestone_StartDateId          = gps_FielsId_Prefix.concat("1:datePicker_input");
	public static final String gps_Milestone_EndDateId            = gps_FielsId_Prefix.concat("2:datePicker_input");
	
	public static final String gps_Expense_Category_DropdownId    = gps_FielsId_Prefix.concat("0:numericDropdown_input");
	public static final String gps_Expense_SubCategory_DropdownId = gps_FielsId_Prefix.concat("1:numericDropdown_input");
	
	public static final String gps_Contribute_Type_DropdownId   = gps_FielsId_Prefix.concat("0:numericDropdown_input");
	public static final String gps_Contribute_NameTxtId         = gps_FielsId_Prefix.concat("1:textBox");
	public static final String gps_Contribution_Type_DropdownId = gps_FielsId_Prefix.concat("2:numericDropdown_input");
	
	public static final String gps_Schedule_NameTxtId           = gps_FielsId_Prefix.concat("0:textBox");
	public static final String gps_Schedule_StartDateId         = gps_FielsId_Prefix.concat("1:datePicker_input");
	public static final String gps_Schedule_DueDateId           = gps_FielsId_Prefix.concat("2:datePicker_input");
	public static final String gps_Schedule_EndDateId           = gps_FielsId_Prefix.concat("3:datePicker_input");
	public static final String gps_Schedule_Form_DropdownId     = gps_FielsId_Prefix.concat("4:numericDropdown_input");
	public static final String gps_Schedule_Required_CheckboxId = gps_FielsId_Prefix.concat("5:booleanProperty_input");
	public static final String gps_Schedule_POOnly_CheckboxId   = gps_FielsId_Prefix.concat("6:booleanProperty_input");
	public static final String gps_Schedule_Forecast_TxtId      = gps_FielsId_Prefix.concat("7:textBox");
	
	public static final String gps_Result_ProgTotal_TxtId       = gps_FielsId_Prefix.concat("0:textBox");
	public static final String gps_Result_Grp1_TxtId            = gps_FielsId_Prefix.concat("1:textBox");
	public static final String gps_Result_Grp2_TxtId            = gps_FielsId_Prefix.concat("2:textBox");
	public static final String gps_Result_VarianceTotal_TxtId   = gps_FielsId_Prefix.concat("3:textBox");
	
	public static final String frm_SummaryFormlet_TableBodyId 	= "g3-form:j_id_ds_data"; //6.0
	
	public static final String frm_FormletDetailsDivId = "g3-form:fi";
	public static final String onClick_optionsDiv = "/caseDataTable:";
	
	
	
	
}
