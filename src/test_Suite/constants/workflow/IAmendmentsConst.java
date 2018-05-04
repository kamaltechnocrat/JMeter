package test_Suite.constants.workflow;

public interface IAmendmentsConst {
    /*********************************************
    #         Progam, Projects Org etc
    #*********************************************/
	
	public static final String amend_FOPP_Prefix = "-Amend-FOPP-";
	
	public static final String amend_PAP_Prefix = "-Amend-PAP-";
	
    //###*****Post award ********************
	public static final String amend_PA_Prefix = "-Amend-PA-";
	
    
    //#**********************************************
	
	public static final String amendCategory_LookupName = "Amendment Categories";
	
	public static final String amendedByMessage = "This submission is being amended by ";
    
    
    /****************************************************
    #             Notifications Steps
    #***************************************************/
    
    public static final String stpApplicantValues[] = {"Amendment", "Amendment", "Grantium Amendment",
                             "Amendment Notification For Applicant, to let you know that the step has being Amended",
                             "true", "true", "false"};
                             
    public static final String stpProjOfficValues[] = {"Amendment", "Amendment", "Grantium Amendment ",
                             "Amendment Notification For Project Officer, to let you know that the step has being Amended",
                             "true", "false", "true"};
                             
    public static final String stpBothYesValues[]      = {"Amendment", "Amendment", "Grantium Amendment ",
                             "Amendment Notification For Project Officer and Applicant, to let you know that the step has being Amended",
                             "true", "true", "true"};
    
    public static final String stpBothNoValues[]      = {"Amendment", "Amendment", "Grantium Amendment",
                             "Amendment Notification For the Amender",
                             "true", "false", "false"};
    
    /****************************************************
    #             Amendments Request Page tag Ids
    #***************************************************/
    
    public static final String amendRequest_Amender_DropDwnFld_ID = "requestAmendmentForm:selAmender_input";
    
    public static final String amendRequest_DateRequiredBy_DateFld_ID = "requestAmendmentForm:dateRequiredBy_input";
    
    public static final String amendRequest_Reason_TxtFld_ID = "requestAmendmentForm:txtAmemendmentReason";
    
    public static final String amendRequest_Comment_TxtFld_ID = "requestAmendmentForm:txtAmemendmentComments";
    
    public static final String amendRequest_Category_DropDwnFld_ID = "requestAmendmentForm:selCategory_input";
    
    /****************************************************
    #             Amendments Lists Page Icons and Links
    #***************************************************/
    
    public static final String amendList_Cancel_LnkTitle = "Cancel Amendment: ";
    
    public static final String amendList_Transfer_LnkTitle = "View Or Edit Amendment Details: ";
    
    public static final String amendList_Open_LnkTitle = "View Submission: ";
    
    public static final String MyProjSubmissionsList_Request_LnkTitle = "Request Amendment";
    
    public static final String MyProjSubmissionsList_Request_ImageAlt = "Request Amendment ";
    
    public static final String defaultPO_AmendmentCategory="Program Office Amendment";
    
    public static final String defaultFO_AmendmentCategory="Front Office Amendment";
    
    /*************************************************************
    #     Step Amendment Request (Project List) Page tag Ids
    #*************************************************************/
    
    public static final String stepAmend_RequestAmendment_ImageAlt = "Request Amendment ";
    
    public static final String stepAmend_RequestStepAmendmentAt_DropdownId = "requestAmendmentForm:subproject_input";
    
    public static final String stepAmend_AmendmentRequest_FormId = "requestAmendmentForm";
    
    public static final String stepAmend_StepToAmend_DropDownId = "requestAmendmentForm:submissionStep_input";
    
    public static final String subStepAmend_Dropdown_Id = "requestAmendmentForm:subproject_input";
    
    public static final String stepAmend_ClaimToStart_DropDownId = "requestAmendmentForm:startingClaim_input";
    
    public static final String stepAmend_AmendForAllStepStaff_CheckboxId = "main:requestAmendment:requestAmendmentForm:_idJsp42";
    
    public static final String stepAmend_NonShared_AAFS_M2MId = "requestAmendmentForm:M2M_AvailableItems";
    
    public static final String stepAmend_NonShared_SAFS_M2MId = "requestAmendmentForm:M2M_SelectedItems";
    
    public static final String stepAmend_View_Comment_TxtFld_ID = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtAmemendmentComments";
    
    public static final String stepAmend_View_Reason_TxtFld_ID = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtAmemendmentReason";
    
    public static final String stepAmend_View_DateRequired_DateFld_ID = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:dateRequiredBy_input";
    
    public static final String stepAmend_View_Amender_Dropdown_ID = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:selAmender_input";
    
    public static final String stepAmend_CancelSub_Reason_TxtFld_ID = "cancelAmendment:cancelAmendmentForm:txtCancelReason";
    
    public static final String stepAmend_Approved_String = ": Submitted, Approved";
    
