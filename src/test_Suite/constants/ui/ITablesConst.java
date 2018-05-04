package test_Suite.constants.ui;


public interface ITablesConst {

    //********** String Variables for all the Table IDs in the Lists ******/
	
	/*********** All Tables Lists Div and Table Id *********************/
	public static enum EReturnTypes {
		rowIndex, entriesCount, totalEntries, exists
	}
	
	public static final String tableListDiv_class = "ui-datatable-tablewrapper";
	
	public static final String widgetDiv_class = "ui-datalist ui-widget";
	
	public static final String tableList_Class = "listTable";
	
	public static final String tableListLetters_Class = "letterBoxBorder";
	
	public static final String tableListFooter_Class = "listFooterBorder";
	
	//***** Pagination Table ID **************
	public static final String paginationDivId = "/paginator_bottom/";
	
	//****** PAP Programs List *********
	public static final String pap_programsTableId = "/programDataTable/";
	
	//****** PAP Intake List************
	public static final String pap_IntakeTableId = "/listSubmissionsTable/";
	
	//***** PAP Assignment List *****************
	public static final String pap_AssignEvaluatorsTableId = "/assignmentDataTable/";
	
	//	**** PAP Evaluate Submission *****
    public static final String pap_EvaluateSubmissionsTableId = "/assignmentDataTable/";
    
    //******* PAP My In Basket Lists****************
    public static final String pap_MyProjectSubmissionsTableId = "/mibcs_SubmissionsTable/";
    
    public static final String pap_MyAssignedSubmissionsTableId = "/mibis_SubmissionsTable/";
    
    
    //************* Reference Submissions
    public static final String referenceSubmission_TableId = "g3-form:submissionListData_data"; //main:displaySubmissionListFormlet:_idJsp94
	
    //**** Funding Opportunities List*****
    public static final String fundingOpp_programsTableId = "programListForm:programDataTable_data";
    
//    public static final String fundingOpp_CloningEFormsDataTableId = "exporteForm:_idJsp31:form_frame";
    
    public static final String fundingOpp_programsFormId = "/programListForm/";       
        
    //**** Forms List*****
    public static final String formsTableId = "formsListForm:formsList_data";//"main:listForms:formsListForm:_idJsp94:formsList";
    
    //**** Documents List***
    public static final String documentsTableId = "j_id_1v:documentDataTable_data";
    
    //**** Intake List *****
    public static final String fundingOpp_IntakeTableId = "/listSubmissionsTable/";
    
    public static String projectOfficerAssignmentTableId = "caseTabbedForm:editCaseTabbedPane:caseStaffAssignment_data";//main:manageCaseView:caseTabbedForm:editCaseStaffView:editCaseStaff:_idJsp97
            
    //**** Projects List ****
    public static final String projectsTableId = "projectListForm:caseDataTable_data";
    
    public static final String transferProjectTableId = "transferProjectForm:applicantDataTable_data";
    
    public static final String submissionHistoryTableId = "submissionHistoryList:historyDataTable_data";
    
    public static final String projectActivitiesTableId = "caseActivityListForm:caseActivityDataTable_data";
    
    public static final String notificationLogTableId = "/notificationLogTable/";
    
    public static final String projectSubmissionHistoryTableId="/historyDataTable/";
    
    public static final String project_AmendmentSubmissionHistoryTableId= "viewAmendment:viewAmendmentForm:submissionHistoryTab:j_id_4j:submissionHistoryTable";//"main:viewAmendment:viewAmendmentForm:submissionHistoryTab:_idJsp81:submissionHistoryTable";
            
    //**** My In Basket List ****
    public static final String fundingOpp_MyProjectSubmissionsTableId = "myInbasketCompletedSubmissionsForm:mibcs_SubmissionsTable_data";
    
    public static final String fundingOpp_myAssignedSubmissionsTableId = "myInbasketIncompleteSubmissionsForm:mibis_SubmissionsTable_data";
    
    public static final String fundingOpp_OptionalStepRe_ExecSelectionTableId = "optionalReexecuteSelectionForm:optionalReexecuteTable_data";
            
    //**** Applicants List ***
    public static final String applicantsTableId = "applicantListForm:applicantDataTable_data";//main:applicantListSubview:applicantListForm:j_id_4a:applicantDataTable
    
    public static final String applicantSubmissionTableId = "applicantSubmissionListForm:applicantSubmissionsDataTable1_data";//"main:applicantSubmissionListSubview:applicantSubmissionListForm:_idJsp153:applicantSubmissionsDataTable1"; //main:applicantSubmissionListSubview:applicantSubmissionListForm:_idJsp150
    
    public static final String applicantRegistrantsTableId = "/listUsersTable/";

