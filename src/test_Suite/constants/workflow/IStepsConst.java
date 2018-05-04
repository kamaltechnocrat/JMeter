/**
 * 
 */
package test_Suite.constants.workflow;

/**
 * @author mshakshouki
 *
 */
public interface IStepsConst {
	
	public static enum EStepsType
	{
		SUB,
		POSS,
		REVIEW,
		APPROVAL,
		AWARD,
		POST_AWARD,
		DECISION,
		WSS,
		IPASS,
		PA_POSS,
		PA_EVALUATION,
		PA_DECISION,
		PA_WSS
	}
	
	public static enum EPostAwardSteps
	{
		IPASS,
		PA_POSS,
		PA_EVALUATION,
		PA_DECISION,
		PA_WSS
		
	}
	
	public static enum EGeneralStepType {
		
		SUBMISSION,
		EVALUATION,
		POSTAWARD,
		IPASS,
		DECISION,
		WSS,
		NONE
	}
	
	
	public static enum EFormletTypes {
		
		noProp, typeProp, minMaxProp, dGrid, attchments, subSchedule, subSummary
	}
	
	
	public static final String formletTitlePostFix = " Fields PEF";
	public static final String formletIdentPostFix = "-Fields-PEF";
	public static final String subFormletTitlePostFix = " Sub Fields PEF";
	public static final String subFormletIdentPostFix = "-Sub-Fields-PEF";
	
	//************** Step Details Fields Tags Ids ***********
	public static final String step_Ident_Id = "editStep:step:tabPane:stepIdentifier";
	public static final String step_Notes_Id = "editStep:step:tabPane:stepNotes";	
	public static final String step_Type_Id = "editStep:step:tabPane:stepTypeSelect_input";
	public static final String step_Proj_Officer_Group_Id = "editStep:step:tabPane:stepAdministratorId_input";
	public static final String step_Start_Date_Id = "editStep:step:tabPane:stepStartDate_input";
	public static final String step_End_Date_Id = "editStep:step:tabPane:stepEndDate_input";
	public static final String step_Critical_Step_Id = "editStep:step:tabPane:criticalCheck_input";
	public static final String step_ReExecute_Id = "editStep:step:tabPane:reExecutionOptionSelect_input";
	public static final String step_Name_Locale_0_Id = "editStep:step:tabPane:stepTexts:0:stepTitleText";
	public static final String step_Name_Locale_1_Id = "editStep:step:tabPane:stepTexts:1:stepTitleText";
	public static final String step_Name_Locale_2_Id = "editStep:step:tabPane:stepTexts:2:stepTitleText";
	public static final String step_Name_Locale_3_Id = "editStep:step:tabPane:stepTexts:3:stepTitleText";
	
	//*********Eval Step Properties**************
	public static final String step_Prop_EFormIdent_Id = "editStep:step:tabPane:properties:0:integerDropdown_input";
	public static final String step_Prop_SharedEval_Id = "editStep:step:tabPane:properties:1:checkbox_input";
	public static final String step_Prop_EvaluationType_Id = "editStep:step:tabPane:properties:2:integerDropdown_input";
	public static final String step_Prop_QuorumAmount_Id = "editStep:step:tabPane:properties:3:textfield";
	public static final String step_Prop_AutoAssign_Id = "editStep:step:tabPane:properties:4:checkbox_input";
	public static final String step_Prop_COI_Id = "editStep:step:tabPane:properties:5:checkbox_input";
	public static final String stepProp_EvalSumVisibleGroups_M2M_Available_Id = "editStep:step:tabPane:properties:4:M2M_AvailableItems_h";
	public static final String stepProp_EvalSumVisibleGroups_M2M_Selected_Id = "editStep:step:tabPane:properties:4:M2M_SelectedItems_h";
	public static final String stepProp_BulkEval_AutoScoringAlg_Id = "editStep:step:tabPane:properties:9:textAreafield";
	public static final String stepProp_BulkEval_AutoScoringResult_Dropdown_Id = "editStep:step:tabPane:properties:10:dropdown_input";
	public static final String stepProp_BulkEval_CompCommAdminFormField_Dropdown_Id = "editStep:step:tabPane:properties:11:dropdown_input";
	public static final String stepProp_BulkEval_CompCommAmountField_Dropdown_Id = "editStep:step:tabPane:properties:12:dropdown_input";
	public static final String stepProp_BulkEval_eFormFieldsToDisplay_M2M_Available_Id = "/M2M_AvailableItems_v/";
	public static final String stepProp_BulkEval_eFormFieldsToDisplay_M2M_Selected_Id = "/M2M_SelectedItems_v/";
	public static final String step_Prop_Amendment_Id ="editStep:step:tabPane:properties:1:integerDropdown_input";
	
