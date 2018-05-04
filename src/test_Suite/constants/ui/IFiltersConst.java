package test_Suite.constants.ui;


public interface IFiltersConst {
	
	/***************************************************************
	 ***   				Dropdown Mode Strings    				 ***
	 ***************************************************************/
	
	//Filters Modes
	public static final String startsWith = "Starts with";
	
	public static final String contains = "Contains";
	
	public static final String endsWith = "Ends with";
	
	public static final String exact = "Exact";
	
	public static final String date = "On";
	
	public static final String filterModes[] = {"Starts with", "Contains", "Ends with", "Exact"};
	
	//Projects Status
	public static final String openProjView = "Open Projects";
	
	public static final String closeProjView = "Closed Projects";
	
	public static final String allProjView = "All Projects";
	
	//Submission Date Range Mode	
	public static final String onLastSub = "On";
	
	public static final String beforeLastSub = "Before";
	
	public static final String onBeforeLastSub = "On or Before";
	
	public static final String onAfterLastSub = "On or After";
	
	public static final String afterLastSub = "After";
	
	public static final String betweenLastSub = "Between";
	
	public static final String submissionVersion_LatestVersion = "Latest Version";
	
	public static final String submissionVersion_AllVersion = "All Versions";
	
	public static final String submissionsStatus_All_StatusSubView = "All";	
	
	public static final String submissionsStatus_Ready_StatusSubView = "Ready";	
	
	public static final String submissionsStatus_InProgress_StatusSubView = "In Progress";	
	
	public static final String submissionsStatus_Complete_StatusSubView = "Complete";	
	
	public static final String submissionsStatus_NotUsed_StatusSubView = "Not Used";
	
	public static final String versionNumber_LessThan = "Less Than";
	public static final String versionNumber_LessThanOrEqualTo = "Less Than or Equal To";
	public static final String versionNumber_EqualTo = "Equal To";
	public static final String versionNumber_GreaterThanOrEqualTo = "Greater Than or Equal To";
	public static final String versionNumber_GreaterThan = "Greater Than";
	public static final String versionNumber_Between = "Between";
	
	
	/***************************************************************
	 ***   PAP List Filters Fields and Dropdown Indexes    ***
	 ***************************************************************/
	public static final String pap_ProgramIdent_Lbl = "Program Identifier";
	public static final String pap_ProgramOfficer_Lbl = "Program Officer";
	public static final String pap_ProgramStartDate_Lbl = "Program Start Date";
	public static final String pap_ProgramEndDate_Lbl = "Program End Date";
	public static final String pap_ProgramStatus_Lbl = "Status";
	public static final String pap_ProgramView_Lbl = "View";
	public static final String pap_ProgramOrganization_Lbl = "Organization";
	public static final String pap_FoppName_Lbl = "Funding Opportunity Name";
	public static final String pap_ProgramDateSubmitted_Lbl = "Date Submitted";
	public static final String pap_ProgramName_Lbl = "Program Name";
	public static final String pap_eFormName_Lbl = "e.Form Name";
	public static final String pap_ApprovalStatus_Lbl = "Approval Status";
	public static final String pap_FoppIdent_Lbl = "Funding Opportunity Identifier";
	public static final String pap_StepName_Lbl = "Step Name";
	public static final String pap_AssignmentType_Lbl = "Assignment Type";
	public static final String pap_SubmissionStatus_Lbl = "Submission Status";
	public static final String pap_IncludeSubNotUsed_Lbl = "Include Submissions Not Used In Previous Steps";
	public static final String pap_IncludeAssociateSub_Lbl = "Include Associated Submission(s)";

	/************************************************************************************
	 ***   Grant Programs Administration Lists Filters Fields and Dropdown Labels    ***
	 ************************************************************************************/
	
	public static final String gpa_FundingOppIdent_Lbl = "Funding Opportunity Identifier";	
	public static final String gpa_FundingOppOfficer_Lbl = "Funding Opportunity Officer";	
	public static final String gpa_FundingOppStartDate_Lbl = "Funding Opportunity Start Date";	
	public static final String gpa_FundingOppEndDate_Lbl = "Funding Opportunity End Date";
	public static final String gpa_Staus_Lbl = "Status";	
	public static final String gpa_View_Lbl = "View";
	public static final String gpa_FormIdent_Lbl = "e.Form Identifier";
	public static final String gpa_FormType_Lbl = "e.Form Type";
	public static final String gpa_PublicationStatus_Lbl = "Publication Status";	
	public static final String gpa_DocName_Lbl = "Document Name";
	public static final String gpa_DocUploadDate_Lbl = "Document Uploaded Date";
	public static final String gpa_Organization_Lbl = "Organization";
	