    public static final String applicantsTypesTableId = "/listApplicantTypesTable/";
            
    //**** Amendments List****
    public static final String amendmentsTableId = "listAmendments:listAmendmentsTable_data"; //main:listAmendments:_idJsp31:_idJsp80
    
    public static final String amendedSubmissionsTableId = "stepAmendmentsListForm:listAmendmentsTable_data"; //main:stepAmendmentsList:_idJsp31:_idJsp52
            
    //**** Assign Evaluators List ****
    public static final String fundingOpp_AssignEvaluatorsTableId = "evaluatorAssignmentListForm:assignmentDataTable_data";
            
    //**** Evaluate Submission *****
    public static final String fundingOpp_EvaluateSubmissionsTableId = "/assignmentDataTable/";
            
    //**** Bulk Evaluate Submission *****
    public static final String fundingOpp_BulkEvaluateStepsTableId = "/:programStepList/"; //main:bulkEvalStepsView:bulkEvalStepsForm:_idJsp77
    public static final String fundingOpp_BulkEvaluateSubmissionsTableId = "/:evalSubmissionList/";//main:bulkEvalSubmissionssView:bulkEvalSubmissionsForm:_idJsp91
    public static final String bulk_OpenForm_AltStart = "Open Evaluation Form: ";
    
    //**** Award List *******
    public static final String awardsTableId = "listAwardSubmissionsForm:listSubmissionsTable_data"; //main:listAwardSubmissions:_idJsp31:_idJsp78
    
    public static final String milestoneTableId = "/data/";
    
    public static final String paSubmissionScheduleTableId = "/data/";
    
            
    //**** My Reports List ************
    public static final String myReprotsTableId = "/reportViewDataTable/";
    
    //**** Reports List ************
    public static final String reprotsTableId = "/reportConfigurationDataTable/";
    public static final String reprotingFieldsTableId = "/:etlFieldsList/"; //main:listForms:formsListForm:_idJsp79
    
    //**** Lookups List ******
    public static final String lookupListTableId = "lookupListForm:listLookupsTable_data";
    
    public static final String lookupValueTableId = "lookupValueListForm:listLookupValuesTable_data";
    
    //********** Reference Tables List *****************
    public static final String refTablesListTableId = "refTablesListForm:programStepList_data"; //main:refTablesListView:refTablesListForm:_idJsp76
    
    public static final String refTableDataListTableId = "g3-form:data_withoutLetterFilter_data";
    
    public static final String refTable_body_Id= "refTablesListForm:programStepList_data";//"main:refTablesListView:refTablesListForm:_idJsp76:programStepList:tbody_element";
    
    //********** Applicant Types List **********
    
    public static final String applicantTypesListTableId = "applicantTypeListForm:listApplicantTypesTable_data"; //main:list_ApplicantTypes:applicantTypeListForm:_idJsp46
            
    //**** Users Lsit *****
        
    public static final String usersTableId = "userListForm:listUsersTable_data";
    
    public static final String usersTableHeaderId = "userListForm:listUsersTable_head";
    
    public static final String usersAccessRightsTableId = "manageUserView:userTabbedForm:accessRightsView:editAccessRightsTab:accessRightsTable";
    
    public static final String usersAccessRightsRadioName = "manageUserView:userTabbedForm:accessRightsView:editAccessRightsTab:accessRightsTable:";
    
    public static final String groupsForUserTableId = "userGroupForm:userGroupTable_data";
    
    public static final String groupsForUserDivId = "userGroupForm:userGroupTable";
            
    //**** Groups Lists
    public static final String groupsTableId = "userListForm:listGroupsTable_data";
    
    public static final String groupAccessRightsFormName = "manageGroupView:groupTabbedForm";
    
    public static final String groupAccessRightsTableId = "manageGroupView:groupTabbedForm:editGroupAccessRightsView:editAccessRightsTab:accessRightsTable";
    
    public static final String groupAccessRightsRadioName = "manageGroupView:groupTabbedForm:editGroupAccessRightsView:editAccessRightsTab:accessRightsTable:";
    
    //***** Locked Out Users
    public static final String lockedOutUsers_TableId = "lockedOutUsersListForm:lockedOutUsersTable_data";
    
    //******* Organizations ***********************
    public static final String org_Org_TableId = "organizationListForm:organizationList_data";
    public static final String org_OrgFundOppList_TableId = "/organizationProgramsList/";
    public static final String org_OrgFormsList_TableId = "/organizationFormsList/";
    public static final String org_OrgDocList_TableId = "/organizationDocumentsList/";
    public static final String org_OrgReportsList_TableId = "/organizationReportsList/";
    public static final String org_OrgLookupsList_TableId = "/organizationLookupsList/";
    public static final String org_OrgUsersList_TableId = "/organizationUsersList/";
    public static final String org_OrgGroupsList_TableId = "/organizationGroupsList/";
    