    public static final String stepAmend_Rejected_String = ": Submitted, Rejected";
    
    public static final String stepAmend_Pending_String = ": Submitted, Pending Approval";
    
    public static final String stepAmend_Submitted_String = ": Submitted";
    
    public static final String stepAmend_InProgress_String = ": In Progress";
    
    public static final String stepAmend_NotUsed_String = ": Never Opened";
    
    public static final String stepAmend_SubProjOptionalReexec_TableId = "main:optionalReexecuteSelectionView:optionalReexecuteSelectionForm:optionalReexecuteTable:0:expandedSubProjectReexecutionPlanTable";
    
    public static final String stepAmend_OptionalStepReexec_TableId = "main:optionalReexecuteSelectionView:optionalReexecuteSelectionForm:optionalReexecuteTable";
    
    public static final String stepAmend_Optional_PA_StepReexec_DivId = "postAwardReexecuteSelectionForm:postAwardReexecuteTable:0:expandedSubProjectReexecutionPlanTable";
    
    public static final String stepAmend_Optional_PA_StepReexec_TableId = "postAwardReexecuteSelectionForm:postAwardReexecuteTable:0:expandedSubProjectReexecutionPlanTable_data";
    
    public static final String stepAmend_Optional_PA_StepReexec_ExpandLink_Title = "Post Award Workflow Details";
    
    public static final String amendmentRequest_StepReExec_FormId = "main:optionalReexecuteSelectionView:optionalReexecuteSelectionForm";
    
    public static String amendmentID_Format="(#?[0-9]{1,3})";
	
	public static String Amendmentformat_HardCoded_Value="-";
	
	public static String Amendmentformat_HardCoded_Value1="Requested";
	
	public static String Amendmentformat_Auto_ReExecuted_HardCoded_Value="Auto";
	
	public static String Amendmentformat_Auto_ReExecuted_HardCoded_Value1="Re-executed";
	
	public static String Amendmentformat_HardCoded_Value2="Cancelled";
	
	public static String AmendmentTime_Format="(([0]?[1-9]|1[0-2])(:|\\.)([0-5][0-9])(:|\\.)([0-5][0-9]))";
	
	public static String AmendmentTime_Format1="(AM|am|aM|Am|PM|pm|pM|Pm|PM,)";
    
    /*************************************************************
    #     Amendment Details Page tag Ids
    #*************************************************************/
    
    public static final String amendmentDetails_Decision_DropDwnFld_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtDecision_input";
    
    public static final String amendmentDetails_ApprovalReason_TextField_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtProcessReason";
    
    public static final String amendmentDetails_Approver_DropDwnFld_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:selApprover_input";
    
    public static final String amendmentDetails_RequiredBy_Date_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:dateRequiredBy_input";
    
    public static final String amendmentDetails_Amender_DropDwnFld_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:selAmender_input";
    
    public static final String amendmentDetails_AmendmentComment_TextField_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtAmemendmentComments";
    
    public static final String amendmentDetails_AmendmentReason_TextField_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:txtAmemendmentReason";
    
    public static final String amendmentDetails_Category_DropDwnFld_Id = "viewAmendment:viewAmendmentForm:editAmendmentTabbedPane:selCategory_input";
    
    public static final String amendmentDetails_TableId="listAmendments:_idJsp31:form_frame";

    /*************************************************************
	#     				Applicnat Amendments
    #*************************************************************/    
    
    public static final String foAmendmentRequest_Amender_DropDwnFld_Id = "g3-form:selAmender_input";
    
    public static final String foAmendmentRequest_DateRequiredBy_Id = "main:viewAmendment:viewAmendmentForm:editAmendmentTab:dateRequiredBy";
    
    public static final String foAmendmentRequest_AmendmentReason_TextField_Id = "g3-form:txtAmemendmentReason";
    
    public static final String foAmendmentRequest_AmendmentComment_TextField_Id = "g3-form:txtAmemendmentComments";
    
    public static final String foAmendmentRequest_CancelReason_TextField_Id = "main:cancelAmendment:cancelAmendmentForm:txtCancelReason";
    
    public static final String foAmendmentRequest_GlobalMessages_Span_Id = "/globalMessages/";
    
    public static final String foAmendmentRequest_GlobalMessages_Span_Class = "ui-messages-info-summary";
    
    public static final String foAmendmentRequest_SuccessfullMessage = "Amendment has been successfully requested, and the approver will receive the notification soon.";
    
    public static final String foAmendmentRequest_CancelMessage = "Amendment request has been cancelled.";
    
    public static final String foAmendNow_SuccessMessage = "A new version of the submission has been ready for the amender.";
    
    public static enum EAmendmentOptions {
    	CANCEL,REQUEST,DETAILS,AMENDNOW
    }
    
    public static final String[] EAmendmentIconsAlts = {"Cancel Amendment Request","Request Amendment","Amendment Request History"};
    	  
}