	public static final String gpa_OriginalVersion_Lbl = "Original Version";
	public static final String gpa_Version_Lbl = "Version";
	public static final String gpa_LastVersion_Lbl = "Last Version";
	public static final String gpa_AmendmentVersion_Lbl = "Amendment Version";
	
	public static final String gpa_AmendmentStatus_Lbl = "Amendment Status";
	
	public static final String gpa_AmendmentId_Lbl = "Amendment ID";

	/***********************************************************************
	 ***   Grant Management Lists Filters Fields and Dropdown Labels    ***
	 ***********************************************************************/
	
	public static final String grantManagement_ApplicantNumber_Lbl = "Applicant Number";
	public static final String grantManagement_ApplicantName_Lbl = "Applicant Name";
	public static final String grantManagement_ApplicantType_Lbl = "Applicant Type";
	public static final String grantManagement_ProjectNumber_Lbl = "Project Number";
	public static final String grantManagement_ProjectName_Lbl = "Project Name";	
	public static final String grantManagement_FundingOppName_Lbl = "Funding Opportunity Name";	
	public static final String grantManagement_StepName_Lbl = "Step Name";	
	public static final String grantManagement_SubmissionDate_Lbl = "Date Submitted";
	public static final String grantManagement_ProjectStatus_Lbl = "Project Status";
	public static final String grantManagement_assignmentType_Lbl = "Assignment Type";
	public static final String grantManagement_AssociatedSub_Lbl = "Include Associated Submission(s)";
	public static final String grantManagement_LastSubmission_Lbl = "Last Submission";
	public static final String grantManagement_OriginalDateSubmitted_Lbl = "Original Date Submitted";
	public static final String grantManagement_AmendmentSubmitted_Lbl = "Amendment Submitted";
	public static final String grantManagement_View_Lbl = "View";
	public static final String grantManagement_SubmissionStatus_Lbl = "Submission Status";
	public static final String grantManagement_SubmissionVersion_Lbl = "Submission Version";
	public static final String grantManagement_Version_Lbl = "Version";
	public static final String grantManagement_AssignmentType_Lbl = "Assignment Type";
	public static final String grantManagement_NotUsedSubmissions_Lbl = "Include Submissions Not Used In Previous Steps";
	public static final String grantManagement_ReportName_Lbl = "Report Name";
	public static final String grantManagement_DateAdded_Lbl = "Date Added";	
	public static final String grantManagement_ActivityType_Lbl = "Activity Type";
	public static final String grantManagement_ActivityPurpose_Lbl = "Activity Purpose";
	public static final String grantManagement_ActivityDate_Lbl = "Activity Date";
	public static final String grantManagement_DueDate_Lbl = "Due Date";
	public static final String grantManagement_ActivityStatus_Lbl = "Activity Status";	
	public static final String grantManagement_NotificationName_Lbl = "Notification Name";
	public static final String grantManagement_NotificationSubject_Lbl = "Notification Subject";
	public static final String grantManagement_DateSent_Lbl = "Date Sent";	
	
	public static final String grantManagement_Status_Lbl = "Status";
	public static final String grantManagement_StatusDate_Lbl = "Status Date";	
	public static final String grantManagement_AssociatedUser_Lbl = "Associated User";
	public static final String grantManagement_OverrideDate_Lbl = "Override Date";

	public static final String administration_LookupStatus_Lbl = "Lookup Status";
	public static final String administration_LookupName_Lbl = "Lookup Name";
	public static final String administration_Organization_Lbl = "Organization";
	public static final String administration_LookupValueStatus_Lbl = "Lookup Value Status";
	public static final String administration_LookupValueIdent_Lbl = "Lookup Value Identifier";
	public static final String administration_LookupValueName_Lbl = "Lookup Value Name";
	public static final String administration_LastName_Lbl = "Last Name";
	public static final String administration_FirstName_Lbl = "First Name";
	public static final String administration_UserName_Lbl = "User Name";
	public static final String administration_UserType_Lbl = "User Type";
	public static final String administration_StatusType_Lbl = "Status Type";
	public static final String administration_GroupName_Lbl = "Group Name";
	public static final String administration_GroupType_Lbl = "Group Type";
	public static final String administration_MemberOfGroup_Lbl = "Member Of Group";
	public static final String administration_OrgIdent_Lbl = "Organization Identifier";
	public static final String administration_Type_Lbl = "Type";
	public static final String administration_Status_Lbl = "Status";
	public static final String administration_Officer_Lbl = "Officer";
	public static final String administration_ReportIdent_Lbl = "Report Identifier";
	public static final String administration_DateAdded_Lbl = "Date Added";	
	