	//Post-award
	public static final String step_Prop_AwardStepName_Id = "editStep:step:tabPane:properties:0:integerDropdown_input";
	public static final String step_Prop_IPASS_M2M_Available_Id = "editStep:step:tabPane:properties:1:M2M_AvailableItems_v";
	public static final String step_Prop_IPASS_M2M_Selected_Id = "editStep:step:tabPane:properties:1:M2M_SelectedItems_v";
	
	//Decision
	public static final String step_Prop_Decision_DataFromStep_Id = "editStep:step:tabPane:properties:0:dropdown_input";
	public static final String step_Prop_Decision_SkipToStep_Id = "editStep:step:tabPane:properties:1:dropdown_input";
	public static final String step_Prop_Decision_Expression_Id ="main:editStep:step:tabPane:properties:2:textAreafield";
	
	//WorkFlow Suspension
	public static final String step_Prop_WSS_M2M_Available_Id = "editStep:step:tabPane:properties:0:M2M_AvailableItems";
	public static final String step_Prop_WSS_M2M_Selected_Id = "editStep:step:tabPane:properties:properties:0:M2M_SelectedItems";
	
	//
	public static final String NoPropretiesFieldsPEF = "No Propreties Fields PEF";
	public static final String TypePropretiesFieldsPEF = "Type Propreties Fields PEF";
	public static final String MinMaxPropretiesFieldsPEF = "Min And Max Propreties Fields PEF";
	public static final String DataGridFieldsPEF= "Data Grid Fields PEF";
	public static final String ScoringPropsSubFieldsPEF = "Scoring Properties Sub Fields PEF";
	public static final String AttachmentFieldsPEF =  "Attachment List Fields PEF";
	
	
	//******** Evaluation Steps Properties ******************
	public static final Object stepProp_NotCrit = Boolean.FALSE;
	public static final Object stepProp_Crit = Boolean.TRUE;
	public static final Object stepProp_RexecOptNo = "Optional (No)";
	public static final Object stepProp_RexecOptYes = "Optional (Yes)";
	public static final Object stepProp_RexecNo = "No";
	public static final Object stepProp_RexecYes = "Yes";
	public static final Object stepProp_EvalType_Qrm = "Quorom";
	public static final Object stepProp_EValType_Mjrty = "Majority";
	public static final Object stepProp_EValType_Unanms = "Unanimous";
	public static final Object stepProp_AutoAssign = Boolean.TRUE;
	public static final Object stepProp_ManuAssign = Boolean.FALSE;
	public static final Object stepProp_No_COI = Boolean.FALSE;
	public static final Object stepProp_With_COI = Boolean.TRUE;
	