  //******* System Settings ***********************
    
    public static final String sys_Settings_TableId = "lookupValueForm:grantiumConfigTable_data"; //main:GrantiumConfig:lookupValueForm:_idJsp32
    
    public static final String license_Management_TableId = "licenseManagementForm:licenseManagementTable_data"; //main:licenseManagement:licenseManagementForm:_idJsp32
    
  //**** Front Office ***
    
    //**** Access Rights Checkbox Table
    public static final String foAccessRightsCheckboxTableId = "A:B:MUTP:T_data"; //"main:A:B:E:G:_idJsp265:T";

    
    //**** Search Grant Programs Table Body
    public static final String foSearchProgramsResultTBodyId = "g3-form:browseFO_data";
    
    //*** Applicants Table and Sub Tables ***
    public static final String fOApplicantsTableId = "g3-form:applicantDataTable_data";//"g3-form:_idJsp101:applicantDataTable";
    
    public static final String fOApplicantsTableId_Header = "g3-form:applicantDataTable_head";//"g3-form:_idJsp101:applicantDataTable";
    
    public static final String fORegistrantsTableId = "g3-form:j_id_55:listUsersTable";//"g3-form:_idJsp114:listUsersTable"; //main:applicantUsers:applicantUsersForm:_idJsp117
    
    //**** Registrations Table
    public static final String fORegistrationTableId = "g3-form:registeredTable_data"; //"g3-form:j_id_4k:registeredTable";//"g3-form:_idJsp99:registeredTable"; //main:foProgramList:_idJsp94:_idJsp100
    
    public static final String fORegistrationTableId_rightIcon = "g3-form:j_id_4k:registeredTableIconRight";//"g3-form:_idJsp99:registeredTableIconRight";
    
    //**** Projects Table and Sub Tables ****
    
	public static final String poFilterSpanId = "/:filterAndFilterControls/";
    
    public static final String foProject_AlphbitTableId = "alphaFilterTable";
    
    public static final String fOProjectsTableId = "g3-form:projectsTable_data"; //"g3-form:_idJsp106:projectsTable"; //main:foProjectList:foProjectListForm:_idJsp111
   
    public static final String foProjectsTableBodyId = "g3-form:projectsTable_data";//"g3-form:_idJsp106:projectsTable:tbody_element";
    
    public static final String fOSubmissionsTableBodyId = "g3-form:submissionStepsTableAssoc_data";

    //no longer exists
    public static final String fOSubmissionsTableId = "g3-form:submissionStepsTableAssoc_data";//"g3-form:_idJsp212:submissionStepsTableAssoc"; //main:foSubmissionsList:foSubmissionsListForm:_idJsp145
    
    public static final String foEFormListTableId = "/data/";
    
    public static final String foAmendmentRequestHistoryTableId = "g3-form:j_id_52:listAmendmentsTable";//"g3-form:_idJsp110:listAmendmentsTable"; //main:applicantAmendmentRequestsList:_idJsp83:_idJsp98
    
    public static final String poProjectsListTable_Id = "projectListSubview:projectListForm:form_frame";
      
    public static final String fo_onDemandSub_tableID = "g3-form:onDemandSubmissionBeanTable_data";
    
    //****************** FO & PO Div Tag ids ***********************************************
    
    public static final String foFormletSpanId = "g3-form:fi";
    
    public static final String foSubmissionFilterDiv = "g3-form:filterAndFilterControls";
    
    public static final String poFilterTableId = "/filters/";
    
    public static final String poFilterEvaluateProjectsTableId = "evaluateProjectSubview:evaluateProjectsForm";
    
    public static final String poFilterTableBodyId = "/filters:tbody_element/";
    
    public static final String poTableBodyId = "/tbody_element/";
    
    
    
    // ***********  Audit Reporting *********************
    
    public static final String auditEventsTableId = "/:auditReportingEventList/";
    
    public static final String auditHistoryTableId = "projectListSubview:projectListForm:dataTableFrame:auditReportingHistoryDataTable";
    
    public static final String auditExportTableId = "/:auditReportingExportList/";
    
    
    //*********** Quartz Control Panel ************
    
    public static final String quartzControlTable = "quartzControlForm:quartzControlTable_data";
    
    public static final String quartzControlTable_Id = "quartzControlForm:quartzControlTable_data"; //"main:quartzControl:quartzControlForm:_idJsp32:quartzControlTable";
    
    public static final String quartzArchJob_lbl = "ArchivingJob";
    