	public static final String administration_EFormIdent_Lbl = "e.Form Identifier";
	public static final String administration_FormletIdent_Lbl = "Formlet Identifier";
	public static final String administration_EFormFieldIdent_Lbl = "e.Form Field Identifier";
	public static final String administration_ReportingIdent_Lbl = "Reporting Identifier";
	public static final String administration_ReportingStatus_Lbl = "Reporting Field Status";
	
//	public static final String projects_projectStatus_dropDowmId = "main:projectListSubview:projectListForm:filters:6:dropdownFilterItem_integer";
//	public static final String projects_projectStatus = "Open Projects";
//	public static final String projectdetailsLink_Id ="main:projectListSubview:projectListForm:_idJsp79:caseDataTable_row_0:editCaseLink";
//	
//	public static final String readyToarchive_fundingOppurtunityIdentifier_lbl = "Funding Opportunity Identifier";
//	public static final String readyToarchive_fundingOppurtunityIdentifier_txtField_id = "main:dataArchiveListSubview:dataArchiveListForm:filters:0:stringFilterItem";
//	public static final String readyToarchive_fundingOppurtunityIdentifier_dropDown_id = "main:dataArchiveListSubview:dataArchiveListForm:filters:0:stringFilterMode";
//	public static final String readyToarchive_fundingOppurtunityStartDate = "Funding Opportunity Start Date";
//	public static final String readyToarchive_fundingOppurtunityStartDate_dropDownId ="main:dataArchiveListSubview:dataArchiveListForm:filters:1:dateFilterMode1";
//	public static final String readyToarchive_fundingOppurtunityStartDate_txtField_Id="main:dataArchiveListSubview:dataArchiveListForm:filters:1:date1";
//	public static final String readyToarchive_fundingOppurtunityEndDate = "Funding Opportunity End Date";
//	public static final String readyToarchive_fundingOppurtunityEndDate_dropDown_Id="main:dataArchiveListSubview:dataArchiveListForm:filters:2:dateFilterMode1";
//	public static final String readyToarchive_fundingOppurtunityEndDate_txtField_Id = "main:dataArchiveListSubview:dataArchiveListForm:filters:2:date1";
//	
//	public static final String da_projectName_lbl = "Project Name";
//	public static final String da_projectName_TextField_Id = "main:archiveProjectListSubview:archiveProjectListForm:filters:0:stringFilterItem";
//	public static final String da_projectName_dropDown_Id = "main:archiveProjectListSubview:archiveProjectListForm:filters:0:stringFilterMode";
//	
//	public static final String da_projectNumber_lbl ="Project Number";
//	public static final String da_projectNumber_txtField_Id ="main:archiveProjectListSubview:archiveProjectListForm:filters:1:stringFilterItem";
//	public static final String da_projectNumber_dropdown_Id ="main:archiveProjectListSubview:archiveProjectListForm:filters:1:stringFilterMode";
//	
//	public static final String da_projectStatus_lbl="Project Status";
//	public static final String da_projectStatus_dropdownId="main:archiveProjectListSubview:archiveProjectListForm:filters:2:dropdownFilterItem_integer";
//	
//	public static final String processArchive_Id = "main:archiveProjectListSubview:archiveProjectListForm:processArchiveLink";
//	public static final String cancelArchive_Id = "main:archiveProjectListSubview:archiveProjectListForm:cancelArchiveLink";
//	public static final String processArchive_checkBox_Id ="main:archiveProjectListSubview:archiveProjectListForm:_idJsp89:archivedProjectDataTable_row_0:disablecheckbox";
//	public static final String processArchive_image = "main:archiveProjectListSubview:archiveProjectListForm:importLink";
//	
//	public static final String archiveLog_projectName_Lbl = "Project Name";
//	public static final String archiveLog_projectName_txtField_Id = "main:projectListSubview:projectListForm:filters:0:stringFilterItem";
//	public static final String archiveLog_projectName_dropDown_Id = "main:projectListSubview:projectListForm:filters:0:stringFilterMode";
//	
//	public static final String archiveLog_projectNumber_lbl ="Project Number";
//	public static final String archiveLog_projectNumber_txtField_Id ="main:projectListSubview:projectListForm:filters:1:stringFilterItem";
//	public static final String archiveLog_projectNumber_dropDown_Id ="main:projectListSubview:projectListForm:filters:1:stringFilterMode";
//	
//	public static final String archiveLog_applicantName_lbl ="Applicant Name";
//	public static final String archiveLog_applicantName_txtField_Id ="main:projectListSubview:projectListForm:filters:2:stringFilterItem";
//	public static final String archiveLog_applicantName_dropDown_Id ="main:projectListSubview:projectListForm:filters:2:stringFilterMode";
//	
//	public static final String archiveLog_applicantNumber_lbl ="Applicant Number";
//	public static final String archiveLog_applicantNumber_txtField_Id ="main:projectListSubview:projectListForm:filters:3:stringFilterItem";
//	public static final String archiveLog_applicantNumber ="main:projectListSubview:projectListForm:filters:3:stringFilterMode";
//	
//	public static final String archiveLog_fundingOppurtunityName_lbl ="Funding Opportunity Name";
//	public static final String archiveLog_fundingOppurtunityName_txtField_Id ="main:projectListSubview:projectListForm:filters:4:stringFilterItem";
//	public static final String archiveLog_fundingOppurtunityName_dropDown_Id ="main:projectListSubview:projectListForm:filters:4:stringFilterMode";
//	
//	public static final String archiveLog_stepName_lbl ="Step Name";
//	public static final String archiveLog_stepName_txtField_Id ="main:projectListSubview:projectListForm:filters:5:stringFilterItem";
//	public static final String archiveLog_stepName_dropDown_Id ="main:projectListSubview:projectListForm:filters:5:stringFilterMode";
//	
//	public static final String archiveLog_projectStatus_lbl="Project Status";
//	public static final String archiveLog_projectStatus_dropdownId="main:projectListSubview:projectListForm:filters:6:dropdownFilterItem_integer";
//	
//	public static final String archiveLog_projectOfficerProjects_lbl="Show Only My Project Officer Projects";
//	public static final String archiveLog_projectOfficerProjects_checkBoxId="main:projectListSubview:projectListForm:filters:7:checkboxFilterItem";
//
//	/***************************************************************
//	 ***   In Basket List Filters Fields and Drop down Labels    ***
//	 ***************************************************************/
//	
//	public static final String fundingOppInBasket_ApplicantNumber_Lbl = "Applicant Number";
//	public static final String fundingOppInBasket_ApplicantName_Lbl = "Applicant Name";	
//	public static final String fundingOppInBasket_ProjectNumber_Lbl = "Project Number";
//	public static final String fundingOppInBasket_ProjectName_Lbl = "Project Name";	
//	public static final String fundingOppInBasket_FundingOppName_Lbl = "Funding Opportunity Name";	
//	public static final String fundingOppInBasket_SubmissionDate_Lbl = "Date Submitted";
//	public static final String fundingOppInBasket_ProjectStatus_Lbl = "Project Status";
	