	public static final Object[] evals_CritReexecYesAuto = {stepProp_Crit, stepProp_RexecYes,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_CritReexecNoAuto[] = {stepProp_Crit, stepProp_RexecNo,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_CritReexecOptionalYesAuto[] = {stepProp_Crit, stepProp_RexecOptYes,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_CritReexecOptionalNoAuto[] = {stepProp_Crit, stepProp_RexecOptNo,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	
	public static final Object evals_NonCritReexecYesAuto[] = {stepProp_NotCrit, stepProp_RexecYes,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecNoAuto[] = {stepProp_NotCrit, stepProp_RexecNo,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecOptionalYesAuto[] = {stepProp_NotCrit, stepProp_RexecOptYes,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecOptionalNoAuto[] = {stepProp_NotCrit, stepProp_RexecOptNo,stepProp_EvalType_Qrm, 1, stepProp_AutoAssign, stepProp_No_COI};
	
	public static final Object evals_CritReexecYesManu[] = {stepProp_Crit, stepProp_RexecYes,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_CritReexecNoManu[] = {stepProp_Crit, stepProp_RexecNo,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_CritReexecOptionalYesManu[] = {stepProp_Crit, stepProp_RexecOptYes,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_CritReexecOptionalNoManu[] = {stepProp_Crit, stepProp_RexecOptNo,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	
	public static final Object evals_NonCritReexecYesManu[] = {stepProp_NotCrit, stepProp_RexecYes,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecNoManu[] = {stepProp_NotCrit, stepProp_RexecNo,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecOptionalYesManu[] = {stepProp_NotCrit, stepProp_RexecOptYes,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	public static final Object evals_NonCritReexecOptionalNoManu[] = {stepProp_NotCrit, stepProp_RexecOptNo,stepProp_EvalType_Qrm, 1, stepProp_ManuAssign, stepProp_No_COI};
	
	
	
	//***********Step Management *****************
	
	public static enum EStepProps {
		EFORM_IDENT,
		EVAL_TYPE,
		QUOROM,
		ASSIGN,
		COI,
		AWARD_STEP_IDENT,
		IPASS_EFORM_IDENT1,
		IPASS_EFORM_IDENT2,
		IPASS_EFORM_IDENT3,
		FROMSTEP,
		SKIPSTEP,
		EXPRESSION,
		WSSSTEP1,
		WSSSTEP2			
	}
	
	public static enum EStepParams {
		NAME,
		IDENT,
		NOTES,
		TYPE,
		OFFICER,
		STARTDATE,
		ENDDATE,
		CRITICAL,
		RE_EXECUTE,
		TITLE
	}
	
	public static enum ESubProp {
		EFORM_IDENT
	}
	
	public static enum EEvalProp {
		EFORM_IDENT,
		EVAL_TYPE,
		QUOROM,
		ASSIGN,
		COI
	}
	
	public static enum EPostAwardProp {
		AWARD_STEP_IDENT,
		IPASS_EFORM_IDENT1,
		IPASS_EFORM_IDENT2,
		IPASS_EFORM_IDENT3
	}
	
	public static enum EWSSProp {
		WSSSTEP1,
		WSSSTEP2	
	}
	
	public static enum EDecisionProp {
		FROMSTEP,
		SKIPSTEP,
		EXPRESSION
	}
	
	public static final String[] formletTypes = { 
		"No Propreties",
		"Type Propreties", "Min And Max Propreties",
		"Data Grid", "Attachment List", "PA Submission Schedule",
		"Submission Summary" };
	
	
	

	
	//Step Staff
	
	//Documents
	
	//Notification
	
	//eForm Access
	public static final String step_Manage_EFormAccess_AvailableForms_Id = "stepFormAccess:formAccessForm:availableForms";
	public static final String step_Manage_EFormAccess_SelectedForms_Id = "stepFormAccess:formAccessForm:selectedForms";
	
	//e.Form Data
	public static final String step_Manage_EFormData_IncludeApplicant_Id = "stepFormData:formDataForm:applicantForm";
	public static final String step_Manage_EFormData_IncludeAdminEForm_Id = "stepFormData:formDataForm:programForm";
	public static final String step_Manage_EFormData_AvailableForms_Id = "stepFormData:formDataForm:availableForms";
	public static final String step_Manage_EFormData_SelectedForms_Id = "stepFormData:formDataForm:selectedForms";
	public static final String step_Details_BulkAutoScoreDd_Id = "main:editBulkEvalAutomatedScoring:step:eFormFieldDropdown";
	public static final String step_Details_BulkAutoScoreExp_Id = "main:editBulkEvalAutomatedScoring:step:expression";

	
//	###***** Forms ******
	public static final String gnrl_SubmissionA[][] = {{"Submission-A", "/ApplicantSingleSubmissionStep/", "Applicant Submission", "true", "No"}, {"/Sub/"}};
	public static final String gnrl_SubmissionB[][] = {{"Submission-B", "/ApplicantSingleSubmissionStep/", "Applicant Submission", "true", "No"}, {"/Sub/"}};
	
	public static final String gnrl_ReviewA[][] = {{"Review-A", "/ReviewStep/", "Review", "true", "No"}, {"Review Form", "Quorum", "1", "false"}};
	public static final String gnrl_ApprovA[][] = {{"Approval-A", "/approvalStep/", "Approval", "true", "No"}, {"Approval Form", "Quorum", "1", "false"}};
	
	public static final String gnrl_ReviewB[][] = {{"Review-B", "/ReviewStep/", "Review", "true", "No"}, {"Review Form", "Quorum", "1", "false"}};
	public static final String gnrl_ApprovB[][] = {{"Approval-B", "/approvalStep/", "Approval", "true", "No"}, {"Approval Form", "Quorum", "1", "false"}};

	public static final String gnrl_AwardCrit[][] = {{"Award", "/awardStep/", "Award", "true", "Yes"}, {"Basic Agreement"}};
	public static final String gnrl_AwardNon[][] = {{"Award", "/awardStep/", "Award", "false", "No"}, {"Basic Agreement"}};

//	###***** Post-Award Type Forms ******

	public static final String reviewQuoCritAuto[][] = {{"Review-CRQA", "/ReviewStep/", "Review", "true", "Optional (Yes)"}, {"Review Form", "Quorum", "1", "true"}};
    public static final String reviewMajoCritAuto[][] = {{"Review-CRMA", "/ReviewStep/", "Review", "true", "Yes"}, {"Review Form", "Majority", "", "true"}};   
    public static final String reviewUnanCritAuto[][] = {{"Review-CRUA", "/ReviewStep/", "Review", "true", "Yes"}, {"Review Form", "Unanimous", "", "true"}};

    public static final String reviewQuoNonAuto[][] = {{"Review-NNQA", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Quorum", "1", "true"}};
    public static final String reviewMajoNonAuto[][] = {{"Review-NNMA", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Majority", "", "true"}};   
    public static final String reviewUnanNonAuto[][] = {{"Review-NNUA", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Unanimous", "", "true"}};

    public static final String approvQuoNonAuto[][] = {{"Approval-NRQA", "/approvalStep/", "Approval", "false", "Yes"}, {"Approval Form", "Quorum", "1", "true"}};
    public static final String approvMajoNonAuto[][] = {{"Approval-NNMA", "/approvalStep/", "Approval", "false", "No"}, {"Approval Form", "Majority", "", "true"}};   
    public static final String approvUnanNonAuto[][] = {{"Approval-NNUA", "/approvalStep/", "Approval", "false", "No"}, {"Approval Form", "Unanimous", "", "true"}};
    
    public static final String approvQuoCritAuto[][] = {{"Approval-CRQA", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Quorum", "1", "true"}};   
    public static final String approvMajoCritAuto[][] = {{"Approval-CRMA", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Majority", "", "true"}};   
    public static final String approvUnanCritAuto[][] = {{"Approval-CRUA", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Unanimous", "", "true"}};


    //###**** Post Award *****
      
	public static final String pa_AwardCrit[][] = {{"Award-Crit", "/awardStep/", "Award", "true", "Yes"}, {"Standard Agreement"}};
	public static final String pa_AwardNon[][] = {{"Award-Non", "/awardStep/", "Award", "true", "No"}, {"Standard Agreement"}};
	
	public static final String pa_GPS_AwardCrit[][] = {{"Award-Crit", "/awardStep/", "Award", "true", "Yes"}, {"/Standard Payment Schedule/"}};
	public static final String pa_GPS_AwardNon[][] = {{"Award-Non", "/awardStep/", "Award", "true", "No"}, {"/Standard Payment Schedule/"}};

	public static final String initialClaim[][] = {{"Initial-Claim", "/ApplicantSingleSubmissionStep/", "Initial Post Award Submission", "true", "Optional (Yes)"}, {"/Post Award Submission/", "Claim ", "true"}};

	public static final String postAwardNon[][] = {{"Post-Award", "/postAwardStep/", "Post-Award", "true", "No"}, {pa_AwardNon[0][0], initialClaim[1][0]}};
	public static final String postAwardCrit[][] = {{"Post-Award", "/postAwardStep/", "Post-Award", "true", "Yes"}, {pa_AwardCrit[0][0], initialClaim[1][0]}};
	
    public static final String reviewQuoCritManu[][] = {{"Review-CRQM", "/ReviewStep/", "Review", "true", "Yes"}, {"Review Form", "Quorum", "1", "false"}};
    public static final String reviewMajoCritManu[][] = {{"Review-CRMM", "/ReviewStep/", "Review", "true", "Yes"}, {"Review Form", "Majority", "", "false"}};
    public static final String reviewUnanCritManu[][] = {{"Review-CRUM", "/ReviewStep", "Review", "true", "Yes"}, {"Review Form", "Unanimous", "", "false"}};
    
    public static final String reviewQuoNonManu[][] = {{"Review-NNQM", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Quorum", "1", "false"}};
    public static final String reviewMajoNonManu[][] = {{"Review-NNMM", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Majority", "", "false"}};
    public static final String reviewUnanNonManu[][] = {{"Review-NNUM", "/ReviewStep/", "Review", "false", "No"}, {"Review Form", "Unanimous", "", "false"}};
    
    public static final String approvQuoCritManu[][] = {{"Approval-CRQM", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Quorum", "1", "false"}};   
    public static final String approvMajoCritManu[][] = {{"Approval-CRMM", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Majority", "", "false"}};   
    public static final String approvUnanCritManu[][] = {{"Approval-CRUM", "/approvalStep/", "Approval", "true", "Yes"}, {"Approval Form", "Unanimous", "", "false"}};   

    public static final String approvQuoNonManu[][] = {{"Approval-NNQM", "/approvalStep/", "Approval", "false", "No"}, {"Approval Form", "Quorum", "1", "false"}};
    public static final String approvMajoNonManu[][] = {{"Approval-NNMM", "/approvalStep/", "Approval", "false", "No"}, {"Approval Form", "Majority", "", "false"}};
    public static final String approvUnanNonManu[][] = {{"Approval-NNUM", "/approvalStep/", "Approval", "false", "No"}, {"Approval Form", "Unanimous", "", "false"}};

    //#******************************************************************************

	
	
	//###****** Post-Award Bring Forward *********
	public static final String pA_BF_Submission[][] = {{"Submission", "/ApplicantSingleSubmissionStep/", "Applicant Single Submission Step", "true", "No"}, {"Simple BF Submission"}};
	public static final String pA_BF_InitialClaim[][] = {{"Initial-Claim", "/ApplicantSingleSubmissionStep/", "Initial Claim Step", "true", "No"}, {"PA BF Claim Submission", "Claim BF Submission ", "true"}};


}