    public static final String quartzDelArchJob_lbl = "DeleteArchivedDataJob";
    
    
    //********************* e.FOrm List Tables id **************************************
    
    
    public static final String eFormListDataWithLetters = "g3-form:data_withLetterFilter_data";
    
    public static final String eFormTextFieldID = "g3-form:eFormFieldList:0:textBox";
    
    public static final String notification_Date_Id=  "caseNotificationLogListSubview:caseNotificationLogListForm:filters:2:date1";
    
    
    public static final String evaluateProjectsTable_Id = "evaluateProjectSubview:evaluateProjectsForm:j_id_2n:evaluateProjectsList"; //main:evaluateProjectSubview:evaluateProjectsForm:_idJsp44:evaluateProjectsList";
   
    public static final String dataArchive_SubmissionsTable_Id = "projectListSubview:projectListForm:caseDataTable_data"; //main:projectListSubview:projectListForm:_idJsp79:caseDataTable";
     
    public static final String dataArchive_InBasket_SubmissionsList_Table_Id =  "inTakeInBasket:inTakeInBasket:listSubmissionsTable_data"; //"main:inTakeInBasket:inTakeInBasket:_idJsp82:listSubmissionsTable";
    
    public static final String da_Amendments_SubmissionsList_Table_Id = "listAmendments:j_id_1w:listAmendmentsTable_data"; //"main:listAmendments:_idJsp31:_idJsp80:listAmendmentsTable";
    
    public static final String da_MyProjectSubmissionsList_Table_Id = "myInbasketCompletedSubmissions:myInbasketCompletedSubmissionsForm:mibcs_SubmissionsTable_data"; //"main:myInbasketCompletedSubmissions:myInbasketCompletedSubmissionsForm:_idJsp78:mibcs_SubmissionsTable";
    
    public static final String dataArchive_applicantSubmissionsList_Table_Id = "applicantListSubview:applicantListForm:applicantDataTable_data"; //"main:applicantListSubview:applicantListForm:_idJsp80:applicantDataTable";
    
    public static final String applicantSubmissions_Table_Id = "applicantSubmissionListForm:applicantSubmissionsDataTable1_data"; //"main:applicantSubmissionListSubview:applicantSubmissionListForm:_idJsp153:applicantSubmissionsDataTable1";
    
    public static final String assignEvaluator_Table_ID = "assignmentListSubview:evaluatorAssignmentListForm:assignmentDataTable_data"; //"main:assignmentListSubview:evaluatorAssignmentListForm:_idJsp79:assignmentDataTable";
    
    public static final String evaluateSubmissions_Table_ID = "evaluateSubmissionsListForm:assignmentDataTable_data"; //"main:assignmentListSubview:evaluateSubmissionsListForm:_idJsp79:assignmentDataTable";
    
    public static final String myAssignedSubmissions_Table_Id = "myInbasketIncompleteSubmissions:myInbasketIncompleteSubmissionsForm:mibis_SubmissionsTable_data"; //"main:myInbasketIncompleteSubmissions:myInbasketIncompleteSubmissionsForm:_idJsp77:mibis_SubmissionsTable";
    
    public static final String foProjects_Table_ID = "g3-form:projectsTable_data";
    
    public static final String foSubmissions_TableId = "g3-form:submissionStepsTableAssoc_data";
    
    public static final String bulkEvaluation_ID = "bulkEvalSubmissionssView:bulkEvalSubmissionsForm:_idJsp91:evalSubmissionList";
      
    public static final String archiveLog_TableID = "archiveLogForm:archiveLogTable_data"; //"main:archiveLogSubview:archiveLogForm:_idJsp79:archiveLogTable";
    
    public static final String bulkEvalStepsView = "bulkEvalStepsView:bulkEvalStepsForm:j_id_43:programStepList"; //"main:bulkEvalStepsView:bulkEvalStepsForm:_idJsp77:programStepList";
    
    public static final String dataArchive_ManageArchiveFOPPListHead_Id = "dataArchiveListForm:archiveFundingOpportunityTable_head";
    
    public static final String dataArchive_ManageArchiveFOPPList_Id = "dataArchiveListForm:archiveFundingOpportunityTable_data"; //"main:dataArchiveListSubview:dataArchiveListForm:_idJsp79:archiveFundingOpportunityTable";
    
    public static final String da_ProjList_DivId = "archiveProjectListForm:archiveProjectDataTable";
    
    public static final String da_ProjTableList_Id = "archiveProjectListForm:archiveProjectDataTable_data";//"archiveProjectListForm:j_id_4g:archivedProjectDataTable"; //"main:archiveProjectListSubview:archiveProjectListForm:_idJsp84:archivedProjectDataTable";

	public static final String reportParams_Id = "main:selectParameterSubview:selectParameterForm:parametersTest";

}