	/***************************************************************
	 *** 	Projects List Filters Fields and Drop down Labels 	 ***
	 ***************************************************************/
	
	public static final String projects_ProjectNumber_Lbl = "Project Number";	
	public static final String projects_ProjectName_Lbl = "Project Name";			
	public static final String projects_ApplicantNumber_Lbl = "Applicant Number";	
	public static final String projects_ApplicantName_Lbl = "Applicant Name";
	public static final String projects_FOPPName_Lbl = "Funding Opportunity Name";	
	public static final String projects_StepName_Lbl = "Step Name";	
	public static final String projects_ProjectStaus_Lbl = "Project Status";
	public static final String projects_InProgress = "In Progress";	
	public static final String projects_SubmisisonHistory_StepStatus="Complete";
	
	/***************************************************************
	 ***   Amendments List Filters Fields and Drop down Indexes    
	 ***************************************************************/
	
	public static final String amendments_AllAmendmentsView = "All Amendments";	
	public static final String amendments_InProgressView = "In Progress";	
	public static final String amendments_CompleteView = "Complete";	
	public static final String amendments_CancelView = "Cancelled";	
	public static final String amendments_TransferView = "Transferred";
	public static final String amendments_RequestedView="Requested";
	
	public static final String amendmentID_LessThan = "Less Than";
	public static final String amendmentID_LessThanOrEqualTo = "Less Than or Equal To";
	public static final String amendmentID_EqualTo = "Equal To";
	public static final String amendmentID_GreaterThanOrEqualTo = "Greater Than or Equal To";
	public static final String amendment_GreaterThan = "Greater Than";
	public static final String amendment_Between = "Between";
	
	public static final String amendmentId_Lbl="Amendment ID";
	
//	public static final String amendment_betweenID="main:listAmendments:_idJsp31:filters:6:between_and_2";
	
	
	
	
	public static final String[] amendments_Views = {"All Amendments","In Progress","Complete","Cancelled","Transferred"};
	
	/***************************************************************
	 *** Evaluate Submission List Filters Fields and Drop down Indexes
	 ***************************************************************/
	
	public static final String evaluateSubmissions_All_StatusSubView = "All";	
	public static final String evaluateSubmissions_Ready_StatusSubView = "Ready";	
	public static final String evaluateSubmissions_InProgress_StatusSubView = "In Progress";	
	public static final String evaluateSubmissions_Complete_StatusSubView = "Complete";		
	public static final String evaluateSubmissions_NotUsed_StatusSubView = "Not Used";
	
	/*************************************************************
	 ***	My Reports List Filters Field and Drop down Indexes
	 *************************************************************/
	
	public static final String reportsStatusAll = "All";	
	public static final String reportStatusActive = "Active";	
	public static final String reportStatusInactive = "Inactive";
	
	/***************************************************************
	 ***   Lookup List Filters Fields and Drop down Indexes    
	 ***************************************************************/
	
	public static final String allLookupStat = "All";	
	public static final String activeLookupStat = "Active";	
	public static final String inactiveLookupStat = "Inactive";	
	
	public static final String refTableName = "Reference Table Name";

	/***************************************************************
	 *** Users List Filters Fields and Drop down Indexes
	 ***************************************************************/
	
	public static final String users_AllUsers_StatusView = "All Users";	
	public static final String users_activeUsers_StatusView = "Active Users";	
	public static final String users_InactiveUsers_StatusView = "Inactive Users";
	
	public static final String users_POUsers_TypeView = "Program Office Users";	
	public static final String users_FOUsers_TypeView = "Front Office Users";

	/***********************************************************************
	 *** Users List Filters Fields and Drop down Names and Portions of Names
	 ***********************************************************************/
	
	public static final String txtFilterItem = ":stringFilterItem";	
	public static final String txtFilterMode=  ":stringFilterMode";	
	public static final String datFilterMode =  ":dateFilterMode";	
	public static final String datDate       =  ":date";	
	
	/************************************************************************
	 ***   Front Office Submissions List Filters Fields and Drop down Indexes    
	 ************************************************************************/
	
	public static final String fo_Submissions_ProjectName_Lbl = "Applicant Project Name";
	public static final String fo_Submissions_DateSubmitted_Lbl = "Date Submitted";
	public static final String fo_Submissions_ProjectStatus_Lbl = "Project Status";
	public static final String fo_Submissions_SubmissionVersion_Lbl = "Submission Version";
	public static final String fo_Projects_FundingOppName_Lbl = "Funding Oppportunity Name";
	public static final String fo_Applicant_Individual_ddVal = "Individual";
	
	/***************************************************************
	 *** 	Audit Reporting Projects List Filters Fields and Drop down Labels 	 ***
	 ***************************************************************/
	
	public static final String audit_Event_Lbl = "Event";
	public static final String audit_Date_Lbl = "Date";
	public static final String audit_ByUser_Lbl = "By User";
	public static final String audit_Entity_Lbl = "Entity";
	public static final String audit_EntityId_Lbl = "Entity Id";
	
	/***************************************************************
	 *** Users List Filters Fields and Dropdown Indexes
	 ***************************************************************/
	
	public static final String audit_Event_Login = "User Login Successful";
	public static final String audit_Event_LoginFailed = "User Login Failed";
	public static final String audit_Event_Logout = "User Logged Out";
	public static final String audit_Entity_Project = "Project";
	public static final String audit_Entity_Applicant = "Applicant";
	
		
	public static final String[] standardFilterLabels = {grantManagement_ProjectName_Lbl,grantManagement_FundingOppName_Lbl,grantManagement_StepName_Lbl};
	
	public static enum EProjStdrdFltrLbls {
		
		projName,foppName,stepName
	}
	
	
	//*********************** Div Id **********************************
	
	public static final String fo_SubmissionFilterDiv = "g3-form:filterAndFilterControls";
	public static final String fo_SubmissionFilterSpan = "g3-form:filterAndFilterControls";
	public static final String eListFiltersElement_Id = "main:displayListFormlet:_idJsp72:filters";
	
}
